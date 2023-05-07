package view.staff;

import controller.StaffController;
import model.Staff;
import view.Index;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class StaffView extends JFrame {

    private final StaffController staffController = new StaffController();
    private DefaultTableModel model = null;

    public static void main(String[] args) {
        new StaffView();
    }

    public StaffView() {
        super("Staff Interface");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        // create a panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JButton addButton = new JButton("Add");

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(addButton);



        JLabel label = new JLabel("Staff");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        String[] columnNames = {"#", "First Name", "Last Name", "Personal Address",
                "Job Address", "Role", "Supervisor Name", "Badge Number"};
        model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        /* button actions */
        deleteButton.addActionListener(actionEvent -> deleteRow(table,model));
        addButton.addActionListener(actionEvent -> addStaff());
        editButton.addActionListener(actionEvent -> editStaff(table,model) );
        /* **** */



        fillTable(model);
        // add the table to a scroll pane and set the layout
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(label, BorderLayout.NORTH);
        getContentPane().add(buttonPanel,BorderLayout.CENTER);
        getContentPane().add(scrollPane, BorderLayout.SOUTH);
        //pack();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new Index();
            }
        });
        setVisible(true);
    }

    private void editStaff(JTable table, DefaultTableModel model) {
        // get the selected row index
        int selectedRowIndex = table.getSelectedRow();

        if (selectedRowIndex != -1) {
            // get the values of the selected row
            Object[] selectedRowValues = new Object[model.getColumnCount()];
            for (int i = 0; i < model.getColumnCount(); i++) {
                selectedRowValues[i] = model.getValueAt(selectedRowIndex, i);
            }
            AddStaffView addStaffView = new AddStaffView(selectedRowValues);
            addStaffView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    super.windowClosed(e);
                    model.setRowCount(0); // Clear the existing rows
                    fillTable(model);
                }
            });
        }
    }

    private void addStaff() {
        AddStaffView addStaffView = new AddStaffView(null);
        addStaffView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                model.setRowCount(0); // Clear the existing rows
                fillTable(model);
            }
        });
    }

    public void deleteRow(JTable table, DefaultTableModel model){
        // get the selected row index
        int selectedRowIndex = table.getSelectedRow();

        if (selectedRowIndex != -1) {
            // get the values of the selected row
            Object[] selectedRowValues = new Object[model.getColumnCount()];
            for (int i = 0; i < model.getColumnCount(); i++) {
                selectedRowValues[i] = model.getValueAt(selectedRowIndex, i);
            }

            // delete from db file
            staffController.deleteStaff((Integer) selectedRowValues[0]);

            // remove the selected row from the table
            model.removeRow(selectedRowIndex);

        }
    }

    public void fillTable(DefaultTableModel model) {
        List<Staff> staffs = staffController.getAllStaffs();
        if(staffs == null || staffs.isEmpty())
            return;
        for (Staff staff : staffs) {
            Object[] rowData;
            if (staff.getSupervisorId() != null && staffController.getStaffById(staff.getSupervisorId())!=null) {
                String supervisorName = staffController.getStaffById(staff.getSupervisorId()).getFirstName();
                rowData = new Object[]{staff.getId(),
                        staff.getFirstName(),
                        staff.getLastName(),
                        staff.getPersonalAddress(),
                        staff.getJobAddress(),
                        staff.getRole(),
                        supervisorName,
                        staff.getBadgeNum()};
            } else {
                rowData = new Object[]{staff.getId(),
                        staff.getFirstName(),
                        staff.getLastName(),
                        staff.getPersonalAddress(),
                        staff.getJobAddress(),
                        staff.getRole(),
                        "NaN",
                        staff.getBadgeNum()};
            }
            model.addRow(rowData);
        }
    }
}
