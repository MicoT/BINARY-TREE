package BinaryTree;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

public class BTNodeMenu extends JFrame implements ActionListener, ItemListener, KeyListener {
    final private JLabel chooseLabel, valueLabel;
    final private JTextField valueField;
    final private JComboBox<String> chooseComboBox;
    final private JTextArea upTextArea, downTextArea;
    final private JButton processButton;
    final private BinaryTree tree;

    private static final class Action {
        private static final int ADD_NODE = 0;
        private static final int REMOVE_NODE = 1;
        private static final int SEARCH = 2;
        private static final int CUT_THE_TREE = 3;
        private static final int END = 4;

    }
    
    public static void main(String[] args) {
        new BTNodeMenu();
    }

    BTNodeMenu() {
        tree = new BinaryTree();
        String[] menu = {"Add Node", "Remove Node", "Search", "Cut the Tree", "End"};

        upTextArea = new JTextArea();
        downTextArea = new JTextArea();
        chooseLabel = new JLabel("Choose");
        chooseComboBox =  new JComboBox<>(menu);
        valueLabel = new JLabel("Value");
        valueField = new JTextField();
        processButton = new JButton("Process");

        upTextArea.setEditable(false);
        downTextArea.setEditable(false);
        upTextArea.setText(displayTreeMetadata());
        downTextArea.setText(displayTreeNodes());

        add(upTextArea).setBounds(20, 20 , 330, 60);
        add(new JScrollPane(downTextArea)).setBounds(20, 80, 330, 100);
        add(chooseLabel).setBounds(20, 200, 60, 20);
        add(chooseComboBox).setBounds(80, 200, 150, 20);
        add(processButton).setBounds(240, 200, 110, 60);
        add(valueLabel).setBounds(20, 230, 100, 20);
        add(valueField).setBounds(80, 230, 150, 20);

        processButton.addActionListener(this);
        chooseComboBox.addItemListener(this);
        valueField.addKeyListener(this);
        processButton.setEnabled(false);

        setTitle("Binary Tree ADT Application Mico Tongco");
        setSize(400, 300);
        getContentPane().setBackground(new Color(100, 50, 90, 80));
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    public String displayTreeMetadata() {
        return "Empty\t: " + tree.isEmpty() + "\tCurrent Nodes\t: " + tree.count()
                + "\nDepth\t: " + tree.getDepth() + "\tHeight\t: " + tree.getHeight()
                +"\nLevel\t: " + tree.getDepth();
    }

    public String displayTreeNodes() {
        return "Level Order\t: " + tree.displayLevelOrder()
                + "\nInorder\t: " + tree.displayInOrder()
                + "\nPreorder\t: " + tree.displayPreOrder()
                + "\nPostorder\t: " + tree.displayPostOrder()
                + "\nInternal Nodes\t: " + tree.displayInternalNodes()
                + "\nLeaves\t: " + tree.displayLeaves();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        int action = chooseComboBox.getSelectedIndex();
        int value = action == Action.ADD_NODE || action == Action.SEARCH
                ? Integer.parseInt(valueField.getText())
                : 0;
        switch (action) {
            case Action.ADD_NODE -> {
                if (!tree.insert(value)) {
                    JOptionPane.showMessageDialog(null, "Duplicate Found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            case Action.REMOVE_NODE -> tree.remove();
            case Action.SEARCH -> JOptionPane.showMessageDialog(null, String.format("%d is %spresent in the tree.", value, tree.contains(value) ? "" : "not "), "Result", JOptionPane.ERROR_MESSAGE);
            case Action.CUT_THE_TREE -> tree.cut();
            case Action.END -> System.exit(0);
        }

        upTextArea.setText(displayTreeMetadata());
        downTextArea.setText(displayTreeNodes());
        valueField.setText("");
        processButton.setEnabled(action == Action.REMOVE_NODE);
        
    }

    @Override
    public void itemStateChanged(ItemEvent e){
        int action = chooseComboBox.getSelectedIndex();
        switch (action) {
            case Action.REMOVE_NODE, Action.CUT_THE_TREE, Action.END -> {
                processButton.setEnabled(true);
                valueField.setEditable(false);
            }
            case Action.ADD_NODE, Action.SEARCH -> {
                valueField.setEditable(true);
                processButton.setEnabled(false);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        if (e.getSource().equals(valueField)) {
            processButton.setEnabled(!(valueField.getText().isEmpty()));
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource().equals(valueField) && !(Character.isDigit(e.getKeyChar()))){
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e ){}
}