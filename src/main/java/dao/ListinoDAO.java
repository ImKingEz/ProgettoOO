package dao;

import model.Utente;

public interface ListinoDAO {

    void setUtente(String username, String password);
    public Utente getUtente(String username);
}
