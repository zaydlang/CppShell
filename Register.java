import java.util.ArrayList;
import java.util.HashMap;

public class Register {
	private static HashMap<String, Variable> vars = new HashMap<String, Variable>();
	
	static {
		vars.put("NULL", null);
	}
	
	public static void addVariable(ArrayList<Token> inputTokens) {
		// Verify the notation is correct!
		if (!(inputTokens.get(1).getValue() == 1000) || !(inputTokens.get(2).getValue() == 403)) return; // TODO: better handling
		double result = 0;
		
		try {
		    ExpressionSolver es = new ExpressionSolver(inputTokens);
		    result = Double.parseDouble(es.simplify());
		} catch (NullPointerException e) {
			if (inputTokens.get(3).getIdentity().equals("NULL") && inputTokens.size() == 4) {
			    vars.put(inputTokens.get(1).getIdentity(), null);
			}
		}
        
        Variable var = null;
		switch (inputTokens.get(0).getIdentity()) {
			case "int":
				var = new VarInteger((int)result);
				break;
			case "double":
				var = new VarDouble(result);
				break;
		}
		
		vars.put(inputTokens.get(1).getIdentity(), var);
	}
	
	public static void updateVariable(ArrayList<Token> inputTokens) {
		if (!(inputTokens.get(1).getValue() == 403)) return; // TODO: better handling
		double result = 0;
		try {
		    ExpressionSolver es = new ExpressionSolver(inputTokens);
		    result = Double.parseDouble(es.simplify());
		} catch (NullPointerException e) {
			if (inputTokens.get(2).getIdentity().equals("NULL") && inputTokens.size() == 3) {
			    vars.put(inputTokens.get(1).getIdentity(), null);
			}
		}
		
		Variable var = null;
		switch (Register.getVar(inputTokens.get(0).getIdentity()).getType()) {
			case 0:
				var = new VarInteger((int)result);
				break;
			case 1:
				var = new VarDouble(result);
				break;
		}
		
		vars.put(inputTokens.get(0).getIdentity(), var);
	}
	
	public static Variable getVar(String name) {
		return vars.get(name);
	}
}
