package model;

import java.util.Date;

public class Modifica {
    private String testo;
    private Date dataEOraModificaProposta;
    private Valutazione valutazione;
    private Utente utenteModificatore;
    private Frase fraseRiferita;
    private int idmodifica;
    public Modifica(int idModifica, String testo, Date dataEOraModificaProposta, Utente utenteModificatore, Frase fraseRiferita) {
        this.idmodifica = idModifica;

        this.testo = testo;
        this.dataEOraModificaProposta = dataEOraModificaProposta;

        this.utenteModificatore = utenteModificatore;
        utenteModificatore.getModificheProposte().add(this);

        this.fraseRiferita = fraseRiferita;
        fraseRiferita.getModifiche().add(this);
    }
    public int getIdModifica() {
        return idmodifica;
    }
    public void setIdmodifica() {
        this.idmodifica = idmodifica;
    }

    public String getTesto() {
        return testo;
    }

    public Date getDataEOraModificaProposta() {
        return dataEOraModificaProposta;
    }

    public Valutazione getValutazione() {
        return valutazione;
    }

    public void setValutazione(Valutazione valutazione) {
        this.valutazione = valutazione;
        valutazione.setModificaFrase(this);
    }
    public Utente getUtenteModificatore() {
        return utenteModificatore;
    }

    public Frase getFraseRiferita() {
        return fraseRiferita;
    }
}
