


public class Brute {


	public static void main(String[] args) {

		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		
		In in = new In(args[0]);
		
		int n = in.readInt();
		
		Point[] array = new Point[n];
		
        for (int i = 0; i< n; i++) {
             array[i] = new Point(in.readInt(), in.readInt());
             array[i].draw();
        }
		
        for(int i = 0; i < n ; i++){ //1
        	for(int j = 0 ; j < n; j++){  // 0
        		for(int k = 0 ; k< n; k++){ // 3
        			for(int m = 0 ; m < n; m++){  //  2
        				if(array[i].slopeTo(array[j]) == array[i].slopeTo(array[k]) &&array[i].slopeTo(array[j]) == array[i].slopeTo(array[m])){
        					if(array[i].compareTo(array[j]) == -1 && array[j].compareTo(array[k]) == -1 && array[k].compareTo(array[m]) == -1){
        						StdOut.println(array[i].toString() + " -> " + array[j].toString() + " -> " + array[k].toString() + " -> " + array[m].toString());
        						array[i].drawTo(array[m]);
        					}
        				}
        			}
        		}   		
        	}
        }
        
        
        
	}
	
}
