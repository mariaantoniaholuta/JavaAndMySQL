package com.utcn.Modul1;

import com.utcn.CommonInterfaces.Login;
import com.utcn.models.Angajat;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfigurareGrade extends JFrame{
    private JPanel mainPane;
    private JButton backButton;
    private JButton logOutButton;
    private JTextField cnpField;
    private JButton submitButton;
    private JTable table;
    private JComboBox comboGrad;
    private JTextField specialitateField;
    private JScrollPane tablePanel;

    public ConfigurareGrade(Connection connection, Angajat angajat) {
        setContentPane(mainPane);
        setTitle("Grade");
        setSize(1000, 500);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        ImageIcon image = new ImageIcon("logo1.png");
        setIconImage(image.getImage());

        tablePanel.getViewport().setBackground(new Color(36,69,74));
        tablePanel.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(76,80,82);
            }
        });

        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
        comboGrad.setRenderer(listRenderer);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GestiuneResurseUmane(connection, angajat);
            }
        });

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login(connection);
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PreparedStatement statement = connection.prepareStatement("INSERT INTO grad(cnp, grad, denumire_specialitate) " +
                            "VALUES (?, ?, ?)");

                    statement.setString(1, cnpField.getText());
                    statement.setString(2, (String) comboGrad.getSelectedItem());
                    statement.setString(3, specialitateField.getText());
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(getParent(), "Grad introdus cu succes!");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(getParent(), "Datele nu au putut fi introduse!");
                    ex.printStackTrace();
                }

                String[] column = {"Grad", "Specialitate"};
                DefaultTableModel dtm = new DefaultTableModel();
                for (int i = 0; i < 2; i++)
                    dtm.addColumn(column[i]);

                try {
                    PreparedStatement statement = connection.prepareStatement("SELECT grad, denumire_specialitate FROM Grad " +
                            "WHERE cnp = ?");
                    statement.setString(1, cnpField.getText());
                    ResultSet resultSet = statement.executeQuery();
                    Object[] rowData = new Object[2];
                    for (int i = 0; i < 2; i++)
                        rowData[i] = new Object();

                    while (resultSet.next()) {
                        rowData[0] = resultSet.getString("grad");
                        rowData[1] = resultSet.getString("denumire_specialitate");
                        dtm.addRow(rowData);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                setTableSettings(table, dtm);
            }
        });
    }

    public void setTableSettings(JTable table, DefaultTableModel dtm) {
        table.getTableHeader().setFont(new Font("Rockwell", Font.BOLD, 16));
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(166,201,181));
        table.setModel(dtm);
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);
        table.setEnabled(false);
    }
}
