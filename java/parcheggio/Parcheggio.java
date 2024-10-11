import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Parcheggio {
    private String nome;
    private List<Auto> listaAuto;
    private static final String filePath = "parcheggio_auto.txt";

    public Parcheggio(String nome) {
        this.nome = nome;
        this.listaAuto = new ArrayList<>();

        try {
            inserisciAutoDaFile();
        }
        catch (IOException e) {
            System.out.println("Errore durante la lettura del file: " + e.getMessage());
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void aggiungiAuto(String targa, String marca, String modello) {
        try {
            if (aggiungiAutoSuFile(targa, marca, modello)) {
                listaAuto.add(new Auto(targa, marca, modello));
                System.out.println("Auto aggiunta con successo.");
            }
        }
        catch (AlreadyPresentPlate e) {
            System.out.println(e.getMessage());
        }
    }

    public void rimuoviAuto(String targa) {
        Auto auto = cercaAutoPerTarga(targa);

        if (auto != null && rimuoviAutoDaFile(targa)) {
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

    private void inserisciAutoDaFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] datiAuto = line.split(",");

            if (datiAuto.length == 3)
                listaAuto.add(new Auto(datiAuto[0], datiAuto[1], datiAuto[2]));
        }

        reader.close();
    }

    private boolean aggiungiAutoSuFile(String targa, String marca, String modello) throws AlreadyPresentPlate {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null)
                if (line.split(",")[0].equalsIgnoreCase(targa))
                    throw new AlreadyPresentPlate(targa);
        } 
        catch (IOException e) {
            System.out.println("Errore di lettura del file: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(targa + "," + marca + "," + modello + "\n");
            return true;
        } 
        catch (IOException e) {
            System.out.println("Errore di scrittura sul file: " + e.getMessage());
            return false;
        }
    }

    private boolean rimuoviAutoDaFile(String targa) {
        File inputFile = new File(filePath);
        File tempFile = new File("tempFile.txt");

        boolean removed = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.split(",")[0].equalsIgnoreCase(targa))
                    writer.write(line + "\n");
                    
                else removed = true;
            }

        } 
        catch (IOException e) {
            System.out.println("Errore nella rimozione dal file: " + e.getMessage());
            return false;
        }

        if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
            System.out.println("Errore nella modifica del file.");
            return false;
        }

        return removed;
    }
}