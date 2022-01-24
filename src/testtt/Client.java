package testtt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// Java implementation for a client 
// Save file as Client.java 

import java.io.*; 
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField; 

// Client class 
public class Client 
{ 
	final static int ServerPort = 1234; 
	String name;
	public ArrayList<ClientHandler> contactList;
	public  JFrame f;
	public JPanel p;
	public InetAddress ip; 
     // establish the connection 
    public Socket s;   
     // obtaining input and out streams 
     public DataInputStream dis;
     public DataOutputStream dos;
     
     
     public Client() throws IOException {
    	 this.name= "";
    	 this.contactList=new ArrayList<ClientHandler>();
    	 this.f=new JFrame();
    	 this.p=new JPanel();
    	 this.ip = InetAddress.getByName("localhost");
    	 this.s=new Socket(ip, ServerPort); 
    	 this.dis=new DataInputStream(s.getInputStream()); 
    	 this.dos = new DataOutputStream(s.getOutputStream());  	 
     }
    
     public void LoginOrReg(){
  	     JButton b1=new JButton("Register");//creating instance of JButton  
         b1.setBounds(130,10,100,40);//x axis, y axis, width, height        
         JButton b2=new JButton("Login");//creating instance of JButton  
         b2.setBounds(130,10,100, 40);//x axis, y axis, width, height  
         this.p.add(b1);
         this.p.add(b2);
         this.f.add(p);
         this.f.setSize(400,100);
         this.f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         this.f.setVisible(true);   
     
         Reg(b1);
         Login(b2);
     } 
     
     public void Login(JButton b2) {
  	    class LoginListener implements ActionListener{
  		  	JPanel panel = new JPanel();
  		    JFrame f=new JFrame();
  		    String username;
  		    
  			public void getFrameAndPanel(JFrame f,JPanel panel) {
  				this.f=f;
  				this.panel=panel;
  			}
  			
  		    public void actionPerformed(ActionEvent event)
  		    {
  		    	this.f.remove(this.panel);
  		        this.f.setSize(400,500);//400 width and 500 height  
  		        this.f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  		        JPanel panel2 = new JPanel();
  		        this.f.add(panel2);
  		        panel2.setLayout(null);
  		        
  		        JLabel name= new JLabel("Username");
  		        name.setBounds(10,20,80,25);
  		        panel2.add(name);
  		        
  		        JTextField usernameField= new JTextField(20);
  		        usernameField.setBounds(100, 20, 165, 25);
  		        panel2.add(usernameField);
  		        
  		        JLabel password= new JLabel("Password");
  		        password.setBounds(10,50,80,25);
  		        panel2.add(password);
  		        
  		        JTextField passwordField= new JPasswordField(20);
  		        passwordField.setBounds(100, 50, 165, 25);
  		        panel2.add(passwordField);
  		        
  		        JButton button = new JButton("Login");
  		        button.setBounds(10, 80, 80, 25);
  		        panel2.add(button);
  		        this.f.setVisible(true);
  		        
  		        LoginSuccess(button,usernameField,passwordField,panel2);
  		    }
  		 
  		}
  	    LoginListener listener2 = new LoginListener();
  	    listener2.getFrameAndPanel(this.f,this.p);
  	    b2.addActionListener(listener2);
     }
     
     
    public void LoginSuccess(JButton button,JTextField usernameField,JTextField passwordField,JPanel panel2){
  	   class LoginSuccessListener implements ActionListener{
  			JTextField user;
  			JTextField pass;
  			JPanel panel;
  			String username;
  			String password;
  			public void getUserPass(JTextField u, JTextField p, JPanel p1){
  				this.user=u;
  				this.pass=p;
  				this.panel=p1;
  			}
  			@Override
  			public void actionPerformed(ActionEvent arg0) {
  				this.username=this.user.getText();
  				name=username;
  				this.password=this.pass.getText();
  				 try {
  					    int pos=Database.CheckLoginUsername(username);
  						if(pos==-1) {
  							 JOptionPane.showMessageDialog(null, "404 error: (username not found)", "InfoBox: " + "", JOptionPane.INFORMATION_MESSAGE);
  						}
  						else {
  							if(Database.CheckLoginPassword(password,pos)==false) {
  							   JOptionPane.showMessageDialog(null, "401 error: (password is incorrect)", "InfoBox: " + "", JOptionPane.INFORMATION_MESSAGE);
  							}
  							else {
  								JOptionPane.showMessageDialog(null, "Logged in Successfully!", "InfoBox: " + "", JOptionPane.INFORMATION_MESSAGE);
  								dos.writeUTF(name);
  								f.setVisible(false);
  								afterLoginScreen();
  							}
  							
  						}
  					
  					} catch (IOException e) {
  						// TODO Auto-generated catch block
  						e.printStackTrace();
  					}
  				
  			}
  		}
  	   LoginSuccessListener listener1 = new LoginSuccessListener();
         listener1.getUserPass(usernameField,passwordField,panel2);
         button.addActionListener(listener1);
     }
    
     
     public void Reg(JButton b1) {
  	   class RegListener implements ActionListener{
  		  	JPanel panel = new JPanel();
  		    JFrame f=new JFrame();
  		    String username;
  		   
  			public void getFrameAndPanel(JFrame f,JPanel panel) {
  				this.f=f;
  				this.panel=panel;
  			}
  		    public void actionPerformed(ActionEvent event)
  		    {
  		    	this.f.remove(this.panel);
  		    	
  		    	JPanel panel2 = new JPanel();
  		        this.f.setSize(400,500);//400 width and 500 height  
  		        this.f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  		        this.f.add(panel2);
  		        panel2.setLayout(null);
  		        
  		        JLabel name= new JLabel("Name");
  		        name.setBounds(10,20,80,25);
  		        panel2.add(name);
  		        
  		        JTextField nameField= new JTextField(20);
  		        nameField.setBounds(100, 20, 165, 25);
  		        panel2.add(nameField);
  		       
  		        
  		        JLabel userName= new JLabel("Username");
  		        userName.setBounds(10,50,80,25);
  		        panel2.add(userName);
  		        
  		        JTextField userNameField= new JTextField(20);
  		        userNameField.setBounds(100, 50, 165, 25);
  		        panel2.add(userNameField);
  		        
  		        JLabel password= new JLabel("Password");
  		        password.setBounds(10,80,80,25);
  		        panel2.add(password);
  		        
  		        JTextField passwordField= new JPasswordField(20);
  		        passwordField.setBounds(100, 80, 165, 25);
  		        panel2.add(passwordField);
  		        
  		        JButton button = new JButton("Register");
  		        button.setBounds(10, 110, 110, 25);
  		        panel2.add(button);
  		        this.f.setVisible(true);
  		        
  		        regSuccess(button,userNameField,passwordField,panel2);
  		    }
  		
  		}
  	   RegListener listener1 = new RegListener();
         listener1.getFrameAndPanel(this.f,this.p);
         b1.addActionListener(listener1);
        
     }
     
     
     public void regSuccess(JButton button,JTextField userNameField,JTextField passwordField,JPanel panel) {
  	    class RegSuccessListener implements ActionListener{
  	   	JTextField user;
  	   	JTextField pass;
  	   	JPanel panel;
  	    String username="";
  	   	String password;
  	   	public void getUserPass(JTextField u, JTextField p, JPanel p1){
  	   		this.user=u;
  	   		this.pass=p;
  	   		this.panel=p1;
  	   	}
  	   	
  	   	@Override
  	   	public void actionPerformed(ActionEvent arg0) {
  	   		username=this.user.getText();
  	   		name=username;
  	   		this.password=this.pass.getText();
  	   		 try {
  	   				if(Database.CheckReg(this.username,this.password)==false) {
  	   					 JOptionPane.showMessageDialog(null, "Username Already Taken!", "InfoBox: " + "", JOptionPane.INFORMATION_MESSAGE);
  	   				}
  	   				else if (this.username.isEmpty() || this.password.isEmpty()) {
  	   					JOptionPane.showMessageDialog(null, "Username or Password can not be null!", "InfoBox: " + "", JOptionPane.INFORMATION_MESSAGE);
  	   				}
  	   				else {
  	   					JOptionPane.showMessageDialog(null, "Registered Successfully!", "InfoBox: " + "", JOptionPane.INFORMATION_MESSAGE);
						dos.writeUTF(name);
						f.setVisible(false);
						afterLoginScreen();
  	   				}
  	   			
  	   			} catch (IOException e) {
  	   				// TODO Auto-generated catch block
  	   				e.printStackTrace();
  	   			}
  	     	}
  	   }

  	     RegSuccessListener listener1 = new RegSuccessListener();
         listener1.getUserPass(userNameField,passwordField,panel);
         button.addActionListener(listener1);  
     }
     
     public void afterLoginScreen() {
    	 JFrame f1=new JFrame();
    	 JPanel p1=new JPanel();
    	 JButton b1=new JButton("Show Contacts List");//creating instance of JButton  
         b1.setBounds(130,10,120,40);//x axis, y axis, width, height        
         JButton b2=new JButton("Add Contact from Active List");//creating instance of JButton  
         b2.setBounds(130,10,160,40);//x axis, y axis, width, height         
         JButton b3=new JButton("Start Private Chat");//creating instance of JButton  
         b3.setBounds(130,10,120, 40);//x axis, y axis, width, height  
         JButton b4=new JButton("One to one chat");//creating instance of JButton  
         b4.setBounds(130,10,120, 40);//x axis, y axis, width, height  
         JButton b5=new JButton("Logout");//creating instance of JButton  
         b5.setBounds(130,10,120, 40);//x axis, y axis, width, height 
         p1.add(b1);
         p1.add(b2);
         p1.add(b3);
         p1.add(b4);
         p1.add(b5);
         f1.add(p1);
         f1.setSize(600,200);
         f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         f1.setVisible(true);  
         AddContact(b2);
         ShowContact(b1);
         StartPrivate(b4);
         Logout(b5,f1);

     }
     public void StartPrivate(JButton b3) {
    	 class AddContactListener implements ActionListener
    	 {
    	 	public void actionPerformed(ActionEvent event)
    	 	{
    	 		try {
					dos.writeUTF("One-One chat");
			     
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	 		 
    	 	}
    	 }
    	 ActionListener listener = new AddContactListener();
         b3.addActionListener(listener); 		
	}

	public void AddContact(JButton b2) {
    	 class AddContactListener implements ActionListener
    	 {
    	 	public void actionPerformed(ActionEvent event)
    	 	{
    	 		try {
					dos.writeUTF("Add Contact");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	 	}
    	 }
    	 ActionListener listener = new AddContactListener();
         b2.addActionListener(listener); 
     }
    
     public void ShowContact(JButton b1) {
    	 class ShowContactListener implements ActionListener
    	 {
    	 	public void actionPerformed(ActionEvent event)
    	 	{
    	 		try {
					dos.writeUTF("Show Contact");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	 	}
    	 }
    	 ActionListener listener = new ShowContactListener();
         b1.addActionListener(listener); 
     }
     
     public void Logout(JButton b4,JFrame f1){
    	 class LogoutListener implements ActionListener
    	 {
    	 	public void actionPerformed(ActionEvent event)
    	 	{
    	 		try {
					dos.writeUTF("Logout");
					f1.setVisible(false);
					dos.close();
					dis.close();
                	s.close();
                	System.exit(0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	 	}
    	 }
    	 ActionListener listener = new LogoutListener();
         b4.addActionListener(listener); 
     }
     
  
	
	public static void main(String[] args) throws IOException 
	{ 
		Scanner scn = new Scanner(System.in); 
		Client c=new Client();
		c.LoginOrReg();
        Gui x = new Gui();
        x.setClient(c);
 
        // sendMessage thread 
        Thread sendMessage = new Thread(new Runnable()  
        { 
            @Override
            public void run() { 
                while (true) { 
  
                    // read the message to deliver. 
                    String msg = scn.nextLine(); 
                      
                    try { 
                        // write on the output stream 
                        c.dos.writeUTF(msg); 
                    } catch (IOException e) { 
                        e.printStackTrace(); 
                    } 
                    if(msg.equals("logout")) {
                    	try {
							c.dis.close();
							c.dos.close();
	                    	c.s.close();
	                    	System.exit(0);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    	break;
                    }
                } 
             
            } 
        }); 
          
        // readMessage thread 
        Thread readMessage = new Thread(new Runnable()  
        { 
            @Override
            public void run() { 
  
                while (true) { 
                    try { 
                        // read the message sent to this client 
                        String msg = c.dis.readUTF(); 
                        System.out.println(msg); 
                    } catch (EOFException f) { 
  
                        System.out.println("Logged out");
                    } catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                } 
            } 
        }); 
		
	    sendMessage.start(); 
	    readMessage.start(); 
		
       
       
  
    } 
} 