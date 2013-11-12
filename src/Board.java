import java.util.Iterator;


public class Board {
	private int N;
	private int[][] board;
	//private int[][] goal;
	private int goalMod = 0;
	private int blankRow = 0;
	private int blankColumn = 0;
	
		
	    public Board(int[][] blocks){           // construct a board from an N-by-N array of blocks
	    	N = blocks[0].length;				// (where blocks[i][j] = block in row i, column j)
	    	board = new int[N][N];  
	    	//goal = new int[N][N];
	    	
	    	for(int i = 0; i<N; i++){
	    		for(int j = 0; j<N; j++){
	    			board[i][j] = blocks[i][j];
	    			if(board[i][j] == 0){
	    				blankRow = i;
	    				blankColumn = j;
	    			}
	    		}
	    	}
	    	/*
	    	for(int i = 0; i<N; i++)
	    		for(int j = 0; j<N; j++)
	    			goal[i][j] = i*N+j+1;
	    	goal[N-1][N-1] = 0;
	    	*/
	    }
	    
	    
	    public int dimension(){                 // board dimension N
	    	return N;
	    }
	   
	    public int hamming(){                   // number of blocks out of place
	    	int result = 0;
	    	for(int i = 0; i<N; i++)
	    		for(int j = 0; j<N; j++){
	    			if(board[i][j] != i*N + j+1 && board[i][j] != 0)
	    				result++;
	    		}
	    	return result;
	    }
	    
	    public int manhattan(){                 // sum of Manhattan distances between blocks and goal
	    	int result = 0;
	    	for(int i = 0; i<N; i++){
	    		for(int j = 0; j<N; j++){
	    			if(board[i][j] != 0)
	    				result+= Math.abs(i - (board[i][j]-1)/N) + Math.abs(j - ((board[i][j]-1)%N));
	    		}
	    	}
	    	return result;
	    }
	    
	    
	    public boolean isGoal(){                // is this board the goal board?
	    	
	    	for(int i = 0; i<N; i++)
	    		for(int j = 0; j<N; j++)
	    			if(board[i][j] != (i*N+j+1)%(N*N))
	    				return false;
	    	return true;
	    	/*
	    	for(int i = 0; i<N; i++)
	    		for(int j = 0; j<N; j++)
	    			if(board[i][j] != [i][j])
	    				return false;
	    	return true;
	    	 */
	    }
	    
	    public Board twin(){                    // a board obtained by exchanging two adjacent blocks in the same row
	    	int blocks[][] = new int[N][N];
	    	int tmp;

	    	for(int i = 0; i<N; i++)
	    		for(int j = 0; j<N; j++)
	    			blocks[i][j] = board[i][j];
	    	
	    	for(int i = 0; i<N; i++){
	    		for(int j = 0; j<N-1; j++){
	    			if(board[i][j] != 0 && board[i][j+1] != 0){
	    				tmp = blocks[i][j];
	    				blocks[i][j] = blocks[i][j+1];
	    				blocks[i][j+1] = tmp;
	    				i = N;
	    				j = N;
	    			}
	    		}
	    	}

	    	return new Board(blocks);
	    }
	    
	    public boolean equals(Object y){        // does this board equal y?
	    	if(y == this) return true;
	    	if(y == null) return false;
	    	if(!(y instanceof Board)) return false;
	    	
	    	Board that = (Board) y;
	    	
	    	if(that.board.length != board.length) return false;
	    		
	    	for(int i = 0; i<N; i++)
	    		for(int j = 0; j<N; j++)
	    			if(board[i][j] != that.board[i][j])
	    				return false;
	    	return true;
	    }
	    
	    public Iterable<Board> neighbors(){     // all neighboring boards
	    	return new BoardIterator();
	    }
	    
	    public String toString(){               // string representation of the board (in the output format specified below)
	    	    StringBuilder s = new StringBuilder();
	    	    s.append(N + "\n");
	    	    for (int i = 0; i < N; i++) {
	    	        for (int j = 0; j < N; j++) {
	    	            s.append(String.format("%2d ", board[i][j]));
	    	        }
	    	        s.append("\n");
	    	    }
	    	    return s.toString();
	    }
	    

	    		
		private class BoardIterator implements Iterable<Board>, Iterator<Board>{
			private Stack<Board> stack;
			
			public BoardIterator(){
				stack = new Stack<Board>();
				
				int[][] blocks;
				
				if(blankRow != 0){
					blocks = new int[N][N];
					for(int i = 0; i<N; i++)
						for(int j = 0; j<N; j++)
							blocks[i][j] = board[i][j];
									
					blocks[blankRow][blankColumn] = blocks[blankRow-1][blankColumn];
					blocks[blankRow-1][blankColumn] = 0;
					stack.push(new Board(blocks));
				}
				
				if(blankRow != N-1){
					blocks = new int[N][N];
					for(int i = 0; i<N; i++)
						for(int j = 0; j<N; j++)
							blocks[i][j] = board[i][j];
									
					blocks[blankRow][blankColumn] = blocks[blankRow+1][blankColumn];
					blocks[blankRow+1][blankColumn] = 0;
					stack.push(new Board(blocks));
				}
				
				if(blankColumn != 0){
					blocks = new int[N][N];
					for(int i = 0; i<N; i++)
						for(int j = 0; j<N; j++)
							blocks[i][j] = board[i][j];
									
					blocks[blankRow][blankColumn] = blocks[blankRow][blankColumn-1];
					blocks[blankRow][blankColumn-1] = 0;
					stack.push(new Board(blocks));
				}
				
				if(blankColumn != N-1){
					blocks = new int[N][N];
					for(int i = 0; i<N; i++)
						for(int j = 0; j<N; j++)
							blocks[i][j] = board[i][j];
									
					blocks[blankRow][blankColumn] = blocks[blankRow][blankColumn+1];
					blocks[blankRow][blankColumn+1] = 0;
					stack.push(new Board(blocks));
				}				
				
			}
			
			public boolean hasNext(){
				return !stack.isEmpty();
			}
			
			public void remove(){
				throw new java.lang.UnsupportedOperationException();
			}

			public Board next() {
				return stack.pop();
			}

			@Override
			public Iterator<Board> iterator() {
				return new BoardIterator();
			}
		}

		public static void main(String[] args) {
		    // create initial board from file
		    In in = new In(args[0]);
		    int N = in.readInt();
		    int[][] blocks = new int[N][N];
		    for (int i = 0; i < N; i++)
		        for (int j = 0; j < N; j++)
		            blocks[i][j] = in.readInt();
		    Board initial = new Board(blocks);

		    BoardIterator it = (BoardIterator) initial.neighbors();
		    
		    StdOut.println(initial.toString());
		    StdOut.println(it.next().toString());
		    StdOut.println(it.next().toString());		
		    
		    /*
		    // solve the puzzle
		    Solver solver = new Solver(initial);

		    // print solution to standard output
		    if (!solver.isSolvable())
		        StdOut.println("No solution possible");
		    else {
		        StdOut.println("Minimum number of moves = " + solver.moves());
		        for (Board board : solver.solution())
		            StdOut.println(board);
		    }
		    */
		}
}
