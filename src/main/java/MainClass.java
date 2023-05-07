import controller.ClientController;
import controller.StaffController;
import model.Client;
import model.Staff;
import view.Index;

public class MainClass {

    private static final StaffController staffController = new StaffController();
    private static final ClientController clientController = new ClientController();

    public static void main(String[] args) {
        for(int i=0;i<5;i++){
            Staff staff = new Staff("first name "+i,"last name "+i,"personal address "+i,"job address "+i,"role ",++i);
            Client client = new Client("client name "+i,"email"+i+"@gmail.com","500"+i);
            staffController.addStaff(staff);
            clientController.addClient(client);
        }

        new Index();
    }
}
