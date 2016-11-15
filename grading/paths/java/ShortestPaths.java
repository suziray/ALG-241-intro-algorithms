import java.util.ArrayList;
import java.util.List;

//
// SHORTESTPATHS.JAVA
// Compute shortest paths in a graph.
//
// Your constructor should compute the actual shortest paths and
// maintain all the information needed to reconstruct them.  The
// returnPath() function should use this information to return the
// appropriate path of edge ID's from the start to the given end.
//
// Note that the start and end ID's should be mapped to vertices using
// the graph's get() function.
//
// You can ignore the input and startTime arguments to the constructor
// unless you are doing the extra credit.
//
class ShortestPaths {
	PriorityQueue<Integer> q;
	Multigraph G;
	ArrayList<Handle<Integer>> handles;

	//
	// constructor
	//
	public ShortestPaths(Multigraph G, int startId, 
			Input input, int startTime) 
	{
		// establish the priority queue and mark the start vertex
		this.G = G;
		q = new PriorityQueue<Integer>();
		handles = new ArrayList<Handle<Integer>>();
		for (int i = 0; i < this.G.nVertices(); i++) {
			this.G.get(i).parent = -1; // null parent vertex
			this.G.get(i).parentEdge = -1; // null parent edge
			handles.add(q.insert(Integer.MAX_VALUE, i));
		}
		q.decreaseKey(handles.get(startId), 0);

		// start to find shortest paths
		while (!q.isEmpty()) {
			int min = q.min();
			int u = q.extractMin();
			if (min == Integer.MAX_VALUE) {
				break;
			}
			Vertex.EdgeIterator t = this.G.get(u).adj();
			// check through all its adjacent vertices and try to decreaseKey
			while (t.hasNext()) {
				Edge e = t.next();
				int layover = 0; // assume no layover
				if(startTime != 0){ // if there is a layover effect of at least 45 minutes
					layover = 45;
					if (this.G.get(u).parentEdge == -1) { // if first flight
						layover += (input.flights[e.id()].startTime - startTime - 45 - 0 + 2880) % 1440; // as given by description
					} 
					else {
						layover += (input.flights[e.id()].startTime - input.flights[e.from().parentEdge].endTime - 45 + 2880) % 1440;
					}
				}	
				if (q.decreaseKey(handles.get(e.to().id()), min + e.weight() + layover)) {
					e.to().parent = u;
					e.to().parentEdge = e.id();
				}
			}

		}

	}
	//
	// returnPath()
	// Return an array containing a list of edge ID's forming
	// a shortest path from the start vertex to the specified
	// end vertex.
	//
	public int [] returnPath(int endId) 
	{ 
		ArrayList<Integer> shortest = new ArrayList<Integer>();
		Vertex helper = G.get(endId);
		while (helper.parent != -1) {
			shortest.add(helper.parentEdge);
			helper = G.get(helper.parent);
		}

		int[] path = new int[shortest.size()]; // size of 0 if null parent in the beginning
		for (int i = 0; i < shortest.size(); i++) {
			path[i] = shortest.get(shortest.size() - 1 - i);
		}
		return path;
	}
}
