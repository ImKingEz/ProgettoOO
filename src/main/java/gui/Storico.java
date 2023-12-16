package gui;

import controller.Controller;
import model.Pagina;
import model.Utente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Storico {
    private JPanel panelPaginaStorico;
    private JPanel panelTitoloETesto;
    private JPanel panelTitolo;
    private JLabel labelTitolo;
    private JPanel panelTesto;
    private JLabel labelTesto;
    private JPanel panelTornaIndietro;
    private JButton tornaIndietroButton;
    private JPanel panelStorico;
    private static JFrame frame;
    private Controller controller;
    private static JFrame frameChiamante;
    private Pagina pagina;

    public Storico(JFrame frameChiamante, Controller controller, Pagina pagina) {
        this.frameChiamante = frameChiamante;
        this.controller = controller;
        this.pagina=pagina;
        frame = new JFrame("Pagina Cercata");
        frame.setContentPane(panelStorico);
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
        labelTesto.setVerticalAlignment(SwingConstants.CENTER);

        labelTitolo.setText("Titolo: " + pagina.getTitolo());
        String testoTotale = "<html>" + controller.getFrasiConIndici(pagina) + "</html>";
        labelTesto.setText(testoTotale);
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.setVisible(false);
                frame.dispose();
            }
        });
    }
}
