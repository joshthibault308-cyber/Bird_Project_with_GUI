/**
 * Joshua Thibault
 * CEN 3024 - Software Development I
 * March 10th, 2026
 * Bird.java
 * This class represents the bird object and will be used to represent birds with their specific attributes in the system.
 */

public class Bird {

    int ID;
    String species;
    String color;
    float size;
    String beakShape;
    char gender;
    float wingspan;
    String activityPattern;

    /**
     * method: Bird
     * parameter: int ID, String species, String color, float size, String
     * beakShape, char gender, float wingspan, String activityPattern
     * return: none
     * purpose: This will initialize the bird object with the parameters.
     */
    public Bird(int ID, String species, String color, float size, String beakShape, char gender, float wingspan,
            String activityPattern) {

        this.ID = ID;
        this.species = species;
        this.color = color;
        this.size = size;
        this.beakShape = beakShape;
        this.gender = gender;
        this.wingspan = wingspan;
        this.activityPattern = activityPattern;

    }

    /**
     * method: getID
     * parameter: none
     * return: int
     * purpose: This will return the ID of the bird object.
     */
    public int getID() {

        return ID;

    }

}