package at.kaismi.snmpmockagent;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

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
