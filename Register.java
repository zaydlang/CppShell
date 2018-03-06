import java.util.ArrayList;
import java.util.HashMap;

public class Register {
	private static HashMap<String, Variable> vars = new HashMap<String, Variable>();
	
	public static void addVariable(ArrayList<Token> inputTokens) throws Exception {
		// Verify the notation is correct!
		double result = 0;
		
		if (inputTokens.size() == 2) {
			result = 0; // c++ values are default 0
		} else {
			if (!(inputTokens.get(1).getValue() == 1000) || !(inputTokens.get(2).getValue() == 403)) return; // TODO: better handling
		
			try {
				ExpressionSolver es = new ExpressionSolver(inputTokens);
				result = Double.parseDouble(es.simplify());
			} catch (Exception e) {
				throw e;
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
			default:
				throw new Exception("java.ccompile.UnknownTokenException");
		}
		
		vars.put(inputTokens.get(1).getIdentity(), var);
	}
	
	public static void updateVariable(ArrayList<Token> inputTokens) throws Exception {
		if (!(inputTokens.get(1).getValue() == 403)) return; // TODO: better handling
		double result = 0;
		try {
		    ExpressionSolver es = new ExpressionSolver(inputTokens);
		    result = Double.parseDouble(es.simplify());
		} catch (NullPointerException e) {
			throw e;
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
