import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

public class XMLValidator {
    public static void main(String[] args) {
        try {
            File xml = new File("data/library.xml");
            File xsd = new File("data/library.xsd");

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            factory.newSchema(xsd).newValidator().validate(new StreamSource(xml));

            System.out.println("XML документ валиден.");
        } catch (Exception e) {
            System.out.println("Ошибка валидации: " + e.getMessage());
        }
    }
}

