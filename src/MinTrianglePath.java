import java.util.NoSuchElementException;
import java.util.Scanner;

/*	Author: Marek Romanowicz
 * 
 * 	Assumption: All the input numbers and their (path costs) sums are within the range of an int. Otherwise I would have to use Long objects
 * 
 * 	Function: Computes minimal path according to the specification
 * 
 * 
 */



// Custom exception to handle not enough numbers entered in one line
	class NotEnoughNumbersInLineException extends Exception{
		int lineNumber;
		
		public NotEnoughNumbersInLineException(int lineNum){
			super();
			lineNumber = lineNum;
		}
	}
	

// Custom exception to handle too many numbers entered in one line
	class TooManyNumbersInLineException extends Exception{
		int lineNumber;
		
		public TooManyNumbersInLineException(int lineNum){
			super();
			lineNumber = lineNum;
		}
	}


	
// Public class used mostly to handle data input and exceptions thrown by NumbersTree class
	
public class MinTrianglePath {
	
	public static void main(String[] args) {
		
		NumbersTree numbersTree = new NumbersTree();									// Initialization of my own class
		
		Scanner sc = new Scanner(System.in);											// Using a Scanner object to read data from standard input
		String line;																	// line stores each line of input
		int lineNumber = 1, numberIndex = 0, i; 										// helper variables with regards to error handling
		String[] numbers = new String[1];												// helper String array in splitting into individual numbers that required initialization
		
		try{

			while(1 == 1){ 																	// Scanning the input till the exception thrown caused by EOF, which is the reason for infinite loop
			
				line = sc.nextLine();
				numbers = line.split(" ");
			
				for(i = 0; i < numbers.length; i++){
					numberIndex = i;
					numbersTree.insert(Integer.parseInt(numbers[i]), false);
				}
				
				lineNumber++;
				numbersTree.insert(0, true);
			}
				
		}catch(NotEnoughNumbersInLineException e){
			System.out.println("You did not enter enough numbers in line " + e.lineNumber);			
		
		} catch (TooManyNumbersInLineException e) {
			System.out.println("You entered too many numbers in line " + e.lineNumber);		
		
		}catch(NoSuchElementException e){															// this exception is thrown when Scanner encounters EOF
			System.out.println(numbersTree.printMinimalPath());
		
		}catch(NumberFormatException e){
			
			if(numberIndex < numbers.length)
				System.out.println("Text \"" + numbers[numberIndex]+"\" that you entered is not a number.");
			else
				System.out.println("Text that you entered in line "+ lineNumber+" is not a number.");
		
		}catch (Exception e){
			e.printStackTrace();		
		
		}finally{
			sc.close();																				// Closing a scanner
		}
		
		
		

	}


}




/* NumbersTree class stores all the numbers from the input in int array called numbers (assuming input number and cannot exceed an int)
 * and calculates minimal costs of paths ending in each position storing it in minimalPathCosts
 */


class NumbersTree{
	private int[] numbers, minimalPathCosts;  								// two arrays storing numbers and costs
	private int currentLevel, currentOffset, currentIndex; 					// private variables used in insertion of numbers
	private Stack<Integer> path; 											// minimal path will be stored here for convenience
	
	
	// Constructor initializing all the class members
	public NumbersTree(){
		numbers = new int[501*500/2+1]; 									// number of nodes in a tree of maximum height with indices starting from 1
		minimalPathCosts = new int [501*500/2+1]; 							// size of this array is the same as that of numbers
		path = new Stack<Integer>();
		currentLevel = 1;
		currentOffset = 0;
		currentIndex = 1;
	}
	
	
	// This function inserts an integer into the next available empty place in the tree
	public void insert(int a, boolean isEOL) throws NotEnoughNumbersInLineException, TooManyNumbersInLineException {
		
																			// isEOL - boolean signifying End Of Line
		if(isEOL && currentLevel == currentOffset){ 						// we reached the end of current tree line
			currentLevel++;
			currentOffset = 0;
			return;
		}else if (isEOL && currentLevel > currentOffset){
			throw new NotEnoughNumbersInLineException(currentLevel);		//throwing an exception when user enters too few numbers in a row
		}else if (isEOL && currentLevel < currentOffset){
			throw new TooManyNumbersInLineException(currentLevel);			//throwing an exception when user enters too many numbers in a row
		}
		
		numbers[currentIndex] = a;
		minimalPathCosts[currentIndex] = a + findMinAncestor(currentLevel, currentOffset, currentIndex); 		// If everything is alright, then update numbers and minimalPathCosts arrays
		
		currentOffset++;
		currentIndex++;
	}

	
	
	// Internal function that finds the minimal path and stores it in a Stack called path
	// It returns a minimal cost of a path 
	private int findMinimalPath(){
		int currentMin, currentMinOffset, currentMinIndex; 					//local variables used to find minimum value in the last row of path costs tree
		int localLevel = currentLevel-1, localIndex, localOffset;			// local variables used to traverse the numbers' tree to find a minimal path
		
		
		// initializing local variables with the first minimal cost candidate, left most node
		currentMin = minimalPathCosts[currentIndex - localLevel];
		currentMinOffset = 0;
		currentMinIndex = currentIndex - localLevel;
		
		
		
		// Need to compare it to all the other nodes in that row and choose the lowest
		for(int i = currentMinIndex; i < currentIndex; i++){
			if(minimalPathCosts[i] < currentMin){
				currentMin = minimalPathCosts[i];
				currentMinOffset = i - currentIndex + localLevel;
				currentMinIndex = i;
			}
		}
		
		
		// At this point we know the position of the final node of the minimal path now		
		// Initialize local variables with the position found
		
		localIndex = currentMinIndex;
		localLevel = currentLevel - 1;
		localOffset = currentMinOffset;
		path.push(numbers[localIndex]);
		
		while(localLevel > 1){ 												// traversing the tree level by level
			
			if(localOffset == 0){ 											//currently used node is left most, there is only one way then
				localIndex -= localLevel -1; 								// we don't need to change localOffset
				path.push(numbers[localIndex]);
				
			}else if(localOffset == localLevel - 1){ 						//currently used node is right most, there is only one way then
				localIndex -= localLevel;
				localOffset--;
				path.push(numbers[localIndex]);
				
			}else if(minimalPathCosts[localIndex] == minimalPathCosts[localIndex - localLevel] + numbers[localIndex]){ // previous node is above to the left
				localIndex -= localLevel;
				localOffset--;
				path.push(numbers[localIndex]);
				
			}else{															// previous node is above to the right, it's the only way left to consider
				localIndex -= localLevel - 1; 								// we don't need to change localOffset here
				path.push(numbers[localIndex]);
			}
			
			localLevel--;													// going up one level
		}

		
		return currentMin;	
	}
	
	
	
	//This function calls an internal method findMinimalPath() and prints out a minimal path according to the scheme
	public String printMinimalPath(){
		StringBuilder stringBuilder = new StringBuilder();
		int minimalCost = findMinimalPath();
		
		stringBuilder.append("Minimal path is: ");
		
		if(!path.isEmpty()){
			stringBuilder.append(path.pop().toString());	
		}
	
		while(!path.isEmpty()){
			stringBuilder.append(" + " + path.pop().toString());
		}
		
		stringBuilder.append(" = " + minimalCost);
		
		return stringBuilder.toString();
	}
	
	
	
	
	//This function returns a minimal value of minimalPathCosts array of adjacent nodes above
	private int findMinAncestor(int level, int offset, int index){
		if(level == 1) return 0; 															// there are no nodes above
		
		if(offset == 0) return minimalPathCosts[index - level + 1]; 						// there is only one node above (left hand most node)
		if(offset == level - 1) return minimalPathCosts[index - level]; 					// there is only one node above (right hand most node)
		
		return min(minimalPathCosts[index - level], minimalPathCosts[index - level +1]); 	// return the minimum of two nodes aboves
	}
		
	
	
	// This function returns a lower of two ints
	private int min(int a, int b){
		if(a < b) return a;
		else return b;
	}
	
	
	
	
	
}