package Project;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ThreadRead extends Thread {
	private BufferedReader reader;
	private String identityFrom;
	private String identityTo;
	private String job;
	private List<String> arrayList;
	private counterA counterA;
	private counterB counterB;

	public ThreadRead(BufferedReader reader, String identityFrom, String identityTo, List<String> list) {
		 this.reader = reader;
		 this.identityFrom = identityFrom;
		 this.identityTo = identityTo;
		 this.arrayList = list;
	 }
	public ThreadRead(BufferedReader reader, String identityFrom, String identityTo, List<String> list, counterA counterA, counterB counterB) {
		 this.reader = reader;
		 this.identityFrom = identityFrom;
		 this.identityTo = identityTo;
		 this.arrayList = list;
		 this.counterA = counterA;
		 this.counterB = counterB;
	 }

	@Override
	 public void run() {
		try{	
			if(identityTo.equals("Master") && identityFrom.substring(0,6).equals("Client")) {
				while(true) {
					job = reader.readLine();
					while(!job.equals("-1")){
						arrayList.add(job);						
						System.out.println(identityTo +  " received job "  + job + " from " + identityFrom);
						job = reader.readLine();
						
					}				
				}
			}
			else if(identityTo.substring(0,6).equals("Client")) {				
				while(true) {
					job = reader.readLine();
					if(job != null) {
						System.out.println("\nJob " + job + " completed");
					}
				}	
			}
			
			else if(identityTo.equals("SlaveA")){
				while(true) {
					job  = reader.readLine();
					if(job != null) {
						System.out.println(identityTo + " Received job " +  job + " from Master");
						if(job.charAt(0)=='a') {
								System.out.println("Sleeping for 2 seconds");
								Thread.sleep(2000);
								arrayList.add("A02" + job);
							}
							else {
								System.out.println("Sleeping for 10 seconds");
								Thread.sleep(10000);
								arrayList.add("A10"+job );
							}
							
						}				
									
					System.out.println("Sending completed job " + job + " back to Master");					
				}
				
			}
			else if(identityTo.equals("SlaveB")) {
				while(true) {
					job  = reader.readLine();
					if(job != null) {
						System.out.println(identityTo + " Received job " +  job + " from Master");
						if(job.charAt(0)=='b') {
							System.out.println("Sleeping for 2 seconds");
							Thread.sleep(2000);
							arrayList.add("B02" + job);
						}
						else {
							System.out.println("Sleeping for 10 seconds");
							Thread.sleep(10000);
							arrayList.add("B10" + job);
						}
						
					}
					System.out.println("Sending completed job " + job + " back to Master");
				}
					
			}
			else {
				int amount;
				while(true) {
					job = reader.readLine();
					
					if(job != null) {
						amount =  Integer.parseInt(job.substring(1,3));
						if(job.charAt(0)=='A') {
							counterA.decrement(amount);
						}
						else {
							counterB.decrement(amount);
						}
						job = job.substring(3);
						System.out.println("Master received completed job " + job + " from " + identityFrom);
						arrayList.add(job);						
					}
				}
			}
		 	 
		 }
		 catch(IOException e){
				System.out.println(e.getMessage());
				System.exit(1);
			} 
		 catch (InterruptedException e) {
			e.printStackTrace();
		}
	 }
}
