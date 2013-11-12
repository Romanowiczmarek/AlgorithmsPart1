
public class MainApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		for (int i = 1; i < 100000; i = 2*i)
				{
				            Stopwatch watch = new Stopwatch();
				            //Timing.trial(i, 747184);
				            StdOut.println(i + " " + watch.elapsedTime());
				}
		
		

	}

}
