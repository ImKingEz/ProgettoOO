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
    public ArrayList<Frase> getFrasi(Pagina pagina);

    public void setModifica(String testo, String usernamemodificatore, Frase frase, Pagina pagina) throws AccettazioneAutomaticaException;
    public Modifica getModifica(Frase frase, String ordine) throws NotFoundException, IllegalArgumentException;
    public int getIdModifica(Modifica modifica, Frase frase, Pagina pagina) throws NotFoundException;

    public void setValutazione(boolean accettazione, Modifica modifica, Utente autore);
    public Valutazione getValutazione(Utente autore, Modifica modifica) throws NotFoundException;
    public ArrayList<Frase> getFrasiAggiornate(Pagina pagina);
    public void setSchema();
    public boolean checkEsistenzaUtenti();
    public boolean checkEsistenzaPagine();
    public int numeroPagineCreateDaUnUtente(String username);
}
