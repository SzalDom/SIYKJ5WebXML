package domSIYKJ5;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class DOMReadSIYKJ5 {
    public static void main(String[] args) {
        try {
            File inputFile = new File("XMLSIYKJ5.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            doc.getDocumentElement().normalize();
            printNode(doc.getDocumentElement(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printNode(Node node, int indent) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            printIndent(indent);
            System.out.print("<" + node.getNodeName());

            NamedNodeMap attributes = node.getAttributes();
            if (attributes != null) {
                for (int i = 0; i < attributes.getLength(); i++) {
                    Node attr = attributes.item(i);
                    System.out.print(" " + attr.getNodeName() + "=\"" + attr.getNodeValue() + "\"");
                }
            }
            System.out.println(">");

            NodeList children = node.getChildNodes();
            boolean hasElementChild = false;
            for (int i = 0; i < children.getLength(); i++) {
                if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    hasElementChild = true;
                    break;
                }
            }

            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                if (child.getNodeType() == Node.ELEMENT_NODE) {
                    printNode(child, indent + 2);
                } else if (child.getNodeType() == Node.TEXT_NODE) {
                    String text = child.getTextContent().trim();
                    if (!text.isEmpty()) {
                        printIndent(indent + 2);
                        System.out.println(text);
                    }
                }
            }

            printIndent(indent);
            System.out.println("</" + node.getNodeName() + ">");
        }
    }

    private static void printIndent(int indent) {
        for (int i = 0; i < indent; i++) System.out.print(" ");
    }
}
