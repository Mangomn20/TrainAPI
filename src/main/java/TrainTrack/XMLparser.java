/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TrainTrack;

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

import org.xml.sax.InputSource;
import java.io.StringReader;
import java.util.ArrayList;

/**
 *
 * @author admyn
 */
public class XMLparser {
    
    
    public static ArrayList getStations(String xml)
    {
        System.out.println("/XMLparser (getStations method) started");
        System.out.println(xml);
        ArrayList<Train> listTrain = new ArrayList<Train>();
        //Document Builder factory isntance to create a docBuilder

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(new InputSource(new StringReader(xml))); // input

            // Normalize the xml structure
             document.getDocumentElement().normalize();

            // Get all the element by the tag name
            NodeList objStationData = document.getElementsByTagName("objStationData");
            
            System.out.println("==================");
            System.out.println("");
            System.out.println("Length of objStationData: " + objStationData.getLength());
            System.out.println("");
            System.out.println("==================");
            
            for(int i = 0; i <objStationData.getLength(); i++) {
                Node train = objStationData.item(i);

                Element trai = (Element)train; // ArrayOfObjStationData -> objStationData -> Traincode
                String traincode = trai.getElementsByTagName("Traincode").item(0).getTextContent();
                String origin = trai.getElementsByTagName("Origin").item(0).getTextContent();
                String destination = trai.getElementsByTagName("Destination").item(0).getTextContent();
                String status = trai.getElementsByTagName("Status").item(0).getTextContent();
                String duein = trai.getElementsByTagName("Origintime").item(0).getTextContent();
                String exparrival = trai.getElementsByTagName("Exparrival").item(0).getTextContent();
                String expdepart = trai.getElementsByTagName("Expdepart").item(0).getTextContent();
                System.out.println("/XMLparser (getStations method) => Elements added");
                listTrain.add(new Train(traincode, origin, destination, status, duein, exparrival, expdepart));
                
                
                // System.out.println("Traincode: " + trai.getAttribute("Traincode"));

                             
            }
            System.out.println("/XMLparser (getStations method) after for loop");

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return listTrain;
        
    }
    
}
