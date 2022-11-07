package src;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Junit test class that tests the premium class.
 * 
 * @author Alex Pham, Alex Lesica
 */
public class PremiumTest {
    
    /**
    * Test function for PremiumTest.
    */
    @Test
    public void test(){

        //tests against a premium member
        Date date = new Date("12/31/2002");
        Date expires = new Date(  "12/31/2024");
        Premium alex = new Premium("alex", "lesica", date, expires, Location.BRIDGEWATER);
        double expectedValue = 659.89;
        assertEquals(expectedValue, alex.membershipFee(), 0);

       //Tests a family member to make sure it will give a different membership fee than a premium member
       date = new Date("12/31/1998");
        expires = new Date(  "12/31/2024");
        Family john = new Family("john", "lesica", date, expires, Location.BRIDGEWATER);
        expectedValue = 659.89;
        double actualValue = john.membershipFee();
        boolean expectedBoolean = false;
        boolean actualBoolean = (expectedValue==actualValue);
        assertFalse(actualBoolean);

       //Tests a standard member to make sure it will give a different membership fee than a premium member
        date= new Date("12/31/2002");
        expires= new Date(  "12/31/2024");
        Member steven = new Member("steven", "lesica", date, expires, Location.BRIDGEWATER);
        expectedValue = 659.89;
        actualValue = steven.membershipFee();
        expectedBoolean = false;
        actualBoolean = (expectedValue==actualValue);
        assertFalse(actualBoolean);

    }
}