// I used this site to figure out how to write a compiler: http://cogitolearning.co.uk/2013/04/writing-a-parser-in-java-the-tokenizer/
// I wrote the code on my own, but I thought I might as well cite my sources!
import java.util.ArrayList;
import java.util.regex.*;
import java.util.Scanner;

public class Tokenizer {
	ArrayList<SearchToken> searchTokens;
	
	public Tokenizer() {
		searchTokens = new ArrayList<SearchToken>();
	}
	
	public void addSearchToken(String regex, int id) {
		searchTokens.add(new SearchToken(regex, id));
	}
	
	public ArrayList<Token> tokenize(String inputLine) {
		String[] input = inputLine.split(" ");
		ArrayList<Token> inputTokens = new ArrayList<Token>();
		
		for (int j = 0; j < input.length; j++) {
			String word = input[j];
			for (int i = 0; i < searchTokens.size(); i++) {
				Matcher match = searchTokens.get(i).getRegex().matcher(word);
			
				if (match.find()) {
					inputTokens.add(new Token(searchTokens.get(i).getValue(), word));
					break;
				}
			}
		}
		
		return inputTokens;
	}
}
