/**
 * Joshua Thibault
 * CEN 3024 - Software Development I
 * March 30th, 2026
 * BirdRepository.java
 * This class will have the functions needed to add birds, find birds, update birds, seeing if attributes are correct, and sorting attribute values.
 */

import com.sun.tools.javac.Main;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.stream.Collectors;

public class BirdRepository {

    static ArrayList<Bird> birdList = new ArrayList<Bird>();

    /**
     * method: realFile
     * parameter: String
     * return: Boolean
     * purpose: This method is to check if the file can be read and if a real file. It will return true if it can be read.
     */

    static Boolean realFile(File file) {

        try (FileInputStream fileStream = new FileInputStream(file)) {

            return true;

        } catch (Exception e) {

            return false;

        }

    }

    /**
     * method: addBirdwithFileFormat
     * parameter: String
     * return: Bird
     * purpose: This is for adding birds for each line of a file and making sure their attributes are correct.
     */

    static Bird addBirdwithFileFormat(String fileLine) {

        int nextDash = 0;
        int nextAttributeinString = 0;
        boolean validID = true;

        nextDash = fileLine.indexOf("-", nextAttributeinString);
        int ID = Integer.parseInt(fileLine.substring(nextAttributeinString, nextDash));
        nextAttributeinString = nextDash + 1;

        nextDash = fileLine.indexOf("-", nextAttributeinString);
        String species = fileLine.substring(nextAttributeinString, nextDash);
        nextAttributeinString = nextDash + 1;

        nextDash = fileLine.indexOf("-", nextAttributeinString);
        String color = fileLine.substring(nextAttributeinString, nextDash);
        nextAttributeinString = nextDash + 1;

        nextDash = fileLine.indexOf("-", nextAttributeinString);
        float size = Float.parseFloat(fileLine.substring(nextAttributeinString, nextDash));
        nextAttributeinString = nextDash + 1;

        nextDash = fileLine.indexOf("-", nextAttributeinString);
        String beakShape = fileLine.substring(nextAttributeinString, nextDash);
        nextAttributeinString = nextDash + 1;

        nextDash = fileLine.indexOf("-", nextAttributeinString);
        char gender = Character.toLowerCase(fileLine.substring(nextAttributeinString, nextDash).charAt(0));
        nextAttributeinString = nextDash + 1;

        nextDash = fileLine.indexOf("-", nextAttributeinString);
        float wingspan = Float.parseFloat(fileLine.substring(nextAttributeinString, nextDash));
        nextAttributeinString = nextDash + 1;

        String activityPattern = fileLine.substring(nextAttributeinString);

        if (BirdRepository.getBird(ID) != null) {

            validID = false;

        }

        if (validID) {

            if (gender == 'm' || gender == 'f') {

                BirdRepository.addBird(ID, species, color, size, beakShape, gender, wingspan, activityPattern);
                return new Bird(ID, species, color, size, beakShape, gender, wingspan, activityPattern);

            } else {

                System.out.println("Invalid gender. Please enter 'm' or 'f'.");

            }
        } else {

            System.out.println("The ID is already used.");

        }

        return null;

    }

    /**
     * method: addBird
     * parameter: int ID, String species, String color, float size, String beakShape, char gender, float wingspan, String activityPattern
     * return: Boolean
     * purpose: This is for adding a bird to the arrayList. It will make sure that the ID is not previously used for another bird.
     */

    static Boolean addBird(int ID, String species, String color, float size, String beakShape, char gender, float wingspan, String activityPattern) {

        if (getBird(ID) == null) {
            Bird newBird = new Bird(ID, species, color, size, beakShape, gender, wingspan, activityPattern);
            birdList.add(newBird);
            MainFrame.tableModel.addRow(new String[]{String.valueOf(ID), species, color, String.valueOf(size), beakShape, String.valueOf(gender), String.valueOf(wingspan), activityPattern});
            return getBird(ID) != null;

        } else {

            return false;

        }

    }

    /**
     * method: getBird
     * parameter: int ID
     * return: Bird
     * purpose: This is for getting a bird based on its ID. It will return null if it can not be found.
     */

    static Bird getBird(int ID) {

        for (Bird birdItem : birdList) {

            if (birdItem.ID == ID) {

                return birdItem;
            }

        }

        return null;

    }

    /**
     * method: removeBird
     * parameter: Bird bird
     * return: Boolean
     * purpose: This is for removing a bird from the arrayList.
     */

    static Boolean removeBird(Bird bird) {

        birdList.remove(bird);

        for (int i = 0; i < MainFrame.tableModel.getRowCount(); i++) {

            if (Integer.parseInt((String) MainFrame.tableModel.getValueAt(i, 0)) == bird.getID()) {

                MainFrame.tableModel.removeRow(i);
                break;

            }

        }

        return getBird(bird.getID()) == null;

    }

    /**
     * method: updateBird
     * parameter: String attribute, T value, Bird bird
     * return: Boolean
     * purpose: This is for updating a birds attribute. It will see which attribute will be updated for the bird and change the value for the bird.
     */

    static <T> Boolean updateBird(String attribute, T value, Bird bird) {

        boolean updatedBird = false;
        int rowNumber = -1;
        int columnNumber = -1;
        String attributeName = attribute;
        int newValue = -1;
        String strValue = "";

        for (int i = 0; i < MainFrame.tableModel.getRowCount(); i++) {

            if (MainFrame.tableModel.getValueAt(i, 0) instanceof String) {

                strValue = (String) MainFrame.tableModel.getValueAt(i, 0);
                newValue = Integer.parseInt(strValue);

                if (newValue == bird.getID()) {

                    System.out.println(i);
                    rowNumber = i;
                    break;

                }

            }

        }

        for (int i = 0; i < MainFrame.tableModel.getColumnCount(); i++) {

            if (attributeName.equals(MainFrame.tableModel.getColumnName(i))) {

                columnNumber = i;
                break;

            }

        }

        if ((rowNumber != -1) && (columnNumber != -1)) {

            MainFrame.tableModel.setValueAt(String.valueOf(value), rowNumber, columnNumber);

        } else {

            System.out.println(rowNumber + " " + columnNumber);

            return false;

        }

        switch (attribute) {
            case "ID":
                bird.ID = (int) value;
                updatedBird = true;
                break;
            case "Species":
                bird.species = (String) value;
                updatedBird = true;
                break;
            case "Color":
                bird.color = (String) value;
                updatedBird = true;
                break;
            case "Size (inches)":
                bird.size = (Float) value;
                updatedBird = true;
                break;
            case "Beak Shape":
                bird.beakShape = (String) value;
                updatedBird = true;
                break;
            case "Gender":
                bird.gender = (char) value;
                updatedBird = true;
                break;
            case "Wingspan (inches)":
                bird.wingspan = (float) value;
                updatedBird = true;
                break;
            case "Activity Pattern":
                bird.activityPattern = (String) value;
                updatedBird = true;
                break;
            default:
        }

        return updatedBird;

    }

    /**
     * method: correctAttribute
     * parameter: String attribute, String type, Scanner scanner
     * return: Boolean
     * purpose: This is for making sure if the attribute is in the correct type. If it is, it will return true and if not, it will return false.
     */

    static boolean correctAttribute(String attribute, String type) {

        try {

            switch (type) {
                case "Integer" -> {

                    int newAttribute = Integer.parseInt(attribute);
                    return true;

                }
                case "Float" -> {

                    float newAttribute = Float.parseFloat(attribute);
                    return true;

                }
                case "Gender" -> {

                    char newAttribute = Character.toLowerCase(attribute.charAt(0));

                    return newAttribute == 'm' || newAttribute == 'f';

                }
            }

        } catch (Exception e) {

            return false;

        }

        return false;

    }

    /**
     * method: attributeChoice
     * parameter: String type
     * return: <T> T
     * purpose: This is for making sure that the attribute is in the correct type and will ask for a new input if it is not.
     */

    static <T> T attributeChoice(String type, Scanner scanner) {

        String attributeEntry = scanner.nextLine();

        while (!correctAttribute(attributeEntry, type)) {

            System.out.println("Invalid input. Please try again. Type \"Exit\" to go to the main menu.");
            attributeEntry = scanner.nextLine();

            if (attributeEntry.equalsIgnoreCase("Exit")) {

                return null;

            }

        }

        return (T) attributeEntry;

    }

    /**
     * method: highestAttribute
     * parameter: String attribute
     * return: Map<String, Integer>
     * purpose: This is for sorting attribute values based on how much birds contain that value. It will then return a map of the sorted values.
     */

    static Map<String, Integer> highestAttribute(String attribute) {

        HashMap<String, Integer> attributeCountMap = new HashMap<>();

        try {
            for (Bird birdItem : birdList) {

                boolean newAttributeValue = false;

                for (String i : attributeCountMap.keySet()) {

                    if (i.equals(String.valueOf(Bird.class.getDeclaredField(attribute).get(birdItem)))) {

                        attributeCountMap.put(i, attributeCountMap.get(i) + 1);
                        newAttributeValue = true;

                    }

                }

                if (!newAttributeValue) {

                    attributeCountMap.put(String.valueOf(Bird.class.getDeclaredField(attribute).get(birdItem)), 1);

                }

            }

            Map<String, Integer> sortedMap = attributeCountMap.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

            return sortedMap;

        } catch (Exception e) {

            System.out.println("Something went wrong, please try again.");

        }

        return null;

    }

}