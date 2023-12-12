package controller;
import model.*;

import java.util.ArrayList;
import java.util.Date;

public class Controller {
    private Autore autore;
    private Pagina pagina;
    private Utente utente;
    private ArrayList<Autore> listaAutori= new ArrayList<Autore>();
    private ArrayList<Utente> listaUtenti= new ArrayList<Utente>();

    public void setAutore(String username, String password) {
        autore = new Autore(username,password);
        listaAutori.add(autore);
    }

    public void setUtente(String username, String password) {
        utente = new Utente(username,password);
        listaUtenti.add(utente);
    }

    public void setPagina(String titolo, Date dataEOraCreazione, Autore autore) {
        pagina = new Pagina(titolo,dataEOraCreazione,autore);
    }
    public String login(String username, String password) throws invalidLoginException {
        boolean usernameTrovato = false;
        boolean passwordTrovata = false;
        for (Autore a : listaAutori){
            if (a.getUsername().equals(username)){
                    usernameTrovato=true;
                if (a.getPassword().equals(password)) {
                    passwordTrovata=true;
                    return "AUTORE";
                }
            }
        }
        for (Utente u : listaUtenti){
            if (u.getUsername().equals(username)){
                usernameTrovato=true;
                if (u.getPassword().equals(password)) {
                    passwordTrovata=true;
                    return "UTENTE";
                }
            }
        }
        throw new invalidLoginException (usernameTrovato,passwordTrovata);
    }

    public ArrayList<Autore> getListaAutori() {
        return listaAutori;
    }
}
