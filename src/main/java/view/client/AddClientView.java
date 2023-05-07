package view.client;

import controller.ClientController;
import model.Client;

import javax.swing.*;
import java.awt.*;

public class AddClientView extends JFrame {

    private final JTextField nameTf = new JTextField();
    //private final JTextField carteNumbTf = new JTextField(); // auto generated
    private final JTextField emailTf = new JTextField();
    private final JTextField codePostalTf = new JTextField();
    private final ClientController clientController = new ClientController();

    public static void main(String[] args) {
        new AddClientView(null);
    }

    public AddClientView(Object[] selectedRowValues) {
        super("Add Client");

        JButton cancelButton = new JButton("Cancel");
        JButton saveButton = new JButton("Save");

        // button action
        cancelButton.addActionListener(actionEvent -> this.dispose());
        saveButton.addActionListener(actionEvent -> addClient(selectedRowValues, this));

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
        JPanel formPanel = new JPanel(new GridLayout(3, 1));
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameTf);
        //formPanel.add(new JLabel("Carte number:"));
        //formPanel.add(carteNumbTf);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailTf);
        formPanel.add(new JLabel("code postal:"));
        formPanel.add(codePostalTf);

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
        nameTf.setText((String) selectedRowValues[1]);
        //carteNumbTf.setText((String) selectedRowValues[2]);
        emailTf.setText((String) selectedRowValues[3]);
        codePostalTf.setText((String) selectedRowValues[4]);
    }

    private void addClient(Object[] selectedRowValues, JFrame addStaffFrame) {
        Client client = new Client();
        if (validateFields()) {
            client.setName(nameTf.getText());
            //client.setCarteNum(Integer.parseInt(carteNumbTf.getText()));
            client.setEmail(emailTf.getText());
            client.setCodePostal(codePostalTf.getText());
            if(selectedRowValues == null) {
                clientController.addClient(client);
            }else {
                client.setId((Integer) selectedRowValues[0]);
                clientController.editClient(client);
            }

            addStaffFrame.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Please verify the fields", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private boolean validateFields() {
        try {
            Integer.parseInt(codePostalTf.getText());
            return !nameTf.getText().isEmpty()
                    && !emailTf.getText().isEmpty()
                    && !codePostalTf.getText().isEmpty();

        } catch (Exception e) {
            return false;
        }
    }
}
