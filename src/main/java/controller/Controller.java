package controller;
import dao.ListinoDAO;
import model.*;
import postgresDAO.ListinoPostgresDAO;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

public class Controller {
    private Pagina pagina;
    private Utente utente;
    private Frase frase;
    private ListinoDAO listinoPostgresDAO;
    public Controller() {
    }
    public Controller(ListinoDAO listinoPostgresDAO) {
        this.listinoPostgresDAO = listinoPostgresDAO;
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

    public Pagina setPagina(String titolo, Date dataEOraCreazione, Utente autore) throws GiaEsistenteException, NotABlankException {
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
    public Utente getUtente(String username) {
        return listinoPostgresDAO.getUtente(username);
    }

    public void login(String username, String password) throws invalidLoginException, usernameNotFoundException, passwordNotFoundException {

        if (username.isBlank() || password.isBlank())
            throw new invalidLoginException();

        utente = listinoPostgresDAO.getUtente(username);

        if(utente==null){
            throw new usernameNotFoundException();
        }else if(!(utente.getPassword().equals(password))){
            throw new passwordNotFoundException();
        }
    }

    public boolean almenoUnAutoreOUnUtente() {
        if(listinoPostgresDAO.checkEsistenzaUtenti()) {
            return true;
        }else{
            return false;
        }
    }

    public static Date getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
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
        for(Frase f: listinoPostgresDAO.getFrasi(pagina)) {
            appoggio += f.getTesto() + " ";
            if(f.getPaginaLinkata() != null) {
                appoggio += appoggio + "(Link: " + frase.getPaginaLinkata().getTitolo() + ") ";
            }
        }
        return appoggio;
    }

    public int calcolaIndice(Pagina pagina) {
        return listinoPostgresDAO.getFrasi(pagina).size() + 1;
    }
    public void setSchema(){
        listinoPostgresDAO.setSchema();
    }
    public int numeroPagineCreateDaUnUtente(String username){
        return listinoPostgresDAO.numeroPagineCreateDaUnUtente(username);
    }
    public void setPagina(String titolo, Utente autore) throws GiaEsistenteException, NotABlankException {
        listinoPostgresDAO.setPagina(titolo, autore);
    }
    public Pagina getPagina(String titolo) throws NotFoundException{
        return listinoPostgresDAO.getPagina(titolo);
    }
    public void setFrase(String testo, Pagina pagina) throws NotABlankException {
        listinoPostgresDAO.setFrase(testo, pagina);
    }
    public ArrayList<Frase> getFrasi(Pagina pagina){
        return listinoPostgresDAO.getFrasi(pagina);
    }

    public String getFrasiConIndici(Pagina pagina) {
        String ret = "<html> ";

        for(Frase f: listinoPostgresDAO.getFrasi(pagina)) {
            ret += f.getIndice() + ") " + f.getTesto();
            if(f.getPaginaLinkata() != null) {
                ret += "(Link: " + f.getPaginaLinkata().getTitolo() + ")";
            }
            ret += " <br> ";
        }
        ret += " </html>";

        return ret;
    }

    public void setModifica(String testo, String usernamemodificatore, Frase frase, Pagina pagina) throws AccettazioneAutomaticaException {
        listinoPostgresDAO.setModifica(testo, usernamemodificatore, frase, pagina);
    }
    public Modifica getModifica(Frase frase) throws NotFoundException {
        return listinoPostgresDAO.getModifica(frase);
    }
}
