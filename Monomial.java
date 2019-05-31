
/**
 * This class represents Monomials as objects, taking in the coefficient and exponent in the constructor.
 * 
 * 
 * 
 * @Alice
 * @11/9
 */
public class Monomial implements Comparable, Token, Operand
{
    // instance variables - each monomial object has these fields

    /** Coefficients can be real numbers (includes all rational numbers - whole, ratios, fractions etc 
     * and irrational numbers -- numbers like pi that can't be represented by a fraction)
     */ 
    private double coefficient;
    // Variables in this program are only x always
    private static char variable = 'x';
    // Exponents must be an integer value 
    private int exponent; 
    
    
    /**
     * Constructor for objects of class Monomial
     */
    public Monomial(double coefficient, int exponent)
    {
        // initialise instance variables
        
        if (exponent >= 0){
            this.exponent = exponent;
            this.coefficient = coefficient;
        }else{
            throw new IllegalArgumentException("Need a non-negative exponent!");
            // throw an exception?
        }
    }
    
    /**
     * gets the coefficient of the monomial
     * @return the double coefficient of the monomial
     */
    public double getCoefficient(){
        return coefficient;
    }

    /**
     * gets the exponent of the monomial
     * @return the exponent of the monomial
     */
    public int getExponent(){
        return exponent;
    }

    /**
    * sets the coefficient of the Monomial
    * @param coefficient a new coefficient
    * @return void
    //*/
    public void setCoefficient(double coefficient){
        this.coefficient=coefficient;
    }
    
    /**
     * compareTo overrides Comparable, comparing the exponents of monomials
     * @param o a monomial
     * @return  1 if mon.exponent>exponent of object this is being called on; 0 if exponents are equal; -1 if mon.exponent<exponent of object this is being called on
     * The reason 1 if mon.exponent is bigger is because comparable puts smallest 
     */
    public int compareTo(Object o){
        if(!(o instanceof Monomial))
        //need to make sure if this is called on a not monomial, it doesn't cast to a monomial
            return 5;
        else{    
            Monomial mon = (Monomial) o;
            // return -1*exponent1.compareTo(exponent2); don't call a method to compare ints
            if (exponent < mon.getExponent())
                return 1;
            else if (exponent == mon.getExponent())
                return 0;
            else //(exponent1 > exponent2)
                return -1;
        }
    }

    /**
     * Compares to Monomial objects to see if their exponents are equal (not necessarily coefficient!)
     * @param o any object o, but should be used for Monomial
     * @return  true if monomial exponents are equal, false if not, false if o is not a monomial
     */
    public boolean equals(Object o)
    {
        //need to make sure if this is called on a not monomial, it doesn't cast to a monomial
        if(!(o instanceof Monomial))
            return false;
        else{
            Monomial mon = (Monomial) o;
            if (mon.getExponent()==exponent)
                return true;
            else
                return false;
        }
    }

    /**
     * Represents Monomial according to the format ax^b
     * @return  a String representation of a Monomial
     */
    public String toString()
    {
        String s = "";
        // Check if coefficient is 1

        if (coefficient != 0){
            if((coefficient !=1)&&(coefficient != -1)){
                // check if coefficient is a whole number
                if (coefficient%1==0){
                    s+= (int) coefficient;
                }else {
                    s+= coefficient;
                }
            }else if (coefficient == -1){
                s+= "-";
            }

            // check if exponent is 0 
            if (exponent == 0){
                if (coefficient == 1){
                    s+= (int) coefficient;
                }
            }else if (exponent == 1){
                s+= (char) variable;
            }
            else{
                s+= (char) variable + "^" + exponent;
            }
        }
        
        if ((coefficient == 0) && (exponent == 0)){
            s += (int) coefficient;
        }
        return s;
    }

    /**
     * Adds two Monomials if exponents are equal
     * @param mon a monomial to be added to this monomial
     * @return  a Monomial with the result of the sum
     */
    public Monomial sum(Monomial mon)
    {
        Monomial resultMon = new Monomial(0,0);
        // First checks if exponents are the same
        if (exponent == mon.exponent){
            resultMon.coefficient = coefficient + mon.coefficient;
            resultMon.exponent = exponent; 
        } else{
            return null;
        }
        return resultMon;
    }

    /**
     * Subtracts input Monomial from this Monomial if exponents are equal
     * @param mon a monomial to be subtracted from the monomial on which subtract is called
     * @return  a Monomial with the result of the subtraction
     */
    public Monomial subtract(Monomial mon)
    {
        Monomial resultMon = new Monomial(0,exponent);
        // First checks if exponents are the same
        if (exponent == mon.exponent){
            resultMon.coefficient = coefficient - mon.coefficient;

        }else{
            return null;
        }
        return resultMon;
    }

    /**
     * Multiplies two Monomials; when monomials are multiplied, coefficients are mulitipled, exponents are added.
     * @param mon a monomial to be multiplied by this Monomial 
     * @return  a Monomial with the result of the multiplication
     */
    public Monomial multiply(Monomial mon)
    {
        Monomial resultMon = new Monomial(0,0);
        resultMon.coefficient = coefficient * mon.coefficient;
        resultMon.exponent = exponent + mon.exponent;
        return resultMon; 
    }

    /**
     * Divides this Monomial by the input Monomial; when monomials are divided, coefficients are divided, exponents are subtracted
     * @param mon Monomial a monomial which the monomial "divide" is called on will be divided by (i.e. the divisor)
     * @return a Monomial with the result of the division
     */
    public Monomial divide(Monomial mon)
    {
        Monomial resultMon = new Monomial(0,0);
        //Can't divide by zero
        if (coefficient != 0){
            resultMon.coefficient = coefficient/mon.coefficient;
            resultMon.exponent = exponent - mon.exponent;

        }else{ //coefficient is 0
            return null;
        }
        return resultMon;
    }

}
