import java.io.*;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Iterator;
import java.lang.StringBuilder;

/*
 * commonFTMaker.java
 * @author Team Orange
 * @assignment: Check-In 3 (ICS 314 - SP15)
 */
public class commonFTMaker {
	
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
	 * 				-4 ERROR: icsFile missing an important event field(s) [TZID, DTSTART, DTEND]
	 */
	public int busySlots(String icsFile){
		
		String line = "";
		String eventName = "";
		String tz = "";
		String eventStartTime = "";
		String eventEndTime = "";
		String eventStartDate = "";
		String eventEndDate = "";
	    
	    String [] eventInfo = new String[2];
	    String [] eventTimes = new String[2];
	    
	    int start = 0;
	    int end = 0;
	    int success = 0;
	    int hours = 0;
	    int minutes = 0;
	    int seconds = 0;
	    int year = 0;
	    int month = 0;
	    int day = 0;
	    
		try{
			
			//Reads the content of the icsFile
			BufferedReader reader = new BufferedReader(new FileReader(icsFile));
			
			while ((line = reader.readLine()) != null) {
    	    	
				if(line.contains("BEGIN:VEVENT")){
					
					eventName = "";
					
				}
				else if(line.contains("TZID") && success == 0){
    	        	
    	        	eventInfo = line.split(":", 2);
    	        	tz = eventInfo[1];
    	        	
    	        	//Establishes the time zone all events must be on (performed only once)
	        		if(stTz.equals("")){
    	        		
    	        		stTz = tz;
    	        			
	        		}
 	        		//Verifies valid TZID 
    	        	if(tz.length() == 0 || tz.contains(" ") || !tz.contains("/") || !stTz.equals(tz)){
	        			
	        			success = -2;
	        			
	        		}
    	        	
    	        }
				
				else if(line.contains("DTSTART") && success == 0){
    	        	
    	        	eventInfo = line.split(":", 2);
    	        	eventTimes = eventInfo[1].split("T", 2);
    	        	eventStartTime = eventTimes[1];
    	        	eventStartDate = eventTimes[0];
    	        	
    	        	try{
    	        		
    	        		start = Integer.parseInt(eventStartTime);
    	        		
    	        	}
    	        	catch(NumberFormatException b){
    	        		
    	        		success = -3;
    	        		
    	        	}
    	        	
    	        	//Establishes the date all events must be on (performed only once)
    	        	if(stDate.equals("")){
    	        		
    	        		stDate = eventStartDate;
    	        		
    	        	}
    	        	//Verifies valid DTSTART field
    	        	if(start < 0 || start >= 240000|| eventStartTime.length() != 6 || !stDate.equals(eventStartDate) || eventStartDate.length() != 8 || !eventInfo[1].contains("T")){
    	        		
    	        		success = -3;
    	        		
    	        	}
    	        	else{
    	        		
    	        		//Date
    	        		year = Integer.parseInt(eventStartDate.substring(0, 4));
    	        		month = Integer.parseInt(eventStartDate.substring(4, 6));
    	        		day = Integer.parseInt(eventStartDate.substring(6, 8));
    	        		
    	        		//Invalid Date
    	        		if(year < 2015 || year > 9999 || month > 12 || month < 1 || (month == 2 && day > 28) || day > 31 || day < 1){
    	        			
    	        				success = -3;
    	       
    	        		}
    	        		
    	        		//Time
    	        		hours = Integer.parseInt(eventStartTime.substring(0, 2));
    	        		minutes = Integer.parseInt(eventStartTime.substring(2, 4));
    	        		seconds = Integer.parseInt(eventStartTime.substring(4, 6));
    	        		
    	        		//Invalid Start Time
    	        		if(hours > 23 || minutes > 59 || seconds > 59){
    	        			
    	        			success = -3;
    	        			
    	        		}
    	        		
    	        	}
    	        		  	
    	        }
    	        else if(line.contains("DTEND") && (success == 0 || success == -3)){
    	        	
    	        	eventInfo = line.split(":", 2);
    	        	eventTimes = eventInfo[1].split("T", 2);
    	        	eventEndTime = eventTimes[1];
    	        	eventEndDate = eventTimes[0];
    	        	
    	        	try{
    	        		
    	        		end = Integer.parseInt(eventEndTime);
    	        		
    	        	}
    	        	catch(NumberFormatException b){
    	        		
    	        		success = -3;
    	        		
    	        	}
    	        	
    	        	//Establishes the date all events must be on (performed only once)
    	        	if(stDate.equals("")){
    	        		
    	        		stDate = eventEndDate;
    	        		
    	        	}
    	        	//Verifies valid DTEND field
    	        	if(end == 0 || end >= 240000|| eventEndTime.length() != 6 || !stDate.equals(eventEndDate) || eventEndDate.length() != 8 || !eventInfo[1].contains("T")){
    	        		
    	        		success = -3;
    	        		
    	        	}
    	        	else{
    	        		
    	        		hours = Integer.parseInt(eventEndTime.substring(0, 2));
    	        		minutes = Integer.parseInt(eventEndTime.substring(2, 4));
    	        		seconds = Integer.parseInt(eventEndTime.substring(4, 6));
    	        		
    	        		//Invalid End Time
    	        		if(hours > 23 || minutes > 59 || seconds > 59){
    	        			
    	        			success = -3;
    	        			
    	        		}
    	        		
    	        	}
    	        	
    	        }
    	        else if(line.contains("LOCATION") && local.equals("")){
    	        	
    	        	eventInfo = line.split(":", 2);
    	        	
    	        	//Sets location that all Common Free Time events will take place in (performed only once)
    	        	if(local.equals("")){
    	        		
    	        		local = eventInfo[1];
    	        		
    	        	}
    	        	
    	        }
    	        else if(line.contains("SUMMARY")){
    	        	
    	        	eventInfo = line.split(":", 2);
					eventName = eventInfo[1];
					
    	        }
    	        
				if(success == 0 && line.equals("END:VEVENT")){
					
					//Inputs valid busy times into the TreeMap that records time slots that can't be filled by free time events
	    	        if(start >= 0 && end < 240000 && start < end){
	    	        	
	    	        	if(busyTimes.containsKey(start)){
	    	        		
	    	        		if(busyTimes.get(start) < end){
	    	        			
	    	        			//System.out.println("BT Put: " + start + ", " + end);
	    	        			busyTimes.put(start, end);
	    	        			
	    	        		}
	    	        		
	    	        	}
	    	        	else{
	    	        		//System.out.println("BT Put: " + start + ", " + end);
	    	        		busyTimes.put(start, end);
	    	        		
	    	        	}
	    	        	
	    	        	start = 0;
	    	        	end = 0;
	    	        	
	    	        }
	    	        
				}
				//An error (-2, -3) has been found in a calendar event in the icsFile
				else if(success < -1 && line.equals("END:VEVENT")){
					
					System.out.print("ERROR [busySlots(" + icsFile + ")]: ");
					
					switch(success){
					
						case -2: System.out.println("The event, " + eventName + ", has an invalid time zone for calendar events.");
								 System.out.println("All events' time zone must have no spaces, have a '/', and must all be the same.");
								 System.out.println("Set Time Zone for calendar events: " + stTz);
								 System.out.println("Event " + eventName + "'s time zone: " + tz);
								 break;
						case -3: System.out.println("The event, " + eventName + ", has an invalid start/end time.");
								 System.out.println("All events' start/end time:");
								 System.out.println(". start date & time must be before its end date & time");
								 System.out.println(". date of events must all be the same and a valid date");
								 System.out.println(". times must be positive whole 6-digit # >= 000000 & < 240000.");
								 System.out.println("Set Date for calendar events: " + stDate);
								 System.out.println("Event " + eventName + "'s Start: " + eventStartDate + "T" + eventStartTime);
								 System.out.println("Event " + eventName + "'s End: " + eventEndDate + "T" + eventEndTime);
								 break;
						default: break;
							
					}
					
					//Error in icsFile - stop all operations
					break;

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
		
		maxStart = 0;
		maxEnd = 0;
		
		int startofDay = 000000;
		int success = 0;
		
		if(busyTimes.size() == 0 || allICSFilesRead == 0){
			
			System.out.println("ERROR [ftSlots()]: Busy time slots haven't been created and/or all ics file arguments haven't been processed.");
			success = -1;
			
		}
		else{
			
			//Inputs free time slots into the freeTimes TreeMap that will be used to create free time events in the ftEvent() method
			for(Map.Entry<Integer, Integer> bt : busyTimes.entrySet()){
				
				if(maxStart == 0 && maxEnd == 0){
					
					maxStart = bt.getKey();
					maxEnd = bt.getValue();
					//System.out.println("Max Start: " + maxStart);
					if(maxStart > 0){
						
						freeTimes.put(startofDay, maxStart);
						
					}
					
				}
				else{
					
					if(bt.getKey() > maxEnd){
						
						//System.out.println("Put: " + maxEnd + ", " + bt.getKey());
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
				
				freeTimes.put(maxEnd, 235959);
				
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
					
					if(Integer.parseInt(start.toString()) < 100000 && b == start.length()){
						
						start.insert(0, "0");
						
					}
					else{
						
						start.append("0");
						
					}
					
				}
				
			}
			
			if(end.length() < 6){
				
				for(int c = end.length(); c < 6; c++){
					
					if(Integer.parseInt(end.toString()) < 100000 && c == end.length()){
						
						end.insert(0, "0");
						
					}
					else{
						
						end.append("0");
						
					}
					
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
		    pw.println("SUMMARY:POSSIBLE MEETING TIME");
		    pw.println("END:VEVENT");
		    
		}
	    
		return success;
		
	}
	
	/**
	 * Displays Busy Times & Common Free Times based on given .ics file arguments given at program's initialization
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
		
		System.out.println("Common Free Times");
		for(Map.Entry<Integer, Integer> ftest : freeTimes.entrySet()){
			
			System.out.println("Start: " + ftest.getKey());
			System.out.println("End: " + ftest.getValue());
			System.out.println("-------------------------");
			
		}
		
		System.out.println("Common Free Times: " + freeTimes.size());
		
		
	}
	
	/**
	 * Creates the commonFT.ics file (Common Free Time Events) around the given icsFiles 
	 * @param icsFiles (HashSet<String>) - a hashset of ics file names/paths
	 * @return success (int) 
	 * 				0 - commonFT.ics file successfully created (all events meet Check-In 2 requirements)
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
				//testFreeTime();
				
				//freeTimes TreeMap was successfully created
				if(ftSuccess == 0){
					
					File file = new File("commonFT.ICS");
				    PrintWriter printWriter = new PrintWriter(file);
				    
				    printWriter.println("BEGIN:VCALENDAR");

					for(Map.Entry<Integer, Integer> ft : freeTimes.entrySet()){
						
						ftEvent(printWriter, ft.getKey(), ft.getValue());
						
					}
					
					printWriter.println("END:VCALENDAR"); 
					printWriter.close();
					System.out.println("Created commonFT.ICS file");
					
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
