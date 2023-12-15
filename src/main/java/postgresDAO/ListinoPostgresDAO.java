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

    public void setPagina(String titolo, Utente autore) throws GiaEsistenteException, NotABlankException {
        PreparedStatement insertPagina = null;

        java.util.Date currentDate = Calendar.getInstance().getTime(); // Ottieni la data attuale
        Date dataCreazione = new Date(currentDate.getTime()); // Converte java.util.Date a java.sql.Date
        Time oraCreazione = new Time(currentDate.getTime()); // Ottieni l'ora attuale

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

    public int getIdPagina(String titolo){
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
        return -1;
    }

    public void setFrase(String testo, Pagina pagina) throws NotABlankException {
        PreparedStatement insertFrase = null;
        try {
            String query = "INSERT INTO frase(testo, indice, idpagina) VALUES(?, ?, ?)";
            insertFrase = connection.prepareStatement(query);
            insertFrase.setString(1, testo);
            insertFrase.setInt(2, controller.calcolaIndice(pagina));
            insertFrase.setInt(3, getIdPagina(pagina.getTitolo()));
            insertFrase.executeUpdate();
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
        }
        finally {
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
    public Modifica getModifica(Frase frase) throws NotFoundException { //prendo la modifica più recente
        PreparedStatement selectModifica = null;
        Pagina p = null;
        ResultSet rs = null;

        try {
            String query = "SELECT m.testo, m.username, m.datamodificaproposta, m.oramodificaproposta  FROM modifica m, frase f WHERE m.idpaginafrase = f.idpagina and m.testofrase = f.testo and m.indice = f.indice order by datamodificaproposta asc, oramodificaproposta asc";
            selectModifica = connection.prepareStatement(query);
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

}
