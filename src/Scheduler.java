import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {
	Queue<PCB> ReadyQueue = new LinkedList<PCB>();
	int InterruptTimer;
	CPU control;
	IODevice io1;
	IODevice io2;
	
	String[] States = {"new","ready","waiting","running","terminated"};
	
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
	
	void runScheduler() {
		int cpuAction;
		while(true) {
			if(InterruptTimer == 2) {
				interrupt();
			}
			cpuAction = control.runCPU();
			if(cpuAction == 1) {
				terminate();
			} else if(cpuAction == 2) {
				ioEvent();
			} else if(cpuAction == 0) {
				InterruptTimer++;
			}
		}
	}
	
}
