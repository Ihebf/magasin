package view.client;

import controller.ClientController;
import model.Client;
import view.Index;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class ClientView extends JFrame {

    private final ClientController clientController = new ClientController();
    private DefaultTableModel model = null;
    public static void main(String[] args) {
        new ClientView();
    }

    public ClientView(){
        super("Client Interface");
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



        JLabel label = new JLabel("Client");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        String[] columnNames = {"#", "Name", "Carte number", "Email", "Code postal"};
        model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        /* button actions */
        deleteButton.addActionListener(actionEvent -> deleteRow(table,model));
        addButton.addActionListener(actionEvent -> addClient());
        editButton.addActionListener(actionEvent -> editClient(table,model) );
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

    private void editClient(JTable table, DefaultTableModel model) {
        // get the selected row index
        int selectedRowIndex = table.getSelectedRow();

        if (selectedRowIndex != -1) {
            // get the values of the selected row
            Object[] selectedRowValues = new Object[model.getColumnCount()];
            for (int i = 0; i < model.getColumnCount(); i++) {
                selectedRowValues[i] = model.getValueAt(selectedRowIndex, i);
            }
            AddClientView addClientView = new AddClientView(selectedRowValues);
            addClientView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    super.windowClosed(e);
                    model.setRowCount(0); // Clear the existing rows
                    fillTable(model);
                }
            });
        }
    }

    private void addClient() {
        AddClientView addClientView = new AddClientView(null);
        addClientView.addWindowListener(new WindowAdapter() {
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
            clientController.deleteClient((Integer) selectedRowValues[0]);

            // remove the selected row from the table
            model.removeRow(selectedRowIndex);

        }
    }

    public void fillTable(DefaultTableModel model) {
        List<Client> clients = clientController.getAllClients();

        if(clients == null || clients.isEmpty())
            return;
        for (Client client : clients) {
            Object[] rowData;
            rowData = new Object[]{client.getId(),
                    client.getName(),
                    client.getCarteNum()+"",
                    client.getEmail(),
                    client.getCodePostal()
            };
            model.addRow(rowData);
        }
    }
}
