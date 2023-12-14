package postgresDAO;

import dao.ListinoDAO;
import database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.SQLException;

public class ListinoPostgresDAO implements ListinoDAO {

    private Connection connection;

    public ListinoPostgresDAO() {
        try {
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {   //fare la exception
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
