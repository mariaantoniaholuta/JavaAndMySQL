package com.utcn.Modulul3;

import com.utcn.Bonus.Pair;
import com.utcn.models.Angajat;
import com.utcn.CommonInterfaces.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OperationaleReceptioner extends JFrame {
    private JPanel activitatiOperationaleReceptioner;
    private JButton logOutBtn;
    private JButton backBtn;
    private JButton programarePacientButton;
    private JPanel PanelMod3;
    private JButton emitereBonButton;
    private JButton inregistrarePacientButton;
    private JPanel NewPacient;
    private JTextField tfNume;
    private JButton anuleazaButton;
    private JButton inregistreazaButton;
    private JTextField tfPrenume;
    private JTextField tfCNP;
    private JPanel NewProgramare;
    private JComboBox comboPoliclinica;
    private JComboBox comboSpecialitate;
    private JComboBox comboServiciu;
    private JComboBox comboMedic;
    private JTextField tfCNPprog;
    private JButton anuleazaButton1;
    private JButton veziDisponibilitatiButton;
    private JList list1;
    private JPanel JPanel;
    private JLabel johnDowLabel;
    private JLabel pacientNeinregistratLabel;
    private JTextArea textArea1;
    private JButton golesteButton;
    private JTable table1;
    private JButton cautaCabinet;
    private JButton inapoiButton;
    private JButton maiCautaOZiButton;
    private JPanel Disponibilitati;
    private JPanel EmitereBon;
    private JTextField CNPbon;
    private JComboBox comboProgramare;
    private JLabel bonLabel;
    private JTextArea bonText;
    private JButton anuleazaButton2;
    private JLabel johnDowLabel1;
    private JLabel pacientNeinregistratLabel1;
    private JButton emiteBonButton;
    private JPanel mainPane;
    private JButton backButton;
    private JButton logOutButton;
    private JLabel disponibile;
    private JScrollPane scrollPane;
    private JTable table;
    private JPanel CabineteDisponibile;
    private JButton creazaProgramareButton;
    private JTable tabelCabinet;
    private static List<Policlinica> listaPoliclicnici = new ArrayList<Policlinica>();
    private static List<Servicii_Medicale> listaServicii;
    private static List<Integer> indexurileServiciilorSelectate = new ArrayList<Integer>();
    private static List<PairMed> numeMedici;
    private static boolean serviciiReactioneaza = true;
    private static List<Programare> listaProgramari;
    private static int zileVerificate = 0;
    private static Calendar rightNow = Calendar.getInstance();
    private static int durataProgramare = 0;
    private static DefaultTableModel dtm;
    private static float totalBon = 0;
    private static String id_programrePtCareSeEmiteBon;
    String[] dateProgramare = new String[7];


    String prettyCal(Calendar c) {/// transforma calendarul c intr-un string frumos de afisat
        String day = "";
        String month = "";
        if (c.get(Calendar.DAY_OF_MONTH) < 10)
            day += "0";
        if (c.get(Calendar.MONTH) < 9)
            month += "0";
        day += c.get(Calendar.DAY_OF_MONTH);
        month += (c.get(Calendar.MONTH) + 1);

        String s = day + "." + month + "." + c.get(Calendar.YEAR);
        s = s + "  " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
        return s;
        ///15.12.2022   4:15
    }

    List<Programare> cautaProgramari(Date sqlDate, Connection connection) {
        ///returneaza o lista cu toate programarile din data sqlDate
        List<Programare> list = new ArrayList<Programare>();
        String sql = "SELECT * from programare where data_consultatie=?";
        ///String sql="SELECT * from programare where data_consultatie= '"+ sqlDate.toString()+"'";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setDate(1, sqlDate);
            ResultSet resultSet = stm.executeQuery();
            Programare p;
            while (resultSet.next()) {
                p = new Programare(resultSet);
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    void clearFields() {/// sterge fieldurile si labelurile
        zileVerificate = 0;
        serviciiReactioneaza = false;
        tfNume.setText("");
        tfPrenume.setText("");
        tfCNP.setText("");
        johnDowLabel.setVisible(false);
        pacientNeinregistratLabel.setVisible(false);
        comboPoliclinica.setSelectedIndex(0);
        comboSpecialitate.setSelectedIndex(0);
        comboMedic.removeAllItems();
        while (!indexurileServiciilorSelectate.isEmpty())
            indexurileServiciilorSelectate.remove(0);
        textArea1.setText("");
        tfCNPprog.setText("");
        comboServiciu.removeAllItems();
        CNPbon.setText("");
        johnDowLabel1.setVisible(false);
        pacientNeinregistratLabel1.setVisible(false);
        comboProgramare.removeAllItems();
        bonText.setText("");
        serviciiReactioneaza = true;
    }

    boolean intersects(Calendar a, int minutes, Programare p) {
        /// verifica daca programarea care dureaza minutes minute din momentul a intersecteaza programarea p
        Calendar sfarsitA = Calendar.getInstance();
        sfarsitA.setTimeInMillis(a.getTimeInMillis() + (long) minutes * 60 * 1000);
        Calendar b = Calendar.getInstance();
        long GMTplus2 = 2 * 60 * 60 * 1000;
        b.setTimeInMillis(p.data_consultatie.getTime() + p.ora.getTime() + GMTplus2);
        Calendar sfarsitB = Calendar.getInstance();
        sfarsitB.setTimeInMillis(p.data_consultatie.getTime() + p.ora_sfarsit.getTime() + GMTplus2);
        if (a.compareTo(b) <= 0 && b.before(sfarsitA))
            return true;
        if (b.compareTo(a) <= 0 && a.before(sfarsitB))
            return true;
        return false;
    }

    List<PairMed> cautaNumeMedici(int id_policlinica, String specializare, Connection connection) {
        ///cauta medicii de la policlinica si specializarea data. Face o pereche(clasa custom) cu numele si cnp ul lor
        String sql = "SELECT DISTINCT nume, prenume, angajat.cnp FROM Angajat join orar_generic o on angajat.cnp = o.cnp join grad g on angajat.cnp = g.cnp " +
                "where o.id_policlinica=? and g.denumire_specialitate=?";
//        String sql="SELECT nume, prenume, angajat.cnp FROM Angajat join orar_generic o on angajat.cnp = o.cnp join grad g on angajat.cnp = g.cnp " +
//                "where o.id_policlinica=" +   String.valueOf(id_policlinica)    +" and g.denumire_specialitate='"  + specializare  + "'";
        List<PairMed> numeMedici = new ArrayList<PairMed>();
        try {
            PreparedStatement stm = connection.prepareStatement(sql);

            stm.setInt(1, id_policlinica);
            stm.setString(2, specializare);
            ResultSet resultSet = stm.executeQuery();
            String numePrenume;
            while (resultSet.next()) {
                numePrenume = resultSet.getString(1) + " " + resultSet.getString(2);
                PairMed pm = new PairMed(numePrenume, resultSet.getString(3));
                numeMedici.add(pm);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return numeMedici;
    }

    List<Servicii_Medicale> cautaServicii(Connection connection) {
        List<Servicii_Medicale> listaSer = new ArrayList<Servicii_Medicale>();
        String sql = "SELECT ts.id_serviciu, ts.denumire_serviciu, ts.pret, ts.durata " +
                "FROM " +
                "(SELECT s.id_serviciu, s.denumire_serviciu, s.pret, s.durata, s.cnp " +
                "FROM servicii_care_nu_sunt_custom_final AS s  " +
                "WHERE s.cnp = ? " +
                "UNION  " +
                "SELECT sc.id_serviciu, sc.denumire_serviciu, sc.cost, sc.durata, sc.cnp_medic " +
                "FROM servicii_custom_medic_cu_nume AS sc  " +
                "WHERE sc.cnp_medic = ?) AS ts " +
                "JOIN portofoliu_servicii AS ps " +
                "ON ps.id_serviciu = ts.id_serviciu " +
                "WHERE ps.denumire_specialitate = ? " +
                "ORDER BY ts.denumire_serviciu";/// asta trebe modificata
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            String cnpMedicSelectat=numeMedici.get(comboMedic.getSelectedIndex()).cnpMedic ;
            pstm.setString(1, cnpMedicSelectat);
            pstm.setString(2, cnpMedicSelectat);

            String denumireSpecialitateSelectata= (String) comboSpecialitate.getSelectedItem();
            pstm.setString(3, denumireSpecialitateSelectata);
            ResultSet resultSet = pstm.executeQuery();
            Servicii_Medicale serviciu;
            while (resultSet.next()) {
                serviciu = new Servicii_Medicale(resultSet);
                listaSer.add(serviciu);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listaSer;
    }

    String whatDayIsIt(Date sqlNow, Statement stm) {
        ///primeste o data, returneaza Tuesday
        ResultSet resultSet;
        String stringNow = sqlNow.toString();
        String weekday = null;
        String sql = "SELECT DAYNAME('" + stringNow + "')";
        try {
            resultSet = stm.executeQuery(sql);
            if (resultSet.next())
                weekday = resultSet.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return weekday;
    }

    boolean addToTable1(Connection connection, int zileVerificate, Calendar rightNow, int durataProgramare, DefaultTableModel dtm) {
        ///adauga in tabelul cu orele disponibile pt consultati
        // orele de acum+zileVerificate stiind ca programarea ar dura durataProgramare
        int id_poli = listaPoliclicnici.get(comboPoliclinica.getSelectedIndex()-1).id_policlinica;
        boolean aux = false;
        String numeMed = numeMedici.get(comboMedic.getSelectedIndex()).numePrenume;
        try {
            Statement stm = connection.createStatement();
            ResultSet resultSet;
            Date sqlToday = null;
            String weekday;
            String cnp = numeMedici.get(comboMedic.getSelectedIndex()).cnpMedic;
            String sql;
            Time oraDeInceput = null;
            Time oraDeSfarsit = null;
            sql = "SELECT DATE_ADD(CURDATE(), INTERVAL " + String.valueOf(zileVerificate) + " DAY)";
            resultSet = stm.executeQuery(sql);
            if (resultSet.next())
                sqlToday = resultSet.getDate(1);/// ziua de acum+zileVerificate
            listaProgramari = cautaProgramari(sqlToday, connection);

            sql="Select CHECK_VALID_DAY(?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setDate(1, sqlToday);
            pstm.setString(2, cnp);
            resultSet=pstm.executeQuery();
            if(resultSet.next()){
                int isWorking=resultSet.getInt(1);
                if (isWorking==0){
                    System.out.println(sqlToday);
                    System.out.println(cnp);
                    return false;
                }
            }
            /// cauta ora de inceput si sfarsit a programului pentru data de sqlToday;
            sql = "SELECT ora_inceput, ora_sfarsit from orar_specific where cnp=? and id_policlinica=? and data_inregistrare=?";
            pstm = connection.prepareStatement(sql);
            pstm.setString(1, cnp);
            pstm.setInt(2, id_poli);
            pstm.setDate(3, sqlToday);
            resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                /// daca are orar custom in ziua asta
                oraDeInceput = resultSet.getTime(1);
                oraDeSfarsit = resultSet.getTime(2);
            } else {
                weekday = whatDayIsIt(sqlToday, stm);
                System.out.println("Azi e " + weekday);
                sql = "SELECT ora_inceput, ora_sfarsit from orar_generic where cnp='" + cnp + "' and zi='" + weekday + "' and id_policlinica= " + id_poli;
                resultSet = stm.executeQuery(sql);
                if (resultSet.next()) {
                    System.out.println("am intrat la lucru");
                    oraDeInceput = resultSet.getTime("ora_inceput");
                    oraDeSfarsit = resultSet.getTime("ora_sfarsit");
                }
            }
            long miliTime = sqlToday.getTime();
            miliTime += oraDeInceput.getTime() + 2 * 60 * 60 * 1000;
            Calendar inceputProgram = Calendar.getInstance();
            inceputProgram.setTimeInMillis(miliTime);
            miliTime += oraDeSfarsit.getTime() - oraDeInceput.getTime();
            Calendar sfarsitProgram = Calendar.getInstance();
            sfarsitProgram.setTimeInMillis(miliTime);
            ///au fost initializate calendarele inceputProgram si sfarsitProgram

            if (inceputProgram.before(rightNow))/// daca se face programare pe ziua in curs consideram ca programul medicului incepe rightNow
                inceputProgram.setTimeInMillis(rightNow.getTimeInMillis());

            for (Calendar i = inceputProgram; i.before(sfarsitProgram); i.add(Calendar.MINUTE, 10)) {
                Calendar temp = Calendar.getInstance();
                temp.setTimeInMillis(i.getTimeInMillis());
                temp.add(Calendar.MINUTE, durataProgramare);
                ///temp=i+durata programare
                if (sfarsitProgram.before(temp))
                    break;

                ///verifica daca are timp de o programare
                boolean areTimp = true;
                for (Programare p : listaProgramari) {
                    if (intersects(i, durataProgramare, p))
                        areTimp = false;
                }
                if (areTimp) {/// daca are timp atunci il punem in tabel
                    System.out.println(prettyCal(i));
                    Object[] rowData = new Object[4];
                    ///String[] column = {"Medic", "Durata Programare (min)", "Data", "Ora"};
                    String stringCal = prettyCal(i);
                    String[] tokens = stringCal.split("  ");
                    ///Time ora_inceput= new Time(i.getTimeInMillis()-i.getTime().getTime());
                    rowData[1] = String.valueOf(durataProgramare);
                    rowData[2] = tokens[0];
                    rowData[3] = tokens[1];
                    rowData[0] = numeMed;
                    dtm.addRow(rowData);
                    aux = true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return aux;
    }

    void finalInsert(Connection connection){
        ///Object[] dateProgramare = new Object[]{id_policlinicaSTR, cnpPacient, cnpMedic, data, ora, oraSfarsit, id_cabinet};
//        String sql = "INSERT into programare (id_policlinica, cnp_pacient, cnp_medic, data_consultatie, ora, ora_sfarsit) " +///this is sql injection proof
//                "VALUES( " + id_policlinicaSTR + ", '" + cnpPacient + "', '" + cnpMedic + "', STR_TO_DATE ( '" + data + "', '%d.%m.%Y' ) " +
//                ", '" + ora + ": 00', '" + oraSfarsit + "' ) ";

        String sqlll="Insert into programare (id_policlinica, cnp_pacient, cnp_medic, data_consultatie, ora, ora_sfarsit, id_cabinet) "+
                "Values(?, ?, ?, STR_TO_DATE(?, '%d.%m.%Y') " +
                ",?,?,?) ";
        PreparedStatement pstm= null;
        try {
            pstm = connection.prepareStatement(sqlll);
            int id_policlinica=Integer.valueOf(dateProgramare[0]);
            pstm.setInt(1,id_policlinica);
            pstm.setString(2,dateProgramare[1]);
            pstm.setString(3, dateProgramare[2]);
            pstm.setString(4, dateProgramare[3]);
            String oraFormatata=dateProgramare[4]+":00";
            pstm.setString(5, oraFormatata);
            pstm.setString(6, dateProgramare[5] );
            int id_cabinet=Integer.valueOf(dateProgramare[6]);
            pstm.setInt(7, id_cabinet);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
/// get the ID_programare:
        try {

            String sql = "Select id_programare from programare where " +
                    "cnp_pacient= '" + dateProgramare[1] + "' and ora= '" + dateProgramare[4] + ":00' and " +
                    "data_consultatie= STR_TO_DATE ( '" + dateProgramare[3] + "', '%d.%m.%Y' )";
            Statement stm= connection.createStatement();;
            int nrOfServices = 0;

            ResultSet resultSet = stm.executeQuery(sql);
            if (resultSet.next()) {
                int id_prog = resultSet.getInt("id_programare");
                int id_serviciu;
                for (int i : indexurileServiciilorSelectate) {
                    /// pentru fiecare serviciu selectat, insereaza-l in tabela lista_servicii_client
                    id_serviciu = listaServicii.get(i).id_serviciu;
                    sql = "insert into lista_servicii_client (id_programare, id_serviciu) VALUES (" + String.valueOf(id_prog) +
                            ", " + String.valueOf(id_serviciu) + ")";
                    nrOfServices += stm.executeUpdate(sql);
                }
                JOptionPane.showMessageDialog(activitatiOperationaleReceptioner, "S-a salvat programarea cu ID " + String.valueOf(id_prog) + " care cuprinde " + String.valueOf(nrOfServices) + " servicii");
                table1.removeAll();
                dtm = new DefaultTableModel();//nu stiu cum sa-l golesc asa ca fac altul :)
                zileVerificate = 0;
                PanelMod3.removeAll();
                PanelMod3.add(activitatiOperationaleReceptioner);
                PanelMod3.repaint();
                PanelMod3.revalidate();
                clearFields();
            } else
                JOptionPane.showMessageDialog(activitatiOperationaleReceptioner, "Eroare la programare");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(activitatiOperationaleReceptioner, "Eroare la programare");
        }
    }

    public OperationaleReceptioner(Connection connection, Angajat angajat) {
        setContentPane(PanelMod3);
        setTitle("Welcome");
        setSize(850, 500);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        PanelMod3.removeAll();
        PanelMod3.add(activitatiOperationaleReceptioner);
        PanelMod3.repaint();
        PanelMod3.revalidate();
        ImageIcon image = new ImageIcon("logo1.png");
        setIconImage(image.getImage());
        programarePacientButton.requestFocus();

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
                new Homepage(connection, angajat);
            }
        });

        programarePacientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSize(850, 500);
                PanelMod3.removeAll();
                PanelMod3.add(NewProgramare);
                Statement stm = null;
                Policlinica p;
                try {
                    stm = connection.createStatement();
                    ResultSet resultSet = stm.executeQuery("SELECT * FROM policlinica");
                    while (resultSet.next()) {
                        p = new Policlinica(resultSet);
                        listaPoliclicnici.add(p);
                        comboPoliclinica.addItem(listaPoliclicnici.get(listaPoliclicnici.size() - 1).getNume());
                    }
                    resultSet = stm.executeQuery("SELECT denumire_specialitate from specialitate");
                    while (resultSet.next()) {
                        comboSpecialitate.addItem(resultSet.getString(1));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                PanelMod3.repaint();
                PanelMod3.revalidate();
            }
        });

        inregistrarePacientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelMod3.removeAll();
                PanelMod3.add(NewPacient);
                PanelMod3.repaint();
                PanelMod3.revalidate();
            }
        });

        anuleazaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelMod3.removeAll();
                PanelMod3.add(activitatiOperationaleReceptioner);
                PanelMod3.repaint();
                PanelMod3.revalidate();
                clearFields();
            }
        });

        inregistreazaButton.addActionListener(new ActionListener() {/// inregistreaza un pacient nou
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PreparedStatement stm = connection.prepareStatement("SELECT * FROM pacient where cnp = ?");
                    stm.setString(1, tfCNP.getText());
                    ResultSet resultSet = stm.executeQuery();
                    if (resultSet.next())
                        JOptionPane.showMessageDialog(activitatiOperationaleReceptioner, "CNP-ul exista deja");
                    else {
                        ///String sql = "INSERT INTO pacient VALUES(" +cnp+ "," +nume+","+prenume+")";
                        String sql = "INSERT INTO pacient VALUES(?,?,?)";
                        stm = connection.prepareStatement(sql);
                        stm.setString(1, tfCNP.getText());
                        stm.setString(2, tfNume.getText());
                        stm.setString(3, tfPrenume.getText());
                        stm.executeUpdate();
                        JOptionPane.showMessageDialog(activitatiOperationaleReceptioner, "Aplicatia n-a explodat deci totul e ok");
                        clearFields();
                        PanelMod3.removeAll();
                        PanelMod3.add(activitatiOperationaleReceptioner);
                        PanelMod3.repaint();
                        PanelMod3.revalidate();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        anuleazaButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelMod3.removeAll();
                PanelMod3.add(activitatiOperationaleReceptioner);
                PanelMod3.repaint();
                PanelMod3.revalidate();
                clearFields();
            }
        });

        tfCNPprog.addActionListener(new ActionListener() {/// la apasarea unui enter dupa introducerea unui CNP
            // (cand se face o programare noua)
            @Override
            public void actionPerformed(ActionEvent e) {
                johnDowLabel.setVisible(false);
                pacientNeinregistratLabel.setVisible(false);
                try {
                    PreparedStatement stm = connection.prepareStatement("SELECT * FROM pacient where cnp = ?");
                    stm.setString(1, tfCNPprog.getText());
                    ResultSet resultSet = stm.executeQuery();
                    if (resultSet.next()) {
                        johnDowLabel.setText(resultSet.getString(2) + " " + resultSet.getString(3));
                        johnDowLabel.setVisible(true);
                        veziDisponibilitatiButton.setEnabled(true);
                    } else {
                        pacientNeinregistratLabel.setVisible(true);
                        veziDisponibilitatiButton.setEnabled(false);
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        comboSpecialitate.addActionListener(new ActionListener() {///cand s-a ales o specialitate se populeaza comboMedici
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboSpecialitate.getSelectedIndex() > 0 && comboPoliclinica.getSelectedIndex() > 0) {
                    comboMedic.removeAllItems();
                    int id = listaPoliclicnici.get(comboPoliclinica.getSelectedIndex()-1).getId_policlinica();
                    numeMedici = cautaNumeMedici(id, comboSpecialitate.getSelectedItem().toString(), connection);
                    for (PairMed n : numeMedici) {
                        comboMedic.addItem(n.numePrenume);
                    }
                }
            }
        });

        comboMedic.addActionListener(new ActionListener() {/// cand s-a ales un medic se populeaza comboServicii
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean temp = serviciiReactioneaza;
                serviciiReactioneaza = false;
                listaServicii = cautaServicii(connection);
                String denumireServiciu;
                for (Servicii_Medicale s : listaServicii) {
                    denumireServiciu = s.getDenumire_serviciu();
                    comboServiciu.addItem(denumireServiciu);
                }
                serviciiReactioneaza = temp;
            }
        });

        comboServiciu.addActionListener(new ActionListener() {/// cand s-a ales un serviciu se pune in lista
            @Override
            public void actionPerformed(ActionEvent e) {
                if (serviciiReactioneaza == true) {
                    int i = comboServiciu.getSelectedIndex();
                    if (indexurileServiciilorSelectate.contains(i))
                        JOptionPane.showMessageDialog(getParent(), "Nu poti muri de 2 ori!");
                    else {
                        indexurileServiciilorSelectate.add(i);
                        int j = indexurileServiciilorSelectate.get(indexurileServiciilorSelectate.size() - 1);
                        /// ar trebui i==j;
                        String serviciuSelectat = listaServicii.get(j).getDenumire_serviciu() + " (" + listaServicii.get(j).getDurata() + "') =" + listaServicii.get(j).getPret() + "lei";
                        textArea1.append(serviciuSelectat + '\n');
                    }
                }
            }
        });

        golesteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while (!indexurileServiciilorSelectate.isEmpty())
                    indexurileServiciilorSelectate.remove(0);
                textArea1.setText(null);
            }
        });

        veziDisponibilitatiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                durataProgramare = 0;
                for (Integer i : indexurileServiciilorSelectate) {
                    durataProgramare += listaServicii.get(i).durata;
                }
                // setez right now ca urmatorul minut multiplu de 10
                rightNow.add(Calendar.MINUTE, 10 - rightNow.get(Calendar.MINUTE) % 10);
                System.out.println("Acum este: " + prettyCal(rightNow));
                System.out.println("Durata Programare: " + durataProgramare);

                PanelMod3.removeAll();
                PanelMod3.add(Disponibilitati);
                PanelMod3.repaint();
                PanelMod3.revalidate();

                String[] column = {"Medic", "Durata Programare (min)", "Data", "Ora"};
                dtm = new DefaultTableModel();
                for (int i = 0; i < 4; i++)/***trăbă adăugate pe rând, ayaye (dacă ești smecher, caută tu o metodă să ți le bage deodată*/
                    dtm.addColumn(column[i]);

                /// function to add to table all posible slots from day [Today+zileVerificate]
                addToTable1(connection, zileVerificate, rightNow, durataProgramare, dtm);
                table1.getTableHeader().setFont(new Font("Rockwell", Font.BOLD, 18));
                table1.getTableHeader().setOpaque(false);
                table1.getTableHeader().setBackground(new Color(166, 201, 181));
                table1.setModel(dtm);
                table1.setRowHeight(30);
                table1.setFillsViewportHeight(true);
            }
        });

        inapoiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelMod3.removeAll();
                PanelMod3.add(NewProgramare);
                PanelMod3.repaint();
                PanelMod3.revalidate();
                table1.removeAll();
                dtm = new DefaultTableModel();//nu stiu cum sa-l golesc asa ca fac altul :)
                zileVerificate = 0;
            }
        });

        maiCautaOZiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean amMaiAdaugatDisponibilitati = false;
                while (!amMaiAdaugatDisponibilitati) {
                    zileVerificate++;
                    amMaiAdaugatDisponibilitati = addToTable1(connection, zileVerificate, rightNow, durataProgramare, dtm);
                }
            }
        });

        cautaCabinet.addActionListener(new ActionListener() {/***inregistreaza programarea selectata*/
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Pair> listaCabineteDisponibile = new ArrayList<>();
                if (table1.getSelectedRow() == -1)
                    JOptionPane.showMessageDialog(getParent(), "Selectati o linie!");
                else {
                    ///insert in tabela programari
                    int id_policlinicaINT = listaPoliclicnici.get(comboPoliclinica.getSelectedIndex()-1).id_policlinica;
                    String id_policlinicaSTR = String.valueOf(id_policlinicaINT);
                    String cnpPacient = tfCNPprog.getText();
                    String cnpMedic = numeMedici.get(comboMedic.getSelectedIndex()).cnpMedic;
                    String data = table1.getValueAt(table1.getSelectedRow(), 2).toString();
                    String ora = table1.getValueAt(table1.getSelectedRow(), 3).toString();
                    String tokens[] = ora.split(":");
                    int oraINT = Integer.parseInt(tokens[0]);
                    int minuteINT = Integer.parseInt(tokens[1]);
                    int secunde = 0;
                    Time oraTIME = new Time(oraINT, minuteINT, secunde);
                    oraTIME.setTime(oraTIME.getTime() + durataProgramare * 60 * 1000);
                    String oraSfarsit = oraTIME.toString();


                    String s = null;///asta fa vi completat de functia de repartizare pe cabinete
                    dateProgramare= new String[]{id_policlinicaSTR, cnpPacient, cnpMedic, data, ora, oraSfarsit, s};


                    try {
                        PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT  id_serviciu FROM " +
                                " lista_servicii_cabinet");
                        ResultSet serviciiEchipament = statement.executeQuery();

                        List<Integer> listaServiciiEchipament = new ArrayList<>();
                        while (serviciiEchipament.next()) {
                            listaServiciiEchipament.add(serviciiEchipament.getInt("id_serviciu"));
                            System.out.print(serviciiEchipament.getInt("id_serviciu") + " ");
                        }
                        System.out.println();
                        serviciiEchipament.close();

                        List<Integer> listaClient = new ArrayList<>();
                        for (int i:indexurileServiciilorSelectate)
                            if(listaServiciiEchipament.contains(listaServicii.get(i).id_serviciu))
                                listaClient.add(listaServicii.get(i).id_serviciu);

                        for (int i = 0; i < listaClient.size(); i++)
                            System.out.println(listaClient);

                        /** cabinetele policlinicii selectate**/
                        statement = connection.prepareStatement("SELECT DISTINCT ls.id_cabinet, ls.id_serviciu, c.numar_cabinet " +
                                "FROM lista_servicii_cabinet AS ls " +
                                "JOIN cabinet AS c " +
                                "ON ls.id_cabinet = c.numar_cabinet " +
                                "WHERE ls.id_policlinica = ? ORDER BY ls.id_cabinet");
                        statement.setString(1, String.valueOf(id_policlinicaINT));
                        ResultSet serviciiCabinet = statement.executeQuery();

                        List<Pair> listaCabinet = new ArrayList<>();
                        while (serviciiCabinet.next()) {
                            Pair value = new Pair();
                            value.x = serviciiCabinet.getInt("id_cabinet");
                            value.y = serviciiCabinet.getInt("id_serviciu");
                            value.z = serviciiCabinet.getInt("numar_cabinet");
                            listaCabinet.add(value);
                            System.out.println(value.x + " " + value.y);
                           // System.out.print("{" + serviciiCabinet.getInt("id_cabinet") + ", " +  serviciiCabinet.getInt("id_serviciu") + "} ");
                        }
                        System.out.println();

                        for(int i = 0; i < listaCabinet.size(); ++i) {
                            Pair p = listaCabinet.get(i);
                            int numberOfMatches = 0;
                            PreparedStatement checkOra = connection.prepareStatement("SELECT CHECK_CABINET_VALID(STR_TO_DATE(?, '%d.%m.%Y'), ?, ?, ?) AS flag");
                            checkOra.setString(1, dateProgramare[3]);
                            checkOra.setString(2, dateProgramare[4]+":00");
                            checkOra.setString(3, dateProgramare[5]);
                            checkOra.setString(4, String.valueOf(p.x));
                            ResultSet flagOra = checkOra.executeQuery();
                            flagOra.next();
                            while(i < listaCabinet.size() && p.x == listaCabinet.get(i).x) {
                                Integer cabinetul = listaCabinet.get(i).y;
                                if(listaClient.contains(cabinetul))
                                    numberOfMatches++;
                                ++i;
                            }
                            if(numberOfMatches == listaClient.size() && flagOra.getInt("flag") == 1)
                                listaCabineteDisponibile.add(p);
                            --i;
                        }

                        DefaultTableModel dtmCabinet = new DefaultTableModel();
                        dtmCabinet.addColumn("Id cabinet");
                        dtmCabinet.addColumn("Numar cabinet");
                        for (Pair i: listaCabineteDisponibile) {
                            System.out.println(i);
                            dtmCabinet.addRow(new Object[]{i.x, i.z});
                        }
                        disponibile.setVisible(true);
                        tabelCabinet.getTableHeader().setFont(new Font("Rockwell", Font.BOLD, 16));
                        tabelCabinet.getTableHeader().setOpaque(false);
                        tabelCabinet.getTableHeader().setBackground(new Color(166,201,181));
                        tabelCabinet.setModel(dtmCabinet);
                        tabelCabinet.setRowHeight(30);
                        tabelCabinet.setFillsViewportHeight(true);
                        DefaultTableCellRenderer rendar = new DefaultTableCellRenderer();
                        rendar.setHorizontalAlignment(JLabel.CENTER);
                        tabelCabinet.getColumnModel().getColumn(0).setCellRenderer(rendar);


                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                if(listaCabineteDisponibile.size() > 0) {
                    PanelMod3.removeAll();
                    PanelMod3.add(CabineteDisponibile);
                    PanelMod3.repaint();
                    PanelMod3.revalidate();
                }
                else
                    JOptionPane.showMessageDialog(getParent(), "Nu exista cabinete libere la ora respectiva");
            }
        });

        CNPbon.addActionListener(new ActionListener() {/// cand s-a dat enter dupa introducerea unui CNP pt emitere de bon
            @Override
            public void actionPerformed(ActionEvent e) {
                johnDowLabel1.setVisible(false);
                pacientNeinregistratLabel1.setVisible(false);
                try {
                    String sql = "SELECT * FROM pacient where cnp=?";
                    PreparedStatement stm = connection.prepareStatement(sql);
                    stm.setString(1, CNPbon.getText());
                    ResultSet resultSet = stm.executeQuery();
                    if (resultSet.next()) {
                        johnDowLabel1.setText(resultSet.getString(2) + " " + resultSet.getString(3));
                        johnDowLabel1.setVisible(true);
                        /// de pus in comboBox programarile acestui pacient
                        // (pt care inca nu s-a emis bon iar raportul medical a fost completat)
                        sql = "Select p.id_programare, p.id_policlinica, p.cnp_pacient, p.cnp_medic, p.data_consultatie, p.ora, p.ora_sfarsit, p.nrBon\n" +
                                "from programare p join raport_medical rm on p.id_programare = rm.id_programare\n" +
                                "where cnp_pacient=? and nrBon is null and rm.moment_parafare is not null";
                        stm = connection.prepareStatement(sql);
                        stm.setString(1, CNPbon.getText());
                        resultSet = stm.executeQuery();
                        while (resultSet.next()) {
                            Programare p = new Programare(resultSet);
                            comboProgramare.addItem(p.toString());
                        }
                    } else
                        pacientNeinregistratLabel1.setVisible(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        emitereBonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelMod3.removeAll();
                PanelMod3.add(EmitereBon);
                PanelMod3.repaint();
                PanelMod3.revalidate();
            }
        });

        comboProgramare.addActionListener(new ActionListener() {///cand s-a selectat programarea pt care se va emite bon...
            @Override
            public void actionPerformed(ActionEvent e) {
                bonText.setText("");
                totalBon = 0;
                int selectedIndex = comboProgramare.getSelectedIndex();
                if (selectedIndex >= 0) {
                    String idProg = comboProgramare.getSelectedItem().toString();
                    String arr[] = idProg.split(":");
                    id_programrePtCareSeEmiteBon = arr[0];
                    Integer idProgramareINT = Integer.parseInt(id_programrePtCareSeEmiteBon);
                    String sql;


                    String cnp_medic = getCnpMedic(connection, idProgramareINT);
                    String denumireSpecialitate = getSpecialitate(connection, idProgramareINT);
                    try {
                        Statement stm = connection.createStatement();
                        sql = "SELECT sf.id_serviciu, sf.denumire_serviciu, sf.pret, sf.durata FROM " +
                                "(SELECT ts.id_serviciu, ts.denumire_serviciu, ts.pret, ts.durata " +
                                "FROM " +
                                "(SELECT s.id_serviciu, s.denumire_serviciu, s.pret, s.durata, s.cnp " +
                                "FROM servicii_care_nu_sunt_custom_final AS s  " +
                                "WHERE s.cnp = ? " +
                                "UNION  " +
                                "SELECT sc.id_serviciu, sc.denumire_serviciu, sc.cost, sc.durata, sc.cnp_medic " +
                                "FROM servicii_custom_medic_cu_nume AS sc  " +
                                "WHERE sc.cnp_medic = ?) AS ts " +
                                "JOIN portofoliu_servicii AS ps " +
                                "ON ps.id_serviciu = ts.id_serviciu " +
                                "WHERE ps.denumire_specialitate = ? ORDER BY ts.denumire_serviciu) " +
                                "AS sf " +
                                "JOIN lista_servicii_client AS lsc " +
                                "ON sf.id_serviciu = lsc.id_serviciu " +
                                "WHERE lsc.id_programare = ?";
                        PreparedStatement pstm = connection.prepareStatement(sql);
                        pstm.setString(1, cnp_medic);
                        pstm.setString(2, cnp_medic);
                        pstm.setString(3, denumireSpecialitate);
                        pstm.setString(4, String.valueOf(idProgramareINT));
                        ResultSet resultSet = pstm.executeQuery();
                        while (resultSet.next()) {///fiecare serviciu din programare il pun in textfiel
                            Servicii_Medicale s = new Servicii_Medicale(resultSet);
                            totalBon += s.pret;
                            bonText.append(s.denumire_serviciu + "      " + String.valueOf(s.pret) + "\n");
                        }
                        bonText.append("\n\n\n TOTAL: " + String.valueOf(totalBon));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        anuleazaButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelMod3.removeAll();
                PanelMod3.add(activitatiOperationaleReceptioner);
                PanelMod3.repaint();
                PanelMod3.revalidate();
                clearFields();
            }
        });

        emiteBonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql = "UPDATE programare SET nrBon=? where id_programare=?";
                try {
                    PreparedStatement stm = connection.prepareStatement(sql);
                    stm.setInt(1, Integer.valueOf(id_programrePtCareSeEmiteBon));
                    stm.setInt(2, Integer.valueOf(id_programrePtCareSeEmiteBon));
                    if (stm.executeUpdate() == 1) {
                        sql = "UPDATE raport_medical set cost_final=? where id_programare=?";
                        stm = connection.prepareStatement(sql);
                        stm.setFloat(1, totalBon);
                        stm.setInt(2, Integer.valueOf(id_programrePtCareSeEmiteBon));
                        if (stm.executeUpdate() == 1) {
                            JOptionPane.showMessageDialog(getParent(), "Bonul a fost emis");
                            clearFields();
                        } else
                            JOptionPane.showMessageDialog(getParent(), "Nu s-a putut calcula costul final");
                    } else
                        JOptionPane.showMessageDialog(getParent(), "S-a prins hartia bonului in imprimanta");

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        creazaProgramareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String thisIsTheSelectedCabinet= String.valueOf(tabelCabinet.getValueAt(tabelCabinet.getSelectedRow(),0));
                dateProgramare[6]=thisIsTheSelectedCabinet;
                finalInsert(connection);
            }
        });
    }

    public String getSpecialitate(Connection connection, int idProgramare) {
        String sql="Select denumire_specialitate from lista_servicii_client\n" +
                "join portofoliu_servicii ps on ps.id_serviciu= lista_servicii_client.id_serviciu\n" +
                "where id_programare=?";
        try {
            PreparedStatement pstm= connection.prepareStatement(sql);
            pstm.setInt(1,idProgramare);
            ResultSet resultset =pstm.executeQuery();
            if(resultset.next())
                return resultset.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "blaaaaa";
    }

    public String getCnpMedic(Connection connection, int idProgramare) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT cnp_medic FROM programare" +
                    " WHERE id_programare = ?");
            statement.setString(1, String.valueOf(idProgramare));
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString("cnp_medic");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "blaa";
    }
}
