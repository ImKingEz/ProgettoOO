package model;

import java.util.ArrayList;
import java.util.Date;

/**
 * La classe Pagina rappresenta una pagina della wiki.
 */
public class Pagina {
    private Utente autore;
    private String titolo;
    private Date dataEOraCreazione;
    private Date dataEOraUltimaModifica;
    private ArrayList<Utente> utentiVisionatori = new ArrayList<Utente>();
    private ArrayList<Frase> frasiCheLinkano = new ArrayList<Frase>();
    private ArrayList<Frase> listaFrasi = new ArrayList<Frase>();

    /**
     * Instanzia una nuova Pagina.
     *
     * @param titolo            il titolo
     * @param dataEOraCreazione la data e ora creazione
     * @param autore            l' autore
     */
    public Pagina(String titolo, Date dataEOraCreazione, Utente autore) {
        this.titolo = titolo;
        this.dataEOraCreazione = dataEOraCreazione;

        this.autore = autore;
        autore.getPagineCreate().add(this);
    }

    /**
     * Gets titolo.
     *
     * @return the titolo
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * Gets data e ora creazione.
     *
     * @return the data e ora creazione
     */
    public Date getDataEOraCreazione() {
        return dataEOraCreazione;
    }

    /**
     * Gets utenti visionatori.
     *
     * @return the utenti visionatori
     */
    public ArrayList<Utente> getUtentiVisionatori() {
        return utentiVisionatori;
    }

    /**
     * Gets autore.
     *
     * @return the autore
     */
    public Utente getAutore() {
        return autore;
    }

    /**
     * Gets frasi che linkano.
     *
     * @return the frasi che linkano
     */
    public ArrayList<Frase> getFrasiCheLinkano() {
        return frasiCheLinkano;
    }

    /**
     * Sets frasi che linkano.
     *
     * @param frasiCheLinkano the frasi che linkano
     */
    public void setFrasiCheLinkano(ArrayList<Frase> frasiCheLinkano) {
        this.frasiCheLinkano = frasiCheLinkano;
    }

    /**
     * Gets frasi.
     *
     * @return the frasi
     */
    public ArrayList<Frase> getFrasi() {
        return listaFrasi;
    }

    /**
     * Sets frasi.
     *
     * @param listaFrasi the lista frasi
     */
    public void setFrasi(ArrayList<Frase> listaFrasi) {
        this.listaFrasi = listaFrasi;

    }
}
