import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

//Scheduler class
public class Scheduler {
	Queue<PCB> ReadyQueue; //Ready Queue for processes ready to use CPU
	int InterruptTimer; //Timer to count time units until current CPU process will be interrupted
	CPU control; //CPU to run instructions
	IODevice io1; //IO Device 1
	IODevice io2; //IO Device 2
	
	String[] States = {"new","ready","waiting","running","terminated"}; //State values for PCB
	
	//Scheduler constructor
	public Scheduler() {
		ReadyQueue = new LinkedList<PCB>();
		InterruptTimer = 0;
		control = new CPU();
		io1 = new IODevice();
		io2 = new IODevice();
	}
	
	//Admit method to add a "new" PCB to the Ready Queue
	void admit(PCB Process) {
		ReadyQueue.add(Process); //Add process
		Process.state = States[0]; //Set state to "new"
		return;
	}
	
	//Interrupt method to send current process on CPU back to ready queue
	void interrupt() {
		control.currentProcess.save(control); //Save CPU registers values to current process PCB
		control.currentProcess.state = States[1]; //Set process state to "ready"
		ReadyQueue.add(control.currentProcess); //Add process back to Ready Queue
		control.currentProcess = null; //Clear process from CPU
		InterruptTimer = 0; //Set Interrupt Timer back to 0
		return;
	}
	
	//Dispatch method to move process from Ready Queue to CPU
	void dispatch() {
		PCB current = ReadyQueue.remove(); //Get 1st PCB from Ready Queue
		current.state = States[3]; //Set state to running
		control.currentProcess = current; //Move process to CPU
		return;
	}
	
	//Terminate method to terminate a process on CPU when finished
	void terminate() {
		control.currentProcess.state = States[4]; //Set state to "terminated"
		control.currentProcess = null; //Clear process from CPU
		InterruptTimer = 0; //Set Interrupt Timer back to 0
		return;
	}
	
	//IO method to move process from CPU into IO Waiting Queue
	void ioEvent() {
		IODevice io = null; //Local IO variable
		int ioNum = control.currentProcess.IORequests[control.currentProcess.currentIORequests][1]; //Check 2D Array for which IO Device to use at current instruction IO Request
		if(ioNum == 1) {
			io = io1; //Set IO to IO 1
		} else if(ioNum == 2){
			io = io2; //Set IO to IO 2
	    }
		io.WaitingQueue.add(control.currentProcess); //Add process to IO Waiting Queue
		control.currentProcess.state = States[2]; //Set state to waiting
		control.currentProcess.currentIORequests++; //Increment current IO index to next value
		control.currentProcess = null; //Clear process from CPU
		InterruptTimer = 0; //Set Interrupt Timer back to 0
		return;
	}
	
	//isEmpty method that checks if Ready Queue and CPU are empty
	boolean isEmpty() {
		if(ReadyQueue.isEmpty() && control.currentProcess == null) {
			return true;
		} else {
			return false;
		}
	}
	
	//toString method that prints all Processes in Ready Queue, CPU and IO Devices
	public String toString() {
		return "Ready Queue " + ReadyQueue + "\n CPU: " + control + "\nIO 1: " + io1 + "\nIO 2: " + io2;
	}
	
	//Run method for scheduler
	void runScheduler() throws IOException {
		FileWriter wr = new FileWriter("output.txt"); //File writer to write to "output.txt"
		int cpuAction; //Variable to store result of run CPU
		int counter = 0; //Variable to count total number of time units
		while(true) {
			if(this.isEmpty() && io1.isEmpty() && io2.isEmpty()) { //Run until Scheduler, CPU and IOs are all empty, then break out of loop
				break;
			}
			wr.write("*******TIME STEP " + counter + "***********\n");
			System.out.println("*******TIME STEP " + counter + "***********");
			if(InterruptTimer == 2) { //Check if 2 time units have passed for current CPU process
				wr.write("-------INTERRUPTING-----\n");
				System.out.println("-------INTERRUPTING-----");
				interrupt(); //Interrupt CPU process
			}
			if(!(ReadyQueue.isEmpty()) && control.currentProcess == null) { //Check if CPU is empty and process is in Ready Queue to be moved
				wr.write("-------DISPATCHING-----\n");
				System.out.println("-------DISPATCHING-----");
				dispatch(); //Dispatch 1st process in queue to CPU
			}
			cpuAction = control.runCPU(); //Run CPU
			if(cpuAction == 1) { //If return 1 then process is finished, then terminate
				wr.write("-------TERMINATING-----\n");
				System.out.println("-------TERMINATING-----");
				terminate();
			} else if(cpuAction == 2) { //If return 2 then process needs to be moved to IO
				wr.write("-------MOVING TO IO-----\n");
				System.out.println("-------MOVING TO IO-----");
				ioEvent();
			} else if(cpuAction == 0) { //If return zero then there is either no process or current CPU process moves to next instruction
				if(control.currentProcess != null) { //If process is on CPU increment interrupt timer
					InterruptTimer++;
				}
			}
			io1.runIO(this); //Run IO 1, if empty it does nothing
			io2.runIO(this); //Run IO 2, if empty it does nothing
			wr.write(this.toString() + "\n"); //Write status of Scheduler to output.txt file
			System.out.println(this);
			counter++; //Increment to next time unit
			wr.flush(); //Flush buffered output to file
		}
		wr.close(); //When finished close File Writer
	}
	
}
