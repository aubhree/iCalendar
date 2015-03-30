import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JOptionPane;

/**
* iCalendar.java
*
* @author TeamOrange
*/

//java.io;

public class iCalendar {
  
  public static void main(String [] args) throws FileNotFoundException {

    String uzone = "";
    int numEvent = 1;
    String newEvent = "yes";
    String fmtDate = "";
    
    createFile newFile = new createFile();
    File file = new File("ourCalendar.ICS");
      
    newFile.printToFile(uzone, numEvent, newEvent, file, fmtDate);  
  }
}

