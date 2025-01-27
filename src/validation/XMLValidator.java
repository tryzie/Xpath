package validation;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;

public class XMLValidator {
    public static void main(String[] args) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File("src/validation/pac008_sample.xsd"));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File("src/validation/pac008_sample.xml")));
            System.out.println("XML is valid.");
        } catch (Exception e) {
            System.out.println("XML is not valid. Error: " + e.getMessage());
        }
    }
}