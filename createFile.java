import java.io.*;
import javax.swing.*;

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
        printWriter.println(zoneID());
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
    
        String classification = JOptionPane.showInputDialog("Classify your event (PUBLIC, PRIVATE, CONFIDENTIAL: ");
        JOptionPane.showMessageDialog(null, "Class: " + classification );

        return ("CLASS:" + classification.toUpperCase());
        
    }
	
    /* Takes zone ID input from the user and converts to time zone in iCalendar standard */
    public String zoneID() 
    {
    
        String zone = JOptionPane.showInputDialog("Enter your time zone (e.g. HST, PST, CST, EST): ");
        JOptionPane.showMessageDialog(null, "Time Zone: " + zone);
        zone = zone.toUpperCase();

        switch(zone)
        {

            case "HST": zone = "Pacific/Honolulu"; break;
            case "PST": zone = "America/Los_Angeles"; break;
            case "CST": zone = "America/Chicago"; break;
            case "EST": zone = "America/New_York"; break;
            default:    zone = "INVALID"; break;            
			
        }
		
        return ("TZID:" + zone);
        
    }
	
    public String timeStart() 
    {
    
        JOptionPane.showMessageDialog(null, "Next up, enter Event Start Time: ");
        String year = JOptionPane.showInputDialog("Enter the Year (e.g. 2015): ");
		
        String month = JOptionPane.showInputDialog("Enter the numerical Month (e.g. 01 - 12): ");
		
        String day = JOptionPane.showInputDialog("Enter the Day (e.g. 01 - 31): ");

        String startTime = JOptionPane.showInputDialog("Enter the Start Time (e.g. 0000 to 2359): ");
		
        String date = (month + "/" + day + "/" + year + " " +  startTime);
        JOptionPane.showMessageDialog(null, "Event starts at: " + date);
		
        String fmtStartTime = (year + month + day + "T" + startTime + "00");
		
        return ("DTSTART:" + fmtStartTime);
        
    }
	
    public String timeEnd() 
    {
    
        JOptionPane.showMessageDialog(null, "Next up, enter Event End Time: ");	
        String year = JOptionPane.showInputDialog("Enter the Year (e.g. 2015): ");
		
        String month = JOptionPane.showInputDialog("Enter the numerical Month (e.g. 01 - 12): ");
		
        String day = JOptionPane.showInputDialog("Enter the Day (e.g. 01 - 31): ");

        String endTime = JOptionPane.showInputDialog("Enter the Start Time (e.g. 0000 to 2359): ");
		
        String date = (month + "/" + day + "/" + year + " " +  endTime);
        JOptionPane.showMessageDialog(null, "Event starts at: " + date);
		
        String fmtEndTime = (year + month + day + "T" + endTime + "00");
		
        return ("DTEND:" + fmtEndTime);
        
    }
	
    public String location()
    {
		
        String location = JOptionPane.showInputDialog("Enter the Location: ");
        JOptionPane.showMessageDialog(null, "Location: " + location);
		
        return ("LOCATION:" + location);
		
    }
	
    public String priority()
    {
		JOptionPane.showMessageDialog(null, "Enter the Event Priority (0 - 9)");
        String priority = JOptionPane.showInputDialog("e.g. 0 (no priority), 1 (highest priority): ");
        JOptionPane.showMessageDialog(null, "Priority: " + priority);
		
        return ("PRIORITY:" + priority);
		
    }

    public String summary()
    {

        String summary = JOptionPane.showInputDialog("Enter Summary of Event: ");
        JOptionPane.showMessageDialog(null, "Summary: " + summary);
		
        return ("SUMMARY:" + summary);
		
    }
}




