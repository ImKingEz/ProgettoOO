package dao;

import controller.*;
import model.*;

import java.util.ArrayList;

public interface ListinoDAO {

    public void setUtente(String username, String password) throws Exception;
    public Utente getUtente(String username);

    public void setPagina(String titolo, Utente autore) throws GiaEsistenteException, NotABlankException , LunghezzaMinimaException;
    public Pagina getPagina(String titolo) throws NotFoundException;
    public int getIdPagina(String titolo) throws NotFoundException;

    public void setFrase(String testo, Pagina pagina) throws NotABlankException, NotFoundException;
    void setFrase(String testo, Pagina pagina, Pagina paginaLinkata) throws NotABlankException, NotFoundException;;
    public ArrayList<Frase> getFrasi(Pagina pagina);
    public Frase getFrase(String testo, Pagina pagina) throws NotFoundException;

    public void setModifica(String testo, String usernamemodificatore, Frase frase, Pagina pagina) throws AccettazioneAutomaticaException;
    public Modifica getModifica(Frase frase, String ordine) throws NotFoundException, IllegalArgumentException;
    public int getIdModifica(Modifica modifica, Frase frase, Pagina pagina, String usernamemodificatore) throws NotFoundException;
    public int getNumeroModifichePerAutore(Utente autore);

    public void setValutazione(boolean accettazione, Modifica modifica, Utente autore);
    public Valutazione getValutazione(Utente autore, Modifica modifica) throws NotFoundException;

    public ArrayList<Frase> getFrasiAggiornate(Pagina pagina) throws NotFoundException;


    public void setSchema();
    public boolean checkEsistenzaUtenti();
    public boolean checkEsistenzaPagine();
    public int numeroPagineCreateDaUnUtente(String username);

    Modifica getModificaPropostaMenoRecente(Utente autore);
}
