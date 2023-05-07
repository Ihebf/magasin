package view.stock;

import controller.ProductController;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;


public class AddProductView extends JFrame {

    private final JTextField nameTf = new JTextField();
    private final JTextField descriptionTf = new JTextField();
    private final String[] options = {"ALIMENTS", "MEDECIENE", "APPAREIL_ELECTRONIQUE","AUTRE"};
    private final JComboBox<String> typeCb = new JComboBox<>(options);
    private final JTextField priceTf = new JTextField();
    private final JTextField countryTf = new JTextField();
    private final JTextField tvaTf = new JTextField();
    private final JTextField quantityTf = new JTextField();
    private final JTextField supplierNameTf = new JTextField();
    private final ProductController productController = new ProductController();

    public static void main(String[] args) {
        new AddProductView(null);
    }

    public AddProductView(Object[] selectedRowValues) {
        super("Add Staff");

        JButton cancelButton = new JButton("Cancel");
        JButton saveButton = new JButton("Save");

        // button action
        cancelButton.addActionListener(actionEvent -> this.dispose());
        saveButton.addActionListener(actionEvent -> addProduct(selectedRowValues, this));

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
        formPanel.add(new JLabel("Product Name:"));
        formPanel.add(nameTf);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(descriptionTf);
        formPanel.add(new JLabel("Type:"));
        formPanel.add(typeCb);
        formPanel.add(new JLabel("Price:"));
        formPanel.add(priceTf);
        formPanel.add(new JLabel("Country:"));
        formPanel.add(countryTf);
        formPanel.add(new JLabel("TVA:"));
        formPanel.add(tvaTf);
        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(quantityTf);
        formPanel.add(new JLabel("Supplier Name:"));
        formPanel.add(supplierNameTf);

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
        descriptionTf.setText((String) selectedRowValues[2]);
        typeCb.setSelectedItem(selectedRowValues[3]);
        priceTf.setText((String) selectedRowValues[4]);
        countryTf.setText((String) selectedRowValues[5]);
        tvaTf.setText((String) selectedRowValues[6]);
        quantityTf.setText((String) selectedRowValues[7]);
        supplierNameTf.setText((String) selectedRowValues[8]);
    }

    private void addProduct(Object[] selectedRowValues, JFrame addStaffFrame) {
        Product product = new Product();
        if (validateFields()) {
            product.setName(nameTf.getText());
            product.setDescription(descriptionTf.getText());
            product.setType(Objects.requireNonNull(typeCb.getSelectedItem()).toString());
            product.setPrice(Double.parseDouble(priceTf.getText()));
            product.setCountry(countryTf.getText());
            product.setTVA(Double.parseDouble(tvaTf.getText()));
            product.setQuantity(Integer.parseInt(quantityTf.getText()));
            product.setSupplierName(supplierNameTf.getText());

            if(selectedRowValues == null) {
                productController.addProduct(product);
            }else {
                product.setId((Integer) selectedRowValues[0]);
                productController.editProduct(product);
            }

            addStaffFrame.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Please verify the fields", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private boolean validateFields() {
        try {
            Double.parseDouble(priceTf.getText());
            Double.parseDouble(tvaTf.getText());
            Integer.parseInt(quantityTf.getText());
            return !nameTf.getText().isEmpty()
                    //&& !typeCb.getSelectedItem()
                    && !priceTf.getText().isEmpty()
                    && !countryTf.getText().isEmpty()
                    && !tvaTf.getText().isEmpty()
                    && !quantityTf.getText().isEmpty()
                    && !supplierNameTf.getText().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}
