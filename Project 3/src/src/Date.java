package src;
import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * Class that stores the date based off the given input string
 * which is broken down into instance variables year, month, and day.
 * 
 * @author Alex Pham, Alex Lesica
 */

public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

     //constants
     public static final int QUADRENNIAL = 4;
     public static final int CENTENNIAL = 100;
     public static final int QUATERCENTENNIAL = 400;
     public static final int FEBRUARY_LEAP = 29;
     public static final int MONTH_START = 1;
     public static final int MONTH_END = 31;

    /**
     * Constructor Method that creates today's date through the use of Java Calendar 
     * libray. Used when a String input is not given.
     */
    public Date() {
        Calendar current= Calendar.getInstance();
        this.year = current.get(Calendar.YEAR);
        this.month = current.get(Calendar.MONTH) + 1;
        this.day = current.get(Calendar.DAY_OF_MONTH);
    } 

    /**
     * Constructor Method that creates a Date object based off the given string
     * StringTokenize separates the string into separate tokens and is then assigned
     * to the instance variables month, day, and year
     * @param date String representing date in the form mm/dd/yyyy
     */
   public Date(String date) {
        StringTokenizer st = new StringTokenizer(date, "/");
        this.month = Integer.parseInt(st.nextToken());
        this.day = Integer.parseInt(st.nextToken());
        this.year = Integer.parseInt(st.nextToken());
    }

    /**
     * Returns the year from the date object
     *
     * @return integer value representing the year of the date
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Returns the month from the date object
     *
     * @return integer value representing the month of the date
     */
    public int getMonth() {
        return this.month;
    }

    /**
     * Returns the day from the date object
     *
     * @return integer value representing the day of the date
     */
    public int getDay() {
        return this.day;
    }
    
    /**
     * Helper method that calculates if the given year is a leap year
     * @return Boolean value, true if year a leap year, false otherwise
     */
    private boolean isLeapYear() {
        if (this.year % QUADRENNIAL == 0) {
            if (this.year % CENTENNIAL == 0) {
                if(this.year % QUATERCENTENNIAL == 0){
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * Method that returns date object in string format mm/dd/yyyy
     * 
     * @return String representation of date object
     */
    public String toString(){
        return this.month + "/" + this.day + "/" + this.year;
    }

    @Override
    /**
     * Method that compares given date to Today's date 
     * @param Date date object that we are comparing
     * @return returns and interger value depending on when given date object occurs after
     * today's date. Returns 0 if date is same as today's date, 1 if date occurs after 
     * today's date, and -1 if date occurs before today's date
     */
    public int compareTo(Date date) {
        Date today = new Date();
        if (this.year == today.year){
            if (this.month == today.month){
                if (this.day == today.day){
                    return 0;
                } else if (this.day > today.day){
                    return 1;
                } else{
                    return -1;
                }
            } else if (this.month > today.month){
                return 1;
            } else{
                return -1;
            }
        } else if (this.year > today.year){
            return 1;
        } else {
            return -1;
        }
     }

    /**
     * Method that checks to see if date is a valid date
     * @return boolean value depending on validity of the date. True if date is valid
     * False if date is invalid.
     */
    public boolean isValid() {
       int month = this.month;
       int day = this.day;
       if (day < MONTH_START || day > MONTH_END){
        return false;
       } 
       switch(month){
        case Calendar.JANUARY + 1:
        case Calendar.MARCH + 1:
        case Calendar.MAY + 1:
        case Calendar.OCTOBER + 1:
        case Calendar.JULY + 1:
        case Calendar.AUGUST + 1:
        case Calendar.DECEMBER + 1:
        return true;
        case Calendar.APRIL + 1:
        case Calendar.JUNE + 1:
        case Calendar.SEPTEMBER + 1:
        case Calendar.NOVEMBER + 1:
        if (day >= MONTH_END){
            return false;
        }else{
            return true;
        }
        case Calendar.FEBRUARY + 1:
        if (this.isLeapYear() && this.day == FEBRUARY_LEAP){
            return true;
        } else if (this.day < FEBRUARY_LEAP){
            return true;
        } else {
            return false;
        }
        default:
        return false;
       }
    } 

     /**
      * Helper method that checks if members is 18 or over based on their date of birth
      * @param dob Members date of birth 
      * @return boolean value true if member is over 18 or is 18 and false if they are
      * younger than 18
      */
     public boolean is18(Date dob){
        Date today = new Date();
        int age = today.year - dob.year;
        if (age > 18){
            return true;
        } else if (age == 18){
            if (today.month > dob.month){
                return true;
            } else if (today.month == dob.month){
                if (today.day >= dob.day){
                    return true;
                }
            }
        }
        return false;
     }
     
     /**
     * Testbed main to exercise the isValid() method in this class.
     *
     * @param args command line arguments
     */ 
    public static void main (String[] args) {
        
        System.out.println("Test Case #1, invalid month 13: ");
        Date date = new Date("13/1/2022");
        boolean expectedOutput = false;
        boolean actualOutput =date.isValid();
        testResult(date, expectedOutput, actualOutput);

        System.out.println("Test Case #2, invalid month 0: ");
        date  = new Date("0/1/2022");
        expectedOutput= false;
        actualOutput = date.isValid();
        testResult(date, expectedOutput, actualOutput);

        System.out.println("Test Case #3, invalid day 0: ");
        date = new Date("12/0/2022");
        expectedOutput= false;
        actualOutput = date.isValid();
        testResult(date, expectedOutput, actualOutput);
  
        System.out.println("Test Case #4 invalid day 32: ");
        date = new Date("12/32/2022");
        expectedOutput= false;
        actualOutput = date.isValid();
        testResult(date, expectedOutput, actualOutput);
        System.out.println("Test Case #5 invalid day 31 when month has only 30 days: ");
        date = new Date("9/31/2022");
        expectedOutput= false;
        actualOutput = date.isValid();
        testResult(date, expectedOutput, actualOutput);

         System.out.println("Test Case #6, non leap year day 29: ");
         date = new Date("2/29/2022");
         expectedOutput= false;
         actualOutput = date.isValid();

         testResult(date, expectedOutput, actualOutput);
         System.out.println("Test Case #7, leap year day 29: ");
         date = new Date("2/29/2020");
         expectedOutput= true;
         actualOutput = date.isValid();
         testResult(date, expectedOutput, actualOutput);

        System.out.println("Test Case #8, valid date first day of year");
        date = new Date("1/1/2022");
        expectedOutput= true;
        actualOutput = date.isValid();
        testResult(date, expectedOutput, actualOutput);
     
        System.out.println("Test Case #9, valid date last day of year");
        date = new Date("12/31/2022");
        expectedOutput= true;
        actualOutput = date.isValid();
        testResult(date, expectedOutput, actualOutput);
    }
        
    /**
     * Helper method that takes the boolean values expectedOutput and actualOutput and 
     * prints out pass if they are both equal and fail if they are different.
     * @param date date that is to be tested
     * @param expectedOutput Correct output
     * @param actualOutput ouput the method isValid() returns
     */
    private static void testResult(Date date, boolean expectedOutput, boolean actualOutput){
        System.out.println(date.toString());
        System.out.print("isValid() returns " + actualOutput);
        if (actualOutput == expectedOutput){ 
        System.out.println(", PASS. \n");
        } 
        else{
            System.out.println(", FAIL. \n");
        }
    }
}
    