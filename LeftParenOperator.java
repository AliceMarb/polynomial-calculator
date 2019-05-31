
/**
 * Represents a ( , included as an operator just as a useful way to represent in a token list.
 *
 * @Alice
 * @12/6
 */
public class LeftParenOperator extends Operator
{
    public static final char sign = '(';
    public static final int priority = 0;
    
    /**
     * Constructor for LeftParenOperator
     */
    public LeftParenOperator(){
        super(sign, priority);
    }
}
