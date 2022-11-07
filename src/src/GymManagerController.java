package src;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;
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
    private MenuItem addStandard;
    @FXML
    private Button remove;
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
    private TextField fitnessClassName;
    @FXML
    private TextField instructorName;
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
    private MenuItem memberCheckIn;
    @FXML
    private MenuItem guestCheckIn;
    @FXML
    private MenuItem memberDrop;
    @FXML
    private MenuItem guestDrop;
    @FXML
    private MenuItem addFamily;
    @FXML
    private MenuItem addPremium;

    @FXML
    private TextArea messageArea;

    MemberDatabase memberDatabase = new MemberDatabase();
    ClassSchedule classSchedule = new ClassSchedule();

    /**
     * Loads schedule of fitness classes into class schedule
     * @param event
     * @throws FileNotFoundException
     */
    @FXML
    void loadSchedule(ActionEvent event) throws FileNotFoundException {
       classSchedule.loadSchedule();
        FitnessClass[] classes = classSchedule.getClasses();
        messageArea.appendText("-Fitness classes loaded-" + "\n");
        for (int i = 0; i < classes.length; i++){
            messageArea.appendText(classes[i].toString() + "\n");
        }
        messageArea.appendText("-end of class list-" );
    }

    /**
     * Load list of members into member database
     * @param event
     * @throws FileNotFoundException
     */
    @FXML
    void loadMembers(ActionEvent event) throws FileNotFoundException {
        memberDatabase.loadMembers();
        messageArea.appendText("-List of members loaded-\n");
        Member[] members = memberDatabase.getMList();
        int listSize = memberDatabase.getSize();
       for (int i = 0; i < listSize; i++){
           messageArea.appendText(members[i].toString() + "\n");
       }
        messageArea.appendText("-end of list-\n");
    }

    /**
     * Adds new member to member database at standard plan
     * @param event
     */
    @FXML
    void addMember(ActionEvent event) {
        try {
            String fName = firstName1.getText();
            String lName = lastName1.getText();
            Date dob = new Date(DOB1.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyy")));
            Date today = new Date();
            int expMonth = today.getMonth() + 3;
            int expYear = today.getYear();
            if (expMonth > 12) {
                expMonth = expMonth - 12;
                expYear = expYear + 1;
            }
            String date = expMonth + "/" + today.getDay() + "/" + expYear;
            Date expire = new Date(date);
            Location location = validLocation(location1.getText());
            Member member = validMember(fName, lName, dob, expire, location, "Standard");
            if (member == null){
                return;
            }
            boolean wasAdded = memberDatabase.add(member);
            if (wasAdded) {
                messageArea.appendText(fName + " " + lName + " was added\n");
            } else {
                messageArea.appendText(fName + " " + lName + " is already in the database\n");
            }
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("ERROR!!");
            alert.setHeaderText("A value is missing.");
            alert.setContentText("Please enter a valid date in the format of mm /dd/yyyy " +
                    "press enter after typing in the date");
            alert.showAndWait();
        }
    }

    /**
     * Add new member to member database at family plan
     * @param event
     */
    @FXML
    void addFamily(ActionEvent event){
        try {
            String fName = firstName1.getText();
            String lName = lastName1.getText();
            Date dob = new Date(DOB1.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyy")));
            Date today = new Date();
            int expMonth = today.getMonth() + 3;
            int expYear = today.getYear();
            if (expMonth > 12) {
                expMonth = expMonth - 12;
                expYear = expYear + 1;
            }
            String date = expMonth + "/" + today.getDay() + "/" + expYear;
            Date expire = new Date(date);
            Location location = validLocation(location1.getText());
            Member member = validMember(fName, lName, dob, expire, location, "Family");
            if (member == null){
                return;
            }
            Family family = (Family) member;
            boolean wasAdded = memberDatabase.add(family);
            if (wasAdded) {
                messageArea.appendText(fName + " " + lName + " was added\n");
            } else {
                messageArea.appendText(fName + " " + lName + " is already in the database\n");
            }
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("ERROR!!");
            alert.setHeaderText("A value is missing.");
            alert.setContentText("Please enter a valid date in the format of mm /dd/yyyy " +
                    "press enter after typing in the date");
            alert.showAndWait();
        }
    }

    /**
     * adds new member to member database at premium plan
     * @param event
     */
    @FXML
    void addPremium(ActionEvent event) {
        try {
            String fName = firstName1.getText();
            String lName = lastName1.getText();
            Date dob = new Date(DOB1.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyy")));
            Date today = new Date();
            int expYear = today.getYear() + 1;
            String date = today.getMonth() + "/" + today.getDay() + "/" + expYear;
            Date expire = new Date(date);
            Location location = validLocation(location1.getText());
            Member member = validMember(fName, lName, dob, expire, location, "Premium");
            if (member == null){
                return;
            }
            Premium premium = (Premium) member;
            boolean wasAdded = memberDatabase.add(premium);
            if (wasAdded) {
                messageArea.appendText(fName + " " + lName + " was added\n");
            } else {
                messageArea.appendText(fName + " " + lName + " is already in the database\n");
            }
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("ERROR!!");
            alert.setHeaderText("A value is missing.");
            alert.setContentText("Please enter a valid date in the format of mm /dd/yyyy " +
                    "press enter after typing in the date");
            alert.showAndWait();
        }
    }

    /**
     * removes member from member database
     * @param event
     */
    @FXML
    void removeMember(ActionEvent event) {
        try {
            String fName = firstName1.getText();
            String lName = lastName1.getText();
            Date dob = new Date(DOB1.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyy")));
            Member member = validMemberAlt(fName, lName, dob, null, null);
            member = memberDatabase.compareMember(member);
            boolean wasRemoved = memberDatabase.remove(member);
            if (wasRemoved) {
                messageArea.appendText(fName + " " + lName + " was removed\n");
            } else {
                messageArea.appendText(fName + " " + lName + " is not in the database\n");
            }
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("ERROR!!");
            alert.setHeaderText("A value is missing.");
            alert.setContentText("Please enter a valid date in the format of mm /dd/yyyy " +
                    "press enter after typing in the date");
            alert.showAndWait();
        }
    }

    /**
     * Adds member as a participant to corresponding fitness class
     * @param event
     */
    @FXML
    void checkIn(ActionEvent event) {
        try {
            String className = fitnessClassName.getText();
            String instructor = instructorName.getText();
            Location location = validLocation(location2.getText());
            String fName = firstName2.getText();
            String lName = lastName2.getText();
            Date dob = new Date(DOB2.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyy")));
            Member member = validMemberAlt(fName, lName, dob, null, null);
            member = memberDatabase.compareMember(member);
            FitnessClass fitnessClass = new FitnessClass(className, instructor, null, location);
            int classFound = classSchedule.findClass(fitnessClass);
            FitnessClass[] classes = classSchedule.getClasses();
            if (classFound == -1) {
                messageArea.appendText("\n" + fitnessClass.getName() + " does not exist with " + instructor + " at " + location);
            } else {
                boolean checkedIn = classes[classFound].checkedIn(member);
                FitnessClass conflict = conflict(classes[classFound], member, classSchedule);
                if (checkedIn && conflict == null) {
                    messageArea.appendText("\n" + fName + " " + lName + " checked in " + classes[classFound].getName()
                            + " - " + classes[classFound].getInstructor() + ", "
                            + classes[classFound].getTimeslot().getTime() + ", " + location + "\n");
                } else if (!checkedIn) {
                    messageArea.appendText("\n" + fName + " " + lName + " has already checked into " + classes[classFound].getName()
                            + " - " + classes[classFound].getInstructor() + ", "
                            + classes[classFound].getTimeslot().getTime() + ", " + location);
                } else if (checkedIn && conflict != null) {
                    messageArea.appendText("\n" + fName + " " + lName + " has already checked into " + conflict.getName()
                            + " - " + conflict.getInstructor() + ", "
                            + conflict.getTimeslot().getTime() + ", " + conflict.getLocation());
                }
            }
            Member[] fitnessClassList = classes[classFound].getList();
            Member[] guestList = classes[classFound].getGuestList();
            messageArea.appendText("- Participants -\n");
            for(int j = 0; j < fitnessClassList.length; j++){
                if (fitnessClassList[j] != null){
                    messageArea.appendText(fitnessClassList[j].toString() + "\n");
                }
            }
            if (guestList[0] != null){
                messageArea.appendText("- Guests -\n");
                for(int j = 0; j < guestList.length; j++){
                    if(guestList[j] != null){
                        messageArea.appendText(guestList[j].toString() + "\n");
                    }
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("ERROR!!");
            alert.setHeaderText("A value is missing.");
            alert.setContentText("Please enter a valid date in the format of mm /dd/yyyy " +
                    "press enter after typing in the date");
            alert.showAndWait();
        }
    }

    /**
     * adds a guest to corresponding fitness class
     * @param event
     */
    @FXML
    void checkInGuest(ActionEvent event) {
        try {
            String className = fitnessClassName.getText();
            String instructor = instructorName.getText();
            Location location = validLocation(location2.getText());
            String fName = firstName2.getText();
            String lName = lastName2.getText();
            Date dob = new Date(DOB2.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyy")));
            Member member = validMemberAlt(fName, lName, dob, null, null);
            member = memberDatabase.compareMember(member);
            FitnessClass fitnessClass = new FitnessClass(className, instructor, null, location);
            int classFound = classSchedule.findClass(fitnessClass);
            FitnessClass[] classes = classSchedule.getClasses();
            if (classFound == -1) {
                messageArea.appendText("\n" + fitnessClass.getName() + " does not exist with " + instructor + " at " + location);
            } else {
                fitnessClass = classes[classFound];
                fitnessClass.guestCheckedIn(member);
                Member[] fitnessClassList = classes[classFound].getList();
                Member[] guestList = classes[classFound].getGuestList();
                messageArea.appendText("- Participants -\n");
                for (int j = 0; j < fitnessClassList.length; j++) {
                    if (fitnessClassList[j] != null) {
                        messageArea.appendText(fitnessClassList[j].toString() + "\n");
                    }
                }
                if (guestList[0] != null) {
                    messageArea.appendText("- Guests -\n");
                    for (int j = 0; j < guestList.length; j++) {
                        if (guestList[j] != null) {
                            messageArea.appendText(guestList[j].toString() + "\n");
                        }
                    }
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("ERROR!!");
            alert.setHeaderText("A value is missing.");
            alert.setContentText("Please enter a valid date in the format of mm /dd/yyyy " +
                    "press enter after typing in the date");
            alert.showAndWait();
        }
    }

    /**
     * drops member as a participant from corresponding fitness class
     * @param event
     */
    @FXML
    void drop(ActionEvent event) {
        try {
            String className = fitnessClassName.getText();
            String instructor = instructorName.getText();
            Location location = validLocation(location2.getText());
            String fName = firstName2.getText();
            String lName = lastName2.getText();
            Date dob = new Date(DOB2.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyy")));
            Member member = validMemberAlt(fName, lName, dob, null, null);
            member = memberDatabase.compareMember(member);
            FitnessClass fitnessClass = new FitnessClass(className, instructor, null, location);
            int classFound = classSchedule.findClass(fitnessClass);
            if(classFound == -1){
                System.out.println(fitnessClass.getName() + " does not exist with " + instructor + " at " + location);
                return;
            }
            else{
                FitnessClass[] classes = classSchedule.getClasses();
                boolean dropped = classes[classFound].drop(member);
                if (dropped){
                    messageArea.appendText(fName + " " + lName + " has dropped " + className + "\n");
                }
                else if(!dropped){
                    messageArea.appendText(fName + " " + lName + " was not checked into " + className + "\n");
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("ERROR!!");
            alert.setHeaderText("A value is missing.");
            alert.setContentText("Please enter a valid date in the format of mm /dd/yyyy " +
                    "press enter after typing in the date");
            alert.showAndWait();
        }
    }

    /**
     * drops guest from corresponding fitness class
     * @param event
     */
    @FXML
    void dropGuest(ActionEvent event){
        try {
            String className = fitnessClassName.getText();
            String instructor = instructorName.getText();
            Location location = validLocation(location2.getText());
            String fName = firstName2.getText();
            String lName = lastName2.getText();
            Date dob = new Date(DOB2.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyy")));
            Member member = validMemberAlt(fName, lName, dob, null, null);
            member = memberDatabase.compareMember(member);
            FitnessClass fitnessClass = new FitnessClass(className, instructor, null, location);
            int classFound = classSchedule.findClass(fitnessClass);
            FitnessClass[] classes = classSchedule.getClasses();
            if (classFound == -1) {
                messageArea.appendText("\n" + fitnessClass.getName() + " does not exist with " + instructor + " at " + location + "\n");
            } else {
                fitnessClass.guestDropped(member);
                messageArea.appendText("Guest was dropped\n");
            }
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("ERROR!!");
            alert.setHeaderText("A value is missing.");
            alert.setContentText("Please enter a valid date in the format of mm /dd/yyyy " +
                    "press enter after typing in the date");
            alert.showAndWait();
        }
    }

    /**
     * prints all members in the database
     * @param event
     */
    @FXML
    void print(ActionEvent event){
        messageArea.appendText("-List of members\n");
        Member[] members = memberDatabase.getMList();
        int listSize = memberDatabase.getSize();
        for (int i = 0; i < listSize; i++){
            messageArea.appendText(members[i].toString() + "\n");
        }
        messageArea.appendText("-end of list-\n");
    }

    /**
     * prints all members in the database sorted by county
     * @param event
     */
    @FXML
    void printByCounty(ActionEvent event){
        messageArea.appendText("-List of members sorted by County\n");
        Member[] members = memberDatabase.printByCounty();
        int listSize = memberDatabase.getSize();
        for (int i = 0; i < listSize; i++){
            messageArea.appendText(members[i].toString() + "\n");
        }
        messageArea.appendText("-end of list-\n");
    }

    /**
     * prints all members in the database sorted by Last/First Name
     * @param event
     */
    @FXML
    void printByName(ActionEvent event){
        messageArea.appendText("-List of members sorted by name\n");
        Member[] members = memberDatabase.printByName();
        int listSize = memberDatabase.getSize();
        for (int i = 0; i < listSize; i++){
            messageArea.appendText(members[i].toString() + "\n");
        }
        messageArea.appendText("-end of list-\n");
    }

    /**
     * prints all members in the database sorted by expire date
     * @param event
     */
    @FXML
    void printByExpire(ActionEvent event){
        messageArea.appendText("-List of members sorted by expiration date\n");
        Member[] members = memberDatabase.printByExpirationDate();
        int listSize = memberDatabase.getSize();
        for (int i = 0; i < listSize; i++){
            messageArea.appendText(members[i].toString() + "\n");
        }
        messageArea.appendText("-end of list-\n");
    }

    /**
     * prints all members in the database with a membership fee
     * @param event
     */
    @FXML
    void printByFee(ActionEvent event){
        messageArea.appendText("-List of members sorted by expiration date\n");
        Member[] members = memberDatabase.printByExpirationDate();
        int listSize = memberDatabase.getSize();
        for (int i = 0; i < listSize; i++){
            messageArea.appendText(members[i].toString() + " $" + members[i].membershipFee() + "\n");
        }
        messageArea.appendText("-end of list-\n");
    }

    /**
     * prints all classes and particpants of each class if there are any
     * @param event
     */
    @FXML
    void printClasses(ActionEvent event){
        FitnessClass[] classes = classSchedule.getClasses();
        if (classSchedule.getNumberofClasses() == 0){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("ERROR!!");
            alert.setHeaderText("Their are no available classes.");
            alert.setContentText("Please load classes first from .txt file");
            alert.showAndWait();
            return;
        }
        messageArea.appendText("- Fitness classes -\n");
        for (int i = 0; i < classes.length; i++){
            if (classes[i].getClassSize() == 0){
                messageArea.appendText(classes[i].toString() + "\n");
            }
            else{
                messageArea.appendText(classes[i].toString() + "\n");
                Member[] fitnessClassList = classes[i].getList();
                Member[] guestList = classes[i].getGuestList();
                System.out.println("- Participants -\n");
                for(int j = 0; j < fitnessClassList.length; j++){
                    if (fitnessClassList[j] != null){
                        messageArea.appendText(fitnessClassList[j].toString() + "\n");
                    }
                }
                if (guestList[0] != null){
                    System.out.println("- Guests -\n");
                    for(int j = 0; j < guestList.length; j++){
                        if(guestList[j] != null){
                            messageArea.appendText(guestList[j].toString() + "\n");
                        }
                    }
                }
            }
        }
        messageArea.appendText("- End of class list. -\n");
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
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("ERROR!!");
            alert.setHeaderText("Invalid date entered.");
            alert.setContentText("Please enter a valid date in the format of mm /dd/yyyy " +
                    "press enter after typing in the date");
            alert.showAndWait();
            return null;
        }

        if (!validExpire){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("ERROR!!");
            alert.setHeaderText("Invalid date entered.");
            alert.setContentText("Please enter a valid date in the format of mm /dd/yyyy " +
                    "press enter after typing in the date");
            alert.showAndWait();
            return null;
        }

        if (location == null){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("ERROR!!");
            alert.setHeaderText("Invalid location entered.");
            alert.setContentText("Please enter a valid location ");
            alert.showAndWait();
            return null;
        }

        if (sameDay >= 0){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("ERROR!!");
            alert.setHeaderText("Invalid date entered.");
            alert.setContentText("Please enter a valid date of birth ");
            alert.showAndWait();
            return null;
        }

        if(expireDay <= 0){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("ERROR!!");
            alert.setHeaderText("Membership has expired.");
            alert.setContentText("Please renew membership ");
            alert.showAndWait();
            return null;
        }

        if (!over18){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("ERROR!!");
            alert.setHeaderText("Invalid date entered.");
            alert.setContentText("Patron is not over 18 or older ");
            alert.showAndWait();
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
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("ERROR!!");
            alert.setHeaderText("Invalid date entered.");
            alert.setContentText("Please enter a valid date in the format of mm /dd/yyyy " +
                    "press enter after typing in the date");
            alert.showAndWait();
            return null;
        }
        if (sameDay >= 0){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("ERROR!!");
            alert.setHeaderText("Invalid date entered.");
            alert.setContentText("Please enter a valid date of birth ");
            alert.showAndWait();
            return null;
        }
        if (!over18){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("ERROR!!");
            alert.setHeaderText("Invalid date entered.");
            alert.setContentText("Patron is not over 18 or older ");
            alert.showAndWait();
            return null;
        }
        return new Member(fName, lName, dob, null, null);
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
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("ERROR!!");
        alert.setHeaderText("Invalid location entered.");
        alert.setContentText("Please enter a valid location");
        alert.showAndWait();
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
            }
        }
        return null;
    }

}
