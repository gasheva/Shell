package ru.gasheva.consultation;

import ru.gasheva.models.DomainModel;
import ru.gasheva.models.RuleModel;
import ru.gasheva.models.VariableModel;
import ru.gasheva.models.classes.Domain;
import ru.gasheva.models.classes.DomainValue;
import ru.gasheva.models.classes.VarType;
import ru.gasheva.models.classes.Variable;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ConsultationForm extends JDialog{
    private JPanel contentPane;
    private JButton btnAnswer;
    private JButton buttonCancel;
    private JButton btnNewConsultation;
    private JButton btnExplanation;
    private JPanel imagePanel;
    private JLabel lblImage;
    private JPanel btnsPanel;
    private ConsultationControl control;
    private RuleModel ruleModel;
    private VariableModel variableModel;
    private DomainModel domainModel;

    public ConsultationForm(ConsultationControl control, RuleModel ruleModel, VariableModel variableModel, DomainModel domainModel) {
        this.ruleModel = ruleModel;
        this.variableModel = variableModel;
        this.domainModel = domainModel;
        this.control = control;
    }

    public String askGlobalTarget(){
        return createDialog("Выберите целевую переменную", variableModel.getVariables().stream().filter(x->x.getVarType() == VarType.RESOLVE).map(Variable::getName).toArray(String[]::new));
    }
    public void createView(){
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnAnswer);
        createControls();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void createControls(){

        btnNewConsultation.addActionListener(e->btnNewConsultationClicked());
        btnExplanation.addActionListener(e->btnExplanationClicked());
        try{
            BufferedImage myPicture = ImageIO.read(new File("src/main/resources/images/_title.jpg"));
            lblImage.setIcon(new ImageIcon(myPicture));
        } catch (IOException e) {
            System.out.println("Не удалось открыть изображение");
            e.printStackTrace();
        }

        btnsPanel.setBorder(new CompoundBorder(new LineBorder(new Color(0x162851)), new EmptyBorder(2, 2, 2, 2)));
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void btnNewConsultationClicked(){control.startNewConsultation();}
    private void btnExplanationClicked(){control.explainAnswer();}
    private void onOK() {
        control.ok();
    }
    private void onCancel() {
        control.cancel();
    }
    public void Dispose(){
        dispose();
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
    public String askVarValue(Variable var){
        Domain d = var.getDomain();
        String[] values = new String[d.domainValuesSize()];
        for(int i=0; i<d.domainValuesSize(); i++){
            values[i]=d.getDomainValue(i).getValue();
        }
        return createDialog(var.getQuestion(), values);
    }
    public String createDialog(String labelText, String[] cbItems){
        JComboBox cb = new JComboBox();
        for(int i=0; i<cbItems.length; i++){
            cb.addItem(cbItems[i]);
        }
        cb.setSelectedItem(0);
        JComponent[] inputs = new JComponent[]{
                new JLabel(labelText),
                cb
        };
        String answer=null;
        int result = JOptionPane.showConfirmDialog(this, inputs, "", JOptionPane.PLAIN_MESSAGE);
        if (result==JOptionPane.OK_OPTION){
            answer = cb.getSelectedItem().toString();
        }
        return answer;
    }
}
