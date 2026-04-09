/**
 * Joshua Thibault
 * CEN 3024 - Software Development I
 * April 8th, 2026
 * BirdRepository.java
 * This class will have the functions needed to add birds, find birds, update birds, seeing if attributes are correct, and sorting attribute values.
 */

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class BirdRepository {

    static String usersUsername = null;
    static char[] usersPassword = null;
    static String databaseName = null;
    static String tableName = null;

    /**
     * method: realFile
     * parameter: File
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

        if (BirdRepository.getBird(ID)) {

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
     * return: boolean
     * purpose: This is for adding a bird to the arrayList. It will make sure that the ID is not previously used for another bird.
     */

    static boolean addBird(int ID, String species, String color, float size, String beakShape, char gender, float wingspan, String activityPattern) {

        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, usersUsername, new String(usersPassword));
            PreparedStatement statement = connection.prepareStatement("INSERT INTO " + tableName + "(ID, Species, Color, Size, Beak_Shape, Gender, Wingspan, Activity_Pattern) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            statement.setString(1, String.valueOf(ID));
            statement.setString(2, species);
            statement.setString(3, color);
            statement.setString(4, String.valueOf(size));
            statement.setString(5, beakShape);
            statement.setString(6, String.valueOf(gender));
            statement.setString(7, String.valueOf(wingspan));
            statement.setString(8, activityPattern);

            statement.executeUpdate();

                return getBird(ID);



        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return false;

        }

    }

    /**
     * method: getBird
     * parameter: int ID
     * return: boolean
     * purpose: This is for getting a bird based on its ID. It will return null if it can not be found.
     */

    static boolean getBird(int ID) {

        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, usersUsername, new String(usersPassword));
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM " + tableName + " WHERE ID = " + ID);

            if (resultSet.next()) {

                return true;

            }

        } catch (SQLException e) {

            System.out.println("Something happened");

            return false;

        }

        return false;

    }

    /**
     * method: removeBird
     * parameter: int ID
     * return: boolean
     * purpose: This is for removing a bird from the arrayList.
     */

    static boolean removeBird(int ID) {

        try {

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, usersUsername, new String(usersPassword));
        PreparedStatement statement = connection.prepareStatement("DELETE FROM  " + tableName + " WHERE ID = ?");

        statement.setInt(1, ID);
        statement.executeUpdate();


    } catch (SQLException e) {

        return false;

    }

        for (int i = 0; i < MainFrame.tableModel.getRowCount(); i++) {

            if (Integer.parseInt((String) MainFrame.tableModel.getValueAt(i, 0)) == ID) {


                MainFrame.tableModel.removeRow(i);
                break;

            }

        }

        return !getBird(ID);

    }

    /**
     * method: updateBird
     * parameter: String attribute, T value, Bird bird
     * return: Boolean
     * purpose: This is for updating a birds attribute. It will see which attribute will be updated for the bird and change the value for the bird.
     */

    static <T> boolean updateBird(String attribute, T value, int ID) {

        boolean updatedBird = false;
        String attributeName = attribute;



        /* switch (attribute) {
            case "Beak Shape":
                attributeName = "Beak_Shape";
                break;
            case "Activity Pattern":
                attributeName = "Activity_Pattern";
                break;
            default:
        } */
        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, usersUsername, new String(usersPassword));
            PreparedStatement statement = connection.prepareStatement("UPDATE Birds SET " + attributeName + " = ? WHERE ID =  ?");

            statement.setString(1, String.valueOf(value));
            statement.setString(2, String.valueOf(ID));


            statement.executeUpdate();

        } catch (Exception e) {

            return false;

        }

        return updatedBird;

    }

    /**
     * method: correctAttribute
     * parameter: String attribute, String type
     * return: boolean
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
     * parameter: String type, Scanner scanner
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

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, usersUsername, new String(usersPassword))) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT " + attribute + ", COUNT(*) AS Occurrences FROM Birds GROUP BY " + attribute + ";");

            while (resultSet.next()) {

                attributeCountMap.put(resultSet.getString(1), Integer.valueOf(resultSet.getString(2)));

            }

            Map<String, Integer> sortedMap = attributeCountMap.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

            return sortedMap;

        } catch (Exception e) {

            System.out.println("Something went wrong, please try again.");

        }

        return null;

    }

    /**
     * method: getConnection
     * parameter: String username, char[] password, String database, String table
     * return: Boolean
     * purpose: This is for checking if a connection is valid and the credentials are correct.
     */

    static Boolean getConnection(String username, char[] password, String database, String table) {


        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, username, new String(password))) {

            return true;

        } catch(Exception e) {

            return false;

        }

    }

    /**
     * method: setLoginInformation
     * parameter: String username, char[] password, String database, String table
     * return: Boolean
     * purpose: This is for updating the database information to be used in the system.
     */

    static Boolean setLoginInformation(String username, char[] password, String database, String table) {

        try {

            usersUsername = username;
            usersPassword = password;
            databaseName = database;
            tableName = table;
            return true;

        } catch(Exception e) {

            return false;

        }

    }

    /**
     * method: setTableRows
     * parameter: N/A
     * return: Boolean
     * purpose: This adds new rows to the table based on what the rows are in the table in the database.
     */

    static Boolean setTableRows() {

        Connection connection = null;
        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, usersUsername, new String(usersPassword));
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);

            MainFrame.tableModel.setRowCount(0);

            while (resultSet.next()) {

                MainFrame.tableModel.addRow(new String[]{resultSet.getString("ID"), resultSet.getString("Species"), resultSet.getString("Color"), resultSet.getString("Size"), resultSet.getString("Beak_Shape"), resultSet.getString("Gender"), resultSet.getString("Wingspan"), resultSet.getString("Activity_Pattern")});

            }

        } catch (SQLException e) {

            return false;

        }

        return true;

    }

}