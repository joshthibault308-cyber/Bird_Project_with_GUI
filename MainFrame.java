import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

/**
 * Joshua Thibault
 * CEN 3024 - Software Development I
 * April 8th, 2026
 * MainFrame.java
 * This class is the main menu for the system. It will show a table of all the birds in the system, as well as all the buttons which go to each function of the system. This will interact with the AddBirdManually, DatabaseLogin, HighestAttribute, RemoveBird, UpdateBird forms to open the forms up.
 */

public class MainFrame extends JFrame {
    private JTable ViewBirdsTable;
    private JPanel mainPanel;
    private JButton highestAttributeButton;
    private JButton fileButton;
    private JTable mainTable;
    private JButton addBirdsManuallyButton;
    private JButton updateButton;
    private JButton removeButton;
    private JPanel buttonPanel;
    private JScrollPane tableScrollPane;
    private JLabel birdImage;
    private JButton databaseButton;
    public String databaseName;
    public String tableName;
    public String username;
    public char[] password;

    public static DefaultTableModel tableModel = new DefaultTableModel(new String[][]{}, new String[]{"ID", "Species", "Color", "Size (inches)", "Beak Shape", "Gender", "Wingspan (inches)", "Activity Pattern"}) {

        @Override
        public boolean isCellEditable(int row, int column) {

            return false;

        }

    };

    /**
     * This constructor will have the main frame of the Bird Project, which will have a table and buttons to go to the other forms. It will also set the main components like the size, button images name, location, and visibility of the form.
     */

    public MainFrame() {

        mainTable.setModel(tableModel);
        mainTable.setShowGrid(true);

        mainTable.getTableHeader().setBackground(new Color(0, 0, 0));

        birdImage.setIcon(new ImageIcon(new ImageIcon("src/Bird Image.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        fileButton.setIcon(new ImageIcon(new ImageIcon("src/Upload Icon.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        addBirdsManuallyButton.setIcon(new ImageIcon(new ImageIcon("src/Plus Icon.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        updateButton.setIcon(new ImageIcon(new ImageIcon("src/Pencil Icon.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        removeButton.setIcon(new ImageIcon(new ImageIcon("src/Trash can Icon.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        databaseButton.setIcon(new ImageIcon(new ImageIcon("src/Database Icon.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));


        setContentPane(mainPanel);
        setTitle("Bird Directory");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        buttonPanel.setBackground(new Color(173, 216, 230));
        tableScrollPane.getViewport().setBackground(new Color(173, 216, 230));
        setVisible(true);

        setExtendedState(Frame.MAXIMIZED_BOTH);

        highestAttributeButton.addActionListener(new ActionListener() {

            /**
             * This method is for when the highestAttributeButton is selected, it will add a new HighestAttribute form.
             * @param e The event to be processed.
             */

            @Override
            public void actionPerformed(ActionEvent e) {

                new HighestAttribute();

            }

        });

        fileButton.addActionListener(new ActionListener() {

            /**
             * This method is for when the fileButton is selected, it will open a file selector for the user to open a file containing birds. This will then add all the birds in the file to the system.
             * @param e The event to be processed.
             */

            @Override
            public void actionPerformed(ActionEvent e) {

                File fileSelected = null;
                Boolean backToMenu = false;


                while (!BirdRepository.realFile(fileSelected)) {

                    JFileChooser fileChooser = new JFileChooser();
                    int returnValue = fileChooser.showOpenDialog(null);

                    if (returnValue == JFileChooser.APPROVE_OPTION) {

                        fileSelected = fileChooser.getSelectedFile();

                        if (!BirdRepository.realFile(fileSelected)) {

                            int userInput = JOptionPane.showConfirmDialog(null, "<html>" + "An error occurred when reading the file, please try again." + "</html>", "Add Bird", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                            if (userInput == JOptionPane.DEFAULT_OPTION) {

                                dispose();

                            }

                        }

                    } else if (returnValue == JFileChooser.CANCEL_OPTION) {

                        backToMenu = true;
                        break;

                    }

                }

                if (backToMenu) {

                    return;

                }

                try (FileInputStream fileStream = new FileInputStream(fileSelected)) {
                    Scanner scannerFile = new Scanner(fileStream);

                    while (scannerFile.hasNextLine()) {

                        String lineScanned = scannerFile.nextLine();
                        System.out.println(lineScanned);

                        BirdRepository.addBirdwithFileFormat(lineScanned);

                    }

                } catch (Exception exception) {

                    int userInput = JOptionPane.showConfirmDialog(null, "<html>" + "An error occurred when reading the file, please try again." + "</html>", "Add Bird", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (userInput == JOptionPane.DEFAULT_OPTION) {

                        dispose();

                    }

                }

            }
        });

        addBirdsManuallyButton.addActionListener(new ActionListener() {

            /**
             * This method is for when the addBirdsManuallyButton is selected, it will add a new AddBirdManually form.
             * @param e The event to be processed.
             */

            @Override
            public void actionPerformed(ActionEvent e) {

                new AddBirdManually();

            }

        });

        updateButton.addActionListener(new ActionListener() {

            /**
             * This method is for when the updateButton button is selected, it will add a new UpdateBird form.
             * @param e The event to be processed.
             */

            @Override
            public void actionPerformed(ActionEvent e) {

                new UpdateBird();

            }

        });

        removeButton.addActionListener(new ActionListener() {

            /**
             * This method is for when the removeButton is selected, it will add a new RemoveBird form.
             * @param e The event to be processed.
             */

            @Override
            public void actionPerformed(ActionEvent e) {

                new RemoveBird();

            }

        });

        databaseButton.addActionListener(new ActionListener() {

            /**
             * This method is for when the databaseButton is selected, it will add a new DatabaseLogin form.
             * @param e The event to be processed.
             */

            @Override
            public void actionPerformed(ActionEvent e) {

                new DatabaseLogin();

            }

        });

    }

}