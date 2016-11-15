public class MyThing {
    
    public void count(int x) 
    {
    	if(x!=0){
    		int i = 1;
    		if(x%2==0){
    			while(i<x-1){
    				System.out.println( i + "...");
    				i = i+2;
    			}	
    		}
    		else {
    			while(i<x){
    				System.out.println( i + "...");
    				i = i+2;
    			}
    		}
    		System.out.println(i + "!");
    	}
    }
}
