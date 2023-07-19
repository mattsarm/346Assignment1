import java.io.IOException;

public class Driver {

	public static void main(String[] args) throws IOException {
		PCB[] input = PCB.fromFile("processes.txt");
		Scheduler s = new Scheduler();
//		for(int i = 6; i < input.length-1;i++) {
//			s.admit(input[i]);
//		}
		s.admit(input[0]);
		s.runScheduler();
	}

}
