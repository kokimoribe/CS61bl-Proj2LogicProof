import static org.junit.Assert.*;
import junit.framework.TestCase;
import org.junit.Test;


public class ProofTest extends TestCase {
	
	public static Proof setUpNewTestProof () {
		// Set up new Proof with a simple Theorem
		TheoremSet t = new TheoremSet();
		try {
			t.put("easy", new Expression("(p=>q)"));
		}
		catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
		return new Proof(t);		
	}

	// Test control of adding a valid first line
	public void test1 () {
		Proof peter = setUpNewTestProof();
		// Try to add a valid first line
		try {
			peter.extendProof("show (q=>q)");
			System.out.println("test1 success!");
		}
		catch (IllegalLineException l) {
			System.out.println("test1 failed: \n" + l.getMessage());
		}
		catch (IllegalInferenceException i) {
			System.out.println("test1 failed: \n" + i.getMessage());
		}
	}
	
	// Test simple cases of invalid arguments for extendProof
	public void test2 () {
		Proof portia = setUpNewTestProof();
		// test2.1: Try to add an invalid first line (empty)
		try {
			portia.extendProof("");
			System.out.println("test2.1 failed: didn't catch empty string input.");
		}
		catch (IllegalLineException l) {
			System.out.println("test2.1 success!");
		}
		catch (IllegalInferenceException i) {
			System.out.println("test2.1 failed: \n" + i.getMessage());
		}
		// test2.2: Try to add an improperly formatted first line
		try {
			portia.extendProof("assume q");
			System.out.println("test2.2 failed: didn't catch wrong first line.");
		}
		catch (IllegalLineException l) {
			System.out.println("test2.2 failed: \n" + l.getMessage());
		}
		catch (IllegalInferenceException i) {
			System.out.println("test2.2 success!");
		}
		// test2.3: Try to add another improperly formatted first line
		try {
			portia.extendProof("show (q");
			System.out.println("test2.3 failed: didn't catch wrong first line");
		}
		catch (IllegalLineException l) {
			System.out.println("test2.3 success!");
		}
		catch (IllegalInferenceException i) {
			System.out.println("test2.3 failed: \n" + i.getMessage());
		}
	}
	
	// Test for theorems
	public void test3 () {
		Proof pat = setUpNewTestProof();
		// Add a valid first line
		try {
			pat.extendProof("show (p=>q)");
		}
		catch (IllegalLineException l) {
			System.out.println("test3 initialization failed: \n" + l.getMessage());
		}
		catch (IllegalInferenceException i) {
			System.out.println("test3 initialization failed: \n" + i.getMessage());
		}
		// test3.1: Try to invoke theorem incorrectly
		try {
			pat.extendProof("easy (=>q)");
			System.out.println("test3.1 failed: didn't catch bad use of theorem.");
		}
		catch (IllegalLineException l) {
			System.out.println("test3.1 success!");
		}
		catch (IllegalInferenceException i) {
			System.out.println("test3.1 failed: \n" + i.getMessage());
		}
		// test3.2: Try to invoke theorem incorrectly
		try {
			pat.extendProof("asy (p=>q)");
			System.out.println("test3.2 failed: didn't catch bad use of theorem.");
		}
		catch (IllegalLineException l) {
			System.out.println("test3.2 success!");
		}
		catch (IllegalInferenceException i) {
			System.out.println("test3.2 failed: \n" + i.getMessage());
		}
		// test3.3
		try {
			pat.extendProof("easy (q=>q)");
			System.out.println("test3.3 success!");
		}
		catch (IllegalLineException l) {
			System.out.println("test3.3 failed: \n" + l.getMessage());
		}
		catch (IllegalInferenceException i) {
			System.out.println("test3.3 failed: \n" + i.getMessage());
		}
	}

}
