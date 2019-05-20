package at.kaismi.snmpmockagent;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "mos")
@XmlAccessorType(XmlAccessType.FIELD)
public class Mos {

    @XmlElement(name = "mo")
    private List<Mo> mos;

    public List<Mo> getMos() {
        return mos;
    }

    public void setMos(List<Mo> mos) {
        this.mos = mos;
    }
}
