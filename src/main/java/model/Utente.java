package model;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Utente {
    protected String username;
    protected String password;
    private ArrayList<Pagina> pagineCercate = new ArrayList<Pagina>();
    private ArrayList<Modifica> modificheProposte = new ArrayList<Modifica>();
    private ArrayList<Pagina> pagineCreate = new ArrayList<Pagina>();
    private ArrayList<Valutazione> valutazioniEffettuate = new ArrayList<Valutazione>();
    public Utente(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }
    public ArrayList<Pagina> getPagineCercate() {
        return pagineCercate;
    }

    public ArrayList<Modifica> getModificheProposte() {
        return modificheProposte;
    }
    public ArrayList<Pagina> getPagineCreate() {
        return pagineCreate;
    }
    public ArrayList<Valutazione> getValutazioniEffettuate() {
        return valutazioniEffettuate;
    }
}
