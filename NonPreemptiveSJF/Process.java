import java.awt.*;
public class Process implements Comparable<Process> {
    private int processID;
    private int arrivalTime;
    private int burstTime;
    private int startTime;
    private int finishTime;
    private int turnaroundTime;
    private int waitingTime;
    private int priority;
    private Color processColor;

    public Process(int processID, int arrivalTime, int burstTime, int priority, Color processColor){
        this.processID = processID;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.processColor = processColor;
    }

    // setter
    public void setStartTime(int startTime) { this.startTime = startTime; }
    public void setFinishTime(int finishTime) { this.finishTime = finishTime; }
    public void setTurnaroundTime(int turnaroundTime) { this.turnaroundTime = turnaroundTime; }
    public void setWaitingtime(int waitingTime) { this.waitingTime = waitingTime; }

    // getter
    public int getStartTime() { return startTime; }
    public int getFinishTime() { return finishTime; }
    public int getTurnaroundTime() { return turnaroundTime; }
    public int getWaitingTime() { return waitingTime; }
    public int getProcessId(){ return processID; }
    public int getArrivalTime(){ return arrivalTime; }
    public int getBurstTime(){ return burstTime; }
    public int getPriority(){ return priority; }
    public Color getColor() { return processColor; }

    public void add(Process process) {
    }

    public int compareTo(Process other) {
        return Integer.compare(this.burstTime, other.burstTime);
    }

    public String toString() {
        return("Process ID      : " + processID + 
        ", Arrival Time  : " + arrivalTime + 
        ", Burst Time    : " + burstTime + 
        ", priority      : " + priority + 
        ", startTime     : " + startTime + 
        ", finishTime    : " + finishTime + 
        ", turnaroundTime: " + turnaroundTime + 
        ", waitingTime   : " + waitingTime);
    }

}
