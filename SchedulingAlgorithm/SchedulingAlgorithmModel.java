import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.awt.*;

public class SchedulingAlgorithmModel {

    List<Process> processes = new ArrayList<>();
    List<Process> readyQueue = new ArrayList<>();
    List<Process> completedQueue = new ArrayList<>();
    List<Process> ganttQueue = new ArrayList<>();
    GanttChartPanel ganttChart;

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

    public List<Process> getCompletedQueue() {
        return completedQueue;
    }

    public GanttChartPanel getGanttChart() {
        return ganttChart;
    }

    public void nonPreSJFAlgorithm() {

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
                ArrayList<Integer> startAndEnd = new ArrayList<>();
                startAndEnd.add(currentTime);
                startAndEnd.add(currentTime + processedProcess.getBurstTime());
                processedProcess.getStartEnd().add(startAndEnd);

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

        ganttChart = new GanttChartPanel(completedQueue);
        ganttChart.setPreferredSize(new Dimension(775, 100));
    }

    public void roundRobinAlgorithm() {

        int quantumTime = 3;
        int currentTime = 0;

        for(Process pro: processes) {
            if(pro.getArrivalTime() <= currentTime ) {
                readyQueue.add(pro);
            }
        }

        readyQueue.sort((p1, p2) -> {
            if(p1.getArrivalTime() != p2.getArrivalTime()) {
                return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
            } else if(p1.getPriority() != p2.getPriority()) {
                return Integer.compare(p1.getPriority(), p2.getPriority());
            } else {
                return Integer.compare(p1.getNumOfExecuted(), p2.getNumOfExecuted());
            }
        });

        while(completedQueue.size() < processes.size()) {

            // processing part
            if(!readyQueue.isEmpty()) {

                Process processedProcess = readyQueue.remove(0);

                if(processedProcess.getRemainingTime() > quantumTime) { // if the process cannot finish within the quantum time

                    System.out.println("Time > quantum: " + processedProcess.getProcessId());
                    System.out.println("Remaining Time: " + processedProcess.getRemainingTime() + "\n");

                    ArrayList<Integer> startAndEnd = new ArrayList<>();
                    startAndEnd.add(currentTime);
                    startAndEnd.add(currentTime + quantumTime);

                    if (processedProcess.getNumOfExecuted() == 0) { // set Start Time for the first time executed process
                        processedProcess.setStartTime(currentTime);
                    }

                    processedProcess.setRemainingTime(processedProcess.getRemainingTime() - quantumTime);
                    processedProcess.setNumOfExecuted(processedProcess.getNumOfExecuted() + 1);
                    processedProcess.setStartTime(currentTime);
                    processedProcess.getStartEnd().add(startAndEnd);

                    currentTime = currentTime + quantumTime;

                    List<Process> tempList = new ArrayList<>();

                    for(Process pro: processes) { // get the new arrival process into the temporary list before being to be sorted before entered the readyQueue
                        
                        if(pro.getArrivalTime() <= currentTime && (!completedQueue.contains(pro)) && (!readyQueue.contains(pro)) && (pro != processedProcess)) {
                            tempList.add(pro);
                        }

                    }        

                    tempList.sort((p1, p2) -> { // sort the temporary list
                        if(p1.getArrivalTime() != p2.getArrivalTime()) {
                            return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
                        } else if(p1.getPriority() != p2.getPriority()) {
                            return Integer.compare(p1.getPriority(), p2.getPriority());
                        } else {
                            return Integer.compare(p1.getNumOfExecuted(), p2.getNumOfExecuted());
                        }
                    });

                    for(Process pro: tempList) { // add the sorted tempList into the ready queue
                        readyQueue.add(pro);
                    }

                    readyQueue.add(processedProcess);
                    ganttQueue.add(processedProcess);

                } else if(processedProcess.getRemainingTime() <= quantumTime){ // if the process is able to complete / finish within the quantum time

                    System.out.println("Time <= quantum: " + processedProcess.getProcessId());
                    System.out.println("Remaining Time: " + processedProcess.getRemainingTime() + "\n");

                    ArrayList<Integer> startAndEnd = new ArrayList<>();
                    startAndEnd.add(currentTime);
                    startAndEnd.add(currentTime + processedProcess.getRemainingTime());

                    if(processedProcess.getNumOfExecuted() == 0) { // set start time for the process that is first time being executed
                        processedProcess.setStartTime(currentTime);
                    }

                    processedProcess.setNumOfExecuted(processedProcess.getNumOfExecuted() + 1);
                    processedProcess.setFinishTime(currentTime + processedProcess.getRemainingTime());
                    processedProcess.setTurnaroundTime(processedProcess.getFinishTime() - processedProcess.getArrivalTime());
                    processedProcess.setWaitingtime(processedProcess.getTurnaroundTime() - processedProcess.getBurstTime());
                    currentTime = currentTime + processedProcess.getRemainingTime();
                    processedProcess.setRemainingTime(0);
                    processedProcess.getStartEnd().add(startAndEnd);

                    List<Process> tempList = new ArrayList<>();

                    for(Process pro: processes) { // get the new arrival process into the temporary list before being to be sorted before entered the readyQueue
                        
                        if(pro.getArrivalTime() <= currentTime && (!completedQueue.contains(pro)) && (!readyQueue.contains(pro)) && (pro != processedProcess)) {
                            tempList.add(pro);
                        }

                    }        

                    tempList.sort((p1, p2) -> { // sort the temporary list
                        if(p1.getArrivalTime() != p2.getArrivalTime()) {
                            return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
                        } else if(p1.getPriority() != p2.getPriority()) {
                            return Integer.compare(p1.getPriority(), p2.getPriority());
                        } else {
                            return Integer.compare(p1.getNumOfExecuted(), p2.getNumOfExecuted());
                        }
                    });

                    for(Process pro: tempList) { // add the sorted tempList into the ready queue
                        readyQueue.add(pro);
                    }

                    completedQueue.add(processedProcess); // add the complete the completedqueue
                    ganttQueue.add(processedProcess);

                }

            } else {

                currentTime++;

                List<Process> tempList = new ArrayList<>();

                    for(Process pro: processes) { // get the new arrival process into the temporary list before being to be sorted before entered the readyQueue
                        
                        if(pro.getArrivalTime() <= currentTime && (!completedQueue.contains(pro)) && (!readyQueue.contains(pro))) {
                            tempList.add(pro);
                        }

                    }        

                    tempList.sort((p1, p2) -> { // sort the temporary list
                        if(p1.getArrivalTime() != p2.getArrivalTime()) {
                            return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
                        } else if(p1.getPriority() != p2.getPriority()) {
                            return Integer.compare(p1.getPriority(), p2.getPriority());
                        } else {
                            return Integer.compare(p1.getNumOfExecuted(), p2.getNumOfExecuted());
                        }
                    });

                    for(Process pro: tempList) { // add the sorted tempList into the ready queue
                        readyQueue.add(pro);
                    }

            }

        }

        ganttChart = new GanttChartPanel(ganttQueue);
        ganttChart.setPreferredSize(new Dimension(775, 100));
    }
    
}

