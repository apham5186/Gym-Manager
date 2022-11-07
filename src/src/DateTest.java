package src;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Junit test class that tests the Date class.
 * 
 * @author Alex Pham, Alex Lesica
 */
public class DateTest {
    
    /**
    * Test function for dateTest that tests each of the following edge cases.
    */
    @Test
    public void test(){

        //Test Case #1, invalid month 13: 
        Date date = new Date("13/1/2022");
        assertFalse(date.isValid());
        boolean expectedOutput = false;
        boolean actualOutput =date.isValid();
        assertEquals(expectedOutput, actualOutput);
    

        //Test Case #2, invalid month 0:
        date  = new Date("0/1/2022");
        assertFalse(date.isValid());
        expectedOutput = false;
        actualOutput =date.isValid();
        assertEquals(expectedOutput, actualOutput);

        //Test Case #3, invalid day 0:
        date = new Date("12/0/2022");
        assertFalse(date.isValid());
        expectedOutput = false;
        actualOutput =date.isValid();
        assertEquals(expectedOutput, actualOutput);
  
        //Test Case #4 invalid day 32:
        date = new Date("12/32/2022");
        assertFalse(date.isValid());
        expectedOutput = false;
        actualOutput =date.isValid();
        assertEquals(expectedOutput, actualOutput);

        //Test Case #5 invalid day 31 when month has only 30 days:
        assertFalse(date.isValid());
        expectedOutput = false;
        actualOutput =date.isValid();
        assertEquals(expectedOutput, actualOutput);

         //Test Case #6, non leap year day 29:
         date = new Date("2/29/2022");
         assertFalse(date.isValid());
         expectedOutput = false;
         actualOutput =date.isValid();
         assertEquals(expectedOutput, actualOutput);

         //Test Case #7, leap year day 29:
         date = new Date("2/29/2020");
         assertTrue(date.isValid());
         expectedOutput = true;
         actualOutput =date.isValid();
         assertEquals(expectedOutput, actualOutput);

        //Test Case #8, valid date first day of year
        date = new Date("1/1/2022");
        assertTrue(date.isValid());
         expectedOutput = true;
         actualOutput =date.isValid();
         assertEquals(expectedOutput, actualOutput);
     
        //Test Case #9, valid date last day of year:
        date = new Date("12/31/2022");
        assertTrue(date.isValid());
         expectedOutput = true;
         actualOutput =date.isValid();
         assertEquals(expectedOutput, actualOutput);
    }
}