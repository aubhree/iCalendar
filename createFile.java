import java.io.*;
import javax.swing.*;
import java.util.TimeZone;
import java.lang.NumberFormatException;


/**
*This version adds error and null point checking, gives option for zoneID to generate automatically from currnet location
*Adds date checking for correct input
*Edited by Gavin
**/
public class createFile
{
  
    public void printToFile() throws FileNotFoundException
    {
	
        File file = new File("ourCalendar.ICS");
        PrintWriter printWriter = new PrintWriter(file);
    
        printWriter.println("BEGIN:VCALENDAR");
        printWriter.println("VERSION:2.0");
        printWriter.println(classType());
        printWriter.println("BEGIN:VTIMEZONE");
        printWriter.println(zoneID(false));
        printWriter.println("END:VTIMEZONE");
        printWriter.println("BEGIN:VEVENT");
        printWriter.println(timeStart());
        printWriter.println(timeEnd());
        printWriter.println(location());
        printWriter.println(priority());
        printWriter.println(summary());
        printWriter.println("END:VEVENT");
        printWriter.println("END:VCALENDAR");	
		
        printWriter.close();
    }

    /* Takes class type input from the user e.g. PUBLIC, PRIVATE, or CONFIDENTIAL */
    public String classType() 
    {
    
      String classification = "";
      while (!classification.equals ("PUBLIC") && !classification.equals ("PRIVATE") && !classification.equals ("CONFIDENTIAL")){
         classification = JOptionPane.showInputDialog("Classify your event (PUBLIC, PRIVATE, CONFIDENTIAL): ");
         try{
            classification = classification.toUpperCase();
         }catch (NullPointerException e){
            classification = ""; 
            continue;
         }
         if (!classification.equals ("PUBLIC") && !classification.equals ("PRIVATE") && !classification.equals ("CONFIDENTIAL")){
            JOptionPane.showMessageDialog(null, "Invalid Option" );
         }else{
            JOptionPane.showMessageDialog(null, "Class: " + classification );
         }
      }
      return ("CLASS:" + classification);
        
    }
	
    /* Takes zone ID input from the user and converts to time zone in iCalendar standard */
    public String zoneID(boolean auto) 
    {
      if (auto){
         //auto creates the time zone from local time zone
         TimeZone tZone = TimeZone.getDefault();
         JOptionPane.showMessageDialog(null, "Time Zone: " + tZone.getID());
         return ("TZID:" + tZone.getID());
      }else{
         //manual creation of time zone
         String zone = "INVALID";
      
         while (zone.equals ("INVALID")){
            zone = JOptionPane.showInputDialog("Enter your time zone (e.g. HST, PST, CST, EST): ");
            try{
               zone = zone.toUpperCase();
            }catch (NullPointerException e){
               JOptionPane.showMessageDialog(null, "Invalid option");
               zone = "INVALID";
               continue;
            }
            switch(zone)
            {
               case "HST": zone = "Pacific/Honolulu"; break;
               case "PST": zone = "America/Los_Angeles"; break;
               case "CST": zone = "America/Chicago"; break;
               case "EST": zone = "America/New_York"; break;
               default: zone = "INVALID"; break;
            }
            
            if (zone.equals ("INVALID")){
               JOptionPane.showMessageDialog(null, "Invalid option");
            }else{
               JOptionPane.showMessageDialog(null, "Time Zone: " + zone);
            }
         }
         return ("TZID:" + zone);
      }
   }
   //checks for valid numbers and dates
    public String timeStart() 
    {
    
        String year,month,day,startTime,date,fmtStartTime = "";
      int temp;
      int tempDay;
      
      JOptionPane.showMessageDialog(null, "Next up, enter Event Start Time: ");
      while (true){
         try{
            year = JOptionPane.showInputDialog("Enter the Year (e.g. 2015): ");
            temp = Integer.parseInt (year);
         }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Invalid option");
            continue;
         }
         break;
      }
      while (true){
         try{
            month = JOptionPane.showInputDialog("Enter the numerical Month (e.g. 01 - 12): ");
            temp = Integer.parseInt (month);
         }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Invalid option");
            continue;
         }
         if (temp >= 1 && temp <= 12){
            break;
         }else{
            JOptionPane.showMessageDialog(null, "Invalid option");
         }
      }
      while (true){
         try{
            day = JOptionPane.showInputDialog("Enter the Day (e.g. 01 - 31): ");
            tempDay = Integer.parseInt (day);
         }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Invalid option");
            continue;
         }
         if((((temp == 1) || (temp == 3) || (temp == 5) || (temp == 7) || (temp == 8) || (temp == 10) || (temp == 12))&& (tempDay  <= 31 && tempDay >= 1)) || (((temp == 4) || (temp == 6) || (temp == 9) || (temp == 11))&& (tempDay  <= 30 && tempDay >= 1)) || ((temp == 2) && (tempDay  <= 28 && tempDay >= 1))){
            break;
         }else{
            JOptionPane.showMessageDialog(null, "Invalid option");
         }
      }
      while (true){
         try{
            startTime = JOptionPane.showInputDialog("Enter the Start Time (e.g. 0000 to 2359): ");
            temp = Integer.parseInt (startTime);
         }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Invalid option");
            continue;
         }
         if (temp >= 1 && temp <= 2359){
            break;
         }else{
            JOptionPane.showMessageDialog(null, "Invalid option");
         }
      }
      date = (month + "/" + day + "/" + year + " " + startTime);
      JOptionPane.showMessageDialog(null, "Event starts at: " + date);
      fmtStartTime = (year + month + day + "T" + startTime + "00");
      return ("DTSTART:" + fmtStartTime);
        
    }
    //checks for valid numbers and dates
    public String timeEnd() 
    {
    
        String year,month,day,startTime,date,fmtEndTime = "";
      int temp;
      int tempDay;
      
      JOptionPane.showMessageDialog(null, "Next up, enter Event End Time: ");
      while (true){
         try{
            year = JOptionPane.showInputDialog("Enter the Year (e.g. 2015): ");
            temp = Integer.parseInt (year);
         }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Invalid option");
            continue;
         }
         break;
      }
      while (true){
         try{
            month = JOptionPane.showInputDialog("Enter the numerical Month (e.g. 01 - 12): ");
            temp = Integer.parseInt (month);
         }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Invalid option");
            continue;
         }
         if (temp >= 1 && temp <= 12){
            break;
         }else{
            JOptionPane.showMessageDialog(null, "Invalid option");
         }
      }
      while (true){
         try{
            day = JOptionPane.showInputDialog("Enter the Day (e.g. 01 - 31): ");
            tempDay = Integer.parseInt (day);
         }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Invalid option");
            continue;
         }
         if((((temp == 1) || (temp == 3) || (temp == 5) || (temp == 7) || (temp == 8) || (temp == 10) || (temp == 12))&& (tempDay  <= 31 && tempDay >= 1)) || (((temp == 4) || (temp == 6) || (temp == 9) || (temp == 11))&& (tempDay  <= 30 && tempDay >= 1)) || ((temp == 2) && (tempDay  <= 28 && tempDay >= 1))){
            break;
         }else{
            JOptionPane.showMessageDialog(null, "Invalid option");
         }
      }
      while (true){
         try{
            startTime = JOptionPane.showInputDialog("Enter the End Time (e.g. 0000 to 2359): ");
            temp = Integer.parseInt (startTime);
         }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Invalid option");
            continue;
         }
         if (temp >= 1 && temp <= 2359){
            break;
         }else{
            JOptionPane.showMessageDialog(null, "Invalid option");
         }
      }
      date = (month + "/" + day + "/" + year + " " + startTime);
      JOptionPane.showMessageDialog(null, "Event ends at: " + date);
      fmtEndTime = (year + month + day + "T" + startTime + "00");
      return ("DTEND:" + fmtEndTime);
        
    }
	
    public String location()
    {
		
        String location = JOptionPane.showInputDialog("Enter the Location: ");
        JOptionPane.showMessageDialog(null, "Location: " + location);
		
        return ("LOCATION:" + location);
		
    }
    //valid priority from 0-9 check
    public String priority()
    {
	String priority = "";
      int temp;
      
      while(true){
         JOptionPane.showMessageDialog(null, "Enter the Event Priority (0 - 9)");
         priority = JOptionPane.showInputDialog("e.g. 0 (no priority), 1 (highest priority): ");
         try{
            temp = Integer.parseInt (priority);
         }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Invalid option");
            continue;
         }
         if (temp >= 0 && temp <= 9){
            JOptionPane.showMessageDialog(null, "Priority: " + priority);
            break;
         }else{
            JOptionPane.showMessageDialog(null, "Invalid option");
         }
      }
      return ("PRIORITY:" + priority);	
    }

    public String summary()
    {

        String summary = JOptionPane.showInputDialog("Enter Summary of Event: ");
        JOptionPane.showMessageDialog(null, "Summary: " + summary);
		
        return ("SUMMARY:" + summary);
		
    }
}




