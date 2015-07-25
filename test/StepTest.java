import static org.junit.Assert.*;
import junit.framework.TestCase;
import org.junit.Test;

public class StepTest extends TestCase{

	public void test1() {
		try {
			Step t1 = new Step("show");
			t1.setLineNumber(new LineNumber(1));
			t1.setExpression(new Expression("(p=>q)"));
			System.out.println(t1);
		}
		
		catch (IllegalLineException e) {
			fail();
		}
	}
	
	public void test2() {
		try {
			Step t1 = new Step("co");
			t1.setLineNumber(new LineNumber("4"));
			t1.setExpression(new Expression("(p=>q)"));
			
			Step t2 = new Step(new LineNumber("3.2.4.1"), "assume", new Expression("p"));
			Step t3 = new Step(new LineNumber("3.2.1"), "assume", new Expression("~p"));

			t1.setPrevLines(t2, t3);
			System.out.println(t1);
		}
		
		catch (IllegalLineException e) {
			fail();
		}
	}
}
