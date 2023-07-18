import java.io.IOException;

public class Driver {

	public static void main(String[] args) throws IOException {
		PCB[] input = PCB.fromFile("processes.txt");
		
	
//		int[] req = {5,6,17,20};
//		int[] io = {1,1,1,2};
//		PCB test1 = new PCB(1, 100, req, io);
//		int[] req2 = {};
//		int[] io2 = {};
//		PCB test2 = new PCB(2, 67, req2, io2);
//		int[] req3 = {14,20,21,22,23,140,145};
//		int[] io3 = {1,2,2,2,2,2,1};
//		PCB test3 = new PCB(3, 150, req3, io3);
		Scheduler s = new Scheduler();
		s.admit(input[1]);
		//s.admit(input[2]);
//		s.admit(test2);
//		s.admit(test3);
		s.runScheduler();
	}

}
