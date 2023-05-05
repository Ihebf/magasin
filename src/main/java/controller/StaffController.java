package controller;

import model.Staff;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StaffController {
    private static final Path staffDB;

    static {
        staffDB = Paths.get(System.getProperty("user.dir") + "/src/main/resources/", "staffDB.txt");
        if(!Files.exists(staffDB)) {
            try {
                Files.createFile(staffDB);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Test staff controller
    public static void main(String[] args) {
        /*StaffController staffController = new StaffController();
        Staff c = new Staff("staff1", "staff1@gmail.com", "5100");
        Staff c1 = new Staff("staff2", "staff2@gmail.com", "5100");
        staffController.addStaff(c);
        staffController.addStaff(c1);

        System.out.println("get staff by email: " + staffController.getStaffByEmail("staff2@gmail.com"));
        System.out.println("get staff by id: " + staffController.getStaffById(0));
        System.out.println("delete staff: " + staffController.deleteStaff(0));
        c1.setEmail("editedMail@gmail.com");
        staffController.editStaff(c1);
        System.out.println("get all staffs: " + staffController.getAllStaffs());*/

    }

    public boolean addStaff(Staff staff) {
        if (getStaffByBadgeNum(staff.getBadgeNum()) != null) {
            return false;
        }
        try {
            Files.writeString(staffDB, staff.toString() + "\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteStaff(int id) {
        try {
            List<String> data = Files.readAllLines(staffDB)
                    .parallelStream()
                    .filter(l -> !(Integer.parseInt(l.split(",")[0]) == id))
                    .collect(Collectors.toList());
            Files.write(staffDB, data);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void editStaff(Staff staff) {
        try {
            List<String> data = Files.readAllLines(staffDB);
            List<String> newData = new ArrayList<>();
            data.parallelStream()
                    .forEach(d -> {
                        if (Integer.parseInt(d.split(",")[0]) == staff.getId()) {
                            Staff oldStaff = convertLineToStaff(d);
                            oldStaff.setId(staff.getId());
                            oldStaff.setFirstName(staff.getFirstName());
                            oldStaff.setLastName(staff.getLastName());
                            oldStaff.setPersonalAddress(staff.getPersonalAddress());
                            oldStaff.setJobAddress(staff.getJobAddress());
                            oldStaff.setRole(staff.getRole());
                            oldStaff.setSupervisorId(staff.getSupervisorId());
                            oldStaff.setBadgeNum(staff.getBadgeNum());
                            newData.add(oldStaff.toString());
                        } else {
                            newData.add(d);
                        }
                    });
            Files.write(staffDB, newData, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Staff getStaffById(int id) {
        try {
            return Files.readAllLines(staffDB)
                    .parallelStream()
                    .filter(l -> Integer.parseInt(l.split(",")[0]) == id)
                    .findFirst()
                    .map(StaffController::convertLineToStaff)
                    .orElse(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Staff getStaffByBadgeNum(int badgeNum) {
        try {
            return Files.readAllLines(staffDB)
                    .parallelStream()
                    .filter(l -> Integer.parseInt(l.split(",")[7]) == badgeNum)
                    .findFirst()
                    .map(StaffController::convertLineToStaff)
                    .orElse(null);
        } catch (IOException e) {
            System.err.println("DB file doesn't exist");
        }
        return null;
    }

    public List<Staff> getAllStaffs() {
        try {
            return Files.readAllLines(staffDB)
                    .stream()
                    .map(StaffController::convertLineToStaff)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Staff convertLineToStaff(String line) {
        Staff staff = new Staff();
        String[] att = line.split(",");
        staff.setId(Integer.parseInt(att[0]));
        staff.setFirstName(att[1]);
        staff.setLastName(att[2]);
        staff.setPersonalAddress(att[3]);
        staff.setJobAddress(att[4]);
        staff.setRole(att[5]);
        try {
            staff.setSupervisorId(Integer.parseInt(att[6]));
        }catch (Exception e){
            staff.setSupervisorId(null);
        }

        staff.setBadgeNum(Integer.parseInt(att[7]));
        return staff;
    }
}
