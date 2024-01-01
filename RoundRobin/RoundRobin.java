public class RoundRobin {
    
    public static void main(String[] args) {

        RoundRobinView theView = new RoundRobinView();
        RoundRobinModel theModel = new RoundRobinModel();
        RoundRobinController theController = new RoundRobinController(theView, theModel);

    }
}
