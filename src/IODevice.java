import java.util.LinkedList;
import java.util.Queue;

//IO Device Class
public class IODevice {
	int IOTimer; //Timer to count to 5 when process is active on IO
	PCB currentIO; //Current processes on the IO Device
	Queue<PCB> WaitingQueue; //Waiting Queue to hold processes sent to IO while IO is full
	
	//IO Device constructor
	public IODevice() {
		WaitingQueue = new LinkedList<PCB>();
		IOTimer = 0;
		currentIO = null;
	}
	
	//Function to check if Waiting Queue and Current process are empty
	boolean isEmpty() {
		if(WaitingQueue.isEmpty() && currentIO == null) {
			return true;
		} else {
			return false;
		}
	}
	
	//toString method to output current process and waiting queue content
	public String toString() {
		return "Process On IO " + currentIO + "\nWaiting Queue: " + WaitingQueue;
	}
	
	//Method to run IO device, takes the current Scheduler accessing device
	void runIO(Scheduler sched) {
		if(IOTimer != 0) { //Check if there is a current device, will be when timer is non-0
			if(IOTimer == 5) { //Check if 5 time units have passed and IO accessing is done
				sched.ReadyQueue.add(currentIO); //If finished add process back to ready queue
				currentIO.state = sched.States[1]; //Set process state to "ready"
				currentIO.CurrentInstruction++; //Go to next instruction
				currentIO = null; //Clear IO processes
				IOTimer = 0; //Set timer to 0
			} else {
				IOTimer++; //If not empty and not finished increment the timer
				return;
			}
		}
		if(WaitingQueue.isEmpty()) { //If current process is empty and Waiting Queue is empty, do nothing and return
			return;
		} else {
			currentIO = WaitingQueue.remove(); //If Waiting Queue is not empty move 1st process onto IO Device
			IOTimer++; //Increment timer
			return;
		}
	};
}
