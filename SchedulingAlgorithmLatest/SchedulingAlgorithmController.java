import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

import javax.swing.table.DefaultTableModel;

public class SchedulingAlgorithmController {

    SchedulingAlgorithmView theView;
    SchedulingAlgorithmModel theModel;
    String selectedScheduler;
    int counter = 0;

    public SchedulingAlgorithmController(SchedulingAlgorithmView theView, SchedulingAlgorithmModel theModel) {
        this.theView = theView;
        this.theModel = theModel;
        theView.confirmButtonListener(new ConfirmButtonListener());
    }

    public void resetAll() {
        theModel.processes.clear();
        theModel.readyQueue.clear();
        theModel.completedQueue.clear();
        theModel.ganttQueue.clear();
        Process.startEnd.clear();
        theView.scrollPane.setViewportView(null);
        theView.scrollPane2.setViewportView(null);
        theView.scrollPane3.setViewportView(null);
        theView.scrollPane.revalidate();
        theView.scrollPane.repaint();
        theView.scrollPane2.revalidate();
        theView.scrollPane2.repaint();
        theView.scrollPane3.revalidate();
        theView.scrollPane3.repaint();
    }

    public class ConfirmButtonListener implements ActionListener {

        private int flag = 1; // flag == 0 ( has empty field) , flag == 1 ( no empty field ) ,

        public ConfirmButtonListener() {
        }

        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < theView.getNumOfProcess(); i++) {
                if (theView.getArrivalTime().get(i).getText().isEmpty() ||
                        theView.getBurstTime().get(i).getText().isEmpty() ||
                        theView.getPriority().get(i).getText().isEmpty()) {
                    flag = 0;
                    break;
                } else {
                    flag = 1;
                }
            }

            if (flag == 0) {
                theView.getLabelEmptyField().setText("Fill out the empty textField.");
            } else {
                resetAll();
                selectedScheduler = (String) theView.scheduler.getSelectedItem(); // get the selected scheduler
                theView.getLabelEmptyField().setText("");
                Color[] colors = { Color.BLUE, Color.GRAY, Color.GREEN, Color.MAGENTA, Color.RED, Color.YELLOW,
                        Color.PINK, Color.LIGHT_GRAY, Color.CYAN, Color.DARK_GRAY };
                for (int i = 0; i < theView.getNumOfProcess(); i++) {
                    System.out.println(theView.getArrivalTime().get(i).getText());
                    int currentArrival = Integer.parseInt(theView.getArrivalTime().get(i).getText());
                    int currentBurst = Integer.parseInt(theView.getBurstTime().get(i).getText());
                    int currentPriority = Integer.parseInt(theView.getPriority().get(i).getText());
                    theModel.getProcesses()
                            .add(new Process(i, currentArrival, currentBurst, currentPriority, colors[i]));
                }

                System.out.println(theModel.getProcesses().size());

                if (selectedScheduler == "Non Preemptive SJF") {
                    System.out.println("Non Preemptive SJF");
                    theModel.nonPreSJFAlgorithm();
                } else if (selectedScheduler == "Round Robin (Quantum = 3)") {
                    System.out.println("Round Robin (Quantum = 3)");
                    theModel.roundRobinAlgorithm();
                } else if (selectedScheduler == "Non Preemptive Priority") {
                    System.out.println("Non Preemptive Priority");
                    theModel.nonPrePriorityAlgorithm();
                } else if (selectedScheduler == "Preemptive Priority") {
                    System.out.println("Preemptive Priority");
                    theModel.prePriorityAlgorithm();
                } else {
                    theModel.nonPreSJFAlgorithm();
                }

                // getting all information of all processes to be inserted into the table
                String[] columnNames = { "Process ID", "Arrival Time", "Burst Time", "Priority", "Start Time",
                        "Finish Time", "Turnaround Time", "Waiting Time" };
                int rows = theModel.getProcesses().size();
                int cols = columnNames.length;
                Integer dataProcess[][] = new Integer[rows][cols];
                for (int i = 0; i < rows; i++) {
                    dataProcess[i][0] = theModel.getProcesses().get(i).getProcessId();
                    dataProcess[i][1] = theModel.getProcesses().get(i).getArrivalTime();
                    dataProcess[i][2] = theModel.getProcesses().get(i).getBurstTime();
                    dataProcess[i][3] = theModel.getProcesses().get(i).getPriority();
                    dataProcess[i][4] = theModel.getProcesses().get(i).getStartTime();
                    dataProcess[i][5] = theModel.getProcesses().get(i).getFinishTime();
                    dataProcess[i][6] = theModel.getProcesses().get(i).getTurnaroundTime();
                    dataProcess[i][7] = theModel.getProcesses().get(i).getWaitingTime();
                    System.out.println(theModel.getProcesses().get(i).getStartTime());
                }

                // table making
                DefaultTableModel model = new DefaultTableModel(dataProcess, columnNames);
                // theView.remove(theView.panel2);
                // theView.add(theView.panel3, BorderLayout.CENTER);
                // theView.add(theView.panel1, BorderLayout.NORTH);
                theView.tableResult.setModel(model);
                theView.tableResult.setEnabled(false);
                theView.tableResult.setBounds(30, 40, 500, 190);
                theView.scrollPane.setViewportView(theView.tableResult);
                theView.scrollPane.setPreferredSize(new Dimension(800, 190));
                theView.panel3.add(theView.scrollPane);

                // gantt chart making
                theView.scrollPane2.setViewportView(theModel.getGanttChart());
                theView.scrollPane2.setPreferredSize(new Dimension(800, 200));
                theView.panel3.add(theView.scrollPane2);

                // add calculation of average turnaround time and average waiting time
                double totalTurnaroundTime = 0;
                double totalWaitingTime = 0;
                double aveTuraroundTime = 0;
                double aveWaitingTime = 0;
                for (Process pro : theModel.completedQueue) {
                    totalTurnaroundTime += pro.getTurnaroundTime();
                    totalWaitingTime += pro.getWaitingTime();
                }
                aveTuraroundTime = totalTurnaroundTime / theModel.completedQueue.size();
                aveWaitingTime = totalWaitingTime / theModel.completedQueue.size();
                String formattedTotalTurnaroundTime = String.format("%.2f", totalTurnaroundTime);
                String formattedTotalWaitingTime = String.format("%.2f", totalWaitingTime);
                String formattedAveTurnaroundTime = String.format("%.2f", aveTuraroundTime);
                String formattedaveWaitingTime = String.format("%.2f", aveWaitingTime);
                JLabel totalTurnaround = new JLabel("total Turnaround Time = " + formattedTotalTurnaroundTime);
                JLabel totalWaiting = new JLabel("total Waiting Time = " + formattedTotalWaitingTime);
                JLabel aveTurnaround = new JLabel("average Turnaround Time = " + formattedAveTurnaroundTime);
                JLabel aveWaiting = new JLabel("average Waiting Time = " + formattedaveWaitingTime);
                JPanel calculationPanel = new JPanel();
                calculationPanel.setLayout(new GridLayout(0, 1));
                calculationPanel.add(totalTurnaround);
                calculationPanel.add(totalWaiting);
                calculationPanel.add(aveTurnaround);
                calculationPanel.add(aveWaiting);
                calculationPanel.setBounds(30, 40, 500, 200);

                
                // JScrollPane scrollPane3 = new JScrollPane(calculationPanel);
                theView.scrollPane3.setViewportView(calculationPanel);
                theView.scrollPane3.setPreferredSize(new Dimension(800, 200));
                theView.panel3.add(theView.scrollPane3);
                JDialog newWindow = new JDialog();
                newWindow.setLayout(new GridLayout(3,1));
                newWindow.add(theView.scrollPane);
                newWindow.add(theView.scrollPane2);
                newWindow.add(theView.scrollPane3);
                newWindow.setVisible(true);
                newWindow.setSize(1000,600);
                newWindow.revalidate();
                newWindow.repaint();

                theView.scrollPane.revalidate();
                theView.scrollPane.repaint();
                theView.scrollPane2.revalidate();
                theView.scrollPane2.repaint();
                theView.scrollPane3.revalidate();
                theView.scrollPane3.repaint();

                // JButton rr = new JButton("Round Robin(Quantum = 3)");
                // JButton nps = new JButton("Non-preemptive SJF)");
                // JButton npp = new JButton("Non-preemptive Priority");
                // JButton pp = new JButton("Preemptive Priority");
                // rr.addActionListener(new ActionListener() {
                //     @Override
                //     public void actionPerformed(ActionEvent e) {
                //         // Clear the 'processes' list
                //         theModel.processes.clear();

                //         // Clear the 'readyQueue' list
                //         theModel.readyQueue.clear();

                //         // Clear the 'completedQueue' list
                //         theModel.completedQueue.clear();

                //         // Clear the 'ganttQueue' list
                //         theModel.ganttQueue.clear();
                //         theModel.roundRobinAlgorithm();
                //     }
                // });
                // nps.addActionListener(new ActionListener() {
                //     @Override
                //     public void actionPerformed(ActionEvent e) {
                //         // Clear the 'processes' list
                //         theModel.processes.clear();

                //         // Clear the 'readyQueue' list
                //         theModel.readyQueue.clear();

                //         // Clear the 'completedQueue' list
                //         theModel.completedQueue.clear();

                //         // Clear the 'ganttQueue' list
                //         theModel.ganttQueue.clear();
                //         theModel.nonPreSJFAlgorithm();
                //     }
                // });
                // npp.addActionListener(new ActionListener() {
                //     @Override
                //     public void actionPerformed(ActionEvent e) {
                //         // Clear the 'processes' list
                //         theModel.processes.clear();
                //         // Clear the 'readyQueue' list
                //         theModel.readyQueue.clear();
                //         // Clear the 'completedQueue' list
                //         theModel.completedQueue.clear();
                //         // Clear the 'ganttQueue' list
                //         theModel.ganttQueue.clear();
                //         theModel.nonPrePriorityAlgorithm();
                //     }
                // });
                // theView.panel1.add(rr);
                // theView.panel1.add(nps);
                // theView.panel1.add(npp);

                theView.panel1.revalidate();
                theView.panel1.repaint();
                theView.panel2.revalidate();
                theView.panel2.repaint();
                theView.panel3.revalidate();
                theView.panel3.repaint();

            }
        }
    }
}
