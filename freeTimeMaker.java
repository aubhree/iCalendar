import java.io.*;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Iterator;
import java.lang.StringBuilder;

/*
 * freeTimeMaker
 * 
 * @author Team Orange
 * @assignment: Check-In 2 (ICS 314 - SP15)
 */
public class freeTimeMaker {
	
	//TreeMaps are used b/c they insert elements in sorted order by keys (useful scheduling times)
	private TreeMap<Integer, Integer> busyTimes = new TreeMap<Integer, Integer>();
	private TreeMap<Integer, Integer> freeTimes = new TreeMap<Integer, Integer>();
	
	//Used to double check that all events of the ics files have the same date & same time zone (as specified in req. for Check-In 2)
	private String stDate = "";
	private String stTz = "";
	
	//Used to establish a common location for free time events
	private String local = "";
	
	private int allICSFilesRead = 0;
	private int maxStart = 0;
	private int maxEnd = 0;
	
	/*
	 * Processes all events in the specified .ics file and verifies that all events meet the req. for Check-In 2.  
	 * Adds valid event times to the record of busy times that can't be filled by free time events.
	 * 
	 * @param icsFile (String) filename of the ics file given
	 * @return success (int)
	 * 				 0 Successfully added busy time(s) to the TreeMap
	 * 				-1 ERROR: IOException occurred
	 * 				-2 ERROR: An event's time zone is different from the set time zone 
	 * 				-3 ERROR: An event's start and/or end time is invalid (different date from set date, negative #, start > end)
	 */
	public int busySlots(String icsFile){
		
		String line = "";
		String eventName = "";
		String tz = "";
		String eventStartTime = "";
		String eventEndTime = "";
		String eventDate = "";
		
	    
	    String [] eventInfo = new String[2];
	    String [] eventTimes = new String[2];
	    
	    int start = 0;
	    int end = 0;
	    
	    int success = 0;
	    
		try{
			
			//Reads the content of the icsFile
			BufferedReader reader = new BufferedReader(new FileReader(icsFile));
			
			while ((line = reader.readLine()) != null) {
    	    	
				if(line.contains("TZID")){
    	        	
    	        	eventInfo = line.split(":", 2);
    	        	tz = eventInfo[1];
    	        	
    	        	//Establishes the time zone all events must be on (performed only once)
    	        	if(stTz.equals("")){
    	        		
    	        		stTz = tz;
    	        		
    	        	}
    	        	else{
    	        		
    	        		//Verifies the time zone all events must be on
    	        		if(!stTz.equals(tz)){
    	        			
    	        			success = -2;
    	        			break;
    	        			
    	        		}
    	        		
    	        	}
    	        	
    	        }
				
				else if(line.contains("DTSTART")){
    	        	
    	        	eventInfo = line.split(":", 2);
    	        	eventTimes = eventInfo[1].split("T", 2);
    	        	eventStartTime = eventTimes[1];
    	        	eventDate = eventTimes[0];
    	        	
    	        	//Establishes the date all events must be on (performed only once)
    	        	if(stDate.equals("")){
    	        		
    	        		stDate = eventDate;
    	        		
    	        	}
    	        	else{
    	        		
    	        		//Verifies the date all events must be one
    	        		if(!stDate.equals(eventDate)){
    	        			
    	        			success = -3;
    	        			
    	        		}
    	        		
    	        	}
    	        	
    	        	try{
    	        		
    	        		start = Integer.parseInt(eventStartTime);
    	        		
    	        	}
    	        	catch(NumberFormatException b){
    	        		
    	        		success = -3;
    	        		
    	        	}
    	        	
    	        }
    	        else if(line.contains("DTEND")){
    	        	
    	        	eventInfo = line.split(":", 2);
    	        	eventTimes = eventInfo[1].split("T", 2);
    	        	eventEndTime = eventTimes[1];
    	        	eventDate = eventTimes[0];
    	        	
    	        	if(stDate.equals("")){
    	        		
    	        		//Establishes the date all events must be on (performed only once)
    	        		stDate = eventDate;
    	        		
    	        	}
    	        	else{
    	        		
    	        		//Verifies the date all events must be on
    	        		if(!stDate.equals(eventDate)){
    	        			
    	        			success = -3;
    	        			
    	        		}
    	        		
    	        	}
    	        	
    	        	try{
    	        		
    	        		end = Integer.parseInt(eventEndTime);
    	        		//Verifies that the start & end times are valid
        	        	if(start < 0 || end <= 0 || start > end || start > 240000 || end > 240000){
            	        	
            	        	success = -3;
            	        	
            	        }
        	        	
    	        	}
    	        	catch(NumberFormatException b){
    	        		
    	        		success = -3;
    	        		
    	        	}
    	        	
    	        }
    	        else if(line.contains("LOCATION") && local.equals("")){
    	        	
    	        	eventInfo = line.split(":", 2);
    	        	
    	        	//Sets location that all Free Time events will take place in (performed only once)
    	        	if(local.equals("")){
    	        		
    	        		local = eventInfo[1];
    	        		
    	        	}
    	        	
    	        }
    	        
				if(success == 0){
					
					//Inputs valid busy times into the TreeMap that records time slots that can't be filled by free time events
	    	        if(start >= 0 && end < 240000 && start < end){
	    	        	
	    	        	if(busyTimes.containsKey(start)){
	    	        		
	    	        		if(busyTimes.get(start) < end){
	    	        			
	    	        			busyTimes.put(start, end);
	    	        			
	    	        		}
	    	        		
	    	        	}
	    	        	else{
	    	        		
	    	        		busyTimes.put(start, end);
	    	        		
	    	        	}
	    	        	
	    	        	start = 0;
	    	        	end = 0;
	    	        	
	    	        }
	    	        
				}
				
				//An error (-2, -3) has been found in a calendar event
				else{
					
					if(line.contains("SUMMARY")){
						
						eventInfo = line.split(":", 2);
						eventName = eventInfo[1];
						System.out.print("ERROR [busySlots(" + icsFile + ")]: ");
						
						switch(success){
						
							case -2: System.out.print("The event, " + eventName + ", has a different time zone than the set time zone for calendar events.");
									 System.out.println("Set Time Zone for calendar events: " + stTz);
									 System.out.println("Event " + eventName + "'s time zone: " + tz);
									 break;
							case -3: System.out.print("The event, " + eventName + ", has an invalid start/end time.");
									 System.out.println("Set Date for calendar events: " + stDate);
									 System.out.println("An event's start date & time must be before its end date & time (times must be positive whole # > 0 & < 240000.");
									 System.out.println("Event " + eventName + "'s Start: " + eventDate + "T" + eventStartTime);
									 System.out.println("Event " + eventName + "'s End: " + eventDate + "T" + eventEndTime);
									 break;
							default: break;
							
						}
						break;
						
					}
				}
				
			}
			
			reader.close();
			
		}
		
		catch(IOException a){
			
			System.out.print("ERROR [busySlots(" + icsFile + ")]: ");
			System.err.format("%s%n", a);
			success = -1;
			
		}
		
		return success;

	}
	
	/*
	 * Adds to the record (TreeMap) of free times
	 * @param 
	 * @return success (int)
	 * 				 0 Successfully added a new free time slot
	 * 				-1 ERROR: Busy time slots haven't been created and/or all ics file arguments haven't been processed
	 */
	public int ftSlots(){
		
		int startofDay = 000000;
		maxStart = 0;
		maxEnd = 0;
		
		int success = 0;
		
		if(busyTimes.size() == 0 || allICSFilesRead == 0){
			
			System.out.println("ERROR [ftSlots()]: Busy time slots haven't been created and/or all ics file arguments haven't been processed.");
			success = -1;
			
		}
		else{
			
			//Inputs free time slots into the freeTimes TreeMap that will be used to create free time events in the ftEvent() method
			for(Map.Entry<Integer, Integer> bt : busyTimes.entrySet()){
				
				if(maxStart == 0){
					
					maxStart = bt.getKey();
					maxEnd = bt.getValue();
					
					freeTimes.put(startofDay, maxStart);
					
				}
				else{
					
					if(bt.getKey() > maxEnd){
						
						freeTimes.put(maxEnd, bt.getKey());
						maxStart = bt.getKey();
						maxEnd = bt.getValue();
						
					}
					else if(bt.getValue() > maxEnd){
						
						maxEnd = bt.getValue();
						
					}
					
				}
				
			}
			
			//Inputs a free time event into the TreeMap of time remaining in the day after the last busy event
			if(maxEnd < 235959){
				
				freeTimes.put(maxEnd, 235959 );
				
			}
			
		}
		
		maxStart = 0;
		maxEnd = 0;
		
		return success;
		
	}
	
	/*
	 * Writes a free time event to a .ics file using PrintWriter
	 * @param pw (PrintWriter) the PrintWriter object that will write to a blank .ics file for the free time event(s)
	 * @param startT (String)  the start time of the free time event
	 * @param endT (String)    the end time of the free time event
	 * @return success (int) 
	 * 				0 - free time event successfully written to the .ics file
	 * 			   -1 - free time's start time and/or end time is invalid (negative #, start > end) 
	 * 
	 */
	public int ftEvent (PrintWriter pw, int startT, int endT){
		
		int success = 0;
		StringBuilder start = new StringBuilder();
		StringBuilder end = new StringBuilder();
		
		start.insert(0, Integer.toString(startT));
		end.insert(0, Integer.toString(endT));
		
		if(startT < 0 || endT < 0 || startT > endT || endT >= 240000){
			
			System.out.println("ERROR [ftEvent(pw, startT, endT)]: Invalid free time start time and/or end time.");
			success = -1;
			
		}
		else{
			
			if(start.length() < 6){
				
				for(int b = start.length(); b < 6; b++){
					
					start.insert(0, "0");
					
				}
				
			}
			
			if(end.length() < 6){
				
				for(int c = end.length(); c < 6; c++){
					
					end.insert(0, "0");
					
				}
				
			}
			
			/*
			 * Writes a free time event using PrintWriter pw to .ics file
			 */
		    pw.println("VERSION:2.0");
		    pw.println("CLASS:PUBLIC");
		    pw.println("BEGIN:VTIMEZONE");
		    pw.println("TZID:" + stTz);
		    pw.println("END:VTIMEZONE");
		    pw.println("BEGIN:VEVENT");
		    pw.println("DTSTART:" + stDate + "T" + start.toString());
		    pw.println("DTEND:" + stDate + "T" + end.toString());
		    pw.println("LOCATION:" + local);
		    pw.println("PRIORITY:0");
		    pw.println("SUMMARY:FREE TIME");
		    pw.println("END:VEVENT");
		    
		}
	    
		return success;
		
	}
	
	/**
	 * Displays Busy Times & Free Times based on given .ics file arguments given at program's initialization
	 * @param 
	 * @return 
	 */
	private void testFreeTime(){
		
		System.out.println("Busy Times");
		
		for(Map.Entry<Integer, Integer> btest : busyTimes.entrySet()){
			
			System.out.println("Start: " + btest.getKey());
			System.out.println("End: " + btest.getValue());
			System.out.println("-------------------------");
			
		}
		System.out.println("Busy Times: " + busyTimes.size());
		
		System.out.println("Free Times");
		for(Map.Entry<Integer, Integer> ftest : freeTimes.entrySet()){
			
			System.out.println("Start: " + ftest.getKey());
			System.out.println("End: " + ftest.getValue());
			System.out.println("-------------------------");
			
		}
		
		System.out.println("Free Times: " + freeTimes.size());
		
		
	}
	
	/**
	 * Creates the availableTimes.ics file (Free Time Events) around the given icsFiles 
	 * @param icsFiles (HashSet<String>) - a hashset of ics file names/paths
	 * @return success (int) 
	 * 				0 - availableTimes.ics file successfully created (all events meet Check-In 2 requirements)
	 * 			   -1 - no ics file names/paths were given as arguments
	 * @throws FileNotFoundException
	 */
	public int ftCreator(HashSet<String> icsFiles) throws FileNotFoundException{
		
		int busySuccess = 1;
		int ftSuccess = 1;
		int success = 0;
		allICSFilesRead = 0;
		
		if(icsFiles.size() <= 0){
			
			success = -1;
			
		}
		
		else{
			
			Iterator<String> icsfn = icsFiles.iterator();
			
			while(icsfn.hasNext()){
				
				busySuccess = busySlots(icsfn.next());
				
				if(busySuccess != 0){
					
					break;
					
				}
				
			}
			
			//busyTimes TreeMap was successfully created
			if(busySuccess == 0){
			
				allICSFilesRead = 1;
				ftSuccess = ftSlots();
				testFreeTime();
				
				//freeTimes TreeMap was successfully created
				if(ftSuccess == 0){
					
					File file = new File("availableTimes.ICS");
				    PrintWriter printWriter = new PrintWriter(file);
				    
				    printWriter.println("BEGIN:VCALENDAR");

					for(Map.Entry<Integer, Integer> ft : freeTimes.entrySet()){
						
						ftEvent(printWriter, ft.getKey(), ft.getValue());
						
					}
					
					printWriter.println("END:VCALENDAR"); 
					printWriter.close();
					System.out.println("Created availableTimes.ICS file");
					
				}
				else{
					
					success = ftSuccess;
					
				}
				
			}
			else{
				
				success = busySuccess;
				
			}
		
		}
		
		return success;
	    
	}
	
}
