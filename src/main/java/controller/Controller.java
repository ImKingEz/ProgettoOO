package controller;
import model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

public class Controller {
    private Autore autore;
    private Pagina pagina;
    private Utente utente;
    private ArrayList<Autore> listaAutori = new ArrayList<Autore>();
    private ArrayList<Utente> listaUtenti = new ArrayList<Utente>();
    private ArrayList<Pagina> listaPagina = new ArrayList<Pagina>();

    public void setAutore(String username, String password) throws invalidLoginException, usernameGiaEsistenteException {
        if (username.isBlank() || password.isBlank())
            throw new invalidLoginException();

        for (Utente u : listaUtenti) {
            if (username.equals(u.getUsername()))
                throw new usernameGiaEsistenteException();
        }
        for (Autore a : listaAutori) {
            if (username.equals(a.getUsername()))
                throw new usernameGiaEsistenteException();
        }

        autore = new Autore(username, password);
        listaAutori.add(autore);
    }

    public void setUtente(String username, String password) throws invalidLoginException, usernameGiaEsistenteException {
        if (username.isBlank() || password.isBlank())
            throw new invalidLoginException();

        for (Utente u : listaUtenti) {
            if (username.equals(u.getUsername()))
                throw new usernameGiaEsistenteException();
        }
        for (Autore a : listaAutori) {
            if (username.equals(a.getUsername()))
                throw new usernameGiaEsistenteException();
        }

        utente = new Utente(username, password);
        listaUtenti.add(utente);
    }

    public void setPagina(String titolo, Date dataEOraCreazione, Autore autore) {
        pagina = new Pagina(titolo, dataEOraCreazione, autore);
        listaPagina.add(pagina);
    }

    public boolean esisteAlmenoUnaPagina(){
        if (listaPagina.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    public String login(String username, String password) throws invalidLoginException, usernameNotFoundException, passwordNotFoundException {
        boolean usernameTrovato = false;
        boolean passwordTrovata = false;

        if (username.isBlank() || password.isBlank())
            throw new invalidLoginException();

        for (Autore a : listaAutori) {
            if (a.getUsername().equals(username)) {
                usernameTrovato = true;
                if (a.getPassword().equals(password)) {
                    passwordTrovata = true;
                    return "AUTORE";
                }
            }
        }
        for (Utente u : listaUtenti) {
            if (u.getUsername().equals(username)) {
                usernameTrovato = true;
                if (u.getPassword().equals(password)) {
                    passwordTrovata = true;
                    return "UTENTE";
                }
            }
        }
        if (!usernameTrovato)
            throw new usernameNotFoundException();
        else
            throw new passwordNotFoundException();
    }

    public boolean almenoUnAutoreOUnUtente() {
        if (getListaAutori().isEmpty() && getListaUtenti().isEmpty())
            return false;
        else
            return true;
    }

    public ArrayList<Autore> getListaAutori() {
        return listaAutori;
    }

    public ArrayList<Utente> getListaUtenti() {
        return listaUtenti;
    }

    public static Date getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public Utente controllaIdentita(String username, String password, String identita) {
        if (identita.equals("UTENTE")) {
            for (Utente u : listaUtenti) {
                if (username.equals(u.getUsername()) && password.equals(u.getPassword())) {
                    return u;
                }
            }
        } else {
            for (Autore a : listaAutori) {
                if (username.equals(a.getUsername()) && password.equals(a.getPassword())) {
                    return (model.Autore) a;
                }
            }
        }
        return null;
    }
}
