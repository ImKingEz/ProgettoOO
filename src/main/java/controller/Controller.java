package controller;
import model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

public class Controller {
    private Autore autore;
    private Pagina pagina;
    private Testo testo;
    private Utente utente;
    private Frase frase;
    private ArrayList<Autore> listaAutori = new ArrayList<Autore>();
    private ArrayList<Utente> listaUtenti = new ArrayList<Utente>();
    private ArrayList<Pagina> listaPagina = new ArrayList<Pagina>();

    public void setAutore(String username, String password) throws invalidLoginException, GiaEsistenteException {
        if (username.isBlank() || password.isBlank())
            throw new invalidLoginException();

        for (Utente u : listaUtenti) {
            if (username.equals(u.getUsername()))
                throw new GiaEsistenteException();
        }
        for (Autore a : listaAutori) {
            if (username.equals(a.getUsername()))
                throw new GiaEsistenteException();
        }

        autore = new Autore(username, password);
        listaAutori.add(autore);
    }

    public void setUtente(String username, String password) throws invalidLoginException, GiaEsistenteException {
        if (username.isBlank() || password.isBlank())
            throw new invalidLoginException();

        for (Utente u : listaUtenti) {
            if (username.equals(u.getUsername()))
                throw new GiaEsistenteException();
        }
        for (Autore a : listaAutori) {
            if (username.equals(a.getUsername()))
                throw new GiaEsistenteException();
        }

        utente = new Utente(username, password);
        listaUtenti.add(utente);
    }

    public void setPagina(String titolo, Date dataEOraCreazione, Autore autore)  throws GiaEsistenteException, NotABlankException{
        if(titolo.isBlank())
            throw new NotABlankException();

        for (Pagina p: listaPagina) {
            if(titolo.equals(p.getTitolo()))
                throw new GiaEsistenteException();
        }

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

    public void setTesto(Date dataEOraUltimaModifica, Pagina paginaAppartenenza) {
        testo = new Testo(dataEOraUltimaModifica, paginaAppartenenza);
    }
    public Pagina getPagina(String titolo) throws Exception{
        for (Pagina p: listaPagina) {
            if(titolo.equals(p.getTitolo())) {
                return p;
            }
        }
        throw new Exception();
    }

    public void aggiungiFraseInTesto(Testo testoDellaPagina, String testoInserito, Frase frase) throws NotABlankException {
        if(testoInserito.isBlank()){
            throw new NotABlankException();
        }

        int indiceFrase=1;
        for(Frase f: testoDellaPagina.getFrasi()) {
            indiceFrase++;
        }
        Frase nuovaFrase = new Frase(testoInserito, indiceFrase, testoDellaPagina);
    }
    public void aggiungiFraseInTesto(Testo testoDellaPagina, String testoInserito, Pagina paginaLinkata) throws NotABlankException {
        if(testoInserito.isBlank()){
            throw new NotABlankException();
        }
        this.frase.setTesto(testoInserito);

        int indiceFrase=1;
        for(Frase f: testoDellaPagina.getFrasi()) {
            indiceFrase++;
        }
        this.frase.setIndice(indiceFrase);

        this.frase.setPaginaLinkata(paginaLinkata);
    }

    public String getTestoTotale(Testo testo) {
        String appoggio = "";
        for(Frase f: testo.getFrasi()) {
            appoggio += f.getTesto() + " ";
            if(f.getPaginaLinkata() != null) {
                appoggio += appoggio + "(Link: " + frase.getPaginaLinkata().getTitolo() + ") ";
            }
        }
        return appoggio;
    }
    public String getFrasiConIndici(Testo testo) {
        ArrayList<Frase> listaFrasi = new ArrayList<Frase>();
        String ret = "<html> ";

        listaFrasi = testo.getFrasi();
        for(Frase f: listaFrasi) {
            ret += f.getIndice() + ") " + f.getTesto();
            if(f.getPaginaLinkata() != null) {
                ret += "(Link: " + f.getPaginaLinkata().getTitolo() + ")";
            }
            ret += " <br> ";
        }
        ret += " </html>";

        return ret;
    }

    public int calcolaIndice(Testo testo) {
        return testo.getFrasi().size() + 1;
    }
}
