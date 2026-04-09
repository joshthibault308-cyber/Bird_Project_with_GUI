/**
 * Joshua Thibault
 * CEN 3024 - Software Development I
 * April 8th, 2026
 * RemoveBird.java
 * This class is where birds can be removed from the system using the birds ID. It will use its own form to do so.
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.util.Arrays;

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
                boolean birdToRemove = false;

                String IDEntry = IDTextField.getText();
                int ID = 0;

                if (!BirdRepository.correctAttribute(IDEntry, "Integer")) {

                    wrongAttributesInputtedString.append("<br> ID: Incorrect format");

                } else if (!BirdRepository.getBird(Integer.parseInt(IDEntry))) {

                    wrongAttributesInputtedString.append("<br> ID: Bird not found");

                } else {

                    ID = Integer.parseInt(IDEntry);

                    birdToRemove = BirdRepository.getBird(ID);

                }


                if (birdToRemove) {

                    int userInput = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove the bird?", "Remove Bird", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (userInput == JOptionPane.YES_OPTION) {

                        BirdRepository.removeBird(ID);
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

                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + BirdRepository.databaseName, BirdRepository.usersUsername, new String(BirdRepository.usersPassword));

                    boolean realBird = true;
                    StringBuilder wrongAttributesInputtedString = new StringBuilder();
                    String birdEntry = IDTextField.getText();
                    Bird birdToRemove = null;

                    if (!BirdRepository.correctAttribute(birdEntry, "Integer")) {

                        realBird = false;
                        wrongAttributesInputtedString.append("<br> Bird: Incorrect format");

                    } else if (!BirdRepository.getBird(Integer.parseInt(birdEntry))) {

                        realBird = false;
                        wrongAttributesInputtedString.append("<br> Bird ID: Bird not found");

                    }

                    if (realBird) {

                        Statement statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + BirdRepository.tableName + " WHERE ID = " + birdEntry);

                        if (resultSet.next()) {


                            tableModel.addRow(new String[]{"ID", resultSet.getString("ID")});
                            tableModel.addRow(new String[]{"Species", resultSet.getString("Species")});
                            tableModel.addRow(new String[]{"Color", resultSet.getString("Color")});
                            tableModel.addRow(new String[]{"Size", resultSet.getString("Size")});
                            tableModel.addRow(new String[]{"Beak Shape", resultSet.getString("Beak_Shape")});
                            tableModel.addRow(new String[]{"Gender", resultSet.getString("Gender")});
                            tableModel.addRow(new String[]{"Wingspan", resultSet.getString("Wingspan")});
                            tableModel.addRow(new String[]{"Activity Pattern", resultSet.getString("Activity_Pattern")});

                        }

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

                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + BirdRepository.databaseName, BirdRepository.usersUsername, new String(BirdRepository.usersPassword));

                        boolean realBird = true;
                        StringBuilder wrongAttributesInputtedString = new StringBuilder();
                        String birdEntry = IDTextField.getText();
                        Bird birdToRemove = null;

                        if (!BirdRepository.correctAttribute(birdEntry, "Integer")) {

                            realBird = false;
                            wrongAttributesInputtedString.append("<br> Bird: Incorrect format");

                        } else if (!BirdRepository.getBird(Integer.parseInt(birdEntry))) {

                            realBird = false;
                            wrongAttributesInputtedString.append("<br> Bird ID: Bird not found");

                        }

                        if (realBird) {

                            Statement statement = connection.createStatement();
                            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + BirdRepository.tableName + " WHERE ID = " + birdEntry);

                            if (resultSet.next()) {


                                tableModel.addRow(new String[]{"ID", resultSet.getString("ID")});
                                tableModel.addRow(new String[]{"Species", resultSet.getString("Species")});
                                tableModel.addRow(new String[]{"Color", resultSet.getString("Color")});
                                tableModel.addRow(new String[]{"Size", resultSet.getString("Size")});
                                tableModel.addRow(new String[]{"Beak Shape", resultSet.getString("Beak_Shape")});
                                tableModel.addRow(new String[]{"Gender", resultSet.getString("Gender")});
                                tableModel.addRow(new String[]{"Wingspan", resultSet.getString("Wingspan")});
                                tableModel.addRow(new String[]{"Activity Pattern", resultSet.getString("Activity_Pattern")});

                            }

                        } else {

                            System.out.println(exitedTab);

                            if (!exitedTab) {

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