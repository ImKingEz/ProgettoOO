package dao;

import model.*;

public interface ListinoDAO {

    public void setUtente(String username, String password) throws Exception;
    public void setSchema();
    public Utente getUtente(String username);
    public boolean checkEsistenzaUtenti();
    public boolean checkEsistenzaPagine();
    public void setPagina(String titolo, Autore autore) throws Exception;
    public int numeroPagineCreateDaUnUtente(Utente utente);
}
