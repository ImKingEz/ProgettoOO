package model;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Utente {
    protected String username;
    protected String password;
    private ArrayList<Pagina> pagineCercate = new ArrayList<Pagina>();
    private ArrayList<Modifica> modificheProposte = new ArrayList<Modifica>();
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
}
