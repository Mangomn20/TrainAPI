/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package TrainTrack;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
/**
 *
 * @author Mangomn20
 * 
 */
public class TrainAPI {

    public static void main(String[] args) {
                
    }
    
    public static ArrayList getStations(String type)
    {
        ArrayList listStations = new ArrayList<Train>();
        
        String str = "https://api.irishrail.ie/realtime/realtime.asmx/getAllStationsXML_WithStationType?StationType=" + type; // dangerous api input
        String output;  // api output
        try {
            output = APIcaller.api( str);
                listStations.addAll(XMLparser.getStations(output)); // copies the references of the objects
        }
        catch( Exception ex ) { 
            System.getLogger( TrainAPI.class.getName() ).log( System.Logger.Level.ERROR, (String) null, ex );
        }
        
        return listStations;
    }
    
    
    
    public static ArrayList<Train> getReqStationCode(String code, int minutes) // 90 min mark
    {
        ArrayList<Train> listTrain = new ArrayList<Train>();
              String str3 = String.valueOf(minutes);

        String str = "https://api.irishrail.ie/realtime/realtime.asmx/getStationDataByCodeXML_WithNumMins?StationCode="+code+"&NumMins="+str3;
        String output;  // api output
        try {
            output = APIcaller.api( str);
             listTrain.addAll(XMLparser.getTrains(output)); // copies the references of the objects
        }
        catch( Exception ex ) { 
            System.getLogger( TrainAPI.class.getName() ).log( System.Logger.Level.ERROR, (String) null, ex );
        }

        return listTrain;        
    }
    
     public static ArrayList<Train> getReqStationName(String name, int minutes) // 90 min mark
    {
        ArrayList<Train> listTrain = new ArrayList<Train>();
         String str3 = String.valueOf(minutes);

         // /realtime/realtime.asmx/getStationDataByCodeXML?StationCode=string     Coderequest
        String str = "https://api.irishrail.ie/realtime/realtime.asmx/getStationDataByNameXML_withNumMins?StationDesc="+name+"&NumMins="+str3; 
        String output;  // api output
        try {
            output = APIcaller.api( str);
             listTrain.addAll(XMLparser.getTrains(output)); // copies the references of the objects
        }
        catch( Exception ex ) { 
            System.getLogger( TrainAPI.class.getName() ).log( System.Logger.Level.ERROR, (String) null, ex );
        }

        return listTrain;        
    }
    
    
}
