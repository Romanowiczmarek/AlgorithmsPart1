import java.util.Iterator;



public class RandomizedQueue<Item> implements Iterable<Item> {
	
	private int N, used;
	private Item[] queue;
	private boolean[] isUsed;


	public RandomizedQueue(){           // construct an empty randomized queue
		queue = (Item[]) new Object[1];
		isUsed = new boolean[1];
		
		isUsed[0] = false;
		N = 1;
		used = 0;
	}	
		   
	public boolean isEmpty(){           // is the queue empty?
		return used == 0;
   }
	
	public int size(){                  // return the number of items on the queue
		return used;
	}
	
	public void enqueue(Item item){     // add the item
		if(item == null) throw new java.lang.NullPointerException();
		
		if(used == N)
			resize(2*N);
		
		int a = StdRandom.uniform(N);
		while(isUsed[a] != false)
			a = StdRandom.uniform(N);
		
		queue[a] = item;
		isUsed[a] = true;
		used++;
	}
		
	public Item dequeue(){              // delete and return a random item
		if(used == 0) throw new java.util.NoSuchElementException();
		
		int a = StdRandom.uniform(N);
		while(isUsed[a] == false)
			a = StdRandom.uniform(N);
		Item itemToReturn = queue[a];
		isUsed[a] = false;
		queue[a] = null;
		used--;
		
		if(used == N/4 && used > 0)
			resize(N/2);
		
		return itemToReturn;
	}
	
	public Item sample(){               // return (but do not delete) a random item
		if(used == 0) throw new java.util.NoSuchElementException();
		
		int a = StdRandom.uniform(N);
		while(isUsed[a] == false)
			a = StdRandom.uniform(N);
		return queue[a];
	}
	
	public Iterator<Item> iterator(){   // return an independent iterator over items in random order
		return new QueueIterator();
	}
	
	private class QueueIterator implements Iterator<Item>{
		private Item[] data;
		private int current = 0;
		private int size;
		
		public QueueIterator(){
			data = (Item[]) new Object[used];
			size = used;
			int count = 0;
			
			for(int i = 0 ; i < N ; i++){
				if(isUsed[i] == true){
					data[count] = queue[i];
					if(count != 0)
						exchange(data, count, StdRandom.uniform(count));
					
					count++;
				}
			}
		}
		
		public boolean hasNext(){
			return current < size;
		}
		
		public void remove(){
			throw new java.lang.UnsupportedOperationException();
		}

		public Item next() {
			if(current>=size || size == 0) throw new java.util.NoSuchElementException();
			Item item = data[current];
			current++;			
			return item;
		}
	}
	

	
	private void resize(int capacity){
		Item[] copyQueue = (Item[]) new Object[capacity];
		boolean[] copyIsUsed = new boolean[capacity];
			for(int m = 0; m < capacity; m++)
				copyIsUsed[m] = false;
			
		int k = 0;
		
		for(int i = 0; i < queue.length;i++){
			if(isUsed[i] == true){
				copyQueue[k] = queue[i];
				copyIsUsed[k] = true;
				k++;
			}
		}
		
		queue = copyQueue;
		isUsed = copyIsUsed;
		N = capacity;
	}
	
	private void exchange(Item[] array, int i, int j){
		Item tmp;
		tmp = array[i];
		array[i] = array[j];
		array[j]  = tmp;	
	}
	
	
	public static void main(String[] args) {
		RandomizedQueue<Integer> queue = new RandomizedQueue<>();
		
		for(int i = 0; i < 10; i++){
			queue.enqueue(i);
		}

		Iterator it = queue.iterator();
		while(it.hasNext()){
			StdOut.println(it.next());			
		}
		it.next();
	}

}
