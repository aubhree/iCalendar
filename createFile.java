import java.io.*;
import javax.swing.*;
import java.util.TimeZone;
//import java.lang.NumberFormatException;
 
/**
* createFile.java
*
* @author Team Orange
*/

public class createFile {
  
  /**
  *  Creates new File called "ourCalendar" with extension ICS.
  *  Calls methods that retrieve information from user
  *  and writes to ourCalendar.ICS creating an iCalendar file
  *  to be read by a calendar (e.g. google calendar).
  * 
  *  @param  numEvent (int)
  *  @throws FileNotFoundException 
  *  @return int tracks amount of events
  */
  
  public int printToFile(String uzone, int numEvent, File file, String fmtDate, PrintWriter printWriter, boolean start) throws FileNotFoundException {
    
	if(start){
		
		printWriter.println("BEGIN:VCALENDAR");
		
	}
  
    printWriter.println("VERSION:2.0");
    printWriter.println(classType());
    printWriter.println("BEGIN:VTIMEZONE");
    uzone = pfTZ(numEvent, printWriter, uzone);
    printWriter.println("END:VTIMEZONE");
    printWriter.println("BEGIN:VEVENT");
    
    if (numEvent == 1) {
      fmtDate = date();
    }
    
    printWriter.println(time(fmtDate));
    printWriter.println(location());
    printWriter.println(priority());
    printWriter.println(summary());
    printWriter.println("END:VEVENT");
    
    numEvent++;
    
    while (true) {
      
      int reply = JOptionPane.showConfirmDialog(null,  "Would you like to create more events for this day?", "Continue Option", JOptionPane.YES_NO_OPTION);
      if (reply == JOptionPane.YES_OPTION) {
        
    	  start = false;
        numEvent = printToFile(uzone, numEvent, file, fmtDate, printWriter, start);
        
        return numEvent;
      }
      
      else {
        JOptionPane.showMessageDialog(null, "Okay.");
        printWriter.println("END:VCALENDAR"); 
        printWriter.close();
        return 0;
        //System.exit(0);
        
      }
      
    }
    
  }
  
  /**
  *  Checks if first event entered. If it is first event, method call
  *  to zoneID(boolean). If event is greater than one, use same time zone.
  * 
  *  @param numEvent (int)
  *  @param printWriter (PrintWriter)
  *  @param uzone (String)
  *  @return String
  */
  
  public String pfTZ(int numEvent, PrintWriter printWriter, String uzone) {
    
    if (numEvent == 1) {
      
      uzone = zoneID(true);
      printWriter.println("TZID:" + uzone);
    }
    
    else {
      
      printWriter.println("TZID:" + uzone);
    }
    
    return uzone;
  }

  /**
  *  Takes class type input from the user e.g. PUBLIC, PRIVATE, or CONFIDENTIAL.
  * 
  *  @param None
  *  @return String
  */  
  
  public String classType() {
    
    String classification = "";
    
    while (!classification.equals("PUBLIC") && !classification.equals("PRIVATE") && !classification.equals("CONFIDENTIAL")) {
         
      classification = JOptionPane.showInputDialog("Classify your event (PUBLIC, PRIVATE, CONFIDENTIAL): ");
      
      try {
        
        classification = classification.toUpperCase();
      }
      
      catch (NullPointerException e) {
        
        classification = ""; 
        
        continue;
      }
      
      if (!classification.equals("PUBLIC") && !classification.equals("PRIVATE") && !classification.equals("CONFIDENTIAL")) {
        
        JOptionPane.showMessageDialog(null, "Invalid Option");
      }
      
      else {
            
        JOptionPane.showMessageDialog(null, "Class: " + classification);
      }
    }
      
    return ("CLASS:" + classification);  
  }
  
  /**
  *  Takes zone ID input from the user and converts to time zone in iCalendar standard.
  * 
  *  @param  auto (boolean)
  *  @return String 
  */
  
  public String zoneID(boolean auto) {
    
    String zone = "INVALID";
    
    if (auto) {
        
      //auto creates the time zone from local time zone
      TimeZone tZone = TimeZone.getDefault();
      JOptionPane.showMessageDialog(null, "Time Zone: " + tZone.getID());
      
      zone = tZone.getID();
      return (zone); 
    } 
      
    else { 
         
      //manual creation of time zone
      while (zone.equals("INVALID")) {
            
        zone = JOptionPane.showInputDialog("Enter your time zone (e.g. HST, PST, CST, EST): ");
        
        try {
               
          zone = zone.toUpperCase();
        }
          
        catch (NullPointerException e) {
            
          JOptionPane.showMessageDialog(null, "Invalid option");
          zone = "INVALID";
               
          continue;
        }
          
        switch (zone) {
          
          case "HST": zone = "Pacific/Honolulu"; break;
          case "PST": zone = "America/Los_Angeles"; break;
          case "CST": zone = "America/Chicago"; break;
          case "EST": zone = "America/New_York"; break;
          default:    zone = "INVALID"; break;
        }
            
        if (zone.equals("INVALID")) {
          
          JOptionPane.showMessageDialog(null, "Invalid option");
        }
          
        else {
             
          JOptionPane.showMessageDialog(null, "Time Zone: " + zone);
        }
      }
         
      return (zone);
    }
  }
  
  /**
   * Allows user to enter year, month, and day for event.
   * Checks for valid numbers and dates.
   * 
   * @param
   * @return String
   */
  
  public String date() {
    
    int temp;
    int tempDay;
    int size;
    
    String year, month, day, fmtDate = "";
    
    while (true) {
      
      try {
        year = JOptionPane.showInputDialog("Enter the Year (e.g. 2015): ");
        temp = Integer.parseInt(year);
      }
        
      catch (NumberFormatException e) {
            
        JOptionPane.showMessageDialog(null, "Invalid option");
          
        continue;
      }
      
      if ((temp >= 2015) && (temp <= 9999)) {
        
        break;
      }
      
      else {
        
        JOptionPane.showMessageDialog(null, "Enter a year between 2015 and 9999: ");
      }
    }
      
    while (true) {
         
      try {
            
        month = JOptionPane.showInputDialog("Enter the numerical Month (e.g. 01 - 12): ");
        
        temp = Integer.parseInt(month);
        
      }
        
      catch (NumberFormatException e) {
          
        JOptionPane.showMessageDialog(null, "Invalid option");
          
        continue;
        
      }
        
      if (temp >= 1 && temp <= 12) {
    
        size = month.length();
        
        //Must have 2 digits for day. e.g. 1st of the month is 01.
        if (size < 2) {
                  
            month = "0" + month;
        }
        
        break;
      }
        
      else {
            
        JOptionPane.showMessageDialog(null, "Invalid option");
      }
    }
      
    while (true) {
         
      try {
            
        day = JOptionPane.showInputDialog("Enter the Day (e.g. 01 - 31): ");
        
        tempDay = Integer.parseInt(day);
      }
        
      catch (NumberFormatException e) {
          
        JOptionPane.showMessageDialog(null, "Invalid option");
            
        continue;
      }
        
      if((((temp == 1) || (temp == 3) || (temp == 5) || (temp == 7) || (temp == 8) || (temp == 10) || (temp == 12))&& (tempDay  <= 31 && tempDay >= 1)) || (((temp == 4) || (temp == 6) || (temp == 9) || (temp == 11))&& (tempDay  <= 30 && tempDay >= 1)) || ((temp == 2) && (tempDay  <= 28 && tempDay >= 1))) {
            
        size = day.length();
        
        //Must have 2 digits for day. e.g. 1st of the month is 01.
        if (size < 2) {
                  
            day = "0" + day;
        }
      
        break;
      }
        
      else {
            
        JOptionPane.showMessageDialog(null, "Invalid option");
      }
    }
    
    fmtDate = (year + month + day);
    return fmtDate;
  }
  
  
  /**
  *  Allows user to enter Event Start and End Time.
  *  Checks for valid numbers.
  *  
  *  @param  none
  *  @return String 
  */
  
  public String time(String fmtDate) {
    
    int temp1, temp2;
    int size;
      
    String startTime, endTime, dateStart, dateEnd, fmtStartTime, fmtEndTime = "";
    String year = fmtDate.substring(0, 4);
    String month = fmtDate.substring(4, 6);
    String day = fmtDate.substring(6);
      
    JOptionPane.showMessageDialog(null, "Next up, enter Event Start Time: ");
      
    while (true) {
        
      try {
          
        startTime = JOptionPane.showInputDialog("Enter the Start Time (e.g. 0000 to 2359): ");
        temp1 = Integer.parseInt(startTime);
        }
        
      catch (NumberFormatException e) {
          
        JOptionPane.showMessageDialog(null, "Invalid option");
          
        continue;
      }
        
      if (temp1 >= 1 && temp1 <= 2359) {
        
        size = startTime.length();
        
        //Military Time has 4 digits. e.g. 0030 is 1230AM
        if (size < 4) {
          
          for (int i = 4; i > size; i--) {
            
            startTime = "0" + startTime;
          }
        }
        
        break;
      }
        
      else {
            
        JOptionPane.showMessageDialog(null, "Invalid option");
      }
    }
    
    while (true) {
      
      try {
            
        endTime = JOptionPane.showInputDialog("Enter the End Time (e.g. 0000 to 2359): ");
        temp2 = Integer.parseInt(endTime);
      }
        
      catch (NumberFormatException e) {
            
        JOptionPane.showMessageDialog(null, "Invalid option");
          
        continue;
      }
        
      if ((temp2 >= 1 && temp2 <= 2359) && (temp2 > temp1)) {
        
        size = endTime.length();
        
        for (int i = 4; i > size; i--) {
          
          endTime = "0" + endTime;
        }
        
        break;
      }
        
      else {
          
        JOptionPane.showMessageDialog(null, "Invalid option");
      }
    }
      
    dateStart = (month + "/" + day + "/" + year + " " + startTime);
    dateEnd = (month + "/" + day + "/" + year + " " + endTime);
    JOptionPane.showMessageDialog(null, "Event starts at: " + dateStart + ", Event ends at: " + dateEnd);
    fmtStartTime = (fmtDate + "T" + startTime + "00");
    fmtEndTime = (fmtDate + "T" + endTime + "00"); 
    
    return ("DTSTART:" + fmtStartTime + "\nDTEND:" + fmtEndTime);  
  }  
  
  /**
  *  Allows user to enter the location of the event.
  * 
  *  @return String  
  */
  
  public String location() {
    
    String location = JOptionPane.showInputDialog("Enter the Location: ");
    JOptionPane.showMessageDialog(null, "Location: " + location);
    
    return ("LOCATION:" + location);
  }
    
  /**
  *  Allows user to enter the priority of their event.
  *  Valid priority from 0-9 check. Zero is no priority. One is the highest
  *  priority. Nine is the lowest priority. Check RFC for details.
  *  
  *  @return String
  */
  
  public String priority() {
      
    int temp;
      
    String priority = "";
      
    while (true) {
        
      JOptionPane.showMessageDialog(null, "Enter the Event Priority (0 - 9)");
      priority = JOptionPane.showInputDialog("e.g. 0 (no priority), 1 (highest priority): ");
        
      try {
            
        temp = Integer.parseInt(priority);
      }
        
      catch (NumberFormatException e) {
            
        JOptionPane.showMessageDialog(null, "Invalid option");
          
        continue;
      }
        
      if (temp >= 0 && temp <= 9) {
        
        priority = priority.replace("0", "");
        
        JOptionPane.showMessageDialog(null, "Priority: " + priority);
          
        break;
      }
        
      else {
            
        JOptionPane.showMessageDialog(null, "Invalid option");
      }
    }
      
    return ("PRIORITY:" + priority);  
  }

  /**
  *  Allows user to enter a Summary of the event.
  * 
  *  @return String
  */
  public String summary() {

    String summary = JOptionPane.showInputDialog("Enter Summary of Event: ");
    JOptionPane.showMessageDialog(null, "Summary: " + summary);
    
    return ("SUMMARY:" + summary);
  }
}



