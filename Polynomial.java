
/**
 * This class represents polynomials in object form. 
 * A polynomial is an expression that can have constants, variables and exponents.
 *  
 * It will be in the form of an array list
 * @Alice Marbach
 * @11/9
 */
import java.util.Set;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.Iterator;

public class Polynomial implements Token
{
    public TreeSet<Monomial> mset = new TreeSet<Monomial>();
    
    /**
     * adds the input Monomial onto this Polynomial's TreeSet (called mset). If there is already a Monomial with the same exponent in the set, the 2 Monomials are added using Monomial's add method. If the resulting
     * Polynomial is empty, a Monomial(0,0) is added to the set. But otherwise 0 values are not added to the set.
     * @param mon a monomial
     * @return  the resulting polynomial of the sum of the monomial and the polynomial
     */
    public Polynomial addMonomial(Monomial mon)
    {
        //contains uses the equals method in Set interface
        // in Monomial class I rewrote equals to only compare monomials' exponents
        Polynomial resultPoly = new Polynomial();

        // Copy contents of mset into resultPoly's mset
        Iterator<Monomial> itr = mset.iterator();

        while(itr.hasNext()){
            Monomial next = itr.next();
            if (next.getCoefficient() != 0){
                resultPoly.mset.add(next);}
        }

        // This is the only way to find and return a value that you are looking for in the set    
        NavigableSet<Monomial> set = resultPoly.mset.subSet(mon, true, mon, true);
        
        if(set.size() == 0){
            // subset uses equals method which i rewrote in monomial to only consider exponents
            //built in add method if nothing with same exponent there
            if (mon.getCoefficient()!=0){ //don't add if the value being added is 0
                resultPoly.mset.add(mon);
            } else{
                if (resultPoly.mset.isEmpty()){
                    resultPoly.mset.add(new Monomial(0,0));
                }
            }
        }else{ //already something there with same exponent
            Monomial resultOfSum = set.first().sum(mon);
            resultPoly.mset.remove(set.first());

            if(resultOfSum.getCoefficient() != 0){
                // if the result of the sum is a 0, remove that monomial from the set
                resultPoly.mset.add(resultOfSum);
            }else{
                if (resultPoly.mset.isEmpty()){
                    resultPoly.mset.add(new Monomial(0,0));
                }
            }
        }
        //sum mon and the monomial in mset with the same exponent
        return resultPoly;    
    }

    /**
     * adds two polynomials by iterating through the input polynomial's mset and calling addMonomial to add it to a copy of polynomial's mset. This does not change this polynomial's mset.
     * @param poly a polynomial
     * @return  the result of the addition of the 2 polynomials 
     * 
     */
    public Polynomial addPoly(Polynomial poly)
    {
        //iterate through the set of monomials, adding each one to the polynomial
        Polynomial resultPoly = new Polynomial();
        // Copy contents of mset into resultPoly's mset
        Iterator<Monomial> itr1 = mset.iterator();

        while(itr1.hasNext()){
            Monomial next = itr1.next();
            if (next.getCoefficient() != 0){
                resultPoly.mset.add(next);    }
        }
        Iterator<Monomial> itr = poly.mset.iterator();
        while(itr.hasNext()){
            //check if an item exists with the same exponent
            // contains uses the equals method, which has been modified to only compare exponents
            resultPoly = resultPoly.addMonomial(itr.next());
        }

       
        return resultPoly;

    }

    /**
     * subtracts mon from a copy of this Polynomial's mset. If there is already a Monomial with the same exponent in the set, mon is subtracted from that Monomial; otherwise mon is added to the set with a negative coefficient.
     * If the resulting Polynomial is empty, a Monomial(0,0) is added to the set. But otherwise 0 values are not added to the set. Adds Monomial(0,0) if resulting mset is empty.
     * @param mon a monomial
     * @return  the resulting polynomial of the subtraction of mon from this polynomial
     */
    public Polynomial subtractMonomial(Monomial mon)
    {
        //contains uses the equals method in Set interface
        // in Monomial class I rewrote equals to only compare monomials' exponents
        Polynomial resultPoly = new Polynomial();

        Iterator<Monomial> itr = mset.iterator();
        while(itr.hasNext()){
            Monomial next = itr.next();
            if (next.getCoefficient() != 0){
                resultPoly.mset.add(next);    }
        }

        NavigableSet<Monomial> set = resultPoly.mset.subSet(mon, true, mon, true);
        if(set.size() == 0){
            mon.setCoefficient(mon.getCoefficient()*-1);
            resultPoly.mset.add(mon);
        } else{ //already something there with same exponent
            Monomial resultOfSum = set.first().subtract(mon);
            resultPoly.mset.remove(set.first());
            if(resultOfSum.getCoefficient() != 0){
                // if the result of the sum is a 0, remove that monomial from the set
                resultPoly.mset.add(resultOfSum);
            }
        }
        //sum mon and the monomial in mset with the same exponent

        
        if (resultPoly.mset.isEmpty()){
            resultPoly.mset.add(new Monomial(0,0));
        }
        return resultPoly;

    }

    /**
     * subtracts the input polynomial from this polynomial by iterating through the input polynomial's mset and calling subtractMonomial to subtract it from a copy of this polynomial's mset.
     * This does not change this polynomial's mset. Adds Monomial(0,0) if resulting mset is empty.
     * @param poly a polynomial
     * @return  the result of the subtraction of the 2 polynomials
     * 
     */
    public Polynomial subtractPoly(Polynomial poly)
    {
        //iterate through the set of monomials, adding each one to the polynomial
        Polynomial resultPoly = new Polynomial();
        Iterator<Monomial> itr = mset.iterator();
        while(itr.hasNext()){
            Monomial next = itr.next();
            
            if (next.getCoefficient() != 0){
                resultPoly.mset.add(next);}
        }

        Iterator<Monomial> itr1 = poly.mset.iterator();

        while(itr1.hasNext()){
            //check if an item exists with the same exponent
            // contains uses the equals method, which has been modified to only compare exponents
            resultPoly = resultPoly.subtractMonomial(itr1.next());
        }

        
        if (resultPoly.mset.isEmpty()){
            resultPoly.mset.add(new Monomial(0,0));
        }
        return resultPoly;

    }

    /**
     * multiplies mon by a copy of this polynomial.
     * Polynomial multiplication is done by: a(b+c+d)= ab + ac + ad
     * @param mon a monomial
     * @return  the resulting polynomial 
     */
    private Polynomial multiplyMonomial(Monomial mon)
    {
        Polynomial resultPoly = new Polynomial();
        Iterator<Monomial> itr = mset.iterator(); 

        while(itr.hasNext()){
            Monomial original = itr.next();
            if (original.getCoefficient() != 0)

                resultPoly = resultPoly.addMonomial(original.multiply(mon));
        }

        return resultPoly;
    }

    /**
     * multiplies poly by a copy of this polynomial. Adds it using addPoly, so any same exponents are added.
     * @param poly a polynomial
     * @return  the result of the multiplication of poly and this polynomial
     */
    public Polynomial multiplyPoly(Polynomial poly)
    {
        //iterate through the set of monomials, adding each one to the polynomial
        Polynomial resultPoly = new Polynomial();
        Iterator<Monomial> itr = poly.mset.iterator();

        while(itr.hasNext()){
            Polynomial singleElmtMultiplication = multiplyMonomial(itr.next());
            resultPoly = resultPoly.addPoly(singleElmtMultiplication);    
        }
        
        if (resultPoly.mset.isEmpty()){
            resultPoly.mset.add(new Monomial(0,0));
        }
        return resultPoly;
    }

    /**
     * This an exception class to handle the exception that the remainder for dividePoly is a non-zero value or if there is dividing by zero taking place
     */

    public class IndivisibleException extends ArithmeticException {
        //Constructor
        public IndivisibleException(){ super(); } 
        // Constructor 2, where it gives a message
        public IndivisibleException(String msg){ super(msg);}

    }

    /**
     * Divides a copy of this polynomial by input polynomial. If the remainder is not 0, or if poly is 0 throws an IndivisibleException. Uses a polynomial division algorithm
     * based on one you can find in Wikipedia.
     * @param poly a polynomial
     * @return  the quotient (result) of the division as a Polynomial.
     */
    public Polynomial dividePoly(Polynomial poly)
    {

        if (poly.mset.first().getCoefficient() == 0){ //when a result was 0 I removed it from the polynomial, so the only way polynomial=0 is if mset is empty
            throw new IndivisibleException("Cannot divide by 0!");
        }
        //initializing quotient and remainder
        Polynomial quotient = new Polynomial();
        //TreeSet<Monomial> quotient = quotientPoly.mset; //quotient = 0
        Polynomial remainder = new Polynomial();
        remainder.mset = mset;

        // starting algorithm
        while ((remainder.mset.first().getCoefficient() != 0)&& (remainder.mset.first().getExponent()>=poly.mset.first().getExponent())) //remainder!=0
        {
            //t = lead(r)/lead(d)
            Monomial t = remainder.mset.first().divide(poly.mset.first());
            // q= q+t
            quotient = quotient.addMonomial(t);
            Polynomial tMultiplyD = poly.multiplyMonomial(t);
            remainder = remainder.subtractPoly(tMultiplyD);
        }
        if(remainder.mset.first().getCoefficient() == 0){
            return quotient;
        }

        else{
            throw new IndivisibleException("Sorry, this division can't be done, because there is a non-zero remainder!");
        }

    }

    /**
     * Makes a String representation of the polynomial using  operators.
     * @return a String representation of the polynomial
     */
    public String toString(){
        String s = "";
        Iterator<Monomial> itr = mset.iterator();
        while (itr.hasNext()){
            Monomial firstMon = itr.next();
            if (firstMon.getExponent() == mset.first().getExponent()){// first monomial in the set  
                s += firstMon;
            }else{
                if (firstMon.getCoefficient() >= 0){
                    s = s + " + " + firstMon;
                }else{
                    s += " " + firstMon;
                }
            }
        }

        return s;
    }
}
