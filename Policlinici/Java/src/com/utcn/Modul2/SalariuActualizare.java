package com.utcn.Modul2;

import com.utcn.models.Angajat;
import com.utcn.CommonInterfaces.Login;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SalariuActualizare extends JFrame{
    private JButton backButton;
    private JButton logOutButton;
    private JTextField cnpField;
    private JTextField numeField;
    private JTextField prenumeField;
    private JTextField salariuField;
    private JButton submitButton;
    private JPanel mainPane;
    private JLabel cnp;
    private JLabel nume;
    private JLabel prenume;
    private JLabel salariu;

    public SalariuActualizare(Connection connection, Angajat angajat) {
        setContentPane(mainPane);
        setTitle("Actualizare salariu");
        setSize(1000,800);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!checkFields()) {
                    JOptionPane.showMessageDialog(getParent(), "Completati toate campurile!");
                }
                else {
                    int input = JOptionPane.showConfirmDialog(getParent(), "Sunteti sigur?");
                    if(input == 0) {
                        try {
                            Statement statement = connection.createStatement();
                            String cnpAngajat = "'" + cnpField.getText() + "'";
                            String salariuNou = "'" + salariuField.getText() + "'";

                            ResultSet resultSet = statement.executeQuery("SELECT * FROM Angajat where cnp = " + cnpAngajat);
                            if(resultSet.next()) {
                                String update = "UPDATE angajat SET salariu_negociat =" + salariuNou + " where cnp = " + cnpAngajat;
                                statement.executeUpdate(update);
                                JOptionPane.showMessageDialog(getParent(), "Modificare realizata cu succes!");
                            }
                            else {
                                JOptionPane.showMessageDialog(getParent(), "Datele nu au putut fi actualizate!");
                            }
                        }
                        catch (SQLException ee) {
                            System.out.println("O picat facebook ul");
                        }
                    }
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new OperatiiFinanciarContabile(connection, angajat);
            }
        });

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login(connection);
            }
        });
    }

    public boolean checkFields() {
        if(numeField.getText().equals("") || prenumeField.getText().equals("") || cnpField.getText().equals("") || salariuField.getText().equals(""))
            return  false;
        return true;
    }
}
