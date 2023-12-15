package dao;

import controller.GiaEsistenteException;
import controller.NotABlankException;
import model.*;

public interface ListinoDAO {

    public void setUtente(String username, String password) throws Exception;
    public void setSchema();
    public Utente getUtente(String username);
    public boolean checkEsistenzaUtenti();
    public boolean checkEsistenzaPagine();
    public void setPagina(String titolo, Utente autore) throws GiaEsistenteException, NotABlankException;
    public int numeroPagineCreateDaUnUtente(String username);
}
