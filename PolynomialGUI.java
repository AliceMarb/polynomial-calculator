
/**
 * A graphical interface for the polynomial calculator
 *
 * @Alice 
 * @version 12/6
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
public class PolynomialGUI extends PolynomialCalc
{

    public static boolean userPressed = false;
    public static boolean filePressed = false;

    /**
     * Makes a window with 4 buttons: exit, user mode, file mode and evaluate!. When person clicks on user mode, they can type into the text area multiple lines and will be given the results
     * in the form of Line 1: ... Line 2: ...
     * Same with filemode. Once one f these is clicked, the person cannot change modes.
     */
    public static void main(String[] args)
     { 
        JFrame polyWindow = new JFrame("Polynomial Calculator");
        polyWindow.setLocationRelativeTo(null);
        polyWindow.setSize(500,500);

        JPanel content = new JPanel();
        polyWindow.setContentPane(content);

        JLabel introMsg = new JLabel("Welcome to the Polynomial Calculator!");
        content.add(introMsg);

        JLabel endLabel = new JLabel("Click User Mode or File Mode, type in, and then Evaluate!");
        content.add(endLabel);

        JButton exit = new JButton("Exit");
        content.add(exit);

        JButton usermode = new JButton("User Mode");
        content.add(usermode);

        JButton filemode = new JButton("File Mode");
        content.add(filemode);

        content.add(new JLabel("For User Mode, type polynomials in the form of ax^b into the textbox."));
        content.add(new JLabel("Save the result by writing a variable and =, e.g. a= 3x^2 + 2 "));
        content.add(new JLabel("For File Mode, type in the path of your file."));

        JTextArea polyWritingSpace = new JTextArea(10, 30);
        content.add(polyWritingSpace);

        JButton eval = new JButton("Evaluate!");
        content.add(eval); 

        // Exit button listener
        exit.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    System.exit(0); //exits the program
                }

            });

        usermode.addActionListener (new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    PolynomialGUI.userPressed = true;
                }

            });

        filemode.addActionListener (new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    PolynomialGUI.filePressed = true;
                }

            });


        eval.addActionListener (new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    if ((PolynomialGUI.userPressed == true)||(PolynomialGUI.filePressed == true)){
                        JFrame userWindow = new JFrame();
                        userWindow.setSize(900,900);
                        userWindow.setLocationRelativeTo(null);
                        userWindow.setVisible(true);
                        userWindow.toFront();

                        JPanel userContent = new JPanel();
                        String text = polyWritingSpace.getText();
                        String[] textArray = text.split("\\n");
                        String output = "";
                        if (PolynomialGUI.userPressed == true){
                            for (int i = 0; i < textArray.length; i++){
                                try {
                                    output += "Line " + (i + 1) + " : " + getSequence(textArray[i]) + "        ";
                                }
                                catch (IllegalArgumentException exception){
                                    output += "Line " + (i + 1) + " : " + exception.getMessage(); 
                                    break;
                                }
                            }

                            //userContent.setPreferredSize(content.getPreferredSize()); 

                            //userWindow.setPreferredSize(userWindow.getPreferredSize());

                            userContent.add(new JLabel("Results!"));
                            userContent.add(new JLabel("<html><pre>" + output + "\n</pre></html>"));
                            userWindow.setContentPane(userContent);
                        } else if (PolynomialGUI.filePressed == true){
                            try{
                                for (int i = 0; i < textArray.length; i++){

                                    BufferedReader r = new BufferedReader(new FileReader(textArray[i]));  
                                    String line;
                                    int j = 0;
                                    while ((line = r.readLine()) != null){//there is still data in the file
                                        output += "Line " + (j + 1) + " : " + getSequence(line) + "    ";
                                        j++;
                                    }
                                }
                                userContent.add(new JLabel("Results!"));
                            }
                            catch (FileNotFoundException f){
                                userContent.add(new JLabel("Sorry, file not found. Try writing the file path  in again!"));

                                // throw new IllegalArgumentException("Sorry, file not found.");
                                //System.out.println("Sorry, file not found.");

                            } catch (IOException g){
                                userContent.add(new JLabel("There has been an error reading your file. Please try again."));
                            } catch (IllegalArgumentException exception){
                                output += exception.getMessage(); 
                            }
                            userContent.add(new JLabel(output));
                        }
                        userWindow.setContentPane(userContent);
                    }
                }
            });
        polyWindow.setVisible(true);
        polyWindow.toFront();
    }
}

