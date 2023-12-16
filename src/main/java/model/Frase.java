package model;

import java.util.ArrayList;

public class Frase {
    private String testo;
    private int indice;
    private ArrayList<Modifica> modifiche = new ArrayList<Modifica>();
    private Pagina paginaLinkata;
    private Pagina paginaDiAppartenenza;
    public Frase(String testo, int indice, Pagina paginaDiAppartenenza) {
        this.testo = testo;
        this.indice = indice;

        this.paginaDiAppartenenza = paginaDiAppartenenza;
        paginaDiAppartenenza.getFrasi().add(this);
    }
    public Frase(String testo, int indice, Pagina paginaDiAppartenenza, Pagina paginaLinkata) {
        this.testo = testo;
        this.indice = indice;
        this.paginaLinkata = paginaLinkata;

        this.paginaDiAppartenenza = paginaDiAppartenenza;
        paginaDiAppartenenza.getFrasi().add(this);
    }

    public String getTesto() {
        return testo;
    }

    public int getIndice() {
        return indice;
    }

    public Pagina getPaginaDiAppartenenza() {
        return paginaDiAppartenenza;
    }

    public ArrayList<Modifica> getModifiche() {
        return modifiche;
    }

    public Pagina getPaginaLinkata() {
        return paginaLinkata;
    }

    public void setPaginaLinkata(Pagina paginaLinkata) {
        this.paginaLinkata = paginaLinkata;
        paginaLinkata.getFrasiCheLinkano().add(this);
    }
    public void setTesto(String testo) {
        this.testo = testo;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }
}
