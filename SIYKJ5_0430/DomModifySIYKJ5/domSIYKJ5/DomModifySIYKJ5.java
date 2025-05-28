package domSIYKJ5;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class DomModifySIYKJ5 {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("XMLSIYKJ5.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // a.) Módosítsa egy vendég nevét és életkorát (pl. vkod="v1")
            NodeList vendegList = doc.getElementsByTagName("vendeg");
            for (int i = 0; i < vendegList.getLength(); i++) {
                Element vendeg = (Element) vendegList.item(i);
                if (vendeg.getAttribute("vkod").equals("v1")) {
                    vendeg.getElementsByTagName("nev").item(0).setTextContent("Fekete Péter Jr.");
                    Element eletkorElem = (Element) vendeg.getElementsByTagName("eletkor").item(0);
                    int newAge = Integer.parseInt(eletkorElem.getTextContent()) + 2;
                    eletkorElem.setTextContent(String.valueOf(newAge));
                }
            }

            // b.) Írja át egy gyakornok attribútumát pl. e-gy= e2 -> e3
            NodeList gyakornokList = doc.getElementsByTagName("gyakornok");
            for (int i = 0; i < gyakornokList.getLength(); i++) {
                Element gy = (Element) gyakornokList.item(i);
                if (gy.getAttribute("gykod").equals("gy2")) {
                    gy.setAttribute("e_gy", "e3");
                }
            }

            // c.) Törölje ki a rendelés összes példányát
            NodeList rendelesList = doc.getElementsByTagName("rendeles");
            for (int i = rendelesList.getLength() - 1; i >= 0; i--) {
                Node rendeles = rendelesList.item(i);
                rendeles.getParentNode().removeChild(rendeles);
            }

            // Kiírás konzolra
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            // Konzolra
            transformer.transform(new DOMSource(doc), new StreamResult(System.out));

            // Fájlba
            StreamResult fileResult = new StreamResult(new File("XMLSIYKJ5_modified.xml"));
            transformer.transform(new DOMSource(doc), fileResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
