import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import javax.swing.JOptionPane;

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
	/* public static void usage(){
		
		System.out.println("ERROR: Incorrect Initialization");
		System.out.println("This program requires at least 2 .ics files as arguments.");
		System.out.println("All .ics file arguments need to be on the same day and timezone.");
		System.out.println("Ex: java commonFT personA.ICS personB.ICS");
		
	} */
	
    public static void main(String [] args) throws FileNotFoundException
    {

      int menu = 0;
      int userInput = 0;
      int i = 0;
      int j = 0;
      int k = 0;
      int n = 1;  //number of ourCalendar files to create
      int reply = 0;
      ArrayList<String> inputA = new ArrayList<String>();
      ArrayList<String> inputB= new ArrayList<String>();
      
      while(menu != 4) {
        System.out.println("\nWelcome to iCalendar! Please choose an option below.\n");
        System.out.println("1. Create an event");
        System.out.println("2. Check available free time");
        System.out.println("3. Check available free time between two persons");
        System.out.println("4. Quit");
      
      Scanner input = new Scanner(System.in);
      userInput = input.nextInt();
      
    	/* if(args.length < 2){
    		
    		usage();
    		
    	}
    	
    	else{ */
      if (userInput == 1){
        
        iCalendar event = new iCalendar(); 
        event.createEvent(n);
        n++;
      }
      
      if (userInput == 2){
        
        do{
          inputA.add(JOptionPane.showInputDialog("Please enter the name of the files you wish to add."));
        
          reply = JOptionPane.showConfirmDialog(null,  "Would you like to add more events to compare?", "Continue Option", JOptionPane.YES_NO_OPTION);
          if (reply == JOptionPane.YES_OPTION){
            j++;
          }
        }
        
        while(reply == JOptionPane.YES_OPTION);
          
        //Creates a HashSet of multiple ics files
        HashSet<String> icsFiles = new HashSet<String>();
        
        for(i = 0; i < inputA.size(); i++){
          
          //System.out.println(inputA.size());
          icsFiles.add(inputA.get(i));
          
        }
        
        freeTimeMaker ftm = new freeTimeMaker();
        
        System.out.println("Status: " + ftm.ftCreator(icsFiles));
      }
      
      
      if(userInput == 3) {	
        
        do{
          inputB.add(JOptionPane.showInputDialog("Please enter the name of the files you wish to add."));
        
          reply = JOptionPane.showConfirmDialog(null,  "Would you like to add more events to compare?", "Continue Option", JOptionPane.YES_NO_OPTION);
          if (reply == JOptionPane.YES_OPTION){
            n++;
          }
        }
        
        while(reply == JOptionPane.YES_OPTION);
    		//Creates a HashSet of multiple ICS files
    		HashSet<String> icsFiles = new HashSet<String>();
    		
    		for(k = 0; k < inputA.size(); k++){
    			
    			icsFiles.add(inputB.get(k));
    			
    		}
    		
    		commonFTMaker ftm = new commonFTMaker();
    		
    		System.out.println("Status: " + ftm.ftCreator(icsFiles));
    		
    	}
    	
      if (userInput == 4) {
        System.out.println("Thank you for using iCalendar. Have a nice day!");
        System.exit(0);
      }
    }
      
      
   }
    
}
