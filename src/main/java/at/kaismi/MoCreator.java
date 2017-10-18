package at.kaismi;

import org.snmp4j.agent.mo.MOScalar;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.Variable;

public class MoCreator {

    public MOScalar create(OID oid, Object value, MoAccess moAccess) {
        return new MOScalar(oid, moAccess.getMoAccess(), getVariable(value));
    }

    private Variable getVariable(Object value) {
        if (value instanceof String) {
            return new OctetString((String)value);
        }
        throw new IllegalArgumentException("Unmanaged Type: " + value.getClass());
    }
}
