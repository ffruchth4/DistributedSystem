package Project;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ThreadDecide extends Thread {
	private List<String> slaveAList;
	private List<String> slaveBList;
	private List<String> masterListToSlave;
	private counterA counterA;
	private counterB counterB;
	
	public ThreadDecide(List<String> masterListToSlave, List<String> slaveAList, List<String> slaveBList, counterA counterA, counterB counterB) {
		this.masterListToSlave = masterListToSlave;
		this.slaveAList = slaveAList;
		this.slaveBList =  slaveBList;
		this.counterA = counterA;
		this.counterB = counterB;
	}
	@Override	
	public void run() {
		String job;
		while(true) {	
					
			if(!masterListToSlave.isEmpty()) {			
				
				
				if(counterA.getCounter()-counterB.getCounter() < 10 && masterListToSlave.get(0).charAt(0) == 'a') {
					synchronized(masterListToSlave) {
						job = masterListToSlave.get(0);
						slaveAList.add(job);
						masterListToSlave.remove(0);
					}
					counterA.increment(2);
					
				}
				else if(counterB.getCounter()-counterA.getCounter() < 10 && masterListToSlave.get(0).charAt(0) == 'b') {
					synchronized(masterListToSlave) {
						job = masterListToSlave.get(0);
						slaveBList.add(job);
						masterListToSlave.remove(0);
					}
					counterB.increment(2);
				}
				else if(counterA.getCounter()-counterB.getCounter() >= 10 && masterListToSlave.get(0).charAt(0) == 'a') {
					synchronized(masterListToSlave) {
						job = masterListToSlave.get(0);
						slaveBList.add(job);
						masterListToSlave.remove(0);
					}
					counterB.increment(10);
				}
				else if(counterB.getCounter()-counterA.getCounter() >= 10 && masterListToSlave.get(0).charAt(0) == 'b') {
					synchronized(masterListToSlave) {
						job = masterListToSlave.get(0);
						slaveAList.add(job);
						masterListToSlave.remove(0);
					}
					counterA.increment(10);
				}
				
			}
		}		
	}
}
