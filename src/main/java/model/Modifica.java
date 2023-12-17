package model;

import java.util.Date;

/**
 * La classe Modifica rappresenta una modifica proposta da un utente ad una frase. Se l'utente è l'autore della frase, allora la modifica viene applicata direttamente.
 */
public class Modifica {
    private String testo;
    private Date dataEOraModificaProposta;
    private Valutazione valutazione;
    private Utente utenteModificatore;
    private Frase fraseRiferita;
    private int idmodifica;

    /**
     * Instantiates a new Modifica.
     *
     * @param idModifica               l' id modifica
     * @param testo                    il testo
     * @param dataEOraModificaProposta la data e ora in cui la modifica viene proposta
     * @param utenteModificatore       l' utente modificatore
     * @param fraseRiferita            la frase a cui è riferita la modifica
     */
    public Modifica(int idModifica, String testo, Date dataEOraModificaProposta, Utente utenteModificatore, Frase fraseRiferita) {
        this.idmodifica = idModifica;

        this.testo = testo;
        this.dataEOraModificaProposta = dataEOraModificaProposta;

        this.utenteModificatore = utenteModificatore;
        utenteModificatore.getModificheProposte().add(this);

        this.fraseRiferita = fraseRiferita;
        fraseRiferita.getModifiche().add(this);
    }

    /**
     * Gets id modifica.
     *
     * @return the id modifica
     */
    public int getIdModifica() {
        return idmodifica;
    }

    /**
     * Sets idmodifica.
     */
    public void setIdmodifica() {
        this.idmodifica = idmodifica;
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
     * Gets data e ora modifica proposta.
     *
     * @return the data e ora modifica proposta
     */
    public Date getDataEOraModificaProposta() {
        return dataEOraModificaProposta;
    }

    /**
     * Gets valutazione.
     *
     * @return the valutazione
     */
    public Valutazione getValutazione() {
        return valutazione;
    }

    /**
     * Sets valutazione.
     *
     * @param valutazione the valutazione
     */
    public void setValutazione(Valutazione valutazione) {
        this.valutazione = valutazione;
        valutazione.setModificaFrase(this);
    }

    /**
     * Gets utente modificatore.
     *
     * @return the utente modificatore
     */
    public Utente getUtenteModificatore() {
        return utenteModificatore;
    }

    /**
     * Gets frase riferita.
     *
     * @return the frase riferita
     */
    public Frase getFraseRiferita() {
        return fraseRiferita;
    }
}
