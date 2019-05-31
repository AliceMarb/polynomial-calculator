
/**
 * An operand is something on which an operation can be done e.g. variables, numbers, monomials etc. This class implementing this interface are all examples of this.
 * Operand is an interface that allows its implementers, Monomial and NonXVariable, to be included in the token list in evaluations.
 * Also has the useful method isOperand() which can be used in stacks.
 *
 * @author Alice
 * @version 12/6
 */
public interface Operand extends Token
{
    /**
     * Checks if a token is an operand
     * @param token a token
     * @return true if the token is an operand, false otherwise
     */
    public static boolean isOperand(Token token)
    {
        if (token instanceof Operand){
            return true;
        } else {
            return false;
        }
}
    
}
