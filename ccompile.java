import java.util.Scanner;
import java.util.ArrayList;

import java.io.File;
import java.io.FileNotFoundException;

public class ccompile {
	private static ArrayList<Token> tokenizedCode = new ArrayList<Token>();
	private static Tokenizer t;
	
	public static void main(String[] args) {
		// Initialize Stuffs
		Scanner s = new Scanner(System.in);
		String input = "";
		ArrayList<Token> inputTokens;
		
		t = new Tokenizer();
		t.addSearchToken("int",    100);
		t.addSearchToken("double", 101);
		t.addSearchToken("bool",   102);
		t.addSearchToken("string", 103);
		t.addSearchToken("char",   104);
		
		t.addSearchToken("cout",  300);
		t.addSearchToken("endl",  301);
		
		t.addSearchToken("<<",    401);
		t.addSearchToken(">>",    402);
		t.addSearchToken("=",     403);
		t.addSearchToken("\\+",   404);
		t.addSearchToken("-",     405);
		t.addSearchToken("\\*",   406);
		t.addSearchToken("/",     407);
		t.addSearchToken("\\^",   408);
		
		t.addSearchToken("(([a-z]|[A-Z]))+", 1000);     // identifiers
		t.addSearchToken("^\"(([a-z]|[A-Z]))+\"$", 1001); // strings
		t.addSearchToken("[0-9]+.*[0-9]*", 1002);            // numbers
		// t.add("(([a-z]|[A-Z])+)\(\)", 500); i probably don't need to add functions...
		
		// Main Loop
		System.out.print("@: ");
		try {
			do {
				input += s.nextLine();
				if (input.charAt(input.length() - 1) == ';') {
					input = input.substring(0, input.length() - 1);
					inputTokens = t.tokenize(input);
					readTokens(inputTokens);
					input = "";
					System.out.print("@: ");
				}
			} while (input != "exit");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static void readTokens(ArrayList<Token> inputTokens) throws Exception {
    	if (inputTokens.get(0).getValue() / 100 == 1) {
    		Register.addVariable(inputTokens);
    	}
    	
    	if (inputTokens.get(0).getValue() == 300) {
    		cout(inputTokens);
    	}
    }
    
    public static void cout(ArrayList<Token> inputTokens) throws Exception {
    	// yes, i know this isn't the traditional c++ method of using streams to print, but i'm too lazy to 
    	// make a stream in java, so this will have to do.
    	String s = "";
    	for (int i = 1; i < inputTokens.size(); i++) {
    		if (!(inputTokens.get(i).getValue() == 401)) throw new Exception();
    		i++;
    		if ((inputTokens.get(i).getValue() == 301)) s += "\n";
    		if ((inputTokens.get(i).getValue() == 1001)) s += inputTokens.get(i).getIdentity();
    		if ((inputTokens.get(i).getValue() == 1000)) {
    			if (Register.getVar(inputTokens.get(i).getIdentity()).getValue() == null) {
    				throw new NullPointerException();
    			}
    			
    			s += Register.getVar(inputTokens.get(i).getIdentity()).getValue();
    		}
    	}
    	
    	System.out.print(s);
    }
}
