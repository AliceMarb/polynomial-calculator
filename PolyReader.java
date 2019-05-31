
/**
 * Takes in a String and uses a Finite State Machine-based expression parser to output a tokenList of Operators and Operands, which can then be further evaluated by InfixToPostfix.
 * There are two possible formats for parser:
 * 
 * 1) Assigns a value to a variable other than x and stores it 
 * input:a=2x^2+x^2-x
 * output:a = 3x^2 - x
 * OR 
 * input :b=x
 * output : b = x
 * 
 * 2) Calculates value but does not store it
 * Given this: 
 * input  : c = a/b
 * output : c = 3x - 1
 * Do THIS:
 * input  : c*(a-b)
 * output : 9x^3 - 9x^2 + 2x
 * 
 * @Alice Marbach
 * @12/6/ 2018
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.ArrayDeque;

public class PolyReader
{
    // Instance variable should be able to be changed by MonomialParser
    public static PrEnums curState;
    public String inputString;

    private ArrayList<Character> intSaver = new ArrayList<Character>();
    private ArrayDeque<Token> tokenList = new ArrayDeque<Token>();
    private boolean bracketSeen;
    private static NonXVariable nameVariable;
    private double monInt;
    private int monExp;
    private int exprLen;

    /**
     * Initializes the instance variable inputString to the parameter, sets the curState as WVARORMON and calls readPoly()
     * @param  a string to be parsed through the polynomial reader
     */
    public PolyReader(String inputString){
        this.inputString = inputString.toLowerCase();
        nameVariable = null;
        curState = PrEnums.WVARORMON;
        readPoly();

    }

    /**
     * gets the name of the polynomial (i.e. the letter before the equals sign, the a in a = 3 + x^2) that was evaluated by readPoly() from the inputString.
     * @return the name of the input expression 
     */
    public static NonXVariable getNameVariable()
    {
        return nameVariable;
    }

    /**
     * gets the resulting list after the inputString is evaluated, to be passed to InfixToPostfix
     * @return  the ArrayDeque of tokens.
     */
    public ArrayDeque<Token> getTokenList()
    {
        return tokenList;
    }

    /**
     * An enum of the states in the finite state machine in readPoly()
     */
    public enum PrEnums{

        WVARORMON(false), 

        VAR(false),

        INT(false), 

        X (false),

        WDEC(false),

        DEC(false),

        CARET(false), 

        EXP(false), 

        CLOSEBRAC(false),

        END(true), 

        ERR(false);

        //enum constructor
        PrEnums(boolean isAccept){this.isAccept = isAccept;}
        //enum instance variable
        private final boolean isAccept;
        //enum get method
        /**
         * Checks if the FSM will accept this state as a finishing state. (only END returns true).
         * @return boolean true if accepting state, false otherwise.
         */
        public boolean isAccept(){return isAccept;}
    }

    /**
     * In the FSM numbers are read character by character and saved in intSaver as a list of digits. This method turns the list into a single double. Once compiled, intSaver is set back toempty.
     * @return  a double which is the number represented by the list of digits in intSaver.
     */ 
    private double compileIntSaver(){
        double total = 0;
        int decimalPointIndex = intSaver.size() -1 ;
        // if there is a decimal point, add numbers up to the decimal point as normal
        // if there is no decimal point, add numbers the whole way to the end so dPI has to be set to the end

        if (intSaver.contains('.')){
            decimalPointIndex = intSaver.indexOf('.'); 
        }
        for (int i = 0; i <= decimalPointIndex; i++){

            int num = Character.getNumericValue(intSaver.get(i));
            // To allow the case of just one integer in the list, I need i to be equal to
            // intSaver.size() - 1, not stricly less than. But this will allow decimal points
            // to be counted, so I have to check for that.
            if(intSaver.get(i) != '.'){total = num + 10*total;}
        }
        if(decimalPointIndex != intSaver.size() -1){ //intSaver does contain .  

            for (int j = decimalPointIndex + 1, i = 0; j < intSaver.size(); j++, i++){
                int num = Character.getNumericValue(intSaver.get(j));
                total += num * Math.pow(10,(-1-i));  
            } 

        }

        intSaver = new ArrayList<Character>(); //once it is compiled, intSaver should be erased and used again
        return total;
    }

    /**
     * Adds to the tokenList the correct Monomial depending on the state of the FSM.
     * FSM feeds Monomials to the tokenList, but what kind of Monomial depends on the state it is in.
     * This method compiles the int saver, puts the results in as parameters to a Monomial, and adds this to the tokenList, according to the state it is in.
     * e.g. is in X state, the exponent should be 1, whereas if in INT state, the exponent should be 0.
     */

    private void addFromIntSaver(){
        switch(curState){
            case INT:
            monInt = compileIntSaver();
            // adds a integer of type monomial to the list so exponent 0 
            tokenList.add(new Monomial(monInt, 0));
            break;

            case DEC:
            monInt = compileIntSaver();
            // adds a decimal number of type monomial to the list, exponent 0
            tokenList.add(new Monomial(monInt, 0));
            break;

            case X:

            // adds (monInt)x monomial to the list, exponent 1
            tokenList.add(new Monomial(monInt, 1));
            break;

            case EXP:
            monExp = (int) compileIntSaver();
            // adds (monInt)x^(monExp) to the list
            tokenList.add(new Monomial(monInt, monExp));
            break;

        } 
    }

    /**
     * Saves the name, i.e. the letter before the equals sign, as an object of class NonXVariable, to the instance variable nameVariable.
     * @param name an input string which is jsut everything before the = sign.
     * @return if name contains a valid name (one character long), intializes nameVariable. Otherwise, throws an IllegalArgumentException.
     */
    private void readName(String name){

        // takes out all the whitespace of everything before the =
        String nameNoSpace = name.replaceAll("\\s","");

        if (nameNoSpace.length() > 1){
            // there should be one char
            curState = PrEnums.ERR;
            throw new IllegalArgumentException("The name for your polynomial must be one character only");
        } else if (nameNoSpace.length() < 1){
            // there should not be less than one char 
            // because then the input string would look like this "  = 3..."
            curState = PrEnums.ERR;
            throw new IllegalArgumentException("Please provide a name (of one character) for your polynomial."); 
        } else{ 
            if (NonXVariable.isNonXVariable(nameNoSpace.charAt(0))){
                nameVariable = new NonXVariable(nameNoSpace.charAt(0));
                curState = PrEnums.WVARORMON;
            }
        } 

    }

    /**
     * Takes the String of everything after the equals sign and converts it to a String array, having gotten rid of all the spaces. Adds a '$' character on the end to show the FSM the
     * parser has reached the end of the string.
     * @param inputExpr a String with whitespace
     * @return a array list of strings without whitespace, and with a "$" added as the last String. 
     */
    private ArrayList<String> exprToNoSpaceArray(String inputExpr){
        if (inputExpr.replaceAll("\\s","").isEmpty()){
            // if expression is empty
            return null;
        }

        // remove all the whitespaces so that everything is parsed without having to check for whitespace each time
        String[] exprArray = inputExpr.trim().split("\\s+");
        //convert from array to ArrayList type for ease of adding
        ArrayList<String> exprArrayList = new ArrayList<String>(Arrays.asList(exprArray)); 
        // know that the expression has come to an end when $ is reached.
        exprArrayList.add("$"); 
        exprLen = exprArrayList.size();

        return exprArrayList;
    }

    /**
     * A Finite State Machine that reads the user inputted string and evaluates it to make a list of tokens.  
     * @return ArrayDeque<Token> a list of tokens if the string has ended in an accepting state ( is a valid stirng), otherwise null. However, because of exceptions being thrown instead it is very unlikely to return null.
     * @throws IllegalArgumentException if 
     */ 
    public ArrayDeque<Token> readPoly(){

        String[] splitStringArray = inputString.split("=");// = input.split("=");
        String name = null;
        String expression = "";
        curState = PrEnums.WVARORMON;
        // This will be the input to the FSM
        ArrayList<String> exprArrayList = null;

        //split the string between the name and the expression proper
        if (splitStringArray.length == 2){
            name = splitStringArray[0]; // name is a string containing everything before the = sign
            expression = splitStringArray[1];
        } else if (splitStringArray.length == 1){
            // there is no = sign, so no name.
            nameVariable = null;
            expression = splitStringArray[0];
            //this would allow something to get through with nothing after = sign but something before
        } else {
            throw new IllegalArgumentException("Problem with your equals signs");
        }

        if (name != null){
            readName(name);
            if (curState == PrEnums.ERR){
                throw new IllegalArgumentException("Name not valid");
            }
        } 

        if (exprToNoSpaceArray(expression) == null){
            throw new IllegalArgumentException("Please enter a complete expression");

        } else {
            exprArrayList = exprToNoSpaceArray(expression); 
        }

        //This part of the finite state machine is going through expression
        int i = 0, j=0;

        // Evaluating expressionArrayList
        while (curState != PrEnums.ERR && i < exprLen){
            //go through the exprArrayList and go to each String in the array
            String stringInList = exprArrayList.get(i++);
            j = 0;
            // within each String, check each char
            while (curState != PrEnums.ERR && j < stringInList.length()){
                char c = stringInList.charAt(j++);
                switch (curState){

                    case WVARORMON: //waiting for a variable or a monomial

                    if (NonXVariable.isNonXVariable(c)){
                        NonXVariable var = new NonXVariable(c);
                        tokenList.add(var);
                        curState = PrEnums.VAR;
                    } else if (c == 'x'){
                        monInt = 1;
                        curState = PrEnums.X;
                    } else if (Character.isDigit(c)){
                        intSaver.add(c);
                        curState = PrEnums.INT;
                    } else if (c == '-'){
                        // exceptional case, only the first time.
                        // otherwise it should be an operator coming back from INT, X, EXP or VAR.
                        if ((tokenList.isEmpty())||(tokenList.getLast() instanceof LeftParenOperator )){ //only add if there has been nothing before, 
                            tokenList.add(new SubtractOperator());
                            // stay in same state
                        } else {
                            curState = PrEnums.ERR;
                            throw new IllegalArgumentException("Can't have two operators in a row!");
                        }
                    } else if (c == '('){
                        tokenList.add(Operator.outputCorrectOperator(c));
                        //stays in WVARORMON
                    } else{ 
                        curState = PrEnums.ERR;
                        if (c != '$'){
                            throw new IllegalArgumentException("Please write a valid variable or polynomial. The use of " + c + " was not correct here.");
                        } else{
                            throw new IllegalArgumentException("Please write a valid variable or polynomial. You did not write syntax correctly.");}
                    }
                    break;

                    case INT:
                    if (Character.isDigit(c)){
                        intSaver.add(c); //stay in INT state
                    } else if (c == '.'){
                        intSaver.add(c);
                        curState = PolyReader.PrEnums.WDEC;
                    } else if (c == 'x'){
                        // compile the intSaver 
                        monInt = compileIntSaver();
                        curState = PrEnums.X;
                    } else if (c == ')'){
                        addFromIntSaver();
                        tokenList.add(new RightParenOperator());
                        curState = PrEnums.CLOSEBRAC;
                    } else if(c == '$'){
                        addFromIntSaver();
                        curState = PrEnums.END;
                    }  else if (Operator.isOperator(c) != 0){
                        addFromIntSaver();
                        tokenList.add(Operator.outputCorrectOperator(c));
                        curState = PolyReader.PrEnums.WVARORMON;
                    } else{
                        curState = PolyReader.PrEnums.ERR;
                        throw new IllegalArgumentException("Invalid character following an integer");
                    } 
                    break;

                    case WDEC:

                    if (Character.isDigit(c)){
                        intSaver.add(c);
                        curState = PolyReader.PrEnums.DEC;
                    } else{
                        curState = PolyReader.PrEnums.ERR;
                    }
                    break;

                    case DEC:

                    if (Character.isDigit(c)){
                        intSaver.add(c);
                    } else if (c == 'x'){
                        // compile the intSaver and add it to the tokenList
                        monInt = compileIntSaver();
                        curState = PolyReader.PrEnums.X;
                    } else if (c == ')'){
                        addFromIntSaver();
                        tokenList.add(new RightParenOperator());
                        curState = PrEnums.CLOSEBRAC;
                    }   else if (Operator.isOperator(c) != 0){
                        addFromIntSaver();
                        tokenList.add(Operator.outputCorrectOperator(c));
                        curState = PolyReader.PrEnums.WVARORMON;
                    } else if (c == '$'){
                        addFromIntSaver();
                        curState = PolyReader.PrEnums.END;
                    } else{
                        curState = PolyReader.PrEnums.ERR;
                        throw new IllegalArgumentException("Invalid character following a decimal.");
                    }
                    break;

                    case X:
                    if(c == '^'){
                        curState = PolyReader.PrEnums.CARET;
                    } else if (c == ')'){
                        addFromIntSaver();
                        tokenList.add(new RightParenOperator());
                        curState = PrEnums.CLOSEBRAC;
                    }  else if (Operator.isOperator(c) != 0){
                        addFromIntSaver();
                        tokenList.add(Operator.outputCorrectOperator(c));
                        curState = PolyReader.PrEnums.WVARORMON;
                    }  else if(c == '$'){
                        addFromIntSaver();                       
                        curState = PolyReader.PrEnums.END;
                    } else{
                        curState = PolyReader.PrEnums.ERR;
                        throw new IllegalArgumentException("Invalid character following x");
                    }
                    break;

                    case CARET:

                    if (Character.isDigit(c)){
                        intSaver.add(c);
                        curState = PolyReader.PrEnums.EXP;
                    } else{
                        curState = PolyReader.PrEnums.ERR;
                        throw new IllegalArgumentException("Invalid character following a ^");
                    }

                    break;

                    case EXP:
                    if (Character.isDigit(c)){
                        intSaver.add(c); //stay in INT state
                    } else if (c == ')'){
                        addFromIntSaver();
                        tokenList.add(new RightParenOperator());
                        curState = PrEnums.CLOSEBRAC;
                    }  else if (Operator.isOperator(c) != 0){
                        addFromIntSaver();
                        tokenList.add(Operator.outputCorrectOperator(c));
                        curState = PolyReader.PrEnums.WVARORMON;
                    } else if(c == '$'){
                        addFromIntSaver();
                        curState = PolyReader.PrEnums.END;
                    } else{
                        curState = PolyReader.PrEnums.ERR; 
                        throw new IllegalArgumentException("Invalid character following an exponent.");
                    }
                    break;

                    case VAR:
                    if (c == ')'){
                        tokenList.add(new RightParenOperator());
                        curState = PrEnums.CLOSEBRAC;
                    }   else if (Operator.isOperator(c) != 0){
                        tokenList.add(Operator.outputCorrectOperator(c));
                        curState = PolyReader.PrEnums.WVARORMON;
                    } else if (c == '$'){
                        curState = PolyReader.PrEnums.END;

                    } else{
                        curState = PolyReader.PrEnums.ERR;
                        throw new IllegalArgumentException("Invalid character following a variable.");
                    }
                    break;

                    case CLOSEBRAC:
                    if (c == ')'){
                        tokenList.add(new RightParenOperator());
                    } else if (Operator.isOperator(c) != 0){
                        tokenList.add(Operator.outputCorrectOperator(c));
                        curState = PolyReader.PrEnums.WVARORMON;
                    } else if (c == '$'){
                        curState = PolyReader.PrEnums.END;
                    } else{
                        curState = PolyReader.PrEnums.ERR; 
                        throw new IllegalArgumentException("Invalid character following a close bracket.");
                    }
                    break;

                    default:
                    throw new IllegalArgumentException("There is an error in what you entered.");

                }
            }
        }

        if (curState.isAccept()){          
            return tokenList;
        } else{
            return null;
        }
    } 

}
