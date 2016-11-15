// Handle class: the object used as elements of PriorityQueue to story keys (distances), values (vertices) and positions in the queue
public class Handle<T> {
	int key;
	T value;
	int pos;
	
    // constructor
    //
    public Handle(int k, T v, int p)
    {
    	key = k;
    	value = v;
    	pos = p;
    }
}
