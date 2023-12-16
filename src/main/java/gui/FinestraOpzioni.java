package gui;

import controller.*;
import model.*;
import postgresDAO.ListinoPostgresDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class FinestraOpzioni {
    private JPanel panelOpzioni;
    private JButton creaUnaPaginaButton;
    private JButton cercaUnaPaginaButton;
    private JButton proponiUnaModificaButton;
    private JButton notificheButton;
    private JPanel panelCreaPagina;
    private JPanel panelCercaPagina;
    private JPanel panelProponiModifica;
    private JPanel panelNotifiche;
    private JButton annullaButton;
    private JPanel panelAnnulla;
    private JPanel panelInserisciFrase;
    private JButton inserisciFraseButton;
    private JButton storicoButton;
    private JPanel panelStorico;
    private static JFrame frame;
    private Controller controller;
    private static JFrame frameChiamante;
    private Utente u;
    private Pagina paginaDiAppartenenza;
    private Pagina paginaStorico;
    private Pagina paginaCercata;
    private ListinoPostgresDAO listinoPostgresDAO = new ListinoPostgresDAO();

    public FinestraOpzioni(JFrame frameChiamante, Controller controller, Utente u) {
        this.u = u;
        this.frameChiamante = frameChiamante;
        this.controller = controller;

        frame = new JFrame("Area Opzioni");
        frame.setContentPane(panelOpzioni);
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
        notificheButton.setEnabled(false);
        inserisciFraseButton.setEnabled(false);

        if (!(controller.esisteAlmenoUnaPagina())) {
            cercaUnaPaginaButton.setEnabled(false);
            proponiUnaModificaButton.setEnabled(false);
            inserisciFraseButton.setEnabled(false);
        }else{
            cercaUnaPaginaButton.setEnabled(true);
            proponiUnaModificaButton.setEnabled(true);
            inserisciFraseButton.setEnabled(true);
        }

        creaUnaPaginaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String titolo = JOptionPane.showInputDialog("Inserisci il titolo della pagina");
                    controller.setPagina(titolo, u);
                    JOptionPane.showMessageDialog(frame, "Complimenti " + u.getUsername() + " hai creato una pagina");

                    cercaUnaPaginaButton.setEnabled(true);
                    proponiUnaModificaButton.setEnabled(true);
                    notificheButton.setEnabled(true);
                    inserisciFraseButton.setEnabled(true);
                } catch (GiaEsistenteException tge) {
                    JOptionPane.showMessageDialog(frame, "Titolo già esistente.");
                } catch (NotABlankException ex) {
                    JOptionPane.showMessageDialog(frame, "Non puoi lasciare il campo vuoto.");
                } catch (LunghezzaMinimaException lme) {
                    JOptionPane.showMessageDialog(frame, "Il titolo deve essere lungo almeno 3 caratteri.");
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(frame, "Errore.");
                }

            }
        });
        cercaUnaPaginaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titolo2 = JOptionPane.showInputDialog("Inserisci il titolo della pagina che vuoi cercare");
                try {
                    paginaCercata = controller.getPagina(titolo2);
                    CercaPagina cercaPagina = new CercaPagina(frame, controller,paginaCercata);
                } catch (NotFoundException nfe) {
                    JOptionPane.showMessageDialog(frame, "Pagina non trovata.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Errore.");
                }
            }
        });
        proponiUnaModificaButton.addActionListener(new ActionListener() {
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
                    if(!(controller.getPagina(titolo).getAutore().getUsername().equals(u.getUsername()))){
                        throw new IllegalAccessException();
                    }
                    paginaDiAppartenenza = controller.getPagina(titolo);
                    InserisciFrase inserisciFrase = new InserisciFrase(frame, controller, paginaDiAppartenenza);
                } catch (IllegalAccessException iae) {
                    JOptionPane.showMessageDialog(frame,"Non sei l'autore di questa pagina");
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
        notificheButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FinestraNotifiche finestraNotifiche = new FinestraNotifiche(frame, controller, u);
            }
        });
        storicoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titolo = JOptionPane.showInputDialog("Inserisci il titolo della pagina di cui vuoi visionare lo storico: ");
                try {
                    paginaStorico = controller.getPagina(titolo);
                    Storico storico = new Storico(frame, controller, paginaStorico);
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
        panelNotifiche = new JPanel();
        panelNotifiche.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelOpzioni.add(panelNotifiche);
        notificheButton = new JButton();
        notificheButton.setText("Valuta Una Modifica");
        panelNotifiche.add(notificheButton);
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