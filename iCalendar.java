import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import javax.swing.JOptionPane;

/**
* iCalendar.java
*
* @author TeamOrange
*/

//java.io;

public class iCalendar {
  
  public static void main(String [] args) throws FileNotFoundException {

    int numEvent = 1;
    
    String uzone = "";
    String fmtDate = "";
    
    createFile newFile = new createFile();
    File file = new File("ourCalendar.ICS");
    PrintWriter printWriter = new PrintWriter(file);
      
    newFile.printToFile(uzone, numEvent, file, fmtDate, printWriter);  
  }
}


