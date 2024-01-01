import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.LinkedList;

public class NonPreemptiveSJFModel {

    List<Process> processes = new ArrayList<>();
    List<Process> readyQueue = new ArrayList<>();
    List<Process> completedQueue = new ArrayList<>();
    private int totalBurstTime;

    public List<Process> getProcesses() {
        return processes;
    }

    public void printAllProcess() {
        for(Process pro: processes) {
            System.out.println("Process ID      : " + pro.getProcessId() + 
                               ", Arrival Time  : " + pro.getArrivalTime() + 
                               ", Burst Time    : " + pro.getBurstTime() + 
                               ", priority      : " + pro.getPriority() + 
                               ", startTime     : " + pro.getStartTime() + 
                               ", finishTime    : " + pro.getFinishTime() + 
                               ", turnaroundTime: " + pro.getTurnaroundTime() + 
                               ", waitingTime   : " + pro.getWaitingTime()
                               );
        }
    }

    public void sorting(List<Process> pro) {
        Collections.sort(pro);
    }

    public void setTotalBurstTime() {
        for(Process pro: processes) {
            totalBurstTime += pro.getBurstTime();
        }
    }

    public List<Process> getCompletedQueue() {
        return completedQueue;
    }

    public void algorithmScheduling() {

        int currentTime = 0;

        while(completedQueue.size() < processes.size()) {
            
            for(Process pro: processes) {
                if(pro.getArrivalTime() <= currentTime && (!completedQueue.contains(pro)) && (!readyQueue.contains(pro))) {
                    readyQueue.add(pro);
                }
            }

            readyQueue.sort((p1, p2) -> {
                if (p1.getBurstTime() != p2.getBurstTime()) {
                    return Integer.compare(p1.getBurstTime(), p2.getBurstTime());
                } else {
                    return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
                }
            });

            for(Process pro: readyQueue) {
                System.out.println(pro);
            }

            System.out.println("\n");

            // processing part
            if(!readyQueue.isEmpty()) {

                Process processedProcess = readyQueue.remove(0);
                processedProcess.setStartTime(currentTime);
                processedProcess.setFinishTime(currentTime + processedProcess.getBurstTime());
                processedProcess.setTurnaroundTime(processedProcess.getFinishTime() - processedProcess.getArrivalTime());
                processedProcess.setWaitingtime(processedProcess.getTurnaroundTime() - processedProcess.getBurstTime());
                currentTime = processedProcess.getFinishTime();
                completedQueue.add(processedProcess);

            } else if( completedQueue.size() == processes.size() ) {

                break;

            } else {

                currentTime++;

            }
        }

        printAllProcess();

    }
    
}
