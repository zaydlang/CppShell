
import java.util.ArrayList;
import java.lang.Character;
import java.lang.Double;
import java.lang.Math;
import java.util.Scanner;
import java.util.Stack;

/**
 * Symbol is an Abstract Class with 3 properties:
 * private String id is the string representation of the Symbol.
 * private int strength is how strong the operator is.
 * private int value is the value of the operand.
 * @author      Zayd Qumsieh
 */
class Symbol {
   private String id;
   private int strength;
   protected double value;
   
   /** 
    * Constructor.
    * @param id the id
    * @param strength the strength
    */
   public Symbol(String id, int strength) {
      this.id = id;
      this.strength = strength;
   }
   
   /** 
    * Getter for id
    * @return the id
    */
   public String getType() { return id; }
   
   /** 
    * Getter for value
    * @return the value
    */
   public double getValue() { return value; }
   
   
   /** 
    * Action method for simplification
    * @param left operand #1
    * @param right operand #2
    * @return the result
    */
   public double action(double left, double right) {
      return 1.0;
   }
   
   /** 
    * Getter for strength
    * @return the strength
    */
   public int getStrength() { return strength; }
}

/**
 * Plus extends Symbol
 * private String id is "+"
 * private int strength is 10, which is the lowest strength.
 * private int value is the value of the operand, which is null
 * @author      Zayd Qumsieh
 */
class Plus extends Symbol {
   /** 
    * Constructor.
    */
   public Plus() {
      super("+", 10);
   }
   
   /** 
    * Action method for simplification
    * @param left operand #1
    * @param right operand #2
    * @return left + right
    */
   public double action(double left, double right) {
      return left + right;
   }
}

/**
 * Minus extends Symbol
 * private String id is "-"
 * private int strength is 10, which is the lowest strength.
 * private int value is the value of the operand, which is null
 * @author      Zayd Qumsieh
 */
class Minus extends Symbol {
   /** 
    * Constructor.
    */
   public Minus() {
      super("-", 10);
   }
   
   /** 
    * Action method for simplification
    * @param left operand #1
    * @param right operand #2
    * @return left + right
    */
   
   public double action(double left, double right) {
      return left - right;
   }
}

/**
 * Astrix extends Symbol
 * private String id is "*"
 * private int strength is 20, which is the second-lowest strength.
 * private int value is the value of the operand, which is null
 * @author      Zayd Qumsieh
 */
class Astrix extends Symbol {
   /** 
    * Constructor.
    */
   public Astrix() {
      super("*", 20);
   }
   
   /** 
    * Action method for simplification
    * @param left operand #1
    * @param right operand #2
    * @return left * right
    */
   
   public double action(double left, double right) {
      return left * right;
   }
}

/**
 * FowardSlash extends Symbol
 * private String id is "/"
 * private int strength is 20, which is the second-lowest strength.
 * private int value is the value of the operand, which is null
 * @author      Zayd Qumsieh
 */
class ForwardSlash extends Symbol { 
   /** 
    * Constructor.
    */
   public ForwardSlash() {
      super("/", 20);
   }
   
   /** 
    * Action method for simplification
    * @param left operand #1
    * @param right operand #2
    * @return left / right
    */
   
   public double action(double left, double right) {
      return left / right;
   }
}

/**
 * Exponent extends Symbol
 * private String id is "^"
 * private int strength is 30, which is the highest strength.
 * private int value is the value of the operand, which is null
 * @author      Zayd Qumsieh
 */
class Exponent extends Symbol {
   /** 
    * Constructor.
    */
   public Exponent() {
      super("^", 30);
   }
   
   /** 
    * Action method for simplification
    * @param left operand #1
    * @param right operand #2
    * @return left ^ right
    */
   
   public double action(double left, double right) {
      return Math.pow(left, right);
   }
}

/**
 * Number extends Symbol
 * private String id is "number"
 * private int strength doesn't matter in this case.
 * private int value is the value of the operand, which is the value of the number.
 * @author      Zayd Qumsieh
 */
class Number extends Symbol {
   /** 
    * Constructor.
    * @param number the value of the number
    */
   public Number(double number) {
      super("number", 536392); // strength here doesnt matter so i had some fun with it
      value = number;
   }
}

/**
 * ExpressionSolver has methods that solve expresions made up of Symbols.
 * @author Zayd Qumsieh
 */
class ExpressionSolver {
   private ArrayList<Symbol> expression = new ArrayList<Symbol>();
   
   /** 
    * Constructor.
    * @param input a string representation of the expression.
    */
   public ExpressionSolver(String input) {
      stringToSymbols(input);
   }
   
   public ExpressionSolver(ArrayList<Token> inputTokens) {
      for (int i = inputTokens.get(0).getValue() / 100 == 1 ? 3 : 2; i < inputTokens.size(); i++) {
         switch (inputTokens.get(i).getValue()) {
         	case 404:
         	   expression.add(new Plus());
         	   break;
         	case 405:
         	   expression.add(new Minus());
         	   break;
         	case 406:
         	   expression.add(new Astrix());
         	   break;
         	case 407:
         	   expression.add(new ForwardSlash());
         	   break;
         	case 408:
         	   expression.add(new Exponent());
         	   break;
         	case 1000:
         	   Variable var = Register.getVar(inputTokens.get(i).getIdentity());
         	   if (var.getType() == 0) {
         	      double value = (int)var.getValue();
         	      expression.add(new Number(value));
         	   }
         	   
         	   if (var.getType() == 1) {
         	      double value = (double)var.getValue();
         	      expression.add(new Number(value));
         	   }
         	   
         	   break;
         	case 1002:
         	   expression.add(new Number(Double.parseDouble(inputTokens.get(i).getIdentity())));
         	   break;
         }
      }
   }
   
   /** 
    * Converts expression from inFix to postFix.
    */
   public void inFixToPostFix() {
      Stack<Symbol> symbolStack = new Stack<Symbol>();
      ArrayList<Symbol> rpnExpression = new ArrayList<Symbol>();
      
      for (int i = 0; i < expression.size(); i++) {
         if (expression.get(i).getType().equals("number")) {
            rpnExpression.add(expression.get(i));
         }
         
         else if (symbolStack.size() == 0 || symbolStack.peek().getStrength() < expression.get(i).getStrength()) {
            symbolStack.push(expression.get(i));
         } else {
            while (symbolStack.size() != 0 && symbolStack.peek().getStrength() >= expression.get(i).getStrength()) {
               rpnExpression.add(symbolStack.pop());
            }
            
            symbolStack.push(expression.get(i));
         }
      }
      
      while (symbolStack.size() != 0) rpnExpression.add(symbolStack.pop());
      expression = rpnExpression;
   }
   
   /** 
    * Converts the input in the constructor to an ArrayList of Symbols and stores in expression.
    * @param input the string representation of the expression
    */
   public void stringToSymbols(String input) {
      for (int i = 0; i < input.length(); i++) {
         // operators
         if (input.charAt(i) == ' ') {}
         else if (!Character.isDigit(input.charAt(i))) {
         	/*
	   		String identifier = "";
	   	    int m;
		    for (m = i; m < input.length() && input.charAt(m) != ' '; m++) {
		    	identifier += input.charAt(m);
		    }
		    
		    int n;
		    for (n = 0; n < identifier.length() && Character.isDigit(identifier.charAt(n)); n++) {}
		    
		    if (n != identifier.length()) {
		    	expression.add(new Number((double)(Register.getVar(identifier).getValue())));
		    } else {
           		expression.add(convertToSymbol(Character.toString(input.charAt(i))));
           	}*/
           	expression.add(convertToSymbol(Character.toString(input.charAt(i))));
               
         // digits
         } else {
            i = convertToNum(input, i);
         }
      }
   }
   
   /** 
    * Converts a string into an operator
    * @param input the string representation of the operator
    * @return the operator (symbol)
    */
   public Symbol convertToSymbol(String input) {
        if (input.equals("+")) {
            Plus temp = new Plus();
            return temp;
        }

        if (input.equals("-")) {
            Minus temp = new Minus();
            return temp;
        }

        if (input.equals("*")) {
            Astrix temp = new Astrix();
            return temp;
        }

        if (input.equals("/")) {
            ForwardSlash temp = new ForwardSlash();
            return temp;
        }  

        if (input.equals("^")) {
            Exponent temp = new Exponent();
            return temp;
        }  
        
        return null;
    }

   /** 
    * Converts the input into a number.
    * @param input the string representation of the entire expression.
    * @param i the starting location of the number.
    * @return the index that stringToSymbols should continue parsing from.
    */
   public int convertToNum(String input, int i) {
        
        double num = 0;
        int j;
        for (j = i; j != input.length() && (Character.isDigit(input.charAt(j)) || input.charAt(j) == '.'); j++) {
            // decimals
            if (input.charAt(j) == '.') {
                double multiplier = 0;
                double decimal = 0;
                j++;

                for (int k = j; k != input.length() && Character.isDigit(input.charAt(k)); k++) {
                    decimal = (decimal * 10) + (input.charAt(k) - 48);
                    j = k;
                    multiplier--;
                }

                num += decimal * Math.pow(10, multiplier);
            } else {
                num = (num * 10) + (double)(input.charAt(j) - 48);
            }

            // i = j
        }

        expression.add(new Number(num));    
        return j - 1;
    }
    
   /** 
    * Simplifies the expression.
    * @return the result.
    */
   public String simplify() {
        inFixToPostFix();
        for (int i = 0; expression.size() != 1; i = 0) {
            for (int j = 0; j < expression.size(); j++) {
                if (expression.get(j).getType() != "number") {
                   i = j;
                   break;
                }          
            }
		    /*Number temp = new Number(expression.get(i).action(expression.get(i - 1).getValue(), expression.get(i + 1).getValue()));*/
          // cout << "OPERATION # " << i << value[i - 1]->getValue() << ", " << value[i + 1]->getValue() << endl;
          Number temp = new Number(expression.get(i).action(expression.get(i - 2).getValue(), expression.get(i - 1).getValue()));
		    expression.set(i - 2, temp);
		    expression.remove(i - 1);
		    expression.remove(i - 1);
        }

        return Double.toString(expression.get(0).getValue());
    }
    
    public ArrayList<Symbol> getExpression() { return expression; }
}
