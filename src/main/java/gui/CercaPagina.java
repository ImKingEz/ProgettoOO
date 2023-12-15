package gui;

import controller.Controller;
import model.Pagina;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

    public CercaPagina(JFrame frameChiamante, Controller controller, Pagina pagina) {
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

        labelTitolo.setText("Titolo: " + pagina.getTitolo());
        String testoTotale = "<html>" + controller.getTestoTotale(pagina) + "</html>";
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
