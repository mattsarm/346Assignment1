import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {
	Queue<PCB> ReadyQueue;
	int InterruptTimer;
	CPU control;
	IODevice io1;
	IODevice io2;
	
	String[] States = {"new","ready","waiting","running","terminated"};
	
	
	public Scheduler() {
		ReadyQueue = new LinkedList<PCB>();
		InterruptTimer = 0;
		control = new CPU();
		io1 = new IODevice();
		io2 = new IODevice();
	}
	
	
	void admit(PCB Process) {
		ReadyQueue.add(Process);
		Process.state = States[0];
		return;
	}
	void interrupt() {
		control.currentProcess.save(control);
		control.currentProcess.state = States[1];
		ReadyQueue.add(control.currentProcess);
		control.currentProcess = null;
		InterruptTimer = 0;
		return;
	}
	void dispatch() {
		PCB current = ReadyQueue.remove();
		current.state = States[3];
		control.currentProcess = current;
		return;
	}
	void terminate() {
		control.currentProcess.state = States[4];
		control.currentProcess = null;
		InterruptTimer = 0;
		return;
	}
	void ioEvent() {
		IODevice io = null;
		int ioNum = control.currentProcess.IORequests[control.currentProcess.currentIORequests][1];
		if(ioNum == 1) {
			io = io1;
		} else if(ioNum == 2){
			io = io2;
	    }
		io.WaitingQueue.add(control.currentProcess);
		control.currentProcess.state = States[2];
		control.currentProcess.currentIORequests++;
		control.currentProcess = null;
		InterruptTimer = 0;
		return;
	}
	
	boolean isEmpty() {
		if(ReadyQueue.isEmpty() && control.currentProcess == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public String toString() {
		return "Ready Queue " + ReadyQueue + "\n CPU: " + control + "\nIO 1: " + io1 + "\nIO 2: " + io2;
	}
	
	void runScheduler() throws IOException {
		FileWriter wr = new FileWriter("output.txt");
		int cpuAction;
		int counter = 0;
		while(true) {
			if(this.isEmpty() && io1.isEmpty() && io2.isEmpty()) {
				break;
			}
			if(InterruptTimer == 2) {
				wr.write("-------INTERRUPTING-----\n");
				System.out.println("-------INTERRUPTING-----");
				interrupt();
			}
			if(!(ReadyQueue.isEmpty()) && control.currentProcess == null) {
				wr.write("-------DISPATCHING-----\n");
				System.out.println("-------DISPATCHING-----");
				dispatch();
			}
			cpuAction = control.runCPU();
			if(cpuAction == 1) {
				wr.write("-------TERMINATING-----\n");
				System.out.println("-------TERMINATING-----");
				terminate();
			} else if(cpuAction == 2) {
				wr.write("-------MOVING TO IO-----\n");
				System.out.println("-------MOVING TO IO-----");
				ioEvent();
			} else if(cpuAction == 0) {
				if(control.currentProcess != null) {
					InterruptTimer++;
				}
			}
			io1.runIO(this);
			io2.runIO(this);
			wr.write("*******TIME STEP " + counter + "***********\n");
			System.out.println("*******TIME STEP " + counter + "***********");
			wr.write(this.toString() + "\n");
			System.out.println(this);
			counter++;
			wr.flush();
		}
		wr.close();
	}
	
}
