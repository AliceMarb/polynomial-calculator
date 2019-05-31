
/**
 * Represents a ) , included as an operator just as a useful way to represent in a token list.
 *
 * @Alice
 * @12/6
 */
public class RightParenOperator extends Operator
{
    // instance variables - replace the example below with your own
    

    public static final char sign = ')';
    public static final int priority = 0;
    
    /**
     * Constructor for RightParenOperator
     */
    public RightParenOperator(){
        super(sign, priority);
    }

    
}
