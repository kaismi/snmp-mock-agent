package at.kaismi.snmpmockagent;

import java.io.File;
import java.util.List;

import at.kaismi.snmpmockagent.model.Mo;
import at.kaismi.snmpmockagent.model.Mos;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

class MoXmlParser {

    private final File file;

    MoXmlParser(File file) {
        this.file = file;
    }

    List<Mo> parse() throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(Mos.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        Mos mos = (Mos)unmarshaller.unmarshal(file);
        return mos.getMos();
    }
}
