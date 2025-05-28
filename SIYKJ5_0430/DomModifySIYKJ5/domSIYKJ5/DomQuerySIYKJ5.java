package domSIYKJ5;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class DomQuerySIYKJ5 {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("XMLSIYKJ5.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            FileWriter fw = new FileWriter("queries_output.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            // a.) Szakácsok, akiknek van Szakközépiskola végzettségük
            bw.write("a) Szakácsok Szakközépiskolával:\n");
            System.out.println("a) Szakácsok Szakközépiskolával:");
            NodeList szakacsok = doc.getElementsByTagName("szakacs");
            for (int i = 0; i < szakacsok.getLength(); i++) {
                Element szakacs = (Element) szakacsok.item(i);
                NodeList vegzettsegek = szakacs.getElementsByTagName("vegzettseg");
                for (int j = 0; j < vegzettsegek.getLength(); j++) {
                    if (vegzettsegek.item(j).getTextContent().equalsIgnoreCase("Szakközépiskola")) {
                        String nev = szakacs.getElementsByTagName("nev").item(0).getTextContent();
                        System.out.println(nev);
                        bw.write(nev + "\n");
                        break;
                    }
                }
            }

            // b.) Öt csillagos éttermek
            bw.write("\nb) Öt csillagos éttermek:\n");
            System.out.println("\nb) Öt csillagos éttermek:");
            NodeList etteremList = doc.getElementsByTagName("etterem");
            for (int i = 0; i < etteremList.getLength(); i++) {
                Element etterem = (Element) etteremList.item(i);
                int csillag = Integer.parseInt(etterem.getElementsByTagName("csillag").item(0).getTextContent());
                if (csillag == 5) {
                    String nev = etterem.getElementsByTagName("nev").item(0).getTextContent();
                    System.out.println(nev);
                    bw.write(nev + "\n");
                }
            }

            // c.) Gyakornokok délutáni műszakkal
            bw.write("\nc) Gyakornokok Délután műszakkal:\n");
            System.out.println("\nc) Gyakornokok Délután műszakkal:");
            NodeList gyakornokList = doc.getElementsByTagName("gyakornok");
            for (int i = 0; i < gyakornokList.getLength(); i++) {
                Element gy = (Element) gyakornokList.item(i);
                NodeList muszakList = gy.getElementsByTagName("muszak");
                for (int j = 0; j < muszakList.getLength(); j++) {
                    if (muszakList.item(j).getTextContent().equalsIgnoreCase("Délután")) {
                        String nev = gy.getElementsByTagName("nev").item(0).getTextContent();
                        System.out.println(nev);
                        bw.write(nev + "\n");
                        break;
                    }
                }
            }

            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
