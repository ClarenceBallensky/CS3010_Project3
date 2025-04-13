import java.io.*;


public class driver {
    public static void main(String[] args) throws Exception {

        try 
        {

            PrintWriter out = new PrintWriter(new FileWriter("output.txt"));

            //Function #1 
            locateRoots.printToFile("Function #1", out);
            //Bisection
            locateRoots.printToFile("\nBisection Method", out);

            locateRoots.header(out);
            locateRoots.bisection(1, 0, 1, out);

        
            locateRoots.header(out);
            locateRoots.bisection(1, 1, 2, out);

            locateRoots.header(out);
            locateRoots.bisection(1, 2, 3, out);

            locateRoots.header(out);
            locateRoots.bisection(1, 3, 4, out);

            //False Postion
            locateRoots.printToFile("\nFalse Position Method", out);

            locateRoots.header(out);
            locateRoots.falsePosition(1, 0, 1, out);

            locateRoots.header(out);
            locateRoots.falsePosition(1, 1, 2, out);

            locateRoots.header(out);
            locateRoots.falsePosition(1, 2, 3, out);

            locateRoots.header(out);
            locateRoots.falsePosition(1, 3, 4, out);

            //Netwon Raphson
            locateRoots.printToFile("\nNewton Raphson Method", out);

            locateRoots.header(out);
            locateRoots.newtonRaphson(1, 0, out);

            locateRoots.header(out);
            locateRoots.newtonRaphson(1, 1, out);

            locateRoots.header(out);
            locateRoots.newtonRaphson(1, 2, out);

            locateRoots.header(out);
            locateRoots.newtonRaphson(1, 3 ,out);

            //Secant
            locateRoots.printToFile("\nSecant Method", out);

            locateRoots.header(out);
            locateRoots.secant(1, 0, 1, out);

            locateRoots.header(out);
            locateRoots.secant(1, 1, 2, out);

            locateRoots.header(out);
            locateRoots.secant(1, 2, 3, out);

            locateRoots.header(out);
            locateRoots.secant(1, 3, 4, out);

            //Modified Secant
            locateRoots.printToFile("\nModified Secant Method", out);

            locateRoots.header(out);
            locateRoots.modifiedSecant(1, 0, out);

            locateRoots.header(out);
            locateRoots.modifiedSecant(1, 1, out);

            locateRoots.header(out);
            locateRoots.modifiedSecant(1, 2, out);

            locateRoots.header(out);
            locateRoots.modifiedSecant(1, 3, out);



            //Function #2
            locateRoots.printToFile("\n\nFunction #2", out);
            //Bisection
            locateRoots.printToFile("\nBisection Method", out);

            locateRoots.header(out);
            locateRoots.bisection(2, 120, 130, out);

            //False Position
            locateRoots.printToFile("\nFalse Position Method", out);

            locateRoots.header(out);
            locateRoots.falsePosition(2, 120, 130, out);

            //Newton Raphson
            locateRoots.printToFile("\nNewton Raphson Method", out);

            locateRoots.header(out);
            locateRoots.newtonRaphson(2, 130, out);

            //Secant
            locateRoots.printToFile("\nSecant Method", out);

            locateRoots.header(out);
            locateRoots.secant(2, 120, 130, out);

            //Modified Secant
            locateRoots.printToFile("\nModified Secant Method", out);

            locateRoots.header(out);
            locateRoots.modifiedSecant(2, 130, out);
            
            out.close();
        }
        catch (IOException e) 
        {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
