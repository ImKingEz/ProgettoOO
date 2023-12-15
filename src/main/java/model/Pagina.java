package model;

import java.util.ArrayList;
import java.util.Date;

public class Pagina {
    private Utente autore;
    private String titolo;
    private Date dataEOraCreazione;
    private Date dataEOraUltimaModifica;
    private ArrayList<Utente> utentiVisionatori = new ArrayList<Utente>();
    private ArrayList<Frase> frasiCheLinkano = new ArrayList<Frase>();
    private ArrayList<Frase> listaFrasi = new ArrayList<Frase>();
    public Pagina(String titolo, Date dataEOraCreazione, Utente autore) {
        this.titolo = titolo;
        this.dataEOraCreazione = dataEOraCreazione;

        this.autore = autore;
        autore.getPagineCreate().add(this);
    }

    public String getTitolo() {
        return titolo;
    }

    public Date getDataEOraCreazione() {
        return dataEOraCreazione;
    }

    public ArrayList<Utente> getUtentiVisionatori() {
        return utentiVisionatori;
    }

    public Utente getAutore() {
        return autore;
    }

    public ArrayList<Frase> getFrasiCheLinkano() {
        return frasiCheLinkano;
    }

    public void setFrasiCheLinkano(ArrayList<Frase> frasiCheLinkano) {
        this.frasiCheLinkano = frasiCheLinkano;
    }

    public ArrayList<Frase> getFrasi() {
        return listaFrasi;
    }
}
