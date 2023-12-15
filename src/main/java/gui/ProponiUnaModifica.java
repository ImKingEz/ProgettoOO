package gui;

import controller.Controller;
import controller.NotFoundException;
import model.Frase;
import model.Pagina;
import model.Utente;
import postgresDAO.ListinoPostgresDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JButton proponiLaModificaButton;
    private JLabel labelListaFrasi;
    private JButton annullaButton;

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

        labelListaFrasi.setHorizontalAlignment(SwingConstants.LEFT);
        labelListaFrasi.setVerticalAlignment(SwingConstants.TOP);

        String testofrasi = controller.getFrasiConIndici(pagina);
        labelListaFrasi.setText(testofrasi);
        annullaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.setVisible(false);
                frame.dispose();
            }
        });
        proponiLaModificaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String indice = textFieldInserisciIndice.getText();
                String testoProposto = textFieldTestoProposto.getText();
                if (indice.isBlank() || testoProposto.isBlank()) {
                    JOptionPane.showMessageDialog(frame, "Non puoi lasciare un campo vuoto.");
                } else {
                    try {
                        for(Frase f: controller.getFrasi(pagina)) {
                            if (f.getIndice() == Integer.parseInt(indice)) {
                                controller.setModifica(testoProposto, u.getUsername(), f, pagina);
                            }
                        }
                        JOptionPane.showMessageDialog(frame, "Modifica proposta correttamente, attendi la valutazione da parte dell'utente");
                        frameChiamante.setVisible(true);
                        frame.setVisible(false);
                        frame.dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Non puoi lasciare un campo vuoto.");
                    }
                }
            }
        });
    }
}