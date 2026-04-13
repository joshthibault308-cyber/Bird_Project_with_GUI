import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * Joshua Thibault
 * CEN 3024 - Software Development I
 * April 8th, 2026
 * AddBirdManually.java
 * This class is for the adding a bird manually to the system using its own form. It will use the BirdRepository to add birds to the database.
 */

public class AddBirdManually extends JFrame {
    private JTextField IDTextField;
    private JTextField speciesTextField;
    private JTextField colorTextField;
    private JTextField sizeTextField;
    private JTextField beakShapeTextField;
    private JTextField genderTextField;
    private JTextField wingspanTextField;
    private JTextField activityPatternTextField;
    private JLabel IDTitle;
    private JLabel speciesTitle;
    private JLabel colorTitle;
    private JLabel sizeTitle;
    private JLabel beakShapeTitle;
    private JLabel genderTitle;
    private JLabel wingspanTitle;
    private JLabel activityPatternTitle;
    private JButton addButton;
    private JButton cancelButton;
    private JPanel panel1;

    /**
     * This constructor will let the user add a bird manually with the user typing out the attributes into the text fields and then submitting it. It will also set the main components like the size, name, location, and visibility of the form. The MainFrame will make a new AddBirdManually form when the Add Bird Manually button is clicked.
     */

    public AddBirdManually() {

        setContentPane(panel1);
        setTitle("Add Bird");
        setSize(450, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        addButton.addActionListener(new ActionListener() {

            /**
             * {@inheritDoc}
             * This method makes sure that the attributes have the correct types and to add the bird to the system.
             * @param e The event to be processed.
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                Boolean correctAttributes = true;
                StringBuilder wrongAttributesInputtedString = new StringBuilder();

                try {

                    String IDEntry = IDTextField.getText();
                    int ID = 0;

                    if (!BirdRepository.correctAttribute(IDEntry, "Integer")) {

                        correctAttributes = false;
                        wrongAttributesInputtedString.append("<br> ID: Incorrect format");

                    } else if (BirdRepository.getBird(Integer.parseInt(IDEntry))) {

                        correctAttributes = false;
                        wrongAttributesInputtedString.append("<br> ID: Already used ID");

                    } else {

                        ID = Integer.parseInt(IDEntry);

                    }

                    String speciesEntry = speciesTextField.getText();

                    if (Objects.equals(speciesEntry, "")) {

                        correctAttributes = false;
                        wrongAttributesInputtedString.append("<br> Species: Blank");

                    }

                    String colorEntry = colorTextField.getText();

                    if (Objects.equals(colorEntry, "")) {

                        correctAttributes = false;
                        wrongAttributesInputtedString.append("<br> Color: Blank");

                    }

                    String sizeEntry = sizeTextField.getText();
                    float size = 0;

                    if (!BirdRepository.correctAttribute(sizeEntry, "Float")) {

                        correctAttributes = false;
                        wrongAttributesInputtedString.append("<br> Size: Incorrect format");

                    } else {

                        size = Float.parseFloat(sizeEntry);

                    }

                    String beakShapeEntry = beakShapeTextField.getText();

                    if (Objects.equals(beakShapeEntry, "")) {

                        correctAttributes = false;
                        wrongAttributesInputtedString.append("<br> Beak Shape: Blank");

                    }

                    String genderEntry = genderTextField.getText();
                    char gender = 0;

                    if (!BirdRepository.correctAttribute(genderEntry, "Gender")) {

                        correctAttributes = false;
                        wrongAttributesInputtedString.append("<br> Gender: Incorrect format");

                    } else {

                        gender = Character.toLowerCase(genderEntry.charAt(0));

                    }

                    String wingspanEntry = wingspanTextField.getText();
                    float wingspan = 0;

                    if (!BirdRepository.correctAttribute(wingspanEntry, "Float")) {

                        correctAttributes = false;
                        wrongAttributesInputtedString.append("<br> Wingspan: Incorrect format");

                    } else {

                        wingspan = Float.parseFloat(wingspanEntry);

                    }

                    String activityPatternEntry = activityPatternTextField.getText();

                    if (Objects.equals(activityPatternEntry, "")) {

                        correctAttributes = false;
                        wrongAttributesInputtedString.append("<br> Activity Pattern: Blank");

                    }

                    if (correctAttributes) {

                        BirdRepository.addBird(ID, speciesEntry, colorEntry, size, beakShapeEntry, gender, wingspan, activityPatternEntry);
                        BirdRepository.setTableRows();
                        dispose();

                    } else {

                        int userInput = JOptionPane.showConfirmDialog(null, "<html>" + wrongAttributesInputtedString.toString() + "</html>", "Add Bird", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                        if (userInput == JOptionPane.DEFAULT_OPTION) {

                            dispose();

                        }

                    }


                } catch (Exception exception) {

                    int userInput = JOptionPane.showConfirmDialog(null, "<html>" + "Invalid input. Please try again." + "</html>", "Add Bird", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (userInput == JOptionPane.DEFAULT_OPTION) {

                        dispose();

                    }

                }

            }
        });
    }

}
