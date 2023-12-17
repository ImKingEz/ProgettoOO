package database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The type Connessione database.
 */
public class ConnessioneDatabase {
    private static ConnessioneDatabase instance;
    private Connection connection = null;
    private String nome;
    private String password;
    private String url;
    private String driver;

    private ConnessioneDatabase() throws SQLException {
        Properties prop = new Properties();
        FileInputStream input = null;

        try {
            // Carica il file di configurazione
            input = new FileInputStream("src/main/java/database/credenzialiDatabase.txt");
            prop.load(input);

            // Ottieni i valori dalle proprietà
            nome = prop.getProperty("db.nome");
            password = prop.getProperty("db.password");
            url = prop.getProperty("db.url");
            driver = prop.getProperty("db.driver");

            Class.forName(driver);
            connection = DriverManager.getConnection(url, nome, password);

        } catch (IOException | ClassNotFoundException | SQLException ex) {
            System.out.println("Errore durante la connessione al database: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Gets connection.
     *
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     * @throws SQLException the sql exception
     */
    public static ConnessioneDatabase getInstance() throws SQLException {
// se la connessione non esiste o è chiusa ne creo una nuova
        if (instance == null) {
            instance = new ConnessioneDatabase();
        } else if (instance.getConnection().isClosed()) {
            instance = new ConnessioneDatabase();
        }
// altrimenti restituisco il riferimento a quella esistente
        return instance;
    }

}
