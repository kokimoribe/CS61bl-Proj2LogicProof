import java.util.*;

public class TheoremSet {
	
	private ArrayList<Theorem> myList;
	private ArrayList<String> reservedNames = new ArrayList<String>(Arrays.asList("print", "repeat", "show", "assume", "mp", "mt", "co", "ic", "=>", "&", "|", "~"));
	public TheoremSet ( ) {
		myList = new ArrayList<Theorem>();
	}

	public Expression put (String s, Expression e) throws IllegalLineException {
		
		StringTokenizer st = new StringTokenizer(s);
		if (st.countTokens() != 1) {
			throw new IllegalLineException("Invalid theorem name: name must be one word");
		}
		if (reservedNames.contains(st.nextToken())) {
			throw new IllegalLineException("Invalid theorem name: " + s);
		}
		
		if (getNames().contains(s)) {
			myList.remove(get(s));
		}
		myList.add(new Theorem(s, e));
		return e;
	}
	
	public ArrayList<Theorem> getList() {
		return myList;
	}
	
	//returns the theorem's expression given the name
	public Expression find(String s) {
		for (Theorem t : myList) {
			if (t.getTitle().equals(s)) {
				return t.getExpression();
			}
		}
		
		return null;
	}
	
	//returns the theorem object given the name
	public Theorem get(String s) {
		for (Theorem t : myList) {
			if (t.getTitle().equals(s)) {
				return t;
			}
		}
		return null;
	}
	
	public ArrayList<String> getNames() {
		ArrayList<String> rtn = new ArrayList<String>();
		for (Theorem t: myList) {
			rtn.add(t.getTitle());
		}
		
		return rtn;
	}
}
