package gui;

import bussinesslayer.DeliveryService;
import bussinesslayer.MenuItem;
import bussinesslayer.Order;
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
import java.util.*;
import java.util.List;

public class ClientInterface extends JFrame {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JScrollPane tablePanel1;
    private JTable table1;
    private JButton goBackButton;
    private JButton refreshButton;
    private JTextField caloriesFld;
    private JTextField proteinFld;
    private JButton goBackButton1;
    private JButton searchButton;
    private JButton refreshButton1;
    private JTextField ratingFld;
    private JTextField nameFld;
    private JTextField fatField;
    private JTextField sodiumField;
    private JTextField priceField;
    private JScrollPane tablePanel2;
    private JTable table2;
    private JLabel logLbl;
    private JScrollPane tablePanel3;
    private JTable table3;
    private JButton selectButton;
    private JLabel selectedLabel;
    private JLabel addLabel;
    private JButton addButton;

    private java.util.List<CompositeProduct> compositeProducts1 = new ArrayList<>();
    private List<CompositeProduct> productsToAddToOrder = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private List<Product> productsFound = new ArrayList<>();
    private HashMap<Order, MenuItem> orders = new HashMap<>();
    private DeliveryService deliveryService = new DeliveryService(orders);
    private String str = "";

    public ClientInterface() {
        setContentPane(tabbedPane1);
        setTitle("LogIn");
        Dimension dimensions = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(1200, 800);
        int x = (int) ((dimensions.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimensions.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        tablePanel3.setVisible(false);

        try {
            products = Serializator.readForProducts();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        DefaultTableModel dm = new DefaultTableModel();
        DefaultTableModel dm1 = new DefaultTableModel();
        DefaultTableModel dm2 = new DefaultTableModel();
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
            DeliveryService.addRowsFromData(rows, products.get(i));
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
            DeliveryService.addRowsFromDataComposed(rows1, c);
            dm1.addRow(rows1);
        }

        table2.getTableHeader().setFont(new Font("Segoe UI Black", Font.BOLD, 18));
        table2.getTableHeader().setOpaque(false);
        table2.getTableHeader().setBackground(new Color(128, 129, 143));
        table2.setModel(dm1);
        table2.setRowHeight(40);
        table2.setFillsViewportHeight(true);
        table2.setShowHorizontalLines(true);
        table2.setShowVerticalLines(false);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table2.setDefaultRenderer(String.class, centerRenderer);
        for (int i = 0; i < 4; i++) {
            table2.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
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
                new ClientInterface();
            }
        });

        goBackButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LogIn();
            }
        });

        refreshButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ClientInterface();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tablePanel3.setVisible(true);

                productsFound = DeliveryService.findProducts(products, nameFld.getText(),ratingFld.getText(),caloriesFld.getText(),
                                                             proteinFld.getText(),fatField.getText(),sodiumField.getText(),priceField.getText());

                Product p = productsFound.get(0);
                String[] column = {p.getTitle(), p.getRating(), p.getCalories(), p.getProtein(), p.getFat(), p.getSodium(), p.getPrice()};
                for (int i = 0; i <= 6; i++) {
                    dm2.addColumn(column[i]);
                }
                Object[] rows = new Object[7];
                for (int i = 0; i <= 6; i++)
                    rows[i] = new Object();

                int count1 = 0;
                for (Product pr : productsFound) {
                    count1++;
                }
                for (int i = 1; i < count1; i++) {
                    DeliveryService.addRowsFromData(rows, productsFound.get(i));
                    dm2.addRow(rows);
                }

                table3.getTableHeader().setFont(new Font("Segoe UI Black", Font.BOLD, 18));
                table3.getTableHeader().setOpaque(false);
                table3.getTableHeader().setBackground(new Color(128, 129, 143));
                table3.setModel(dm2);
                table3.setRowHeight(40);
                table3.setFillsViewportHeight(true);
                table3.setShowHorizontalLines(true);
                table3.setShowVerticalLines(false);
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                table3.setDefaultRenderer(String.class, centerRenderer);
                for (int i = 0; i < 7; i++) {
                    table3.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }

            }
        });

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addLabel.setVisible(true);
                if (table2.isEditing()) {
                    table2.getCellEditor().stopCellEditing();
                }
                str += table2.getModel().getValueAt(table2.getSelectedRow(), 0)+"\n";

                selectedLabel.setText("<html>" + str.replaceAll("\n", "<br/>") + "</html>");
                String title = String.valueOf(table2.getModel().getValueAt(table2.getSelectedRow(), 0));
                String ingredients = String.valueOf(table2.getModel().getValueAt(table2.getSelectedRow(), 1));
                String price = String.valueOf(table2.getModel().getValueAt(table2.getSelectedRow(), 3));
                String cantity = String.valueOf(table2.getModel().getValueAt(table2.getSelectedRow(), 2));


                CompositeProduct compositeProduct = new CompositeProduct(title,ingredients,Float.parseFloat(price),Integer.parseInt(cantity));
                productsToAddToOrder.add(compositeProduct);
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // productName += String.valueOf(id);
                Random r = new Random();
                Order newOrder = new Order(r.nextInt(200));
                newOrder.setClientID(r.nextInt(300));

                MenuItem menuItem = new MenuItem(productsToAddToOrder);
                orders.put(newOrder,menuItem);

                deliveryService.setOrders(orders);

                FileWriters.writeNewOrder(newOrder, menuItem);

                JOptionPane.showMessageDialog(getParent(), "new order added!");
                Employee employee = new Employee();
                deliveryService.addObserver(employee);
                deliveryService.notifyEmployee();
            }
        });
    }
}
