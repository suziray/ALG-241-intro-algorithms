import java.util.ArrayList;

//
// PRIORITYQUEUE.JAVA
// A priority queue class supporting sundry operations needed for
// Dijkstra's algorithm.
//

class PriorityQueue<T> {
    
	ArrayList<Handle<T>> queue = new ArrayList<Handle<T>>();
    // constructor
    //
    public PriorityQueue()
    {
    	queue.add(null); // need a dummy handle so that real index starts with 1
    }
    
    // Return true iff the queue is empty.
    //
    public boolean isEmpty()
    {
    	return queue.size() < 2;
    }
    
    // Insert a pair (key, value) into the queue, and return
    // a Handle to this pair so that we can find it later in
    // constant time.
    //
    Handle<T> insert(int key, T value)
    {
		int p = queue.size();
    	Handle<T> h = new Handle<T>(key, value, p);
		queue.add(h);
		Handle<T> parent = queue.get((queue.size()-1)/2);
		while(parent!=null && h.key<parent.key){
			swap(h, parent);
			parent = queue.get(h.pos/2);
		}
		return h;
    }
    
    // Return the smallest key in the queue.
    //
    public int min()
    {
    	return queue.get(1).key;
    }
    
    // Extract the (key, value) pair associated with the smallest
    // key in the queue and return its "value" object.
    //
    public T extractMin()
    {
    	T min = queue.get(1).value;
    	queue.set(1, queue.get(queue.size()-1));
    	queue.get(1).pos = 1;
    	queue.remove(queue.size()-1);
    	heapify(queue, 1);
    	return min;
    }
    
    
    // Look at the (key, value) pair referenced by Handle h.
    // If that pair is no longer in the queue, or its key
    // is <= newkey, do nothing and return false.  Otherwise,
    // replace "key" by "newkey", fixup the queue, and return
    // true.
    //
    public boolean decreaseKey(Handle<T> h, int newkey)
    {
    	if(h.pos > 0 && newkey < h.key){
    		h.key = newkey;
    		
    		while(h.pos > 1 && h.key < queue.get((int) Math.floor(h.pos/2)).key){
    			swap(h, queue.get((int) Math.floor(h.pos/2)));
    		}
    		return true;
    	}
    	else return false;
    }
    
    
    // Get the key of the (key, value) pair associated with a 
    // given Handle. (This result is undefined if the handle no longer
    // refers to a pair in the queue.)
    //
    public int handleGetKey(Handle<T> h)
    {
    	return h.key;
    }

    // Get the value object of the (key, value) pair associated with a 
    // given Handle. (This result is undefined if the handle no longer
    // refers to a pair in the queue.)
    //
    public T handleGetValue(Handle<T> h)
    {
    	return h.value;
    }
    
    // Print every element of the queue in the order in which it appears
    // in the implementation (i.e. the array representing the heap).
    public String toString()
    {
    	String s = "";
    	for (int i = 1; i < queue.size(); i++) {
    	s += "(" + queue.get(i).key + ", " + queue.get(i).value.toString() + ")\n";
    	}
    	return s;
    }
    
    // Exchange the position of two Handles in the queue
    public void swap(Handle<T> h1, Handle<T> h2){
    	queue.set(h2.pos, h1);
    	queue.set(h1.pos, h2);
    	int tempPos = h2.pos;
    	h2.pos = h1.pos;
    	h1.pos = tempPos;

    }
    
    // Move the Handle at position p to the correct position in an otherwise correct heap que
    private void heapify(ArrayList<Handle<T>> que, int p){
    	if(p <= (que.size()-1)/2){
    		int l = p*2;
    		int r = l+1;
    		if(r<que.size()){
    			if(que.get(l).key > que.get(r).key) l = r;
    		}
    		if(que.get(l).key < que.get(p).key){
    			swap(que.get(l), que.get(p));
    			heapify(que, l);
    		}
    	}
    }
}
