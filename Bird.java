/**
 * Joshua Thibault
 * CEN 3024 - Software Development I
 * April 10th, 2026
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
     * This will initialize the bird object with the parameters.
     * @param ID The ID of the bird initialized.
     * @param species The species of the bird initialized.
     * @param color The color of the bird initialized.
     * @param size The size of the bird initialized.
     * @param beakShape The beak shape of the bird initialized.
     * @param gender The gender of the bird initialized.
     * @param wingspan The wingspan of the bird initialized.
     * @param activityPattern The activity pattern of the bird initialized.
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
     * This will return the ID of the bird object.
     * @return int: ID of the bird.
     */

    public int getID() {

        return ID;

    }

}