package myapp;

import calcengine.CalculateHelper;
import calcengine.InvalidStatementException;
import calcengine.MathEquation;
import calcengine.CalculateBase;
import calcengine.Adder;
import calcengine.Subtracter;
import calcengine.Multiplier;
import calcengine.Divider;

public class Main {

    public static void main(String[] args) {
        useMathEquation();
        useCalculatorBase();
        useCalculateHelper();

        String[] statements = {
                "add 25.0 92.0",       // 25.0 + 92.0 = 117.0
        };

        for(String statement:statements) {

        }

    }

    static void useCalculateHelper() {
        String[] statements = {
                "add 1.0",             // Error: incorrect number of values
                "add xx 25.0",         // Error: non-numeric data
                "addX 0.0 0.0",        // Error: invalid command
                "divide 100.0 50.0",   // 100.0 / 50.0 = 2.0
                "add 25.0 92.0",       // 25.0 + 92.0 = 117.0
                "subtract 225.0 17.0", // 225.0 - 17.0 = 108.0
                "multiply 11.0 3.0"    // 11.0 * 3.0 = 33.0
        };

        CalculateHelper helper = new CalculateHelper();
        for(String statement:statements) {
            try {
                helper.process(statement);
                System.out.println(helper);
            } catch(InvalidStatementException e) {
                System.out.println(e.getMessage());
                if(e.getCause() != null)
                    System.out.println("  Original exception: " + e.getCause().getMessage());
            }
        }
    }

    static void useMathEquation() {
        MathEquation[] equations = new MathEquation[4];
        equations[0] = new MathEquation('d', 100.0d, 50.0d);
        equations[1] = new MathEquation('a', 25.0d, 92.0d);
        equations[2] = new MathEquation('s', 225.0d, 17.0d);
        equations[3] = new MathEquation('m', 11.0d, 3.0d);

        for(MathEquation equation : equations) {
            equation.execute();
            System.out.print("result = ");
            System.out.println(equation.getResult());
        }

        System.out.println();
        System.out.println("Using Overloads");
        System.out.println();

        double leftDouble = 9.0d;
        double rightDouble = 4.0d;
        int leftInt = 9;
        int rightInt = 4;

        MathEquation equationOverload = new MathEquation('d');

        equationOverload.execute(leftDouble, rightDouble);
        System.out.print("result=");
        System.out.println(equationOverload.getResult());

        equationOverload.execute(leftInt, rightInt);
        System.out.print("result=");
        System.out.println(equationOverload.getResult());

        equationOverload.execute((double) leftInt, rightInt);
        System.out.print("result=");
        System.out.println(equationOverload.getResult());
    }

    static void useCalculatorBase() {
        System.out.println();
        System.out.println("Using Inheritance");
        System.out.println();

        CalculateBase[] calculators = {
                new Divider(100.0d, 50.0d),
                new Adder(25.0d, 92.0d),
                new Subtracter(225.0d, 17.0d),
                new Multiplier(11.0d, 3.0d)
        };

        for(CalculateBase calculator:calculators) {
            calculator.calculate();
            System.out.print("result=");
            System.out.println(calculator.getResult());
        }
    }
}
