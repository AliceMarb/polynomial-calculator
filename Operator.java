
/**
 * This abstract class represents operators and provides 2 variables that all operators will share: their sign and their priority in the order of operations.
 * @Alice
 * @12/6
 */
public  abstract class Operator implements Token
{
    private char sign;
    private int priority;
    
    /**
     * Operator Constructor won't be instantiated since this is an abstract class but useful for the constructors of its subclasses.
     * @param sign the operator's sign i.e. '-', '+', '/', '*' 
     * @param priority the operator's priority in order of operations
     */
    public Operator(char sign, int priority){
        this.sign = sign;
        this.priority = priority;
    }
    
    /**
     * A getter method for operator's sign
     * @return  the sign of the operator
     */
    public char getSign(){
        return sign;
    }
    
    /**
     * A getter method for operator's priority i.e. its priority in order of operations
     * @return  operator's priority number. Division is highest at 4, then multiplication at 3, addition at 2, and subtraction at 1.
     */
    public int getPrio(){
        return priority;
    }

    /**
     * Represents the operator as a String
     * @return outputs the sign of the operator as a string
     */
    public String toString()
    {
        return "" + sign;
    }
    
    /**
     * Checks if the input character is an operator
     * @param c a character
     * @return  1 if c is the sign of an operator, 0 otherwise
     */
    public static char isOperator(char c){
            if ((c == '+')||(c == '-')||(c == '*')||(c == '/')||(c == '(')||(c == ')'))
              return 1;
            else{
               return 0;
            }   
    }
    
    /**
     * Returns the matching object of the correct class according to the character input 
     *
     * @param c a sign
     * @return  the matching operator
     */
    public static Operator outputCorrectOperator(char c){
        Operator s;
        switch(c){

            case '+':
            s = new AddOperator();
            break;
            case '-':
            s = new SubtractOperator();
            break;
            case '*':
            s = new MultiplyOperator();
            break;
            case '/':
            s = new DivideOperator();
            break;
            case '(':
            s = new LeftParenOperator();
            break;
            case ')':
            s = new RightParenOperator();
            break;
            default:
            s = null;
            break;   
        }
        return s;
    }
    
    /**
     * Applies the operator to the two input operands
     * @param op1 a Polynomial
     * @param op2 a Polynomial
     * @return just a stub here to be overriden by subclasses
     * 
     */
    
    public Polynomial operate(Polynomial op1, Polynomial op2){
        Polynomial p = new Polynomial();
        return p;
    }
    
    
    
}
