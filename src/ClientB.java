package Project;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ClientB {
	public static void main (String [] args) throws Exception{
		
		
		//using a try block because we want everything to close
		//after were done using it
		try (
		//specify the ip address and the port number
		Socket client= new Socket("127.0.0.1", 7865);
		
		//create object to send data to server
		PrintWriter writeToMaster= new PrintWriter(client.getOutputStream(), true);
		
		//create object to read from server
		BufferedReader readFromMaster= new BufferedReader(new InputStreamReader(client.getInputStream()));
		){
			List<String> clientBList = Collections.synchronizedList (new ArrayList<String>());
			System.out.println("ClientB connected.");
			ThreadWrite writeMaster = new ThreadWrite(writeToMaster, "ClientB", "Master", clientBList);
			ThreadRead readMaster = new ThreadRead(readFromMaster, "Master", "ClientB", clientBList);
			writeMaster.start();
			readMaster.start();
			
			writeMaster.join();
			readMaster.join();
		}
	}
}
