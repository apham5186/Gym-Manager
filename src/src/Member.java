package src;
/**
 * Class that stores the name, date of birth, expiry date, and location of a gym. 
 * That makes up all the information towards a member's membership
 * 
 * @author Alex Pham, Alex Lesica
 */
public class Member implements Comparable<Member>{
    private String fname;
    private String lname;
    private Date dob;
    private Date expire;
    private Location location;
    
    /**
    * Constructor method that creates a Member object, using the members name split into
    * first and last, date of birth, membership expire date, and gym location
    * @param fname String value representing member's first name
    * @param lname String value representing member's last name
    * @param dob Member's date of birth
    * @param expire Member's membership expire date
    * @param location Location of Gym where member is registering from
    */
    public Member(String fname, String lname, Date dob, Date expire, Location location){
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.expire = expire;
        this.location=location;
    }

    /**
     * returns member's first name
     * @return String value representing member's first name
     */
    public String getFirstName(){
        return fname;
    }

    /**
     * returns member's last name
     * @return String value representing member's last name
     */
    public String getLastName(){
        return lname;
    }

    /**
     * returns member's date of birth
     * @return return date object representing member's date of birth
     */
    public Date getDOB(){
        return dob;
    }
    /**
     * returns member's expire date
     * @return Date object represeting the member's expire date
     */
    public Date getExpire(){
        return expire;
    }

    /**
     * retunrs location of gym
     * @return Location object representing the gym where member is registering from
     */
    public Location getLocation(){
        return location;
    }

    /**
    * returns the membership fee to be billed
    * @return 39.99*3+29.99: The amount billed quarterly for the stardard membership
    */
    public double membershipFee() {  
        return 39.99*3+29.99;
    }

    /**
     * Converts all information of the member into a single String value 
     * 
     * @return A String representing the member's membership information
     */
    @Override
    public String toString() { 
        String town=location.getTown();
        String zipcode=location.getZipcode();
        String county=location.getCounty();
        return fname + " " + lname + 
        ", " + "DOB: " + dob.toString()+
        ", " + "Membership expires " + expire.toString() +
        ","  + " Location: " + town + ", " +zipcode + ", " +county; 
    }

    /**
     * Helper method that 
     * Checks whether two members are equivalent to each other. 
     * 2 members are equivalent if their first name, last name, and date of birth match
     * @param obj Object that is being compared to member that the method was called on
     * 
     * @return Boolean value, True if members are equivalent, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
       Member member1 = this;
       Member member2 = (Member) obj;
       
       boolean sameFName = member1.fname.equalsIgnoreCase(member2.fname);
       boolean sameLName = member1.lname.equalsIgnoreCase(member2.lname);
       boolean sameDob = member1.dob.equals(member2.dob);

       return sameFName && sameLName && sameDob;
     }

    /**
     * Helper method that compares two members name to find which order they occur
     * alphabetically. Starting with last name then first name.
     * @param member1 member that is being compared to member that the method was called on
     * 
     * @return and int value depending the two members order. 1 if the compared member
     * occurs after, -1 if the compared member occurs before, and 0 if the compared member
     * are equivalent in order
     */
    @Override
    public int compareTo(Member member1) {
    	int value = this.lname.compareToIgnoreCase(member1.lname);
     	if(value == 0){
      		value = this.fname.compareToIgnoreCase(member1.fname);
            if (value > 0){
                return 1;
            } else if ( value < 0){
                return -1;
            } 
            return 0;
    	}
     	else if(value > 0){
	     return 1;
    	}
        return -1;
    }
     /**
     * Helper method that takes the boolean values expectedOutput and actualOutput and 
     * prints out pass if they are both equal and fail if they are different.
     * @param member1 first member that is being compared to
     * @param member2 second member that is comparing itself to member1
     * @param expectedOutput Correct output
     * @param actualOutput ouput the method compareTo() returns
     */
    private static void testResult(Member member1, Member member2, int expectedOutput, int actualOutput){
    
        System.out.print("compareTo() returns " + actualOutput);
        if (actualOutput == expectedOutput){
        System.out.println(", PASS. \n");
        }
        else{
            System.out.println(", FAIL. \n");
        }
      }

    /**
     * Testbed main to exercise the isValid() method in this class.
     *
     * @param args command line arguments
     */ 
    public static void main (String[] args) {
            
        System.out.println("Test Case #1, Same first name, different last name. Before in alphabetical order");
        Date date = new Date("12/31/2002");
        Date expires = new Date(  "12/31/2024");
        Member member1 = new Member("Alex","Lesica", date, expires, Location.BRIDGEWATER);
        Member member2 = new Member("Alex","Pham", date, expires, Location.BRIDGEWATER);
        int expectedOutput = 1;
        int actualOutput = member2.compareTo(member1);
        testResult(member1,member2, expectedOutput, actualOutput);

        System.out.println("Test Case #2, Same first name, different last name. After in alphabetical order");
        date = new Date("12/31/2002");
        expires = new Date(  "12/31/2024");
        member2 = new Member("Alex","Lesica", date, expires, Location.BRIDGEWATER);
        member1 = new Member("Alex","Pham", date, expires, Location.BRIDGEWATER);
        expectedOutput = -1;
        actualOutput = member2.compareTo(member1);
        testResult(member1,member2, expectedOutput, actualOutput);

        System.out.println("Test Case #3, Different first name, Same last name. After in alphabetical order");
        date = new Date("12/31/2002");
        expires = new Date(  "12/31/2024");
        member2 = new Member("Joe","Swan", date, expires, Location.BRIDGEWATER);
        member1 = new Member("Alex","Swan", date, expires, Location.BRIDGEWATER);
        expectedOutput = 1;
        actualOutput = member2.compareTo(member1);
        testResult(member1,member2, expectedOutput, actualOutput);
        
        System.out.println("Test Case #4, Different first name, Same last name. Before in alphabetical order");
        date = new Date("12/31/2002");
        expires = new Date(  "12/31/2024");
        member1 = new Member("Joe","Swan", date, expires, Location.BRIDGEWATER);
        member2 = new Member("Alex","Swan", date, expires, Location.BRIDGEWATER);
        expectedOutput = -1;
        actualOutput = member2.compareTo(member1);
        testResult(member1,member2, expectedOutput, actualOutput);
     }
      
}