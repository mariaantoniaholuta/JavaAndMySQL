package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.*;
/**
 * This is the class used for GUI with home menu
 *
 * @author Holuta Maria
 *
 */
public class Homepage extends JFrame{
    private JPanel mainPanel;
    private JButton manageClientsButton;
    private JButton manageProductsButton;
    private JButton createOrderButton;
    private JLabel cinemaLabel;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JButton getManageClientsButton() {
        return manageClientsButton;
    }

    public JButton getManageProductsButton() {
        return manageProductsButton;
    }

    public JButton getCreateOrderButton() {
        return createOrderButton;
    }

    public JLabel getCinemaLabel() {
        return cinemaLabel;
    }

    private static final Color ulColor = new Color(26, 26, 26);
    private static final Color lrColor = new Color(55, 1, 0);

    JLayeredPane mainPanel2 = new JLayeredPane() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int x = getWidth();
            int y = getHeight();
            Graphics2D g2 = (Graphics2D) g;
            g2.setPaint(new GradientPaint(new Point(0, 0), ulColor,
                    new Point(x, y), lrColor, false));
            g2.fillRect(0, 0, x, y);
        }
    };

    public Homepage() {
        setContentPane(mainPanel2);
        setTitle("Home");
        Dimension dimensions = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(800, 500);
        int x = (int)((dimensions.getWidth()- this.getWidth())/2);
        int y = (int)((dimensions.getHeight()- this.getHeight())/2);
        this.setLocation(x,y);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        ImageIcon image = new ImageIcon("cinema.jpg");
        setIconImage(image.getImage());
/*
        ImageIcon image2 = new ImageIcon("cinema2.jpg");
        JLabel background = new JLabel( image2,JLabel.CENTER);
        background.setVisible(true);
        background.setBounds(0,0,800,600);
        add(background);
*/

        mainPanel2.setPreferredSize(new Dimension(400, 200));
        cinemaLabel.setBounds(200, 3, 1000, 200);
        getManageClientsButton().setBounds(250,150,300,40);
        getManageProductsButton().setBounds(250,200,300,40);
        getCreateOrderButton().setBounds(250,250,300,40);
        mainPanel2.add(cinemaLabel);
        mainPanel2.add(manageClientsButton, BorderLayout.CENTER);
        mainPanel2.add(manageProductsButton, BorderLayout.CENTER);
        mainPanel2.add(createOrderButton, BorderLayout.CENTER);

        manageClientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ManageClients clients = new ManageClients();
            }
        });

        manageProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ManageProducts products = new ManageProducts();
            }
        });

        createOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                CreateOrder create = new CreateOrder();
            }
        });

    }

}
