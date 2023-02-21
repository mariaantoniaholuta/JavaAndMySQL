package presentation;

import data_access.ClientDAO;
import data_access.ProductDAO;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * This is the class used for GUI with managing products
 *
 * @author Holuta Maria
 *
 */
public class ManageProducts extends JFrame {
    private JPanel productsPanel;
    private JButton goBackButton;
    private JTabbedPane tabbedPane1;
    private JScrollPane tablePanel;
    private JTable table;
    private JButton refreshButton;
    private JButton updateButton;
    private JTextField nameFld;
    private JTextField genreFld;
    private JTextField cantityFld;
    private JLabel nameLabel;
    private JLabel genreLabel;
    private JLabel cantityLabel;
    private JButton goBackButton1;
    private JButton addProductButton;
    private JButton refreshButton1;
    private JTextField productIdField;
    private JLabel productIdLabel;
    private JButton deleteButton;
    private JLabel priceLabel;
    private JTextField priceFld;

    public ManageProducts() {
        setContentPane(productsPanel);
        setTitle("orders");
        Dimension dimensions = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(950, 600);
        int x = (int) ((dimensions.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimensions.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        ImageIcon image = new ImageIcon("cinema.jpg");
        setIconImage(image.getImage());

        table.getTableHeader().setFont(new Font("Segoe UI Black", Font.BOLD, 18));
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(148,148,149));
        table.setModel(ProductDAO.makeTable());
        table.setRowHeight(60);
        table.getColumnModel().getColumn(0).setMaxWidth(35);
        table.getColumnModel().getColumn(1).setMinWidth(100);
        table.getColumnModel().getColumn(2).setMinWidth(100);
        table.getColumnModel().getColumn(3).setMaxWidth(100);
        table.setFillsViewportHeight(true);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);

        setVisible(true);

        tablePanel.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(82,66,62);
                this.trackColor = new Color(55,1,0);
            }
        });

        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Homepage();
            }
        });
        goBackButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Homepage();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ManageProducts();
            }
        });
        refreshButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ManageProducts();
            }
        });

        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductDAO productDAO = new ProductDAO(nameFld.getText(), genreFld.getText(), cantityFld.getText(), priceFld.getText());
                ProductDAO.addProduct();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
                ProductDAO.updateProduct(table, (String) table.getModel().getValueAt(table.getSelectedRow(), 0));
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductDAO.deleteProduct(productIdField.getText());
            }
        });

    }

}
