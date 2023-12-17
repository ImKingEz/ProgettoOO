package dao;

import controller.*;
import model.*;

import java.util.ArrayList;

/**
 * The interface Listino dao.
 */
public interface ListinoDAO {

    /**
     * Sets utente.
     *
     * @param username the username
     * @param password the password
     * @throws Exception the exception
     */
    public void setUtente(String username, String password) throws Exception;

    /**
     * Gets utente.
     *
     * @param username the username
     * @return the utente
     */
    public Utente getUtente(String username);

    /**
     * Sets pagina.
     *
     * @param titolo the titolo
     * @param autore the autore
     * @throws GiaEsistenteException    the gia esistente exception
     * @throws NotABlankException       the not a blank exception
     * @throws LunghezzaMinimaException the lunghezza minima exception
     */
    public void setPagina(String titolo, Utente autore) throws GiaEsistenteException, NotABlankException , LunghezzaMinimaException;

    /**
     * Gets pagina.
     *
     * @param titolo the titolo
     * @return the pagina
     * @throws NotFoundException the not found exception
     */
    public Pagina getPagina(String titolo) throws NotFoundException;

    /**
     * Gets id pagina.
     *
     * @param titolo the titolo
     * @return the id pagina
     * @throws NotFoundException the not found exception
     */
    public int getIdPagina(String titolo) throws NotFoundException;

    /**
     * Sets frase.
     *
     * @param testo  the testo
     * @param pagina the pagina
     * @throws NotABlankException the not a blank exception
     * @throws NotFoundException  the not found exception
     */
    public void setFrase(String testo, Pagina pagina) throws NotABlankException, NotFoundException;

    /**
     * Sets frase.
     *
     * @param testo         the testo
     * @param pagina        the pagina
     * @param paginaLinkata the pagina linkata
     * @throws NotABlankException the not a blank exception
     * @throws NotFoundException  the not found exception
     */
    void setFrase(String testo, Pagina pagina, Pagina paginaLinkata) throws NotABlankException, NotFoundException;;

    /**
     * Gets frasi.
     *
     * @param pagina the pagina
     * @return the frasi
     */
    public ArrayList<Frase> getFrasi(Pagina pagina);

    /**
     * Gets frase.
     *
     * @param testo  the testo
     * @param pagina the pagina
     * @return the frase
     * @throws NotFoundException the not found exception
     */
    public Frase getFrase(String testo, Pagina pagina) throws NotFoundException;

    /**
     * Sets modifica.
     *
     * @param testo                the testo
     * @param usernamemodificatore the usernamemodificatore
     * @param frase                the frase
     * @param pagina               the pagina
     * @throws AccettazioneAutomaticaException the accettazione automatica exception
     */
    public void setModifica(String testo, String usernamemodificatore, Frase frase, Pagina pagina) throws AccettazioneAutomaticaException;

    /**
     * Gets modifica.
     *
     * @param frase  the frase
     * @return the modifica
     * @throws NotFoundException        the not found exception
     * @throws IllegalArgumentException the illegal argument exception
     */
    public Modifica getModifica(Frase frase) throws NotFoundException, IllegalArgumentException;
    /**
     * Gets modifica.
     *
     * @param frase  the frase
     * @return the modifica
     * @throws NotFoundException        the not found exception
     * @throws IllegalArgumentException the illegal argument exception
     */
    public Modifica getModifica2(Frase frase) throws NotFoundException, IllegalArgumentException;

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
    public int getIdModifica(Modifica modifica, Frase frase, Pagina pagina, String usernamemodificatore) throws NotFoundException;

    /**
     * Gets numero modifiche per autore.
     *
     * @param autore the autore
     * @return the numero modifiche per autore
     */
    public int getNumeroModifichePerAutore(Utente autore);

    /**
     * Sets valutazione.
     *
     * @param accettazione the accettazione
     * @param modifica     the modifica
     * @param autore       the autore
     */
    public void setValutazione(boolean accettazione, Modifica modifica, Utente autore);

    /**
     * Gets valutazione.
     *
     * @param autore   the autore
     * @param modifica the modifica
     * @return the valutazione
     * @throws NotFoundException the not found exception
     */
    public Valutazione getValutazione(Utente autore, Modifica modifica) throws NotFoundException;

    /**
     * Gets frasi aggiornate.
     *
     * @param pagina the pagina
     * @return the frasi aggiornate
     */
    public ArrayList<Frase> getFrasiAggiornate(Pagina pagina) throws NotFoundException;


    /**
     * Sets schema.
     */
    public void setSchema();

    /**
     * Check esistenza utenti boolean.
     *
     * @return the boolean
     */
    public boolean checkEsistenzaUtenti();

    /**
     * Check esistenza pagine boolean.
     *
     * @return the boolean
     */
    public boolean checkEsistenzaPagine();

    /**
     * Numero pagine create da un utente int.
     *
     * @param username the username
     * @return the int
     */
    public int numeroPagineCreateDaUnUtente(String username);

    /**
     * Gets modifica proposta meno recente.
     *
     * @param autore the autore
     * @return the modifica proposta meno recente
     */
    Modifica getModificaPropostaMenoRecente(Utente autore);
}
