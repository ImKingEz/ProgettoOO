package model;

import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * La classe Utente rappresenta un utente della wiki.
 */
public class Utente {
    /**
     * The Username.
     */
    protected String username;
    /**
     * The Password.
     */
    protected String password;
    private ArrayList<Pagina> pagineCercate = new ArrayList<Pagina>();
    private ArrayList<Modifica> modificheProposte = new ArrayList<Modifica>();
    private ArrayList<Pagina> pagineCreate = new ArrayList<Pagina>();
    private ArrayList<Valutazione> valutazioniEffettuate = new ArrayList<Valutazione>();

    /**
     * Instanzia un nuovo Utente.
     *
     * @param username l' username
     * @param password la password
     */
    public Utente(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets pagine cercate.
     *
     * @return the pagine cercate
     */
    public ArrayList<Pagina> getPagineCercate() {
        return pagineCercate;
    }

    /**
     * Gets modifiche proposte.
     *
     * @return the modifiche proposte
     */
    public ArrayList<Modifica> getModificheProposte() {
        return modificheProposte;
    }

    /**
     * Gets pagine create.
     *
     * @return the pagine create
     */
    public ArrayList<Pagina> getPagineCreate() {
        return pagineCreate;
    }

    /**
     * Gets valutazioni effettuate.
     *
     * @return the valutazioni effettuate
     */
    public ArrayList<Valutazione> getValutazioniEffettuate() {
        return valutazioniEffettuate;
    }
}
