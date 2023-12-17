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


/**
 * La classe ListinoPostgresDAO implementa i metodi per la gestione dei dati relativi al database mediante l'utilizzo di postgreSQL.
 */
public class ListinoPostgresDAO implements ListinoDAO {

    private Connection connection;
    private Controller controller;

    /**
     * Inizializza un nuovo Listino postgres dao.
     */
    public ListinoPostgresDAO() {
        try {
            connection = ConnessioneDatabase.getInstance().getConnection();
            this.controller = new Controller(this);
        } catch (SQLException e) {   //fare la exception
            e.printStackTrace();
        }
    }

    /**
     * setSchema permette di settare lo schema su cui lavorare.
     */
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

    /**
     * setUtente permette di inserire un utente nel database dati username e password.
     * @param username the username
     * @param password the password
     * @throws Exception the exception
     */
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

    /**
     * getUtente permette di estrarre un utente dal database dato un username.
     * @param username the username
     * @return the utente
     */
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

    /**
     * setPagina permette di inserire una pagina nel database dati titolo e autore.
     * @param titolo the titolo
     * @param autore the autore
     * @throws GiaEsistenteException    the gia esistente exception
     * @throws NotABlankException       the not a blank exception
     * @throws LunghezzaMinimaException the lunghezza minima exception
     */
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
            throw new GiaEsistenteException();
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

    /**
     * getPagina permette di estrarre una pagina dal database dato un titolo.
     * @param titolo the titolo
     * @throws NotFoundException the not found exception
     * @return the pagina
     */
    public Pagina getPagina(String titolo) throws NotFoundException {
        PreparedStatement selectPagina = null;
        Pagina p = null;
        ResultSet rs = null;

        try {
            String query = "SELECT titolo, usernameautore, datacreazione, oracreazione FROM pagina WHERE titolo = ?";
            selectPagina = connection.prepareStatement(query);
            selectPagina.setString(1, titolo);
            rs = selectPagina.executeQuery();
            if(rs.next()) {
                long dateTimestamp = rs.getDate("datacreazione").getTime();
                long timeTimestamp = rs.getTime("oracreazione").getTime();
                long combinedTimestamp = dateTimestamp + timeTimestamp;
                java.util.Date utilDate = new java.util.Date(combinedTimestamp);

                p = new Pagina(rs.getString("titolo"), utilDate, getUtente(rs.getString("usernameautore")));
                p.setFrasi(getFrasi(p));
                return p;
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

    /**
     * getIdPagina permette di estrarre l'id di una pagina dal database dato un titolo.
     * @param titolo the titolo
     * @throws NotFoundException the not found exception
     * @return the id pagina
     */
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

    /**
     * La prima variante di setFrase permette di inserire una frase nel database dati testo e pagina.
     * @param testo the testo
     * @param pagina the pagina
     * @throws NotABlankException the not a blank exception
     * @throws NotFoundException the not found exception
     */
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

    /**
     * La seconda variante di setFrase permette di inserire una frase nel database dati testo, pagina e pagina linkata.
     * @param testo the testo
     * @param pagina the pagina
     * @param paginaLinkata the pagina linkata
     * @throws NotABlankException the not a blank exception
     * @throws NotFoundException the not found exception
     */
    public void setFrase(String testo, Pagina pagina, Pagina paginaLinkata) throws NotABlankException, NotFoundException {
        PreparedStatement insertFrase = null;
        try {
            String query = "INSERT INTO frase(testo, indice, idpagina) VALUES(?, ?, ?)";
            insertFrase = connection.prepareStatement(query);
            insertFrase.setString(1, testo);
            insertFrase.setInt(2, controller.calcolaIndice(pagina));
            insertFrase.setInt(3, getIdPagina(pagina.getTitolo()));
            insertFrase.executeUpdate();
            String query2 = "INSERT INTO linkare(idpaginalinkata, idpaginafrase, testo, indice) VALUES(?, ?, ?, ?)";
            insertFrase = connection.prepareStatement(query2);
            insertFrase.setInt(1, getIdPagina(paginaLinkata.getTitolo()));
            insertFrase.setInt(2, getIdPagina(pagina.getTitolo()));
            insertFrase.setString(3, testo);
            insertFrase.setInt(4, controller.calcolaIndice(pagina));
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

    /**
     * setModifica permette di inserire una modifica nel database dati testo, username modificatore, frase e pagina.
     * @param testo the testo
     * @param usernamemodificatore the usernamemodificatore
     * @param frase the frase
     * @param pagina the pagina
     * @throws AccettazioneAutomaticaException the accettazione automatica exception
     */
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

    /**
     * getModifica permette di estrarre la modifica più recente di una frase dal database. Il metodo è stato implementato ma non usato poichè non necessario.
     */
    public Modifica getModifica(Frase frase) throws NotFoundException, IllegalArgumentException { //prendo la modifica più recente
        PreparedStatement selectModifica = null;
        Pagina p = null;
        ResultSet rs = null;

        try {
            String query = "SELECT m.idmodifica, m.testo, m.username, m.datamodificaproposta, m.oramodificaproposta  " +
                    "FROM modifica m, frase f " +
                    "WHERE m.idpaginafrase = f.idpagina and m.testofrase = f.testo and m.indice = f.indice " +
                    "and f.indice = ? and f.idpagina = ? " +
                    "order by datamodificaproposta desc, oramodificaproposta desc";
            selectModifica = connection.prepareStatement(query);
            selectModifica.setInt(1, frase.getIndice());
            selectModifica.setInt(2, getIdPagina(frase.getPaginaDiAppartenenza().getTitolo()));
            rs = selectModifica.executeQuery();
            if(rs.next()) { //prendo solo la prima tupla, ossia quella con la data maggiore
                long dateTimestamp = rs.getDate("datamodificaproposta").getTime();
                long timeTimestamp = rs.getTime("oramodificaproposta").getTime();

                // Sommo i due timestamp
                long combinedTimestamp = dateTimestamp + timeTimestamp;
                // Creo un oggetto java.util.Date utilizzando il timestamp combinato
                java.util.Date utilDate = new java.util.Date(combinedTimestamp);
                return new Modifica(rs.getInt("idmodifica"), rs.getString("testo"), utilDate, getUtente(rs.getString("username")),frase);
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

    /**
     * getModifica2 permette di estrarre la modifica più recente di una frase dal database.
     * @param frase the frase
     * @return the modifica
     */
    public Modifica getModifica2(Frase frase) throws NotFoundException, IllegalArgumentException { //prendo la modifica più recente
        PreparedStatement selectModifica = null;
        Pagina p = null;
        ResultSet rs = null;

        try {
            String query = "select m.idmodifica, m.testo, m.datamodificaproposta, m.oramodificaproposta, m.username from frase f join modifica m on (f.idpagina = ? and f.indice = ? and f.idpagina = m.idpaginafrase and f.testo = m.testofrase and f.indice = m.indice) \n" +
                    "join valutazione v on (v.idmodifica = m.idmodifica and v.accettazione = true)\n" +
                    "order by f.indice asc, m.datamodificaproposta desc, m.oramodificaproposta desc";
            selectModifica = connection.prepareStatement(query);
            selectModifica.setInt(1, getIdPagina(frase.getPaginaDiAppartenenza().getTitolo()));
            selectModifica.setInt(2, frase.getIndice());
            rs = selectModifica.executeQuery();
            if(rs.next()) { //prendo solo la prima tupla, ossia quella con la data maggiore
                long dateTimestamp = rs.getDate("datamodificaproposta").getTime();
                long timeTimestamp = rs.getTime("oramodificaproposta").getTime();

                // Sommo i due timestamp
                long combinedTimestamp = dateTimestamp + timeTimestamp;
                // Creo un oggetto java.util.Date utilizzando il timestamp combinato
                java.util.Date utilDate = new java.util.Date(combinedTimestamp);
                return new Modifica(rs.getInt("idmodifica"), rs.getString("testo"), utilDate, getUtente(rs.getString("username")),frase);
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

    /**
     * getModifiche permette di estrarre tutte le modifiche di una frase dal database.
     * @param frase the frase
     * @return the modifiche
     * @throws NotFoundException        the not found exception
     * @throws IllegalArgumentException the illegal argument exception
     */
    public ArrayList<Modifica> getModifiche(Frase frase) throws NotFoundException, IllegalArgumentException { //prendo la modifica più recente
        PreparedStatement selectModifica = null;
        Pagina p = null;
        ResultSet rs = null;
        ArrayList<Modifica> modifiche = new ArrayList<Modifica>();
        try {
            String query = "select * \n" +
                    "from modifica m, valutazione v\n" +
                    "where m.idmodifica = v.idmodifica and m.indice = ? and v.accettazione = true";
            selectModifica = connection.prepareStatement(query);
            selectModifica.setInt(1, frase.getIndice());
            rs = selectModifica.executeQuery();
            while(rs.next()) {
                long dateTimestamp = rs.getDate("datamodificaproposta").getTime();
                long timeTimestamp = rs.getTime("oramodificaproposta").getTime();

                // Sommo i due timestamp
                long combinedTimestamp = dateTimestamp + timeTimestamp;
                // Creo un oggetto java.util.Date utilizzando il timestamp combinato
                java.util.Date utilDate = new java.util.Date(combinedTimestamp);
                modifiche.add(new Modifica(rs.getInt("idmodifica"), rs.getString("testo"), utilDate, getUtente(rs.getString("username")),frase));
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
        return modifiche;
    }

    /**
     * getIdModifica permette di estrarre l'id di una modifica dal database dati modifica, frase, pagina e username modificatore. Il metodo è stato implementato ma non usato poichè non necessario.
     * @param modifica the modifica
     * @param frase the frase
     * @param pagina the pagina
     * @param usernamemodificatore the usernamemodificatore
     * @return the id modifica
     */
    public int getIdModifica(Modifica modifica, Frase frase, Pagina pagina, String usernamemodificatore) throws NotFoundException {
        PreparedStatement selectModifica = null;
        ResultSet rs = null;

        try {
            String query = "SELECT m.idmodifica FROM modifica m, frase f " +
                    "WHERE m.testo = ? and m.username = ? and m.idpaginafrase = ? and m.testofrase = ? and m.indice = ? " +
                    "and m.testofrase = f.testo and m.indice = f.indice and m.idpaginafrase = f.idpagina";
            selectModifica = connection.prepareStatement(query);
            selectModifica.setString(1, modifica.getTesto());
            selectModifica.setString(2, usernamemodificatore);
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

    /**
     * setValutazione permette di inserire una valutazione nel database dati accettazione, modifica e autore.
     * @param accettazione the accettazione
     * @param modifica the modifica
     * @param autore the autore
     */
    public void setValutazione(boolean accettazione, Modifica modifica, Utente autore) {
        PreparedStatement insertValutazione = null;

        java.util.Date currentDate = Calendar.getInstance().getTime(); // Ottieni la data attuale
        Date dataValutazione = new Date(currentDate.getTime()); // Converte java.util.Date a java.sql.Date
        Time oraValutazione = new Time(currentDate.getTime()); // Ottieni l'ora attuale

        try {
            String query = "INSERT INTO valutazione(accettazione, datavalutazione, oravalutazione, usernameautore, idmodifica) VALUES(?, ?, ?, ?, ?)";
            insertValutazione = connection.prepareStatement(query);
            insertValutazione.setBoolean(1, accettazione);
            insertValutazione.setDate(2, dataValutazione);
            insertValutazione.setTime(3, oraValutazione);
            insertValutazione.setString(4, autore.getUsername());
            insertValutazione.setInt(5, modifica.getIdModifica());
            modifica.setValutazione(new Valutazione(accettazione, currentDate, autore, modifica));
            insertValutazione.executeUpdate();
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

    /**
     * getValutazione permette di estrarre una valutazione dal database dati autore e modifica.
     * @param modifica the modifica
     * @param autore the autore
     * @throws NotFoundException the not found exception
     * @return the valutazione
     */
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
            selectValutazione.setInt(2, modifica.getIdModifica());
            rs = selectValutazione.executeQuery();
            if(rs.next()) {
                long dateTimestamp = rs.getDate("datavalutazione").getTime();
                long timeTimestamp = rs.getTime("oravalutazione").getTime();
                // Sommo i due timestamp
                long combinedTimestamp = dateTimestamp + timeTimestamp;
                // Creo un oggetto java.util.Date utilizzando il timestamp combinato
                java.util.Date utilDate = new java.util.Date(combinedTimestamp);
                Valutazione valutazione = new Valutazione(rs.getBoolean("accettazione"), utilDate, autore, modifica);
                modifica.setValutazione(valutazione);
                return valutazione;
            }
        } catch (SQLException e) {
            System.out.println("Errore durante l'estrazione della valutazione: " + e.getMessage());
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

    /**
     * numeroPagineCreateDaUnUtente permette di estrarre il numero di pagine create da un utente dal database dati username.
     * @param username the username
     * @return the int
     */
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

    /**
     * checkEsistenzaUtenti permette di verificare se esistono utenti nel database.
     * @return the boolean
     */
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

    /**
     * checkEsistenzaPagine permette di verificare se esistono pagine nel database.
     * @return the boolean
     */
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

    /**
     * getFrasi permette di estrarre tutte le frasi di una pagina dal database.
     * @param pagina the pagina
     * @return the frasi
     */
    public ArrayList<Frase> getFrasi(Pagina pagina) {
        PreparedStatement selectFrase = null;
        ArrayList<Frase> frasi = new ArrayList<Frase>();
        ResultSet rs = null;
        String titolopagina = pagina.getTitolo();
        try {
            String query =  "select * from frase f left join modifica m on (f.idpagina = ? and f.idpagina = m.idpaginafrase and f.testo = m.testofrase and f.indice = m.indice) " +
                    "left join valutazione v on (v.idmodifica = m.idmodifica and accettazione = true)\n" +
                    "order by f.indice asc, m.datamodificaproposta asc, m.oramodificaproposta asc";
            selectFrase = connection.prepareStatement(query);
            selectFrase.setInt(1, getIdPagina(titolopagina));
            rs = selectFrase.executeQuery();
            while (rs.next()) {
                frasi.add(new Frase(rs.getString("testo"), rs.getInt("indice"), pagina));
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
        return frasi;
    }

    /**
     * getFrasiAggiornate permette di estrarre tutte le frasi aggiornate (ultime modifiche se presenti) di una pagina dal database.
     * @param pagina the pagina
     * @return the frasi aggiornate
     * @throws NotFoundException the not found exception
     */
    public ArrayList<Frase> getFrasiAggiornate(Pagina pagina) throws NotFoundException {
        int contatore=0;
        int controlloStampa=0;
        int controlloIndici=0;
        ArrayList<Frase> frasiTotali = new ArrayList<Frase>();
        ArrayList<Frase> frasiAggiornate = new ArrayList<Frase>();
        ArrayList<Integer> indiciVisitati = new ArrayList<Integer>();
        ArrayList<Modifica> modificheperFrase = new ArrayList<Modifica>();
        java.util.Date dataMassima= Date.valueOf("1900-12-12");
        int confronto = -1;
        Modifica modificaMigliore = null;
        frasiTotali = getFrasi(pagina);
        for(Frase f: frasiTotali){
            contatore=0;
            controlloStampa=0;
            controlloIndici=0;
            for(int n: indiciVisitati){
                if(f.getIndice()==n){
                    controlloIndici=1;
                }
            }
            if(controlloIndici==0){
                for(Frase f2: frasiTotali){
                    if(f.getIndice()==f2.getIndice()){
                        contatore=contatore+1;
                    }
                }
                if(contatore==1){
                    frasiAggiornate.add(f);
                }else if(contatore==2) {
                    modificaMigliore=getModifica2(f); //controlare se accettazione è true
                    frasiAggiornate.add(getFrase(modificaMigliore.getTesto(), pagina));
                } else if(contatore > 2) {
                    modificaMigliore=getModifica2(f);
                    frasiAggiornate.add(getFrase(modificaMigliore.getTesto(), pagina));
                }
                indiciVisitati.add(f.getIndice());
            }
        }
        return frasiAggiornate;
    }

    /**
     * getModificaPropostaMenoRecente permette di estrarre la modifica proposta meno recente di una pagina dal database.
     * @param autore the autore
     * @return the modifica
     */
    public Modifica getModificaPropostaMenoRecente(Utente autore){
        PreparedStatement selectModifica = null;
        ResultSet rs = null;
        int numeroModifiche = 0;
        ArrayList<Pagina> pagineDellAutore = new ArrayList<Pagina>();
        ArrayList<Frase> frasiPagina = new ArrayList<Frase>();
        ArrayList<Modifica> modificheFrase = new ArrayList<Modifica>();
        ArrayList<Modifica> modificheNonValutate = new ArrayList<Modifica>();
        java.util.Date dataEora = null;
        java.util.Date dataMin = null;
        Modifica modificaMenoRecente = null;
        try {
            String query = "SELECT p.titolo FROM utente u, pagina p WHERE u.username = p.usernameautore and u.username = ? ";
            selectModifica = connection.prepareStatement(query);
            selectModifica.setString(1, autore.getUsername());
            rs = selectModifica.executeQuery();
            while (rs.next()) {
                pagineDellAutore.add(getPagina(rs.getString("titolo")));
            }
            for(Pagina p: pagineDellAutore) {
                String query2 = "select m.idmodifica, m.testo, m.datamodificaproposta, m.oramodificaproposta, m.testofrase\n" +
                        "from modifica m \n" +
                        "where m.idpaginafrase = ? and m.idmodifica not in (select v.idmodifica\n" +
                        "from valutazione v)";
                selectModifica = connection.prepareStatement(query2);
                selectModifica.setInt(1, getIdPagina(p.getTitolo()));
                rs = selectModifica.executeQuery();
                while (rs.next()) {
                    long dateTimestamp = rs.getDate("datamodificaproposta").getTime();
                    long timeTimestamp = rs.getTime("oramodificaproposta").getTime();
                    long sumTimeStamp = dateTimestamp + timeTimestamp;
                    dataEora = new java.util.Date(sumTimeStamp);
                    modificheNonValutate.add(new Modifica(rs.getInt("idmodifica"), rs.getString("testo"), dataEora, autore, getFrase(rs.getString("testofrase"), p)));
                }
                dataMin = dataEora; //imposto datamin all'ultimo modifica preso
                for(Modifica m: modificheNonValutate) {
                    if(m.getDataEOraModificaProposta().before(dataMin) || m.getDataEOraModificaProposta().equals(dataMin)) {
                        dataMin = m.getDataEOraModificaProposta();
                        modificaMenoRecente = m;
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

    /**
     * getFrase permette di estrarre una frase dal database dati testo e pagina.
     * @param testo  the testo
     * @param pagina the pagina
     * @return the frase
     * @throws NotFoundException
     */
    public Frase getFrase(String testo, Pagina pagina) throws NotFoundException {
        PreparedStatement selectFrase = null;
        ResultSet rs = null;
        try {
            String query = "SELECT testo, indice FROM frase WHERE testo = ? and idpagina = ?";
            selectFrase = connection.prepareStatement(query);
            selectFrase.setString(1, testo);
            selectFrase.setInt(2, getIdPagina(pagina.getTitolo()));
            rs = selectFrase.executeQuery();
            if(rs.next()) {
                return new Frase(testo, rs.getInt("indice"), pagina);
            }
        } catch (SQLException e) {
            System.out.println("Errore durante l'estrazione della pagina: " + e.getMessage());
        } catch (NotFoundException e) {
            System.out.println("id pagina non trovato");
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
        throw new NotFoundException();
    }

    /**
     * getNumeroModifichePerAutore permette di estrarre il numero di modifiche proposte per un autore dal database.
     * @param autore the autore
     * @return the int
     */
    public int getNumeroModifichePerAutore(Utente autore) {
        PreparedStatement selectModifica = null;
        ResultSet rs = null;
        int numeroModifiche = 0;
        ArrayList<Pagina> pagineDellAutore = new ArrayList<Pagina>();
        ArrayList<Frase> frasiPagina = new ArrayList<Frase>();
        ArrayList<Modifica> modificheFrase = new ArrayList<Modifica>();
        ArrayList<Modifica> modificheNonValutate = new ArrayList<Modifica>();
        java.util.Date dataEora = null;
        try {
            String query = "SELECT p.titolo FROM utente u, pagina p WHERE u.username = p.usernameautore and u.username = ? ";
            selectModifica = connection.prepareStatement(query);
            selectModifica.setString(1, autore.getUsername());
            rs = selectModifica.executeQuery();
            while (rs.next()) {
                pagineDellAutore.add(getPagina(rs.getString("titolo")));
            }
            for(Pagina p: pagineDellAutore) {
                String query2 = "select m.idmodifica, m.testo, m.datamodificaproposta, m.oramodificaproposta, m.testofrase\n" +
                        "from modifica m \n" +
                        "where m.idpaginafrase = ? and m.idmodifica not in (select v.idmodifica\n" +
                        "from valutazione v)";
                selectModifica = connection.prepareStatement(query2);
                selectModifica.setInt(1, getIdPagina(p.getTitolo()));
                rs = selectModifica.executeQuery();
                while (rs.next()) {
                    long dateTimestamp = rs.getDate("datamodificaproposta").getTime();
                    long timeTimestamp = rs.getTime("oramodificaproposta").getTime();
                    long sumTimeStamp = dateTimestamp + timeTimestamp;
                    dataEora = new java.util.Date(sumTimeStamp);
                    modificheNonValutate.add(new Modifica(rs.getInt("idmodifica"), rs.getString("testo"), dataEora, autore, getFrase(rs.getString("testofrase"), p)));
                }
                numeroModifiche = modificheNonValutate.size();
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

}
