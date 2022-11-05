package src;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.Alert.AlertType;

public class GymManagerController {
    @FXML
    private Button add;
    @FXML
    private Button remove;
    @FXML
    private Button checkIn;
    @FXML
    private Button drop;
    @FXML
    private TextField firstName1;
    @FXML
    private TextField lastName1;
    @FXML
    private DatePicker DOB1;
    @FXML
    private TextField firstName2;
    @FXML
    private TextField lastName2;
    @FXML
    private DatePicker DOB2;
    @FXML
    private TextField className;
    @FXML
    private TextField instructor;
    @FXML
    private TextField location1;
    @FXML
    private TextField location2;
    @FXML
    private MenuItem print;
    @FXML
    private MenuItem printByCounty;
    @FXML
    private MenuItem printByName;
    @FXML
    private MenuItem printByExpire;
    @FXML
    private MenuItem loadMembers;
    @FXML
    private MenuItem printClasses;
    @FXML
    private MenuItem loadClasses;
    @FXML
    private MenuItem firstBill;
    @FXML
    private MenuItem nextBill;

    @FXML
    private TextArea messageArea;

    MemberDatabase memberDatabase = new MemberDatabase();
    ClassSchedule classSchedule = new ClassSchedule();

    @FXML
    void loadSchedule(ActionEvent event) throws FileNotFoundException {
        File schedule = new File("src\\info\\classSchedule");
        Scanner scan = new Scanner(schedule);
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            StringTokenizer info = new StringTokenizer(line, " ");
            String name = info.nextToken();
            String instructor = info.nextToken();
            Time time = validTime(info.nextToken());
            Location location = validLocation(info.nextToken());
            FitnessClass fitnessClass = new FitnessClass(name, instructor, time, location);
            classSchedule.addClass(fitnessClass);
        }
        scan.close();
        FitnessClass[] classes = classSchedule.getClasses();
        messageArea.appendText("-Fitness classes loaded-");
        for (int i = 0; i < classes.length; i++){
            messageArea.appendText(classes[i].toString());
        }
        messageArea.appendText("-end of class list-");
        System.out.println();
    }

    @FXML
    void loadMembers(ActionEvent event) throws FileNotFoundException {
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
            memberDatabase.add(member);
        }
        scan.close();
        System.out.println("-List of members loaded-");
        memberDatabase.print();
        System.out.println("-end of list-");
        System.out.println();
    }

    void addMember(ActionEvent event){

    }
    void removeMember(ActionEvent event){

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
