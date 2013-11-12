import java.util.Scanner;


public class BinarySearch {
	
	
	
	
	public static void main(String[] args) {
		int intArray[] = new int[6];
		int query;
		
		try {
			Scanner sc = new Scanner(System.in);
			
			for(int i = 0; i<6; i++)
				intArray[i] = sc.nextInt();
			
			query = sc.nextInt();	
			
			System.out.println(searchBinary(intArray, 0, intArray.length-1, query));
			//System.out.println("Output:");
			
		} catch (Exception e) {
			System.err.println("Error:" + e.getMessage());
		}
	
	}

	
	
	// Assertion: ascending array
	static public int searchBinary(int[] array, int a, int b, int query){

		int c = (a+b)/2;
		if((a == b && array[a] != query) || array[a]>query || array[b]<query||((a == c||b == c) && array[c]!= query)) return -1;

		System.out.println("a = "+a+" value = "+array[a]+" b = "+b+" value = "+array[b]+" c = "+c+" value = "+array[c]);
		
		if(array[c]>query) return searchBinary(array, a, c, query);
		else if(array[c]<query) return searchBinary(array, c, b, query);
		else if(array[c] == query) return c+1;
		else if(array[c] != query) return -1;
		
		return -1;
	}
	
}


