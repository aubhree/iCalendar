import javax.swing.*;

public class HelloWorld 
{

  public static void main(String[] args) 
  {
    
    String name = JOptionPane.showInputDialog("Enter your name");
    JOptionPane.showMessageDialog(null, "Hello " + name + ", welcome to Java!");

  }

}
