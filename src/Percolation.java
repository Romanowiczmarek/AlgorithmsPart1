

import  java.lang.IndexOutOfBoundsException;


public class Percolation {
	private boolean[][] idGrid;
	private int N;
	private WeightedQuickUnionUF quickUnion, quickUnionBackwash;
	private boolean isPathological = false;
	
	
	public Percolation(int N){ // create N-by-N grid, with all sites blocked
		idGrid = new boolean[N][N]; //blocked site  = true , open site - false
		this.N = N;
		if(N != 1){
			for(int i = 0; i < N; i++)
				for(int j = 0; j < N ; j++)
					idGrid[i][j] = true;
		
			isPathological = false;
			quickUnion = new WeightedQuickUnionUF(N*N+2);
			quickUnionBackwash = new WeightedQuickUnionUF(N*N+1);
		}else{
			isPathological = true;
			idGrid[0][0] = true;
		}
	}
	
	//Let top rows have component N, and bottom rows have N+1
	
	public void open(int i, int j){ // open site (row i, column j) if it is not already
		if (i <= 0 || i > N) throw new java.lang.IndexOutOfBoundsException("row index i out of bounds");
		if (j <= 0 || j > N) throw new java.lang.IndexOutOfBoundsException("row index j out of bounds");
		
		if(idGrid[i-1][j-1] && !isPathological){
			
			idGrid[i-1][j-1] = false;
			
		//	StringBuilder builder = new StringBuilder();
			
			if(!idGrid[i-1][max(0, j-2)]){  // Checking to the left
				quickUnion.union(j + (i-1)*N, max(1,j-1) + (i-1)*N);
				quickUnionBackwash.union(j + (i-1)*N, max(1,j-1) + (i-1)*N);
			//	builder.append("Left ");
			}
			
			if(!idGrid[i-1][min(N-1, j)]){   // Checking to the right
				quickUnion.union(j + (i-1)*N, min(N,j+1) + (i-1)*N);
				quickUnionBackwash.union(j + (i-1)*N, min(N,j+1) + (i-1)*N);
			//	builder.append("Right ");
			}
			
			if(!idGrid[max(0, i-2)][j-1]){  // Checking upwards
				quickUnion.union(max(0,i-2)*N + j, (i-1)*N + j);
				quickUnionBackwash.union(max(0,i-2)*N + j, (i-1)*N + j);
			//	builder.append("Up ");
			}
			
			if(!idGrid[min(N-1, i)][j-1]){  // Checking downwards
				quickUnion.union(min(N-1,i)*N + j, (i-1)*N + j);
				quickUnionBackwash.union(min(N-1,i)*N + j, (i-1)*N + j);
			//	builder.append("Down ");
			}
		
			if(i == 1){
				quickUnion.union((i-1)*N+j, 0);
				quickUnionBackwash.union((i-1)*N+j, 0);
			//	builder.append("Top ");
			}
			if(i == N){
				quickUnion.union((i-1)*N+j, N*N+1);
			//	builder.append("Bottom");
			}
			
			//System.out.println(builder.toString());
		}else if(isPathological && idGrid[0][0]){
			idGrid[0][0] = false;
			
		}
	}
	
	public boolean isOpen(int i, int j){ // is site (row i, column j) open?
		
		if (i <= 0 || i > N) throw new java.lang.IndexOutOfBoundsException("row index i out of bounds");
		if (j <= 0 || j > N) throw new java.lang.IndexOutOfBoundsException("row index j out of bounds");
				
		return !idGrid[i-1][j-1];
	}
	
	public boolean isFull(int i, int j){ // is site (row i, column j) full?
		
		if (i <= 0 || i > N) throw new java.lang.IndexOutOfBoundsException("row index i out of bounds");
		if (j <= 0 || j > N) throw new java.lang.IndexOutOfBoundsException("row index j out of bounds");
		
		if(isPathological)
			return isOpen(i, j);
		
		return (quickUnion.find((i-1)*N+j) == quickUnion.find(0) && (quickUnionBackwash.find((i-1)*N+j) == quickUnionBackwash.find(0))); 	
	}
	
	public boolean percolates(){ // does the system percolate?
		if(isPathological)
			return isOpen(1,1);
		//System.out.println("Root top: "+ quickUnion.find(N+2));
		
		return (quickUnion.find(N*N+1) == quickUnion.find(0)); 	
	}
	
		
	private int min(int a, int b){
		if(a>b)
			return b;
		else
			return a;
	}
	
	private int max(int a, int b){
		if(a>b)
			return a;
		else
			return b;
	}
	
	public static void main(String[] args) {
		
		

	}

}
