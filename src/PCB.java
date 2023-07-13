
public class PCB {
int PNum;
int PCounter;
int CurrentInstruction;
int Reg1;
int Reg2;
String state;
int currentIORequests;
int[][] IORequests;

void save(CPU controller) {
	Reg1 = controller.Reg1;
	Reg2 = controller.Reg2;
};

}
