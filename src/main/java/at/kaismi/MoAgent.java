package at.kaismi;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.xml.bind.JAXBException;

import org.apache.commons.cli.*;
import org.snmp4j.TransportMapping;
import org.snmp4j.agent.*;
import org.snmp4j.agent.mo.MOTableRow;
import org.snmp4j.agent.mo.snmp.*;
import org.snmp4j.agent.security.MutableVACM;
import org.snmp4j.mp.MPv3;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.security.SecurityModel;
import org.snmp4j.security.USM;
import org.snmp4j.smi.*;
import org.snmp4j.transport.TransportMappings;

public class MoAgent extends BaseAgent {

    private static MoAgent moAgent = null;
    private String address;
    private String community;

    private MoAgent(String address, String community) throws IOException {
        /**
         * Creates a base agent with boot-counter, config file, and a
         * CommandProcessor for processing SNMP requests. Parameters:
         * "bootCounterFile" - a file with serialized boot-counter information
         * (read/write). If the file does not exist it is created on shutdown of
         * the agent. "configFile" - a file with serialized configuration
         * information (read/write). If the file does not exist it is created on
         * shutdown of the agent. "commandProcessor" - the CommandProcessor
         * instance that handles the SNMP requests.
         */
        super(new File("conf.agent"), new File("bootCounter.agent"),
            new CommandProcessor(new OctetString(MPv3.createLocalEngineID())));
        this.address = address;
        this.community = community;
    }

    public static void main(String[] args) throws IOException {
        Options options = new Options();
        options.addOption(Option.builder("a").longOpt("address").required()
            .desc("Required address of agent - e.g. 127.0.0.1/9999").hasArg().build());
        options.addOption(
            Option.builder("c").longOpt("community").desc("Optional community - default public").hasArg().build());
        options.addOption(
            Option.builder("f").longOpt("file").desc("Optional managed objects xml file path").hasArg().build());
        options.addOption(
            Option.builder("s").longOpt("shellMode").desc("Optional shellMode - default false").hasArg().build());

        String address = null;
        String community = null;
        String file = null;
        boolean shellMode = false;

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            address = cmd.getOptionValue("a");
            community = cmd.getOptionValue("c", "public");
            file = cmd.getOptionValue("f");
            shellMode = cmd.getOptionValue("s", "false").equals("true") || cmd.getOptionValue("s", "false").equals("1");
        } catch (ParseException e) {
            printHelp(options);
        }

        System.out.println(String.format("Agent start with address %s and community %s", address, community));
        moAgent = new MoAgent(address, community);
        moAgent.start();
        System.out.println("Agent started");
        System.out.println("Registering managed objects");

        if (file == null) {
            System.out.println("No managed objects xml file path provided - use Snmpv2MIB");
        } else {
            // Since BaseAgent registers some MIBs by default we need to unregister
            // one before we register our own sysDescr. Normally you would
            // override that method and register the MIBs that you need
            moAgent.unregisterManagedObject(moAgent.getSnmpv2MIB());
            System.out.println("Unregistered Snmpv2MIB");

            System.out.println(String.format("Managed objects xml file registration - %s", file));
            moAgent.registerManagedObjects(new File(file));
            System.out.println("Managed objects xml file registered");
        }

        if (shellMode) {
            System.out.println("Agent running - write exit to stop");
            Scanner scanner = new Scanner(System.in);
            while (!scanner.hasNext("exit")) {
                scanner.nextLine();
            }
            moAgent.stop();
            System.out.println("Agent stopped");
        } else {
            // Wait forever - until jvm is killed
            try {
                Thread.currentThread().join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                moAgent.stop();
                System.out.println("Agent stopped");
            }
        }
    }

    private static void printHelp(Options options) {
        HelpFormatter formater = new HelpFormatter();
        formater.printHelp("Main", options);
        System.exit(0);
    }

    private void registerManagedObjects(File file) {
        if (file.exists()) {
            MoXmlParser moXmlParser = new MoXmlParser(file);

            List<Mo> mos = null;
            try {
                mos = moXmlParser.parse();
            } catch (JAXBException e) {
                System.out.println(String.format("Error parsing file %s", file.getAbsolutePath()));
                e.printStackTrace();
                moAgent.stop();
                System.exit(0);
            }

            MoCreator moCreator = new MoCreator();
            for (Mo mo : mos) {
                System.out.println(String.format("Register managed object %s", mo));
                moAgent.registerManagedObject(moCreator.create(new OID(mo.getOid()), mo.getValue(), mo.getAccess()));
            }
        } else {
            System.out.println(String.format("File %s does not exist - nothing to register", file.getAbsolutePath()));
        }
    }

    @Override
    protected void addCommunities(SnmpCommunityMIB communityMIB) {
        Variable[] com2sec = new Variable[] {
            new OctetString(community), new OctetString(String.format("c%s", community)), // security name
            getAgent().getContextEngineID(), // local engine ID
            new OctetString(community), // default context name
            new OctetString(), // transport tag
            new Integer32(StorageType.nonVolatile), // storage type
            new Integer32(RowStatus.active) // row status
        };
        MOTableRow row = communityMIB.getSnmpCommunityEntry()
            .createRow(new OctetString(String.format("%s2%s", community, community)).toSubIndex(true), com2sec);
        communityMIB.getSnmpCommunityEntry().addRow(row);
    }

    @Override
    protected void addViews(VacmMIB vacm) {
        vacm.addGroup(SecurityModel.SECURITY_MODEL_SNMPv2c, new OctetString(String.format("c%s", community)),
            new OctetString("v1v2group"), StorageType.nonVolatile);

        vacm.addAccess(new OctetString("v1v2group"), new OctetString(community), SecurityModel.SECURITY_MODEL_ANY,
            SecurityLevel.NOAUTH_NOPRIV, MutableVACM.VACM_MATCH_EXACT, new OctetString("fullReadView"),
            new OctetString("fullWriteView"), new OctetString("fullNotifyView"), StorageType.nonVolatile);

        vacm.addViewTreeFamily(new OctetString("fullReadView"), new OID("1.3"), new OctetString(),
            VacmMIB.vacmViewIncluded, StorageType.nonVolatile);
    }

    @Override
    protected void unregisterManagedObjects() {
    }

    @Override
    protected void addUsmUser(USM usm) {
    }

    @Override
    protected void addNotificationTargets(SnmpTargetMIB snmpTargetMIB, SnmpNotificationMIB snmpNotificationMIB) {
    }

    @Override
    protected void registerManagedObjects() {
    }

    @Override
    protected void initTransportMappings() throws IOException {
        transportMappings = new TransportMapping[1];
        Address addr = GenericAddress.parse(address);
        TransportMapping tm = TransportMappings.getInstance().createTransportMapping(addr);
        transportMappings[0] = tm;
    }

    public void start() throws IOException {

        init();
        // This method reads some old config from a file and causes
        // unexpected behavior.
        // loadConfig(ImportModes.REPLACE_CREATE);
        addShutdownHook();
        getServer().addContext(new OctetString(community));
        finishInit();
        run();
    }

    public void registerManagedObject(ManagedObject mo) {
        try {
            server.register(mo, null);
        } catch (DuplicateRegistrationException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void unregisterManagedObject(MOGroup moGroup) {
        moGroup.unregisterMOs(server, getContext(moGroup));
    }

}
