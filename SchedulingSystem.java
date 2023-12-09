import java.util.*;

public class SchedulingSystem {
    private List<Process> processes;
    private Scheduler scheduler;

    public SchedulingSystem() {
        this.processes = new ArrayList<>();
    }

    public void addProcess(Process process) {
        processes.add(process);
    }

    public List<Process> getProcesses() {
        return processes;
    }

    public void removeProcess() {
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void executeScheduling() {
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to Scheduling Algorithm");
        System.out.println("Enter number of processes: ");
        int numProcesses = input.nextInt();

        SchedulingSystem schedulingSystem = new SchedulingSystem();
        for (int i = 1; i <= numProcesses; i++) {
            System.out.println("Enter details for Process " + i);
            System.out.print("Arrival Time: ");
            int arrTime = input.nextInt();
            System.out.print("Burst Time: ");
            int burTime = input.nextInt();
            System.out.print("Priority: ");
            int priority = input.nextInt();
            System.out.println();
            Process process = new Process(i, arrTime, burTime, priority);
            schedulingSystem.addProcess(process);
        }

        // Print all processes
        List<Process> allProcesses = schedulingSystem.getProcesses();
        System.out.println("All Processes:");
        for (Process process : allProcesses) {
            System.out.println("Process ID: " + process.getProcessId());
            System.out.println("Arrival Time: " + process.getArrivalTime());
            System.out.println("Burst Time: " + process.getBurstTime());
            System.out.println("Priority: " + process.getPriority());
            System.out.println("-------------------------");
        }
    }
}
