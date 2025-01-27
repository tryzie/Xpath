package validation;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;

public class Main {

    public static void main(String[] args) {

        String filePath = "src/validation/pac008_sample.xml";

        try {

            //load and parse the xml
            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            //normalize the xml structure
            document.getDocumentElement().normalize();

            //create an xpath object
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();

            String expression = "//*";

            // evaluate xpath expression
            NodeList node = (NodeList) xPath.evaluate(expression, document, XPathConstants.NODESET);

            //process the result
            for (int i = 0; i < node.getLength(); i++) {
               Node node1 = node.item(i);

               if (node1.getNodeType() == Node.ELEMENT_NODE){
                   Element element = (Element) node1;

                   String nodePath = getXPath(element);
                   String nodeValue = element.getTextContent().trim();

                   // Only print if the node has a direct text value
                   if (!nodeValue.isEmpty() && element.getChildNodes().getLength() == 1) {
                       System.out.println("XPath: " + nodePath);
                       System.out.println("Value: " + nodeValue);
                       System.out.println("-------------------");
                   }

                   // Process attributes if any
                   NamedNodeMap attributes = element.getAttributes();
                   if (attributes != null && attributes.getLength() > 0) {
                       for (int j = 0; j < attributes.getLength(); j++) {
                           Node attr = attributes.item(j);
                           System.out.println("Attribute " + nodePath + "/@" + attr.getNodeName() +
                                   " = " + attr.getNodeValue());
                       }
               }
        } } }catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }
    private static String getXPath(Node node) {
        Node parent = node.getParentNode();
        if (parent == null) {
            return "/" + node.getNodeName();
        }
        return getXPath(parent) + "/" + node.getNodeName();
    }
}
