package gui;

import controller.Controller;
import model.Frase;
import controller.NotABlankException;
import model.Pagina;
import model.Utente;
import postgresDAO.ListinoPostgresDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * La gui InserisciFrase permette di inserire una frase in una pagina. A sua volta viene visualizzato il testo della pagina aggiornato.
 */
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
    private static JFrame frame;
    private static JFrame frameChiamante;
    private Controller controller;
    private Pagina pagina;
    private ListinoPostgresDAO listinoPostgresDAO = new ListinoPostgresDAO();

    /**
     * Instanzia un nuovo Inserisci frase.
     *
     * @param frameChiamante the frame chiamante
     * @param controller     the controller
     * @param pagina         the pagina
     */
    public InserisciFrase(JFrame frameChiamante, Controller controller, Pagina pagina) {
        this.frameChiamante = frameChiamante;
        this.controller = controller;
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
        frame.setSize(650,400);

        labelTesto.setHorizontalAlignment(SwingConstants.LEFT);
        labelTesto.setVerticalAlignment(SwingConstants.TOP);

        labelTitolo.setText("Titolo: " + pagina.getTitolo());
        String testoTotale = "<html>" + controller.getTestoTotaleAggiornato(pagina) + "</html>";
        labelTesto.setText(testoTotale);
        inserisciButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(textFieldFrase.getText().isBlank()) {
                        throw new NotABlankException();
                    }
                    else {
                        controller.setFrase(textFieldFrase.getText(), pagina);
                    }
                    labelTesto.setText("<html>" + controller.getTestoTotaleAggiornato(pagina) + "</html>");
                    textFieldFrase.setText("");
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
