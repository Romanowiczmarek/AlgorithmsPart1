
import java.lang.IllegalArgumentException;

public class PercolationStats {

	private double[] p;
	private int N,T;
	private Percolation perc;
	private double average;
	
	public PercolationStats(int N, int T){  // perform T independent computational experiments on an N-by-N grid
		if(N <= 0 || T <= 0) throw new IllegalArgumentException();
		p = new double[T];
		
		this.N = N;
		this.T = T;
		
		for(int i = 0 ; i < T ; i++){
			perc = new Percolation(N);
			int c = 0;
			while(!perc.percolates()){
				int a = StdRandom.uniform(N) + 1;
				int b = StdRandom.uniform(N) + 1;
				if(!perc.isOpen(a,b)){
					c++;
					perc.open(a, b);
				}
			}
			p[i] = c*1.0/(N*N);
		}
			
		average = mean();
	}
	
	public double mean(){  // sample mean of percolation threshold
		double m = 0.0;
		for(int i = 0 ; i < T ; i++){
			m+=p[i]/T;
		}
		return m;
	}
	
	public double stddev(){  // sample standard deviation of percolation threshold
		double result = 0.0;
		
		if(T == 1)
			return Double.NaN;
		
		for(int i = 0 ; i < T ; i++){
			result += (p[i]-average)*(p[i]-average);
		}
		
		return Math.sqrt(result/(T-1));
	}
	
	public static void main(String[] args) {
		int N = Integer.valueOf(args[0]);
		int T = Integer.valueOf(args[1]);
	
		
		PercolationStats percStats = new PercolationStats(N,T);
	
		double mean = percStats.mean();
		double stddev = percStats.stddev();
		
		StdOut.println("Mean = "+mean);
		StdOut.println("Stddev = "+stddev);
		StdOut.println("95% confidence interval: " + (mean - 1.96*stddev/(Math.sqrt(T))) + ", " + (mean - 1.96*stddev/(Math.sqrt(T))));
		StdIn.readChar();
		

	}

}
