


public class findUnion {

	public final static int MAX_SIZE = 1000;

	// BASE CLASS REPRESENTS QUICK FIND
	
	private int[] id; // it represents the to which group does the single object belong to - in the beginning to its own group
	
	public findUnion(int N){
		id = new int[N];
		for(int i = 0 ; i < N; i++)
			id[i] = i;
	}

	public boolean areConnected(int p, int q){
		return id[p] == id[q];
	}
	
	public void union(int p, int q){
		int idP = id[p];
		int idQ = id[q];
		
		for(int i = 0; i < id.length ; i++){
			if(id[i] == idP)
				id[i] = idQ;
		}
	}

	
	
	
	class quickUnion extends findUnion{
		
		//This time id array represents a parent of a given object
		
		public quickUnion(int N){
			super(N);
		}
		
		@Override
		public boolean areConnected(int p, int q){
			return root(p) == root(q);			
		}
		
		@Override
		public void union(int p, int q){
			id[root(p)] = root(q);
		}	
		
		private int root(int a){
			while(a != id[a])				
				a = id[a];
			return a;
		}
	}
	
	
	class weightedQuickUnion extends findUnion{
		private int[] size;
		
		//This time id array represents a parent of a given object
		//Size represents the size of the tree of a given root
		
		public weightedQuickUnion(int N){
			super(N);
			size = new int[N];
			for(int i = 0; i < size.length ; i++)
				size[i] = 1;
		}
		
		@Override
		public boolean areConnected(int p, int q){
			return root(p) == root(q);			
		}
		
		@Override
		public void union(int p, int q){
			int rootP = root(p);
			int rootQ = root(q);
			
			if(size[rootP] < size[rootQ]){
				id[rootP] = rootQ;
				size[rootQ] += size[rootP];
			}else
				id[rootQ] = rootP;
				size[rootP] += size[rootQ];
		}	
		
		private int root(int a){
			while(a != id[a])				
				a = id[a];
			return a;
		}
	}
	
	
	class improvedWeightedQuickUnion extends findUnion{
		private int[] size;
		
		//This time id array represents a parent of a given object
		//Size represents the size of the tree of a given root
		
		public improvedWeightedQuickUnion(int N){
			super(N);
			size = new int[N];
			for(int i = 0; i < size.length ; i++)
				size[i] = 1;
		}
		
		@Override
		public boolean areConnected(int p, int q){
			return root(p) == root(q);			
		}
		
		@Override
		public void union(int p, int q){
			int rootP = root(p);
			int rootQ = root(q);
			
			if(size[rootP] < size[rootQ]){
				id[rootP] = rootQ;
				size[rootQ] += size[rootP];
			}else
				id[rootQ] = rootP;
				size[rootP] += size[rootQ];
		}	
		
		private int root(int a){
			while(a != id[a]){				
				id[a] = id[id[a]];
				a = id[a];
			}
			return a;
		}
	
		// RECURRENT ROOT METHOD FLATTENING THE TREE TOTALLY
		
		private int recurrentRoot(int a){
			if(a != id[a]){
				id[a] = recurrentRoot(id[a]);
				return id[a];
			}else{
				return a;				
			}
		}
		
		
	}


}
