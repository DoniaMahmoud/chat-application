package testtt;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

// ClientHandler class 
class ClientHandler extends Thread 
{ 
    Scanner scn = new Scanner(System.in); 
    String name; 
    public ArrayList<ClientHandler> contactList;
    final DataInputStream dis; 
    final DataOutputStream dos; 
    Socket s; 
    boolean isloggedin; 
   // JFrame f;
    //JPanel p;
	


 // constructor 
    public ClientHandler(Socket s,DataInputStream dis, DataOutputStream dos) { 
        this.dis = dis; 
        this.dos = dos;  
        this.s = s; 
        this.isloggedin=true;
        this.contactList=new ArrayList<ClientHandler>();
    } 

    
    public String getname() {
    	return name;
    }

    public void setname(String name) {
    	this.name=name;
    }
    
    public void ShowAct() {
        JFrame f = new JFrame("Active List");
        JPanel p=new JPanel();
        ArrayList<String>usernames=new ArrayList<String>();
        for(int i=0; i<Server.active.size(); i++) {
        	ClientHandler c=Server.active.get(i);
        	usernames.add(c.name);
        }
        usernames.remove(this.name);
        JList b;
        b=new JList(usernames.toArray());
        p.add(b);
        f.add(p);
        f.pack();
        f.setSize(300,500); 
        f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        
        selectContact(b);
       // checkActiveAnd
    }
    

    
    public void selectContact(JList b) {
    	class listListener implements ListSelectionListener{
    		public JList b1;
    		public void getList(JList b) {
    			this.b1=b;
    		}
			@Override
			public void valueChanged(ListSelectionEvent evt) {
				if (!evt.getValueIsAdjusting()) {//This line prevents double events
					String username=(String)b1.getSelectedValue();	
					String copy="";
					for(int i=0; i<contactList.size(); i++) {
						ClientHandler c=contactList.get(i);
						if(c.name.equals(username)) {
							copy=c.name;
							break;
						}
						else {continue;}
					}
					if(copy.equals("")) {
						addcont(username);
					}
					else {
						JOptionPane.showMessageDialog(null, "This user is already in your Contact List!", "InfoBox: " + "", JOptionPane.INFORMATION_MESSAGE);
					}
					
			    }
			}    		
    	}
    	listListener s=new  listListener();
    	s.getList(b);
    	b.addListSelectionListener(s);
    }
    
    public void addcont(String user) {
    	ClientHandler Client;
    	for(int i=0; i<Server.active.size(); i++) {
    		ClientHandler c=Server.active.get(i);
    		if(c.name.equals(user)) {
    			Client=Server.active.get(i);
    			break;
    		}
    		else {
    			continue;
    		}
    	}
    	JFrame fr=new JFrame("Add Request");
    	JPanel pl= new JPanel();
    	JLabel l=new JLabel();
    	l.setText("Hi  "+user+" !, "+this.name+"  would like to add you to their contact list.");
    	l.setBounds(100, 200, 300, 300);
    	JButton b1=new JButton("Accept");//creating instance of JButton  
        b1.setBounds(130,10,100,40);//x axis, y axis, width, height        
        JButton b2=new JButton("Decline");//creating instance of JButton  
        b2.setBounds(130,10,100, 40);//x axis, y axis, width, height  
        pl.add(l);
        pl.add(b1);
        pl.add(b2);
        fr.add(pl);
        fr.setSize(700,250);
        fr.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        fr.setLocationRelativeTo(null);
        fr.setVisible(true);
        
        acceptReq(user,b1,this,fr);
        DeclineReq(user,b2,this,fr);
    }
    
    public void acceptReq(String user, JButton b1,ClientHandler c, JFrame f) {
    	 class acceptListener implements ActionListener
    	 {
    	 	public void actionPerformed(ActionEvent event)
    	 	{
    	 		ClientHandler Client;
    	    	for(int i=0; i<Server.active.size(); i++) {
    	    		ClientHandler cl=Server.active.get(i);
    	    		if(cl.name.equals(user)) {
    	    			Client=Server.active.get(i);
    	    			Client.contactList.add(c);
    	    			contactList.add(Client);
    	    			JOptionPane.showMessageDialog(null, Client.name+"  has accepted your request, "+c.name , "InfoBox: " + "", JOptionPane.INFORMATION_MESSAGE);
    	    			JOptionPane.showMessageDialog(null, c.name+"  is added to your contact list, "+Client.name , "InfoBox: " + "", JOptionPane.INFORMATION_MESSAGE);
    	    			f.setVisible(false);
    	    			break;
    	    		}
    	    		else {
    	    			continue;
    	    		}
    	    	}
    	 	}
    	 }
    	 ActionListener listener = new acceptListener();
         b1.addActionListener(listener); 
    }
    
    public void DeclineReq(String user, JButton b1,ClientHandler c, JFrame f) {
   	 class acceptListener implements ActionListener
   	 {
   	 	public void actionPerformed(ActionEvent event)
   	 	{
   	 	    f.setVisible(false);
			JOptionPane.showMessageDialog(null, user+"  has unfortunately declined your request, "+c.name , "InfoBox: " + "", JOptionPane.INFORMATION_MESSAGE);
   	 	}
   	 }
   	 ActionListener listener = new acceptListener();
        b1.addActionListener(listener); 
   }
    
    
    
    public void ShowContacts() {
        JFrame f = new JFrame("Contacts List");
        JPanel p=new JPanel();
        JList b;
        ArrayList<String>usernames=new ArrayList<String>();
        for(int i=0; i<contactList.size(); i++) {
        	ClientHandler c=contactList.get(i);
        	usernames.add(c.name);
        }
        b=new JList(usernames.toArray());
        p.add(b);
        f.add(p);
        f.pack();
        f.setSize(300,500); 
        f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        
        //selectContact(b);
    }
    
    
    public void ShowContactsForChatting() {
        JFrame f = new JFrame("Contacts List");
        JPanel p=new JPanel();
        JList b;
        ArrayList<String>usernames=new ArrayList<String>();
        for(int i=0; i<contactList.size(); i++) {
        	ClientHandler c=contactList.get(i);
        	usernames.add(c.name);
        }
        b=new JList(usernames.toArray());
        p.add(b);
        f.add(p);
        f.pack();
        f.setSize(300,500); 
        f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        
        selectContactForChatting(b);
    }
    public void selectContactForChatting(JList b) {
    	class listListener implements ListSelectionListener{
    		public JList b1;
    		public void getList(JList b) {
    			this.b1=b;
    		}
			@Override
			public void valueChanged(ListSelectionEvent evt) {
				if (!evt.getValueIsAdjusting()) {//This line prevents double events
					String username=(String)b1.getSelectedValue();	
				//	String copy="";
					for(int i=0; i<contactList.size(); i++) {
						ClientHandler c=contactList.get(i);
						if(c.name.equals(username) && c.name.equals(Server.active.get(i).name)) {
							Gui x;
							try {
								x = new Gui();
								x.setUsername(username);
								x.frame.setVisible(true);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else {
							continue;
							}
					}
					
					
			    }
			}    		
    	}
    	listListener s=new  listListener();
    	s.getList(b);
    	b.addListSelectionListener(s);
    }
   
	
	@Override
	public void run()
	{
		
		String receivedName;
		try {
			receivedName = dis.readUTF(); 
			this.setname(receivedName);
			Server.active.add(this);
			System.out.println("Adding this client to active client list");
			for(int i=0; i<Server.active.size(); i++) {
		   	    ClientHandler client=Server.active.get(i);
		   	    System.out.println("Client: "+client.getname());
		   	  }
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		String received;
        while (true)  
        { 
            try
            { 
                // receive the string 
                received = dis.readUTF(); 
              
                if(received.equals("Add Contact")) {
                	ShowAct();
                }
                else if(received.equals("One-One chat")){
                	ShowContactsForChatting();
                	
                }
                else if(received.equals("Show Contact")) {
                	ShowContacts();
                }
                else if(received.equals("Logout")){ 
                    this.isloggedin=false; 
                    break; 
                } 
                else  {
                	
                	System.out.println(received); 
                    // break the string into message and recipient part 
                    StringTokenizer st = new StringTokenizer(received, "#"); 
                    String MsgToSend = st.nextToken(); 
                    String recipient = st.nextToken(); 
      
                    // search for the recipient in the connected devices list. 
                    // active is the arraylist storing client of active users 
                    for (ClientHandler mc : Server.active)  
                    { 
                        // if the recipient is found, write on its 
                        // output stream 
                        if (mc.name.equals(recipient) && mc.isloggedin==true)  
                        { 
                            mc.dos.writeUTF(this.name+" : "+MsgToSend); 
                            break; 
                        } 
                    } 
                }
                
            } catch (IOException e) { 
                  
                e.printStackTrace(); 
            } 
              
        } 
       
            // closing resources 
   
            for (ClientHandler mc : Server.active)  
            { 
                // if the recipient is found, write on its 
                // output stream 
                if (mc.name.equals(this.name))  
                { 
                	Server.active.remove(mc);
                	break;
                } 
            }
            
            try
            { 
            this.dis.close(); 
            this.dos.close(); 
            this.s.close();
              
        }catch(IOException e){ 
            e.printStackTrace(); 
        } 
}

}