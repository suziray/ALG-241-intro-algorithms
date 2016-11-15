//
// MAIN.JAVA
// Main driver code for CSE 241 Introductory Lab
//
// WARNING: ANY CHANGES YOU MAKE TO THIS FILE WILL BE DISCARDED
// BY THE AUTO-GRADER!  Make sure your code works with the
// unmodified original driver before you turn it in.
//

public class Main {
    
    public static void main(String args[])
    {
	Integer limit;
	
	// one argument: limiting value for numbers to print
	if (args.length >= 1)
	    {
		limit = Integer.parseInt(args[0]);
	    }
	else
	    {
		System.out.println("Syntax: Main <maxValue>");
		return;
	    }
	
	MyThing thing = new MyThing();
	
	thing.count(limit);
    }
}
