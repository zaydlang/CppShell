import java.util.ArrayList;
import java.util.regex.*;

public class Token {
	private int value;
	private String identity;
	
	public Token(int value, String identity) {
		this.value = value;
		this.identity = identity;
	}
	
	/*
	public static Token getToken(String input) {
		// Is a string?
		if (input.at(0).equals('"')) {
			for (int i = 1; i < input.size && !input.at(i).equals('"'); i++) {
				if (input.at(i) == '\') {
					i++;
				}
			}
			
			// string is valid!
		    if (i == input.size) {
		    	return new Token(101, input.substring(1, input.size - 1);
		    }
		} else
		
		// miscellaneous tokens
		if (input == "main()") { return new Token(0, input); }
		
		if (input == "int")    { return new Token(100, input); }
		
		if (input == "cout")   { return new Token(300, input); }
		if (input == "endl")   { return new Token(301, input); }
		
		if (input == "<<")     { return new Token(400, input); }
		if (input == ">>")     { return new Token(401, input); }
		
		if (input == "{")      { return new Token(501, input); }
		if (input == "}")      { return new Token(502, input); }
		
		if (input == ";")      { return new Token(600, input); }
	}*/
	
	public int getValue() {
		return value;
	}
	
	public String getIdentity() {
		return identity;
	}
}
