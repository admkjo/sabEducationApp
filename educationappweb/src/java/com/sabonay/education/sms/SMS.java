package com.sabonay.education.sms;

import ie.omk.smpp.BadCommandIDException;
import ie.omk.smpp.Connection;
import ie.omk.smpp.event.ConnectionObserver;
import ie.omk.smpp.event.ReceiverExitEvent;
import ie.omk.smpp.event.SMPPEvent;
import ie.omk.smpp.message.SMPPPacket;
import ie.omk.smpp.message.SubmitSM;
import ie.omk.smpp.message.SubmitSMResp;
import java.io.IOException;
import java.net.UnknownHostException;
/*
 * SMS.java
 *
 * Created on September 09, 2008, 11:48 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
import java.sql.SQLException;
import java.util.EmptyStackException;
import javax.xml.ws.RespectBinding;
import sjm.parse.Assembly;
import sjm.parse.Sequence;
import sjm.parse.tokens.TokenAssembly;


/**
 *
 * @author koPHy
 */
//ConnectionObserver is an interface handling the Recieving functionality of
//the Asynchronous Connection/communication
public class SMS extends Thread implements ConnectionObserver 
{
    
    private Connection conn;
    private boolean exit = false;
    
    public static int passengersNum; // so it can be initialised by the 
     //isRoutedaycodeServiceValid method in Route.
    
    private static SMS instance;

    public boolean started = false;
    
    /** Creates a new instance of SMS */
    private SMS() {
    }
    
    //creates an intance of the SMS class
    public static SMS getInstance() {
        if (instance == null)
            instance = new SMS();
        return instance;
    }
    
    //this method is for establishing connection to the SMSC
    private void connect() 
    {
        try 
        {
            //instantiating the Connection class 
            //First parameter is appropriate Host required for connection to the SMSC
            //2nd parameter sets the port for communication
            //3rd parameter sets the connection type ie Synchronous or Asynchronous
            //synchronous is assigned if parameter is ignored
            conn = new Connection("localhost", 2775, true);
            
            //attaches the ConnectionObserver interface to the established Connection object.
            //Hence adds the Recieving Functionalities of the interface to the connection
            conn.addObserver(this);
            
        } 
        catch (UnknownHostException msg) 
        {
            System.exit(0);
        }
        
        boolean tryAgain = false;
        
        while (!tryAgain) 
        {
            try 
            {
                //connection is established by binding application to the SMSC as a TRX
                //2nd parameter sets the systemID. 3rd parameter sets connection password
                //4th parameter sets the System type ie voicemail,mms etc
                //system type for SMSCs are always null.
                conn.bind(Connection.TRANSCEIVER, "smppclient", "password", null);
                tryAgain = true;// initialised to stop while loop if no exceptions are thrown
            } 
            catch ( IOException ioeMsg ) 
            {
                //System.out.println( msg );
                try 
                {
                    //if for some reason, connection fails, application sleeps for a minute
                    //and tries to a reconnection after the sleep
                    sleep( 60 * 1000 );
                } 
                catch ( InterruptedException ieMsg ) 
                {
                }
            }
        }
        
    }
    
    //the main thread of our application
    //Basically connects app to SMSC and waits for connection termination or app exit
    //if connection termination, reconnection is tried ie the sleep in the connect() method
    @Override
    public void run() 
    {
        //while application is not exiting or while exit is equal to false
        while (!exit) 
        {
            connect();
            synchronized(this) 
            {
                try 
                {
                    wait();
                } 
                catch ( InterruptedException ieMsg ) 
                {
                }
            }
        }//end of while
    }
    
    //the first method of ConnectionObserver interface
    //used in checking the events of the recieving thread
    //Listens for Recieving events caused caused by Exceptions
    //Exceptions are handled by refferencing the main thread for the Reconnection procedure
    //of the connect() method.
    public void update(Connection conn, SMPPEvent event) 
    {
        if (event.getType() == SMPPEvent.RECEIVER_EXIT && ((ReceiverExitEvent)event).equals(SMPPEvent.RECEIVER_EXCEPTION) ) //NB it use to isException
        {
            synchronized(this) 
            {
                System.out.println("..............Sync invoked.............");
                notify();
            }
        }
    }
    
    //2nd method of the ConnectionObserver interface
    //Handles the PDU's that are sent from users and SMSC to app
    //Parameter 1 is the Connection object.
    //Parameter 2 is the SMPPPacket ( ie the instance of PDUs sent )
    public void packetReceived(Connection conn, SMPPPacket pack) 
    {
        switch( pack.getCommandId() ) 
        {
            //checks if PDU is from user.
            case SMPPPacket.DELIVER_SM :
                try 
                {
                    SubmitSM response = processRequest(pack);//the reply to user messages stored in response
                    SubmitSMResp smResponse  = (SubmitSMResp)conn.sendRequest(response);//the replied msg status.
                } 
                catch (Exception msg) 
                {
                    msg.printStackTrace();
                }
                break;
            
            //checks if PDU is the status of ( response to ) TRX request from app.
            case SMPPPacket.BIND_TRANSCEIVER_RESP :
                if (pack.getCommandStatus() != 0) 
                {
                    System.out.println("Error binding: " + pack.getCommandStatus());
                    exit = true;
                    synchronized(this) 
                    {
                         notify();
                    }
                } 
                else 
                {
                    System.out.println("Bounded");
                }
          }
    }
    

   private SubmitSM processRequest(SMPPPacket request) throws BadCommandIDException, SQLException
    {
        SubmitSM sm = (SubmitSM)conn.newInstance(SMPPPacket.SUBMIT_SM);
        sm.setDestination(request.getSource());

        String msg = request.getMessageText();

//        System.out.println("Message Just in :::: " + msg);
//        EducationParser educationParser = new EducationParser();
//        String response = educationParser.process(msg);

//        System.out.println("++++++++++++++++ " + response);
        
        
        return sm;
    }
    
    private void logPacket(SMPPPacket packet, String direction) {
        String phone;
        if (direction.equals("OUT"))
            phone = packet.getDestination().getAddress();
        else
            phone = packet.getSource().getAddress();
        System.out.println("\n\n" + direction + ": " + phone +  " - " + packet.getMessageText() + "\n\n");
    }
    
    public Connection getConnection() {
        return conn;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {



       

        Runtime.getRuntime().addShutdownHook(new Hook());
        SMS demo = SMS.getInstance();
        demo.start();
    }
    
}
