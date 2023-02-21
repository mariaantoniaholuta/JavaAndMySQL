package presentation;

import data_access.ClientDAO;
import model.Client;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * This is the class used for GUI with managing clients
 *
 * @author Holuta Maria
 *
 */
public class ManageClients extends JFrame {

    private JPanel clientsPanel;
    private JButton goBackButton;
    private JTabbedPane tabbedPane1;
    private JScrollPane tablePanel;
    private JTable table;
    private JButton refreshButton;
    private JButton updateButton;
    private JTextField nameFld;
    private JLabel nameLabel;
    private JLabel emailLabel;
    private JLabel addressLabel;
    private JLabel ageLabel;
    private JTextField emailFld;
    private JTextField addressFld;
    private JTextField ageFld;
    private JButton deleteButton;
    private JLabel clientIdLabel;
    private JTextField clientIdField;
    private JButton goBackButton1;
    private JButton refreshButton1;
    private JButton addClientButton;


    public ManageClients() {
        setContentPane(clientsPanel);
        setTitle("clients");
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
        table.setModel(ClientDAO.makeTable());
        table.setRowHeight(60);
        table.getColumnModel().getColumn(0).setMaxWidth(35);
        table.getColumnModel().getColumn(2).setMinWidth(100);
        table.getColumnModel().getColumn(3).setMinWidth(100);
        table.getColumnModel().getColumn(4).setMaxWidth(60);
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
                new ManageClients();
            }
        });
        refreshButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ManageClients();
            }
        });

        addClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientDAO clientDAO = new ClientDAO(nameFld.getText(), emailFld.getText(), addressFld.getText(), Integer.parseInt(ageFld.getText()));
                ClientDAO.addClient();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
                ClientDAO.updateClient(table, (String) table.getModel().getValueAt(table.getSelectedRow(), 0));
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               ClientDAO.deleteClient(clientIdField.getText());
            }
        });

    }

}
