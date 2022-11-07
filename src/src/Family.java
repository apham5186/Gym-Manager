package src;

/**
 * Class that defines a family member and defines a toString method and a membership fee method.
 * 
 * @author Alex Pham, Alex Lesica
 */
public class Family extends Member{
    
  public int guestPasses;

  /**
  * Constructor method that creates a family object, using the members name split into
  * first and last, date of birth, membership expire date, and gym location and number of guess passes
  * family members receive.
  * @param first String value representing member's first name
  * @param last String value representing member's last name 
  * @param dob Member's date of birth
  * @param expire Member's membership expire date
  * @param location Location of Gym where member is registering from
  */
  public Family(String first, String last, Date dob, Date expire, Location location){
    super(first,last,dob,expire,location);
    this.guestPasses = 1;
  }
  /**
   * returns member's membership fee
   * @return double value of 59.99*3 + 29.99 which represents the amount a family member would be billed for one cycle
   */
  @Override
  public double membershipFee(){ 
    return 59.99*3+29.99;
  }

  /**
   * return number of guestpasses
   * @return
   */
  public int getGuestPasses(){
    return guestPasses;
  }

  /**
   * update number of guestpasses remaining
   */
  public void update(int newValue){
    this.guestPasses = newValue;
  }
  
  /**
  * returns the members info and number of guess passes they have remaining
  * @return a string value detailing the family members info and number of guess passes remaining
  */
  @Override
  public String toString() { 
    return super.toString()+ ", (Family) Guest-Passes Remaining: "+guestPasses+",";
  }
}