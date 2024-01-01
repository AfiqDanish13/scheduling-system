public class NonPreemptiveSJF {

    public static void main(String[] args) {
        
        NonPreemptiveSJFView theView = new NonPreemptiveSJFView();
        NonPreemptiveSJFModel theModel = new NonPreemptiveSJFModel();
        NonPreemptiveSJFController theController = new NonPreemptiveSJFController(theView, theModel);

    }
    
}
