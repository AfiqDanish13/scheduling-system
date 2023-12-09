


public class Process {
    private int processID;
    private int arrivalTime;
    private int burstTime;
    private int priority;

    public Process(int processID, int arrivalTime, int burstTime, int priority){
        this.processID = processID;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }

    public int getProcessId(){return processID;}
    public int getArrivalTime(){return arrivalTime;}
    public int getBurstTime(){return burstTime;}
    public int getPriority(){return priority;}

    public void add(Process process) {
    }
}
