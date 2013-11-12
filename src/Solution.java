/*
Please write complete compilable code.
Your class should be named Solution
Read input from standard input (STDIN) and print output to standard output(STDOUT).
For more details, please check https://www.interviewstreet.com/recruit/challenges/faq/view#stdio
 */

import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeSet;

public class Solution {
	public final static int MAX_MOVES = 7;
	

	TreeSet unOrderedElements = new TreeSet();
	TreeSet pegs[] = new TreeSet[6];	

	public static void main(String[] args) {

		Configuration initial, finalConfig;
		int N = 0, K = 0;
		boolean found = false;
		//Configuration initial, finalConfig;
		initial = new Configuration();
		finalConfig = new Configuration();
		

		
		
		try {
			Scanner sc = new Scanner(System.in);
			N = sc.nextInt();
			K = sc.nextInt();
			
			for(int i = 0; i<N; i++)
				initial.disks[i+1] = sc.nextInt();
			
			for(int i = 0; i<N; i++)
				finalConfig.disks[i+1] = sc.nextInt();	
			
			//System.out.println("Output:");
			
		} catch (Exception e) {
			System.err.println("Error:" + e.getMessage());
		}
		
		
		
		PriorityQueue<Configuration> pq = new PriorityQueue<Configuration>();
		initial.move = 0;
		initial.calculateMins();
		initial.heurestics(finalConfig);
		pq.add(initial);
		
		Configuration current, temp;
		current = pq.poll();
		while(!found && current != null && current.move < MAX_MOVES){
			
			//current.printDisks(N);
			
			for(int i = 1; i <= K; i++){
				if(current.mins[i] != 0){
					for(int j = 1; j <= K; j++){
						if(i != j && (current.mins[j] == 0 || current.mins[i]<current.mins[j]) && current.move < MAX_MOVES){  // need to add a condition for not repeating a previous configuration
							
							if(current.parent != null || (current.mins[i] != current.lastMoved) || ( j != current.parent.disks[current.lastMoved])){ // checking if we don't go back to previous state

							
								temp = new Configuration(current);
								temp.move = current.move+1;
								temp.parent = current;
								temp.lastMoved = current.mins[i];
								temp.disks[current.mins[i]] = j;
								temp.heurestics(finalConfig);
								temp.calculateMins();
								//temp.printDisks(N);
							
								if(temp.heur == 0){
									System.out.println(temp.move);
									temp.printPath();
									i = 10;
									j = 10;
									found = true;
									break;
								}else{
									pq.add(temp);		
								}
							}
						}
						
					}
				}
			}
				
			if(!pq.isEmpty() && !found)
				current = pq.poll();
		}
		
		

	}
	
}


class Configuration implements Comparable {
	int disks[];
	int mins[];
	Configuration parent = null;
	int distanceSoFar, heur;
	int lastMoved, move;
	
	
	public Configuration() {
		disks = new int[9]; // holds a its peg number
		mins = new int[6];
		
		//Assuming they are all zero'ed. Need to check this	
		lastMoved = 0;
	}

	public Configuration(Configuration a) {
		disks = new int[9]; // holds a its peg number
		mins = new int[6];
		
		//Assuming they are all zero'ed. Need to check this	
		lastMoved = 0;
		
		for (int i = 0; i < a.disks.length; i++)
			this.disks[i] = a.disks[i];
	}
	
	@Override
	public int compareTo(Object other) {
		if(this.move  < ((Configuration) other).move ) return -1;
		else if(this.move > ((Configuration) other).move ) return 1;
		if(this.heur < + ((Configuration) other).heur) return -1;
		else if(this.heur > ((Configuration) other).heur) return 1;
		//Might work on this a little longer
		return 0;
	}
	
	@Override
	public boolean equals(Object other){
		for(int i = 0; i < ((Configuration) other).disks.length; i++){
			if(((Configuration) other).disks[i] != this.disks[i])
				return false;
		}
		
		return true;	
	}
	
	public int heurestics(Configuration finalConf){
		int result = 0;
		
		for(int i = 0 ; i<this.disks.length; i++){
			if(this.disks[i] != finalConf.disks[i])
				result++;
		}
		heur = result;
		return result;
	}
	
	public void printPath(){
		Configuration current = this;
		
		if(current.parent != null){
			current.parent.printPath();
			System.out.println(current.parent.disks[current.lastMoved] + " " + current.disks[current.lastMoved]);
		}
		
	}
	
	public void printDisks(int K){
		
		for(int i = 1; i <= K; i++){
			System.out.print(disks[i] + " ");
		}
		System.out.println("");
	}
	
	public void calculateMins(){
		for(int i = 0; i<9; i++)
			mins[disks[i]] = mins[disks[i]] != 0 ? Math.min(mins[disks[i]], i) : i;
	}

}