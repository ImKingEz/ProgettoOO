package controller;
import dao.ListinoDAO;
import model.*;
import postgresDAO.ListinoPostgresDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

public class Controller {
    private Autore autore;
    private Pagina pagina;
    private Utente utente;
    private Frase frase;
    private ArrayList<Autore> listaAutori = new ArrayList<Autore>();
    private ArrayList<Utente> listaUtenti = new ArrayList<Utente>();
    private ArrayList<Pagina> listaPagina = new ArrayList<Pagina>();
    private ListinoDAO listinoPostgresDAO;
    public Controller() {
    }
    public Controller(ListinoDAO listinoPostgresDAO) {
        this.listinoPostgresDAO = listinoPostgresDAO;
    }

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

    public Utente setUtente(String username, String password) throws invalidLoginException, GiaEsistenteException, LunghezzaPasswordException{
        if (username.isBlank() || password.isBlank())
            throw new invalidLoginException();
        else if (password.length() < 6)
            throw new LunghezzaPasswordException();

        utente = new Utente(username, password);

        try {
            listinoPostgresDAO.setUtente(username, password);
        } catch (Exception e) {
            throw new GiaEsistenteException();
        }

        return utente;

    }

    public Pagina setPagina(String titolo, Date dataEOraCreazione, Autore autore) throws GiaEsistenteException, NotABlankException {
        if(titolo.isBlank())
            throw new NotABlankException();

        try {
            listinoPostgresDAO.setPagina(titolo, autore);
        } catch (Exception e) {
            throw new GiaEsistenteException();
        }

        pagina = new Pagina(titolo, dataEOraCreazione, autore);

        return pagina;
    }

    public boolean esisteAlmenoUnaPagina() {
        if (listinoPostgresDAO.checkEsistenzaPagine()){
            return true;
        } else {
            return false;
        }
    }

    public String login(String username, String password) throws invalidLoginException, usernameNotFoundException, passwordNotFoundException {

        if (username.isBlank() || password.isBlank())
            throw new invalidLoginException();

        utente = listinoPostgresDAO.getUtente(username);

        if(utente==null){
            throw new usernameNotFoundException();
        }else if(!(utente.getPassword().equals(password))){
            throw new passwordNotFoundException();
        }

        if(listinoPostgresDAO.numeroPagineCreateDaUnUtente(utente) > 0) {
            return "autore";
        }else{
            return "utente";
        }
    }

    public boolean almenoUnAutoreOUnUtente() {
        if(listinoPostgresDAO.checkEsistenzaUtenti()) {
            return true;
        }else{
            return false;
        }
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
    public Pagina getPagina(String titolo) throws Exception{
        for (Pagina p: listaPagina) {
            if(titolo.equals(p.getTitolo())) {
                return p;
            }
        }
        throw new Exception();
    }

    public void aggiungiFraseInPagina(Pagina pagina, String testoInserito, Frase frase) throws NotABlankException {
        if(testoInserito.isBlank()){
            throw new NotABlankException();
        }

        int indiceFrase=1;
        for(Frase f: pagina.getFrasi()) {
            indiceFrase++;
        }
        Frase nuovaFrase = new Frase(testoInserito, indiceFrase, pagina);
    }
    public void aggiungiFraseInPagina(Pagina pagina, String testoInserito, Pagina paginaLinkata) throws NotABlankException {
        if(testoInserito.isBlank()){
            throw new NotABlankException();
        }
        this.frase.setTesto(testoInserito);

        int indiceFrase=1;
        for(Frase f: pagina.getFrasi()) {
            indiceFrase++;
        }
        this.frase.setIndice(indiceFrase);

        this.frase.setPaginaLinkata(paginaLinkata);
    }

    public String getTestoTotale(Pagina pagina) {
        String appoggio = "";
        for(Frase f: pagina.getFrasi()) {
            appoggio += f.getTesto() + " ";
            if(f.getPaginaLinkata() != null) {
                appoggio += appoggio + "(Link: " + frase.getPaginaLinkata().getTitolo() + ") ";
            }
        }
        return appoggio;
    }
    public String getFrasiConIndici(Pagina pagina) {
        ArrayList<Frase> listaFrasi = new ArrayList<Frase>();
        String ret = "<html> ";

        listaFrasi = pagina.getFrasi();
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

    public int calcolaIndice(Pagina pagina) {
        return pagina.getFrasi().size() + 1;
    }
    public void setSchema(){
        listinoPostgresDAO.setSchema();
    }
}
