import java.util.*;

public class BinaryTree {

	private TreeNode myRoot;
	
	public BinaryTree ( ) {
		myRoot = null;
	}
	
	public BinaryTree (TreeNode t) {
		myRoot = t;
	}
	
	public BinaryTree getLeft() {
		return new BinaryTree(myRoot.myLeft);
	}
	
	public BinaryTree getRight() {
		return new BinaryTree(myRoot.myRight);
	}
	
	public Object getMain() {
		return myRoot.myItem;
	}
	
	public boolean hasLeft() {
		return myRoot.myLeft != null;
	}
	
	public boolean hasRight() {
		return myRoot.myRight != null;
	}
	
	private static class TreeNode {
		
		public Object myItem;
		public TreeNode myLeft;
		public TreeNode myRight;
		
		public TreeNode (Object obj) {
			myItem = obj;
			myLeft = myRight = null;
		}
		
		public TreeNode (Object obj, TreeNode left, TreeNode right) {
			myItem = obj;
			myLeft = left;
			myRight = right;
		}
	}
	
	public static BinaryTree exprTree (String s) throws IllegalLineException{
	    BinaryTree result = new BinaryTree ( );
	    result.myRoot = result.exprTreeHelper (s);
	    return result;
	}
	
	private TreeNode exprTreeHelper (String expr) throws IllegalLineException{
		
		if (expr.length() == 0) {
			throw new IllegalLineException("bad expression " + expr);
		}
		
	    if (expr.charAt (0) != '(') {
	    		    	
	    	if (expr.length() == 1 && Character.isLowerCase(expr.charAt(0))) {
	    		return new TreeNode(expr);
	    	}
	    	
	    	if (expr.length() > 1 && expr.charAt(0) == '~') {
	    		return new TreeNode("~", exprTreeHelper(expr.substring(1)), null);
	    	}
	    	
	    	throw new IllegalLineException("bad expression: " + expr);
	    		    		
	    } else {
	    	if (expr.charAt(expr.length() - 1) != ')' || expr.length() < 5) {
	    		throw new IllegalLineException("bad expression: " + expr);
	    	}
	    	
	        int nesting = 0;
	        int opPos = 0;
	        char check;
	        int addLength = 0;
	        for (int k=1; k<expr.length()-1; k++) {
	        	check = expr.charAt(k);
	        	if (check == '(') {
	        		nesting++;
	        	} 
	        	
	        	else if (check == ')') {
	        		nesting--;
	        	}
	        	
	        	else if (check == '&' || check == '|') {
	        		if (nesting == 0) {
	        			opPos = k;
	        			break;
	        		}
	            }
	            
	        	else if (check == '=') {
	        		if (k + 2 < expr.length() && expr.charAt(k+1) == '>') {
	        			if (nesting == 0) {
		        			opPos = k;
		        			addLength = 1;
		        			break;
	        			}
	        			
	        			else {
	        				k++;
	        			}
	        		}
	        		
	        		else {
	        			throw new IllegalLineException("bad expression: " + expr);
	        		}
	            }
	        	
	        	else if (check == '~' | Character.isLowerCase(check)) {
	        	}
	        	
	        	else {
	        		throw new IllegalLineException("bad expression: " + expr);
	        	}	            
	        }
	        
	        if (nesting != 0) {
	        	throw new IllegalLineException("bad expression: " + expr);
	        }
	        
	        if (opPos == 0) {
	        	throw new IllegalLineException("bad expression: " + expr);
	        }
	        
	        String opnd1 = expr.substring (1, opPos);
	        String opnd2 = expr.substring (opPos+1+addLength, expr.length()-1);
	        String op = expr.substring (opPos, opPos+1+addLength);
	        return new TreeNode(op, exprTreeHelper(opnd1), exprTreeHelper(opnd2)); 
	    }
	}
	
	public String toString() {
		return toStringHelper(myRoot);
	}
	
	private String toStringHelper(TreeNode t) {
		if (t != null) {
			return (String) t.myItem + toStringHelper(t.myLeft) + toStringHelper(t.myRight);
		}
		
		return "";	
	}
}