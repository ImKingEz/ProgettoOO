package model;

import java.util.ArrayList;
import java.util.Date;

public class Testo {
    private Date dataEOraUltimaModifica;
    private ArrayList<Frase> Frasi = new ArrayList<Frase>();
    private Pagina paginaDiAppartenenza;
    public Testo(Date dataEOraUltimaModifica, Pagina paginaDiAppartenenza) {
        this.dataEOraUltimaModifica = dataEOraUltimaModifica;

        this.paginaDiAppartenenza = paginaDiAppartenenza;
        paginaDiAppartenenza.setTesto(this);
    }

    public Date getDataEOraUltimaModifica() {
        return dataEOraUltimaModifica;
    }

    public ArrayList<Frase> getFrasi() {
        return Frasi;
    }

    public Pagina getPaginaDiAppartenenza() {
        return paginaDiAppartenenza;
    }

    public void setPaginaDiAppartenenza(Pagina paginaDiAppartenenza) {
        this.paginaDiAppartenenza = paginaDiAppartenenza;
    }
}
