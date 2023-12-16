package gui;

import controller.Controller;
import controller.NotFoundException;
import model.Modifica;
import model.Pagina;
import model.Utente;
import postgresDAO.ListinoPostgresDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FinestraNotifiche {
    private JPanel panelNotifiche;
    private JPanel panelNumeroNotifiche;
    private JLabel labelNumNotificheTitolo;
    private JPanel panelModifica;
    private JLabel labelTitoloModifica;
    private JLabel labelFraseDaModificare;
    private JLabel labelFraseTesto;
    private JLabel labelIndiceTitolo;
    private JLabel labelIndiceFrase;
    private JLabel labelTestoPropostoTitolo;
    private JLabel labelTestoProposto;
    private JLabel labelNumNotifiche;
    private JButton buttonApprova;
    private JButton buttonRifiuta;
    private JLabel labelPagina;
    private JLabel labelTitoloPagina;
    private Controller controller;
    private static JFrame frameChiamante;
    private Utente autore;
    private Modifica modifica;
    private ListinoPostgresDAO listinoPostgresDAO = new ListinoPostgresDAO();
    public FinestraNotifiche(JFrame frameChiamante, Controller controller, Utente autore) {
        this.autore = autore;
        this.frameChiamante = frameChiamante;
        this.controller = controller;

        JFrame frame = new JFrame("Area Notifiche");
        frame.setContentPane(panelNotifiche);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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

        labelNumNotifiche.setText(String.valueOf(controller.getNumeroModifichePerAutore(autore)));

        if (controller.getNumeroModifichePerAutore(autore) == 0) {
            labelPagina.setVisible(false);
            labelTitoloPagina.setVisible(false);
            labelTitoloModifica.setVisible(false);
            labelFraseDaModificare.setVisible(false);
            labelFraseTesto.setVisible(false);
            labelIndiceTitolo.setVisible(false);
            labelIndiceFrase.setVisible(false);
            labelTestoPropostoTitolo.setVisible(false);
            labelTestoProposto.setVisible(false);
            buttonApprova.setVisible(false);
            buttonRifiuta.setVisible(false);
        } else {
            labelPagina.setVisible(true);
            labelTitoloPagina.setVisible(true);
            labelTitoloModifica.setVisible(true);
            labelFraseDaModificare.setVisible(true);
            labelFraseTesto.setVisible(true);
            labelIndiceTitolo.setVisible(true);
            labelIndiceFrase.setVisible(true);
            labelTestoPropostoTitolo.setVisible(true);
            labelTestoProposto.setVisible(true);
            buttonApprova.setVisible(true);
            buttonRifiuta.setVisible(true);

            modifica = controller.getModificaPropostaMenoRecente(autore);
            labelTitoloPagina.setText(modifica.getFraseRiferita().getPaginaDiAppartenenza().getTitolo());
            labelFraseTesto.setText(modifica.getFraseRiferita().getTesto());
            labelIndiceFrase.setText(String.valueOf(modifica.getFraseRiferita().getIndice()));
            labelTestoProposto.setText(modifica.getTesto());
        }
        buttonApprova.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setValutazione(true, modifica, autore);
                JOptionPane.showMessageDialog(frame, "Modifica approvata correttamente");
                labelNumNotifiche.setText(String.valueOf(controller.getNumeroModifichePerAutore(autore)));
                if (controller.getNumeroModifichePerAutore(autore) == 0) {
                    labelPagina.setVisible(false);
                    labelTitoloPagina.setVisible(false);
                    labelTitoloModifica.setVisible(false);
                    labelFraseDaModificare.setVisible(false);
                    labelFraseTesto.setVisible(false);
                    labelIndiceTitolo.setVisible(false);
                    labelIndiceFrase.setVisible(false);
                    labelTestoPropostoTitolo.setVisible(false);
                    labelTestoProposto.setVisible(false);
                    buttonApprova.setVisible(false);
                    buttonRifiuta.setVisible(false);
                } else {
                    labelPagina.setVisible(true);
                    labelTitoloPagina.setVisible(true);
                    labelTitoloModifica.setVisible(true);
                    labelFraseDaModificare.setVisible(true);
                    labelFraseTesto.setVisible(true);
                    labelIndiceTitolo.setVisible(true);
                    labelIndiceFrase.setVisible(true);
                    labelTestoPropostoTitolo.setVisible(true);
                    labelTestoProposto.setVisible(true);
                    buttonApprova.setVisible(true);
                    buttonRifiuta.setVisible(true);

                    modifica = controller.getModificaPropostaMenoRecente(autore);
                    labelTitoloPagina.setText(modifica.getFraseRiferita().getPaginaDiAppartenenza().getTitolo());
                    labelFraseTesto.setText(modifica.getFraseRiferita().getTesto());
                    labelIndiceFrase.setText(String.valueOf(modifica.getFraseRiferita().getIndice()));
                    labelTestoProposto.setText(modifica.getTesto());
                }
            }
        });
        buttonRifiuta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setValutazione(false, modifica, autore);
                JOptionPane.showMessageDialog(frame, "Modifica rifiutata correttamente");
                labelNumNotifiche.setText(String.valueOf(controller.getNumeroModifichePerAutore(autore)));
                if (controller.getNumeroModifichePerAutore(autore) == 0) {
                    labelTitoloModifica.setVisible(false);
                    labelFraseDaModificare.setVisible(false);
                    labelFraseTesto.setVisible(false);
                    labelIndiceTitolo.setVisible(false);
                    labelIndiceFrase.setVisible(false);
                    labelTestoPropostoTitolo.setVisible(false);
                    labelTestoProposto.setVisible(false);
                    buttonApprova.setVisible(false);
                    buttonRifiuta.setVisible(false);
                } else {
                    labelTitoloModifica.setVisible(true);
                    labelFraseDaModificare.setVisible(true);
                    labelFraseTesto.setVisible(true);
                    labelIndiceTitolo.setVisible(true);
                    labelIndiceFrase.setVisible(true);
                    labelTestoPropostoTitolo.setVisible(true);
                    labelTestoProposto.setVisible(true);
                    buttonApprova.setVisible(true);
                    buttonRifiuta.setVisible(true);

                    modifica = controller.getModificaPropostaMenoRecente(autore); //TOOD prendere modifica proposta più recente senza valutazione
                    labelFraseTesto.setText(modifica.getFraseRiferita().getTesto());
                    labelIndiceFrase.setText(String.valueOf(modifica.getFraseRiferita().getIndice()));
                    labelTestoProposto.setText(modifica.getTesto());
                }
            }
        });
    }
}
