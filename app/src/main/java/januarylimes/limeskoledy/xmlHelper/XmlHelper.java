package januarylimes.limeskoledy.xmlHelper;

import android.content.res.AssetManager;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import januarylimes.limeskoledy.model.Koleda;

/**
 * Created by kacper on 29.09.2016.
 */
public class XmlHelper {

    AssetManager assetManager;
    Document doc = null;
    ArrayList<Koleda> ListaKoled=new ArrayList<>();
    public static final String KEY_KOLEDA = "koleda";
    public static final String KEY_NAZWA = "nazwa";
    public static final String KEY_TEKST = "tekst";

    public XmlHelper(AssetManager assetManager) {
        this.assetManager = assetManager;
        doc = getDomElement(getXmlFromAssets("teksty.xml"));
        generateList();
    }
    public ArrayList<Koleda> getListeKoled(){
        return this.ListaKoled;
    }

    private void generateList() {
        NodeList nl = getDocument().getElementsByTagName(XmlHelper.KEY_KOLEDA);

        // looping through all item nodes <item>
        for (int i = 0; i < nl.getLength(); i++) {
            String name = getValue((Element) nl.item(i), XmlHelper.KEY_NAZWA);
            String tekst = getValue((Element) nl.item(i), XmlHelper.KEY_TEKST);

            //trim po to aby usunac dwie lub wiecej spacji przy sobie
            Koleda koleda = new Koleda(name, tekst.trim().replaceAll("  +", ""));
            ListaKoled.add(koleda);
        }
    }

    public Document getDocument() {
        return this.doc;
    }

    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }

    private String getXmlFromAssets(String assetName) {
        String tekstyXml = null;
        // To load text file
        InputStream input;
        try {
            input = assetManager.open(assetName);

            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            // byte buffer into a string
            tekstyXml = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tekstyXml;
    }

    private Document getDomElement(String xml) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);

        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }
        // return DOM
        return doc;
    }

    private String getElementValue(Node elem) {
        Node child;
        if (elem != null) {
            if (elem.hasChildNodes()) {
                for (child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {
                    if (child.getNodeType() == Node.TEXT_NODE) {
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }
}
