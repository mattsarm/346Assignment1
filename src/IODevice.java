import java.util.LinkedList;
import java.util.Queue;

public class IODevice {
	int IOTimer;
	PCB currentIO;
	Queue<PCB> WaitingQueue;
	
	
	public IODevice() {
		WaitingQueue = new LinkedList<PCB>();
		IOTimer = 0;
		currentIO = null;
	}
	
	boolean isEmpty() {
		if(WaitingQueue.isEmpty() && currentIO == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public String toString() {
		return "Process On IO " + currentIO + "\nWaiting Queue: " + WaitingQueue;
	}
	
	void runIO(Scheduler sched) {
		if(IOTimer != 0) {
			if(IOTimer == 5) {
				sched.ReadyQueue.add(currentIO);
				currentIO.state = sched.States[1];
				currentIO.CurrentInstruction++;
				currentIO = null;
				IOTimer = 0;
			} else {
				IOTimer++;
				return;
			}
		}
		if(WaitingQueue.isEmpty()) {
			return;
		} else {
			currentIO = WaitingQueue.remove();
			IOTimer++;
			return;
		}
	};
}
