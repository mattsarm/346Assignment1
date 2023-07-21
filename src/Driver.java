import java.io.IOException;

public class Driver {

	public static void main(String[] args) throws IOException {
		PCB[] input = PCB.fromFile("processes.txt");
		Scheduler s = new Scheduler();
		for(int i = 0; i < 3;i++) {
		s.admit(input[i]);
	}
	//	s.admit(input[0]);
		s.runScheduler();
	}

}
