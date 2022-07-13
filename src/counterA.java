package Project;

public class counterA {
	int counter;
	
	public counterA() {
		counter = 0;
	}
	
	public void increment(int value) {
		counter += value;
	}
	
	public void decrement(int value) {
		counter -= value;
	}
	
	public int getCounter() {
		return counter;
	}
}
