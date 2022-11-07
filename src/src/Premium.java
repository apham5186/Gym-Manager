package src;

/**
 * Class that defines a premium member and defines a toString method and a membership fee method.
 * 
 * @author Alex Pham, Alex Lesica
 */
public class Premium extends Family{
  
  /**
  * Constructor method that creates a premium object, using the members name split into
  * first and last, date of birth, membership expire date, and gym location and number of guess passes
  * premium members receive.
  * @param first String value representing member's first name
  * @param last String value representing member's last name 
  * @param dob Member's date of birth
  * @param expire Member's membership expire date
  * @param location Location of Gym where member is registering from
  */
  public Premium(String first, String last, Date dob, Date expire, Location location){
    super(first,last,dob,expire,location);
    guestPasses=3;
    }

  /**
  * returns member's membership fee
  * @return double value of 59.99*11 which represents the amount a premimum member would be billed for one cycle
  */
  @Override
  public double membershipFee() {      
    return 59.99*11;
  }
  
  /**
   * return number of guestpasses
   * @return
   */
  @Override
  public int getGuestPasses(){
    return guestPasses;
  }

  /**
  * update number of guestpasses remaining
  */
  @Override
  public void update(int newValue){
    this.guestPasses = newValue;
  }

  /**
  * returns the members info and number of guess passes they have remaining
  * @return a string value detailing the family members info and number of guess passes remaining
  */
  @Override
  public String toString() { 
    return super.toString()+ ", (Premium) Guest-Passes Remaining: " + guestPasses+",";
  }
}