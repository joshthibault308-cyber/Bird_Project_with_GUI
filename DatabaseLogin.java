/**
 * Joshua Thibault
 * CEN 3024 - Software Development I
 * April 8th, 2026
 * DatabaseLogin.java
 * This class is for having the user put in the database credentials.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DatabaseLogin extends JFrame {
    private JPanel panel1;
    private JTextField usernameTextField;
    private JTextField databaseTextField;
    private JTextField tableTextField;
    private JPasswordField passwordField;
    private JButton connectButton;
    private JLabel birdImage;

    public DatabaseLogin() {

        setContentPane(panel1);
        setTitle("Database Login");
        setSize(350, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        birdImage.setIcon(new ImageIcon(new ImageIcon("src/Bird Image 2.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        /**
         * method: connectionButton.addActionListener
         * parameter: N/A
         * return: N/A
         * purpose: This method will get the user's responses and give the system the information to access the database.
         */

        connectButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                char[] password = passwordField.getPassword();
                String databaseName = databaseTextField.getText();
                String tableName = tableTextField.getText();

                if (BirdRepository.getConnection(username, password, databaseName, tableName)) {

                    BirdRepository.setLoginInformation(username, password, databaseName, tableName);
                    BirdRepository.setTableRows();

                    dispose();

                } else {

                    int userInput = JOptionPane.showConfirmDialog(null, "<html>" + "Invalid input. Please try again." + "</html>", "Database Login", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (userInput == JOptionPane.DEFAULT_OPTION) {

                        dispose();

                    }

                }

            }

        });

    }
}
