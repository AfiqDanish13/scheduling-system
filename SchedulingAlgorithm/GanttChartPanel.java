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
        int currPro = 0;
        for (Process process : processes) {
            int diff = process.getStartEnd().get(currPro).get(1) - process.getStartEnd().get(currPro).get(0);
            int width = diff * timeScale; // Width based on burst time.
            g.setColor(process.getColor());
            g.fillRect(xPos, yPos, width, height);
            g.setColor(Color.BLACK);
            g.drawString("" + process.getProcessId(), xPos + width / 2, yPos + height / 2);
            xPos += width; // Move to the position for the next process.
            currPro++;
        }

        // Draw time axis.
        xPos = 10;
        int currentPro = 0;
        for (Process process : processes) {
            int diff = process.getStartEnd().get(currentPro).get(1) - process.getStartEnd().get(currentPro).get(0);
            g.drawString(String.valueOf(process.getStartEnd().get(currentPro).get(0)), xPos, yPos + height + 20);
            xPos += diff * timeScale;
            currentPro++;
        }
        // Draw the last time mark.
        g.drawString(String.valueOf(processes.get(processes.size() - 1).getFinishTime()), xPos, yPos + height + 20);
    }
}
