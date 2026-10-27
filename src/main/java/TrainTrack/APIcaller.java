/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TrainTrack;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

/**
 *
 * @author admyn
 */
public class APIcaller {
    
    public static void api(String str)
        throws Exception {
            System.out.println("/APIcaller (api method) started");
            HttpClient client = HttpClient.newHttpClient(); // abstract class, but .newHttpClient gives instance

            // String str = "";
            // create new instance of a request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create( str ))
                    .GET()
                    .build();
                                    // .uri expects a URI object, so we add URI.create( str )
            HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

            ArrayList temp = XMLparser.getStations(response.body());
            
            /*
            return XMLparser.station(response);
            return XMLparser.allStations(response);
            return XMLparser.allStations(response);
            */
            System.out.println("Status: " + response.statusCode());
            // System.out.println(response.body());
            
        };
    
}
