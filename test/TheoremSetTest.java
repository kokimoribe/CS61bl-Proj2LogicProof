import static org.junit.Assert.*;
import junit.framework.TestCase;
import org.junit.Test;

public class TheoremSetTest extends TestCase{
	
	public void test1() {
		
		try {
			TheoremSet t = new TheoremSet();
			t.put("hello", new Expression("(p=>q)"));
			t.put("hi", new Expression("((p|b)=>q)"));
			t.put("goodbye", new Expression("(p=>(q&a))"));
			t.put("farewell", new Expression("(a=>(b|c))"));
			
			System.out.println(t.getList());
			System.out.println(t.getNames());
			assertTrue(t.find("hello").equals(new Expression("(p=>q)")));
		}
		
		catch (IllegalLineException e) {
			fail();
		}
		
	}
	
	public void test2() {
		TheoremSet t = new TheoremSet();
		try {
			t.put("assume", new Expression("(p=>q)"));
			fail();
		}
		
		catch (IllegalLineException e) {
		}
		
		try {
			t.put("two words", new Expression("(p=>q)"));
			fail();
		}
		
		catch (IllegalLineException e) {
		}
		
		try {
			t.put("test", new Expression("(p=>q)"));
			t.put("test", new Expression("(a=>b)"));
			assertTrue(t.find("test").equals(new Expression("(a=>b)")));
		}
		
		catch (IllegalLineException e) {
			fail();
		}
	}
}
