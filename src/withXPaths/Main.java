package withXPaths;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    String filePath = "src/signatories_model_info.xml";

    public static void main(String[] args) {

        Main run = new Main();

        run.getTagName();
        run.count();
        run.duplicate();
        run.remove();
        run.update();

    }


    //get element field type by their tag name
    public void getTagName(){
        try {

            //load and parse the xml
            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            //normalize the xml structure
            document.getDocumentElement().normalize();

            //create a xpath object
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();

            String expression = "//item[@field_type=\"API_BASED\"]";

           // evaluate xpath expression
            NodeList node = (NodeList) xPath.evaluate(expression, document, XPathConstants.NODESET);

            //process the result
            for (int i = 0; i < node.getLength(); i++) {
                Node nodeAttribute = node.item(i);

               // Get the tag_name attribute
                NamedNodeMap attributes = nodeAttribute.getAttributes();
                Node tagNameAttr = attributes.getNamedItem("tag_name");

                if (tagNameAttr != null) {
                    System.out.println("Tag Name: " + tagNameAttr.getNodeValue());

                }
            }
        } catch (Exception e){
            System.out.println("Error " + e.getMessage());
        }

    }


    //count elements with field type table based
    public void count (){
        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            //normalize the xml structre
            document.getDocumentElement().normalize();

            //create xpath object
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();

            XPathExpression expression = xPath.compile("count(//item[@field_type=\"TABLE_BASED\"])");
            Number result = (Number) expression.evaluate(document, XPathConstants.NUMBER);
            System.out.println("Total number of Table based field type is " + result);
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    //duplicate
    public void duplicate(){

        try {
            // Load and parse the XML
            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            document.getDocumentElement().normalize();

            // Create XPath object
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();

            // XPath to find elements with duplicate checking attributes
            String expression = "//item[@duplicate_check='true']";
            NodeList duplicateCheckNodes = (NodeList) xPath.evaluate(expression, document, XPathConstants.NODESET);

            // Map to store elements and their associated fields
            Map<String, List<String>> duplicateCheckInfo = new HashMap<>();

            // Process each node
            for (int i = 0; i < duplicateCheckNodes.getLength(); i++) {
                Node node = duplicateCheckNodes.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // Get the tag name and duplicate check fields
                    String tagName = element.getAttribute("tag_name");
                    String duplicateFields = element.getAttribute("duplicate_check_fields");

                    // Store the information
                    if (!duplicateFields.isEmpty()) {
                        List<String> fields = Arrays.asList(duplicateFields.split(","));
                        duplicateCheckInfo.put(tagName, fields);
                    }
                }
            }

            // Print the results
            System.out.println("Elements requiring duplicate checking and their fields:");
            System.out.println("------------------------------------------------");

            for (Map.Entry<String, List<String>> entry : duplicateCheckInfo.entrySet()) {
                System.out.println("\nElement: " + entry.getKey());
                System.out.println("Fields to check for duplicates:");
                for (String field : entry.getValue()) {
                    System.out.println("- " + field.trim());
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

    }


    //remove element
    public void remove (){
        try {

            //load and parse the xml
            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            //normalize the xml structure
            document.getDocumentElement().normalize();

            //create a xpath object
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();

            //String expression = "//item[@tag_name=\"(RESTRICTED_ACCESS_NATIONALITIES_MATCH_TYPE|MAX_RESTRICTED_ACCESS_NATIONALITIES|RESTRICTED_ACCESS_NATIONALITIES)\"]";

            String expression = "//item[@tag_name='RESTRICTED_ACCESS_NATIONALITIES_MATCH_TYPE' or " +
                    "@tag_name='MAX_RESTRICTED_ACCESS_NATIONALITIES' or " +
                    "@tag_name='RESTRICTED_ACCESS_NATIONALITIES']";
            // evaluate xpath expression
            NodeList node = (NodeList) xPath.evaluate(expression, document, XPathConstants.NODESET);

            //process the result
            for (int i = 0; i < node.getLength(); i++) {

                Node nodes = node.item(i);
                Node parent = nodes.getParentNode();
                if (parent != null) {
                    parent.removeChild(nodes);
                    System.out.println("Removed element with tag_name: " +
                            nodes.getAttributes().getNamedItem("tag_name").getNodeValue());
                }
            }
        } catch (Exception e){
            System.out.println("Error " + e.getMessage());
        }
    }


    //update
    public void update (){
        try {

            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            //normalize the xml structure
            document.getDocumentElement().normalize();

            //create xpath object
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            String expression = "//*[@use='MANDATORY']";

            // evaluate xpath expression
            NodeList node = (NodeList) xPath.evaluate(expression, document, XPathConstants.NODESET);

            //process the result
            for (int i = 0; i < node.getLength(); i++) {
                Node nodes = node.item(i);
                if (nodes.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodes;
                    element.setAttribute("use", "OPTIONAL");
                    System.out.println("Updated use attribute to OPTIONAL for element: " +
                            element.getAttribute("tag_name"));
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // Set output properties for better formatting
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(document);
            String outputPath = "src/signatories_model_info_modified.xml";
            StreamResult result = new StreamResult(new File(outputPath));

            transformer.transform(source, result);
            System.out.println("Modified XML saved to: " + outputPath);

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }
}
