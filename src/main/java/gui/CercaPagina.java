package gui;

import controller.Controller;
import controller.NotFoundException;
import model.Pagina;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * La gui CercaPagina permette di visualizzare una pagina cercata al suo stato corrente.
 */
public class CercaPagina {
    private JPanel panelPaginaCercata;
    private JPanel panelTitoloETesto;
    private JPanel panelTitolo;
    private JLabel labelTitolo;
    private JPanel panelTesto;
    private JLabel labelTesto;
    private JPanel panelTornaIndietro;
    private JButton tornaIndietroButton;
    private JPanel panelCercaPagina;
    private static JFrame frame;
    private static JFrame frameChiamante;
    private Controller controller;
    private Pagina pagina;

    /**
     * Instanzia un nuovo Cerca pagina.
     *
     * @param frameChiamante the frame chiamante
     * @param controller     the controller
     * @param pagina         the pagina
     */
    public CercaPagina(JFrame frameChiamante, Controller controller, Pagina pagina) throws NotFoundException {
        this.frameChiamante = frameChiamante;
        this.controller = controller;
        this.pagina=pagina;
        frame = new JFrame("Pagina Cercata");
        frame.setContentPane(panelPaginaCercata);
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
        frame.setSize(650,400);

        labelTesto.setHorizontalAlignment(SwingConstants.LEFT);
        labelTesto.setVerticalAlignment(SwingConstants.TOP);

        labelTitolo.setText("Titolo: " + pagina.getTitolo());
        String testoTotale = "<html>" + controller.getTestoTotaleAggiornato(pagina) + "</html>";
        labelTesto.setText(testoTotale);
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
