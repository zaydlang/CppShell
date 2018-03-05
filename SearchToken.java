import java.util.ArrayList;
import java.util.regex.*;

public class SearchToken {
	private Pattern regex;
	private int value;
	
	public SearchToken(String regex, int value) {
		this.regex = Pattern.compile(regex);
		this.value = value;
	}
	
	public Pattern getRegex() {
		return regex;
	}
	
	public int getValue() {
		return value;
	}
}
