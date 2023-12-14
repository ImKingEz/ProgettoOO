package gui;

import controller.Controller;
import model.Pagina;
import model.Utente;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ProponiUnaModifica {
    private final JFrame frameChiamante;
    private final Controller controller;
    private final Utente u;
    private final Pagina pagina;
    private JPanel panelProponiUnaModifica;
    private JPanel panelListaFrasi;
    private JLabel labelFrasi;
    private JTextField textFieldInserisciIndice;
    private JPanel panelInserisciIndice;
    private JTextField textFieldTestoProposto;
    private JLabel labelInserisciTesto;
    private JLabel labelInserisciIndice;

    public ProponiUnaModifica(JFrame frameChiamante, Controller controller, Utente u, Pagina pagina) {
        this.frameChiamante = frameChiamante;
        this.controller = controller;
        this.u = u;
        this.pagina = pagina;

        JFrame frame = new JFrame("Modellazione pagina");
        frame.setContentPane(panelProponiUnaModifica);
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
        frame.setSize(600, 600);
    }
}