package src;
/**
 * Enum class that store information of all 5 gym loctaions including the name of the 
 * town, county and zipcode of the corresponding gym location.
 * 
 * @author Alex Pham, Alex Lesica
 */
public enum Location {
    BRIDGEWATER("Bridgewater", "08807", "Somerset"), 
    EDISON("Edison", "08837", "Middlesex"), 
    FRANKLIN("Franklin", "08873", "Somerset"), 
    PISCATAWAY("Piscataway", "08854", "Middlesex"), 
    SOMERVILLE("SomerVille", "08876", "Somerset");

    private String town;
    private String zipcode;
    private String county;

/**
 * Constructor method that creates an Enum object from the following values
 * @param town Name of the town the gym resides in
 * @param zipcode 5 digit string value representaing area's zipcode
 * @param county Name of the county the gym resides in
 */
    Location(String town, String zipcode, String county){
        this.town = town;
        this.zipcode = zipcode;
        this.county = county;
    }

    /**
     * Returns name of the town from Location object
     * @return String value representing name of the town
     */
    public String getTown(){
        return town;
    }

    /**
     * Returns zipcode from Location object
     * @return String value representing zipcode
     */
    public String getZipcode(){
        return zipcode;
    }

    /**
     * Returns name of the county
     * @return String value representing the county
     */
    public String getCounty(){
        return county;
    }
    
}