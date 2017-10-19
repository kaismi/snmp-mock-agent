# snmp-mock-agent
Simple XML based java snmp mock agent for testing purposes.

# Usage
Example: java -jar snmp-mock-agent-1.0-SNAPSHOT-jar-with-dependencies.jar -a  127.0.0.1/9999 -f mos.xml -s true

-a,--address <arg>     Required address of agent - e.g. 127.0.0.1/9999

-c,--community <arg>   Optional community - default public

-f,--file <arg>        Optional managed objects xml file path

-s,--shellMode <arg>   Optional shellMode - default false
