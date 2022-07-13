package Project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class SlaveA {
public static void main (String [] args) throws Exception{
		
		
		//using a try block because we want everything to close
		//after were done using it
		try (
		//specify the ip address and the port number
		Socket slave= new Socket("127.0.0.1", 7865);
		
		//create object to send data to server
		PrintWriter writeToMaster= new PrintWriter(slave.getOutputStream(), true);
		
		//create object to read from server
		BufferedReader readFromMaster= new BufferedReader(new InputStreamReader(slave.getInputStream()));
		){
			
			List<String> slaveAToMaster = Collections.synchronizedList (new ArrayList<String>());
			//slaveAToMaster.add("slaveAToMaster");
			System.out.println("SlaveA connected.");
			ThreadWrite writeMaster = new ThreadWrite(writeToMaster, "SlaveA", "Master", slaveAToMaster);
			ThreadRead readMaster = new ThreadRead(readFromMaster, "Master", "SlaveA", slaveAToMaster );
			writeMaster.start();
			readMaster.start();
			
			writeMaster.join();
			readMaster.join();
		}
	catch(UnknownHostException e){
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		
	catch(IOException e){
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
}
