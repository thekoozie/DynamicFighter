package core.handlers;

import core.Constants;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ScriptGui extends JFrame {
    public ScriptGui() {
        initComponents();
    }

    private void startButtonMouseClicked(MouseEvent e) {
        //setVariables
        Constants.NPC_TARGET = targetTextField.getText();
        Constants.FOOD = foodTextField.getText();
        Constants.LOOT = lootTextField2.getText();
        Constants.BANK_OPTION = bankCheckBox.isSelected();
        Constants.LOOT_OPTION = lootCheckBox.isSelected();
        Constants.EAT_OPTION = eatCheckBox.isSelected();
        Constants.EAT_PERCENT = eatPercent.getValue();
        Constants.FOOD_AMOUNT = Integer.parseInt(foodamountTextField.getText());

        Constants.INITIALIZE = true;
        this.setVisible(false);
    }

    private void initComponents() {
        setTitle("Dynamic Fighter");
        startButton = new JButton();
        label1 = new JLabel();
        targetTextField = new JTextField();
        label2 = new JLabel();
        foodTextField = new JTextField();
        label3 = new JLabel();
        foodamountTextField = new JTextField();
        bankCheckBox = new JCheckBox();
        lootCheckBox = new JCheckBox();
        eatCheckBox = new JCheckBox();
        eatPercent = new JSlider();
        label4 = new JLabel();
        label5 = new JLabel();
        lootTextField2 = new JTextField();

        //======== this ========
        setResizable(false);
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- startButton ----
        startButton.setText("Start");
        startButton.setFont(startButton.getFont().deriveFont(startButton.getFont().getSize() + 6f));
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startButtonMouseClicked(e);
            }
        });
        contentPane.add(startButton);
        startButton.setBounds(5, 255, 390, 35);

        //---- label1 ----
        label1.setText("Target's Name:");
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(label1);
        label1.setBounds(5, 0, 195, 30);

        //---- targetTextField ----
        targetTextField.setText("Goblin");
        targetTextField.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(targetTextField);
        targetTextField.setBounds(205, 0, 190, 30);

        //---- label2 ----
        label2.setText("Food's Name:");
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(label2);
        label2.setBounds(5, 35, 195, 30);

        //---- foodTextField ----
        foodTextField.setText("Tuna");
        foodTextField.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(foodTextField);
        foodTextField.setBounds(205, 35, 190, 30);

        //---- label3 ----
        label3.setText("Food Amount:");
        label3.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(label3);
        label3.setBounds(5, 70, 195, 30);

        //---- foodamountTextField ----
        foodamountTextField.setText("6");
        foodamountTextField.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(foodamountTextField);
        foodamountTextField.setBounds(205, 70, 190, 30);

        //---- bankCheckBox ----
        bankCheckBox.setText("Bank");
        contentPane.add(bankCheckBox);
        bankCheckBox.setBounds(10, 150, 80, bankCheckBox.getPreferredSize().height);

        //---- lootCheckBox ----
        lootCheckBox.setText("Loot");
        contentPane.add(lootCheckBox);
        lootCheckBox.setBounds(165, 150, 80, lootCheckBox.getPreferredSize().height);

        //---- eatCheckBox ----
        eatCheckBox.setText("Eat");
        contentPane.add(eatCheckBox);
        eatCheckBox.setBounds(315, 150, 80, eatCheckBox.getPreferredSize().height);

        //---- eatPercent ----
        eatPercent.setPaintTicks(true);
        contentPane.add(eatPercent);
        eatPercent.setBounds(20, 215, 360, eatPercent.getPreferredSize().height);

        //---- label4 ----
        label4.setText("Eat Percent");
        label4.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(label4);
        label4.setBounds(10, 175, 380, 30);

        //---- label5 ----
        label5.setText("Loot's Name:");
        label5.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(label5);
        label5.setBounds(5, 105, 195, 30);

        //---- lootTextField2 ----
        lootTextField2.setText("Coins");
        lootTextField2.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lootTextField2);
        lootTextField2.setBounds(205, 105, 190, 30);

        { // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());

    }

    private JButton startButton;
    private JLabel label1;
    private JTextField targetTextField;
    private JLabel label2;
    private JTextField foodTextField;
    private JLabel label3;
    private JTextField foodamountTextField;
    private JCheckBox bankCheckBox;
    private JCheckBox lootCheckBox;
    private JCheckBox eatCheckBox;
    private JSlider eatPercent;
    private JLabel label4;
    private JLabel label5;
    private JTextField lootTextField2;

}
