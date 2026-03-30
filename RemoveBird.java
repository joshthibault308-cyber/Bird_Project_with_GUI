/**
 * Joshua Thibault
 * CEN 3024 - Software Development I
 * March 30th, 2026
 * RemoveBird.java
 * This class is where birds can be removed from the system using the birds ID. It will use its own form to do so.
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class RemoveBird extends JFrame {
    private JPanel panel1;
    private JTable removeBirdTable;
    private JButton deleteButton;
    private JButton cancelButton;
    private JTextField IDTextField;
    private JButton useButton;
    boolean exitedTab = false;

    DefaultTableModel tableModel = new DefaultTableModel(new String[][]{}, new String[]{"Attribute", "Value"});


    public RemoveBird() {

        removeBirdTable.setModel(tableModel);

        setContentPane(panel1);
        setTitle("Remove Bird");
        setSize(450, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        /**
         * method: deleteButton.addActionListener
         * parameter: ActionEvent e
         * return: N/A
         * purpose: This method is for when the deleteButton is selected, it will make sure the ID is from a bird in the system. It will then delete the bird from the system.
         */

        deleteButton.addActionListener(e -> {

            try {

                StringBuilder wrongAttributesInputtedString = new StringBuilder();
                Bird birdToRemove = null;

                String IDEntry = IDTextField.getText();
                int ID = 0;

                if (!BirdRepository.correctAttribute(IDEntry, "Integer")) {

                    wrongAttributesInputtedString.append("<br> ID: Incorrect format");

                } else if (BirdRepository.getBird(Integer.parseInt(IDEntry)) == null) {

                    wrongAttributesInputtedString.append("<br> ID: Bird not found");

                } else {

                    ID = Integer.parseInt(IDEntry);
                    birdToRemove = BirdRepository.getBird(ID);

                }


                if (birdToRemove != null) {

                    int userInput = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove the bird?", "Remove Bird", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (userInput == JOptionPane.YES_OPTION) {

                        BirdRepository.removeBird(birdToRemove);
                        dispose();

                    }

                } else {

                    int userInput = JOptionPane.showConfirmDialog(null, "<html>" + wrongAttributesInputtedString.toString() + "</html>", "Update Bird", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (userInput == JOptionPane.DEFAULT_OPTION) {

                        dispose();

                    }

                }

            } catch (Exception exception) {

                System.out.println("Invalid input. Please try again.");

            }

        });

        /**
         * method: IDTextField.addFocusListener
         * parameter: FocusEvent e
         * return: N/A
         * purpose: This method is for when the useButton is clicked. It will add a table showing the birds attributes, and it will give a warning if the bird could not be found.
         */

        useButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                tableModel.setRowCount(0);

                try {

                    boolean realBird = true;
                    StringBuilder wrongAttributesInputtedString = new StringBuilder();
                    String birdEntry = IDTextField.getText();
                    Bird birdToRemove = null;

                    if (!BirdRepository.correctAttribute(birdEntry, "Integer")) {

                        realBird = false;
                        wrongAttributesInputtedString.append("<br> Bird: Incorrect format");

                    } else if (BirdRepository.getBird(Integer.parseInt(birdEntry)) == null) {

                        realBird = false;
                        wrongAttributesInputtedString.append("<br> Bird ID: Bird not found");

                    } else {

                        birdToRemove = BirdRepository.getBird(Integer.parseInt(birdEntry));

                    }

                    if (realBird) {

                        tableModel.addRow(new String[]{"ID", String.valueOf(birdToRemove.ID)});
                        tableModel.addRow(new String[]{"Species", String.valueOf(birdToRemove.species)});
                        tableModel.addRow(new String[]{"Color", String.valueOf(birdToRemove.color)});
                        tableModel.addRow(new String[]{"Size", String.valueOf(birdToRemove.size)});
                        tableModel.addRow(new String[]{"Beak Shape", String.valueOf(birdToRemove.beakShape)});
                        tableModel.addRow(new String[]{"Gender", String.valueOf(birdToRemove.gender)});
                        tableModel.addRow(new String[]{"Wingspan", String.valueOf(birdToRemove.wingspan)});
                        tableModel.addRow(new String[]{"Activity Pattern", String.valueOf(birdToRemove.activityPattern)});

                    } else {

                        System.out.println(exitedTab);

                        if (!exitedTab) {

                            System.out.println("hi2");

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
         * method: IDTextField.addKeyListener
         * parameter: KeyEvent e
         * return: N/A
         * purpose: This method is for when the enter key is typed in the IDTextField. It will add a table showing the birds attributes, and it will give a warning if the bird could not be found.
         */

        IDTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {

                tableModel.setRowCount(0);

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    try {

                        boolean realBird = true;
                        StringBuilder wrongAttributesInputtedString = new StringBuilder();
                        String birdEntry = IDTextField.getText();
                        Bird birdToRemove = null;

                        if (!BirdRepository.correctAttribute(birdEntry, "Integer")) {

                            realBird = false;
                            wrongAttributesInputtedString.append("<br> Bird: Incorrect format");

                        } else if (BirdRepository.getBird(Integer.parseInt(birdEntry)) == null) {

                            realBird = false;
                            wrongAttributesInputtedString.append("<br> Bird ID: Bird not found");

                        } else {

                            birdToRemove = BirdRepository.getBird(Integer.parseInt(birdEntry));

                        }

                        if (realBird) {

                            tableModel.addRow(new String[]{"ID", String.valueOf(birdToRemove.ID)});
                            tableModel.addRow(new String[]{"Species", String.valueOf(birdToRemove.species)});
                            tableModel.addRow(new String[]{"Color", String.valueOf(birdToRemove.color)});
                            tableModel.addRow(new String[]{"Size", String.valueOf(birdToRemove.size)});
                            tableModel.addRow(new String[]{"Beak Shape", String.valueOf(birdToRemove.beakShape)});
                            tableModel.addRow(new String[]{"Gender", String.valueOf(birdToRemove.gender)});
                            tableModel.addRow(new String[]{"Wingspan", String.valueOf(birdToRemove.wingspan)});
                            tableModel.addRow(new String[]{"Activity Pattern", String.valueOf(birdToRemove.activityPattern)});

                        } else {

                            System.out.println(exitedTab);

                            if (!exitedTab) {

                                System.out.println("hi2");

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