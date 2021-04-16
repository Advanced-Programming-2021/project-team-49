import controller.Controller;
import controller.MasterController;

public class Main {
    public static void main(String[] args) {
        Controller masterController = new MasterController();
        masterController.run();
    }
}
