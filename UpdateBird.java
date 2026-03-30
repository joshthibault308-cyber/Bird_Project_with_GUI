/**
 * Joshua Thibault
 * CEN 3024 - Software Development I
 * March 30th, 2026
 * UpdateBird.java
 * This class is where birds can be updated in the system using the birds ID. It will use its own form to do so.
 */


import javax.swing.*;
import java.awt.event.*;
import java.util.Objects;

public class UpdateBird extends JFrame {
    private JTextField IDTextField;
    private JPanel panel1;
    private JTextField colorTextField;
    private JTextField sizeTextField;
    private JTextField beakShapeTextField;
    private JTextField genderTextField;
    private JTextField wingspanTextField;
    private JTextField activityPatternTextField;
    private JTextField speciesTextField;
    private JLabel IDLabel;
    private JLabel speciesLabel;
    private JLabel colorLabel;
    private JLabel sizeLabel;
    private JLabel beakShapeLabel;
    private JLabel genderLabel;
    private JLabel wingspanLabel;
    private JLabel activityPatternLabel;
    private JButton updateButton;
    private JButton cancelButton;
    private JTextField birdToUpdateTextField;
    private JLabel birdToUpdateLabel;
    private JButton addButton;
    boolean exitedTab = false;

    public UpdateBird() {

        setContentPane(panel1);
        setTitle("Update Bird");
        setSize(450, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        /**
         * method: updateButton.addActionListener
         * parameter: ActionEvent e
         * return: N/A
         * purpose: This method is for when the updateButton is selected, it will make sure the ID is from a bird in the system. It will then update the bird using the new attributes added by the user.
         */

        updateButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                boolean correctAttributes = true;
                StringBuilder wrongAttributesInputtedString = new StringBuilder();

                try {

                    String birdEntry = birdToUpdateTextField.getText();
                    Bird birdToUpdate = null;

                    if (!BirdRepository.correctAttribute(birdEntry, "Integer")) {

                        correctAttributes = false;
                        wrongAttributesInputtedString.append("<br> Bird: Incorrect format");

                    } else if (BirdRepository.getBird(Integer.parseInt(birdEntry)) == null) {

                        correctAttributes = false;
                        wrongAttributesInputtedString.append("<br> Bird ID: Bird not found");

                    } else {

                        birdToUpdate = BirdRepository.getBird(Integer.parseInt(birdEntry));

                    }

                    String IDEntry = IDTextField.getText();
                    int ID = 0;

                    if (!BirdRepository.correctAttribute(IDEntry, "Integer")) {

                        correctAttributes = false;
                        wrongAttributesInputtedString.append("<br> ID: Incorrect format");

                    } else if ((BirdRepository.getBird(Integer.parseInt(IDEntry)) != null) && (Integer.parseInt(IDEntry) != Objects.requireNonNull(birdToUpdate).getID())) {

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

                        BirdRepository.updateBird("Species", speciesEntry, birdToUpdate);
                        BirdRepository.updateBird("Color", colorEntry, birdToUpdate);
                        BirdRepository.updateBird("Size (inches)", size, birdToUpdate);
                        BirdRepository.updateBird("Beak Shape", beakShapeEntry, birdToUpdate);
                        BirdRepository.updateBird("Gender", gender, birdToUpdate);
                        BirdRepository.updateBird("Wingspan (inches)", wingspan, birdToUpdate);
                        BirdRepository.updateBird("Activity Pattern", activityPatternEntry, birdToUpdate);
                        BirdRepository.updateBird("ID", ID, birdToUpdate);

                        dispose();

                    } else {

                        int userInput = JOptionPane.showConfirmDialog(null, "<html>" + wrongAttributesInputtedString.toString() + "</html>", "Update Bird", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                        if (userInput == JOptionPane.DEFAULT_OPTION) {

                        }

                    }


                } catch (Exception exception) {

                    exception.printStackTrace();

                    int userInput = JOptionPane.showConfirmDialog(null, "Invalid input. Please try again.", "Update Bird", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (userInput == JOptionPane.DEFAULT_OPTION) {

                    }

                }
            }
        });

        /**
         * method: addButton.addActionListener
         * parameter: FocusEvent e
         * return: N/A
         * purpose: This method is for when addButton is selected, it will show the attributes of the bird based on the ID specified.  It will give a warning if the bird could not be found.
         */

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    boolean realBird = true;
                    StringBuilder wrongAttributesInputtedString = new StringBuilder();
                    String birdEntry = birdToUpdateTextField.getText();
                    Bird birdToUpdate = null;

                    if (!BirdRepository.correctAttribute(birdEntry, "Integer")) {

                        realBird = false;
                        wrongAttributesInputtedString.append("<br> Bird: Incorrect format");

                    } else if (BirdRepository.getBird(Integer.parseInt(birdEntry)) == null) {

                        realBird = false;
                        wrongAttributesInputtedString.append("<br> Bird ID: Bird not found");

                    } else {

                        birdToUpdate = BirdRepository.getBird(Integer.parseInt(birdEntry));

                    }

                    if (realBird) {

                        IDTextField.setText(String.valueOf(birdToUpdate.ID));
                        speciesTextField.setText(String.valueOf(birdToUpdate.species));
                        colorTextField.setText(String.valueOf(birdToUpdate.color));
                        sizeTextField.setText(String.valueOf(birdToUpdate.size));
                        beakShapeTextField.setText(String.valueOf(birdToUpdate.beakShape));
                        genderTextField.setText(String.valueOf(birdToUpdate.gender));
                        wingspanTextField.setText(String.valueOf(birdToUpdate.wingspan));
                        activityPatternTextField.setText(String.valueOf(birdToUpdate.activityPattern));

                    } else {

                        System.out.print(cancelButton.hasFocus());

                        if ((!exitedTab) && (!cancelButton.hasFocus())) {

                            int userInput = JOptionPane.showConfirmDialog(null, "<html>" + wrongAttributesInputtedString.toString() + "</html>", "Update Bird", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                            if (userInput == JOptionPane.DEFAULT_OPTION) {

                                dispose();

                            }

                        }

                    }

                } catch (Exception exception) {

                    if (!exitedTab) {

                        int userInput = JOptionPane.showConfirmDialog(null, "Invalid input. Please try again.", "Update Bird", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                        if (userInput == JOptionPane.DEFAULT_OPTION) {

                            dispose();

                        }

                    }

                }
            }
        });

        /**
         * method: cancelButton.addActionListener
         * parameter: ActionEvent e
         * return: N/A
         * purpose: This method is for when the cancelButton is selected, it will remove the RemoveBird form.
         */

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                exitedTab = true;
                dispose();

            }
        });

        /**
         * method: birdToUpdateTextField.addKeyListener
         * parameter: KeyEvent e
         * return: N/A
         * purpose: This method is for when the enter key is typed in the IDTextField. it will show the attributes of the bird based on the ID specified.  It will give a warning if the bird could not be found.
         */

        birdToUpdateTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    try {

                        boolean realBird = true;
                        StringBuilder wrongAttributesInputtedString = new StringBuilder();
                        String birdEntry = birdToUpdateTextField.getText();
                        Bird birdToUpdate = null;

                        if (!BirdRepository.correctAttribute(birdEntry, "Integer")) {

                            realBird = false;
                            wrongAttributesInputtedString.append("<br> Bird: Incorrect format");

                        } else if (BirdRepository.getBird(Integer.parseInt(birdEntry)) == null) {

                            realBird = false;
                            wrongAttributesInputtedString.append("<br> Bird ID: Bird not found");

                        } else {

                            birdToUpdate = BirdRepository.getBird(Integer.parseInt(birdEntry));

                        }

                        if (realBird) {

                            IDTextField.setText(String.valueOf(birdToUpdate.ID));
                            speciesTextField.setText(String.valueOf(birdToUpdate.species));
                            colorTextField.setText(String.valueOf(birdToUpdate.color));
                            sizeTextField.setText(String.valueOf(birdToUpdate.size));
                            beakShapeTextField.setText(String.valueOf(birdToUpdate.beakShape));
                            genderTextField.setText(String.valueOf(birdToUpdate.gender));
                            wingspanTextField.setText(String.valueOf(birdToUpdate.wingspan));
                            activityPatternTextField.setText(String.valueOf(birdToUpdate.activityPattern));

                        } else {

                            System.out.print(cancelButton.hasFocus());

                            if ((!exitedTab) && (!cancelButton.hasFocus())) {

                                int userInput = JOptionPane.showConfirmDialog(null, "<html>" + wrongAttributesInputtedString.toString() + "</html>", "Update Bird", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                                if (userInput == JOptionPane.DEFAULT_OPTION) {

                                    dispose();

                                }

                            }

                        }

                    } catch (Exception exception) {

                        if (!exitedTab) {

                            int userInput = JOptionPane.showConfirmDialog(null, "Invalid input. Please try again.", "Update Bird", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                            if (userInput == JOptionPane.DEFAULT_OPTION) {

                                dispose();

                            }

                        }

                    }

                }

            }

        });

    }

}