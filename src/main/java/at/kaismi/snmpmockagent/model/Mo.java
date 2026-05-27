package at.kaismi.snmpmockagent.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

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
        return "[" + oid + "]" +
                "[" + value + "]" +
                "[" + access.name() + "]";
    }
}
