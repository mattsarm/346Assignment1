import java.util.Random;

public class CPU {
Random rand = new Random();
int Reg1;
int Reg2;
PCB currentProcess;

public CPU() {
	currentProcess = null;
	Reg1 = rand.nextInt(1000);
	Reg2 = rand.nextInt(1000);
}


int runCPU() {
	Reg1 = rand.nextInt(1000);
	Reg2 = rand.nextInt(1000);
	currentProcess.save(this);
	if(currentProcess.CurrentInstruction > currentProcess.PCounter) {
		return 1;
	}
	if(currentProcess.currentIORequests < currentProcess.IORequests.length) {
		if(currentProcess.CurrentInstruction == currentProcess.IORequests[currentProcess.currentIORequests][0]) {
			currentProcess.CurrentInstruction++;
			return 2;
		}
	}
	currentProcess.CurrentInstruction++;
	return 0;
};
}
