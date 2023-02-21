package com.utcn.Bonus;

import com.utcn.CommonInterfaces.Login;
import com.utcn.models.Angajat;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepartizareMedici extends JFrame{
    private JPanel mainPane;
    private JButton backButton;
    private JButton logOutButton;
    private JTextField programareField;
    private JButton submitButton;
    private JTable table;
    private JScrollPane scrollPane;
    private JLabel disponibile;

    public RepartizareMedici(Connection connection, Angajat angajat) {
        setContentPane(mainPane);
        setTitle("Repartizare");
        setSize(1000, 1000);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ImageIcon image = new ImageIcon("logo1.png");
        setIconImage(image.getImage());
        scrollPane.getViewport().setBackground(new Color(36,69,74));
        setVisible(true);
        disponibile.setVisible(false);

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login(connection);
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PreparedStatement statement = connection.prepareStatement("SELECT id_serviciu FROM " +
                            " lista_servicii_client_restrictie_echipamente WHERE id_programare = ?");
                    statement.setString(1, programareField.getText());
                    ResultSet serviciiClient = statement.executeQuery();

                    List<Integer> listaClient= new ArrayList<>();
                    while (serviciiClient.next()) {
                        listaClient.add(serviciiClient.getInt("id_serviciu"));
                        System.out.print(serviciiClient.getInt("id_serviciu") + " ");
                    }
                    System.out.println();
                    serviciiClient.close();

                    int id_policlinica = 5;
                    statement = connection.prepareStatement("SELECT id_cabinet, id_serviciu FROM lista_servicii_cabinet " +
                            "WHERE id_policlinica = ? ORDER BY id_cabinet");
                    statement.setString(1, String.valueOf(id_policlinica));
                    ResultSet serviciiCabinet = statement.executeQuery();

                    List<Pair> listaCabinet = new ArrayList<>();
                    while (serviciiCabinet.next()) {
                        Pair value = new Pair();
                        value.x = serviciiCabinet.getInt("id_cabinet");
                        value.y = serviciiCabinet.getInt("id_serviciu");
                        listaCabinet.add(value);
                        System.out.print("{" + serviciiCabinet.getInt("id_cabinet") + ", " +  serviciiCabinet.getInt("id_serviciu") + "} ");
                    }
                    System.out.println();

                    List<Integer> listaCabineteDisponibile = new ArrayList<>();
                    for(int i = 0; i < listaCabinet.size(); ++i) {
                        Pair p = listaCabinet.get(i);
                        int numberOfMatches = 0;
                        while(i < listaCabinet.size() && p.x == listaCabinet.get(i).x) {
                            Integer s = listaCabinet.get(i).y;
                            if(listaClient.contains(s))
                                numberOfMatches++;
                            ++i;
                        }
                        if(numberOfMatches == listaClient.size())
                            listaCabineteDisponibile.add(p.x);
                        --i;
                    }

                    DefaultTableModel dtm = new DefaultTableModel();
                    dtm.addColumn("Id cabinet disponibil");
                    for (Integer i: listaCabineteDisponibile) {
                        System.out.println(i);
                        dtm.addRow(new Object[]{i});
                    }
                    disponibile.setVisible(true);
                    table.getTableHeader().setFont(new Font("Rockwell", Font.BOLD, 16));
                    table.getTableHeader().setOpaque(false);
                    table.getTableHeader().setBackground(new Color(166,201,181));
                    table.setModel(dtm);
                    table.setRowHeight(30);
                    table.setFillsViewportHeight(true);
                    table.setEnabled(false);
                    DefaultTableCellRenderer rendar = new DefaultTableCellRenderer();
                    rendar.setHorizontalAlignment(JLabel.CENTER);
                    table.getColumnModel().getColumn(0).setCellRenderer(rendar);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

}
