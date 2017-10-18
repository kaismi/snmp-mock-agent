package at.kaismi;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

import org.snmp4j.agent.MOAccess;
import org.snmp4j.agent.mo.MOAccessImpl;

@XmlType(name = "access")
@XmlEnum
public enum MoAccess {

    READ_ONLY(MOAccessImpl.ACCESS_READ_ONLY),
    WRITE_ONLY(MOAccessImpl.ACCESS_WRITE_ONLY),
    READ_WRITE(MOAccessImpl.ACCESS_READ_WRITE),
    READ_CREATE(MOAccessImpl.ACCESS_READ_CREATE);

    private MOAccess moAccess;

    MoAccess(MOAccess moAccess) {
        this.moAccess = moAccess;
    }

    public static MoAccess fromValue(String v) {
        return valueOf(v);
    }

    public String value() {
        return name();
    }

    public MOAccess getMoAccess() {
        return moAccess;
    }
}
