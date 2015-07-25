
public class Theorem {
	
	String myTitle;
	Expression myExp;
	
	public Theorem (String s, Expression e) {
		myTitle = s;
		myExp = e;
	}
	
	public String getTitle() {
		return myTitle;
	}
	
	public Expression getExpression() {
		return myExp;
	}
	
	public boolean equals(Theorem t) {
		return (t.getTitle().equals(myTitle) && t.getExpression().equals(myExp));
	}
}
