/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package TrainTrack;
import java.util.Scanner;
/**
 *
 * @author Mangomn20
 * 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
 */
public class TrainAPI {

    public static void main(String[] args) {
        System.out.println("Enter Station: \n");
        Scanner input = new Scanner(System.in);
        String station = input.nextLine();
        System.out.println("/TrainApi started");
        String str = "https://api.irishrail.ie/realtime/realtime.asmx/getStationDataByNameXML?StationDesc="+station; // dangerous
        try {
            APIcaller.api( str);
        }
        catch( Exception ex ) {
            System.getLogger( TrainAPI.class.getName() ).log( System.Logger.Level.ERROR, (String) null, ex );
        }
        
    }
}
