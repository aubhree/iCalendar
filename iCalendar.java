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
  
  public iCalendar() {
    
  }
  

  public void createEvent(int i) throws FileNotFoundException {

    int numEvent = 1;
    
    boolean start = true;
    
    String uzone = "";
    String fmtDate = "";
        
    createFile newFile = new createFile();
    File file = new File("ourCalendar" + i + ".ICS");
    PrintWriter printWriter = new PrintWriter(file);
      
    newFile.printToFile(uzone, numEvent, file, fmtDate, printWriter, start);  

  }
}


