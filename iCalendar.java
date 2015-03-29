import java.io.FileNotFoundException;
import javax.swing.JOptionPane;

/**
* iCalendar.java
*
* @author TeamOrange
*/

public class iCalendar {
  
  public static void main(String [] args) throws FileNotFoundException {
    
    int numEvent = 1;
    String uzone = "";
    String newEvent = "yes";

    createFile newFile = new createFile();
    
    while (newEvent.equals("yes")) {
      
      numEvent = newFile.printToFile(numEvent, uzone);  
      newEvent = JOptionPane.showInputDialog("Do you wish to enter in another event?: ");
      newEvent = newEvent.toLowerCase();
    }
  }
}

