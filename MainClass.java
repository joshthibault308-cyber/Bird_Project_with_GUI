/**
 * Joshua Thibault
 * CEN 3024 - Software Development I
 * April 8th, 2026
 * MainClass.java
 * This class will add a bird to the system from a file, add a bird manually, remove a bird from the system,
 * update a bird in the system, show the highest values of each attribute, and show the list of each bird in the system.
 * The Bird class will be used to add new birds and store them into an ArrayList.
 */

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;
import java.util.Map;
import static java.sql.DriverManager.getConnection;

public class MainClass {

    /**
     * method: main
     * parameter: none
     * return: void
     * purpose: This is the main method of the system that will run the program.
     * It will have the user select which function they want to use (add a bird with
     * a file, add a bird manually, remove a bird, update a bird, show the highest
     * values of each attribute, show the list of each bird in the system, or exit
     * the program).
     */

    public static void main(String[] args) {

        boolean exitProgram = false;
        Scanner scanner = new Scanner(System.in);

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception _) {
        }

        new MainFrame();

    }

}