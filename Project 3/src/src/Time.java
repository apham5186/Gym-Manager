package src;
import java.util.StringTokenizer;
import java.util.Calendar;

/**
 * Enum class that stores TimeSlots for when classes start and separates them into
 * hours and minutes
 * 
 * @author Alex Pham, Alex Lescia
 */
public enum Time {
    MORNING("9:30"),
    AFTERNOON("14:00"),
    EVENING("18:30");
    private String time;
    private int hour;
    private int minutes;

    /**
     * Constructor method that takes current time and fills instance variables with
     * their respective value. Used when String is not given.
     */
     Time() {
        Calendar current= Calendar.getInstance();
        this.hour = current.get(Calendar.HOUR_OF_DAY);
        this.minutes = current.get(Calendar.MINUTE);
    }

    /**
     * Constructor method that takes given string in the format of hh:mm and separates
     * it into hours and minutes
     * @param time String value representing time in the format of hh:mm
     */
    Time(String time){
        this.time = time;
        StringTokenizer st = new StringTokenizer(time, ":");
        this.hour = Integer.parseInt(st.nextToken());
        this.minutes = Integer.parseInt(st.nextToken());
    }

    /**
     * Returns time in format of hh:mm
     * @return time as a String value in the format of hh:mm
     */
    public String getTime(){
        return time;
    }

    /**
     * Returns hour
     * @return hour as an int value
     */
    public int getHour(){
        return hour;
    }

    /**
     * Returns minutes
     * @return minutes as an int value
     */
    public int getMinutes(){
        return minutes;
    }
}
