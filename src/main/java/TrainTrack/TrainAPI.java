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
        
        getStations();
        
        getReqStation();
        
        
    }
    
    public static void getStations()
    {
        ArrayList<Train> listStations = new ArrayList<Train>();
        
        String str = "https://api.irishrail.ie/realtime/realtime.asmx/getAllStationsXML?"; // dangerous api input
        String output;  // api output
        try {
            output = APIcaller.api( str);
            if(output != null) // APIcaller.(if not 200 return null)
            {
                listStations.addAll(XMLparser.getStations(output)); // copies the references of the objects
            }
            else{
                System.out.println("error with getStations");
                return;
            }
        }
        catch( Exception ex ) { 
            System.getLogger( TrainAPI.class.getName() ).log( System.Logger.Level.ERROR, (String) null, ex );
        }
    }
    
    
    
    public static void getReqStation() // 90 min mark
    {
        ArrayList<Train> listTrain = new ArrayList<Train>();
        System.out.println("Enter Station: \n");
        Scanner input = new Scanner(System.in);
        String station = input.nextLine();
        System.out.println("/TrainApi started");
        input.close();
        
        String str = "https://api.irishrail.ie/realtime/realtime.asmx/getStationDataByNameXML?StationDesc="+station; // dangerous api input
        String output;  // api output
        try {
            output = APIcaller.api( str);
            if(output != null) // APIcaller.(if not 200 return null)
            {
                listTrain.addAll(XMLparser.getTrains(output)); // copies the references of the objects
            }
            else{
                System.out.println("error with getReqStation");
                return;
            }
        }
        catch( Exception ex ) { 
            System.getLogger( TrainAPI.class.getName() ).log( System.Logger.Level.ERROR, (String) null, ex );
        }
        
        
        
        
        
        
        
    }
    
    
}
