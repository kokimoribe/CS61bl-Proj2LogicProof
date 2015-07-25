import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;


public class LineNumberTest extends TestCase {

	public void testNextLine1 () {
		LineNumber larry = new LineNumber(1);
		LineNumber lyssa = larry.nextLine();
		assertEquals(lyssa.get(0), 2);
	}
	
	public void testJumpIn () {
		LineNumber lorde = new LineNumber(1);
		LineNumber lady = lorde.jumpIn();
		assertEquals(lady.get(0), 1);
		assertEquals(lady.get(1), 1);
	}
	
	public void testJumpOut () {
		LineNumber lex = new LineNumber(1);
		LineNumber lauren = lex.jumpIn();
		LineNumber lysi = lauren.jumpOut();
		assertEquals(lysi.toString(), "2");
		
		LineNumber laurent = new LineNumber(1);
		LineNumber lick = laurent.jumpIn();
		LineNumber lance = lick.jumpIn();
		assertEquals("1.1.1", lance.toString());
		LineNumber logan = lance.jumpOut();
		assertEquals("1.2", logan.toString());
	}
	
	public void testEqualsAndToString () {
		LineNumber lizzie = new LineNumber(1);
		LineNumber lana = lizzie.nextLine();
		LineNumber lacan = new LineNumber(1);
		LineNumber laney = lacan.nextLine();
		assertTrue(lana.equals(laney));
		assertTrue(laney.equals(lana));
		assertEquals(lana.toString(), laney.toString());
		
		LineNumber lilith = new LineNumber(2);
		LineNumber lorrie = lilith.nextLine();
		assertFalse(lana.equals(lorrie));
		assertFalse(laney.equals(lorrie));
	}
	
	public void testHasScope() {
		
		try {
			LineNumber r1 = new LineNumber("2.3.4");
			LineNumber r2 = new LineNumber("1");
			LineNumber r3 = new LineNumber("3");
			LineNumber r4 = new LineNumber("4.3.2");
			LineNumber r5 = new LineNumber("3.3.2.4.2.2.1");
			LineNumber r6 = new LineNumber("3.3.2.4.2.2.4");
			LineNumber r7 = new LineNumber("3.1.2.4.2.1");
			LineNumber r8 = new LineNumber("3.3.2.4.2.1");
			
			assertTrue(LineNumber.hasScope(r3, r2));
			assertTrue(LineNumber.hasScope(r6, r5));
			assertTrue(LineNumber.hasScope(r4, r3));
			assertTrue(LineNumber.hasScope(r6, r8));
			assertFalse(LineNumber.hasScope(r3, r1));
			assertFalse(LineNumber.hasScope(r2, r3));
			assertFalse(LineNumber.hasScope(r6, r7));
		}
		
		catch (IllegalLineException e) {
			fail();
		}
	}

}
