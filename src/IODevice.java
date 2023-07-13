import java.util.LinkedList;
import java.util.Queue;

public class IODevice {
	int IOTimer;
	PCB currentIO;
	Queue<PCB> WaitingQueue = new LinkedList<PCB>();
	
	void runIO(Scheduler sched) {
		if(IOTimer != 0) {
			if(IOTimer == 5) {
				sched.ReadyQueue.add(currentIO);
				currentIO.state = sched.States[1];
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
