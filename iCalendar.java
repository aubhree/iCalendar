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
    
    createFile newFile = new createFile();
      
    newFile.printToFile(uzone, numEvent);  
  }
}
