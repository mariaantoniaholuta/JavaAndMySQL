package com.utcn.Modulul3;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.utcn.models.*;
import com.utcn.CommonInterfaces.*;

public class OperationaleMedic extends JFrame{
    private JPanel activitatiOperationaleMedic;
    private JButton logOutButton;
    private JButton backButton;
    private JButton consultatiileMeleButton;
    private JButton istoricPacientButton;
    private JPanel Consultatii;
    private JButton inapoiButton;
    private JButton veziServiciiButton;
    private JTable table1;
    private JPanel mainPanel;
    private JScrollPane tabelConsult;
    private JButton completeazaRaportButton1;
    private JPanel Servicii;
    private JComboBox comboServicii;
    private JButton adaugaServiciuButton;
    private JButton eliminaServiciuButton;
    private JTable table2;
    private JLabel lb_Id_programare;
    private JLabel lb_pacient;
    private JButton inapoiButton1;
    private JPanel Raport;
    private JTextField textField1a;
    private JTextField textField2a;
    private JTextField textField11a;
    private JTextField textField12a;
    private JTextField textField9a;
    private JTextField textField10a;
    private JTextField textField4a;
    private JTextField textField3a;
    private JTextField textField8a;
    private JTextField textField7a;
    private JTextField textField6a;
    private JTextField textField5a;
    private JButton verificaAnalizeButton;
    private JButton inapoiButton2;
    private JButton parafeazaButton;
    private JButton updateSAUCreateButton;
    private JPanel Analize;
    private JButton verificaInvestigatiiButton1;
    private JPanel Investigatii;
    private JTextArea textArea1;
    private JLabel pacLb;
    private JLabel idLb;
    private JButton updateButton;
    private JLabel servLbLabel;
    private JButton inapoiButton3;
    private JPanel istoric;
    private JTextField textField1;
    private JButton inapoiButton4;
    private JButton veziProgramarileButton;
    private JTextField textField13a;
    private JTextField validareTf;
    private JTextArea textArea2;
    private JTextField idProgTf;
    private JTextField analizaTf;
    private JButton inapoiButton5;
    private static List<Servicii_Medicale> lista_servicii=new ArrayList<>();
    private static boolean faceUpdate;
    private static Integer intIDserviciuInvest;

    void populareConsultatii(Connection connection, int mod, String cnpConsultat){
        ///populeaz tabela din formul Consultatii
        ///mod 0: urmatoarele consultatii ale medicului avand cnp cnpConsultat + consultatiile pt care nu s-a emis bon in ultimele 30 zile
        ///mod 1: consultatiile pacientului cu cnp like cnpConsultat
        String sql;
        if(mod==0)
            sql="SELECT id_policlinica, nume, prenume, ora, ora_sfarsit, data_consultatie, id_programare\n" +
                    "from programare join pacient p on programare.cnp_pacient = p.cnp\n" +
                    "where cnp_medic=? and nrBon is null and data_consultatie>DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 30 DAY)" +
                    "ORDER BY data_consultatie";
        else
            sql="SELECT id_policlinica, nume, prenume, ora, ora_sfarsit, data_consultatie, id_programare\n" +
                    "from programare join pacient p on programare.cnp_pacient = p.cnp\n" +
                    "where cnp_pacient like ?\n" +
                    "ORDER BY data_consultatie";
            String[] column = {"id_policlinica", "Nume", "Prenume", "Ora inceput", "Ora sfarsit", "Data", "id_programare"};
            DefaultTableModel dtm = new DefaultTableModel();
            for (int i = 0; i < 7; i++)/***trăbă adăugate pe rând, ayaye (dacă ești smecher, caută tu o metodă să ți le bage deodată*/
                dtm.addColumn(column[i]);
            try {
                PreparedStatement stm= connection.prepareStatement(sql);
                stm.setString(1, cnpConsultat);
                ResultSet resultSet=stm.executeQuery();
                Object[] rowData = new Object[7];
                while(resultSet.next()){
                    rowData[0]=resultSet.getInt(1);
                    rowData[1]=resultSet.getString(2);
                    rowData[2]=resultSet.getString(3);
                    rowData[3]=resultSet.getTime(4);
                    rowData[4]=resultSet.getTime(5);
                    rowData[5]=resultSet.getDate(6);
                    rowData[6]=resultSet.getInt(7);
                    dtm.addRow(rowData);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        table1.getTableHeader().setFont(new Font("Rockwell", Font.BOLD, 18));
        table1.getTableHeader().setOpaque(false);
        table1.getTableHeader().setBackground(new Color(166,201,181));
        table1.setModel(dtm);
        table1.setRowHeight(30);
        table1.setFillsViewportHeight(true);
    }

    void populareServicii( Connection connection){
        ///populeaza tabela cu servicii din formul Servicii
        eliminaServiciuButton.setEnabled(false);///         initial nici un serviciu nu e selectat
        verificaInvestigatiiButton1.setEnabled(false);///   asa ca nu se poate elimina sau verifica serviciul.
        ///                                                 cand se da click pe un rand se vor activa
        Integer id_progINT=Integer.parseInt(lb_Id_programare.getText());/// idul programarii in care cautam serviciile
        String sql="Select denumire_serviciu, lsc.id_serviciu\n" +
                        "from servicii_medicale join lista_servicii_client lsc on servicii_medicale.id_serviciu = lsc.id_serviciu\n" +
                        "where id_programare=?";
        String[] column = {"Serviciu", "Denumire"};
        DefaultTableModel dtm = new DefaultTableModel();
        for (int i = 0; i < 2; i++)/***trăbă adăugate pe rând, ayaye (dacă ești smecher, caută tu o metodă să ți le bage deodată*/
            dtm.addColumn(column[i]);
        try {
            PreparedStatement stm= connection.prepareStatement(sql);
            stm.setInt(1, id_progINT);
            ResultSet resultSet=stm.executeQuery();
            String[] rowData = new String[2];
            while(resultSet.next()){
                rowData[0]=String.valueOf(resultSet.getInt(2));
                rowData[1]=resultSet.getString(1);
                dtm.addRow(rowData);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        table2.setModel(dtm);
        table2.getTableHeader().setFont(new Font("Rockwell", Font.BOLD, 18));
        table2.getTableHeader().setOpaque(false);
        table2.getTableHeader().setBackground(new Color(166,201,181));
        table2.setRowHeight(30);
        table2.setFillsViewportHeight(true);
    }

    void prepare_list(Connection connection){///    pune in comboBox serviciile (pe care le-ar putea adauga in programare)
        lista_servicii.clear();///                  le pune si intr-o lista ca sa stiu ce a selectat utilizatorul
        comboServicii.removeAllItems();
        String sql="Select * from servicii_medicale";/// de modificat sa fie doar serviciile pe care le poate presta medicul
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet resultSet=stm.executeQuery();
            while (resultSet.next()){
                Servicii_Medicale ser=new Servicii_Medicale(resultSet);
                lista_servicii.add(ser);
                comboServicii.addItem(ser.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    boolean populareFielduri(Connection connection, int idProg){/// true daca exista in tabela rapoarte//true daca e un update, false daca e un insert
        /// populeaza textfieldurile din formul raport
        String sql="Select * from raport_medical where id_programare =?";
        try {
            PreparedStatement stm= connection.prepareStatement(sql);
            stm.setInt(1,idProg);
            ResultSet resultSet=stm.executeQuery();
            String paci= (String) table1.getValueAt(table1.getSelectedRow(), 1);
            paci+=" ";
            paci+=table1.getValueAt(table1.getSelectedRow(), 2);
            lb_pacient.setText(paci);
            String pacient= lb_pacient.getText();
            textField1a.setText(String.valueOf(idProg));
            textField2a.setText(pacient);
            textField2a.setEnabled(false);/// id programare si numele pacientului nu pot fi schimbate
            textField1a.setEnabled(false);
            if(resultSet.next())
            {
                textField4a.setText(resultSet.getString(3));
                textField5a.setText(resultSet.getString(4));
                textField6a.setText(resultSet.getString(5));
                textField7a.setText(resultSet.getString(6));
                textField8a.setText(resultSet.getString(7));

                textField9a.setText(resultSet.getString(8));
                textField10a.setText(resultSet.getString(9));
                textField9a.setEnabled(false);/// numele si prenumele medicului nu pot fi modificate
                textField10a.setEnabled(false);

                textField11a.setText(resultSet.getString(10));
                textField12a.setText(resultSet.getString(11));
                textField13a.setEnabled(true);
                textField13a.setText(resultSet.getString(12));
                textField13a.setEnabled(false);

                if(resultSet.getTimestamp(12)!=null){/// daca e parafat
                    parafeazaButton.setEnabled(false);
                    updateSAUCreateButton.setEnabled(false);

                    textField1a.setEnabled(false);
                    textField2a.setEnabled(false);

                    textField4a.setEnabled(false);
                    textField5a.setEnabled(false);
                    textField6a.setEnabled(false);
                    textField7a.setEnabled(false);
                    textField8a.setEnabled(false);
                    textField9a.setEnabled(false);
                    textField10a.setEnabled(false);
                    textField11a.setEnabled(false);
                    textField12a.setEnabled(false);
                }
                else {
                    textField13a.setEnabled(false);
                    parafeazaButton.setEnabled(true);
                }
                return  true;
            }
            else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    void enableAllFields(){/// pune toate fieldurileEnabled ca sa poata fi modificate
        textField1a.setEnabled(true);
        textField2a.setEnabled(true);
        textField4a.setEnabled(true);
        textField5a.setEnabled(true);
        textField6a.setEnabled(true);
        textField7a.setEnabled(true);
        textField8a.setEnabled(true);
        textField9a.setEnabled(true);
        textField10a.setEnabled(true);
        textField11a.setEnabled(true);
        textField12a.setEnabled(true);
        textField13a.setEnabled(true);
        parafeazaButton.setEnabled(true);
        updateSAUCreateButton.setEnabled(true);
        textField1a.setText("");
        textField2a.setText("");
        textField4a.setText("");
        textField5a.setText("");
        textField6a.setText("");
        textField7a.setText("");
        textField8a.setText("");
        textField9a.setText("");
        textField10a.setText("");
        textField11a.setText("");
        textField12a.setText("");
        textField13a.setText("");
    }

    public OperationaleMedic(Connection connection, Angajat angajat) {
        setContentPane(mainPanel);
        setTitle("Welcome");
        setSize(850, 500);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        veziServiciiButton.setEnabled(false);
        mainPanel.removeAll();
        mainPanel.add(activitatiOperationaleMedic);
        mainPanel.repaint();
        mainPanel.revalidate();
        enableAllFields();
        completeazaRaportButton1.setEnabled(false);

        table1.addFocusListener(new FocusAdapter() { ///cand se da click pe o linie in tabelul cu consultatiile
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                lb_Id_programare.setText(String.valueOf(table1.getValueAt(table1.getSelectedRow(), 6)));
                veziServiciiButton.setEnabled(true);
                completeazaRaportButton1.setEnabled(true);
            }
        });

        consultatiileMeleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(Consultatii);
                mainPanel.repaint();
                mainPanel.revalidate();
                populareConsultatii(connection, 0, angajat.cnp);
            }
        });
        inapoiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(activitatiOperationaleMedic);
                mainPanel.repaint();
                mainPanel.revalidate();
                veziServiciiButton.setEnabled(false);
                completeazaRaportButton1.setEnabled(false);
                enableAllFields();
            }
        });
        veziServiciiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(Servicii);
                mainPanel.repaint();
                mainPanel.revalidate();
                eliminaServiciuButton.setEnabled(false);
                verificaInvestigatiiButton1.setEnabled(false);
                lb_Id_programare.setText(String.valueOf(table1.getValueAt(table1.getSelectedRow(), 6)));
                String paci= (String) table1.getValueAt(table1.getSelectedRow(), 1);
                paci+=" ";
                paci+=table1.getValueAt(table1.getSelectedRow(), 2);
                lb_pacient.setText(paci);
                populareServicii(connection);
                prepare_list(connection);
            }
        });

        table2.addFocusListener(new FocusAdapter() { ///cand e selectata o linie din tabela servicii
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                eliminaServiciuButton.setEnabled(true);
                verificaInvestigatiiButton1.setEnabled(true);
            }
        });

        inapoiButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(Consultatii);
                mainPanel.repaint();
                mainPanel.revalidate();
            }
        });
        adaugaServiciuButton.addActionListener(new ActionListener() {
            /// adauga serviciul selectat din comboBox in programare
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboServicii.getSelectedIndex()!=-1){
                    String sql="INSERT INTO lista_servicii_client (id_serviciu, id_programare) \n" +
                            "VALUES (?, ?)";
                    PreparedStatement stm= null;
                    try {
                        stm = connection.prepareStatement(sql);
                        int id_servAdaugat=lista_servicii.get(comboServicii.getSelectedIndex()).id_serviciu;
                        int id_prog=Integer.parseInt(lb_Id_programare.getText());
                        stm.setInt(1, id_servAdaugat);
                        stm.setInt(2, id_prog);
                        stm.executeUpdate();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    populareServicii(connection);/// se repopuleaza tabela(ca sa vada ca acum este cu un serviciu in plus)
                }
            }
        });
        eliminaServiciuButton.addActionListener(new ActionListener() {///elimina serviciul de pe linia selectata
            @Override
            public void actionPerformed(ActionEvent e) {
                Object aidi_serviciu=table2.getValueAt(table2.getSelectedRow(),0);
                Integer intID=Integer.valueOf(String.valueOf(aidi_serviciu));
                String sql="Delete from lista_servicii_client where id_programare=? and id_serviciu=?";
                Integer id_progINT=Integer.parseInt(lb_Id_programare.getText());
                PreparedStatement stm= null;
                try {
                    stm = connection.prepareStatement(sql);
                    stm.setInt(2,intID);
                    stm.setInt(1,id_progINT );
                    stm.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                populareServicii(connection);/// se repopuleaza tabela(ca sa vada ca acum este cu un serviciu in minus)
            }
        });
        completeazaRaportButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(Raport);
                mainPanel.repaint();
                mainPanel.revalidate();

                int idProgramareINT=Integer.valueOf(String.valueOf(table1.getValueAt(table1.getSelectedRow(), 6)));
                faceUpdate=populareFielduri(connection, idProgramareINT);
                if(faceUpdate)
                    updateSAUCreateButton.setText("Update");
                else{
                    updateSAUCreateButton.setText("Creaza raport");
                    textField9a.setText(angajat.nume);
                    textField10a.setText(angajat.prenume);
                }
            }
        });
        inapoiButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(Consultatii);
                mainPanel.repaint();
                mainPanel.revalidate();
                enableAllFields();
            }
        });
        updateSAUCreateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(faceUpdate){
                    String sql="update raport_medical set\n" +
                            "diagnostic=?, recomandari=?, simptome=?, nume_medic_recomandat=?, prenume_medic_recomandat=?, nume_asistent=?, prenume_asistent=?\n" +
                            "where id_programare=?";
                    try {
                        PreparedStatement stm=connection.prepareStatement(sql);
                        stm.setString(1,textField4a.getText());
                        stm.setString(2, textField5a.getText());
                        stm.setString(3, textField6a.getText());
                        stm.setString(4, textField7a.getText());
                        stm.setString(5, textField8a.getText());
                        stm.setString(6, textField11a.getText());
                        stm.setString(7, textField12a.getText());
                        stm.setInt(8, Integer.valueOf(textField1a.getText()));
                        stm.executeUpdate();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(getParent(), "Eroare in raport. Verificati asistentul");
                    }
                }
                else{
                    String sql="Insert into raport_medical values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    try {
                        PreparedStatement stm=connection.prepareStatement(sql);
                        stm.setInt(1, Integer.valueOf(textField1a.getText()));
                        stm.setString(2, angajat.cnp);
                        stm.setString(3,textField4a.getText());
                        stm.setString(4,textField5a.getText());
                        stm.setString(5,textField6a.getText());
                        stm.setString(6,textField7a.getText());
                        stm.setString(7,textField8a.getText());
                        stm.setString(8,textField9a.getText());
                        stm.setString(9,textField10a.getText());
                        stm.setString(10,textField11a.getText());
                        stm.setString(11,textField12a.getText());
                        stm.setNull(13, Types.FLOAT);
                        stm.setNull(12, Types.TIMESTAMP);
                        stm.executeUpdate();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(getParent(), "Eroare in raport. Verificati asistentul");
                    }
                    updateSAUCreateButton.setText("Update");
                    faceUpdate=true;
                }
            }
        });

        parafeazaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql="Update raport_medical set moment_parafare=current_timestamp where id_programare=?";
                PreparedStatement stm= null;
                try {
                    stm = connection.prepareStatement(sql);
                    int idProgramareINT=Integer.valueOf(textField1a.getText());
                    stm.setInt(1, idProgramareINT);
                    stm.executeUpdate();
                    faceUpdate=populareFielduri(connection, idProgramareINT);
                    System.out.println("a parafat");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        verificaInvestigatiiButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql="select detalii_investigatie from investigatie where id_raport=? and id_serviciu=?";
                idLb.setText(String.valueOf(table1.getValueAt(table1.getSelectedRow(), 6)));
                Object aidi_serviciu=table2.getValueAt(table2.getSelectedRow(),0);
                intIDserviciuInvest=Integer.valueOf(String.valueOf(aidi_serviciu));
                try {
                    PreparedStatement stm= connection.prepareStatement(sql);
                    stm.setInt(2,intIDserviciuInvest);
                    stm.setInt(1, Integer.valueOf(idLb.getText()));
                    ResultSet resultSet= stm.executeQuery();
                    if(resultSet.next())/// daca exista investigatia
                    {
                        textArea1.setText(resultSet.getString(1));
                        pacLb.setText(lb_pacient.getText());
                        servLbLabel.setText((String) table2.getValueAt(table2.getSelectedRow(),1));
                        mainPanel.removeAll();
                        mainPanel.add(Investigatii);
                        mainPanel.repaint();
                        mainPanel.revalidate();
                    }
                    else {
                        //Custom dialog box
                        System.out.println("nu exista investigatia");
                        Object[] options = {"Da", "Nu"};
                        int n = JOptionPane.showOptionDialog(mainPanel,
                                "Doriti sa creati investigatia?",
                                "Investigatie inexistenta",
                                JOptionPane.YES_NO_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[1]);
                        if(n==0){
                            System.out.println("Da, vreau sa creez investigatia");
                            sql="INSERT INTO investigatie (id_raport, id_serviciu) values(?, ?)";
                            stm= connection.prepareStatement(sql);
                            stm.setInt(2,intIDserviciuInvest);
                            stm.setInt(1, Integer.valueOf(idLb.getText()));
                            if(stm.executeUpdate()==1);
                                JOptionPane.showMessageDialog(getParent(), "Investigatia a fost creata. Acum o puteti accesa");
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {/// update la investigatie
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql="update investigatie set detalii_investigatie=? where id_raport=? and id_serviciu=?";
                PreparedStatement stm= null;
                try {
                    stm = connection.prepareStatement(sql);
                    stm.setString(1, textArea1.getText());
                    stm.setInt(2, Integer.valueOf(idLb.getText()));
                    stm.setInt(3, intIDserviciuInvest);
                    stm.executeUpdate();

///copy paste de mai sus(acelasi lucru ca si cum ar da click pe verificaInvestigatiiButton1
                    sql="select detalii_investigatie from investigatie where id_raport=? and id_serviciu=?";
                    idLb.setText(String.valueOf(table1.getValueAt(table1.getSelectedRow(), 6)));
                    Object aidi_serviciu=table2.getValueAt(table2.getSelectedRow(),0);
                    intIDserviciuInvest=Integer.valueOf(String.valueOf(aidi_serviciu));
                    try {
                        stm= connection.prepareStatement(sql);
                        stm.setInt(2,intIDserviciuInvest);
                        stm.setInt(1, Integer.valueOf(idLb.getText()));
                        ResultSet resultSet= stm.executeQuery();
                        if(resultSet.next())
                        {
                            textArea1.setText(resultSet.getString(1));
                            pacLb.setText(lb_pacient.getText());
                            servLbLabel.setText((String) table2.getValueAt(table2.getSelectedRow(),1));
                            mainPanel.removeAll();
                            mainPanel.add(Investigatii);
                            mainPanel.repaint();
                            mainPanel.revalidate();
                        }
                        else {
                            JOptionPane.showMessageDialog(getParent(), "Nu sunt disponibile investigatii");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        inapoiButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(Servicii);
                mainPanel.repaint();
                mainPanel.revalidate();
            }
        });
        inapoiButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(activitatiOperationaleMedic);
                mainPanel.repaint();
                mainPanel.revalidate();
            }
        });
        istoricPacientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(istoric);
                mainPanel.repaint();
                mainPanel.revalidate();
            }
        });
        veziProgramarileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(Consultatii);
                mainPanel.repaint();
                mainPanel.revalidate();
                populareConsultatii(connection, 1, textField1.getText());
            }
        });
        verificaAnalizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql="Select * from analize_medicale where id_programare=?";
                try {
                    PreparedStatement stm= connection.prepareStatement(sql);
                    stm.setInt(1, Integer.valueOf(textField1a.getText()));
                    ResultSet resultSet = stm.executeQuery();
                    if(resultSet.next()){
                        idProgTf.setEnabled(true);
                        validareTf.setEnabled(true);
                        analizaTf.setEnabled(true);
                        textArea2.setEnabled(true);

                        idProgTf.setText(resultSet.getString(1));
                        validareTf.setText(resultSet.getString(3));
                        analizaTf.setText(resultSet.getString(4));
                        textArea2.setText(resultSet.getString(2));

                        mainPanel.removeAll();
                        mainPanel.add(Analize);
                        mainPanel.repaint();
                        mainPanel.revalidate();
                    }
                    else
                        JOptionPane.showMessageDialog(getParent(), "Nu sunt disponibile analize");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                idProgTf.setEnabled(false);
                validareTf.setEnabled(false);
                analizaTf.setEnabled(false);
                textArea2.setEnabled(false);
            }
        });
        inapoiButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(Raport);
                mainPanel.repaint();
                mainPanel.revalidate();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Homepage homepage = new Homepage(connection, angajat);
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
}

