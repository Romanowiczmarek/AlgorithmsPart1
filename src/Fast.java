import java.util.Arrays;




public class Fast {


	public static void main(String[] args) {

		In in = new In(args[0]);
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		
		int n = in.readInt();
		
		Point[] array = new Point[n];
		Point[] arrayComparator = new Point[n];
		int x, y;
		
        for (int i = 0; i< n; i++) {
        	x = in.readInt();
        	y = in.readInt();
            array[i] = new Point(x, y);
            array[i].draw();
            arrayComparator[i] = new Point(x, y);
        }
        int lower = 0, higher = 0;
        Point preserved, lowest, highest;
        StringBuilder builder;
        
        for(int i = 0; i<n; i++){
        	preserved = arrayComparator[i];
        	Arrays.sort(array,0, n,  preserved.SLOPE_ORDER); 
        	lower = 0;
        	higher = 0;
        	int k = 0;
        	
        	for(int j = 1; j <= n - 3 ; j++){
        		builder = new StringBuilder();
        		k = j;

        		while(j+1 < n && preserved.slopeTo(array[j]) == preserved.slopeTo(array[j+1])){   					
    				j++;
        		}
        		
        		if((j - k >= 2)){
        			Arrays.sort(array, k, j+1);
        			if(preserved.compareTo(array[k]) == -1){
        				preserved.drawTo(array[j]);
        				
                		builder.append(preserved.toString());
                		for(int m = k; m<=j;m++)
                			builder.append(" -> "+array[m].toString());
        				
                		StdOut.println(builder.toString());
        			}
        		}
        			
        	}
        }
        
	}
	
}
