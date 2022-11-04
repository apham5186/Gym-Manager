package src;

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
}