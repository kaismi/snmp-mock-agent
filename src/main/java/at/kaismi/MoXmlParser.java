package at.kaismi;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

class MoXmlParser {

    private File file;

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
