package graphicaluserinterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CalculatorView extends JFrame {

    private JTextField firstPolFld;
    private JTextField secondPolFld;
    private JButton a1Btn;
    private JButton a2Btn;
    private JButton plusBtn;

    private JLabel firstPolLbl;
    private JLabel secondPolLbl;
    private JButton addBtn;
    private JButton substractBtn;
    private JButton multiplicateBtn;
    private JButton divideBtn;
    private JButton deleteBtn;
    private JButton derivateBtn;
    private JButton clearBtn;

    private JLabel resultFld;
    private JLabel titleLbl;
    private JButton a3Btn;
    private JButton a4Btn;
    private JButton a5Btn;
    private JButton a6Btn;
    private JButton a7Btn;
    private JButton a8Btn;
    private JButton a9Btn;
    private JButton a0Btn;
    private JButton minusBtn;
    private JButton multBtn;
    private JButton powerBtn;
    private JButton xBtn;

    private JPanel calculatorPanel;
    private JButton nextBtn;
    private JButton integrateBtn;

    public CalculatorView() {
       setContentPane(calculatorPanel);
       setTitle("Polynomial Calculator");
       Dimension dimensions = Toolkit.getDefaultToolkit().getScreenSize();
       setSize(450, 600);
       int x = (int)((dimensions.getWidth()- this.getWidth())/2);
       int y = (int)((dimensions.getHeight()- this.getHeight())/2);
       this.setLocation(x,y);
       setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       setVisible(true);

       ImageIcon image = new ImageIcon("polynom_icon.png");
       setIconImage(image.getImage());

    }

    public String getResult() {
        return resultFld.getText();
    }

    public String getfirstPolFld() {
        return firstPolFld.getText();
    }

    public String getSecondPolFld() {
        return secondPolFld.getText();
    }

    public void setFirstPolFld(String string) {
        this.firstPolFld.setText(string);
    }

    public void setSecondPolFld(String string) {
        this.secondPolFld.setText(string);
    }

    void addSumListener(ActionListener listenForAddBtn){
        addBtn.addActionListener(listenForAddBtn);
    }
    void addSubListener(ActionListener listenForAddBtn){
        substractBtn.addActionListener(listenForAddBtn);
    }
    void addMultiplicateListener(ActionListener listenForAddBtn){
        multiplicateBtn.addActionListener(listenForAddBtn);
    }
    void addDerivativeListener(ActionListener listenForAddBtn){
        derivateBtn.addActionListener(listenForAddBtn);
    }
    void addIntegrateListener(ActionListener listenForAddBtn){
        integrateBtn.addActionListener(listenForAddBtn);
    }
    void addDivideListener(ActionListener listenForAddBtn){
        divideBtn.addActionListener(listenForAddBtn);
    }

    void addClearListener(ActionListener listenForClearBtn){
        clearBtn.addActionListener(listenForClearBtn);
    }
    void addDeleteListener(ActionListener listenForClearBtn){
        deleteBtn.addActionListener(listenForClearBtn);
    }

    void add0Listener(ActionListener listenForAddBtn){
        a0Btn.addActionListener(listenForAddBtn);
    }
    void add1Listener(ActionListener listenForAddBtn){
        a1Btn.addActionListener(listenForAddBtn);
    }
    void add2Listener(ActionListener listenForAddBtn){
        a2Btn.addActionListener(listenForAddBtn);
    }
    void add3Listener(ActionListener listenForAddBtn){
        a3Btn.addActionListener(listenForAddBtn);
    }
    void add4Listener(ActionListener listenForAddBtn){
        a4Btn.addActionListener(listenForAddBtn);
    }
    void add5Listener(ActionListener listenForAddBtn){
        a5Btn.addActionListener(listenForAddBtn);
    }
    void add6Listener(ActionListener listenForAddBtn){
        a6Btn.addActionListener(listenForAddBtn);
    }
    void add7Listener(ActionListener listenForAddBtn){
        a7Btn.addActionListener(listenForAddBtn);
    }
    void add8Listener(ActionListener listenForAddBtn){
        a8Btn.addActionListener(listenForAddBtn);
    }
    void add9Listener(ActionListener listenForAddBtn){
        a9Btn.addActionListener(listenForAddBtn);
    }

    void addPlusListener(ActionListener listenForAddBtn){
        plusBtn.addActionListener(listenForAddBtn);
    }
    void addMinusListener(ActionListener listenForAddBtn){
        minusBtn.addActionListener(listenForAddBtn);
    }
    void addMultListener(ActionListener listenForAddBtn){
        multBtn.addActionListener(listenForAddBtn);
    }
    void addPowerListener(ActionListener listenForAddBtn){
        powerBtn.addActionListener(listenForAddBtn);
    }
    void addXListener(ActionListener listenForAddBtn){
        xBtn.addActionListener(listenForAddBtn);
    }

    void addNextListener(ActionListener listenForAddBtn){
        nextBtn.addActionListener(listenForAddBtn);
    }

    public JLabel getResultFld() {
        return resultFld;
    }

    public void setResultFld(String string) {
        this.resultFld.setText(string);
    }

    public JPanel getCalculatorPanel() {
        return calculatorPanel;
    }

    public void setCalculatorPanel() {
        this.calculatorPanel = calculatorPanel;
    }

    public JLabel getFirstPolLbl() {
        return firstPolLbl;
    }

    public JLabel getSecondPolLbl() {
        return secondPolLbl;
    }

}
