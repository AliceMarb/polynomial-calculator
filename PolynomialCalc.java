
/**
 * PolynomialCalc is the bootstrapper of this program, for text input only. Asks the user to type their input. Also has a useful method getSequence which calls the
 * evaluator classes in order.
 *
 * @Alice
 * @12/6
 */
import java.util.Map;
import java.util.TreeMap;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
public class PolynomialCalc
{
    // instance variables - replace the example below with your own
    public static Map<Character,Polynomial> polyMap = new TreeMap<Character,Polynomial>();

    /**
     * getSequence()
     * @param polyString an input String, taken from user input, to be evaluated
     * @return a String output of the evaluation. If the user used an = sign, this will be repeated (e.g input: a = 3 +4, output: a =7); otherwise just the result of the evaluation is outputted a a string.
     */
    public static String getSequence(String polyString){
        PolyReader polyReader = new PolyReader(polyString);
        //System.out.println("READPOLY" + polyReader.getTokenList());
        //System.out.println("NAMEVARIABLE = " + PolyReader.getNameVariable());
        InfixToPostfix in = new InfixToPostfix(polyReader);
        //System.out.println("POSTFIX VALUE" + in.getOutput());
        PostfixEvaluator expression = new PostfixEvaluator(in);

        String outputString = "";
        if (PolyReader.getNameVariable() != null){
            expression.evaluatorToMap();
            outputString = PolyReader.getNameVariable() + " = " + PolynomialCalc.polyMap.get(PolyReader.getNameVariable().nonXVariable);
        } else {
            outputString += expression.getOutputPoly();
        }
        return outputString;
    }

    /**
     * Takes in the user input by typing it in, evaluates it and prints the result out to them. 
     * User will choose interactive mode (called usermode) or file mode and then proceed only in that mode for the rest of the method running.
     *
     * @param args user typing in whatever they please.
     */
    public static void main(String[] args)
    {
        System.out.println("Welcome to the Polynomial Calculator! Type EXIT to exit at any time.");
        boolean keepGoing = true;
        String lowerCaseString = "";
        while (keepGoing == true){
            
            if (lowerCaseString.equals("exit")){break;}
            System.out.println("If you want to type your polynomial expressions straight into the comman line, write: USERMODE");
            System.out.println("If you want to read a file containing your polynomial expressions, write: FILEMODE");
            System.out.println("Type back to choose a different mode.");

            PolynomialCalc p = new PolynomialCalc();
            Scanner s = new Scanner(System.in);
            String whichMode = s.next();
            lowerCaseString = whichMode.toLowerCase();

            while (!lowerCaseString.equals("exit")){
                switch (lowerCaseString){
                    case "usermode" :

                    System.out.println("You can write in polynomials in the form of ax^b. You can choose to just get the result, or save the result by writing a variable and =, e.g. a= 3x^2 + 2 ");
                    System.out.println("Remember, if you want to update a variable, you need to write '='. e.g. if c = 3 and I want to add 4 to 3 and have c store 7, I need to write c = c+ 4.");

                    while (true){
                        // get the user input and put into PolyReader
                        System.out.println("Enter your polynomial expression!");
                        Scanner sc = new Scanner(System.in);
                        String inputString = sc.nextLine();

                        String lCS = inputString.toLowerCase();

                        try{
                            if (lCS.equals("back")){
                                keepGoing = true;
                                break;
                            }
                            else if (lCS.equals("exit")){
                                lowerCaseString = "exit";
                                keepGoing = false;
                                break;
                            } else{
                                String q = getSequence(lCS);
                                System.out.println(q);
                            }
                        } catch (IllegalArgumentException e){
                            System.out.println(e.getMessage());
                            continue;
                        }
                    }
                    break;

                    case "filemode":
                    while (true){
                        // get the user input and put into PolyReader
                        System.out.println("Enter your file location or EXIT!!");
                        Scanner sc = new Scanner(System.in);
                        String fpath = s.next();
                        if (fpath.equals("exit")){
                            lowerCaseString = "exit";
                            keepGoing = false;
                            break;
                        } else if (fpath.equals("back")){
                            keepGoing = true;
                            break;
                        }

                        try{
                            //Reader r = new FileReader(fpath);
                            BufferedReader r = new BufferedReader(new FileReader(fpath));  
                            String line;
                            while ((line = r.readLine()) != null){ //there is still data in the file
                                System.out.println(getSequence(line));
                            }
                        } catch (FileNotFoundException e){
                            //throw new IllegalArgumentException("Sorry, file not found.");
                            System.out.println("Sorry, file not found.");
                            continue;
                        } catch (IOException e){
                            //throw new IllegalArgumentException("There has been an error reading your file. Please try again.");
                            System.out.println("There has been an error reading your file. Please try again.");
                            continue;
                        } catch (IllegalArgumentException e){
                            System.out.println(e.getMessage());
                            continue;
                        }
                    }
                    // what happens if there are exceptions?
                    break;

                    default:
                    System.out.println("Sorry! Not sure what you mean... try again.");
                    keepGoing = true;
                    break;
                }
                if (keepGoing == true){
                    break;
                }
            }
            if (lowerCaseString.equals("exit")){
                
                break;} else {
                System.out.println(lowerCaseString);
                }
        }
       
    }

}