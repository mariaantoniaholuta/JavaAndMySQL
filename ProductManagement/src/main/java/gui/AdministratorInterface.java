package gui;

import datalayer.FileWriters;
import model.Product;
import datalayer.Serializator;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdministratorInterface extends JFrame {
    private JScrollPane tablePanel;
    private JTable table;
    private JPanel mainPanel;
    private JButton goBackButton;
    private JButton refreshButton;
    private JLabel nameLabel;
    private JTextField nameFld;
    private JLabel emailLabel;
    private JLabel caloriesLabel;
    private JLabel proteinLabel;
    private JLabel fatLabel;
    private JLabel sodiumLabel;
    private JLabel priceLabel;
    private JTextField ratingFld;
    private JTextField caloriesFld;
    private JTextField proteinFld;
    private JTextField fatField;
    private JTextField sodiumField;
    private JTextField priceField;
    private JButton addProductButton;
    private JButton modifyButton;
    private JButton deleteButton;
    private JButton goToMenuButton;
    private JButton reportsButton;

    private List<Product> products = new ArrayList<>();

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public AdministratorInterface () {
        setContentPane(mainPanel);
        setTitle("Admin");
        Dimension dimensions = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(1200, 700);
        int x = (int) ((dimensions.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimensions.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

      //  List<Product> products = new ArrayList<>();
        try {
            products = Serializator.readForProducts();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        DefaultTableModel dm = new DefaultTableModel();
        Product p = products.get(0);
        String[] column = {p.getTitle(), p.getRating(), p.getCalories(), p.getProtein(), p.getFat(),p.getSodium(), p.getPrice()};
        for (int i = 0; i <= 6; i++) {
            dm.addColumn(column[i]);
        }
        Object[] rows = new Object[7];
        for (int i = 0; i <= 6; i++)
            rows[i] = new Object();

        int count = 0;
        for(Product pr: products) {
            count++;
        }
        for (int i = 1; i < count; i++) {
             addRowsFromData(rows, products.get(i));
             dm.addRow(rows);
        }

        table.getTableHeader().setFont(new Font("Segoe UI Black", Font.BOLD, 18));
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(128,129,143));
        table.setModel(dm);
        table.setRowHeight(40);
        table.setFillsViewportHeight(true);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        table.setDefaultRenderer(String.class, centerRenderer);
        for(int i=0;i<7;i++){
            table.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
        }

        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LogIn();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AdministratorInterface();
            }
        });

        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product newProduct = new Product(nameFld.getText(), ratingFld.getText(), caloriesFld.getText(), proteinFld.getText(), fatField.getText(),sodiumField.getText(), priceField.getText());
                FileWriters.writeNewProduct(newProduct);
                JOptionPane.showMessageDialog(getParent(), "new product added!");
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Product p: products) {
                    if(p.getTitle().equals(table.getModel().getValueAt(table.getSelectedRow(), 0))) {
                        System.out.println("Product FOUND to delete: "+p.getTitle());
                        try {
                             FileWriters.deleteProduct(p, products);
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        JOptionPane.showMessageDialog(getParent(), "product deleted!");
                    }
                }
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
                for(Product p: products) {
                    if(p.getTitle().equals(table.getModel().getValueAt(table.getSelectedRow(), 0))) {
                        System.out.println("Product FOUND to update: "+p.getTitle());
                        try {
                            FileWriters.updateProduct(p, products, table);
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }

            }
        });

        goToMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AddToMenu();
            }
        });

        reportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Reports();
            }
        });
    }


    private static void addRowsFromData(Object[] rows, Product p) {

            rows[0] = p.getTitle();
            rows[1] = p.getRating();
            rows[2] = p.getCalories();
            rows[3] = p.getProtein();
            rows[4] = p.getFat();
            rows[5] = p.getSodium();
            rows[6] = p.getPrice();
    }

    private static void updateProduct(List <Product> products) {


    }

}
