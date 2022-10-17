package testing;

import static org.junit.Assert.*;
import org.junit.*;


public class JUnitTest {
   int test;
   
   @Test
   public void testSilly()
   {
       assertEquals(0, test);
   }
   
   @Before
   public void setup() {
      test = 0;
   }

}
