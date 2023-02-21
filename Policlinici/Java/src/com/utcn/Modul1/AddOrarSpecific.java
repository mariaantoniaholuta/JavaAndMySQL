package com.utcn.Modul1;

import com.utcn.models.Angajat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddOrarSpecific extends JFrame{

    private JTextField cnpField;
    private JLabel cnpLbl;
    private JButton backBtn;
    private JButton addOrarBtn;
    private JLabel idPolLbl;
    private JLabel ora_inceputLbl;
    private JLabel dataLucruLbl;
    private JLabel ora_sfarsitLbl;
    private JTextField idField;
    private JTextField inceputField;
    private JTextField sfarsitField;
    private JTextField dataLucruField;
    private JPanel addOrarSpecificPane;

    public AddOrarSpecific (Connection connection, Angajat angajat, String cnp, String querry) {
        setContentPane(addOrarSpecificPane);
        setTitle("Welcome");
        setSize(650, 600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        ImageIcon image = new ImageIcon("logo1.png");
        setIconImage(image.getImage());

        cnpField.setText(cnp);

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new OrarSpecific(connection, angajat, cnp, querry);
            }
        });

        if(!angajat.getFunctie().equals("inspector resurse")) {
            addOrarBtn.setVisible(false);
        }

        addOrarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String cnp = cnpField.getText();
                int id_policlinica = Integer.parseInt(idField.getText());
                String ora_inceput = inceputField.getText();
                String ora_sfarsit = sfarsitField.getText();
                String data_lucru = dataLucruField.getText();

                try {
                    Statement statement = connection.createStatement();
                    String querry = "INSERT INTO orar_specific (cnp, id_policlinica, ora_inceput, ora_sfarsit, data_lucru) VALUES (?,?,?,?,?)";

                    PreparedStatement pr = connection.prepareStatement(querry);
                    pr.setString(1, cnp);
                    pr.setInt(2,  id_policlinica);
                    pr.setString(3, ora_inceput);
                    pr.setString(4, ora_sfarsit);
                    pr.setString(5, data_lucru);
                    System.out.println(pr);
                    pr.execute();

                    JOptionPane.showMessageDialog(getParent(), "Orar Adaugat!");

                }
                catch (SQLException ee) {
                    System.out.println("O picat facebook ul");
                }
            }

        });
    }
}