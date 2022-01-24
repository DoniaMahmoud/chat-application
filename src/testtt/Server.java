package testtt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

// Java implementation of Server side 
// It contains two classes : Server and ClientHandler 
// Save file as Server.java 

import java.io.*; 
import java.text.*; 
import java.util.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.net.*; 

// Server class 
public class Server 
{ 
	 // arraylist to store active clients 
    static ArrayList<ClientHandler> active = new ArrayList<ClientHandler>();
      
    // counter for clients 
    static JPanel panel = new JPanel();
    static JFrame f=new JFrame();//creating instance of JFrame  
	
	
	public static void main(String[] args) throws IOException 
	{ 
		// server is listening on port 1234 
        ServerSocket ss = new ServerSocket(1234);  
        Socket s; 
		
		// running infinite loop for getting 
		// client request 
		while (true) 
		{ 
			 // Accept the incoming request 
            s = ss.accept(); 
            System.out.println("New client request received : " + s); 
            // obtain input and output streams 
            DataInputStream dis = new DataInputStream(s.getInputStream()); 
            DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
            System.out.println("Creating a new handler for this client..."); 
            // Create a new handler object for handling this request. 
            ClientHandler mtch = new ClientHandler(s,dis, dos); 
            
            // Create a new Thread with this object. 
            Thread t = new Thread(mtch);     
                
           // System.out.println("Adding this client to active client list"); 
            // add this client to active clients list 
           //active.add(mtch);
            /*
           for(int i=0; i<Server.active.size(); i++) {
	   	    	ClientHandler client=Server.active.get(i);
	   	    	System.out.println(client.getname());
	   	    }
  */
            // start the thread. 
       
            t.start(); 
         
          
  
            // increment i for new client. 
            // i is used for naming only, and can be replaced 
            // by any naming scheme 
		} 
	} 
}