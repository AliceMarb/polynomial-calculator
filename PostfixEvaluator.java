
/**
 * Evaluates a postfix token list, outputting the correct polynomial, and if applicable, saving the result in the map found in PolynomialCalc
 *
 * @Alice
 * @12/6
 */
import java.util.ArrayDeque;

public class PostfixEvaluator
{

    private ArrayDeque<Token> tokenList;
    private ArrayDeque<Token> stack; 
    private Polynomial outputPoly;

    /**
     * Constructor for objects of class PostfixEvaluator which initializes the token list, makes an empty stack and calls postfixEvaluator()
     * @param in an ArrayDeque (token list in postfix notation) provided by InfixtoPostfix
     */
    public PostfixEvaluator(InfixToPostfix in)
    {
        tokenList = in.getOutput();
        stack = new ArrayDeque<Token>();
        postfixEvaluator();
    }

    /**
     * Gets the output Polynomial
     * @return the Polynomial output, which is teh result of the evaluation by postfixEvaluator
     */
    public Polynomial getOutputPoly(){
        return outputPoly;
    }
    
    /**
     * Puts the name of the Polynomial evaluated by PolyReader as the key, and the outputPoly evaluated by postfixEvaluator() as the value, into the polyMap
     * @return void, but side effect of putting the name and polynomial into the map 
     */
    public void evaluatorToMap(){
        PolynomialCalc.polyMap.put(PolyReader.getNameVariable().nonXVariable, outputPoly);
    }
    
    /**
     * Evaluates the token list and outputs a Polynomial as the result of the evaluation
     * @return void, but instantiates outputPoly as the result of evaluating the postfix expression
     */
    public void postfixEvaluator()
    {
        Polynomial result = new Polynomial(); // result could be a polynomial or a monomial
        result = result.addMonomial(new Monomial (0,0));

        for (Token token : tokenList){
            if (token instanceof NonXVariable){
                NonXVariable t = (NonXVariable) token;
                if (PolynomialCalc.polyMap.get(t.nonXVariable) == null)
                {
                    throw new IllegalArgumentException("Could not find the value for the variable '" + token + "' that you wrote."); 
                } else{
                    stack.push((Token) PolynomialCalc.polyMap.get(t.nonXVariable));
                } 
            } else if (Operand.isOperand(token)){
                stack.push(token);
            }else if (token instanceof Operator){
                Operator t = (Operator) token;
                Token resultPoly = new Polynomial();

                Token op2 =  stack.pop();
                Polynomial p2 = new Polynomial();
                p2 = p2.addMonomial(new Monomial (0,0));

                Polynomial p1 = new Polynomial();

                Monomial mon2 = null;

                if (op2 instanceof Monomial){
                    mon2 = (Monomial) op2;
                    p2 = p2.addMonomial(mon2);
                    if (p2.mset.isEmpty()){
                        p2 = p2.addMonomial(new Monomial(0,0));
                    }

                } else {
                    if (p2.mset.isEmpty()){
                            p2 = p2.addMonomial(new Monomial(0,0));
                        }
                    p2 = (Polynomial) op2;
                }

                if (stack.isEmpty() && (token instanceof SubtractOperator)){
                    // e.g. -3 
                    Polynomial p = new Polynomial();
                    p = p.addMonomial(new Monomial(1,1));
                    p = p.subtractPoly(p2);
                    p = p.subtractMonomial(new Monomial(1,1));
                    resultPoly = (Token) p;
                    // a work around for getting a minus monomial
                } else {
                    Token op1 = stack.pop();
                    if (op1 instanceof Monomial){
                        Monomial mon1 = (Monomial) op1;
                        p1 = p1.addMonomial(mon1);
                        if (p1.mset.isEmpty()){
                            p1 = p1.addMonomial(new Monomial(0,0));
                        }
                    } else{ //op1 is a Polynomial
                        p1 = (Polynomial) op1;
                    }
                    //operate requires 2 polynomials are inputs
                    // if (p2.mset.isEmpty() || p1.mset.isEmpty()){
                    // throw new IllegalArgumentException("Sorry this is not possible!");
                    // }
                    try{ 
                        Polynomial r = new Polynomial();
                        r = r.addMonomial(new Monomial(0,0));
                        r = r.addPoly(t.operate(p1, p2));
                        resultPoly = (Token) r;
                    } catch (Polynomial.IndivisibleException e){
                        throw new IllegalArgumentException(e.getMessage());
                    }

                }
                stack.push(resultPoly);
            } 
        }
        Token s = stack.pop();
        if (s instanceof Monomial){
            Monomial smon = (Monomial) s;
            result = result.addMonomial(smon);
            if (result.mset.isEmpty()){
                result = result.addMonomial(new Monomial(0,0));
            }
        } else{
            result = (Polynomial) s;
        }

        outputPoly = result;
        //System.out.println("outputPoly=" + outputPoly);
    }
}
