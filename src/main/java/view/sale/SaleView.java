package view.sale;

import controller.ProductController;
import controller.SaleController;
import model.Product;
import model.Staff;
import view.Index;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class SaleView extends JFrame {


    private DefaultTableModel model = null;
    private final JTextField searchTextField = new JTextField(20);


    public static void main(String[] args) {
        new SaleView(null);
    }

    public SaleView(Staff staff) {
        super("Sale Interface");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        // create a panel for buttons
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton searchButton = new JButton("search");

        searchPanel.add(searchTextField);
        searchPanel.add(searchButton);

        JLabel label = new JLabel("Sale");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        String[] columnNames = {"#",
                "Name",
                "Description",
                "Type",
                "Price",
                "Country",
                "TVA",
                "Quantity",
                "Supplier name",
                "Action"
        };

        model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(table,model));


        /* button actions */
        searchButton.addActionListener(actionEvent -> searchProduct(model));
        /* ************* */


        fillTable(model);
        // add the table to a scroll pane and set the layout
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(label, BorderLayout.NORTH);
        getContentPane().add(searchPanel, BorderLayout.CENTER);
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

    private static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText("sell");
            return this;
        }
    }

    private static class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private JButton button;
        private Object value;
        private JTable table;
        private DefaultTableModel model;
        private final ProductController productController = new ProductController();
        private final SaleController saleController = new SaleController();

        public ButtonEditor(JTable table, DefaultTableModel model) {
            button = new JButton();
            button.setText("Sell");
            button.addActionListener(this);
            this.table = table;
            this.model = model;
        }

        public Object getCellEditorValue() {
            return value;
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.value = value;
            return button;
        }


        public void actionPerformed(ActionEvent e) {
            int row = table.getEditingRow();
            int id = (int) table.getValueAt(row, 0);
            Product product = productController.getProductById(id);

            String quantityText = JOptionPane.showInputDialog(null, "Enter Quantity:", "Set Quantity", JOptionPane.PLAIN_MESSAGE);
            try {
                int quantity = Integer.parseInt(quantityText);
                if(quantity>product.getQuantity()){
                    JOptionPane.showMessageDialog(null, "Invalid quantity");
                }else {
                    product.setQuantity(product.getQuantity()-quantity);
                    productController.editProduct(product);
                    ProductController productController = new ProductController();
                    List<Product> products = productController.getAllProducts();
                    model.setRowCount(0); // Clear the existing rows
                    if(products == null || products.isEmpty())
                        return;
                    for (Product p : products) {
                        Object[] rowData;
                        rowData = new Object[]{p.getId(),
                                p.getName(),
                                p.getDescription(),
                                p.getType(),
                                p.getPrice()+"",
                                p.getCountry(),
                                p.getTVA()+"",
                                p.getQuantity()+"",
                                p.getSupplierName()
                        };
                        model.addRow(rowData);
                    }
                }
            } catch (NumberFormatException ee) {
                JOptionPane.showMessageDialog(null, "Invalid quantity");
            }
            fireEditingStopped();
        }

    }

    private void searchProduct(DefaultTableModel model) {
        ProductController productController = new ProductController();
        if (searchTextField.getText().isEmpty()) {
            fillTable(model);
        } else {
            List<Product> products = productController.matchProductByName(searchTextField.getText());
            if (products == null || products.isEmpty()) {
                JOptionPane.showMessageDialog(null, "product not found", "Information", JOptionPane.INFORMATION_MESSAGE);
            } else {
                model.setRowCount(0); // Clear the existing rows
                for (Product p : products) {
                    Object[] rowData;

                    rowData = new Object[]{p.getId(),
                            p.getName(),
                            p.getDescription(),
                            p.getType(),
                            p.getPrice() + "",
                            p.getCountry(),
                            p.getTVA() + "",
                            p.getQuantity() + "",
                            p.getSupplierName()
                    };
                    model.addRow(rowData);
                }
            }
        }
    }


    public static void fillTable(DefaultTableModel model) {
        ProductController productController = new ProductController();
        List<Product> products = productController.getAllProducts();
        model.setRowCount(0); // Clear the existing rows
        if(products == null || products.isEmpty())
            return;
        for (Product product : products) {
            Object[] rowData;
            rowData = new Object[]{product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getType(),
                    product.getPrice()+"",
                    product.getCountry(),
                    product.getTVA()+"",
                    product.getQuantity()+"",
                    product.getSupplierName()
            };
            model.addRow(rowData);
        }
    }
}
