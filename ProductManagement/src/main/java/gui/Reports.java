package gui;

import bussinesslayer.DeliveryService;
import bussinesslayer.Order;
import datalayer.FileWriters;
import datalayer.Serializator;
import model.CompositeProduct;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reports extends JFrame{
    private JPanel mainPanel;
    private JTextField beginFld;
    private JButton goBackButton;
    private JButton searchIntervalButton;
    private JTextField endFld;
    private JButton searchProductsByOrderNumberButton;
    private JTextField nrField;
    private JButton searchClientsButton;
    private JTextField orderNrField;
    private JTextField payNrField;
    private JButton searchProductsFromDayButton;
    private JTextField dayField;
    private JLabel resultLabel;

    private List<CompositeProduct> compositeProducts1 = new ArrayList<>();

    public Reports() {
        setContentPane(mainPanel);
        setTitle("Admin");
        Dimension dimensions = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(800, 600);
        int x = (int) ((dimensions.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimensions.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AdministratorInterface();
            }
        });

        searchIntervalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Order> orders = new ArrayList<>();
                List<Order> ordersFound = new ArrayList<>();
                try {
                    orders = Serializator.readForOrders();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                ordersFound = DeliveryService.findOrders(orders, beginFld.getText(), endFld.getText());
                try {
                    FileWriters.writeOrdersFoundFromDate(ordersFound);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        searchProductsByOrderNumberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                List<Order> orders = new ArrayList<>();
                List<CompositeProduct> compositeProducts= new ArrayList<>();
                String result="";
                try {
                    orders = Serializator.readForOrders();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                result = DeliveryService.findProductsMoreThanX(orders, nrField.getText());
                resultLabel.setText("<html>" + result.replaceAll("\n", "<br/>") + "</html>");
                System.out.println(result);
            }
        });

        searchClientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Order> orders = new ArrayList<>();
                String result="";
                try {
                    orders = Serializator.readForOrders();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                result = DeliveryService.findClientsMoreThanX(orders, orderNrField.getText(), payNrField.getText());
                resultLabel.setText("<html>" + result.replaceAll("\n", "<br/>") + "</html>");
                System.out.println(result);
            }
        });

        searchProductsFromDayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Order> orders = new ArrayList<>();
                String result="";
                try {
                    orders = Serializator.readForOrders();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                result = DeliveryService.findProducstFromDay(orders, dayField.getText());
                System.out.println(result);
                resultLabel.setText("<html>" + result.replaceAll("\n", "<br/>") + "</html>");
            }
        });
    }
}
