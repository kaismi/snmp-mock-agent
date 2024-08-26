# snmp-mock-agent

Start application on specific port with mos.xml reference

```shell
# -a Required address of agent - e.g. 127.0.0.1/9999 
# -f Optional managed objects xml file path
# -s Optional shellMode - default false
java -jar snmp-mock-agent-*-jar-with-dependencies.jar -a  127.0.0.1/9999 -f mos.xml -s true
```

Example mos.xml

```xml
<?xml version='1.0' encoding='UTF-8'?>
<mos>
    <mo>
        <oid>.1.3.6.1.4.1.29671.1.1.2.1.2.0.56.66.111.49.80.99.54</oid>
        <value>LAB-2-KLU - switch</value>
        <access>READ_ONLY</access>
    </mo>
    <mo>
        <oid>.1.3.6.1.4.1.29671.1.1.2.1.2.0.73.106.119.53.100.100.54</oid>
        <value>LAB-4-VIE - wireless</value>
        <access>READ_ONLY</access>
    </mo>
    <mo>
        <oid>.1.3.6.1.4.1.29671.1.1.2.1.2.0.75.99.119.90.108.97.54</oid>
        <value>8230_Test03_912345678_Switch</value>
        <access>READ_ONLY</access>
    </mo>
    <mo>
        <oid>.1.3.6.1.4.1.29671.1.1.2.1.2.0.76.73.102.52.81.97.54</oid>
        <value>8230_Test04_912345678_WLAN</value>
        <access>READ_ONLY</access>
    </mo>
    <mo>
        <oid>.1.3.6.1.4.1.29671.1.1.2.1.2.0.81.67.82.76.80.98.54</oid>
        <value>8230_Test01_911345678_WLAN</value>
        <access>READ_ONLY</access>
    </mo>
    <mo>
        <oid>.1.3.6.1.4.1.29671.1.1.2.1.2.0.86.84.68.72.76.98.54</oid>
        <value>LAB-3-Linz</value>
        <access>READ_ONLY</access>
    </mo>
</mos>
```

