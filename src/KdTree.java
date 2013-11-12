import java.util.Iterator;

public class KdTree {

	private Node root;
	private int size;
	
   public KdTree(){                               // construct an empty set of points
	   root = null;
	   size = 0;
   }
   
   public boolean isEmpty(){                        // is the set empty?
	   return root == null;
   }
   
   public int size(){                               // number of points in the set
	   return size;
   }
   
   
   public void insert(Point2D p){                   // add the point p to the set (if it is not already in the set)	   
	   put(p);
	   
	   return;
   }
   
   //Putting root
   private void put(Point2D query){
	   if(root == null){
		   root = new Node(query, new RectHV(0d, 0d, 1d, 1d));
		   size++;
   		}else{
		   Node current = root;
		   int level = 0; 
		   
		   while(current != null){
			   if(current.p.equals(query))
				   return;
			   
			   if(isLeftBottom(level, query, current.p)){
				   if(current.lb != null) current = current.lb;
				   else{
					   current.lb = new Node(query, getRectLB(level, current.rect, current.p));
					   size++;
					   return;
				   }
			   }else{
				   if(current.rt != null) current = current.rt;
				   else {
					   current.rt = new Node(query, getRectRT(level, current.rect, current.p));				   
					   size++;
					   return;
				   }
			   }
			   level++;
		   }   
	  }
   }
   
   private boolean isLeftBottom (int level, Point2D query, Point2D p){
	
	   if(level % 2 == 1){   // top/bottom
		   if(query.y() <= p.y())
			   return true;
		   else
			   return false;
	   }else if(level % 2 == 0){ //left/right
		   if(query.x() < p.x())
			   return true;
		   else
			   return false;
	   }
	   return false;
   }
   
   private RectHV getRectLB(int level, RectHV rect, Point2D p){
	   switch(level % 2){ 
	   case 1:	// top/bottom
		   return new RectHV(rect.xmin(), rect.ymin(),rect.xmax() , p.y());
	   case 0:	// left/right
		   return new RectHV(rect.xmin(), rect.ymin() , p.x() , rect.ymax());
	   }
	   return new RectHV(0d, 0d, 0d, 0d);
   }
   
   private RectHV getRectRT(int level, RectHV rect, Point2D p){
	   switch(level % 2){ 
	   case 1:	// top/bottom
		   return new RectHV(rect.xmin(), p.y(), rect.xmax() , rect.ymax());
	   case 0:	// left/right
		   return new RectHV(p.x(), rect.ymin(), rect.xmax() , rect.ymax());
	   }
	   return new RectHV(0d, 0d, 0d, 0d);
   }
   
   
   public boolean contains(Point2D query){              // does the set contain the point p?
	   Node current = root;
	   int level = 0;
	   
	   while(current != null){
		   
		   if(current.p.equals(query)) return true;
		   
		   if(isLeftBottom(level, query, current.p)){
			   current = current.lb;
		   }else{
			   current = current.rt;
		   }
		   level++;
	   }
	   
	   return false;
   }


   

   
   public void draw(){                              // draw all of the points to standard draw
	   drawPoint(root, 0);
   }
   
   private void drawPoint(Node x, int level){   
	   StdDraw.setPenColor(StdDraw.BLACK);
	   StdDraw.setPenRadius(0.01);
	   
	   if(x != null){
		   StdDraw.point(x.p.x(), x.p.y());
		   
		   StdDraw.setPenRadius(0.005);
		   StdDraw.setPenColor(StdDraw.BLUE);
		   //top/bottom
		   
		   if(level % 2 == 1) StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
		
		   
		   //left/right
		   StdDraw.setPenColor(StdDraw.RED);
		   if(level % 2 == 0) StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
	   }
	   
	   StdDraw.setPenColor(StdDraw.BLACK);
	   StdDraw.setPenRadius(0.01);
	   StdDraw.point(x.p.x(), x.p.y());
	   
	   if(x.lb != null) drawPoint(x.lb, level+1);
	   if(x.rt != null) drawPoint(x.rt, level+1);
   }
   
   public Iterable<Point2D> range(RectHV rect){     // all points in the set that are inside the rectangle
	   Queue<Point2D> pointsInRange = new Queue<Point2D>();
	   
	   findPointsInRange(rect, pointsInRange, root);
	   
	   return pointsInRange;
   }
   
   private void findPointsInRange(RectHV range, Queue<Point2D> q, Node node){
	   if(node == null)
		   return;
	   
	   if(range.contains(node.p))
		   q.enqueue(node.p);
	   
	   if(node.lb != null && node.lb.rect.intersects(range))
		   findPointsInRange(range, q, node.lb);
	   
	   if(node.rt != null && node.rt.rect.intersects(range))
		   findPointsInRange(range, q, node.rt);
	   
   }
   
   public Point2D nearest(Point2D p){               // a nearest neighbor in the set to p; null if set is empty
	   Point2D champion = null;
	   
	   champion = findNearest(root, champion, p, 0);
	   
	   return champion;
   }
   
   private Point2D findNearest(Node x, Point2D champ, Point2D p, int level){
	   if(x == null) return null;
	   
	   if(champ == null || x.p.distanceSquaredTo(p) < champ.distanceSquaredTo(p)) champ = x.p;

	   
	   if(isLeftBottom(level, p , x.p)){
		   if(x.lb != null) champ = findNearest(x.lb, champ, p, level+1);
		   if(x.rt != null && x.rt.rect.distanceSquaredTo(p) < champ.distanceSquaredTo(p)) champ = findNearest(x.rt, champ, p, level+1);
	   }else{
		   if(x.rt != null) champ = findNearest(x.rt, champ, p, level+1);
		   if(x.lb != null && x.lb.rect.distanceSquaredTo(p) < champ.distanceSquaredTo(p)) champ = findNearest(x.lb, champ, p, level+1);
	   }
	   
	   
	   /*
	   if(x.lb != null && x.lb.rect.distanceSquaredTo(p) < champ.distanceSquaredTo(p)) champ = findNearest(x.lb, champ, p);
	   if(x.rt != null && x.rt.rect.distanceSquaredTo(p) < champ.distanceSquaredTo(p)) champ = findNearest(x.rt, champ, p);
	   */
	   
	   return champ;
   }
   

   
   private static class Node {
	   private Point2D p;      // the point
	   private RectHV rect;    // the axis-aligned rectangle corresponding to this node
	   private Node lb;        // the left/bottom subtree
	   private Node rt;        // the right/top subtree
	
	   
	   public Node(Point2D p, RectHV rectangle){
		   this.p = p;
		   this.rect = rectangle;
		   lb = null;
		   rt = null;
	   }
   }
   
   
   public static void main(String[] args) {

       String filename = args[0];
       In in = new In(filename);


       StdDraw.show(0);

       // initialize the data structures with N points from standard input
       PointSET brute = new PointSET();
       KdTree kdtree = new KdTree();
       while (!in.isEmpty()) {
           double x = in.readDouble();
           double y = in.readDouble();
           Point2D p = new Point2D(x, y);
           kdtree.insert(p);
           brute.insert(p);
       }

       kdtree.draw();
       
       StdDraw.show(50);
       
       Queue<Point2D> q = (Queue<Point2D>) kdtree.range(new RectHV(0.0, 0.3, 0.0, 0.81));
       //StdOut.print("HAHA");
   }
}