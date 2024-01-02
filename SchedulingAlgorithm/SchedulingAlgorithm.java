public class SchedulingAlgorithm {

    public static void main(String[] args) {
        
        SchedulingAlgorithmView theView = new SchedulingAlgorithmView();
        SchedulingAlgorithmModel theModel = new SchedulingAlgorithmModel();
        SchedulingAlgorithmController theController = new SchedulingAlgorithmController(theView, theModel);

    }
    
}
