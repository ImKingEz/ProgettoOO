package postgresDAO;

import controller.Controller;
import dao.ListinoDAO;
import database.ConnessioneDatabase;
import model.GiaEsistenteException;
import model.Utente;
import model.invalidLoginException;

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
        } catch (SQLException e) {   //fare la exception
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setUtente(String username, String password) {
        PreparedStatement insertUtente = null;
        try {
            String query = "INSERT INTO utente (username, password) VALUES (?, ?)";
            insertUtente = connection.prepareStatement(query);
            insertUtente.setString(1, username);
            insertUtente.setString(2, password);
            insertUtente.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Errore durante l'inserimento dell'utente: " + e.getMessage());
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
        try {
            String query = "SELECT username,password  FROM utente WHERE username = ? ";
            selectUtente = connection.prepareStatement(query);
            selectUtente.setString(1, username);
            ResultSet rs = selectUtente.executeQuery();
            u = controller.setUtente(rs.getString("username"),rs.getString("password"));

        } catch (SQLException | GiaEsistenteException | invalidLoginException ile) {
            System.out.println("Errore durante l'estrazione dell'utente: " + ile.getMessage());
        } finally {
            try {
                if (selectUtente != null) {
                    selectUtente.close();
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la chiusura dello statement: " + e.getMessage());
            }
        }
        return u;
    }

}
