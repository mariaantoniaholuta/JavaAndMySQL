package gui;

import bussinesslayer.DeliveryService;
import bussinesslayer.Order;
import datalayer.Serializator;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

public class EmployeeInterface extends JFrame {
    private JPanel mainPanel;
    private JButton goBackButton;
    private JScrollPane tablePanel;
    private JTable table;
    private JLabel logLbl;

    private java.util.List<Order> orders = new ArrayList<>();

    public EmployeeInterface() {
        setContentPane(mainPanel);
        setTitle("LogIn");
        Dimension dimensions = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(1200, 500);
        int x = (int) ((dimensions.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimensions.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        try {
            orders = Serializator.readForOrders();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        DefaultTableModel dm = new DefaultTableModel();

        String[] column = {"OrderID", "ClientID", "Products", "Date"};

        for (int i = 0; i <4; i++) {
            dm.addColumn(column[i]);
        }
        Object[] rows = new Object[4];
        for (int i = 0; i < 4 ; i++)
            rows[i] = new Object();

        for(Order o : orders) {
            DeliveryService.addRowsFromDataOrders(rows, o);
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
        for(int i=0;i<4;i++){
            table.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
        }
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LogIn();
            }
        });
    }

    public void update(Observable obj, Object arg) { }
}
