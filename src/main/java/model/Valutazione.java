package model;

import java.util.Date;

public class Valutazione {
    private Boolean accettazione;
    private Date dataEOraValutazione;
    private Autore autore;
    private Modifica modificaFrase;
    public Valutazione(Boolean accettazione, Date dataEOraValutazione, Autore autore, Modifica modificaFrase) {
        this.accettazione = accettazione;
        this.dataEOraValutazione = dataEOraValutazione;

        this.autore = autore;
        autore.getValutazioniEffettuate().add(this);

        this.modificaFrase = modificaFrase;
        modificaFrase.setValutazione(this);
    }

    public Boolean getAccettazione() {
        return accettazione;
    }

    public Date getDataEOraValutazione() {
        return dataEOraValutazione;
    }

    public Autore getAutore() {
        return autore;
    }

    public Modifica getModificaFrase() {
        return modificaFrase;
    }

    public void setModificaFrase(Modifica modificaFrase) {
        this.modificaFrase = modificaFrase;
    }
}
