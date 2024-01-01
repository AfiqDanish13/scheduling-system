import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GanttChartPanel extends JPanel {
    private List<Process> processes;

    public GanttChartPanel(List<Process> processes) {
        this.processes = processes;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int xPos = 10; // Start position for the first process.
        int yPos = 30; // Vertical position of the rectangles.
        int height = 20; // Height of each rectangle representing a process.
        int timeScale = 12; // Scale to determine the width of rectangles.

        // Draw each process as a rectangle.
        for (Process process : processes) {
            int width = process.getBurstTime() * timeScale; // Width based on burst time.
            g.setColor(process.getColor());
            g.fillRect(xPos, yPos, width, height);
            g.setColor(Color.BLACK);
            g.drawString("" + process.getProcessId(), xPos + width / 2, yPos + height / 2);
            xPos += width; // Move to the position for the next process.
            System.out.println(process);
        }

        // Draw time axis.
        xPos = 10;
        for (Process process : processes) {
            g.drawString(String.valueOf(process.getStartTime()), xPos, yPos + height + 20);
            xPos += process.getBurstTime() * timeScale;
        }
        // Draw the last time mark.
        g.drawString(String.valueOf(processes.get(processes.size() - 1).getFinishTime()), xPos, yPos + height + 20);
    }
}
