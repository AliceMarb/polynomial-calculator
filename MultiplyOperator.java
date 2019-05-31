
/**
 * A representation of * operator, with an order of operations priority of 3.
 *
 * @Alice 
 * @12/6
 */
public class MultiplyOperator extends Operator
{
    public static final char sign = '*';
    public static final int priority = 3;
    
    /**
     * Constructor for MultiplyOperator, with instance variables being initialized using Operator's constructor.
     */
    public MultiplyOperator(){
        super(sign, priority);
    }
    
   /**
     * Multiplies two input polynomials
     * @param op1 a Polynomial
     * @param op2 a Polynomial
     * @return Polynomial, the result of op1 multiplied by op2
     */
    public Polynomial operate(Polynomial op1, Polynomial op2){

        return op1.multiplyPoly(op2);
    
    }
}
