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


}
