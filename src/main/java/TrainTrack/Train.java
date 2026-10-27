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
    
    private String traincode;
    private String origin;
    private String destination;
    private String status;
    private String duein;
    private String exparrival;
    private String expdepart;


    Train( String traincode, String origin, String destination, String status, String duein, String exparrival, String expdepart ) {
        this.traincode = traincode;
        this.origin = origin;
        this.destination = destination;
        this.status = status;
        this.duein = duein;
        this.exparrival = exparrival;
        this.expdepart = expdepart;
    }
        
}
