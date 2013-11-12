import java.util.Iterator;

public class PointSET {
	private SET<Point2D> pointSet;
	
   public PointSET(){                               // construct an empty set of points
	   pointSet = new SET<Point2D>();
   }
   
   public boolean isEmpty(){                        // is the set empty?
	   return pointSet.isEmpty();
   }
   
   public int size(){                               // number of points in the set
	   return pointSet.size();
   }
   public void insert(Point2D p){                   // add the point p to the set (if it is not already in the set)
	   if(!contains(p))
		   pointSet.add(p);
   }
   
   public boolean contains(Point2D p){              // does the set contain the point p?
	   return pointSet.contains(p);
   }
   
   public void draw(){                              // draw all of the points to standard draw
	   Iterator<Point2D> it;
	   it = pointSet.iterator();
	   Point2D point;
	   
	   StdDraw.setPenColor(StdDraw.BLACK);
	   StdDraw.setPenRadius(0.01);
	   while(it.hasNext()){
		   point = it.next();
		   StdDraw.point(point.x(), point.y());
	   }
   }
   public Iterable<Point2D> range(RectHV rect){     // all points in the set that are inside the rectangle
	   Iterator<Point2D> it;
	   it = pointSet.iterator();
	   Point2D point;
	   Queue<Point2D> result = new Queue<Point2D>();
	   
	   while(it.hasNext()){
		   point = it.next();
		   if(rect.contains(point))
			   result.enqueue(point);
	   }
	   
	   return result;
   }
   public Point2D nearest(Point2D p){               // a nearest neighbor in the set to p; null if set is empty
	   Iterator<Point2D> it;
	   it = pointSet.iterator();
	   Point2D point, champion = null;
	   
	   while(it.hasNext()){
		   point = it.next();
		   if(champion == null || point.distanceTo(p)<champion.distanceTo(p))
			   champion = point;
	   }
	   return champion;
   }
}