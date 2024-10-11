import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Parcheggio {
    private String nome;
    private List<Auto> listaAuto;

    public Parcheggio(String nome) {
        this.nome = nome;
        this.listaAuto = new ArrayList<>();

        try {
            inserisciAutoDaDatabase();
        } catch (SQLException e) {
            System.out.println("Errore durante la lettura dal database: " + e.getMessage());
        }
    }

    public void aggiungiAuto(String targa, String marca, String modello) {
        try {
            if (aggiungiAutoSuDatabase(targa, marca, modello)) {
                listaAuto.add(new Auto(targa, marca, modello));
                System.out.println("Auto aggiunta con successo.");
            }
        } catch (AlreadyPresentPlate e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println("Errore nel database: " + e.getMessage());
        }
    }

    public void rimuoviAuto(String targa) {
        Auto auto = cercaAutoPerTarga(targa);

        if (auto != null && rimuoviAutoDaDatabase(targa)) {
            listaAuto.remove(auto);
            System.out.println("Auto rimossa con successo.");
            return;
        }

        System.out.println("Auto non trovata o errore nella rimozione.");
    }

    public Auto cercaAutoPerTarga(String targa) {
        for (Auto auto : listaAuto)
            if (auto.getTarga().equalsIgnoreCase(targa))
                return auto;

        return null;
    }

    public String cercaAutoPerMarca(String marca) {
        StringBuilder result = new StringBuilder();

        for (Auto auto : listaAuto)
            if (auto.getMarca().equalsIgnoreCase(marca))
                result.append(auto.toString()).append("\n");

        return result.toString().isEmpty() ? "Nessuna auto trovata per questa marca." : result.toString();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (Auto auto : listaAuto)
            result.append(auto.toString()).append("\n");

        return result.toString().isEmpty() ? "Nessuna auto presente nel parcheggio." : result.toString();
    }

    private void inserisciAutoDaDatabase() throws SQLException {
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

    private boolean aggiungiAutoSuDatabase(String targa, String marca, String modello) throws SQLException, AlreadyPresentPlate {
        // Check if the car already exists
        String checkQuery = "SELECT targa FROM auto WHERE targa = ?";
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
    }

    private boolean rimuoviAutoDaDatabase(String targa) {
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
}
