
public class Driver {

	public static void main(String[] args) {
		int[] req = {5,6,17,20};
		int[] io = {1,1,1,2};
		PCB test = new PCB(1, 100, req, io);
		Scheduler s = new Scheduler();
		s.admit(test);
		s.runScheduler();
	}

}
