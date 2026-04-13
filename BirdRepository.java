import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Joshua Thibault
 * CEN 3024 - Software Development I
 * April 8th, 2026
 * BirdRepository.java
 * This class will have the functions needed to add birds, find birds, update birds, seeing if attributes are correct, and sorting attribute values. This will interact with the AddBirdManually, DatabaseLogin, HighestAttribute, RemoveBird, UpdateBird, and MainFrame to do some of the important functions they need to do.
 */

public class BirdRepository {

    static String usersUsername = null;
    static char[] usersPassword = null;
    static String databaseName = null;
    static String tableName = null;

    /**
     * This method is to check if the file can be read and if a real file. It will return true if it can be read.
     * @param file The file that is validated.
     * @return Boolean: True if valid and false if not.
     */

    static Boolean realFile(File file) {

        try (FileInputStream fileStream = new FileInputStream(file)) {

            return true;

        } catch (Exception e) {

            return false;

        }

    }

    /**
     * This is for adding birds for each line of a file and making sure their attributes are correct.
     * @param fileLine Line of file where a bird is being added.
     * @return Bird: Bird object containing attributes of bird added. Null if attributes are incorrect.
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
     * This is for adding a bird to the arrayList. It will make sure that the ID is not previously used for another bird.
     * @param ID ID of bird added.
     * @param species Species of bird added.
     * @param color Color of bird added.
     * @param size Size of bird added.
     * @param beakShape Beak shape of bird added.
     * @param gender Gender of bird added.
     * @param wingspan Wingspan of bird added.
     * @param activityPattern Activity pattern of bird added.
     * @return boolean: True if bird is added and false if not.
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
     * This is for seeing if a bird is there. It will return true if it is found and false if not.
     * @param ID ID of bird.
     * @return boolean: True if bird is there and false if not.
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
     * This is for removing a bird from the arrayList.
     * @param ID ID of bird to be removed.
     * @return boolean True if bird is removed and false if not or if an error occurred.
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
     * This is for updating a birds attribute. It will see which attribute will be updated for the bird and change the value for the bird.
     * @param attribute Attribute of bird used to update.
     * @param value New value of the attribute to update.
     * @param ID ID of the bird.
     * @return Boolean
     */

    static <T> boolean updateBird(String attribute, T value, int ID) {

        boolean updatedBird = false;
        String attributeName = attribute;

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
     * This is for making sure if the attribute is in the correct type. If it is, it will return true and if not, it will return false.
     * @param value The value of the attribute.
     * @param type The data type of the attribute.
     * @return boolean: True if the attribute is correct and false if not.
     */

    static boolean correctAttribute(String value, String type) {

        try {

            switch (type) {
                case "Integer" -> {

                    int newAttribute = Integer.parseInt(value);
                    return true;

                }
                case "Float" -> {

                    float newAttribute = Float.parseFloat(value);
                    return true;

                }
                case "Gender" -> {

                    char newAttribute = Character.toLowerCase(value.charAt(0));

                    return newAttribute == 'm' || newAttribute == 'f';

                }
            }

        } catch (Exception e) {

            return false;

        }

        return false;

    }

    /**
     * This is for sorting attribute values based on how much birds contain that value.
     * @param attribute The attribute used for the function.
     * @return {@literal Map<String, Integer>}: The map organized of the sorted values.
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
     * This is for checking if a connection is valid and the credentials are correct.
     * @param username The username for the database.
     * @param password The password for the database.
     * @param database The database name.
     * @param table The table used from the database.
     * @return Boolean: True if the connection can be made and false if not.
     */

    static Boolean getConnection(String username, char[] password, String database, String table) {


        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, username, new String(password))) {

            return true;

        } catch(Exception e) {

            return false;

        }

    }

    /**
     * This is for updating the database information to be used in the system.
     * @param username The username for the database.
     * @param password The password for the database.
     * @param database The database name.
     * @param table The table used for the database.
     * @return Boolean: True if the connection works and false if not.
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
     * This adds new rows to the table based on what the rows are in the table in the database.
     * @return Boolean: True if successful and false if not.
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