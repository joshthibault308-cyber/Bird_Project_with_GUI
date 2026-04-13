import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.File;
import java.sql.*;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Joshua Thibault
 * CEN 3024 - Software Development I
 * April 8th, 2026
 * BirdRepositoryTest.java
 * This class will test the different functions the system will have.
 */

class BirdRepositoryTest {

    Bird bird;
    Scanner scanner;

    /**
     * This will set up adding a new bird and connect it wth the database.
     */

    @BeforeEach
    void setUp() {

        bird = new Bird(40, "Sparrow", "Brown", 4.13F, "Conical", 'm', 8.66F, "Diurnal");
        BirdRepository.usersUsername = "root";
        BirdRepository.usersPassword = new char[]{'Y', 'e', 'l', 'l', 'o', 'w', '2'};
        BirdRepository.databaseName = "birddatabase";
        BirdRepository.tableName = "birds";

    }

    /**
     * This will remove all the birds from the database once each test is done.
     */

    @AfterEach
    void tearDown() {

        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + BirdRepository.databaseName, BirdRepository.usersUsername, new String(BirdRepository.usersPassword));
            PreparedStatement statement = connection.prepareStatement("DELETE FROM  " + BirdRepository.tableName + " WHERE ID = ?");

            statement.setInt(1, bird.ID);
            statement.executeUpdate();


        } catch (SQLException e) {

        }


    }

    /**
     * This will test adding a bird to the database.
     */

    @Test
    @DisplayName("Add bird test")
    void addBird() {

        assertTrue(BirdRepository.addBird(bird.ID, bird.species, bird.color, bird.size, bird.beakShape, bird.gender, bird.wingspan, bird.activityPattern), "Bird was not added.");

    }

    /**
     * This will test adding a bird to the database with an ID already used.
     */

    @Test
    @DisplayName("Already used ID bird test")
    void addBirdWithUsedID() {

        BirdRepository.addBird(bird.ID, bird.species, bird.color, bird.size, bird.beakShape, bird.gender, bird.wingspan, bird.activityPattern);
        assertFalse(BirdRepository.addBird(bird.ID, bird.species, bird.color, bird.size, bird.beakShape, bird.gender, bird.wingspan, bird.activityPattern), "Bird was not added.");

    }

    /**
     * This will try to open a file that was can be read.
     */

    @Test
    @DisplayName("Open file test")
    void openFile() {

        File fileSelected = null;


        while (!BirdRepository.realFile(fileSelected)) {

            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {

                fileSelected = fileChooser.getSelectedFile();
                System.out.println(fileSelected.getName());

            }

        }

        assertTrue(BirdRepository.realFile(fileSelected));

    }

    /**
     * This will try to remove a bird from the database.
     */

    @Test
    @DisplayName("Remove bird test")
    void removeBird() {

        boolean birdinArrayList = false;

        BirdRepository.addBird(bird.ID, bird.species, bird.color, bird.size, bird.beakShape, bird.gender, bird.wingspan, bird.activityPattern);

        birdinArrayList = (BirdRepository.getBird(bird.getID()));

        assertTrue(birdinArrayList, "Bird is not in list.");

        BirdRepository.removeBird(bird.ID);

        assertFalse(BirdRepository.getBird(bird.getID()));

    }

    /**
     * This will test to see if a birds attribute is updated.
     */

    @Test
    @DisplayName("Update bird attribute test")
    void updateBird() {

        boolean birdinArrayList = false;
        String attribute = "Size";
        Float value = 5.0F;
        Float resultValue = null;

        BirdRepository.addBird(bird.ID, bird.species, bird.color, bird.size, bird.beakShape, bird.gender, bird.wingspan, bird.activityPattern);

        birdinArrayList = (BirdRepository.getBird(bird.getID()));

        assertTrue(birdinArrayList, "Bird is not in list.");

        BirdRepository.updateBird(attribute, value, bird.getID());

        Connection connection = null;
        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + BirdRepository.databaseName, BirdRepository.usersUsername, new String(BirdRepository.usersPassword));
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + BirdRepository.tableName + " WHERE ID = " + bird.getID());

            MainFrame.tableModel.setRowCount(0);

            if (resultSet.next()) {

                resultValue = Float.valueOf(resultSet.getString(attribute));

            }

        } catch (Exception _) {

        }

        assertNotNull(BirdRepository.getBird(bird.getID()));
        assertEquals(value, resultValue);

    }

    /**
     * This will test to see which value of a certain attribute is the most common.
     */

    @Test
    @DisplayName("Highest attribute test")
    void highestAttribute() {

        String attributePicked = "gender";

        BirdRepository.addBird(1, "Sparrow", "Brown", 4.13F, "Conical", 'm', 8.66F, "Diurnal");
        BirdRepository.addBird(2, "Eagle", "Brown/White", 27.65F, "Hooked", 'f', 70.87F, "Diurnal");
        BirdRepository.addBird(3, "Robin", "Red/Gray", 8.19F, "Pointed", 'f', 9.84F, "Diurnal");
        BirdRepository.addBird(4, "Hummingbird", "Green", 3.23F, "Straight", 'm', 3.15F, "Diurnal");
        BirdRepository.addBird(5, "Owl", "Gray/Brown", 15.94F, "Hooked", 'f', 51.18F, "Nocturnal");

        Map<String, Integer> birdMap = BirdRepository.highestAttribute(attributePicked);
        char firstElement = 1;

        assertNotNull(birdMap);


        for (Map.Entry<String, Integer> attributeValue : birdMap.entrySet()) {

            firstElement = attributeValue.getKey().charAt(0);
            break;

        }

        assertEquals('f', firstElement);

    }

}