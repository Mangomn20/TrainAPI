/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TrainTrack;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


/**
 *
 * @author admyn
 */
public class APIcaller {
    
    public static void api(String str)
        throws Exception {
        
            HttpClient client = HttpClient.newHttpClient(); // abstract class, but .newHttpClient gives instance

            // String str = "";
            // create new instance of a request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create( str ))
                    .GET()
                    .build();
                                    // .uri expects a URI object, so we add URI.create( str )
            HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());


            System.out.println("Status: " + response.statusCode());
            System.out.println(response.body());
            
        };        
    
}
