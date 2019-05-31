
/**
 * A representation of - operator, with an order of operations priority of 1, the lowest.
 *
 * @Alice
 * @12/6
 */
public class SubtractOperator extends Operator
{
    public static final char sign = '-';
    public static final int priority = 1;

    /**
     * Constructor for Operator, with instance variables being initialized using Operator's constructor.
     */
    public SubtractOperator(){
        super(sign, priority);
    }

    /**
     * Subtracts one input polynomial from another
     * @param op1 a Polynomial
     * @param op2 a Polynomial
     * @return Polynomial, the result of op1 minus op1
     */
    public Polynomial operate(Polynomial op1, Polynomial op2){
        return op1.subtractPoly(op2);
    }
}
