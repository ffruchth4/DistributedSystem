package Project;
import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.*;

public class Master {
	

	public static void main (String[] args) throws Exception{
		
	try	(
			//create a server socket and client socket
			ServerSocket server = new ServerSocket(7865);
			Socket clientA = server.accept();
			Socket clientB = server.accept();
			Socket slaveA = server.accept();
			Socket slaveB = server.accept();
	
			//create a bufferedreader to read from the client and server
			BufferedReader bufferCA= new BufferedReader(new InputStreamReader(clientA.getInputStream()));
			BufferedReader bufferCB= new BufferedReader(new InputStreamReader(clientB.getInputStream()));
			BufferedReader bufferSA = new BufferedReader(new InputStreamReader(slaveA.getInputStream()));
			BufferedReader bufferSB = new BufferedReader(new InputStreamReader(slaveB.getInputStream()));
	
			//send data to client with printwriter
			PrintWriter printCA= new PrintWriter(clientA.getOutputStream(), true);
			PrintWriter printCB= new PrintWriter(clientB.getOutputStream(), true);
			PrintWriter printSA = new PrintWriter(slaveA.getOutputStream(), true);
			PrintWriter printSB = new PrintWriter(slaveB.getOutputStream(), true);
			
			
		){
		
			
			System.out.println("Master connected.");
			
			List<String> masterListToSlave = Collections.synchronizedList(new ArrayList<String>());
			List<String> masterListToClient = Collections.synchronizedList(new ArrayList<String>());
			List<String> slaveAList = Collections.synchronizedList(new ArrayList<String>());
			List<String> slaveBList = Collections.synchronizedList(new ArrayList<String>());
			
			counterA counterA = new counterA();
			counterB counterB = new counterB();
	
			
			ThreadWrite writeToCA = new ThreadWrite(printCA, "Master", "ClientA", masterListToClient);
			ThreadWrite writeToCB = new ThreadWrite(printCB, "Master", "ClientB", masterListToClient);
			ThreadWrite writeToSA = new ThreadWrite(printSA, "Master", "SlaveA", slaveAList);
			ThreadWrite writeToSB = new ThreadWrite(printSB, "Master", "SlaveB", slaveBList);
			
			ThreadRead readCA = new ThreadRead(bufferCA, "ClientA", "Master", masterListToSlave);
			ThreadRead readCB = new ThreadRead(bufferCB, "ClientB", "Master", masterListToSlave);
			ThreadRead readSA = new ThreadRead(bufferSA, "SlaveA", "Master", masterListToClient, counterA, counterB);
			ThreadRead readSB = new ThreadRead(bufferSB, "SlaveB", "Master", masterListToClient, counterA, counterB);
			
			ThreadDecide decide = new ThreadDecide(masterListToSlave, slaveAList, slaveBList, counterA, counterB);
			
			writeToCA.start();
			writeToCB.start();
			writeToSA.start();
			writeToSB.start();
			
			readCA.start();
			readCB.start();
			readSA.start();
			readSB.start();
			
			decide.start();
			
			
			writeToCA.join();
			writeToCB.join();
			writeToSA.join();
			writeToSB.join();
			
			readCA.join();
			readCB.join();
			readSA.join();
			readSB.join();
			
			decide.join();		
		}
	
	catch(IOException e){
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
}
