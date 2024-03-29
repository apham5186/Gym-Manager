package src;

import javafx.scene.control.Alert;

import java.io.File;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.FileNotFoundException;

/**
 * MemberDatabase class that stores a list of Members to an array with functons to add,
 * find, remove members along with a variety of print methods with different sorting 
 * options. 
 * @author Alex Pham, Alex Lesica
 */

public class MemberDatabase {

  private Member[] mlist;
  private int size;

  static final int NOT_FOUND=-1; 
  static final int Length=4; 

  /** 
   * Creates a MemberDatabase that starts off with memory for 4 members
  */
  public MemberDatabase(){
    this.mlist = new Member[4];
    this.size = 0;
  }

  /**
   * Returns the database size from the MemberDatabase object
   *
   * @return integer value representing the size of the database
  */
  public int getSize(){
    return size;
  }

    /**
     * Returns the members list from the MemberDatabase object
     *
     * @return Member array
     */
    public Member[] getMList(){
        return mlist;
    }

  /**
     * A helper method to check to see and find a member in the member list.
     * @param member the member we are looking for.
     * @return index of the member if found, otherwise return NOT_FOUND if the member is
     * not in the members list.
     */
  private int find(Member member) { 
  Member[] mlist = this.mlist;
  for(int x = 0; x < mlist.length; x++){
    if(mlist[x] != null && member.compareTo(mlist[x]) == 0){
      return x;
    }
   }
    return NOT_FOUND;
  }

 /**
  * Helper method that grows member list by 4 when members list is full/
 */
  private void grow() { 
    Member[] mlist = this.mlist;
    int increasedLength = mlist.length + Length;
    Member [] newList = new Member[increasedLength];
    for (int x = 0; x < size; x++){
      newList[x] = mlist[x];
    }
    this.mlist = newList;
  }
  /**
   * Method that adds a member to the members list
   * @param member member that is being added
   * @return boolean value that shows true if member was sucessfully added, false if not
   */
  public boolean add(Member member) {
    Member[] mlist = this.mlist;
    int memberExists = this.find(member);
    if (mlist.length == size) {
      this.grow();
    }
    if (memberExists == NOT_FOUND){
      for (int x = 0; x < mlist.length; x++){
        if (mlist[x] == null){
          mlist[x] = member;
          size++;
          return true;
        }
        if (mlist[x] != null && mlist[x] == member){
          return false;
        }
      }
    } 
    return false;
  }
  /**
   * Method that removes a member from the members list
   * @param member member that is being remmoved
   * @return boolean value that shows true if member was sucessfully removed, false if not
   */
  public boolean remove(Member member) { 
   Member[] mlist = this.mlist;
   int memberExists = this.find(member);
   if (memberExists == NOT_FOUND){
    return false;
   }
    for(int i =0; i< mlist.length; i++){
      if (mlist[i] !=null && member.compareTo(mlist[i]) == 0){
        mlist[i] = null;
        size--;
        return true;
      }
    }
    return false;
  }

  /**
   * Method to print all members in the database
   */
  public String print () {
    for(int x = 0; x < mlist.length; x++)
    { 
      if (mlist[x] != null){
      return (mlist[x].toString()+ "\n");
      }
    }
    return null;
   } 


  /**
   * Method to print all members in the database sorted by county then zipcode
   */
  public Member[] printByCounty() {
    for(int x=0; x<size; x++)
    {
      for(int y=x+1; y<size; y++)
      {
        String county = mlist[x].getLocation().getCounty();
        String zipcode = mlist[x].getLocation().getZipcode();
        String county2 = mlist[y].getLocation().getCounty();
        String Zipcode2 = mlist[y].getLocation().getZipcode();
        int value = county.compareToIgnoreCase(county2);
     	  if(value == 0){
      		value= zipcode.compareToIgnoreCase(Zipcode2);
    	  }
        if(value>=0)
        {
          Member temp = mlist[x];
          mlist[x]=mlist[y];
          mlist[y]=temp;
        }
      } 
      
    } 
    return mlist;
  }
 
  /**
   * Method to print all members in the database sorted by expiration date
   */
  public Member[] printByExpirationDate() {
    for(int x=0; x<size; x++)
    {
      for(int y=x+1; y<size; y++)
      {
        Date expiration1= mlist[x].getExpire();
        Date expiration2= mlist[y].getExpire();
        int value = expiration1.compareTo(expiration2);
        if(value>0)
        {
          Member temp = mlist[x];
          mlist[x]=mlist[y];
          mlist[y]=temp;
        }
      }
    }
      return mlist;
   } 

   /**
   * Method to print all members in the database sorted by last name then first name
   */
  public Member[] printByName() {
    for(int x=0; x<size; x++)
    {
      for(int y=x+1; y<size; y++)
      {
        int value = mlist[x].compareTo(mlist[y]);
        if(value<0)
        {
          Member temp = mlist[x];
          mlist[x]=mlist[y];
          mlist[y]=temp;
        }
      } 
    }
      return mlist;
  } 

  /**
   * Method to print all members with membership fees
   */
  public Member[] printByFee() {
    for(int x = 0; x < mlist.length; x++)
    {
      if ((mlist[x] != null)&&(mlist[x].membershipFee()!=0)){

      }
    }
    return null;
  } 


  /**
  * A helper method to compare member with missing information and return it with full information 
  * @param member that is missing expiration date and location
  * @return equivalent member that has informationf fully filled out
  */
  public Member compareMember(Member member){
    Member mlist[] = this.mlist;
    int memberExists = this.find(member);
    if (memberExists != NOT_FOUND){
      return member = mlist[memberExists];
    }
    return member;
  }

    /**
     * Method that loads list of members from a .txt file into the Members Database.
     * @throws FileNotFoundException
     */
  public void loadMembers() throws FileNotFoundException {
      File list = new File("src\\info\\memberList");
      Scanner scan = new Scanner(list);
      while (scan.hasNextLine()){
          String line = scan.nextLine();
          StringTokenizer info = new StringTokenizer(line, " ");
          String fName = info.nextToken();
          String lName = info.nextToken();
          Date dob = new Date(info.nextToken());
          Date expire = new Date(info.nextToken());
          Location location = validLocation(info.nextToken());
          Member member = new Member(fName, lName, dob, expire, location);
          add(member);
      }
      scan.close();
  }

    /**
     * Helper method that verifies location given is a valid gym location
     * @param location Input string value representing a given location
     * @return If input String is a valid location returns corresponding Location
     * otherwise returns null.
     */
    public Location validLocation(String location){
        if (location.equalsIgnoreCase(Location.BRIDGEWATER.name())){
            return Location.BRIDGEWATER;

        } else if (location.equalsIgnoreCase(Location.EDISON.name())){
            return Location.EDISON;

        } else if(location.equalsIgnoreCase(Location.FRANKLIN.name())){
            return Location.FRANKLIN;

        } else if(location.equalsIgnoreCase(Location.PISCATAWAY.name())){
            return Location.PISCATAWAY;

        } else if (location.equalsIgnoreCase(Location.SOMERVILLE.name())){
            return Location.SOMERVILLE;
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR!!");
        alert.setHeaderText("Invalid location entered.");
        alert.setContentText("Please enter a valid location");
        return null;
    }
}