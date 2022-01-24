package testtt;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Gui {

	public JFrame frame;
	public String username;
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
     Client c;
     
     public Gui() throws IOException {
    	 this.name= "";
    	 this.contactList=new ArrayList<ClientHandler>();
    	 this.f=new JFrame();
    	 this.p=new JPanel();
    	 this.ip = InetAddress.getByName("localhost");
    	 this.s=new Socket(ip, ServerPort); 
    	 this.dis=new DataInputStream(s.getInputStream()); 
    	 this.dos = new DataOutputStream(s.getOutputStream());  
     }
     
    
     
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	/*public Gui() {
		initialize();
	}
*/
	//public Gui(String username) {
	//	this.username=username;
	//}

	

	/**
	 * Initialize the contents of the frame.
	 * @param username
	 */
	public void initialize(String username) {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		/*
		JButton btnNewButton = new JButton("Send");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnNewButton.setBackground(Color.PINK);
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(335, 11, 89, 32);
		frame.getContentPane().add(btnNewButton);
		*/
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Courier New", Font.PLAIN, 16));
		textArea.setBackground(new Color(255, 127, 80));
		textArea.setForeground(Color.WHITE);
		textArea.setBounds(10, 54, 414, 169);
		frame.getContentPane().add(textArea);

		JButton btnNewButton = new JButton("Send");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnNewButton.setBackground(Color.PINK);
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					dos.writeUTF(textArea.getText()+"#"+username);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(335, 11, 89, 32);
		frame.getContentPane().add(btnNewButton);
		JLabel lblNewLabel = new JLabel(username);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBackground(new Color(135, 206, 235));
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setBounds(10, 14, 310, 29);
		frame.getContentPane().add(lblNewLabel);
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public void setUsername(String username) {
		this.username=username;
		initialize(username);
	}



	public void setClient(Client c) {
		this.c=c;
	}
}
