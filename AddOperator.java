
/**
 * A representation of + operator, with an order of operations priority of 3.
 *
 * @Alice
 * @12/6
 */
public class AddOperator extends Operator
{
    public static final char sign = '+';
    public static final int priority = 2;
    
    /**
     * Constructor for AddOperator, with instance variables being initialized using Operator's constructor.
     */
    public AddOperator(){
        super(sign, priority);
    }
    
    /**
   *  Adds two input polynomials
     * @param op1 a Polynomial
     * @param op2 a Polynomial
     * @return Polynomial, the result of op1 add op1
     */
    
    public Polynomial operate(Polynomial op1, Polynomial op2){
        
        return op1.addPoly(op2);
    
    }
    
}
