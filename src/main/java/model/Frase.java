package model;

import java.util.ArrayList;

/**
 * La classe Frase rappresenta una frase di una pagina.
 */
public class Frase {
    private String testo;
    private int indice;
    private ArrayList<Modifica> modifiche = new ArrayList<Modifica>();
    private Pagina paginaLinkata;
    private Pagina paginaDiAppartenenza;

    /**
     * Instanzia una nuova Frase.
     *
     * @param testo                il testo della frase
     * @param indice               l'indice della frase
     * @param paginaDiAppartenenza la pagina di appartenenza
     */
    public Frase(String testo, int indice, Pagina paginaDiAppartenenza) {
        this.testo = testo;
        this.indice = indice;

        this.paginaDiAppartenenza = paginaDiAppartenenza;
        paginaDiAppartenenza.getFrasi().add(this);
    }

    /**
     * Instanzia una nuova Frase che linka ad una pagina.
     *
     * @param testo                il testo della frase
     * @param indice               l'indice della frase
     * @param paginaDiAppartenenza la pagina di appartenenza
     * @param paginaLinkata        la pagina linkata
     */
    public Frase(String testo, int indice, Pagina paginaDiAppartenenza, Pagina paginaLinkata) {
        this.testo = testo;
        this.indice = indice;
        this.paginaLinkata = paginaLinkata;

        this.paginaDiAppartenenza = paginaDiAppartenenza;
        paginaDiAppartenenza.getFrasi().add(this);
    }

    /**
     * Gets testo.
     *
     * @return the testo
     */
    public String getTesto() {
        return testo;
    }

    /**
     * Gets indice.
     *
     * @return the indice
     */
    public int getIndice() {
        return indice;
    }

    /**
     * Gets pagina di appartenenza.
     *
     * @return the pagina di appartenenza
     */
    public Pagina getPaginaDiAppartenenza() {
        return paginaDiAppartenenza;
    }

    /**
     * Gets modifiche.
     *
     * @return the modifiche
     */
    public ArrayList<Modifica> getModifiche() {
        return modifiche;
    }

    /**
     * Gets pagina linkata.
     *
     * @return the pagina linkata
     */
    public Pagina getPaginaLinkata() {
        return paginaLinkata;
    }

    /**
     * Sets pagina linkata.
     *
     * @param paginaLinkata the pagina linkata
     */
    public void setPaginaLinkata(Pagina paginaLinkata) {
        this.paginaLinkata = paginaLinkata;
        paginaLinkata.getFrasiCheLinkano().add(this);
    }

    /**
     * Sets testo.
     *
     * @param testo the testo
     */
    public void setTesto(String testo) {
        this.testo = testo;
    }

    /**
     * Sets indice.
     *
     * @param indice the indice
     */
    public void setIndice(int indice) {
        this.indice = indice;
    }
}
