package model;

import java.util.ArrayList;

public class Autore extends Utente {
    private ArrayList<Pagina> pagineCreate = new ArrayList<Pagina>();
    private ArrayList<Valutazione> valutazioniEffettuate = new ArrayList<Valutazione>();
    public Autore(String username, String password) {
        super(username, password);
    }

    public ArrayList<Pagina> getPagineCreate() {
        return pagineCreate;
    }

    public ArrayList<Valutazione> getValutazioniEffettuate() {
        return valutazioniEffettuate;
    }
}
