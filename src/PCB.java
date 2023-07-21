import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//PCB CLass
public class PCB {
int PNum; //Process Number
int PCounter; //Max Instruction
int CurrentInstruction; //Current instruction
int Reg1; //Register 1 data
int Reg2; //Register 2 data
String state; //Current Process state
int currentIORequests; //Index of first dimension of IO Request Array
int[][] IORequests; //2D array of [IO Request Instruction Number, IO Device Number]


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

//PCB Constructor, takes process number, max instruction, and IO 2D array
public PCB(int num, int counter, int[][] instructIO) {
	PNum = num;
	PCounter = counter;
	CurrentInstruction = 0;
	Reg1 = 0;
	Reg2 = 0;
	IORequests = instructIO;
}

//PCB static method to read .txt and output an array of valid PCB's
static PCB[] fromFile(String filename) throws IOException {

        BufferedReader lineReaderCounter = new BufferedReader(new FileReader(filename));
        int lineCounter1 = 0;
        int[][] emptyarray = {};
        while ((lineReaderCounter.readLine()) != null) {
            lineCounter1++;
        }

        PCB[] data = new PCB[lineCounter1 + 1];

        BufferedReader lineReader = new BufferedReader(new FileReader(filename));
        String line;
        int lineCounter2 = 0;

        int[][] ioRequests;
        
        while ((line = lineReader.readLine()) != null) {
            String[] row = line.split(",(?![^\\[]*\\])");

            int processID = Integer.parseInt(row[0].trim());
            int nbrInstructions = Integer.parseInt(row[1].trim());

            if (row[2].length() > 3) {
                String[] ioReqTime = row[2].substring(2, row[2].length() - 1).split(",");

                String[] ioDevReq = row[3].substring(2, row[3].length() - 1).split(",");
                
                ioRequests = new int[ioReqTime.length][2];
                for (int i = 0; i < ioReqTime.length; i++) {
                    ioRequests[i][0] = Integer.parseInt(ioReqTime[i].trim());
                    ioRequests[i][1] = Integer.parseInt(ioDevReq[i].trim());
                }

            }else{
                ioRequests = emptyarray;
            }

            data[lineCounter2] = new PCB(processID, nbrInstructions, ioRequests);

            lineCounter2++;
        }
        lineReaderCounter.close();
        lineReader.close();
        return data;

    }

//Function to save CPU register values on PCB
void save(CPU controller) {
	Reg1 = controller.Reg1;
	Reg2 = controller.Reg2;
};

//toString method to print PCB
public String toString() {
	String io = "";
	for(int i = 0; i < IORequests.length; i++) {
		io += "[" + IORequests[i][0] + "," + IORequests[i][1] + "],";
	}
	return "Program " + PNum + ", Instruction " + CurrentInstruction + "/" + PCounter + ", State \"" + state + "\", IO Requests: " + io  + " Registers 1/2 " + Reg1 + " " + Reg2;
}

}
