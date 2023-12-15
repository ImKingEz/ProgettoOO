package gui;

import controller.Controller;
import controller.GiaEsistenteException;
import controller.NotABlankException;
import model.*;
import postgresDAO.ListinoPostgresDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FinestraOpzioni {
    private JPanel panelOpzioni;
    private JButton creaUnaPaginaButton;
    private JButton cercaUnaPaginaButton;
    private JButton proponiUnaModificaButton;
    private JButton valutaUnaModificaButton;
    private JPanel panelCreaPagina;
    private JPanel panelCercaPagina;
    private JPanel panelProponiModifica;
    private JPanel panelValutaModifica;
    private JButton annullaButton;
    private JPanel panelAnnulla;
    private JPanel panelInserisciFrase;
    private JButton inserisciFraseButton;
    private static JFrame frame;
    private Controller controller;
    private static JFrame frameChiamante;
    private Utente u;
    private Pagina paginaDiAppartenenza;
    private Pagina paginaCercata;
    private String identita;
    private ListinoPostgresDAO listinoPostgresDAO = new ListinoPostgresDAO();

    public FinestraOpzioni(JFrame frameChiamante, Controller controller, Utente u, String identita) {
        this.u = u;
        this.frameChiamante = frameChiamante;
        this.controller = controller;
        this.identita = identita;

        frame = new JFrame("Area Opzioni");
        frame.setContentPane(panelOpzioni);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frameChiamante.setVisible(false);
        frame.setVisible(true);
        valutaUnaModificaButton.setEnabled(false);
        inserisciFraseButton.setEnabled(false);

        if (identita.equals("utente")) {
            valutaUnaModificaButton.setEnabled(false);
            inserisciFraseButton.setEnabled(false);
        }

        if (!(controller.esisteAlmenoUnaPagina())) {
            cercaUnaPaginaButton.setEnabled(false);
            proponiUnaModificaButton.setEnabled(false);
        }
        creaUnaPaginaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String titolo = JOptionPane.showInputDialog("Inserisci il titolo della pagina");
                    listinoPostgresDAO.setPagina(titolo, u);
                    JOptionPane.showMessageDialog(frame, "Ciao " + u.getUsername() + " hai creato una pagina");

                    cercaUnaPaginaButton.setEnabled(true);
                    proponiUnaModificaButton.setEnabled(true);
                    valutaUnaModificaButton.setEnabled(true);
                    inserisciFraseButton.setEnabled(true);
                } catch (GiaEsistenteException tge) {
                    JOptionPane.showMessageDialog(frame, "Titolo già esistente.");
                } catch (NotABlankException ex) {
                    JOptionPane.showMessageDialog(frame, "Non puoi lasciare il campo vuoto.");
                }
//                catch (Exception e1) {
//                    JOptionPane.showMessageDialog(frame, "Errore.");
//                }

            }
        });
        cercaUnaPaginaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titolo2 = JOptionPane.showInputDialog("Inserisci il titolo della pagina che vuoi cercare");
                try {
                    paginaCercata = controller.getPagina(titolo2);
                    CercaPagina cercaPagina = new CercaPagina(frame, controller,paginaCercata);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame,"Nessuna pagina trovata");
                }
            }
        });
        proponiUnaModificaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        valutaUnaModificaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
        inserisciFraseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titolo = JOptionPane.showInputDialog("Inserisci il titolo della pagina in cui vuoi inserire la frase: ");
                try {
                    paginaDiAppartenenza = controller.getPagina(titolo);
                    InserisciFrase inserisciFrase = new InserisciFrase(frame, controller, u, paginaDiAppartenenza);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame,"Nessuna pagina trovata");
                }
            }
        });
        proponiUnaModificaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titolo = JOptionPane.showInputDialog("Inserisci il titolo della pagina in cui vuoi inserire la frase: ");
                try {
                    paginaDiAppartenenza = controller.getPagina(titolo);
                    ProponiUnaModifica proponiUnaModifica = new ProponiUnaModifica(frame, controller, u, paginaDiAppartenenza);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame,"Nessuna pagina trovata");
                }
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
        panelOpzioni = new JPanel();
        panelOpzioni.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelCreaPagina = new JPanel();
        panelCreaPagina.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelOpzioni.add(panelCreaPagina);
        creaUnaPaginaButton = new JButton();
        creaUnaPaginaButton.setText("Crea Una Pagina");
        panelCreaPagina.add(creaUnaPaginaButton);
        panelCercaPagina = new JPanel();
        panelCercaPagina.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelOpzioni.add(panelCercaPagina);
        cercaUnaPaginaButton = new JButton();
        cercaUnaPaginaButton.setText("Cerca Una Pagina");
        panelCercaPagina.add(cercaUnaPaginaButton);
        panelProponiModifica = new JPanel();
        panelProponiModifica.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelOpzioni.add(panelProponiModifica);
        proponiUnaModificaButton = new JButton();
        proponiUnaModificaButton.setText("Proponi Una Modifica");
        panelProponiModifica.add(proponiUnaModificaButton);
        panelValutaModifica = new JPanel();
        panelValutaModifica.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelOpzioni.add(panelValutaModifica);
        valutaUnaModificaButton = new JButton();
        valutaUnaModificaButton.setText("Valuta Una Modifica");
        panelValutaModifica.add(valutaUnaModificaButton);
        panelAnnulla = new JPanel();
        panelAnnulla.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelOpzioni.add(panelAnnulla);
        annullaButton = new JButton();
        annullaButton.setText("Torna All'Area Login");
        panelAnnulla.add(annullaButton);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelOpzioni;
    }

}