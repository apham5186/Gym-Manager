package src;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Class that stores a list of fitness Classes to an array is able to find if a class exist and add new classes
 * if needed
 * @author Alex Pham, Alex Lesica
 */
public class ClassSchedule {
    private FitnessClass [] classes;
    private int numClasses;

    /**
     * Constructor method that starts with memory for 16 classes
     */
    ClassSchedule(){
        this.classes = new FitnessClass[15];
        this.numClasses = 0;
    }
    /**
     * Method that returns the array of classes
     * @return array of classes
     */
    public FitnessClass[] getClasses(){
        return classes;
    }

    /**
     * Method that returns the number of classes
     * @return int value representing number of classes
     */
    public int getNumberofClasses(){
        return numClasses;
    }

    /**
     * Method that looks to see if the class exist in the schedule and 
     * @param fitnessClass class that is being looked for
     * @return true if fitnessClass was found in the schedule false otherwise
     */
    public int findClass(FitnessClass fitnessClass){
        FitnessClass[] classes = this.classes;
        String className = fitnessClass.getName();
        String instructor = fitnessClass.getInstructor();
        Location location = fitnessClass.getLocation();
        for (int i = 0; i< classes.length; i++){
            String className2 = classes[i].getName();
            String instructor2 = classes[i].getInstructor();
            Location location2 = classes[i].getLocation();
            if(classes[i] != null && className.equalsIgnoreCase(className2) && instructor.equalsIgnoreCase(instructor2) && location == location2){
               return i;
            }
        }
        return -1;
    }

    /**
     * Method that adds a fitness class to the classes array
     * @param fitnessClass
     */
    public void addClass(FitnessClass fitnessClass){
        FitnessClass[] classes = this.classes;
        for (int i = 0; i < classes.length; i++){
            if (classes[i] == null){
                classes[i] = fitnessClass;
                numClasses++;
                return;
            }
        }
    }

    /**
     * Method that loads list of fitness calsses into the class schedule database
     * @throws FileNotFoundException
     */
    void loadSchedule() throws FileNotFoundException {
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
            addClass(fitnessClass);
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

    /**
     * Helper method that verifies timeslot given is a valid timeslot for a class
     * @param time Input string value representing a given timeslot
     * @return If input String is a valid timeslot returns corresponding timeslot
     * otherwise returns null.
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
}