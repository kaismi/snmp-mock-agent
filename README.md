# snmp-mock-agent

Simple snmp mock agent for testing purposes that takes XML files as input.

```shell
# -a Required address of agent - e.g. 127.0.0.1/9999 
# -f Optional managed objects xml file path
# -s Optional shellMode - default false
java -jar snmp-mock-agent-1.1-SNAPSHOT-jar-with-dependencies.jar -a  127.0.0.1/9999 -f mos.xml -s true
```

