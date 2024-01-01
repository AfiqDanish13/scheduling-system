import java.util.ArrayList;
import java.util.List;

public class RoundRobinModel {
    
    List<Process> processes = new ArrayList<>();
    List<Process> readyQueue = new ArrayList<>();
    List<Process> completedQueue = new ArrayList<>();
    List<Process> ganttQueue = new ArrayList<>();
    int quantumTime = 3;

    public List<Process> getProcesses() {
        return processes;
    }

    public List<Process> getGanttQueue() {
        return ganttQueue;
    }

    public void algorithmSchedule() {

        int currentTime = 0;

        for(Process pro: processes) {
            if(pro.getArrivalTime() <= currentTime ) {
                readyQueue.add(pro);
            }
        }

        readyQueue.sort((p1, p2) -> {
            if(p1.getArrivalTime() != p2.getArrivalTime()) {
                return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
            } else if(p1.getBurstTime() != p2.getBurstTime()) {
                return Integer.compare(p1.getBurstTime(),p2.getBurstTime());
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
                        } else if(p1.getBurstTime() != p2.getBurstTime()) {
                            return Integer.compare(p1.getBurstTime(),p2.getBurstTime());
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
                        } else if(p1.getBurstTime() != p2.getBurstTime()) {
                            return Integer.compare(p1.getBurstTime(),p2.getBurstTime());
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
                        } else if(p1.getBurstTime() != p2.getBurstTime()) {
                            return Integer.compare(p1.getBurstTime(),p2.getBurstTime());
                        } else {
                            return Integer.compare(p1.getNumOfExecuted(), p2.getNumOfExecuted());
                        }
                    });

                    for(Process pro: tempList) { // add the sorted tempList into the ready queue
                        readyQueue.add(pro);
                    }

            }

        }

        System.out.println(processes.get(0).getStartEnd());

    }
}
