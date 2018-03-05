public class Variable {
	public Object value;
	public int type;
	
	public Variable(Object value, int type) {
		this.value = value;
		this.type  = type;
	}
	
	public Object getValue() {
		return value;
	}
	
	public int getType() {
		return type;
	}
}
