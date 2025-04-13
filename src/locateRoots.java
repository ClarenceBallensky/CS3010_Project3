/** 
 * @author Athena Ballensky
 * Date: 4/8/2025
 * Professor: Dr. Raheja
 * 
 * Write a program for all methods used to locate roots: the Bisection, Newton-Raphson, Secant, False-Position, and Modified Secant Methods
 * 
 * Test with two functions:
 * 
 *      #1   f(x) = 2*(x)^3 – 11.7*(x)^2 + 17.7*(x) – 5
 *          Has 3 roots in range [0,4]: 0.365, 1.922, 3.563
 * 
 *      #2   f(x) = x + 10 – xcosh(50/x)
 *          Has 1 root in range [120, 130]: 126.632
 * 
 * Note: all mentions of error refer to relative approximate error
 */


import java.io.PrintWriter;

public class locateRoots {

    private static final int MAX_ITERATIONS = 100; //100 is the max number of iterations
    private static final double ERROR = 0.001; //.1% desired error
    private static final double DIVERGING_ERROR = 10; //1000% error checking for divergence
    //public static PrintStream output; //output iteration and error to file 


    /**
     * For the bracketing methods (Bisection, False Position):
     * Prints the row of values associated with iteration n
     * @param n
     * @param a
     * @param b
     * @param c
     * @param fa
     * @param fb
     * @param fc
     * @param error
     */
    public static void printBracketing (int n, double a, double b, double c, double fa, double fb, double fc, double error)
    {
        System.out.printf("%d \t    %.3f \t     %.3f \t     %.3f \t     %.3f \t     %.3f \t     %.3f \t     %.3f \n", 
                         n, a, b, c, fa, fb, fc, error);
    }

    /**
     * For the Newton Raphson method:
     * Prints the rows of values associated with iteration n
     * @param n
     * @param x
     * @param x1
     * @param fx
     * @param fx1
     * @param fxPrime
     * @param error
     */
    public static void printNewtonRaphson (int n, double x, double x1, double fx, double fx1, double fxPrime, double error)
    {
        System.out.printf("%d \t    %.3f \t     %.3f \t     %.3f \t     %.3f \t     %.3f \t     %.3f \n", 
                         n, x, x1, fx, fx1, fxPrime, error);
    }

   /**
    * For the Secant method:
    * Prints the rows of values associated with iteration i
    * @param i
    * @param xPrev
    * @param x
    * @param xNext
    * @param fxPrev
    * @param fx
    * @param fxNext
    * @param error
    */
    public static void printSecant (int i, double xPrev, double x, double xNext, double fxPrev, double fx, double fxNext, double error)
    {
        System.out.printf("%d \t    %.3f \t     %.3f \t     %.3f \t     %.3f \t     %.3f \t     %.3f \t     %.3f \n", 
                         i, xPrev, x, xNext, fxPrev, fx, fxNext, error);
    }

    /**
     * For the Modified Secant method:
     * Prints the rows of values associated with iteration n
     * @param n
     * @param x
     * @param x1
     * @param fx
     * @param fx1
     * @param fxPrime
     * @param error
     */
    public static void printModifiedSecant (int n, double x, double x1, double fx, double fx1, double fxPrime, double error)
    {
        System.out.printf("%d \t    %.3f \t     %.3f \t     %.3f \t     %.3f \t     %.3f \t     %.3f \n", 
        n, x, x1, fx, fx1, fxPrime, error);
    }

    /**
     * Write iteration number and the approximate error to output file
     * @param n iteration
     * @param error approximate error
     */
    private static void writeToFile(int n, double error, PrintWriter out) {
        out.println(n + "," + error);
    }
    
    /**
     * Write header for Iteration and Error to output file
     * @param out file to print into
     */
    public static void header(PrintWriter out) {
        out.println("Iteration, Error");
    }

    /**
     * Print text to output file 
     * @param text text to print
     * @param out file to print into
     */
    public static void printToFile(String text, PrintWriter out)
    {
        out.println("\n" + text + "\n");
    }


    /**
     * Get f(x) at this x value
     * @param whichFunction function #1 or #2
     * @param x current value
     * @return f(x)
     */
    private static double getFX (int whichFunction, double x) 
    {

        if (whichFunction == 1)
        {
            //#1   f(x) = 2*(x)^3 – 11.7*(x)^2 + 17.7*(x) – 5
            return (2*Math.pow(x, 3)) - (11.7*Math.pow(x, 2)) + (17.7*x) - (5);

        }
        else if (whichFunction == 2)
        {
            if (x == 0) {
                throw new IllegalArgumentException("Cannot divide By zero.");
            }
            //#2   f(x) = x + 10 – xcosh(50/x)
            return x + 10 - (x*Math.cosh(50/x));
        }
        else
        {
            return Math.tan(x);
        }
    }

    /**
     * Get f'(x) at this x value
     * @param whichFunction function #1 or #2
     * @param x current value
     * @return f'(x)
     */
    private static double getFXPrime (int whichFunction, double x) 
    {

        if (whichFunction == 1)
        {
            //#1   f'(x) = 6*(x)^2 – 23.4*(x) + 17.7
            return (6*Math.pow(x, 2)) - (23.4*x) + (17.7);

        }
        else
        {
            if (x == 0) {
                throw new IllegalArgumentException("Cannot divide by zero.");
            }
            //#2   f'(x) = 1 - cosh(50/x) + (50*sinh(50/x))/x
            return (1 - Math.cosh(50/x) + ((50*Math.sinh(50/x))/x));
        }
    }

    /**
     * Check to determine if f(x) is associated with a root x
     * @param fx f(x)
     * @return true if it is associated with a root, false otherwise 
     */
    private static boolean isRoot(double fx){
        if (fx > -.5 && fx < .5)
        {
            return true;
        }
        return false;
    }

    /** *
     * Returns true if the iteration n is below the max number of iterations, false otherwise
     * @param n iteration number
     * @return true or false
    */
    public static boolean maxIterations (int n)
    {
        if(n > MAX_ITERATIONS){
            System.out.println("\nSolution could not be found within 100 iterations.");
            return false;
        }
        else
            return true;
    }

    /**
     * Checks to see if the the absolute relative error is positive or negative.
     * If negative, it is converted to positive.
     * If positive, it remains the same. 
     * @param error
     * @return positive error
     */
    private static double posError(double error)
    {
        if (error < 0) //if error is negative 
        {
            return error = error * -1; //make positive
        }
        return error;
    }

    /**
     * Bisection Method
     * @param whichFunction function #1 or #2
     * @param a value of a
     * @param b value of b
     */
    public static void bisection (int whichFunction, double a, double b, PrintWriter out)
    {
        int n = 0; //number of iterations 
        double aInitial = a; //used for the conclusion message about the root found/not found
        double bInitial = b; //used for the conclusion message about the root found/not found
        double c = 0; //current c value
        double cPrev = 0; //previous c value
        double fa = 0; //value of f(a)
        double fb = 0; //value of f(b)
        double fc = 0; //value of f(cCurr)
        double error = 1;

        // Check if the initial guesses actually bracket a root
        if (fa * fb > 0) {
        System.out.printf("Bisection Method: f(a) = %.3f and f(b) = %.3f have the same sign. No root is bracketed between %.3f and %.3f for function #%d.\n", 
                          fa, fb, a, b, whichFunction);
        out.println("Bisection Method failed: initial guesses do not bracket a root.");
        return;
    }

        //prints header
        System.out.println("\nn \t    a \t             b \t             c \t             f(a) \t     f(b) \t     f(c) \t     error ");
        System.out.println("------------------------------------------------------------------------------------------------------------------");

        
        while (maxIterations(n))
        {
            c = (b + a)/2; //this method of getting c is specific to the bisection method
            fa = getFX(whichFunction, a);
            fb = getFX(whichFunction, b);
            fc = getFX(whichFunction, c);
            error = Math.abs(c - cPrev)/c;
            error = posError(error); //makes error positive if it is negative

            //prints the current row of numbers
            printBracketing(n, a, b, c, fa, fb, fc, error);
            writeToFile(n, error, out);

            //checks for divergence 
            if (error > DIVERGING_ERROR || Double.isNaN(c) || Double.isInfinite(c)) {
                System.out.println("Bisection Method: Error. This equation is diverging.");
                out.println("Error. This equation is diverging.");
                return;
            }

            //we've either found a root, or we are stuck on a horizontal tangent
            if (Math.abs(fc) < 0.01 || error < ERROR) //if f(c) is 0, or desired error is satisfied 
            {
                if (isRoot(fc)) //root found
                {
                    System.out.printf("Bisection Method: The root %.3f has been found between %.3f and %.3f for " +
                                     "function #%d in %d iterations.\n", c, aInitial, bInitial, whichFunction, n+1);
                    out.println("The root is: " + c);
                }
                else //stuck on a horizontal tangent - no root found 
                {
                    System.out.printf("Bisection Method: There are no roots between %.3f and %.3f for " +
                                      "function #%d.\n", aInitial, bInitial, whichFunction);
                    out.println("Root does not exist");
                }
                return;
            }
            else //fc != 0
            {
                if (fa * fc < 0){
                    b = c;
                }
                else {  //fa * fc > 0
                    a = c;
                }
            }

            cPrev = c;
            n++;
        }
    }


    /**
     * False Position Method
     * @param whichFunction function #1 or #2
     * @param a value of a
     * @param b value of b
     */
    public static void falsePosition (int whichFunction, double a, double b, PrintWriter out)
    {
        int n = 0; //number of iterations 
        double aInitial = a; //used for the conclusion message about the root found/not found
        double bInitial = b; //used for the conclusion message about the root found/not found
        double c = 0; //current c value
        double cPrev = 0; //previous c value
        double fa = 0; //value of f(a)
        double fb = 0; //value of f(b)
        double fc = 0; //value of f(cCurr)
        double error = 1;

        //prints header
        System.out.println("\nn \t    a \t             b \t             c \t             f(a) \t     f(b) \t     f(c) \t     error ");
        System.out.println("------------------------------------------------------------------------------------------------------------------");

        
        while (maxIterations(n))
        {
            fa = getFX(whichFunction, a);
            fb = getFX(whichFunction, b);

            //if fa and fb have the same sign, stop searching for root
            if (fa * fb > 0) {
                System.out.printf("False Position Method: f(a) = %.3f and f(b) = %.3f have the same sign. No root is bracketed between %.3f and %.3f for function #%d.\n", 
                          fa, fb, a, b, whichFunction);
                out.println("False Position Method failed: initial guesses do not bracket a root.");
                return;
            }

            c = b - ((fb * (a - b)))/(fa - fb); //this method of getting c is specific to the false position method
            fc = getFX(whichFunction, c);
            error = Math.abs(c - cPrev)/c;
            error = posError(error); //makes error positive if it is negative

            //prints the current row of numbers 
            printBracketing(n, a, b, c, fa, fb, fc, error);
            writeToFile(n, error, out);

            //checks for divergence 
            if (error > DIVERGING_ERROR || Double.isNaN(c) || Double.isInfinite(c)) {
                System.out.println("False Position Method: Error. This equation is diverging.");
                out.println("Error. This equation is diverging.");
                return;
            }

            //we've either found a root, or we are stuck on a horizontal tangent
            if (Math.abs(fc) < 0.01 || error < ERROR) //if f(c) is 0, or desired error is satisfied 
            {
                if (isRoot(fc)) //found a root
                {
                    System.out.printf("False Position Method: The root %.3f has been found between %.3f and %.3f for " +
                                     "function #%d in %d iterations.\n", c, aInitial, bInitial, whichFunction, n+1);
                    out.println("The root is: " + c);
                }
                else //stuck on a horizontal tangent - no root found 
                {
                    System.out.printf("False Position Method: There are no roots between %.3f and %.3f for " +
                                      "function #%d.\n", aInitial, bInitial, whichFunction);
                    out.println("Root does not exist");
                }
                return;
            }
            else //fc != 0
            {
                if (fa * fc < 0){ 
                    b = c;
                }
                else { // fa * fc > 0
                    a = c;
                }
            }

            cPrev = c;
            n++;
        }
    }

    /**
     * Newton Raphson Method
     * @param whichFunction function #1 or #2
     * @param x value of x_n - initial guess
     */
    public static void newtonRaphson (int whichFunction, double x, PrintWriter out)
    {
        int n = 0; //number of iterations 
        double xInitial = x; //used for the conclusion message about the roots found/not found
        double x1 = 0; //value of x_n+1
        double x1Prev = 0; //previous value of x_n+1
        double fx = 0; //value of f(x_n)
        double fx1 = 0; //value of f(x_n+1)
        double fxPrime = 0; //value of f'(x_n)
        double error = 1;

        System.out.println("\nn \t    x_n \t     x_n+1 \t     f(x_n) \t     f(x_n+1) \t     f'(x_n) \t     error ");
        System.out.println("--------------------------------------------------------------------------------------------------");

        while(maxIterations(n))
        {
            fx = getFX(whichFunction, x);
            fxPrime = getFXPrime(whichFunction, x);
            x1 = x - (fx/fxPrime);
            fx1 = getFX(whichFunction, x1);
            error = Math.abs(x1 - x1Prev)/x1; //current x_n+1 minus previous x_n+1 over current x_n+1
            error = posError(error); //makes error positive if it is negative

            

            printNewtonRaphson(n, x, x1, fx, fx1, fxPrime, error); //print the row of values for the current iteration n
            writeToFile(n, error, out);

            //checks for divergence 
            if (error > DIVERGING_ERROR || Double.isNaN(x1) || Double.isInfinite(x1)) {
                System.out.println("Newton Raphson Method: Error. This equation is diverging.");
                out.println("Error. This equation is diverging.");
                return;
            }
            
            //we've either found a root, or it's a false alarm 
            if (Math.abs(fx1) < 0.01 || error < ERROR) //if f(x_n+1) is 0, or desired error is satisfied
            {
                if (isRoot(fx1)) //found a root
                {
                    System.out.printf("Newton Raphson Method: The root %.3f has been found with a starting guess of x = %.3f for the " +
                                     "function #%d in %d iterations.\n", x1, xInitial, whichFunction, n+1);
                    out.println("The root is: " + x1);
                }
                else //false alarm 
                {
                    System.out.printf("Newton Raphson Method: There are no roots found from the starting guess of x = %.3f for " +
                                      "function #%d.\n", xInitial, whichFunction);
                    out.println("Root does not exist");

                }
                return;
            }

            //means we are stuck on a horizontal tangent 
            if (fxPrime == 0) 
            {
                System.out.println("Error: Stuck on a horizontal tangent.");
                out.println("Error. Horizontal tangent.");
                return;
            }
            
            x1Prev = x;
            x = x1;
            n++;
        }
    }

    /**
     * Secant Method
     * @param whichFunction function #1 or #2
     * @param xPrev x_i-1
     * @param x x_i
     * Note: To avoid confusion, I use i as the iteration variable instead of n because this method starts with iteration 1, not 0
     */
    public static void secant (int whichFunction, double xPrev, double x, PrintWriter out)
    {
        int i = 1; //this method starts at iteration 1
        double xPrevInitial = xPrev; //x_i-1, used for the conclusion message about roots found/not found
        double xInitial = x; //x_i, used for the conclusion message about roots found/not found
        double xNext = 0; //x_i+1
        double fxPrev = 0; //f(x_i-1)
        double fx = 0; //f(x_i)
        double fxPrime = 0; //f'(x_i)
        double fxNext = 0; //f(x_i+1)
        double error = 1;

        System.out.println("\nn \t    x_i-1 \t     x_i \t     x_i+1 \t     f(x_i-1) \t     f(x_i) \t     f(x_i+1) \t     error");
        System.out.println("------------------------------------------------------------------------------------------------------------------");

        while (maxIterations(i))
        {
            fxPrev = getFX(whichFunction, xPrev);
            fx = getFX(whichFunction, x);
            fxPrime = getFXPrime(whichFunction, x);
            xNext = x - ((x-xPrev)/(fx-fxPrev))*fx; //x_i+1 = x_i - (( x_i - x_i-1 ) / f(x_i) - f(x_i-1)) * f(x_i)
            fxNext = getFX(whichFunction, xNext);
            error = Math.abs(xNext-x)/xNext;
            error = posError(error); //makes error positive if it is negative

            printSecant(i, xPrev, x, xNext, fxPrev, fx, fxNext, error);
            writeToFile(i, error, out);

            //checks for divergence 
            if (error > DIVERGING_ERROR || Double.isNaN(xNext) || Double.isInfinite(xNext)) {
                System.out.println("Secant Method: Error. This equation is diverging.");
                out.println("Error. This equation is diverging.");
                return;
            }
            

            if (Math.abs(fxNext) < 0.01 || error < ERROR)
            {
                if (isRoot(fx)) //found a root
                {
                    System.out.printf("Secant Method: The root %.3f has been found with the starting guesses x = %.3f and x = %.3f for " +
                                     "function #%d in %d iterations.\n", xNext, xPrevInitial, xInitial, whichFunction, i);
                    out.println("The root is: " + xNext);
                }
                else //false alarm
                {
                    System.out.printf("Secant Method: There are no roots found with the starting guesses x = %.3f and x = %.3f for " +
                                      "function #%d.\n", xPrevInitial, xInitial, whichFunction);
                    out.println("Root does not exist");
                }
                return;
            }

            if(fxPrime == 0)
            {
                System.out.println("Error: Stuck on a horizontal tangent.");
                out.println("Error. Horizontal tangent.");
                return;
            }
            
            xPrev = x;
            x = xNext;
            i++;
            
        }
    }

    /**
     * Modified Secant method
     * @param whichFunction function #1 or #2
     * @param x initial guess
     * @param out file to print results into
     */
    public static void modifiedSecant (int whichFunction, double x, PrintWriter out)
    {
        int n = 0;
        double xInitial = x;
        double x1 = 0; //x_n+1
        double fx = 0;
        double fx1 = 0;
        double fxPrime = 0;
        double epsilon = 0.01;
        double epsilonX = epsilon * x; 
        double error = 1;
        
        System.out.println("\nn \t     x_i \t     x_i+1 \t     f(x_i) \t     f(x_i+1) \t     f'(x_i) \t     error");
        System.out.println("--------------------------------------------------------------------------------------------------");


        while(maxIterations(n)){
            fx = getFX(whichFunction, x);
            fxPrime = getFXPrime(whichFunction, x);
            //x_n_1 = x_n - ((f(x_n) * δ * x_n) / (f(x_n + δ * x_n) - f(x_n)))
            x1 = x - ((fx * epsilonX) / (getFX(whichFunction, x + epsilonX) - fx));
            fx1 = getFX(whichFunction, x1);
            error = Math.abs(x1 - x) / x1;
            error = posError(error); //makes error positive if it is negative

            printModifiedSecant(n, x, x1, fx, fx1, fxPrime, error);
            writeToFile(n, error, out);

            //checks for divergence 
            if (error > DIVERGING_ERROR || Double.isNaN(x1) || Double.isInfinite(x1)) {
                System.out.println("Mofified Secant Method: Error. This equation is diverging.");
                out.println("Error. This equation is diverging.");
                return;
            }

            //found root or root does not exist 
            if (Math.abs(fx1) < .01 || error < ERROR)
            {
                if(isRoot(fx)) //root found 
                {
                    System.out.printf("Modified Secant Method: The root %.3f has been found with a starting guess of x = %.3f for the " +
                                        "function #%d in %d iterations.\n", x1, xInitial, whichFunction, n+1);
                    out.println("The root is: " + x1);
                }
                else //false alarm 
                {
                    System.out.printf("Modified Secant Method: There are no roots found from the starting guess of x = %.3f for " +
                                        "function #%d.\n", xInitial, whichFunction);
                    out.println("Root does not exist");

                }
                return;
            }

            if(fxPrime == 0)
            {
                System.out.println("Error: Stuck on a horizontal tangent.");
                out.println("Error. Horizontal tangent.");
                return;
            }

            x = x1;
            n++;

        }
    }
}