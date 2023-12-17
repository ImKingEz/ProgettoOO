package controller;
import dao.ListinoDAO;
import model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

/**
 * La classe Controller rappresenta il controller del programma.
 */
public class Controller {
    private Pagina pagina;
    private Utente utente;
    private Frase frase;
    private ListinoDAO listinoPostgresDAO;

    /**
     * Instanzia un nuovo Controller.
     *
     * @param listinoPostgresDAO è il DAO che si occupa di gestire le funzioni di accesso al database
     */
    public Controller(ListinoDAO listinoPostgresDAO) {
        this.listinoPostgresDAO = listinoPostgresDAO;
    }

    /**
     * Sets utente.
     *
     * @param username l' username
     * @param password la password
     * @return l' utente
     * @throws InvalidLoginException    the invalid login exception
     * @throws GiaEsistenteException    the gia esistente exception
     * @throws LunghezzaMinimaException the lunghezza minima exception
     */
    public Utente setUtente(String username, String password) throws InvalidLoginException, GiaEsistenteException, LunghezzaMinimaException {
        if (username.isBlank() || password.isBlank())
            throw new InvalidLoginException();
        else if (password.length() < 6)
            throw new LunghezzaMinimaException();

        utente = new Utente(username, password);

        try {
            listinoPostgresDAO.setUtente(username, password);
        } catch (Exception e) {
            throw new GiaEsistenteException();
        }

        return utente;

    }

    /**
     * Esiste almeno una pagina boolean.
     *
     * @return the boolean
     */
    public boolean esisteAlmenoUnaPagina() {
        if (listinoPostgresDAO.checkEsistenzaPagine()){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets utente.
     * Dato un username restituisce l'utente corrispondente.
     *
     * @param username the username
     * @return the utente
     */
    public Utente getUtente(String username) {
        return listinoPostgresDAO.getUtente(username);
    }

    /**
     * Login.
     * Dato un username e una password, se l'utente esiste e la password è corretta, allora viene effettuato il login.
     *
     * @param username the username
     * @param password the password
     * @throws InvalidLoginException     the invalid login exception
     * @throws UsernameNotFoundException the username not found exception
     * @throws PasswordNotFoundException the password not found exception
     */
    public void login(String username, String password) throws InvalidLoginException, UsernameNotFoundException, PasswordNotFoundException {

        if (username.isBlank() || password.isBlank())
            throw new InvalidLoginException();

        utente = listinoPostgresDAO.getUtente(username);

        if(utente==null){
            throw new UsernameNotFoundException();
        }else if(!(utente.getPassword().equals(password))){
            throw new PasswordNotFoundException();
        }
    }

    /**
     * Almeno un autore o un utente boolean.
     * Se esiste almeno un utente allora restituisce true, altrimenti false.
     *
     * @return the boolean
     */
    public boolean almenoUnAutoreOUnUtente() {
        if(listinoPostgresDAO.checkEsistenzaUtenti()) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * Gets testo totale.
     *
     * @param pagina the pagina
     * @return the testo totale
     */
    public String getTestoTotale(Pagina pagina) {
        String appoggio = "";
        for(Frase f: listinoPostgresDAO.getFrasi(pagina)) {
            appoggio += f.getTesto() + " ";
            if(f.getPaginaLinkata() != null) {
                appoggio += appoggio + "(Link: " + frase.getPaginaLinkata().getTitolo() + ") ";
            }
        }
        return appoggio;
    }

    /**
     * Gets testo totale aggiornato.
     * Restituisce il testo totale aggiornato di una pagina. (Ossia l'ultima versione di ogni frase).
     *
     * @param pagina the pagina
     * @return the testo totale aggiornato
     */
    public String getTestoTotaleAggiornato(Pagina pagina) {
        String appoggio = "";
        for(Frase f: listinoPostgresDAO.getFrasiAggiornate(pagina)) {
            appoggio += f.getTesto() + " ";
            if(f.getPaginaLinkata() != null) {
                appoggio += appoggio + "(Link: " + frase.getPaginaLinkata().getTitolo() + ") ";
            }
        }
        return appoggio;
    }

    /**
     * Calcola indice int.
     * Calcola l'indice massimo di una pagina e restituisce il successivo.
     *
     * @param pagina the pagina
     * @return the int
     */
    public int calcolaIndice(Pagina pagina) {
        return listinoPostgresDAO.getFrasiAggiornate(pagina).size() + 1;
    }

    /**
     * Set schema.
     * Imposta lo schema "wiki" usato nel database.
     */
    public void setSchema(){
        listinoPostgresDAO.setSchema();
    }

    /**
     * Numero pagine create da un utente int.
     * restituisce il numero di pagine create da un utente.
     *
     * @param username the username
     * @return the int
     */
    public int numeroPagineCreateDaUnUtente(String username){
        return listinoPostgresDAO.numeroPagineCreateDaUnUtente(username);
    }

    /**
     * Sets pagina.
     *
     * @param titolo the titolo
     * @param autore the autore
     * @throws GiaEsistenteException    the gia esistente exception
     * @throws NotABlankException       the not a blank exception
     * @throws LunghezzaMinimaException the lunghezza minima exception
     */
    public void setPagina(String titolo, Utente autore) throws GiaEsistenteException, NotABlankException, LunghezzaMinimaException {
        listinoPostgresDAO.setPagina(titolo, autore);
    }

    /**
     * Gets pagina.
     *
     * @param titolo the titolo
     * @return the pagina
     * @throws NotFoundException the not found exception
     */
    public Pagina getPagina(String titolo) throws NotFoundException{
        return listinoPostgresDAO.getPagina(titolo);
    }

    /**
     * Sets frase.
     *
     * @param testo  the testo
     * @param pagina the pagina
     * @throws NotABlankException the not a blank exception
     * @throws NotFoundException  the not found exception
     */
    public void setFrase(String testo, Pagina pagina) throws NotABlankException, NotFoundException {
        listinoPostgresDAO.setFrase(testo, pagina);
    }

    /**
     * Sets frase.
     *
     * @param testo         the testo
     * @param pagina        the pagina
     * @param paginaLinkata the pagina linkata
     * @throws NotABlankException the not a blank exception
     * @throws NotFoundException  the not found exception
     */
    public void setFrase(String testo, Pagina pagina, Pagina paginaLinkata) throws NotABlankException, NotFoundException {
        listinoPostgresDAO.setFrase(testo, pagina, paginaLinkata);
    }

    /**
     * Get frasi array list.
     * restituisce tutte le frasi di una pagina.
     *
     * @param pagina the pagina
     * @return the array list
     */
    public ArrayList<Frase> getFrasi(Pagina pagina){
        return listinoPostgresDAO.getFrasi(pagina);
    }

    /**
     * Get frasi aggiornate array list.
     * restituisce tutte le frasi aggiornate di una pagina. (Ossia l'ultima versione di ogni frase).
     *
     * @param pagina the pagina
     * @return the array list
     */
    public ArrayList<Frase> getFrasiAggiornate(Pagina pagina){
        return listinoPostgresDAO.getFrasiAggiornate(pagina);
    }

    /**
     * Gets frasi con indici.
     * restituisce tutte le frasi di una pagina con i relativi indici.
     *
     * @param pagina the pagina
     * @return the frasi con indici
     */
    public String getFrasiConIndici(Pagina pagina) {
        String ret = "<html> <br>";

        for(Frase f: listinoPostgresDAO.getFrasi(pagina)) {
            ret += f.getIndice() + ") " + f.getTesto();
            if(f.getPaginaLinkata() != null) {
                ret += "(Link: " + f.getPaginaLinkata().getTitolo() + ")";
            }
            ret += " <br> ";
        }
        ret += " </html>";

        return ret;
    }

    /**
     * Gets frasi con indici aggiornato.
     * restituisce tutte le frasi aggiornate di una pagina con i relativi indici. (Ossia l'ultima versione di ogni frase).
     *
     * @param pagina the pagina
     * @return the frasi con indici aggiornato
     */
    public String getFrasiConIndiciAggiornato(Pagina pagina) {
        String ret = "<html> <br>";

        for(Frase f: listinoPostgresDAO.getFrasiAggiornate(pagina)) {
            ret += f.getIndice() + ") " + f.getTesto();
            if(f.getPaginaLinkata() != null) {
                ret += "(Link: " + f.getPaginaLinkata().getTitolo() + ")";
            }
            ret += " <br> ";
        }
        ret += " </html>";

        return ret;
    }

    /**
     * Sets modifica.
     *
     * @param testo                the testo
     * @param usernamemodificatore the usernamemodificatore
     * @param frase                the frase
     * @param pagina               the pagina
     * @throws AccettazioneAutomaticaException the accettazione automatica exception
     */
    public void setModifica(String testo, String usernamemodificatore, Frase frase, Pagina pagina) throws AccettazioneAutomaticaException {
        listinoPostgresDAO.setModifica(testo, usernamemodificatore, frase, pagina);
    }

    /**
     * Gets modifica.
     *
     * @param frase  the frase
     * @param ordine the ordine
     * @return the modifica
     * @throws NotFoundException        the not found exception
     * @throws IllegalArgumentException the illegal argument exception
     */
    public Modifica getModifica(Frase frase, String ordine) throws NotFoundException, IllegalArgumentException{
        return listinoPostgresDAO.getModifica(frase, ordine);
    }

    /**
     * Gets id modifica.
     *
     * @param modifica             the modifica
     * @param frase                the frase
     * @param pagina               the pagina
     * @param usernamemodificatore the usernamemodificatore
     * @return the id modifica
     * @throws NotFoundException the not found exception
     */
    public int getIdModifica(Modifica modifica, Frase frase, Pagina pagina, String usernamemodificatore) throws NotFoundException{
        return listinoPostgresDAO.getIdModifica(modifica, frase, pagina, usernamemodificatore);
    }

    /**
     * Set valutazione.
     *
     * @param accettazione the accettazione
     * @param modifica     the modifica
     * @param autore       the autore
     */
    public void setValutazione(boolean accettazione, Modifica modifica, Utente autore){
        listinoPostgresDAO.setValutazione(accettazione, modifica, autore);
    }

    /**
     * Gets valutazione.
     *
     * @param autore   the autore
     * @param modifica the modifica
     * @return the valutazione
     * @throws NotFoundException the not found exception
     */
    public Valutazione getValutazione(Utente autore, Modifica modifica) throws NotFoundException{
        return listinoPostgresDAO.getValutazione(autore, modifica);
    }

    /**
     * Gets numero modifiche per autore.
     * Restituisce il numero di modifiche che l'autore deve ancora valutare.
     *
     * @param autore the autore
     * @return the numero modifiche per autore
     */
    public int getNumeroModifichePerAutore(Utente autore) {
        return listinoPostgresDAO.getNumeroModifichePerAutore(autore);
    }

    /**
     * Get modifica proposta meno recente modifica.
     * Restituisce la modifica più vecchia che l'autore deve ancora valutare.
     *
     * @param autore the autore
     * @return the modifica
     */
    public Modifica getModificaPropostaMenoRecente(Utente autore){
        return listinoPostgresDAO.getModificaPropostaMenoRecente(autore);
    }
}
