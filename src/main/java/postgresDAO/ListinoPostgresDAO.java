package postgresDAO;

import controller.Controller;
import dao.ListinoDAO;
import database.ConnessioneDatabase;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public void setPagina(String titolo, Autore autore) throws Exception {
        PreparedStatement insertPagina = null;
        try {
            String query = "INSERT INTO pagina (titolo, datacreazione, oracreazione, usernameautore) VALUES (?, SYSDATE, SYSTIME, ?)";
            insertPagina = connection.prepareStatement(query);
            insertPagina.setString(1, titolo);
            insertPagina.setString(2, autore.getUsername());
            insertPagina.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Errore durante l'inserimento della pagina: " + e.getMessage());
            throw new Exception();

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


    public int numeroPagineCreateDaUnUtente(Utente utente){
        PreparedStatement selectPagina = null;
        int numeroPagine=0;
        ResultSet rs = null;
        try {
            String query = "SELECT COUNT(*) FROM pagina WHERE usernameautore = ? ";
            selectPagina = connection.prepareStatement(query);
            selectPagina.setString(1, utente.getUsername());
            rs = selectPagina.executeQuery();
            /*while (rs.next()) {
                numeroPagine = rs.getInt("count");
            }*/
            numeroPagine = rs.getInt("count");

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

    //public Pagina getPagina(String titolo);

}
