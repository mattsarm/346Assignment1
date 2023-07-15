
public class PCB {
int PNum;
int PCounter;
int CurrentInstruction;
int Reg1;
int Reg2;
String state;
int currentIORequests;
int[][] IORequests;

public PCB(int num, int counter, int[] instructIO, int[] device) {
	PNum = num;
	PCounter = counter;
	CurrentInstruction = 0;
	Reg1 = 0;
	Reg2 = 0;
	IORequests = new int[instructIO.length][device.length]; 
	for(int i = 0; i < instructIO.length; i++) {
		IORequests[i][0] = instructIO[i];
		IORequests[i][1] = device[i];
	}
}

void save(CPU controller) {
	Reg1 = controller.Reg1;
	Reg2 = controller.Reg2;
};

public String toString() {
	String io = "";
	for(int i = 0; i < IORequests.length; i++) {
		io += "[" + IORequests[i][0] + "," + IORequests[i][1] + "],";
	}
	return "Program " + PNum + ", Instruction " + CurrentInstruction + "/" + PCounter + ", State \"" + state + "\", IO Requests: " + io  + " Registers 1/2 " + Reg1 + " " + Reg2;
}

}
