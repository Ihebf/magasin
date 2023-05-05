import controller.StaffController;
import model.Staff;
import view.Index;

public class MainClass {

    private static final StaffController staffController = new StaffController();

    public static void main(String[] args) {
        for(int i=0;i<50;i++){
            Staff staff = new Staff("first name "+i,"last name "+i,"personal address "+i,"job address "+i,"role ",++i);
            staffController.addStaff(staff);
        }

        new Index();
    }
}
