//
// MAIN.JAVA
// Main driver code for CSE 241 Closest Pair Lab.
//
// WARNING: ANY CHANGES YOU MAKE TO THIS FILE WILL BE DISCARDED BY THE
// AUTO-GRADER!  Make sure your code works with the unmodified
// original driver before you turn it in.  (You may wish to modify
// your local copy to do the timing experiments requested by the lab.)

import java.util.*;

public class Main {

	static long seed = 87654321;

	public static void main(String args[])
	{
		PRNG prng = new PRNG(seed); // seed random number generator
		XYPoint points [];
		int nPoints = 0;
		String fileName;

		if (args.length >= 1)
		{
			System.out.println(args[0]);
			fileName = args[0];
		}
		else
		{
			System.out.println("Syntax: Main [ <filename> | @<numPoints> ]");
			return;
		}


		// A filename argument of the form '@x', where x is a non-negative
		// integer, allocates x random points.  Any other argument is
		// assumed to be a file from which points are read.

		if (fileName.charAt(0) != '@')
		{
			points = PointReader.readXYPoints(fileName);
			nPoints = points.length;
		}
		else
		{
			nPoints = Integer.parseInt(fileName.substring(1));
			points = null;
		}

		if (nPoints < 2)
		{
			System.err.println("ERROR: need at least two points");
			return;
		}

		if(points == null)
			points = genPointsAtRandom(nPoints, prng);
		// Generate a set of points if one was not read.
		// When timing, call genPointsAtRandom() as shown
		// each time you want a new set of points.

		// run the DC algorithm
		{
			XComparator lessThanX = new XComparator();
			YComparator lessThanY = new YComparator();

			/////////////////////////////////////////////////////////////////
			// DC CLOSEST-PAIR ALGORITHM STARTS HERE	

			double averageDC = 0;
			for(int i=0; i<120; i++){
				Date startTime = new Date();

				// Ensure sorting precondition for divide-and-conquer CP
				// algorithm.  NB: you should *not* have to call sort() in
				// your own code!

				// The algorithm expects two arrays containing the same points.
				XYPoint pointsByX [] = Arrays.copyOf(points, points.length);
				XYPoint pointsByY [] = Arrays.copyOf(points, points.length);

				Arrays.sort(pointsByX, lessThanX); // sort by x-coord
				Arrays.sort(pointsByY, lessThanY); // sort by y-coord

				ClosestPairDC.findClosestPair(pointsByX, pointsByY, false);

				Date endTime = new Date();




				// DC CLOSEST-PAIR ALGORITHM ENDS HERE
				/////////////////////////////////////////////////////////////////

				long elapsedTime = endTime.getTime() - startTime.getTime();
				averageDC += elapsedTime;
//				System.out.println("For n = " + points.length + 
//						", the elapsed time is " +
//						elapsedTime + " milliseconds.");
//				System.out.println("");

			}
			averageDC = (averageDC/120.0);
			System.out.println("DC average:" + averageDC);




		}


		// run the naive algorithm
		{
			double averageNaive = 0;
			for(int i=0; i<120; i++){
				Date startTime = new Date();

				ClosestPairNaive.findClosestPair(points, false);

				Date endTime = new Date();

				long elapsedTime = endTime.getTime() - startTime.getTime();
				averageNaive += elapsedTime;
			}
			averageNaive = averageNaive/120.0;

			System.out.println("Naive average:" + averageNaive);
			//			System.out.println("For n = " + points.length + 
			//					", the naive elapsed time is " +
			//					elapsedTime + " milliseconds.");
			//			System.out.println("");
		}	





	}


	//
	// genPointsAtRandom()
	// Generate an array of specified size containing
	// points with coordinates chosen at random, using
	// the specified random sequence generator.
	//

	static XYPoint[] genPointsAtRandom(int nPoints, 
			PRNG prng) 
	{
		XYPoint points[] = new XYPoint [nPoints];

		double x = 0.0;
		double y = 0.0;

		double step = Math.sqrt(nPoints);

		for (int j = 0; j < nPoints; j++) 
		{
			// jitter next point's X coordinate
			x += 10000.0 * (prng.nextDouble() - 0.5);

			// move the Y coordinate a random amount up,
			// while keeping it within limits [0 .. nPoints)
			y = (y + step * prng.nextDouble()) % nPoints;

			points[j] = new XYPoint((int) Math.round(x), 
					(int) Math.round(y));
		}

		return points;
	}
}
