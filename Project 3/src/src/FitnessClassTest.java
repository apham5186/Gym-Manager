package src;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Junit test class that tests the FitnessClass class.
 * 
 * @author Alex Pham, Alex Lesica
 */
public class FitnessClassTest {
    Member member1, member2;
    Family family;
    Premium premium;
    MemberDatabase memberDatabase;
    FitnessClass fitnessClass, fitnessClass2;


    /**
    * Test function for FitnessClass.
    */
    @Test
    public void test(){
      Date date = new Date("6/9/2000");
      Date expire = new Date ("1/17/2023");
      member1 = new Member("Alex", "Pham", date, expire, Location.EDISON);
      member2 = new Member("Alex", "Lesica", date, expire, Location.EDISON);
      family = new Family("Jack", "Long", date, expire, Location.EDISON);
      premium = new Premium("Katie", "Smith", date, expire, Location.EDISON);

      fitnessClass = new FitnessClass("Cardio", "Kim", Time.AFTERNOON, Location.EDISON);
      fitnessClass2 = new FitnessClass("Pilates", "Davis", Time.AFTERNOON, Location.BRIDGEWATER);

        assertTrue(fitnessClass.checkedIn(member1)); // Checking member into class that is not alreading participating in is true
        assertFalse(fitnessClass.checkedIn(member1)); // Checking member into class that is already in is false
        assertTrue(fitnessClass.classConflict(member1)); // if member is checked into another class occuring at same time return true
        assertTrue(fitnessClass.drop(member1)); // Dropping member from class they are checked into is true
        assertFalse(fitnessClass.drop(member1)); // Dropping member from class they are not checked into is false
        assertFalse(fitnessClass.classConflict(member1)); //checking for conflict from class member is not in is false
    }
}
