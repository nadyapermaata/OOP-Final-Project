
public abstract class ChessPiece {

	private int values;
	
	public ChessPiece(int values) {
		this.values = values;
	}
	
	public int getValues() {
		return values;
	}
	
	public void setValues(int values) {
		this.values = values;
	}
	
	public abstract String Possible();
}
