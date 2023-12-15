package gui;

import controller.Controller;
import controller.invalidLoginException;
import controller.GiaEsistenteException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FinestraInserisciAutore {
    private JPanel panelInserisciAutore;
    private JTextField usernameInseritoTextField;
    private JTextField passwordInseritaField;
    private JButton registrazioneButton;
    private JPanel panelInserisciUsername;
    private JPanel panelInseritoPassword;
    private JPanel panelButtonInseritiValori;
    private JButton annullaButton;
    private JPanel panelButtonAnnulla;
    private static JFrame frameChiamante;
    private static JFrame frame;
    private Controller controller;
    private String usernameInserito;
    private String passwordInserita;

    public FinestraInserisciAutore(JFrame frameChiamante, Controller controller) {
        this.frameChiamante = frameChiamante;
        this.controller = controller;
        frame = new JFrame("Registrazione Autore");
        frame.setContentPane(panelInserisciAutore);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Evita la chiusura automatica
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Logica quando la finestra di inserimento autore sta chiudendo
                // Puoi inserire qui eventuali operazioni di pulizia o gestione della chiusura
                frameChiamante.setVisible(true);
                frame.dispose(); // Chiude la finestra di inserimento autore
            }
        });
        frame.pack();
        frameChiamante.setVisible(false);
        frame.setVisible(true);

        usernameInseritoTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        passwordInseritaField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        registrazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usernameInserito = usernameInseritoTextField.getText();
                String passwordChars = passwordInseritaField.getText();  // per ottenere la password
                passwordInserita = new String(passwordChars);
                try {
                    controller.setAutore(usernameInserito, passwordInserita);
                    JOptionPane.showMessageDialog(frame, "Ciao " + usernameInserito + " benvenuto nel nostro sistema");
                    frameChiamante.setVisible(true);
                    frame.setVisible(false);
                    frame.dispose();
                } catch (invalidLoginException il) {
                    JOptionPane.showMessageDialog(frame, "Non puoi lasciare un campo vuoto.");
                } catch (GiaEsistenteException ex) {
                    JOptionPane.showMessageDialog(frame, "L'username inserito è già esistente");
                }
            }
        });
        annullaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.setVisible(false);
                frame.dispose();
            }
        });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelInserisciAutore = new JPanel();
        panelInserisciAutore.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelInserisciUsername = new JPanel();
        panelInserisciUsername.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelInserisciUsername.setPreferredSize(new Dimension(102, 40));
        panelInserisciAutore.add(panelInserisciUsername);
        usernameInseritoTextField = new JTextField();
        usernameInseritoTextField.setPreferredSize(new Dimension(92, 30));
        panelInserisciUsername.add(usernameInseritoTextField);
        panelInseritoPassword = new JPanel();
        panelInseritoPassword.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelInseritoPassword.setPreferredSize(new Dimension(102, 40));
        panelInserisciAutore.add(panelInseritoPassword);
        passwordInseritaField = new JPasswordField();
        passwordInseritaField.setPreferredSize(new Dimension(92, 30));
        panelInseritoPassword.add(passwordInseritaField);
        panelButtonInseritiValori = new JPanel();
        panelButtonInseritiValori.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelInserisciAutore.add(panelButtonInseritiValori);
        registrazioneButton = new JButton();
        registrazioneButton.setText("Registrati");
        panelButtonInseritiValori.add(registrazioneButton);
        panelButtonAnnulla = new JPanel();
        panelButtonAnnulla.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelInserisciAutore.add(panelButtonAnnulla);
        annullaButton = new JButton();
        annullaButton.setMaximumSize(new Dimension(92, 30));
        annullaButton.setMinimumSize(new Dimension(92, 30));
        annullaButton.setPreferredSize(new Dimension(92, 30));
        annullaButton.setText("Annulla");
        panelButtonAnnulla.add(annullaButton);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelInserisciAutore;
    }

}
