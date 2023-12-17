package model;

import java.util.Date;

/**
 * La classe Valutazione rappresenta una valutazione di una modifica proposta da un utente ad una frase.
 */
public class Valutazione {
    private Boolean accettazione;
    private Date dataEOraValutazione;
    private Modifica modificaFrase;
    private Utente autore;

    /**
     * Instanzia una nova Valutazione.
     *
     * @param accettazione        l'esito della valutazione
     * @param dataEOraValutazione la data e ora in cui è stata fatta la valutazione
     * @param autore              l' autore
     * @param modificaFrase       la modifica a cui si riferisce
     */
    public Valutazione(Boolean accettazione, Date dataEOraValutazione, Utente autore, Modifica modificaFrase) {
        this.accettazione = accettazione;
        this.dataEOraValutazione = dataEOraValutazione;

        this.autore = autore;
        autore.getValutazioniEffettuate().add(this);

        this.modificaFrase = modificaFrase;
        modificaFrase.setValutazione(this);
    }

    /**
     * Gets accettazione.
     *
     * @return the accettazione
     */
    public Boolean getAccettazione() {
        return accettazione;
    }

    /**
     * Gets data e ora valutazione.
     *
     * @return the data e ora valutazione
     */
    public Date getDataEOraValutazione() {
        return dataEOraValutazione;
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
     * Gets modifica frase.
     *
     * @return the modifica frase
     */
    public Modifica getModificaFrase() {
        return modificaFrase;
    }

    /**
     * Sets modifica frase.
     *
     * @param modificaFrase the modifica frase
     */
    public void setModificaFrase(Modifica modificaFrase) {
        this.modificaFrase = modificaFrase;
    }
}
