package com.utcn.Modul2;


import com.utcn.models.Angajat;
import com.utcn.CommonInterfaces.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class EmitereSalariu extends JFrame{
    private JButton backButton;
    private JButton logOutButton;
    private JTextField cnpTextField;
    private JTextField dataEmitere;
    private JButton submitButton;
    private JPanel firstPane;
    private JTextField numeField;
    private JTextField prenumeField;
    private JTextField salariuField;
    private JButton emiteSalariuButton;
    private JTextField oreField;
    private JLabel nume;
    private JLabel prenume;
    private JLabel ore;
    private JLabel salariu;
    private JTextField consultatiiField;
    private JLabel numarConsultatii;

    public EmitereSalariu(Connection connection, Angajat angajat) {
        setContentPane(firstPane);
        setTitle("Emitere");
        setSize(1000, 800);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setVisibleFields(false);
        setVisibleMedicFields(false);

        dataEmitere.setText(String.valueOf(java.time.LocalDate.now()));
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

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkFunction(connection).equals("medic")) {
                    calculateSalaryForMedic(connection);
                }
                else {
                    try {
                        PreparedStatement statement = connection.prepareStatement("SELECT nume, prenume, salariu_negociat, numar_ore FROM angajat " +
                                "WHERE cnp = ?");

                        statement.setString(1, cnpTextField.getText());
                        ResultSet resultSet = statement.executeQuery();

                        if (resultSet.next()) { /*** poate nu avem un angajat cu cnp ul asta**/
                            Integer oreContract = resultSet.getInt("numar_ore"), oreEfectuate = 0;
                            Double salariuContract = resultSet.getDouble("salariu_negociat");
                            String nume = resultSet.getString("nume");
                            String prenume = resultSet.getString("prenume");
                            resultSet.close();

                            statement = connection.prepareStatement("SELECT GET_NUMAR_ORE(?, ?) AS ore");
                            statement.setString(1, dataEmitere.getText());
                            statement.setString(2, cnpTextField.getText());
                            ResultSet ore_efectuate = statement.executeQuery();
                            ore_efectuate.next();
                            oreEfectuate = ore_efectuate.getInt("ore");
                            salariuContract = salariuContract / oreContract * oreEfectuate;

                            salariuField.setText(String.valueOf(salariuContract));
                            salariuField.setEditable(false);
                            oreField.setText(String.valueOf(oreEfectuate));
                            oreField.setEditable(false);
                            numeField.setText(nume);
                            numeField.setEditable(false);
                            prenumeField.setText(prenume);
                            prenumeField.setEditable(false);

                            setVisibleFields(true);
                        } else {
                            JOptionPane.showMessageDialog(getParent(), "CNP invalid sau angajat fără un orar alocat!");
                        }
                    } catch (SQLException ee) {
                        ee.printStackTrace();
                        JOptionPane.showMessageDialog(getParent(), "Datele nu au putut fi actualizate!");
                        System.out.println("O picat facebook ul");
                    }
                }
            }
        });

        emiteSalariuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cnp = "'" + cnpTextField.getText() + "'";
                String salariu = "'" + salariuField.getText() + "'";
                String data = "'" + dataEmitere.getText() + "'";
                try {
                    Statement statement = connection.createStatement();
                    statement.executeUpdate("insert into salariu(cnp, salariu, data_incasare) " +
                            "VALUES ( " + cnp + ", " + salariu + ", " + data + ")");
                    JOptionPane.showMessageDialog(getParent(), "Salariu emis cu succes!");
                }
                catch (SQLException ee) {
                    ee.printStackTrace();
                    JOptionPane.showMessageDialog(getParent(), "Datele nu au putut fi introduse");
                    System.out.println("O picat facebook ul");
                }
            }
        });
    }

    private void calculateSalaryForMedic(Connection connection) {
        try {
            String space = " ";
            PreparedStatement statement = connection.prepareStatement("SELECT cnp_medic, nume, prenume, COUNT(*) AS numar_consultatii, ((SUM(cost_fara_comision) + SUM(comision_medic)) * 80) / 100 AS salariu, luna_an " +
                    "FROM raport_final_medic " +
                    "GROUP BY luna_an " +
                    "HAVING CONCAT(MONTHNAME(?), ?, YEAR(?)) = luna_an AND cnp_medic = ?");
            statement.setString(1, dataEmitere.getText());
            statement.setString(2, space);
            statement.setString(3, dataEmitere.getText());
            statement.setString(4, cnpTextField.getText());
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                setVisibleMedicFields(true);
                numeField.setText(resultSet.getString("nume"));
                prenumeField.setText(resultSet.getString("prenume"));
                consultatiiField.setText(resultSet.getString("numar_consultatii"));
                salariuField.setText(String.valueOf(resultSet.getFloat("salariu")));
                numeField.setEditable(false);
                prenumeField.setEditable(false);
                consultatiiField.setEditable(false);
            }
            else {
                JOptionPane.showMessageDialog(getParent(), "Angajatul nu a facut absolut nimic toata luna!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setVisibleFields(boolean b) {
        nume.setVisible(b);
        prenume.setVisible(b);
        salariu.setVisible(b);
        ore.setVisible(b);
        numeField.setVisible(b);
        prenumeField.setVisible(b);
        oreField.setVisible(b);
        salariuField.setVisible(b);
        emiteSalariuButton.setVisible(b);
    }

    public void setVisibleMedicFields(boolean b) {
        nume.setVisible(b);
        prenume.setVisible(b);
        numeField.setVisible(b);
        prenumeField.setVisible(b);
        consultatiiField.setVisible(b);
        numarConsultatii.setVisible(b);
        salariu.setVisible(b);
        salariuField.setVisible(b);
        emiteSalariuButton.setVisible(b);
    }

    public String checkFunction(Connection connection) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT functie FROM angajat WHERE cnp = ?");
            statement.setString(1, cnpTextField.getText());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getString("functie");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(getParent(), "CNP invalid!");
            e.printStackTrace();
        }
        return  "bla";
    }
}
