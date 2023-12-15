package dao;

import controller.GiaEsistenteException;
import controller.NotABlankException;
import controller.NotFoundException;
import model.*;

import java.util.ArrayList;

public interface ListinoDAO {

    public void setUtente(String username, String password) throws Exception;
    public Utente getUtente(String username);

    public void setPagina(String titolo, Utente autore) throws GiaEsistenteException, NotABlankException;
    public Pagina getPagina(String titolo) throws NotFoundException;
    public int getIdPagina(String titolo);

    public void setFrase(String testo, Pagina pagina) throws NotABlankException;
    public ArrayList<Frase> getFrasi(Pagina pagina);

    public void setModifica(String testo, String usernamemodificatore, Frase frase, Pagina pagina);
    public Modifica getModifica(Frase frase) throws NotFoundException;

    public void setSchema();
    public boolean checkEsistenzaUtenti();
    public boolean checkEsistenzaPagine();
    public int numeroPagineCreateDaUnUtente(String username);
}
