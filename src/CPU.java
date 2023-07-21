import java.util.Random;

//CPU class
public class CPU {
Random rand = new Random(); //Random number generator
int Reg1; //Register 1 data
int Reg2; //Register 2 data
PCB currentProcess; //Current process loaded on PCB, can be null for no process loaded

//CPU constructor
public CPU() {
	currentProcess = null; 
	Reg1 = rand.nextInt(1000);
	Reg2 = rand.nextInt(1000);
}

//toString to print CPU registers and current process
public String toString() {
	return "Registers: " + Reg1 + " " + Reg2 + "\nProcess On CPU: " + currentProcess;
}

//Function to run CPU, returns int value 1 for termination, 2 for IO request and 0 for no action needed
int runCPU() {
	if(currentProcess != null) { //If there is an active process on CPU
		Reg1 = rand.nextInt(1000); //Randomize registers and save onto PCB
		Reg2 = rand.nextInt(1000);
		currentProcess.save(this);
		if(currentProcess.CurrentInstruction > currentProcess.PCounter) { //Check if current instruction is greater than max number of instructions on current PCB 
			return 1; //Return 1 to terminate
		}
		if(currentProcess.currentIORequests < currentProcess.IORequests.length) { //Check if any remaining IO Requests
			if(currentProcess.CurrentInstruction == currentProcess.IORequests[currentProcess.currentIORequests][0]) { //Check if current instruction is in the IORequest array
				//currentProcess.CurrentInstruction++;
				return 2; //Return 2 to move Process to IO
			}
		}
		currentProcess.CurrentInstruction++; //Go to next instruction
	}
	return 0; //Return 0 for no action needed
};
}
