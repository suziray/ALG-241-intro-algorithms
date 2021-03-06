import java.util.ArrayList;

//
// STRINGTABLE.JAVA
// A hash table mapping Strings to their positions in the the pattern sequence
// You get to fill in the methods for this part.
//
public class StringTable {
	int tSize;
    double totalKeys;
    Record[] slot;
    int[] hashKeys; //the extra space to store the hash key for each key
    //
    // Create an empty table big enough to hold maxSize records.
    //
    public StringTable(int maxSize) 
    {
    	tSize = 2;
    	totalKeys = 0.0;
    	slot = new Record[tSize];
    	hashKeys = new int[tSize];
    }
    
    
    //
    // Insert a Record r into the table.  Return true if
    // successful, false if the table is full.  You shouldn't ever
    // get two insertions with the same key value, but you may
    // simply return false if this happens.
    //
    public boolean insert(Record r) 
    { 
    	// dynamic growing hash table
    	totalKeys ++;
		if(totalKeys/tSize >= .25){
			rehash();
		}
		
    	int h1 = baseHash(toHashKey(r.key));
    	int h2 = stepHash(toHashKey(r.key));
    	int ins = h1;
    	int counter = 0; //count how many slots have been taken
    	while(counter<tSize){
    		if(slot[ins]==null || slot[ins].key==""){
    			slot[ins] = r;
        		hashKeys[ins] = toHashKey(r.key);
        		return true;
    		}
    		else {
    			ins = (ins+h2)%tSize;
    			counter ++;
    		}	
    	}
    	return false;
    }
    
    public void rehash(){
    	
    	tSize = 2*tSize;
		Record[] tempSlot = new Record[tSize];
		int[] tempHashKeys = new int[tSize];
		for(int i=0; i<tSize/2; i++){
			if(slot[i]!=null && slot[i].key!=""){
				int h3 = baseHash(toHashKey(slot[i].key));
		    	int h4 = stepHash(toHashKey(slot[i].key));
		    	int reh = h3;
		    	while(tempSlot[reh]!=null){
		    		reh = (reh+h4)%tSize;
		    	}
		    	tempSlot[reh] = slot[i];
		    	tempHashKeys[reh] = toHashKey(slot[i].key);
			}
		}
		slot = tempSlot;
		hashKeys = tempHashKeys;
    }
    
    
    //
    // Delete a Record r from the table.  Note that you'll have to
    // find the record first unless you keep some extra information
    // in the Record structure.
    //
    public void remove(Record r) 
    {
    	if(r!=null){
    		int h1 = baseHash(toHashKey(r.key));
    		int h2 = stepHash(toHashKey(r.key));
    		int rev = h1;
    		int counter = 0;
    		while(slot[rev]!=null && counter<tSize){
    			if(hashKeys[rev]==toHashKey(r.key)){
    				if(slot[rev].key.equals(r.key)){
    					slot[rev] = new Record("");
    					hashKeys[rev] = -1;
    					totalKeys --;	
    					break;
    				}
    			}
    			else {
    				rev = (rev+h2)%tSize;
    			}
    			counter ++;
    		}
    	}
    }
    
    
    //
    // Find a record with a key matching the input.  Return the
    // record if it exists, or null if no matching record is found.
    //
    public Record find(String key) 
    {
    	int h1 = baseHash(toHashKey(key));
    	int h2 = stepHash(toHashKey(key));
    	int fin = h1;
    	int counter = 0;
    	while(slot[fin]!=null && counter<tSize){
    		if(hashKeys[fin]==toHashKey(key)){
    			if(slot[fin].key.equals(key))
    				return slot[fin];
    		}
    		else {
    			fin = (fin+h2)%tSize;
    		}
    		counter ++;
    	}
    	return null;
    }
    
    
    ///////////////////////////////////////////////////////////////////////
    
    
    // Convert a String key into an integer that serves as input to hash
    // functions.  This mapping is based on the idea of a linear-congruential
    // pesudorandom number generator, in which successive values r_i are 
    // generated by computing
    //    r_i = ( A * r_(i-1) + B ) mod M
    // A is a large prime number, while B is a small increment thrown in
    // so that we don't just compute successive powers of A mod M.
    //
    // We modify the above generator by perturbing each r_i, adding in
    // the ith character of the string and its offset, to alter the
    // pseudorandom sequence.
    //
    int toHashKey(String s)
    {
    	int A = 1952786893;
    	int B = 367257;
    	int v = B;

    	for (int j = 0; j < s.length(); j++)
    	{
    		char c = s.charAt(j);
    		v = A * (v + (int) c + j) + B;
    	}

    	if (v < 0) v = -v;
    	return v;
    }
    
    int baseHash(int hashKey)
    {
    	double a = (Math.sqrt(5)-1)/2;
		return (int) Math.floor(tSize*(hashKey*a-Math.floor(hashKey*a)));
    }
    
    int stepHash(int hashKey)
    {
    	double b = (Math.sqrt(7)-2)/3;
		return (int) Math.floor(tSize*(hashKey*b-Math.floor(hashKey*b)));
    }
}
