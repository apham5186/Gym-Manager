package src;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.File;
import java.io.IOException;

/**
 * Main driver class that runs the entire program and takes in
 * the user's input
 * 
 * @author Alex Pham, Alex Lesica
 */
public class GymManager {

    /**
     * Runs the entire progam by taking in the user's input and changing the results
     * based on the given input. 
     */
    public void run(){
        boolean running = true;
        MemberDatabase memberDatabase = new MemberDatabase();
        ClassSchedule classSchedule = new ClassSchedule();
        System.out.println("Gym Manager running...");

        while(running){
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                try{ 
                    StringTokenizer input = new StringTokenizer(line, " ");
                    String command = input.nextToken();
                    switch(command){
                    // Load fitness class schedule
                    case "LS":
                    try{
                        File schedule = new File("src\\info\\classSchedule");
                        Scanner scan = new Scanner(schedule);
                        while (scan.hasNextLine()){
                            line = scan.nextLine();
                            StringTokenizer  info = new StringTokenizer(line, " ");
                            String name = info.nextToken();
                            String instructor = info.nextToken();
                            Time time = validTime(info.nextToken());
                            Location location = validLocation(info.nextToken());
                            FitnessClass fitnessClass = new FitnessClass(name, instructor, time, location);
                            classSchedule.addClass(fitnessClass);
                        }
                        scan.close();
                        FitnessClass[] classes = classSchedule.getClasses();
                        if (classSchedule.getNumberofClasses() == 0){
                            System.out.println("Fitness class schedule is empty.");
                            break;
                        }
                        System.out.println("-Fitness classes loaded-");
                        for (int i = 0; i < classes.length; i++){
                            System.out.println(classes[i].toString());
                        }
                        System.out.println("-end of class list-");
                        System.out.println();
                        break;
                    } 
                    catch (IOException e){
                        System.out.println("File not found"); 
                        break;
                    }
                    
                    // Load list of members to database
                    case "LM": 
                    try{
                        File list = new File("src\\info\\memberList");
                        Scanner scan = new Scanner(list);
                        while (scan.hasNextLine()){
                            line = scan.nextLine();
                            StringTokenizer  info = new StringTokenizer(line, " ");
                            String fName = info.nextToken();
                            String lName = info.nextToken();
                            Date dob = new Date(info.nextToken());
                            Date expire = new Date(info.nextToken());
                            Location location = validLocation(info.nextToken());
                            Member member = new Member(fName, lName, dob, expire, location);
                            memberDatabase.add(member);
                        }
                        scan.close();
                        System.out.println("-List of members loaded-");
                        memberDatabase.print();
                        System.out.println("-end of list-");
                        System.out.println();
                        break;
                    } 
                    catch (IOException e){
                        System.out.println("File not found"); 
                        break;
                    }

                    // Adds member to the database
                    case "A":
                        String fName = input.nextToken();
                        String lName = input.nextToken();
                        Date dob = new Date(input.nextToken());
                        Date today = new Date();
                        int expMonth = today.getMonth() + 3;
                        int expYear = today.getYear();
                        if (expMonth == 13){
                            expMonth = 1;
                            expYear = expYear + 1;
                        }
                        String date = expMonth + "/" + today.getDay() + "/" + expYear;
                        Date expire = new Date(date);
                        Location location = validLocation(input.nextToken());
                        Member member = validMember(fName, lName, dob, expire, location, "Standard");
                        if (member == null){
                            break;
                        }
                        boolean wasAdded = memberDatabase.add(member);
                        if (wasAdded){
                            System.out.println(fName + " " + lName + " was added");
                        }
                        else {
                            System.out.println(fName + " " + lName + " is already in the database");
                        }
                        break;
                    // Adds member with family membership to the database
                    case "AF":
                        fName = input.nextToken();
                        lName = input.nextToken();
                        dob = new Date(input.nextToken());
                        today = new Date();
                        expMonth = today.getMonth() + 3;
                        expYear = today.getYear();
                        if (expMonth == 13){
                            expMonth = 1;
                            expYear = expYear + 1;
                        }
                        date = expMonth + "/" + today.getDay() + "/" + expYear;
                        expire = new Date(date);
                        location = validLocation(input.nextToken());
                        member = validMember(fName, lName, dob, expire, location, "Family");
                        if (member == null){
                            break;
                        }
                        Family family = (Family)member;
                        wasAdded = memberDatabase.add(family);
                        if (wasAdded){
                            System.out.println(fName + " " + lName + " was added");
                        }
                        else {
                            System.out.println(fName + " " + lName + " is already in the database");
                        }
                        break;
                        
                    // Adds member with premimum membership to the database
                    case "AP":
                        fName = input.nextToken();
                        lName = input.nextToken();
                        dob = new Date(input.nextToken());
                        today = new Date();
                        expYear = today.getYear() + 1;
                        date = today.getMonth() + "/" + today.getDay() + "/" + expYear;
                        expire = new Date(date);
                        location = validLocation(input.nextToken());
                        member = validMember(fName, lName, dob, expire, location, "Premium");
                        if (member == null){
                            break;
                        }
                        Premium premium = (Premium)member;
                        wasAdded = memberDatabase.add(premium);
                        if (wasAdded){
                            System.out.println(fName + " " + lName + " was added");
                        }
                        else {
                            System.out.println(fName + " " + lName + " is already in the database");
                        }
                        break;
                    
                    // Removes member from the database
                    case "R":
                        fName = input.nextToken();
                        lName = input.nextToken();
                        dob = new Date(input.nextToken());
                        member = validMemberAlt(fName, lName, dob, null, null);
                        member = memberDatabase.compareMember(member);
                        if (member == null){
                            break;
                        }
                        boolean wasRemoved = memberDatabase.remove(member);
                        if (wasRemoved){
                            System.out.println(fName + " " + lName + " was removed");
                        }
                        else {
                            System.out.println(fName + " " + lName + " is not in the database");
                        }
                        break;

                    // Prints all members from the database
                    case "P":
                        if (memberDatabase.getSize() > 0){
                            System.out.println("-List of Members-");
                            memberDatabase.print();
                        } else{
                            System.out.println("Member database is empty");
                        }
                        System.out.println();
                        break;
                    // Prints all members from the database with membership fees
                    case "PF":
                        if (memberDatabase.getSize() > 0){
                            System.out.println("-List of Members with membership fees-");
                            memberDatabase.printByFee();
                        } else{
                            System.out.println("Member database is empty");
                        }
                        System.out.println();
                        break;
                    // Prints all members sorted by county and then zipcode
                    case "PC":
                        if (memberDatabase.getSize() > 0){
                            System.out.println("-List of Members sorted by county and zipcode-");
                            memberDatabase.printByCounty();
                        } else{
                            System.out.println("Member database is empty");
                        }
                        System.out.println();
                        break;
                    // Prints all members sorted by last name and then first name
                    case "PN":
                        if (memberDatabase.getSize() > 0){
                            System.out.println("-List of Members sorted by last name, and first name-");
                            memberDatabase.printByCounty();
                        } else{
                            System.out.println("Member database is empty");
                        }
                        System.out.println();
                        break;
                    // Prints all members sorted by expiration date
                    case "PD":
                        if (memberDatabase.getSize() > 0){
                            System.out.println("-List of Members sorted by membership expiration date-");
                            memberDatabase.printByExpirationDate();
                        } else{
                            System.out.println("Member database is empty");
                        }
                        System.out.println();
                        break;
                    // Prints all classes for today and particpants of each class
                    case "S":
                        FitnessClass[] classes = classSchedule.getClasses();
                        if (classSchedule.getNumberofClasses() == 0){
                                System.out.println("Fitness class schedule is empty.");
                                break;
                        }
                        System.out.println("-Fitness classes-");
                        for (int i = 0; i < classes.length; i++){
                            if (classes[i].getClassSize() == 0){
                                System.out.println(classes[i].toString());
                            }
                            else{
                                System.out.println(classes[i].toString());
                                classes[i].printPartcipants();
                            }
                        }
                        System.out.println("- end of class list." + "\n");
                        break;
                    // Adds member to respective class list
                    case "C":
                        String className =input.nextToken();
                        String instructor = input.nextToken();
                        location = validLocation(input.nextToken());
                        fName = input.nextToken();
                        lName = input.nextToken();
                        dob = new Date(input.nextToken());
                        member = validMemberAlt(fName, lName, dob, null, null);
                        member = memberDatabase.compareMember(member);
                        FitnessClass fitnessClass = new FitnessClass(className, instructor, null, location);
                        int classFound = classSchedule.findClass(fitnessClass);
                        classes = classSchedule.getClasses();
                        
                        if(classFound == -1){
                            System.out.println(fitnessClass.getName() + " does not exist with " + instructor + " at " + location);
                        }
                        else{
                            boolean checkedIn = classes[classFound].checkedIn(member);
                            FitnessClass conflict = conflict(classes[classFound], member, classSchedule);
                            if (checkedIn && conflict == null){
                                System.out.println(fName + " " + lName + " checked in " + classes[classFound].getName() 
                                + " - " + classes[classFound].getInstructor() + ", " 
                                + classes[classFound].getTimeslot().getTime() + ", " + location);
                                for (int i = 0; i < classes.length ; i++){
                            classes[i].toString();
                        }
                            }
                            else if(!checkedIn){
                                System.out.println(fName + " " + lName + " has already checked into " + classes[classFound].getName() 
                                + " - " + classes[classFound].getInstructor() + ", " 
                                + classes[classFound].getTimeslot().getTime() + ", " + location);
                            }
                            else if(checkedIn && conflict != null){
                                System.out.println(fName + " " + lName + " has already checked into " + conflict.getName() 
                                + " - " + conflict.getInstructor() + ", " 
                                + conflict.getTimeslot().getTime() + ", " + conflict.getLocation());
                            }
                        }
                        classes[classFound].printPartcipants();
                        break;
                    // Adds guest if member has guess passes available
                    case "CG":
                        className =input.nextToken();
                        instructor = input.nextToken();
                        location = validLocation(input.nextToken());
                        fName = input.nextToken();
                        lName = input.nextToken();
                        dob = new Date(input.nextToken());
                        member = validMemberAlt(fName, lName, dob, null, null);
                        member = memberDatabase.compareMember(member);
                        fitnessClass = new FitnessClass(className, instructor, null, location);
                        classFound = classSchedule.findClass(fitnessClass);
                        if(classFound == -1){
                            System.out.println(fitnessClass.getName() + " does not exist with " + instructor + " at " + location);
                        }
                        else{
                            classes = classSchedule.getClasses();
                            fitnessClass = classes[classFound];
                            fitnessClass.guestCheckedIn(member);
                            fitnessClass.printPartcipants();
                        }
                        break;
                    // Removes member from respective class list
                    case "D":
                    className =input.nextToken();
                    instructor = input.nextToken();
                    location = validLocation(input.nextToken());
                    fName = input.nextToken();
                    lName = input.nextToken();
                    dob = new Date(input.nextToken());
                    member = validMemberAlt(fName, lName, dob, null, null);
                    member = memberDatabase.compareMember(member);
                    fitnessClass = new FitnessClass(className, instructor, null, location);
                    classFound = classSchedule.findClass(fitnessClass);
                    if(classFound == -1){
                       System.out.println(fitnessClass.getName() + " does not exist with " + instructor + " at " + location);
                       break;
                    }
                    else{
                        classes = classSchedule.getClasses();
                        boolean dropped = classes[classFound].drop(member);
                        if (dropped){
                            System.out.println(fName + " " + lName + " has dropped " + className);
                        }
                        else if(!dropped){
                            System.out.println(fName + " " + lName + " was not checked into " + className);
                        }
                    }
                    break;
                    // Removes guest from respective class list
                    case "DG":
                    className =input.nextToken();
                    instructor = input.nextToken();
                    location = validLocation(input.nextToken());
                    fName = input.nextToken();
                    lName = input.nextToken();
                    dob = new Date(input.nextToken());
                    member = validMemberAlt(fName, lName, dob, null, null);
                    member = memberDatabase.compareMember(member);
                    fitnessClass = new FitnessClass(className, instructor, null, location);
                    classFound = classSchedule.findClass(fitnessClass);
                    if(classFound == -1){
                        System.out.println(fitnessClass.getName() + " does not exist with " + instructor + " at " + location);
                        break;
                    }
                    else{
                        classes = classSchedule.getClasses();
                        fitnessClass = classes[classFound];
                        fitnessClass.guestDropped(member);
                    }
                    break;
                    // Ends the program
                    case "Q":
                        System.out.println("Gym Manager terminated.");
                        scanner.close();
                        running = false;
                        System.exit(0);
                        break;
                    // Invalid command
                    default:
                        System.out.println("Command " + command +" is invalid");
                        break;
                    } 
                }  catch (Exception ignored){ }
            }
        } 
    }

    /**
     * Helper method that verifies all information that was given
     * Checks if dates and location are valid and makes sure member is 18 or older
     * and membership has not expired
     * @param fName String value representing member's first name
     * @param lName String value representing member's last name 
     * @param dob Member's date of birth
     * @param expire Member's membership expiry date
     * @param location Location of Gym where member is registering from
     * @param type type of membership patron is registering for
     * @return Null if any of the information provided is not valid to register
     * otherwise returns a new Member object with the provided information
     */
    private Member validMember(String fName, String lName, Date dob, Date expire, Location location, String type){
        boolean validDoB= dob.isValid();
        boolean validExpire= expire.isValid();
        int sameDay = dob.compareTo(dob);
        int expireDay = expire.compareTo(expire);
        boolean over18 = dob.is18(dob); 

        if (!validDoB){
            System.out.println(dob + " is not a valid date");
            return null;
        }

        if (!validExpire){
            System.out.println(expire + " is not a valid date");
            return null;
        }

        if (location == null){
            return null;
        }

        if (sameDay >= 0){
            System.out.println(dob + " is not a valid date");
            return null;
        }

        if(expireDay <= 0){
            System.out.println(expire + " membership has expired");
            return null;
        }

        if (!over18){
            System.out.println("Patron is not 18 or older");
            return null;
        }
        if (type.equalsIgnoreCase("Standard")){
            return new Member(fName, lName, dob, expire, location);
        } else if ( type.equalsIgnoreCase("Family")){
            return new Family(fName, lName, dob, expire, location);
        } else if (type.equalsIgnoreCase("Premium")){
            return new Premium(fName, lName, dob, expire, location);
        }
        return new Member(fName, lName, dob, expire, location);
    }

    /**
     * Alternative helper method for when not enough arguments are provide in certain
     * commands that validMember() is unable to account for and performs same function
     * as validMember().
     * @param fName String value representing member's first name
     * @param lName String value representing member's last name 
     * @param dob Member's date of birth
     * @param expire Null value as expire date is not given for certain commands
     * @param location Null value as location is not given for certain commands
     * @return Null if any of the information provided is not valid to register
     * otherwise returns a new Member object with the provided information
     */
    private Member validMemberAlt(String fName, String lName, Date dob, Date expire, Location location){
        boolean validDoB= dob.isValid();
        int sameDay = dob.compareTo(dob);
        boolean over18 = dob.is18(dob); 

        if (!validDoB){
            System.out.println(dob + " is not a valid date");
            return null;
        }
        if (sameDay >= 0){
            System.out.println(dob + " is not a valid date");
            return null;
        }
        if (!over18){
            System.out.println("Patron is not 18 or older");
            return null;
        }
        return new Member(fName, lName, dob, null, null);
    }

    /**
     * Helper method that verifies location given is a valid gym location
     * @param location Input string value representing a given location
     * @return If input String is a valid location returns corresponding Location 
     * other wise returns null.
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
        System.out.println(location + " is not a valid location");
        return null;  
    }

    /**
     * Helper method that verifies timeslot given is a valid timeslot for a class
     * @param time Input string value representing a given timeslot
     * @return If input String is a valid timeslot returns corresponding timeslot
     * other wise returns null.
     */
    public Time validTime(String time){
        if (time.equalsIgnoreCase(Time.MORNING.name())){
            return Time.MORNING;
        }
        if (time.equalsIgnoreCase(Time.AFTERNOON.name())){
            return Time.AFTERNOON;
        }
        if (time.equalsIgnoreCase(Time.EVENING.name())){
            return Time.EVENING;
        }
        return null;
    }

    /**
     * Helper method that itterates through all classes in the same timeslot as the compared fitnessClass and
     * finds if a member is already checked into another class that would cause a conflict
     * @param fitnessClass fitnessClass that is being compared to
     * @param member member that is checking into a fitnessClass and is being searched for participation in other classes
     * @param classSchedule list of all other classes 
     * @return if a conflict is found return the fitnessClass where the conflict occurs otherwise return null
     */
    public FitnessClass conflict(FitnessClass fitnessClass, Member member, ClassSchedule classSchedule){
        FitnessClass[] classList = classSchedule.getClasses();
        for (int i = 0; i < classList.length; i++){
            if( classList[i] != fitnessClass && classList[i].getTimeslot() == fitnessClass.getTimeslot()){
               if (classList[i].classConflict(member)){
                fitnessClass.drop(member);
                return classList[i];
               } 
               else{
                continue;
               }
            }
        }
        return null;
    }
}