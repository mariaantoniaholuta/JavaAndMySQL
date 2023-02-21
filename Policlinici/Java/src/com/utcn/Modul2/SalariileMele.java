package com.utcn.Modul2;

import com.utcn.models.Angajat;
import com.utcn.CommonInterfaces.Homepage;
import com.utcn.CommonInterfaces.Login;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SalariileMele extends JFrame {

    private JPanel salariileMelePanel;
    private JButton logOutBtn;
    private JButton backBtn;
    private JTable table;
    private JTable profitTable;
    private JScrollPane profitPanel;
    private JButton veziProfitButton;
    private JLabel dataLbl1;
    private JLabel dataLbl2;
    private JLabel dataLbl3;
    private JLabel dataLbl4;
    private JLabel dataLbl5;

    public SalariileMele(Connection connection, Angajat angajat) {
        setContentPane(salariileMelePanel);
        setTitle("salarii");
        setSize(850, 500);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        profitPanel.setVisible(false);

        if(angajat.getFunctie().equals("medic")) {
            createCustomTable(connection, angajat);
        }

        ImageIcon image = new ImageIcon("logo1.png");
        setIconImage(image.getImage());

        profitPanel.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(76,80,82);
            }
        });

        String[] column = {"Salariu", "Data incasare"};
        DefaultTableModel dtm = new DefaultTableModel();
        for (int i = 0; i < 2; i++)
            dtm.addColumn(column[i]);

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Salariu where cnp = ?");
            statement.setString(1, angajat.getCnp());
            ResultSet resultSet = statement.executeQuery();

            Object[] rowData = new Object[10];
            for (int i = 0; i < 2; i++)
                rowData[i] = new Object();

            while (resultSet.next()) {
                rowData[0] = resultSet.getString("salariu") + " €";
                rowData[1] = resultSet.getString("data_incasare");
                dtm.addRow(rowData);
            }
        } catch (SQLException ee) {
                System.out.println("O picat facebook ul");
        }


        logOutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login(connection);
            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                if(!angajat.getFunctie().equals("expert financiar"))
                    new Homepage(connection, angajat);
                else
                    new OperatiiFinanciarContabile(connection, angajat);
            }
        });
        setTableSettings(table, dtm);
    }

    public void setTableSettings(JTable table, DefaultTableModel dtm) {
        table.getTableHeader().setFont(new Font("Rockwell", Font.BOLD, 18));
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(166,201,181));
        table.setModel(dtm);
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);
        table.setEnabled(false);
    }

    public void createCustomTable(Connection connection, Angajat angajat) {
        String[] column = {"Salariu","Venit policlinca fara comisioane", "Total comisioane", "Profit generat",
                "Luna/An"};
        DefaultTableModel dtm = new DefaultTableModel();
        for (int i = 0; i < 5; i++)
            dtm.addColumn(column[i]);

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT sl.salariu,cl.cost_luna AS castig_fara_comision, cl.comisioane_totale, (cl.cost_luna - sl.salariu) AS profit, cl.luna_an " +
                    "FROM salarii_luna AS sl, " +
                    "(SELECT cnp_medic, luna_an, SUM(cost_fara_comision) AS cost_luna, SUM(comision_medic) AS comisioane_totale, data_consultatie " +
                    "FROM raport_medic " +
                    "GROUP BY luna_an) AS cl " +
                    "WHERE cl.cnp_medic = sl.cnp AND cl.luna_an = sl.luna_an AND cl.cnp_medic = ?" +
                    "ORDER BY cl.data_consultatie;");
            statement.setString(1, angajat.getCnp());
            ResultSet resultSet = statement.executeQuery();

            Object[] rowData = new Object[10];
            for (int i = 0; i < 5; i++)
                rowData[i] = new Object();

            while (resultSet.next()) {
                rowData[0] = resultSet.getString("salariu") + " €";
                rowData[1] = resultSet.getString("castig_fara_comision") +" €";
                rowData[2] = resultSet.getString("comisioane_totale") +" €";
                rowData[3] = resultSet.getString("profit") + " €";
                rowData[4] = resultSet.getString("luna_an");
                dtm.addRow(rowData);
            }
        } catch (SQLException ee) {
            System.out.println("O picat facebook ul");
        }

        setTableSettings(profitTable, dtm);
        profitPanel.setVisible(true);
    }
}
