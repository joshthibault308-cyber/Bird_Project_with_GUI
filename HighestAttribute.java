/**
 * Joshua Thibault
 * CEN 3024 - Software Development I
 * April 8th, 2026
 * HighestAttribute.java
 * This class is seeing which values for a specific attribute have the greatest quantity out of all of the birds using its own form.
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class HighestAttribute extends JFrame {
    private JPanel highestAttributePanel;
    private JComboBox comboBox;
    private JTable highestAttributeTable;
    String attributePicked;

    DefaultTableModel tableModel = new DefaultTableModel(new String[][]{}, new String[]{"Attribute", "Amount"});

    public HighestAttribute() {

        for (String s : Arrays.asList("Species", "Color", "Size", "Beak Shape", "Gender", "Wingspan", "Activity Pattern")) {
            comboBox.addItem(s);
        }

        highestAttributeTable.setModel(tableModel);

        setContentPane(highestAttributePanel);
        setTitle("Highest Attribute");
        setSize(450, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        /**
         * method: comboBox.addActionListener
         * parameter: N/A
         * return: N/A
         * purpose: This method is for when a new attribute from the comboBox is selected, it will make a table for it describing the count of every value from the specific attribute.
         */

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                attributePicked = Objects.requireNonNull(comboBox.getSelectedItem()).toString();
                System.out.println(attributePicked);

                switch (attributePicked) {

                    case "Beak Shape":
                        attributePicked = "Beak_Shape";
                        break;
                    case "Activity Pattern":
                        attributePicked = "Activity_Pattern";
                        break;
                }

                if (attributePicked != null) {

                    Map<String, Integer> attributeList = BirdRepository.highestAttribute(attributePicked);

                    if (attributeList != null) {

                        tableModel.setRowCount(0);

                        for (Map.Entry<String, Integer> attributeValue : attributeList.entrySet()) {

                            tableModel.addRow(new String[]{attributeValue.getKey(), String.valueOf(attributeValue.getValue())});

                        }

                    }

                }

            }
        });
    }
}
