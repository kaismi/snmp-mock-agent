package at.kaismi.snmpmockagent;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Mo {

    @XmlElement(name = "oid")
    private String oid;
    @XmlElement(name = "value")
    private String value;
    @XmlElement(name = "access")
    private MoAccess access;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MoAccess getAccess() {
        return access;
    }

    public void setAccess(MoAccess access) {
        this.access = access;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[" + oid + "]");
        builder.append("[" + value + "]");
        builder.append("[" + access.name() + "]");
        return builder.toString();
    }
}
