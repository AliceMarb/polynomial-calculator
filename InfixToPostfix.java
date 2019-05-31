
/**
 * Converts an ArrayDeque in infix form from PolyReader to postfix form  in order to evaluate the list according to the correct order of operations.
 *
 * @Alice Marbach
 * @12/6
 */

import java.util.ArrayDeque;
import java.util.Arrays;
public class InfixToPostfix
{
    // instance variables - replace the example below with your own
    private ArrayDeque<Token> inputTokenList;
    private ArrayDeque<Token> stack = new ArrayDeque<Token>();
    private ArrayDeque<Token> output = new ArrayDeque<Token>();
    
    /**
     * Constructor for objects of class InfixToPostfix, which takes the token list from p, initializing instance variable inputTokenList to p, and calls postFixConverter()
     * @param p a PolyReader
     */
    public InfixToPostfix(PolyReader p)
    {
        inputTokenList = p.getTokenList();
        postFixConverter();

    }
    
    /**
     * Gets the output token list which has been re-ordered according to order of operations and has 
     * removed brackets.
     * @return a token list (ArrayDeque<Token>)
     */
    public ArrayDeque<Token> getOutput(){
     return output;
    }
    
    /**
     * Converts the string in infix format (as would be written by a human typing it in to PolyReader) to postfix format. 
     * @return  a stack (an ArrayDeque<Token>) in postfix ordering  
     * @throws IllegalArgumentException if parentheses are not paired correctly
     */
    public ArrayDeque<Token> postFixConverter() 
    {
        for (Token token : inputTokenList){
            if (Operand.isOperand(token) == true){
                output.add(token);
            } else if (token instanceof LeftParenOperator){
                stack.push(token);
            } else if (token instanceof RightParenOperator){
                while ((!stack.isEmpty()) && !(stack.peek() instanceof LeftParenOperator)){
                    output.add(stack.pop());
                } 
                if (stack.isEmpty()){
                    throw new IllegalArgumentException("Mismatched right parenthesis!");
                }
                // throw away left parenthesis    
                stack.pop();
            } else if (token instanceof Operator){
                Operator t = (Operator) token;
                Operator s = (Operator) stack.peek();
                while ((!stack.isEmpty()) && !(stack.peek() instanceof LeftParenOperator) && (s.getPrio() <= t.getPrio())){
                    output.add(stack.pop());
                    //} 
                }
                stack.push(token);
            }
        }
        while (!stack.isEmpty()){
            Token t = stack.pop();
            if (t instanceof LeftParenOperator){
                //throw new ParserException("Mismatched left parenthesis!");
                throw new IllegalArgumentException("Mismatched left parenthesis!");
            } else {
                output.add(t);
            }
        }
        
        return output;
        
    }
}
