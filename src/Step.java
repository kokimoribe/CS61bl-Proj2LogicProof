
/* 
 * A class that contains relevant info about each line of a proof. 
 * Each node and leaf of the tree structure in Proof will be 
 * exactly one instance of the Step class (as in "Step" of the proof).
 */

import java.util.LinkedList;

public class Step {
	// A Step should keep track of its unique line number and expression object
	// as well as the reason in that line and any references to previous lines. 
	
	private LineNumber line;
	private String reason;
	private LinkedList<Step> prevLines = new LinkedList<Step>();
	private Expression expr;
	
	public Step (String r) {
		this.reason = r;
	}
	
	public Step (LineNumber l, String r, Expression e) {
		this.line = l;
		this.reason = r;
		this.expr = e;
	}
	
	// Returns String representation of a Step object
	public String toString () {
		String a = "";
		a += this.line.toString();
		a += "\t";
		a += this.reason;
		a += " ";
		if (this.prevLines.size() != 0) {
			for (Step n : this.prevLines){
				a += n.line.toString();
				a += " ";
			}
		}
		a += this.expr;
		return a;
	}
	
	// Getter and setter methods
	
	public LineNumber getLineNumber () {
		return this.line;
	}
	
	public void setLineNumber (LineNumber n) {
		this.line = n;
	}
	
	public String getReason () {
		return this.reason;
	}
	
	public void setReason (String r) {
		this.reason = r;
	}
	
	public LinkedList<Step> getPrevLines () {
		return this.prevLines;
	}
	
	public void setPrevLines (Step s) {
		this.prevLines.add(s);
	}
	
	public void setPrevLines (Step s1, Step s2) {
		this.prevLines.add(s1);
		this.prevLines.add(s2);
	}
	
	public Expression getExpression () {
		return this.expr;
	}
	
	public void setExpression (Expression e) {
		this.expr = e;
	}
}
