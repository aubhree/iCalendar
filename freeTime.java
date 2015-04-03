import java.io.*;
import java.util.HashSet;

/*
 * freeTime
 * 
 * @author Team Orange
 * @assignment: Check-In 2 (ICS 314 - SP15)
 * 
 */
public class freeTime
{
  
	/*
	 * Explains how to use the freeTime code from the command line
	 * @param 
	 * @return
	 */
	public static void usage(){
		
		System.out.println("ERROR: Incorrect Initialization.");
		System.out.println("Ex: java freeTime ourCalendar.ICS sunday.ICS");
		
	}
	
    public static void main(String [] args) throws FileNotFoundException
    {
    
    	if(args.length < 1){
    		
    		usage();
    		
    	}
    	
    	else{
    		
    		//Creates a HashSet of multiple ics files
    		HashSet<String> icsFiles = new HashSet<String>();
    		
    		for(int i = 0; i < args.length; i++){
    			
    			System.out.println(args[i]);
    			icsFiles.add(args[i]);
    			
    		}
    		
    		freeTimeMaker ftm = new freeTimeMaker();
    		
    		System.out.println("Status: " + ftm.ftCreator(icsFiles));
    		
    	}
    	
    	
    }
    
}
