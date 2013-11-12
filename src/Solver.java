import java.util.Iterator;




public class Solver {
    private Board searchBoard, previousBoard,searchBoardTwin, previousBoardTwin;
    private int moves = 0, movesTwin = 0;
	private boolean solved = false;
    private Queue<Board> shortestRoute;
	private Queue<Board> shortestRouteTwin;
	private Stack<Board> route;
	private MinPQ<SearchNode> minPQ, minPQTwin;
	
	public Solver(Board initial){            // find a solution to the initial board (using the A* algorithm)
		shortestRoute = new Queue<Board>();
		shortestRouteTwin = new Queue<Board>();
		
		route = new Stack<Board>();
		
		minPQ = new MinPQ<SearchNode>();
		minPQTwin = new MinPQ<SearchNode>();
		
		Iterator<Board> it;
		Board tmp;
		
		//previousBoard = null;
		searchBoard = null;
		minPQ.insert(new SearchNode(initial, moves, null));
		
		//previousBoardTwin = null;
		searchBoardTwin = null;
		minPQTwin.insert(new SearchNode(initial.twin(), movesTwin, null));
		
		while(!minPQ.min().itsBoard.isGoal() && !minPQTwin.min().itsBoard.isGoal()){
			
			//previousBoard = searchBoard;
			searchBoard = minPQ.min().itsBoard;
			moves = minPQ.min().moves;
			
			shortestRoute.enqueue(searchBoard);
			
			it = searchBoard.neighbors().iterator();
			
			while(it.hasNext()){
				tmp = it.next();
				if( minPQ.min().previous == null || !tmp.equals(minPQ.min().previous.itsBoard))
					minPQ.insert(new SearchNode(tmp, moves+1, minPQ.min()));
			}
			
			
			//previousBoardTwin = searchBoardTwin;
			searchBoardTwin = minPQTwin.min().itsBoard;
			movesTwin = minPQTwin.min().moves;
			
			shortestRouteTwin.enqueue(searchBoardTwin);
			
			it = searchBoardTwin.neighbors().iterator();
			
			while(it.hasNext()){
				tmp = it.next();
				if(minPQTwin.min().previous == null || !tmp.equals(minPQTwin.min().previous.itsBoard))
					minPQTwin.insert(new SearchNode(tmp, movesTwin+1, minPQTwin.min()));
			}
			minPQTwin.delMin();
			minPQ.delMin();
		}
		
		if(minPQ.min().itsBoard.isGoal()){
			solved = true;
			moves = minPQ.min().moves;
			//shortestRoute.enqueue(minPQ.delMin().itsBoard);
			SearchNode current = minPQ.min();
			
			route.push(current.itsBoard);
			while(current.previous != null){
				current = current.previous;
				route.push(current.itsBoard);
			}
				
			
		}else{
			solved = false;
		}
    }
    
	private class SearchNode implements Comparable<SearchNode>{
		private SearchNode previous = null;
		private Board itsBoard;
		private int moves;

		public SearchNode(Board a, int b, SearchNode c){
			this.itsBoard = a;
			this.moves = b;
			this.previous = c;
		}
				
		@Override
		public int compareTo(SearchNode that) {

			if(itsBoard.manhattan() + moves < that.itsBoard.manhattan() + that.moves) return -1;
			if(itsBoard.manhattan() + moves > that.itsBoard.manhattan() + that.moves) return +1;
			
			return 0;
		}
	}
	
    public boolean isSolvable(){            // is the initial board solvable?
    	return solved;
    }

    public int moves(){                      // min number of moves to solve initial board; -1 if no solution
    	if(solved)
    		return moves; 
    	else
    		return -1;
    }
    
    public Iterable<Board> solution(){       // sequence of boards in a shortest solution; null if no solution
    	if(solved)
    		return route; 
    	else
    		return null;
    }
    
    public static void main(String[] args){  // solve a slider puzzle (given below)
	    // create initial board from file
	    In in = new In(args[0]);
	    int N = in.readInt();
	    int[][] blocks = new int[N][N];
	    for (int i = 0; i < N; i++)
	        for (int j = 0; j < N; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);

	    
	    
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
    }

}
