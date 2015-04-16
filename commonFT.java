import java.io.*;
import java.util.HashSet;

/*
 * commonFT.java
 * @author Team Orange
 * @assignment: Check-In 3 (ICS 314 - SP15)
 */
public class commonFT
{
  
	/*
	 * Explains how to use the freeTime code from the command line
	 * @param  none
	 * @return none
	 */
	public static void usage(){
		
		System.out.println("ERROR: Incorrect Initialization");
		System.out.println("This program requires at least 2 .ics files as arguments.");
		System.out.println("All .ics file arguments need to be on the same day and timezone.");
		System.out.println("Ex: java commonFT personA.ICS personB.ICS");
		
	}
	
    public static void main(String [] args) throws FileNotFoundException
    {
    
    	if(args.length < 2){
    		
    		usage();
    		
    	}
    	
    	else{
    		
    		//Creates a HashSet of multiple ics files
    		HashSet<String> icsFiles = new HashSet<String>();
    		
    		for(int i = 0; i < args.length; i++){
    			
    			System.out.println(args[i]);
    			icsFiles.add(args[i]);
    			
    		}
    		
    		commonFTMaker ftm = new commonFTMaker();
    		
    		System.out.println("Status: " + ftm.ftCreator(icsFiles));
    		
    	}
    	
    	
    }
    
}
