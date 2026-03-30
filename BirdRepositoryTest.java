/**
 * Joshua Thibault
 * CEN 3024 - Software Development I
 * March 30th, 2026
 * BirdRepositoryTest.java
 * This class will test the different functions the system will have.
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.File;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class BirdRepositoryTest {

    Bird bird;
    Scanner scanner;

    /**
     * method: setUp
     * parameter: N/A
     * return: void
     * purpose: This will set up adding a new bird.
     */

    @BeforeEach
    void setUp() {

        bird = new Bird(1, "Sparrow", "Brown", 4.13F, "Conical", 'm', 8.66F, "Diurnal");

    }

    /**
     * method: tearDown
     * parameter: N/A
     * return: void
     * purpose: This will remove all the birds from the arrayList once each test is done.
     */

    @AfterEach
    void tearDown() {

        BirdRepository.birdList.clear();

    }

    /**
     * method: addBird
     * parameter: N/A
     * return: void
     * purpose: This will test adding a bird to the arrayList.
     */

    @Test
    @DisplayName("Add bird test")
    void addBird() {

        assertTrue(BirdRepository.addBird(bird.ID, bird.species, bird.color, bird.size, bird.beakShape, bird.gender, bird.wingspan, bird.activityPattern), "Bird was not added.");

    }

    /**
     * method: addBirdWithUsedID
     * parameter: N/A
     * return: void
     * purpose: This will test adding a bird to the arrayList with an ID already used.
     */

    @Test
    @DisplayName("Already used ID bird test")
    void addBirdWithUsedID() {

        //BirdRepository.addBird(bird.ID, bird.species, bird.color, bird.size, bird.beakShape, bird.gender, bird.wingspan, bird.activityPattern);
        assertTrue(BirdRepository.addBird(bird.ID, bird.species, bird.color, bird.size, bird.beakShape, bird.gender, bird.wingspan, bird.activityPattern), "Bird was not added.");

    }

    /**
     * method: openFile
     * parameter: N/A
     * return: void
     * purpose: This will try to open a file that was can be read.
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
     * method: openFakeFile
     * parameter: N/A
     * return: void
     * purpose: This will try to open a file that can not be read.
     */

    /* @Test
    @DisplayName("Open fake file test")
    void openFakeFile() {

        String fileName = "Bird File.txt";

        assertFalse(BirdRepository.realFile(fileName));

    } */

    /**
     * method: removeBird
     * parameter: N/A
     * return: void
     * purpose: This will try to remove a bird from the arrayList.
     */

    @Test
    @DisplayName("Remove bird test")
    void removeBird() {

        boolean birdinArrayList = false;

        BirdRepository.addBird(bird.ID, bird.species, bird.color, bird.size, bird.beakShape, bird.gender, bird.wingspan, bird.activityPattern);

        birdinArrayList = (BirdRepository.getBird(bird.getID()) != null);

        assertTrue(birdinArrayList, "Bird is not in list.");

        BirdRepository.removeBird(bird);

        assertNotNull(BirdRepository.getBird(bird.getID()));

    }

    /**
     * method: updateBird
     * parameter: N/A
     * return: void
     * purpose: This will test to see if a birds attribute is updated.
     */

    @Test
    @DisplayName("Update bird attribute test")
    void updateBird() {

        boolean birdinArrayList = false;
        String attribute = "Size (inches)";
        Float value = 5.0F;

        BirdRepository.addBird(bird.ID, bird.species, bird.color, bird.size, bird.beakShape, bird.gender, bird.wingspan, bird.activityPattern);

        birdinArrayList = (BirdRepository.getBird(bird.getID()) != null);

        assertTrue(birdinArrayList, "Bird is not in list.");

        BirdRepository.updateBird(attribute, value, BirdRepository.getBird(bird.getID()));

        assertNotNull(BirdRepository.getBird(bird.getID()));
        assertEquals(value, BirdRepository.getBird(bird.getID()).size);

    }

    /**
     * method: highestAttribute
     * parameter: N/A
     * return: void
     * purpose: This will test to see which value of a certain attribute is the most common.
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