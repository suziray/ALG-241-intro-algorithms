//
// EVENTLIST.JAVA
// Skeleton code for your EventList collection type.
//
import java.util.*;

class EventList {

	Random randseq;
	public int usedHeight = 1;	// record of the highest level used so far
	Integer negInf = Integer.MIN_VALUE;
	Integer posInf = Integer.MAX_VALUE;
	Event head, tail;

	////////////////////////////////////////////////////////////////////
	// Here's a suitable geometric random number generator for choosing
	// pillar heights.  We use Java's ability to generate random booleans
	// to simulate coin flips.
	////////////////////////////////////////////////////////////////////

	int randomHeight()
	{
		int v = 1;
		while (randseq.nextBoolean()) { v++; }
		return v;
	}
	
	void updateHT(){	// Dynamicall Resize the Head and Tail pillars
		usedHeight = 2*usedHeight;
		Event [] headNext = new Event[usedHeight];
		for(int i=0; i<usedHeight/2; i++){
			headNext[i] =head.next[i];
		}
		for(int i=usedHeight/2; i<usedHeight; i++){
			headNext[i] =tail;
		}
		head.next = headNext;
	}


	//
	// Constructor with only head and tail
	//
	public EventList()
	{
		randseq = new Random(58243); // You may seed the PRNG however you like.
		head = new Event(negInf, null);	// initiate heads
		head.next = new Event[1];
		tail = new Event(posInf, null);	// initiate tails
		for(int i = 0; i<usedHeight; i++){
			head.next[i] = tail;	// build connection between head and tail
		}
	}


	//
	// Add an Event to the list.
	//
	public void insert(Event e)
	{
		e.h = randomHeight();	// randomly generate the height for event
		e.next = new Event[e.h];
		while(e.h > usedHeight) updateHT(); // update used height
		int l = e.h - 1;
		Event x = head;
		while(l >= 0){
			Event y = x.next[l];
			if(y.year < e.year) x = y;
			else {
				if(l < e.h){
					x.next[l] = e;
					e.next[l] = y;
				}
				l--;	//move one level down
			}
		}
	}


	//
	// Remove all Events in the list with the specified year.
	//
	public void remove(int year)
	{
		int l = usedHeight - 1;
		Event x = head;
		while(l >= 0 && x.next!=null){
			while(x.next[l].year < year){
				x = x.next[l]; // the last event before this year at l
			}
			Event temp = x.next[l];
			while(temp.year == year){
				temp = temp.next[l];
			}
			x.next[l] = temp; // reassign the pointers and thereby remove the events within this year at this level
			l--;
		}
	}

	//
	// Find all events with greatest year <= input year
	//
	public Event [] findMostRecent(int year)
	{	
		int l = usedHeight - 1;
		Event x = head;
		while(l >= 0){
			while(x.next[l].year < year){
				x = x.next[l]; // the last event in previous year
			}
			l--;
		}
		l = usedHeight - 1;
		Event x1 = head;
		while(l >= 0){
			while(x1.next[l].year < x.year){
				x1 = x1.next[l]; // the last event before previous year
			}
			l--;
		}
		if(x.year == negInf) return null; // no event occurred before/in this year
		if(x.next[0].year == year) x1 = x.next[0];
		else x1 = x1.next[0];
		int leng = 0;
		int gyear = x1.year;
		x = x1;
		while(x.year == gyear){ // find how many events happened in this/last year
			leng++;
			x = x.next[0];
		}
		Event[] recent = new Event[leng];
		for(int i=0; i<leng; i++){ // build the array for the most recent events
			recent[i] = x1;
			x1 = x1.next[0];
		}
		return recent;
	}


	//
	// Find all Events within the specific range of years (inclusive).
	//
	public Event [] findRange(int first, int last)
	{
		int l = usedHeight - 1;
		Event x = head;
		while(l >= 0){	
			while(x.next[l].year < first){
				x = x.next[l]; // find the latest event occurred before the first year
			}
			l--;
		}
		int leng = 0;
		Event y = x.next[0];
		while(y.year <= last){ 
			leng++;// find how many events happened within the range
			y = y.next[0]; // find the first event happened after the last year
		}
		if(leng == 0) return null;
		Event [] range = new Event[leng];
		for(int i = 0; i<leng; i++){ // build the array of events within the range in increasing date order
			x = x.next[0];
			range[i] = x;
		}
		return range;
	}
}
