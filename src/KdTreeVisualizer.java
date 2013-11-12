/*************************************************************************
 *  Compilation:  javac KdTreeVisualizer.java
 *  Execution:    java KdTreeVisualizer
 *  Dependencies: StdDraw.java Point2D.java KdTree.java
 *
 *  Add the points that the user clicks in the standard draw window
 *  to a kd-tree and draw the resulting kd-tree.
 *
 *************************************************************************/

public class KdTreeVisualizer {

    public static void main(String[] args) {
        StdDraw.show(0);
 
        String filename = args[0];
        In in = new In(filename);


        StdDraw.show(0);

        /*
        // initialize the data structures with N points from standard input
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        
        kdtree.draw();
        
        StdDraw.show(50);
        */
        //Queue<Point2D> queue;
        
        KdTree kdtree = new KdTree();
        while (true) {
            if (StdDraw.mousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                System.out.printf("%8.6f %8.6f\n", x, y);
                Point2D p = new Point2D(x, y);
                kdtree.insert(p);
                StdDraw.clear();
                kdtree.draw();
            }
            //queue = (Queue<Point2D>) kdtree.range(new RectHV(0.1f, 0.1f, 0.5f, 0.5f));
            StdDraw.show(50);
            Point2D champ = kdtree.nearest(new Point2D(0.1f, 0.1f));
            
            if(champ != null);
            	//StdOut.print("Nearest: " + champ.toString()+"\n");
        }
        

    }
}
