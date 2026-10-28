/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TrainTrack;

/**
 *
 * @author admyn
 */
public class Train {
    
    private final String traincode;
    private final String origin;
    private final String destination;
    private final String status;
    private final String duein;
    private final String exparrival;
    private final String expdepart;


    Train( String traincode, String origin, String destination, String status, String duein, String exparrival, String expdepart ) {
        this.traincode = traincode;
        this.origin = origin;
        this.destination = destination;
        this.status = status;
        this.duein = duein;
        this.exparrival = exparrival;
        this.expdepart = expdepart;
    }
    
    public void printvalues()
    {
        System.out.println("Train Code: " + traincode
        + ", Origin: " + origin
        + ", Destination: " + destination
        + ", Status: " + status
        + ", Due In: " + duein
        + ", Expected Arrival: " + exparrival
        + ", Expected Departure: " + expdepart);
    }
    
    public String gettraincode()
    {
        return this.traincode;
    }
    
    public String getorigin()
    {
        return this.origin;
    }
    
    public String getdestination()
    {
        return this.destination;
    }
    
    public String getstatus()
    {
        return this.status;
    }
    
    public String getduein()
    {
        return this.duein;
    }
    
    public String getexparrival()
    {
        return this.exparrival;
    }
        
    public String getexpdepart()
    {
        return this.expdepart;
    }
        
}
