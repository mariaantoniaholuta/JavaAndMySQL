package presentation;

import data_access.ClientDAO;
import data_access.OrderDAO;
import data_access.ProductDAO;
import model.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This is the class used for GUI with managing orders
 *
 * @author Holuta Maria
 *
 */

public class CreateOrder extends JFrame{
    private JPanel ordersPanel;
    private JScrollPane table1Panel;
    private JTable table1;
    private JScrollPane table2Panel;
    private JButton goBackButton;
    private JTextField clientIdField;
    private JButton createButton;
    private JTable table2;
    private JTextField productIdField;
    private JTextField cantityField;
    private JButton refreshButton;

    public CreateOrder() {
        setContentPane(ordersPanel);
        setTitle("orders");
        Dimension dimensions = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(1200, 600);
        int x = (int) ((dimensions.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimensions.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        ImageIcon image = new ImageIcon("cinema.jpg");
        setIconImage(image.getImage());

        table1.getTableHeader().setFont(new Font("Segoe UI Black", Font.BOLD, 18));
        table1.getTableHeader().setOpaque(false);
        table1.getTableHeader().setBackground(new Color(148,148,149));
        table1.setModel(ClientDAO.makeTable());
        table1.setRowHeight(60);
        table1.getColumnModel().getColumn(0).setMaxWidth(35);
        table1.getColumnModel().getColumn(2).setMinWidth(100);
        table1.getColumnModel().getColumn(3).setMinWidth(100);
        table1.getColumnModel().getColumn(4).setMaxWidth(60);
        table1.setFillsViewportHeight(true);
        table1.setShowHorizontalLines(true);
        table1.setShowVerticalLines(false);

        table2.getTableHeader().setFont(new Font("Segoe UI Black", Font.BOLD, 18));
        table2.getTableHeader().setOpaque(false);
        table2.getTableHeader().setBackground(new Color(148,148,149));
        table2.setModel(ProductDAO.makeTable());
        table2.setRowHeight(60);
        table2.getColumnModel().getColumn(0).setMaxWidth(35);
        table2.getColumnModel().getColumn(1).setMinWidth(100);
        table2.getColumnModel().getColumn(2).setMinWidth(100);
        table2.getColumnModel().getColumn(3).setMaxWidth(100);
        table2.setFillsViewportHeight(true);
        table2.setShowHorizontalLines(true);
        table2.setShowVerticalLines(false);

        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Homepage();
            }
        });

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderDAO orderDAO = new OrderDAO(clientIdField.getText(), productIdField.getText(), cantityField.getText());
                OrderDAO.createOrder();
                JOptionPane.showMessageDialog(getParent(), "Check the bill!");
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CreateOrder();
            }
        });

    }
}
