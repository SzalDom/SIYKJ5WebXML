package domSIYKJ5;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class DOMModifySIYKJ5 {
    public static void main(String[] args) {
        try {
            File inputFile = new File("XMLSIYKJ5.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // Új vendég létrehozása
            Element newGuest = doc.createElement("vendeg");
            newGuest.setAttribute("vkod", "v4");

            Element nev = doc.createElement("nev");
            nev.setTextContent("Új Vendég");
            newGuest.appendChild(nev);

            Element eletkor = doc.createElement("eletkor");
            eletkor.setTextContent("29");
            newGuest.appendChild(eletkor);

            Element cim = doc.createElement("cim");
            Element varos = doc.createElement("varos");
            varos.setTextContent("Győr");
            Element utca = doc.createElement("utca");
            utca.setTextContent("Kossuth");
            Element hazszam = doc.createElement("hazszam");
            hazszam.setTextContent("9");
            cim.appendChild(varos);
            cim.appendChild(utca);
            cim.appendChild(hazszam);

            newGuest.appendChild(cim);

            // Hozzáadjuk az új vendéget a dokumentumhoz
            Node root = doc.getDocumentElement();
            root.appendChild(newGuest);

            // Kiírás konzolra
            System.out.println("Módosított XML tartalom:");
            printNode(root, 0);

            // Mentés fájlba
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("XMLSIYKJ5_modified.xml"));
            transformer.transform(source, result);

            System.out.println("\nA fájl mentésre került: XMLSIYKJ5_modified.xml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printNode(Node node, int indent) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            printIndent(indent);
            System.out.print("<" + node.getNodeName());

            NamedNodeMap attributes = node.getAttributes();
            for (int i = 0; i < attributes.getLength(); i++) {
                Node attr = attributes.item(i);
                System.out.print(" " + attr.getNodeName() + "=\"" + attr.getNodeValue() + "\"");
            }
            System.out.println(">");

            NodeList children = node.getChildNodes();
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
