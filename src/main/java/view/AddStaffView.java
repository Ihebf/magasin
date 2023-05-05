package view;

import controller.StaffController;
import model.Staff;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.Arrays;

public class AddStaffView extends JFrame {

    private JTextField firstNameTf = new JTextField();
    private JTextField lastNameTf = new JTextField();
    private JTextField personalAddressTf = new JTextField();
    private JTextField jobAddressTf = new JTextField();
    private JTextField roleTf = new JTextField();
    private JTextField supervisorNameTf = new JTextField();
    private JTextField badgeNumTf = new JTextField();
    private StaffController staffController = new StaffController();

    public static void main(String[] args) {
        new AddStaffView(null);
    }

    public AddStaffView(Object[] selectedRowValues) {
        super("Add Staff");

        JButton cancelButton = new JButton("Cancel");
        JButton saveButton = new JButton("Save");

        // button action
        cancelButton.addActionListener(actionEvent -> this.dispose());
        saveButton.addActionListener(actionEvent -> addStaff(selectedRowValues, this));

        if (selectedRowValues != null) {
            fillFields(selectedRowValues);
        }


        // create the main panel with a BorderLayout
        JPanel addStaffPanel = new JPanel(new BorderLayout());

        // create the label for the top center of the panel
        JLabel titleLabel = new JLabel("Add Staff");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        addStaffPanel.add(titleLabel, BorderLayout.PAGE_START);

        // create the form panel
        JPanel formPanel = new JPanel(new GridLayout(8, 1));
        formPanel.add(new JLabel("First Name:"));
        formPanel.add(firstNameTf);
        formPanel.add(new JLabel("Last Name:"));
        formPanel.add(lastNameTf);
        formPanel.add(new JLabel("Personal address:"));
        formPanel.add(personalAddressTf);
        formPanel.add(new JLabel("Job address:"));
        formPanel.add(jobAddressTf);
        formPanel.add(new JLabel("Role:"));
        formPanel.add(roleTf);
        formPanel.add(new JLabel("Super Visor Name:"));
        formPanel.add(supervisorNameTf);
        formPanel.add(new JLabel("budge Num:"));
        formPanel.add(badgeNumTf);

        // create the panel for the buttons on the bottom center of the panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);
        addStaffPanel.add(buttonPanel, BorderLayout.PAGE_END);

        // add the form panel to the center of the main panel
        addStaffPanel.add(formPanel, BorderLayout.CENTER);

        this.getContentPane().add(addStaffPanel);
        this.setSize(500, 800);
        this.setLocationRelativeTo(null); // center the frame on the screen

        // display the add staff frame
        this.setVisible(true);
    }

    private void fillFields(Object[] selectedRowValues) {
        System.out.println(Arrays.toString(selectedRowValues));
        firstNameTf.setText((String) selectedRowValues[1]);
        lastNameTf.setText((String) selectedRowValues[2]);
        personalAddressTf.setText((String) selectedRowValues[3]);
        jobAddressTf.setText((String) selectedRowValues[4]);
        roleTf.setText((String) selectedRowValues[5]);
        try {
            Staff supervisor = staffController.getStaffById((Integer) selectedRowValues[6]);
            supervisorNameTf.setText(supervisor.getFirstName() + " " + supervisor.getLastName());
        } catch (Exception e) {
            supervisorNameTf.setText("NaN");
        }
        badgeNumTf.setText(selectedRowValues[7] + "");
    }

    private void addStaff(Object[] selectedRowValues, JFrame addStaffFrame) {
        Staff staff = new Staff();
        if (validateFields()) {
            staff.setFirstName(firstNameTf.getText());
            staff.setLastName(lastNameTf.getText());
            staff.setSupervisorId(null); // TODO
            staff.setRole(roleTf.getText());
            staff.setBadgeNum(Integer.parseInt(badgeNumTf.getText()));
            staff.setJobAddress(jobAddressTf.getText());
            staff.setPersonalAddress(personalAddressTf.getText());

            if(selectedRowValues == null) {
                staffController.addStaff(staff);
            }else {
                staff.setId((Integer) selectedRowValues[0]);
                staffController.editStaff(staff);
            }

            addStaffFrame.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Please verify the fields", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private boolean validateFields() {
        try {
            Integer.parseInt(badgeNumTf.getText());
            return !firstNameTf.getText().isEmpty()
                    && !lastNameTf.getText().isEmpty()
                    && !roleTf.getText().isEmpty()
                    && !badgeNumTf.getText().isEmpty()
                    //&& !badgeNumTf.getText().matches("\\d+")
                    && !jobAddressTf.getText().isEmpty()
                    && !personalAddressTf.getText().isEmpty();

        } catch (Exception e) {
            return false;
        }
    }
}
