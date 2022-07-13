package Project;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

	public class ThreadWrite extends Thread {
		private PrintWriter write;
		private String writeTo;
		private String writeFrom;
		private Scanner keyboard = new Scanner(System.in);
		private List<String> arrayList;
		
		public ThreadWrite(PrintWriter write, String writeFrom, String writeTo, List<String> list) {
			this.write = write;
			this.writeTo = writeTo;
			this.writeFrom = writeFrom;
			this.arrayList = list;
		}
		@Override
		public void run() {
			try {
				int counterA = 0;
				int counterB = 0;
				if(writeFrom.equals("Master") && writeTo.substring(0,6).equals("Client")) {					
					while(true) {
					if(!arrayList.isEmpty()){	
						
						if(Integer.parseInt(arrayList.get(0).substring(1,2))%2 != 0) {
							if(writeTo.equals("ClientA")) {
								write.println(arrayList.get(0));
								System.out.println("Master sending back job "+ arrayList.get(0) + " to " + writeTo);
								arrayList.remove(0);
							}	
							else {
								Thread.yield();
							}
						}
						else if(Integer.parseInt(arrayList.get(0).substring(1,2))%2 == 0){
							if(writeTo.equals("ClientB")) {
								write.println(arrayList.get(0));
								System.out.println("Master sending back job "+ arrayList.get(0) + " to " + writeTo);
								arrayList.remove(0);
							}
							else {
								Thread.yield();
							}
						}
					}
				}
				}
				
				else if(writeFrom.equals("Master") && writeTo.substring(0,5).equals("Slave")) {
					String job = "a1";
					while(true) {
						if(arrayList.size() > 0) {
							job = arrayList.get(0);
							write.println(job);
							System.out.println("Master sent job " + job + " to " + writeTo);
							arrayList.remove(0);
							
						}
						
					}					
				}
				
				else if(writeFrom.substring(0,6).equals("Client") && writeTo.equals("Master")){					
					String jobType = "1";
					int count = 0;
					if(writeFrom.equals("ClientA")) {
						count = 1;
					}
					while(!jobType.equals("-1")) {
						System.out.print("Choose your job type (a/b):");
						jobType = keyboard.nextLine();	
						if(jobType.equals("-1")) {
							write.println("-1");
						}
						else {
							String jobIdType;
							if(jobType.equals("a")) {
								jobIdType = jobType + count;
								count += 2;
							} 
							else {
								jobIdType = jobType + count; 
								count += 2;
							}
							
							arrayList.add(jobIdType);
								
							write.println(jobIdType);
							System.out.println(jobIdType + " sent to master.");
						}
					}
				}
				else if(writeFrom.substring(0,5).equals("Slave") && writeTo.equals("Master")) {
					String job;
					while(true) {				
							if(!arrayList.isEmpty()) {								
								job = arrayList.get(0);
								write.println(job);
								arrayList.remove(0);
							}
					}	
				}
			}
			catch(Exception e) {
				
			}
		}

}
	