import java.util.*;

public class Expression {
	//Expression is represented by a binary tree
	//if the node is a variable (single lower case letter), then node will not have left and right branches
	//if the node is &, |, or =>, it will have left and right branches
	//if the node is ~, it will have a left branch, but no right branch
	
	
	private BinaryTree myExp;
	private String myString;

	public Expression (String s) throws IllegalLineException {
		
		myString = s;
		
		if (s.length() != 0) {
			myExp = BinaryTree.exprTree(s);
		}
		
		else {
			throw new IllegalLineException("bad expression: " + s);
		}
	}
	private Expression (BinaryTree b) {
		myString = null;
		myExp = b;
	}
	
	public Expression getLeft () {
		return new Expression(myExp.getLeft());
	}
	
	public Expression getRight() {
		return new Expression(myExp.getRight());
	}
	
	public String getMain() {
		return (String) myExp.getMain();
	}
	
	//expression is simple if left and right are both null
	//we only need to check left b/c all expressions with an operator will have a left branch
	public boolean isSimple() {
		return !(myExp.hasLeft());
	}
	
	public String toString() {
		return myString;
	}
	
	public boolean equals(Expression e) {
		
		return myExp.toString().equals(e.myExp.toString());
	}
	
	private HashMap<String, Expression> check = new HashMap<String,Expression>();
	
	public boolean matches(Expression e) {
		
		return matchesHelper(this, e);
	}
	
	//want to make this static, but it relies on a hashmap that relies on the scope of the entire tree
	private boolean matchesHelper(Expression theorem, Expression e) {
		
		if (!theorem.isSimple()) {
			//if theorem is not simple, expression e must also not be simple
			if (!e.isSimple()) {
				if (theorem.getMain().equals(e.getMain())) {				
					if (theorem.myExp.hasRight() == e.myExp.hasRight()) {
						//find out if left expressions match
						boolean left = matchesHelper(theorem.getLeft(), e.getLeft());
						
						//if theorem has a right expression, test for match
							//a theorem won't have a right expression if the expression is a ~
						//left and right must both match
						if (theorem.myExp.hasRight()) {
							return left && matchesHelper(theorem.getRight(), e.getRight());
						}
						
						//if theorem has only left expression (i.e. operator was a ~), no need to check right
						return left;
					}
					
					//hasRight did not match
					return false;
				}
				
				//operators did not match
				return false;
			}
			
			//expression e is simple when theorem is not simple
			return false;
		}
		
		if (!check.containsKey(theorem.getMain())) {
			check.put(theorem.getMain(), e);
			return true;
		}
		
		if (check.get(theorem.getMain()).equals(e)) {
			return true;
		}
		
		//variables didn't match
		return false;
	}
}
