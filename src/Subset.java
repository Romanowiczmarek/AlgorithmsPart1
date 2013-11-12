
public class Subset {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String i;
		
		int k = Integer.valueOf(args[0]);
		
		RandomizedQueue<String> randQueue = new RandomizedQueue<String>();
				
        while (!StdIn.isEmpty()) {
            i = StdIn.readString();
            randQueue.enqueue(i);
        }
        
        for(int j = 0; j < k ; j++){
        	StdOut.println(randQueue.dequeue());        	
        }
        
	}

}
