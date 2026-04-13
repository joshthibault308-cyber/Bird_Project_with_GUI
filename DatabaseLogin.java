import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Joshua Thibault
 * CEN 3024 - Software Development I
 * April 8th, 2026
 * DatabaseLogin.java
 * This class is for having the user put in the database credentials. It will depend on the MainFrame to go to this form. It will also depend on the BirdRepository to let the user use the database credentials for other database functions. The MainFrame will make a new DatabaseLogin form when the Database Login button is clicked.
 */

public class DatabaseLogin extends JFrame {
    private JPanel panel1;
    private JTextField usernameTextField;
    private JTextField databaseTextField;
    private JTextField tableTextField;
    private JPasswordField passwordField;
    private JButton connectButton;
    private JLabel birdImage;

    /**
     * This constructor is used for letting the user use the information of their input to access the user's database. It will also set the main components like the size, name, location, and visibility of the form.
     */

    public DatabaseLogin() {

        setContentPane(panel1);
        setTitle("Database Login");
        setSize(350, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        birdImage.setIcon(new ImageIcon(new ImageIcon("src/Bird Image 2.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        connectButton.addActionListener(new ActionListener() {

            /**
             * {@inheritDoc}
             * This method will get the user's responses and give the system the information to access the database.
             * @param e The event to be processed.
             */

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
