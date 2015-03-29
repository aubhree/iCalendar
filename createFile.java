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
  
  public int printToFile(String uzone, int numEvent) throws FileNotFoundException {
    
    String newEvent = "yes";
    
    File file = new File("ourCalendar" + numEvent + ".ICS");
    PrintWriter printWriter = new PrintWriter(file);
    
    printWriter.println("BEGIN:VCALENDAR");
    printWriter.println("VERSION:2.0");
    printWriter.println(classType());
    printWriter.println("BEGIN:VTIMEZONE");
    uzone = pfTZ(numEvent, printWriter, uzone);
    //printWriter.println(zoneID(false));
    printWriter.println("END:VTIMEZONE");
    printWriter.println("BEGIN:VEVENT");
    printWriter.println(time());
    printWriter.println(location());
    printWriter.println(priority());
    printWriter.println(summary());
    printWriter.println("END:VEVENT");
    printWriter.println("END:VCALENDAR"); 
    
    printWriter.close();
    
    numEvent++;
    
    while (newEvent.equals("yes")) {
      
      newEvent = JOptionPane.showInputDialog("Do you wish to enter more events for this day?: ");
      newEvent = newEvent.toLowerCase();
      numEvent = printToFile(uzone, numEvent);   
    }
    
    return numEvent;
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
      
      uzone = zoneID(false);
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
    
    if (auto) {
        
      //auto creates the time zone from local time zone
      TimeZone tZone = TimeZone.getDefault();
      JOptionPane.showMessageDialog(null, "Time Zone: " + tZone.getID());
        
      return ("TZID:" + tZone.getID()); 
    } 
      
    else { 
         
      //manual creation of time zone
      String zone = "INVALID";
      
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
  *  Allows user to enter Event Start Time.
  *  Checks for valid numbers and dates.
  *  
  *  @param  none
  *  @return String 
  */
  
  public String time() {
    
    int temp, temp1, temp2;
    int tempDay;
      
    String year, month, day, startTime, endTime, dateStart, dateEnd, fmtStartTime, fmtEndTime = "";
      
    JOptionPane.showMessageDialog(null, "Next up, enter Event Start Time: ");
      
    while (true) {
         
      try {
        year = JOptionPane.showInputDialog("Enter the Year (e.g. 2015): ");
        temp = Integer.parseInt(year);
      }
        
      catch (NumberFormatException e) {
            
        JOptionPane.showMessageDialog(null, "Invalid option");
          
        continue;
      }
        
      break;
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
            
        break;
      }
        
      else {
            
        JOptionPane.showMessageDialog(null, "Invalid option");
      }
    }
      
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
            
        break;
      }
        
      else {
          
        JOptionPane.showMessageDialog(null, "Invalid option");
      }
    }
      
    dateStart = (month + "/" + day + "/" + year + " " + startTime);
    dateEnd = (month + "/" + day + "/" + year + " " + endTime);
    JOptionPane.showMessageDialog(null, "Event starts at: " + dateStart + ", Event ends at: " + dateEnd);
    fmtStartTime = (year + month + day + "T" + startTime + "00");
    fmtEndTime = (year + month + day + "T" + endTime + "00"); 
    
    return ("DTSTART:" + fmtStartTime + "\nDTEND:" + fmtStartTime);  
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


