import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SchedulingAlgorithmView extends JFrame {

    JPanel panel1 = new JPanel(); // panel to get user number of processes
    JPanel panel2 = new JPanel(); // panel to get user input for all processes
    JPanel panel3 = new JPanel(); // panel to show all result
    DefaultComboBoxModel<Integer> numOfProcesses = new DefaultComboBoxModel<Integer>();
    DefaultComboBoxModel<String> schedulerChoice = new DefaultComboBoxModel<String>();
    JComboBox<String> scheduler = new JComboBox<>(schedulerChoice);
    JComboBox<Integer> processNum = new JComboBox<>(numOfProcesses);
    JButton enterButton = new JButton("Enter");
    JButton confirmButton = new JButton("Confirm");
    JLabel labelEmptyField = new JLabel();
    JTable tableResult = new JTable();
    private ArrayList<JTextField> arrivalTimeInput = new ArrayList<>();
    private ArrayList<JTextField> burstTimeInput = new ArrayList<>();
    private ArrayList<JTextField> priorityInput = new ArrayList<>();
    private int numOfProcessSelected;
    JScrollPane scrollPane3 = new JScrollPane();
    JScrollPane scrollPane2 = new JScrollPane();
    JScrollPane scrollPane = new JScrollPane();

    public SchedulingAlgorithmView() {
        super("Scheduling Algorithm");
        setLayout(new BorderLayout());
        // setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        JLabel label = new JLabel("Enter number of processes: ");
        numOfProcesses.addElement(3);
        numOfProcesses.addElement(4);
        numOfProcesses.addElement(5);
        numOfProcesses.addElement(6);
        numOfProcesses.addElement(7);
        numOfProcesses.addElement(8);
        numOfProcesses.addElement(9);
        numOfProcesses.addElement(10);

        processNum.setSelectedIndex(0);

        enterButton.addActionListener(new EnterButton());

        
        // enterButton.addActionListener(new EnterButton(processNum, numOfProcessesSelected, panel1, enterButton));

        panel1.add(label);
        panel1.add(processNum);
        panel1.add(enterButton);

        add(panel1, BorderLayout.NORTH);
        add(panel2, BorderLayout.CENTER);
        add(panel3, BorderLayout.SOUTH);
        // add(panel1);
        // add(panel2);
        // add(panel3);
        setSize(1000,1000);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void confirmButtonListener(ActionListener l) {
        confirmButton.addActionListener(l);
    }

    public ArrayList<JTextField> getArrivalTime() {
        return arrivalTimeInput;
    }

    public ArrayList<JTextField> getBurstTime() {
        return burstTimeInput;
    }

    public ArrayList<JTextField> getPriority() {
        return priorityInput;
    }

    public int getNumOfProcess() {
        return numOfProcessSelected;
    }

    public JLabel getLabelEmptyField() {
        return labelEmptyField;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JTable getTable() {
        return tableResult;
    }

    // Listener for enter button after user select desired process number
    public class EnterButton implements ActionListener {

        public EnterButton() {
        }

        public void actionPerformed(ActionEvent e) {

            numOfProcessSelected = (Integer) processNum.getSelectedItem();

            System.out.println("Selected in the home class: " + numOfProcessSelected);
            enterButton.setEnabled(false);

            panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS)); 
            panel2.setMaximumSize(new Dimension(1000, 400));

            // panel2.setLayout(new GridLayout(numOfProcessSelected, 7));
            // panel2.setMaximumSize(new Dimension(1000, 100));


            for(int i = 0; i < numOfProcessSelected; i++) {
                JLabel processIntro = new JLabel("Process " + ( i ));
                JLabel arrivalIntro = new JLabel("Enter arrival time: ");
                JLabel burstIntro = new JLabel("Enter burst time: ");
                JLabel priorityIntro = new JLabel("Enter priority: ");
                JLabel label = new JLabel();
                arrivalTimeInput.add(new JTextField(10));
                burstTimeInput.add(new JTextField(10));
                priorityInput.add(new JTextField(10));
                arrivalTimeInput.get(i).addKeyListener(new TextFieldNumOnly(arrivalTimeInput.get(i), label));
                burstTimeInput.get(i).addKeyListener(new TextFieldNumOnly(burstTimeInput.get(i), label));
                priorityInput.get(i).addKeyListener(new TextFieldNumOnly(priorityInput.get(i), label));
                JPanel contain = new JPanel();
                contain.add(processIntro);
                contain.add(arrivalIntro);
                contain.add(arrivalTimeInput.get(i));
                contain.add(burstIntro);
                contain.add(burstTimeInput.get(i));   
                contain.add(priorityIntro);
                contain.add(priorityInput.get(i));
                contain.add(label);
                panel2.add(contain);
            }

            schedulerChoice.addElement("Round Robin (Quantum = 3)");
            schedulerChoice.addElement("Non Preemptive SJF");
            schedulerChoice.addElement("Preemptive Priority");
            schedulerChoice.addElement("Non Preemptive Priority");
            scheduler.setSelectedItem(0);
            panel1.add(scheduler);
            
            panel1.add(confirmButton);
            panel1.add(labelEmptyField);

            panel2.revalidate();
            panel2.repaint();

        }

    }
    
    // Listener for input from user
    public class TextFieldNumOnly implements KeyListener {

        private String value;
        private JTextField currentTextField;
        private JLabel label;

        public TextFieldNumOnly(JTextField currentText, JLabel label) {
            currentTextField = currentText;
            this.label = label;
        }

        public void keyPressed(KeyEvent k) {

        }

        public void keyTyped(KeyEvent k) {

        }

        public void keyReleased(KeyEvent k) {
            value = currentTextField.getText();
            if(k.getKeyChar() >= '0' && k.getKeyChar() <= '9' || ( k.getKeyCode() == KeyEvent.VK_BACK_SPACE )) {
                currentTextField.setEditable(true);
                value = currentTextField.getText();
                label.setText("");
            } else {
                int textFieldLength = currentTextField.getText().length();
                currentTextField.setText(currentTextField.getText().substring(0, textFieldLength - 1));
                label.setText("number input only.");
            }
        }
    }
}