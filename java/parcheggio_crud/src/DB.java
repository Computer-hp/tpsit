import java.sql.*;

public class DB {

    /**
     * Represents a connection to a database.
     *
     * The conn variable is used to establish a connection to a database and execute SQL queries.
     *
     * Methods in the DB class utilize the conn variable to execute queries such as SELECT, INSERT, UPDATE, and DELETE.
     *
     * This variable should be initialized with a valid Connection object before using any of the database-related methods.
     */
    private Connection conn;


    /**
     * Represents a connection to a database.
     */
    public DB(String address, String port, String databaseName, String username, String password) {
        //stringa di connessione -> jdbc:mysql://127.0.0.1:3306/nomeDB
        String dbConnectionString = "jdbc:mysql://" + address + ":" + port + "/" + databaseName;
        try {
            conn = DriverManager.getConnection(dbConnectionString, username, password);
            if (conn != null)
                System.out.println("connessione avvenuta");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * Executes a SELECT query on the database.
     *
     * @param what   the column(s) to select
     * @param from   the table(s) to select from
     * @param where  the condition to filter the result
     * @param is     the value to compare in the WHERE clause
     * @return a string representation of the selected data, formatted as tab-separated values
     * @throws SQLException if an SQL exception occurs while executing the query
     */


    public void inserisciAutoDaDatabase() throws SQLException {
        String query = "SELECT * FROM auto";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String targa = rs.getString("targa");
                String marca = rs.getString("marca");
                String modello = rs.getString("modello");

                listaAuto.add(new Auto(targa, marca, modello));
            }
        }
    }

    public boolean aggiungiAutoSuDatabase(String targa, String marca, String modello) throws SQLException, AlreadyPresentPlate {
        // Check if the car already exists
        /*String checkQuery = "SELECT targa FROM auto WHERE targa = ?";
        String insertQuery = "INSERT INTO auto (targa, marca, modello) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            checkStmt.setString(1, targa);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                throw new AlreadyPresentPlate(targa);
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                insertStmt.setString(1, targa);
                insertStmt.setString(2, marca);
                insertStmt.setString(3, modello);

                insertStmt.executeUpdate();
                return true;
            }
        }
        */
        
    }

    public boolean rimuoviAutoDaDatabase(String targa) {
        String deleteQuery = "DELETE FROM auto WHERE targa = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {

            stmt.setString(1, targa);
            int affectedRows = stmt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Errore durante la rimozione dal database: " + e.getMessage());
            return false;
        }
    }


//===========================================================================
}