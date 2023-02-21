package com.utcn.Modul1;

import com.utcn.models.Angajat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Angajeaza extends JFrame {
    private JButton backBtn;
    private JTextField cnpField;
    private JTextField utilizatorField;
    private JTextField adresaField;
    private JTextField parolaField;
    private JTextField salariuField;
    private JTextField emailField;
    private JTextField numeField;
    private JLabel cnpLbl;
    private JLabel utilizatorLbl;
    private JLabel parolaLbl;
    private JLabel adresaLbl;
    private JLabel emailLbl;
    private JLabel numeLbl;
    private JLabel prenumeLbl;
    private JButton angajeazaBtn;
    private JPanel angajeazaPanel;
    private JTextField prenumeField;
    private JTextField telefonField;
    private JTextField ibanField;
    private JTextField dataAngajareField;
    private JTextField functieField;
    private JTextField numarOreField;

    public Angajeaza(Connection connection, Angajat angajat) {
        setContentPane(angajeazaPanel);
        setTitle("Welcome");
        setSize(650, 600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        ImageIcon image = new ImageIcon("logo1.png");
        setIconImage(image.getImage());


        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GestiuneResurseUmane(connection, angajat);
            }
        });

        angajeazaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cnp = "'" + cnpField.getText() + "'";
                String utilizator = "'" + utilizatorField.getText() + "'";
                String parola = "'" + parolaField.getText() + "'";
                String adresa = "'" +  adresaField.getText() + "'";
                String email = "'" + emailField.getText() + "'";
                String numar_ore = "'" + numarOreField.getText() + "'";
                String telefon = "'" + telefonField.getText() + "'";
                String iban = "'" + ibanField.getText() + "'";
                String data_angajare = "'" + dataAngajareField.getText() + "'";
                String nume = "'" + numeField.getText() + "'";
                String prenume = "'" + prenumeField.getText() + "'";
                String functie = "'" + functieField.getText() + "'";
                String salariu = "'" + salariuField.getText() + "'";

                try {
                    Statement statement = connection.createStatement();
                    String querry = "INSERT INTO angajat(cnp,utilizator,parola,adresa,email,nume,prenume," +
                                    "numar_ore,telefon,iban,data_angajare,functie,salariu_negociat) " +
                                    "VALUES (" + cnp + "," + utilizator + ","  + parola + "," + adresa + ","  + email + "," + nume  + "," +
                                     prenume + "," + numar_ore + "," + telefon + "," + iban + "," + data_angajare + "," + functie + "," + salariu + ")";
                    //System.out.println(querry);

                    statement.executeUpdate(querry);
                    JOptionPane.showMessageDialog(getParent(), "Angajat adaugat!");
                    if(functieField.getText().equals("medic") || functieField.getText().equals("asistent medical")) {
                        dispose();
                        new InsereazaMedicAsistent(connection, cnpField.getText(), functieField.getText(), angajat);
                    }
                }
                catch (SQLException ee) {
                    JOptionPane.showMessageDialog(getParent(), "Datele nu au putut fi adaugate!");
                    System.out.println("O picat facebook ul");
                }
            }

        });
    }

}
