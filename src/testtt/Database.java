package testtt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Database {

	static String Usernames="C:\\Users\\PC\\eclipse-workspace-2020\\testtt\\Usernames.txt";
	static String Passwords="C:\\Users\\PC\\eclipse-workspace-2020\\testtt\\Passwords.txt";
	
	public static ArrayList<String> readFiles(String Filename) throws IOException {
		File file=new File(Filename);
		Scanner in=new Scanner(file);
		String read;
		ArrayList<String>written= new ArrayList<String>();
		while(in.hasNextLine()) {
			read=in.nextLine();
			written.add(read);
		}
		return written;
	}
	
	public static void WriteFile(String user ,String pass) throws IOException {
		File f1= new File(Usernames);
		File f2= new File(Passwords);
		FileWriter filewriter1=new FileWriter(f1,true);
		FileWriter filewriter2=new FileWriter(f2,true);
		BufferedWriter bw1=new BufferedWriter(filewriter1);
		BufferedWriter bw2=new BufferedWriter(filewriter2);
		bw1.write(user);
		bw1.newLine();
		
		bw2.write(pass);
		bw2.newLine();
	
		bw1.close();
		bw2.close();
		
	}
	
	public static boolean CheckReg(String user, String pass) throws IOException {
		ArrayList<String>written= new ArrayList<String>();
		written=readFiles(Usernames);
		for(int i=0; i<written.size(); i++) {
			if(written.get(i).equals(user)) {
				return false;
			}
			else {
				continue;
			}
		}
		
		WriteFile(user,pass);
		return true;
		
	}
	
	public static int CheckLoginUsername(String user) throws IOException {
		ArrayList<String>written= new ArrayList<String>();
		written=readFiles(Usernames);
		int pos=0;
		for(int i=0; i<written.size(); i++) {
			if(written.get(i).equals(user)) {
				pos=i;
				return pos;
			}
			else {
				continue;
			}
		}
		return -1;
	}
	
	
	public static boolean CheckLoginPassword(String pass,int pos) throws IOException {
		ArrayList<String>written= new ArrayList<String>();
		written=readFiles(Passwords);
		if(written.get(pos).equals(pass)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
	
	
	
	
}
