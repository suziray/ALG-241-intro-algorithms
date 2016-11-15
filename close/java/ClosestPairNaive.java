public class ClosestPairNaive {
    
    public final static double INF = java.lang.Double.POSITIVE_INFINITY;
    public static double minDist=INF;
    public static int p1 = (int) INF;
    public static int p2 = (int) INF;
    
    //
    // findClosestPair()
    //
    // Given a collection of nPoints points, find and ***print***
    //  * the closest pair of points
    //  * the distance between them
    // in the form "NAIVE (x1, y1) (x2, y2) distance"
    //
    
    // INPUTS:
    //  - points sorted in nondecreasing order by X coordinate
    //  - points sorted in nondecreasing order by Y coordinate
    //
    
    public static void findClosestPair(XYPoint points[], boolean print)
    {
    	int nPoints = points.length;						
    	
    	for(int i=0; i<nPoints-1; i++){						//compare distances of each pair of points
    		for(int j=i+1; j<nPoints; j++){					//and store the minimum distance and the 
    			double dist = points[i].dist(points[j]);	//corresponding points
    			if(dist<minDist){
    				minDist = dist;
    				p1=i;
    				p2=j;
    			}
    		}
    	}		
	
    	if (print){
    		System.out.println("NAIVE " + points[p1].toString() + " " + points[p2].toString() + " " + minDist);
    	}
    }
}
