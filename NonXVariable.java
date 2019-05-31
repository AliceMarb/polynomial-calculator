
/**
 * A NonXVariable is any letter that is not x. This class can be used in token lists to represent variables, so that it can implement the useful Operand method of isOperand() for later evaluation.
 *
 * @Alice
 * @11/26
 */
public class NonXVariable implements Operand, Comparable
{
    // Instance variable
    public final char nonXVariable;

    /**
     * Constructor for NonXVariable that initializes the nonXVariable to the input character, or if c is not a valid input, initalizes nonXVariable to 0.
     */
    public NonXVariable(char c){
        if (isNonXVariable(c) == true){
            nonXVariable = c;  }
        else {
            nonXVariable = 0;
        }    
    }

    /**
     * Checks if this is char is a non x variable (a letter - character -  other than x).
     *
     * @param c a char 
     * @return  true if a non-x variable (letter), false otherwise
     */
    public static boolean isNonXVariable(char c) 
    {
        if ((Character.isLetter(c)) && (c != 'x'))
            return true;
        else {
            return false;
        }
    }

    /**
     * Outputs the nonXVariable field (a character) as a string..
     * @return  a String representation of the polynomial
     */
    public String toString(){
        return Character.toString(nonXVariable);
    }

    /**
     * Compares characters so that they can be arranged in a TreeMap alphabetically, 'a' first.
     * @param o any object o, but should be a NonXVariable.
     * @return  -1 if  o is an earlier letter than nonXVariable, 0 if they are the letter, or 1 if o is a later letter than nonXVariable. 
     */
    public int compareTo(Object o)
    {
        if(!(o instanceof NonXVariable))
            return 5;
        else{
            NonXVariable n = (NonXVariable) o;
            if (n.nonXVariable < nonXVariable){
                return -1;
            } else if (n.nonXVariable > nonXVariable){
                return 1;
            } else {
                return 0;
            }
        }
    }
}
