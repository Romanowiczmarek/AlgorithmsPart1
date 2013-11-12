
import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new ByOrder();       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
    	if(x != that.x && y != that.y)
    		return (double) (that.y - y)/(that.x - x);
    	else if(x == that.x && y == that.y)
    		return Double.NEGATIVE_INFINITY;
    	else if(y == that.y)
    		return +0.0;
    	else if(x == that.x)
    		return Double.POSITIVE_INFINITY;
    	
    	return 0.0d;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
    	if((y < that.y) || (y == that.y && x < that.x))
    		return -1;
    	else if( y == that.y && x == that.x)
    		return 0;
    	else
    		return 1;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
    
    private class ByOrder implements Comparator<Point>{

		@Override
		public int compare(Point o1, Point o2) {
			Point myPoint = new Point(x, y);
			
			if(myPoint.slopeTo(o1) < myPoint.slopeTo(o2))
				return -1;
			else if(myPoint.slopeTo(o1) > myPoint.slopeTo(o2))
				return 1;
			else			
				return 0;
		}
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }


}
