import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClosestPairDC {
    
    public final static double INF = java.lang.Double.POSITIVE_INFINITY;
    public static XYPoint p1;
    public static XYPoint p2;
    public static double minDist = INF;
    //
    // findClosestPair()
    //
    // Given a collection of nPoints points, find and ***print***
    //  * the closest pair of points
    //  * the distance between them
    // in the form "DC (x1, y1) (x2, y2) distance"
    //
    
    // INPUTS:
    //  - points sorted in nondecreasing order by X coordinate
    //  - points sorted in nondecreasing order by Y coordinate
    //
    
    public static void findClosestPair(XYPoint pointsByX[], XYPoint pointsByY[], boolean print)
    {
    	divideNConquer(pointsByX, pointsByY);
    	if (print)
    		System.out.println("DC " + p1.toString() + " "+ p2.toString() + " " + minDist);
    }
    
    public static void divideNConquer(XYPoint pointsByX[], XYPoint pointsByY[])
    {
    	int nPoints = pointsByX.length;
    	
    	if(nPoints==1){										//base case	1
    			return;
    			
    	}
    	if(nPoints==2){										//base case 2
    		if(pointsByX[0].dist(pointsByX[1])<minDist){
    			p1=pointsByX[0];
    			p2=pointsByX[1];
    			minDist = pointsByX[0].dist(pointsByX[1]);
    			return;
    		}
    	}
    	int mid = (int)Math.ceil(nPoints/2.0)-1;								//divide the array of points by X and Y
    	XYPoint[] xl = Arrays.copyOfRange(pointsByX, 0, mid+1); 
    	XYPoint[] xr = Arrays.copyOfRange(pointsByX, mid+1, nPoints); 
    	XYPoint[] yl = new XYPoint[mid+1];
    	int ylInd = 0;
    	XYPoint[] yr = new XYPoint[nPoints-mid-1];
    	int yrInd = 0;
    	for(int i=0; i<pointsByY.length; i++){
    		if(pointsByY[i].isLeftOf(pointsByX[mid])||pointsByY[i].isSame(pointsByX[mid])){
    			yl[ylInd] = pointsByY[i];
    			ylInd ++;
    		}
    		else {
    			yr[yrInd] = pointsByY[i];
    			yrInd ++;
    		}
    	}
    	
    	divideNConquer(xl, yl);			//merge the results
    	divideNConquer(xr, yr);
    	
    	List<XYPoint> yStrip = new ArrayList<XYPoint>();
    	for(int i=0; i<nPoints; i++)
    	{
    		if(Math.abs(pointsByX[mid].getX() - pointsByY[i].getX())<minDist)
    			yStrip.add(pointsByY[i]);
    	}
    	
    	for(int j = 0;j<yStrip.size()-1; j++){
    		int k = j + 1;
    		while(k<yStrip.size() && yStrip.get(k).getY()-yStrip.get(j).getY()<minDist){
    			if(yStrip.get(j).dist(yStrip.get(k))<minDist){
    				minDist = yStrip.get(j).dist(yStrip.get(k));
    				p1 = yStrip.get(j);
    				p2 = yStrip.get(k);
    			}
    			k++;
    		}
    	}
    	

    }
}
