package com.technohest.constants;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by Oscar on 2015-05-20.
 */
public class Settings {

    private String width;
    private String height;

    //Filereader variables needed to read from .xml files
    private File file;
    private DocumentBuilderFactory dbFactory;
    private DocumentBuilder dBuilder;
    private Document doc;
    private NodeList list;
    private NodeList list1;
    private NodeList list2;
    private org.w3c.dom.Element element1;
    private Node node;
    private org.w3c.dom.Element element2;

    /**
     * Creates the file reader and reads from width and height element in config.xml
     */
    public Settings(){
        //File initialization
        try {
            file = new File("assets/config.xml");
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            list = doc.getElementsByTagName("options");
            element2 = (Element) list.item(0);
            node = list.item(0);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        element1 = (Element) node;
        list1 = element2.getElementsByTagName("width");
        element1 = (Element) list1.item(0);
        list2 = element1.getChildNodes();
        width = list2.item(0).getNodeValue();

        list1 = element2.getElementsByTagName("height");
        element1 = (Element) list1.item(0);
        list2 = element1.getChildNodes();
        height = list2.item(0).getNodeValue();
    }

    /**
     * @return
     * Value of width in config.xml
     */
    public int getWidth(){
        return Integer.parseInt(width);
    }
    /**
     * @return
     * Value of height in config.xml
     */
    public int getHeight(){
        return Integer.parseInt(height);
    }
}
