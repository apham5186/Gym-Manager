package src;
/**
 * Class that stores a list of participants for a fitness class into an array.
 * With different methods that help add and remove members 
 * 
 * @author Alex Pham, Alex Lesica
 */
public class FitnessClass {
  private String className;
  private String instructor;
  private Time timeslot;
  private Location location;
  private Member[] fitnessClassList;
  private Member[] guestList;
  private int classSize;
    
  static final int Length = 4;
  static final int NOT_FOUND = -1;

  /**
   * Creates a new fitness class that starts off with memory for 4 participants
   */
  FitnessClass(String className, String instructor, Time timeslot, Location location){
    this.className = className;
    this.instructor = instructor;
    this.timeslot = timeslot;
    this.location = location;
    this.fitnessClassList = new Member[4];
    this.guestList = new Member[3];
    this.classSize = 0;
  }

  /**
  * returns number of members checked in for class
  * @return int value representing number of members checked in for class
  */
  public int getClassSize(){
   return classSize;
  }

  /**
  * returns name of the fitness class
  * @return string value representing the name of the fitness class
  */
  public String getName(){
    return className;
   }

  /**
  * Converts all information of the fitness class into a single String value 
  * 
  * @return A String representing the fitness class information
  */
  @Override
  public String toString() { 
    return className + " - " + instructor + ", " + timeslot + ", " + location;
  }

  /**
  * returns name of the fitness class instructor
  * @return string value representing the name of the fitness class instructor
  */
  public String getInstructor(){
    return instructor;
   }

  /**
  * retunrs timeslot class is being held at
  * @return time when class is being held
  */
  public Time getTimeslot(){
    return timeslot;
   }

  /**
  * returns number of members checked in for class
  * @return int value representing number of members checked in for class
  */
  public Location getLocation(){
    return location;
  }

  public Member[] getList(){
    return fitnessClassList;
  }

  /**
  * A helper method to check to see and find a member in the member list.
  * @param member the member we are looking for.
  * @return index of the member if found, otherwise return NOT_FOUND if the member is
  * not in the members list.
  */
  private int find(Member member) { 
    Member[] fitnessClassList = this.fitnessClassList;
    for(int i = 0; i < fitnessClassList.length; i++){
      if(fitnessClassList[i] != null && member.compareTo(fitnessClassList[i]) == 0){
      return i;
      }
    }
    return NOT_FOUND;
  }
      
  /**
  * Helper method that grows member list by 4 when members list is full
  */    
  private void grow() { 
    Member[] fitnessClassList = this.fitnessClassList;
    int increasedLength = fitnessClassList.length + Length;
    Member [] newList = new Member[increasedLength];
    for (int i = 0; i < classSize; i++){
      newList[i] = fitnessClassList[i];
    }
    this.fitnessClassList = newList;
  }

  /**
  * Helper method that grows guest list by 4 when guest list is full
  */    
  private void growGuest() { 
    Member[] guestList = this.guestList;
    int increasedLength = fitnessClassList.length + Length;
    Member [] newGuestList = new Member[increasedLength];
    for (int i = 0; i < classSize; i++){
      newGuestList[i] = guestList[i];
    }
    this.fitnessClassList = newGuestList;
  }
    
  /**
  * Method that adds a member to the class list
  * @param member that is checking into a class
  * @return boolean value true if member sucessfuly checked in false otherwise
  */
  public boolean checkedIn(Member member) {
    Member[] fitnessClassList = this.fitnessClassList;
    int memberExists = this.find(member);
    if (fitnessClassList.length == classSize) {
      this.grow();
    }
      if (memberExists == NOT_FOUND){
        for (int i = 0; i < fitnessClassList.length; i++){
          if (fitnessClassList[i] == null){
            fitnessClassList[i] = member;
            classSize++;
            return true;
          }
        }
      } 
        return false;
  }

  /**
  * Method that adds a guest to the class list
  * @param member guest of a member that is checking into a class
  * @return 
  */
  public void guestCheckedIn(Member member) {
    Member[] guestList = this.guestList;
    if (member instanceof Family){
      if(this.location != member.getLocation()){
        System.out.println(member.getFirstName() + " " + member.getLastName() + " Guest checking in "
        + this.location + ", " + this.location.getZipcode() + ", " + this.location.getCounty() + " - guest location restriction.");
        return;
      }
      Family family = (Family)member;
      if (family.getGuestPasses() == 0){
        System.out.println(member.getFirstName() + " " + member.getLastName() + " ran out of guest passes.");
        return;
      }
      if (guestList.length == classSize - this.fitnessClassList.length) {
        this.growGuest();
      }
      System.out.println(member.getFirstName() + " " + member.getLastName() + " (guest) checked in" + this.className + " - " +
      this.instructor + ", " + this.timeslot.getTime() + ", " + this.location);
      for(int i= 0; i < guestList.length; i++){
        if(guestList[i] == null){
          guestList[i] = member;
          family.update(family.getGuestPasses() - 1);
          classSize++;
          return;
        }
      }
    }
    else if (member instanceof Premium){
      if(this.location != member.getLocation()){
        System.out.println(member.getFirstName() + " " + member.getLastName() + " Guest checking in "
        + this.location + ", " + this.location.getZipcode() + ", " + this.location.getCounty() + " - guest location restriction.");
        return;
      }
      src.Premium premium = (Premium)member;
      if (premium.getGuestPasses() == 0){
        System.out.println(member.getFirstName() + " " + member.getLastName() + " ran out of guest passes.");
        return;
      }
      if (guestList.length == classSize - this.fitnessClassList.length) {
        this.growGuest();
      }
      System.out.println(member.getFirstName() + " " + member.getLastName() + " (guest) checked in" + this.className + " - " +
      this.instructor + ", " + this.timeslot.getTime() + ", " + this.location);
      for(int i= 0; i < guestList.length; i++){
        if(guestList[i] == null){
          guestList[i] = member;
          premium.update(premium.getGuestPasses() - 1);
          classSize++;
          return;
        }
      }  
    }
    else{
      System.out.println(" Standard membership - guest check-in is not allowed.");
      return;
    }
  } 
   
  /**
  * Method that removes a member from the fitnessClass list
  * @param member member that is dropping the class
  * @return boolean value that shows true if member was sucessfully removed, false if not
  */
  public boolean drop(Member member) { 
    Member[] fitnessClassList = this.fitnessClassList;
    int memberExists = this.find(member);
    if (memberExists == NOT_FOUND){
     return false;
    }
     for(int i =0; i< fitnessClassList.length; i++){
       if (fitnessClassList[i] !=null && member.compareTo(fitnessClassList[i]) == 0){
        fitnessClassList[i] = null;
         classSize--;
         return true;
       }
     }
     return false;
   }
  /**
  * Method that drops guest from class list
  * @param member guest of a member that is dropping a class
  * @return
  */
  public void guestDropped(Member member){
    Member[] fitnessClassList = this.fitnessClassList;
    int memberExists = this.find(member);
    if (memberExists == NOT_FOUND){
      System.out.println(member.getFirstName() + " " + member.getLastName() + " (guest) did not check in");
    }

    if (member instanceof Family){
      Family family = (Family)member;
      if (family.getGuestPasses() < 1){
        for(int i =0; i< fitnessClassList.length; i++){
          if (fitnessClassList[i] !=null && member.compareTo(fitnessClassList[i]) == 0){
            fitnessClassList[i] = null;
            family.update(family.getGuestPasses() + 1);
            classSize--;
            return;
          }
        }
      }
    }
    if (member instanceof Premium){
      src.Premium premium = (Premium)member;
      if (premium.getGuestPasses() < 3){
        for(int i =0; i< fitnessClassList.length; i++){
          if (fitnessClassList[i] !=null && member.compareTo(fitnessClassList[i]) == 0){
            fitnessClassList[i] = null;
            premium.update(premium.getGuestPasses() + 1);
            classSize--;
            return;
          }
        }
      }
    }
  }

   /**
    * Prints all particpants checked in for a class
    */
  public void printPartcipants() {
    Member[] fitnessClassList = this.fitnessClassList;
    Member[] guestList = this.guestList;
    System.out.println("- Participants -");
    for(int i = 0; i < fitnessClassList.length; i++){
      if (fitnessClassList[i] != null){
        System.out.println(fitnessClassList[i].toString()+ "\n");
      }
    }
    if (guestList[0] != null){
      System.out.println("- Guests -");
      for(int i = 0; i < guestList.length; i++){
        if(guestList[i] != null){
          System.out.println(guestList[i].toString()+ "\n");
        }
      }
    }
  } 

   /**
    * helper method that checks to see if particpant is registed for another class in the
    * same timeslot
    * @param member that is being checked to see if they are in another class of the same
    * timeslot
    * @return a boolean value true if their is a class conflict, otherwise false.
    */
  public boolean classConflict(Member member){
    for(int i = 0; i < fitnessClassList.length; i++){
      if(fitnessClassList[i] != null && member.compareTo(fitnessClassList[i]) == 0){
          return true;
      }
    }
      return false;
  }
}