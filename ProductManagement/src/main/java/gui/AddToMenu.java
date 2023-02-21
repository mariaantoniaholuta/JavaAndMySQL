package gui;

import datalayer.FileWriters;
import datalayer.Serializator;
import model.CompositeProduct;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddToMenu extends JFrame {
    private JPanel firstPanel;
    private JTabbedPane tabbedPane1;
    private JButton goBackButton;
    private JButton refreshButton;
    private JPanel mainPanel;
    private JScrollPane tablePanel;
    private JTable table1;
    private JButton selectButton;
    private JLabel selectedLabel;
    private JButton addButton;
    private JLabel addLabel;
    private JTextField cantityFld;
    private JScrollPane tablePanel3;
    private JTable table3;
    private JButton toMenuButton;

    private List<Product> productsToAddToMenu = new ArrayList<>();
    private List<CompositeProduct> compositeProducts = new ArrayList<>();
    private List<CompositeProduct> compositeProducts1 = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private String str = "";
    private String productName = "product ";
    private int id;

    public AddToMenu () {
        setContentPane(tabbedPane1);
        setTitle("Admin");
        Dimension dimensions = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(1200, 700);
        int x = (int) ((dimensions.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimensions.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        addLabel.setVisible(false);
        id++;

        //  List<Product> products = new ArrayList<>();
        try {
            products = Serializator.readForProducts();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        DefaultTableModel dm = new DefaultTableModel();
        DefaultTableModel dm1 = new DefaultTableModel();
        Product p = products.get(0);
        String[] column = {p.getTitle(), p.getRating(), p.getCalories(), p.getProtein(), p.getFat(), p.getSodium(), p.getPrice()};
        for (int i = 0; i <= 6; i++) {
            dm.addColumn(column[i]);
        }
        Object[] rows = new Object[7];
        for (int i = 0; i <= 6; i++)
            rows[i] = new Object();

        int count = 0;
        for (Product pr : products) {
            count++;
        }
        for (int i = 1; i < count; i++) {
            addRowsFromData(rows, products.get(i));
            dm.addRow(rows);
        }

        table1.getTableHeader().setFont(new Font("Segoe UI Black", Font.BOLD, 18));
        table1.getTableHeader().setOpaque(false);
        table1.getTableHeader().setBackground(new Color(128, 129, 143));
        table1.setModel(dm);
        table1.setRowHeight(40);
        table1.setFillsViewportHeight(true);
        table1.setShowHorizontalLines(true);
        table1.setShowVerticalLines(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table1.setDefaultRenderer(String.class, centerRenderer);
        for (int i = 0; i < 7; i++) {
            table1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }


        try {
            compositeProducts1 = Serializator.readForCompositeProducts();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String[] column1 = {"Name", "Ingredients", "Cantity", "Price"};
        for (int i = 0; i < 4; i++) {
            dm1.addColumn(column1[i]);
        }
        Object[] rows1 = new Object[4];
        for (int i = 0; i < 4; i++)
            rows1[i] = new Object();

        int count1 = 0;
        for (CompositeProduct c : compositeProducts1) {
            count1++;
            addRowsFromDataComposed(rows1, c);
            dm1.addRow(rows1);
        }

        table3.getTableHeader().setFont(new Font("Segoe UI Black", Font.BOLD, 18));
        table3.getTableHeader().setOpaque(false);
        table3.getTableHeader().setBackground(new Color(128, 129, 143));
        table3.setModel(dm1);
        table3.setRowHeight(40);
        table3.setFillsViewportHeight(true);
        table3.setShowHorizontalLines(true);
        table3.setShowVerticalLines(false);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table3.setDefaultRenderer(String.class, centerRenderer);
        for (int i = 0; i < 4; i++) {
            table3.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AdministratorInterface();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AddToMenu();

            }
        });

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addLabel.setVisible(true);
                if (table1.isEditing()) {
                    table1.getCellEditor().stopCellEditing();
                }
                 str += table1.getModel().getValueAt(table1.getSelectedRow(), 0)+"\n";

                selectedLabel.setText("<html>" + str.replaceAll("\n", "<br/>") + "</html>");
                String title = String.valueOf(table1.getModel().getValueAt(table1.getSelectedRow(), 0));
                String rating = String.valueOf(table1.getModel().getValueAt(table1.getSelectedRow(), 1));
                String calories = String.valueOf(table1.getModel().getValueAt(table1.getSelectedRow(), 2));
                String protein = String.valueOf(table1.getModel().getValueAt(table1.getSelectedRow(), 3));
                String fat = String.valueOf(table1.getModel().getValueAt(table1.getSelectedRow(), 4));
                String sodium = String.valueOf(table1.getModel().getValueAt(table1.getSelectedRow(), 5));
                String price = String.valueOf(table1.getModel().getValueAt(table1.getSelectedRow(), 6));

                Product product = new Product(title,rating,calories,protein,fat,sodium,price);
                productsToAddToMenu.add(product);

            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

               // productName += String.valueOf(id);
                Random r = new Random();
                productName += String.valueOf(r.nextInt(100));
                id++;
                CompositeProduct compositeProduct = new CompositeProduct(productName,0,Integer.parseInt(cantityFld.getText()),productsToAddToMenu);
                compositeProduct.setPrice(compositeProduct.computePrice());
                compositeProduct.setIngredients1();

                compositeProducts.add(compositeProduct);
                System.out.println(compositeProduct.getIngredients());
                CompositeProduct compositeProduct1 = new CompositeProduct(productName, null,compositeProduct.getPrice(),Integer.parseInt(cantityFld.getText()));
                compositeProduct1.setIngredients2(compositeProduct.getIngredients());

                FileWriters.writeNewComposedProduct(compositeProduct1);
                JOptionPane.showMessageDialog(getParent(), "new products added to the menu!");
            }
        });

    }

    private static void addRowsFromDataComposed(Object[] rows, CompositeProduct c) {
        rows[0] = c.getName();
        rows[1] = c.getIngredients();
        rows[2] = c.getCantity();
        rows[3] = c.getPrice();
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
}
