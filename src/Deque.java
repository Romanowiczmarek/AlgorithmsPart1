import java.util.Iterator;


public class Deque<Item> implements Iterable <Item>{

	private Node first, last;
	private int size;
	
	public Deque(){  // construct an empty deque
		first = null;
		last = null;
		size = 0;
	}
	
	public boolean isEmpty(){           // is the deque empty?
		return (first == null) || (last == null);
	}
	
	public int size(){                  // return the number of items on the deque
		return size;
	}
		
	public void addFirst(Item item){    // insert the item at the front
		Node newFirst = new Node();
		
		if(item == null) throw new java.lang.NullPointerException();
		
		newFirst.item = item;
		
		if(size == 0){
			first = newFirst;
			last = first;
			first.next = null;
			first.previous = null;
		}else{
			first.previous = newFirst;
			newFirst.next = first;
			first = newFirst;
		}
		size++;
	}
	
	public void addLast(Item item){     // insert the item at the end
		Node newLast = new Node();
		
		if(item == null) throw new java.lang.NullPointerException();
		
		newLast.item = item;

		if(size == 0){
			first = newLast;
			last = first;
			first.next = null;
			first.previous = null;
		}else{
			newLast.previous = last;
			last.next = newLast;
			last = newLast;
		}
		size++;
	}
	
	public Item removeFirst(){          // delete and return the item at the front
		if(size == 0) throw new java.util.NoSuchElementException();
		
		Item itemToReturn = first.item;
		if(size == 1){
			last = null;
			first = null;
		}else{
			first = first.next;
			first.previous = null;
		}
		size--;
		return itemToReturn;
	}

	public Item removeLast(){           // delete and return the item at the end
		if(size == 0) throw new java.util.NoSuchElementException(); 
		
		Item itemToReturn = last.item;
		
		if(size == 1){
			last = null;
			first = null;
		}else{
			last = last.previous;
			last.next = null;
		}
		size--;
		return itemToReturn;
	}
	
	public Iterator<Item> iterator(){ return new ListIterator();}
	
	private class ListIterator implements Iterator<Item>{
		private Node current;
		
		public ListIterator(){
			current = first;
		}
		
		public boolean hasNext(){
			return current != null;
		}
		
		public void remove(){
			throw new java.lang.UnsupportedOperationException();
		}

		public Item next() {
			if(current == null)
				throw new java.util.NoSuchElementException();
				
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
	

	private class Node{
		Item item;
		Node next, previous;
		public Node(){
			item = null;
			next = null;
			previous = null;			
		}
	}
	
	public static void main(String[] args) {
		
		Deque<Integer> queue = new Deque<Integer>();
		
		for(int i = 0; i < 10; i++){
			queue.addLast(i);
		}
		Iterator it = queue.iterator();
		
		while(it.hasNext()){
			StdOut.println(it.next());			
		}
		
	}

}

