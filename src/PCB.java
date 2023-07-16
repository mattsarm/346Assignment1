
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

static PCB[] fromFile() throws IOException {

        BufferedReader lineReaderCounter = new BufferedReader(new FileReader("processes.txt"));
        int lineCounter1 = 0;

        while ((lineReaderCounter.readLine()) != null) {
            lineCounter1++;
        }

        PCB[] data = new PCB[lineCounter1 + 1];

        BufferedReader lineReader = new BufferedReader(new FileReader("processes.txt"));
        String line;
        int lineCounter2 = 0;

        int[][] ioRequests;

        while ((line = lineReader.readLine()) != null) {
            String[] row = line.split(",(?![^\\[]*\\])");

            System.out.println(Arrays.toString(row));

            int processID = Integer.parseInt(row[0].trim());
            int nbrInstructions = Integer.parseInt(row[1].trim());

            if (row[2].length() > 3) {
                String[] ioReqTime = row[2].substring(2, row[2].length() - 1).split(",");
                System.out.println(Arrays.toString(ioReqTime));
                String[] ioDevReq = row[3].substring(2, row[3].length() - 1).split(",");
                System.out.println(Arrays.toString(ioDevReq));

                for (int i = 0; i < ioReqTime.length; i++) {
                    System.out.println(ioReqTime[i]);
                    System.out.println(ioDevReq[i]);
                }
                ioRequests = new int[ioReqTime.length][2];
                for (int i = 0; i < ioReqTime.length; i++) {
                    ioRequests[i][0] = Integer.parseInt(ioReqTime[i].trim());
                    System.out.println(ioRequests[i][0]);
                    ioRequests[i][1] = Integer.parseInt(ioDevReq[i].trim());
                    System.out.println(ioRequests[i][1]);
                }
                System.out.println(Arrays.deepToString(ioRequests));

            }else{
                ioRequests = new int[1][2];
                System.out.println(Arrays.deepToString(ioRequests));
            }

            data[lineCounter2] = new PCB(processID, nbrInstructions, ioRequests);

            lineCounter2++;
        }

        return data;

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
