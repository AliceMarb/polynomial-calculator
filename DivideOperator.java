
/**
 * Write a description of class DivideOperator here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class DivideOperator extends Operator
{
    public static final char sign = '/';
    public static final int priority = 4;
    
    /**
     * Constructor for DivideOperator, with instance variables being initialized using Operator's constructor
     */
    public DivideOperator(){
        super(sign, priority);
    }
    
    /**
     * Applies the divide operator to the two input operands
     * @param op1 a Polynomial
     * @param op2 a Polynomial
     * @return Polynomial, the result of op1 divided by op2
     */
    
    public Polynomial operate(Polynomial op1, Polynomial op2){
        return op1.dividePoly(op2);
    }
}
