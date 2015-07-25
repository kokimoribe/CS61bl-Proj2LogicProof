/*
 * LineNumber objects have a list of integers representing a whole
 * line number for a step in the proof.
 * Each Proof instance should keep track of the current LineNumber
 * and make new line numbers by calling the methods below.
 */

import java.util.*;

public class LineNumber {
	
	private LinkedList<Integer> line;
	
	public LineNumber () {
		this.line = new LinkedList<Integer> ();
	}
	
	public LineNumber (int start) {
		this.line = new LinkedList<Integer> ();
		this.line.add(start);
	}
	
	public LineNumber(String str) throws IllegalLineException{
		if (str.length() == 0) {
			throw new IllegalLineException("invalid line number");
		}
		this.line = new LinkedList<Integer> ();
		StringTokenizer st = new StringTokenizer(str, ".");
		while(st.hasMoreTokens()) {
			try {
				line.addLast(Integer.parseInt(st.nextToken()));
			}
			
			catch (NumberFormatException e) {
				throw new IllegalLineException("invalid line number: " + str);
			}
		}
	}
	// Several helper methods to preserve abstraction.
	// Most of these methods simply call corresponding LinkedList methods.
	public void add (Integer i) {
		this.line.addLast(i);
	}
	
	private void removeLast () {
		this.line.removeLast();
	}
	
	public int size () {
		return this.line.size();
	}
	
	public int get (int index) {
		Integer a = this.line.get(index);
		return a.intValue();
	}
	
	public int getLast() {
		Integer a = this.line.getLast();
		return a.intValue();
	}
	
	public void set (int index, Integer elem) {
		this.line.set(index, elem);
	}
	
	
	
	// Returns a new LineNumber object representing the next line in 
	// a continuing proof or lemma.
	public LineNumber nextLine () {
		LineNumber a = new LineNumber();
		for (Integer i : this.line) {
			a.add(i);
		}
		int last = a.getLast();
		a.set(a.size() - 1, last + 1);
		return a;
	}
	
	// Returns a new LineNumber object representing the next line
	// after a lemma has been completed.
	public LineNumber jumpOut () {
		LineNumber a = new LineNumber();
		for (Integer i : this.line){
			a.add(i);
		}
		a.removeLast();
		return a.nextLine();
	}
	
	// Returns a new LineNumber object representing the next line
	// starting a new lemma.
	public LineNumber jumpIn () {
		LineNumber a = new LineNumber();
		for (Integer i : this.line) {
			a.add(i);
		}
		a.add(1);
		return a;
	}
	
	// Returns a string representation of the LineNumber
	public String toString () {
		String a = "";
		for (int k = 0; k < this.line.size() - 1; k++) {
			a += this.line.get(k);
			a += ".";
		}
		a += this.line.getLast();
		return a;
	}
	
	// Returns true if both LineNumber objects have the same 
	// numbers in the same order and false otherwise.
	public boolean equals (LineNumber n) {
		boolean indicator = true;
		if (this.line.size() != n.size()) {
			indicator = false;
		}
		else {
			for (int k = 0; k < this.line.size(); k++) {
				if (this.line.get(k) != n.get(k)) {
					indicator = false;
					break;
				}
			}
		}
		return indicator;
	}
	
	//Returns true if LineNumber self can legally reference LineNumber ref
	//Precondition: LineNumber ref exists in a previous Step object.
	//self = the actual line number belonging to a step object
	//ref = the line number being referenced
	public static boolean hasScope(LineNumber self, LineNumber ref) throws IllegalLineException {
		
		//line size of ref cannot be higher than line size of self
		if (ref.size() > self.size()) {
			return false;
		}
		
		for (int i = 0; i < ref.size(); i++) {
			//No digit of ref can be larger than digit of self at the same level of depth
			if (ref.get(i) > self.get(i)) {
				return false;
			}
			
			//all digits of ref have to be the same digit of self at the same level of depth
			//Exception: the last digit of ref may be a lower digit than the digit of self at same depth
			if (ref.get(i) != self.get(i)) {
				if (i+1 != ref.size()) {
					return false;
				}
			}			
		}		
		return true;
	}
}