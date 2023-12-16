package postgresDAO;

import controller.*;
import dao.ListinoDAO;
import database.ConnessioneDatabase;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;


public class ListinoPostgresDAO implements ListinoDAO {

    private Connection connection;
    private Controller controller;

    public ListinoPostgresDAO() {
        try {
            connection = ConnessioneDatabase.getInstance().getConnection();
            this.controller = new Controller(this);
        } catch (SQLException e) {   //fare la exception
            e.printStackTrace();
        }
    }

    public void setSchema() {
        PreparedStatement setSchema = null;
        try {
            String query = "SET search_path TO wiki";
            setSchema = connection.prepareStatement(query);
            setSchema.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Errore nel set dello schema: " + e.getMessage());
        } finally {
            try {
                if (setSchema != null) {
                    setSchema.close();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la chiusura dello statement: " + e.getMessage());
            }
        }
    }

    public void setUtente(String username, String password) throws Exception{
        PreparedStatement insertUtente = null;
        try {
            String query = "INSERT INTO utente (username, password) VALUES (?, ?)";
            insertUtente = connection.prepareStatement(query);
            insertUtente.setString(1, username);
            insertUtente.setString(2, password);
            insertUtente.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Errore durante l'inserimento dell'utente: " + e.getMessage());
            throw new Exception();

        } finally {
            try {
                if (insertUtente != null) {
                    insertUtente.close();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la chiusura dello statement: " + e.getMessage());
            }
        }
    }

    public Utente getUtente(String username){
        PreparedStatement selectUtente = null;
        Utente u=null;
        ResultSet rs = null;
        try {
            String query = "SELECT username,password  FROM utente WHERE username = ?";
            selectUtente = connection.prepareStatement(query);
            selectUtente.setString(1, username);
            rs = selectUtente.executeQuery();
            if(rs.next()) {
                u = new Utente(rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println("Errore durante l'estrazione dell'utente: " + e.getMessage());
        } finally {
            try {
                if (selectUtente != null) {
                    selectUtente.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la chiusura dello statement: " + e.getMessage());
            }
        }
        return u;
    }

    public void setPagina(String titolo, Utente autore) throws GiaEsistenteException, NotABlankException , LunghezzaMinimaException {
        PreparedStatement insertPagina = null;

        java.util.Date currentDate = Calendar.getInstance().getTime(); // Ottieni la data attuale
        Date dataCreazione = new Date(currentDate.getTime()); // Converte java.util.Date a java.sql.Date
        Time oraCreazione = new Time(currentDate.getTime()); // Ottieni l'ora attuale
        if (titolo.isBlank()){
            throw new NotABlankException();
        }
        if (titolo.length() < 3){
            throw new LunghezzaMinimaException();
        }
        try {
            String query = "INSERT INTO pagina(titolo, datacreazione, oracreazione, usernameautore) VALUES(?, ?, ?, ?)";
            insertPagina = connection.prepareStatement(query);
            insertPagina.setString(1, titolo);
            insertPagina.setDate(2, dataCreazione);
            insertPagina.setTime(3, oraCreazione);
            insertPagina.setString(4, autore.getUsername());
            insertPagina.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Errore durante l'inserimento della pagina: " + e.getMessage());
        } finally {
            try {
                if (insertPagina != null) {
                    insertPagina.close();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la chiusura dello statement: " + e.getMessage());
            }
        }
    }

    public Pagina getPagina(String titolo) throws NotFoundException {
        PreparedStatement selectPagina = null;
        Pagina p = null;
        ResultSet rs = null;

        java.util.Date currentDate = Calendar.getInstance().getTime(); // Ottieni la data attuale
        Date dataCreazione = new Date(currentDate.getTime()); // Converte java.util.Date a java.sql.Date
        Time oraCreazione = new Time(currentDate.getTime()); // Ottieni l'ora attuale

        try {
            String query = "SELECT titolo, usernameautore  FROM pagina WHERE titolo = ?";
            selectPagina = connection.prepareStatement(query);
            selectPagina.setString(1, titolo);
            rs = selectPagina.executeQuery();
            if(rs.next()) {
                return p = new Pagina(rs.getString("titolo"), currentDate, getUtente(rs.getString("usernameautore")));
            }
        } catch (SQLException e) {
            System.out.println("Errore durante l'estrazione della pagina: " + e.getMessage());
        } finally {
            try {
                if (selectPagina != null) {
                    selectPagina.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la chiusura dello statement: " + e.getMessage());
            }
        }
        throw new NotFoundException();
    }

    public int getIdPagina(String titolo) throws NotFoundException {
        PreparedStatement selectPagina = null;
        Pagina p = null;
        ResultSet rs = null;

        java.util.Date currentDate = Calendar.getInstance().getTime(); // Ottieni la data attuale
        Date dataCreazione = new Date(currentDate.getTime()); // Converte java.util.Date a java.sql.Date
        Time oraCreazione = new Time(currentDate.getTime()); // Ottieni l'ora attuale

        try {
            String query = "SELECT idpagina FROM pagina WHERE titolo = ?";
            selectPagina = connection.prepareStatement(query);
            selectPagina.setString(1, titolo);
            rs = selectPagina.executeQuery();
            if(rs.next()) {
                return rs.getInt("idpagina");
            }
        } catch (SQLException e) {
            System.out.println("Errore durante l'estrazione della pagina: " + e.getMessage());
        } finally {
            try {
                if (selectPagina != null) {
                    selectPagina.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la chiusura dello statement: " + e.getMessage());
            }
        }
        throw new NotFoundException();
    }

    public void setFrase(String testo, Pagina pagina) throws NotABlankException, NotFoundException {
        PreparedStatement insertFrase = null;
        try {
            String query = "INSERT INTO frase(testo, indice, idpagina) VALUES(?, ?, ?)";
            insertFrase = connection.prepareStatement(query);
            insertFrase.setString(1, testo);
            insertFrase.setInt(2, controller.calcolaIndice(pagina));
            insertFrase.setInt(3, getIdPagina(pagina.getTitolo()));
            insertFrase.executeUpdate();
        } catch (NotFoundException ex) {
            System.out.println("Errore durante l'estrazione dell'id della pagina: " + ex.getMessage());
        } catch (SQLException e) {
            System.out.println("Errore durante l'inserimento della frase: " + e.getMessage());
        }
        finally {
            try {
                if (insertFrase != null) {
                    insertFrase.close();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la chiusura dello statement: " + e.getMessage());
            }
        }
    }

    public ArrayList<Frase> getFrasi(Pagina pagina) {
        PreparedStatement selectFrase = null;
        ArrayList<Frase> frasi = new ArrayList<Frase>();
        ResultSet rs = null;
        String titolopagina = pagina.getTitolo();
        try {
            String query = "SELECT testo, indice FROM frase f, pagina p WHERE p.titolo = ? AND p.idpagina = f.idpagina order by indice asc";
            selectFrase = connection.prepareStatement(query);
            selectFrase.setString(1, titolopagina);
            rs = selectFrase.executeQuery();
            while (rs.next()) {
                frasi.add(new Frase(rs.getString("testo"), rs.getInt("indice"), pagina));
            }
        } catch (SQLException e) {
            System.out.println("Errore durante l'estrazione della frase: " + e.getMessage());
        } finally {
            try {
                if (selectFrase != null) {
                    selectFrase.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la chiusura dello statement: " + e.getMessage());
            }
        }
        return frasi;
    }

    public void setModifica(String testo, String usernamemodificatore, Frase frase, Pagina pagina) throws AccettazioneAutomaticaException {
        PreparedStatement insertModifica = null;

        java.util.Date currentDate = Calendar.getInstance().getTime(); // Ottieni la data attuale
        Date dataModifica = new Date(currentDate.getTime()); // Converte java.util.Date a java.sql.Date
        Time oraModifica = new Time(currentDate.getTime()); // Ottieni l'ora attuale

        try {
            String query = "INSERT INTO modifica(testo, datamodificaproposta, oramodificaproposta, username, testofrase, indice, idpaginafrase) VALUES(?, ?, ?, ?, ?, ?, ?)";
            insertModifica = connection.prepareStatement(query);
            insertModifica.setString(1, testo);
            insertModifica.setDate(2, dataModifica);
            insertModifica.setTime(3, oraModifica);
            insertModifica.setString(4, usernamemodificatore);
            insertModifica.setString(5, frase.getTesto());
            insertModifica.setInt(6, frase.getIndice());
            insertModifica.setInt(7, getIdPagina(pagina.getTitolo()));
            insertModifica.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Errore durante l'inserimento della modifica: " + e.getMessage());
        } catch (NotFoundException e) {
            System.out.println("Errore durante l'estrazione dell'id della pagina: " + e.getMessage());
        } finally {
            try {
                if (insertModifica != null) {
                    insertModifica.close();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la chiusura dello statement: " + e.getMessage());
            }
        }
        if(usernamemodificatore.equals(pagina.getAutore().getUsername())) {
            throw new AccettazioneAutomaticaException();
        }
    }
    public Modifica getModifica(Frase frase, String ordine) throws NotFoundException, IllegalArgumentException { //prendo la modifica più recente
        PreparedStatement selectModifica = null;
        Pagina p = null;
        ResultSet rs = null;

        if(!(ordine.equals("asc") || ordine.equals("desc"))) {
            throw new IllegalArgumentException();
        }

        try {
            String query = "SELECT m.testo, m.username, m.datamodificaproposta, m.oramodificaproposta  FROM modifica m, frase f WHERE m.idpaginafrase = f.idpagina and m.testofrase = f.testo and m.indice = f.indice order by datamodificaproposta asc, oramodificaproposta ?";
            selectModifica = connection.prepareStatement(query);
            selectModifica.setString(1, ordine);
            rs = selectModifica.executeQuery();
            if(rs.next()) { //prendo solo la prima tupla, ossia quella con la data minore
                long dateTimestamp = rs.getDate("datamodificaproposta").getTime();
                long timeTimestamp = rs.getTime("oramodificaproposta").getTime();

                // Sommo i due timestamp
                long combinedTimestamp = dateTimestamp + timeTimestamp;
                // Creo un oggetto java.util.Date utilizzando il timestamp combinato
                java.util.Date utilDate = new java.util.Date(combinedTimestamp);
                return new Modifica(rs.getString("testo"), utilDate, getUtente(rs.getString("username")),frase);
            }
        } catch (SQLException e) {
            System.out.println("Errore durante l'estrazione della modifica: " + e.getMessage());
        } finally {
            try {
                if (selectModifica != null) {
                    selectModifica.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la chiusura dello statement: " + e.getMessage());
            }
        }
        throw new NotFoundException();
    }

    public int getIdModifica(Modifica modifica, Frase frase, Pagina pagina) throws NotFoundException {
        PreparedStatement selectModifica = null;
        ResultSet rs = null;

        try {
            String query = "SELECT m.idmodifica FROM modifica m, frase f WHERE m.testo = ? and m.username = ? and m.idpaginafrase = ? and m.testofrase = ? and m.indice = ?";
            selectModifica = connection.prepareStatement(query);
            selectModifica.setString(1, modifica.getTesto());
            selectModifica.setString(2, modifica.getUtenteModificatore().getUsername());
            selectModifica.setInt(3, getIdPagina(pagina.getTitolo()));
            selectModifica.setString(4, frase.getTesto());
            selectModifica.setInt(5, frase.getIndice());
            rs = selectModifica.executeQuery();
            if(rs.next()) {
                return rs.getInt("idmodifica");
            }
        } catch (SQLException e) {
            System.out.println("Errore durante l'estrazione della modifica: " + e.getMessage());
        } finally {
            try {
                if (selectModifica != null) {
                    selectModifica.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la chiusura dello statement: " + e.getMessage());
            }

        }
        throw new NotFoundException();
    }

    public void setValutazione(boolean accettazione, Modifica modifica, Utente autore) {
        PreparedStatement insertValutazione = null;

        java.util.Date currentDate = Calendar.getInstance().getTime(); // Ottieni la data attuale
        Date dataValutazione = new Date(currentDate.getTime()); // Converte java.util.Date a java.sql.Date
        Time oraValutazione = new Time(currentDate.getTime()); // Ottieni l'ora attuale

        try {
            String query = "INSERT INTO valutazione(accettazione, datavalutazione, oravalutazione, username, idmodifica) VALUES(?, ?, ?, ?, ?)";
            insertValutazione = connection.prepareStatement(query);
            insertValutazione.setBoolean(1, accettazione);
            insertValutazione.setDate(2, dataValutazione);
            insertValutazione.setTime(3, oraValutazione);
            insertValutazione.setString(4, autore.getUsername());
            insertValutazione.setInt(5, getIdModifica(modifica, modifica.getFraseRiferita(), modifica.getFraseRiferita().getPaginaDiAppartenenza()));
            insertValutazione.executeUpdate();
        } catch (NotFoundException e) {
            System.out.println("Errore durante l'estrazione dell'id della modifica: " + e.getMessage());
        } catch (SQLException ex) {
            System.out.println("Errore durante l'inserimento della valutazione: " + ex.getMessage());
        }
        finally {
            try {
                if (insertValutazione != null) {
                    insertValutazione.close();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la chiusura dello statement: " + e.getMessage());
            }
        }
    }

    public Valutazione getValutazione(Utente autore, Modifica modifica) throws NotFoundException {
        PreparedStatement selectValutazione = null;
        ResultSet rs = null;

        java.util.Date currentDate = Calendar.getInstance().getTime(); // Ottieni la data attuale
        Date dataValutazione = new Date(currentDate.getTime()); // Converte java.util.Date a java.sql.Date
        Time oraValutazione = new Time(currentDate.getTime()); // Ottieni l'ora attuale

        try {
            String query = "SELECT accettazione, datavalutazione, oravalutazione FROM valutazione WHERE usernameautore = ? and idmodifica = ?";
            selectValutazione = connection.prepareStatement(query);
            selectValutazione.setString(1, autore.getUsername());
            selectValutazione.setInt(2, getIdModifica(modifica, modifica.getFraseRiferita(), modifica.getFraseRiferita().getPaginaDiAppartenenza()));
            rs = selectValutazione.executeQuery();
            if(rs.next()) {
                long dateTimestamp = rs.getDate("datamodificaproposta").getTime();
                long timeTimestamp = rs.getTime("oramodificaproposta").getTime();
                // Sommo i due timestamp
                long combinedTimestamp = dateTimestamp + timeTimestamp;
                // Creo un oggetto java.util.Date utilizzando il timestamp combinato
                java.util.Date utilDate = new java.util.Date(combinedTimestamp);

                return new Valutazione(rs.getBoolean("accettazione"), utilDate, autore, modifica);
            }
        } catch (SQLException e) {
            System.out.println("Errore durante l'estrazione della valutazione: " + e.getMessage());
        } catch (NotFoundException e) {
            System.out.println("Errore durante l'estrazione dell'id della modifica: " + e.getMessage());
        } finally {
            try {
                if (selectValutazione != null) {
                    selectValutazione.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la chiusura dello statement: " + e.getMessage());

            }
        }
        throw new NotFoundException();
    }

    public int numeroPagineCreateDaUnUtente(String username){
        PreparedStatement selectPagina = null;
        int numeroPagine=0;
        ResultSet rs = null;
        try {
            String query = "SELECT COUNT(*) FROM pagina WHERE usernameautore = ? ";
            selectPagina = connection.prepareStatement(query);
            selectPagina.setString(1, username);
            rs = selectPagina.executeQuery();
            while (rs.next()) {
                numeroPagine = rs.getInt("count");
            }

        } catch (SQLException e) {
            System.out.println("Errore durante l'estrazione del numero di pagine create da un autore: " + e.getMessage());
        } finally {
            try {
                if (selectPagina != null) {
                    selectPagina.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la chiusura dello statement: " + e.getMessage());
            }
        }
        return numeroPagine;
    }

    public boolean checkEsistenzaUtenti(){
        PreparedStatement selectUtente = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM utente";
            selectUtente = connection.prepareStatement(query);
            rs = selectUtente.executeQuery();
            if(rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Errore durante l'estrazione dell'utente: " + e.getMessage());
        } finally {
            try {
                if (selectUtente != null) {
                    selectUtente.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la chiusura dello statement: " + e.getMessage());
            }
        }
        return false;
    }

    public boolean checkEsistenzaPagine() {
        PreparedStatement selectPagina = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM pagina";
            selectPagina = connection.prepareStatement(query);
            rs = selectPagina.executeQuery();
            if(rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Errore durante l'estrazione della pagina: " + e.getMessage());
        } finally {
            try {
                if (selectPagina != null) {
                    selectPagina.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la chiusura dello statement: " + e.getMessage());
            }
        }
        return false;
    }

    public ArrayList<Frase> getFrasiAggiornate(Pagina pagina){
        int contatore=0;
        int controlloStampa=0;
        int controlloIndici=0;
        PreparedStatement selectFrase = null;
        ArrayList<Frase> frasitotali = new ArrayList<Frase>();
        ArrayList<Integer> indiciVisitati = new ArrayList<Integer>();
        ArrayList<Frase> frasiAggiornate = new ArrayList<Frase>();
        ResultSet rs = null;
        String titolopagina = pagina.getTitolo();
        try {
            String query = "SELECT f.testo,f.indice,f.idpagina\n" +
                            "FROM frase f\n" +
                            "WHERE f.idpagina = ?\n" +
                            "ORDER BY f.indice asc;";
            selectFrase = connection.prepareStatement(query);
            selectFrase.setInt(1, getIdPagina(titolopagina));
            rs = selectFrase.executeQuery();
            while (rs.next()) {
                frasitotali.add(new Frase(rs.getString("testo"), rs.getInt("indice"), pagina));
            }
            for(Frase f: frasitotali){
                contatore=0;
                controlloStampa=0;
                controlloIndici=0;
                for(int n: indiciVisitati){
                    if(f.getIndice()==n){
                        controlloIndici=1;
                    }
                }
                if(controlloIndici==0){  //se non è già stato visitato faccio la query, altrimenti passo alla prossima frase
                    for(Frase f2: frasitotali){
                        if(f.getIndice()==f2.getIndice()){
                            contatore=contatore+1;
                        }
                    }
                    if(contatore == 2){
                        String query2 = "SELECT m.testo,m.indice,m.idpaginafrase\n" +
                                "FROM frase f, modifica m, valutazione v\n" +
                                "WHERE f.idpagina = ? and f.indice= ?\n" +
                                "and f.testo=m.testoFrase\n" +
                                "and f.indice=m.indice and f.idpagina=m.idpaginafrase and\n" +
                                "m.idmodifica=v.idmodifica;";
                        selectFrase = connection.prepareStatement(query2);
                        selectFrase.setInt(1, getIdPagina(titolopagina));
                        selectFrase.setInt(2, f.getIndice());
                        rs = selectFrase.executeQuery();
                        while (rs.next()) {
                            frasiAggiornate.add(new Frase(rs.getString("testo"), rs.getInt("indice"), pagina));
                        }
                        controlloStampa=1;
                    } else if(contatore>2){
                        String query2 = "SELECT m.testo,m.indice,m.idpaginafrase\n" +
                                "FROM valutazione v, modifica m\n" +
                                "WHERE m.idmodifica=v.idmodifica and v.accettazione = true and\n" +
                                "v.datavalutazione in (select max(v1.datavalutazione)\n" +
                                "from valutazione v1\n" +
                                "where m.idpaginafrase = ? and\n" +
                                "m.indice= ? and\n" +
                                "v1.accettazione = true and v.oravalutazione in\n" +
                                "(select max(v2.oravalutazione)\n" +
                                "from valutazione v2\n" +
                                "where m.idpaginafrase = ? and\n" +
                                "m.indice = ? and\n" +
                                "v2.datavalutazione=v.datavalutazione and\n" +
                                "v2.accettazione = true));";
                        selectFrase = connection.prepareStatement(query2);
                        selectFrase.setInt(1, getIdPagina(titolopagina));
                        selectFrase.setInt(2, f.getIndice());
                        selectFrase.setInt(3, getIdPagina(titolopagina));
                        selectFrase.setInt(4, f.getIndice());
                        rs = selectFrase.executeQuery();
                        while (rs.next()) {
                            frasiAggiornate.add(new Frase(rs.getString("testo"), rs.getInt("indice"), pagina));
                        }
                        controlloStampa=1;
                    }
                    if(controlloStampa==0){
                        frasiAggiornate.add(f);
                    }
                    indiciVisitati.add(f.getIndice());
                }
            }
        } catch (SQLException e) {
            System.out.println("Errore durante l'estrazione della frase: " + e.getMessage());
        } catch (NotFoundException ex) {
            System.out.println("Errore durante l'estrazione del id pagina: " + ex.getMessage());
        } finally {
            try {
                if (selectFrase != null) {
                    selectFrase.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la chiusura dello statement: " + e.getMessage());

            }
        }
        return frasiAggiornate;
    }

    public int getNumeroModifichePerAutore(Utente autore) {
        PreparedStatement selectModifica = null;
        ResultSet rs = null;
        int numeroModifiche = 0;
        ArrayList<Pagina> pagineDellAutore = new ArrayList<Pagina>();
        ArrayList<Frase> frasiPagina = new ArrayList<Frase>();
        ArrayList<Modifica> modificheFrase = new ArrayList<Modifica>();
        try {
            String query = "SELECT p.titolo FROM utente u, pagina p WHERE u.username = p.usernameautore and u.username = ? ";
            selectModifica = connection.prepareStatement(query);
            selectModifica.setString(1, autore.getUsername());
            rs = selectModifica.executeQuery();
            while (rs.next()) {
                pagineDellAutore.add(getPagina(rs.getString("titolo")));
            }
            for(Pagina p: pagineDellAutore) {
                frasiPagina = p.getFrasi();
                for(Frase f: frasiPagina) {
                    modificheFrase = f.getModifiche();
                    for(Modifica m: modificheFrase) {
                        if(m.getValutazione() == null) { //Se non è ancora stata valutata dovrà uscire tra le notifiche
                            numeroModifiche++;
                        }
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Errore durante l'estrazione del numero di modifiche per autore: " + e.getMessage());
        } catch (NotFoundException e) {
            System.out.println("Errore durante l'estrazione della pagina: " + e.getMessage());
        } finally {
            try {
                if (selectModifica != null) {
                    selectModifica.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la chiusura dello statement: " + e.getMessage());

            }
        }
        return numeroModifiche;
    }

    public Modifica getModificaPropostaMenoRecente(Utente autore){
        PreparedStatement selectModifica = null;
        ResultSet rs = null;
        int numeroModifiche = 0;
        ArrayList<Pagina> pagineDellAutore = new ArrayList<Pagina>();
        ArrayList<Frase> frasiPagina = new ArrayList<Frase>();
        ArrayList<Modifica> modificheFrase = new ArrayList<Modifica>();
        java.util.Date dataMinModifica = null;
        Modifica modificaMenoRecente = null;
        try {
            String query = "SELECT p.titolo FROM utente u, pagina p WHERE u.username = p.usernameautore and u.username = ? ";
            selectModifica = connection.prepareStatement(query);
            selectModifica.setString(1, autore.getUsername());
            rs = selectModifica.executeQuery();
            if (rs.next()) { //Salvo una data minima per poi confrontarla con le altre dopo
                long dateTimestamp = rs.getDate("datamodificaproposta").getTime();
                long timeTimestamp = rs.getTime("oramodificaproposta").getTime();
                long sumTimeStamp = dateTimestamp + timeTimestamp;
                dataMinModifica = new java.util.Date(sumTimeStamp);
                pagineDellAutore.add(getPagina(rs.getString("titolo")));
            }

            while (rs.next()) {
                pagineDellAutore.add(getPagina(rs.getString("titolo")));
            }
            for(Pagina p: pagineDellAutore) {
                frasiPagina = p.getFrasi();
                for(Frase f: frasiPagina) {
                    modificheFrase = f.getModifiche();
                    for(Modifica m: modificheFrase) {
                        if(dataMinModifica == null) {
                            return null;
                        }
                        if(m.getValutazione() == null && m.getDataEOraModificaProposta().after(dataMinModifica)) {
                            modificaMenoRecente = m;
                        }
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Errore durante l'estrazione del numero di modifiche per autore: " + e.getMessage());
        } catch (NotFoundException e) {
            System.out.println("Errore durante l'estrazione della pagina: " + e.getMessage());
        } finally {
            try {
                if (selectModifica != null) {
                    selectModifica.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la chiusura dello statement: " + e.getMessage());

            }
        }
        return modificaMenoRecente;
    }
}
