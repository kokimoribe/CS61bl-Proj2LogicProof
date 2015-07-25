import static org.junit.Assert.*;
import junit.framework.TestCase;
import org.junit.Test;

public class ExpressionTest extends TestCase {

	public void testExpression1() {
		try {
			Expression t1 = new Expression("(((q=>q)=>q)=>((q=>p)=>p))");
			Expression t2 = new Expression("(((q=>q)=>q)=>((q=>p)=>p))");
			Expression t3 = new Expression("e");
			Expression t4 = new Expression("(a|b)");
			Expression t5 = new Expression("((p&c)=>(d|f))");
			Expression t6= new Expression("(a=>b)");
			Expression t7 = new Expression("((q=>q)=>q)");
			Expression t8 = new Expression("~a");
			Expression t9 = new Expression("~(a|b)");
			Expression t10 = new Expression("~~~a");
			Expression t11 = new Expression("~~~(p&q)");
			assertTrue(t1.equals(t2));
			assertTrue(t1.getMain().equals("=>"));
			assertFalse(t3.equals(t4));
			assertTrue(t6.matches(t5));
			assertTrue(t7.equals(t1.getLeft()));
		}
		
		catch (IllegalLineException e) {
			fail();
		}
		
	}
	
	public void testExpression2() {
		try {
			Expression t1 = new Expression("(((q => q) => q) => ((q => p) => p))");
			fail();
		}
		
		catch (IllegalLineException e) {
		}
		
		try {
			Expression t1 = new Expression("");
			fail();
		}
		
		catch (IllegalLineException e) {
		}
		
		try {
			Expression t1 = new Expression("()");
			fail();
		}
		
		catch (IllegalLineException e) {
		}
		
		try {
			Expression t1 = new Expression("(a)");
			fail();
		}
		
		catch (IllegalLineException e) {
		}
		
		try {
			Expression t1 = new Expression("(abc)");
			fail();
		}
		
		catch (IllegalLineException e) {			
		}
		
		try {
			Expression t1 = new Expression("abc");
			fail();
		}
		
		catch (IllegalLineException e) {
		}
		
		try {
			Expression t1 = new Expression("~a=>p");
			fail();
		}
		
		catch (IllegalLineException e) {
		}
		
		try {
			Expression t1 = new Expression("(&=>|)");
			fail();
		}
		
		catch (IllegalLineException e) {
		}
		
		try {
			Expression t1 = new Expression("((a=>b)&(a=>))");
			fail();
		}
		
		catch (IllegalLineException e) {
		}
		
		try {
			Expression t1 = new Expression("(((q=>q)=>q)=>((q=>p)=>p)");
			fail();
		}
		
		catch (IllegalLineException e) {
		}
	}

}
