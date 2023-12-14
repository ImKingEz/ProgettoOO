package gui;

import controller.Controller;
import model.NotABlankException;
import model.Pagina;
import model.Utente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InserisciFrase {
    private JPanel panelInserisciFrase;
    private JPanel panelTitolo;
    private JPanel panelTesto;
    private JPanel panelTitoloETesto;
    private JPanel panelFrase;
    private JPanel panelTestoFrase;
    private JButton inserisciButton;
    private JTextField textFieldFrase;
    private JPanel panelInserisci;
    private JLabel labelTitolo;
    private JLabel labelTesto;
    private JButton tornaIndietroButton;
    private JPanel panelTornaIndietro;
    private JTextField textFieldLink;
    private JPanel panelTestoLink;
    private static JFrame frame;
    private static JFrame frameChiamante;
    private Controller controller;
    private Utente u;
    private Pagina pagina;
    public InserisciFrase(JFrame frameChiamante, Controller controller, Utente u, Pagina pagina) {
        this.frameChiamante = frameChiamante;
        this.controller = controller;
        this.u = u;
        this.pagina = pagina;

        frame = new JFrame("Modellazione pagina");
        frame.setContentPane(panelInserisciFrase);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Evita la chiusura automatica
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Logica quando la finestra di inserimento autore sta chiudendo
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });
        frame.pack();
        frameChiamante.setVisible(false);
        frame.setVisible(true);
        frame.setSize(600,600);

        labelTitolo.setText("Titolo: " + pagina.getTitolo());
        String testoTotale = "<html>" + controller.getTestoTotale(pagina.getTesto()) + "</html>";
        labelTesto.setText(testoTotale);
        inserisciButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(textFieldLink.getText() == "") {
                        controller.aggiungiFraseInTesto(pagina.getTesto(), textFieldFrase.getText());
                    }
                    else {
                        Pagina paginaAppoggio = controller.getPagina(textFieldLink.getText());
                        controller.aggiungiFraseInTesto(pagina.getTesto(), textFieldFrase.getText(), paginaAppoggio);
                    }
                    labelTesto.setText("<html>" + controller.getTestoTotale(pagina.getTesto()) + "</html>");
                    textFieldFrase.setText("");
                    textFieldLink.setText("");
                } catch (NotABlankException nabe) {
                    JOptionPane.showMessageDialog(frame,"Non puoi inserire un testo vuoto.");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });
    }
}
