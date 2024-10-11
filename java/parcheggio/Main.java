import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Parcheggio parcheggio = new Parcheggio("Parcheggio Centrale");

        while (true) {
            System.out.println("\n--- Menu Parcheggio ---");
            System.out.println("1. Aggiungere un'auto");
            System.out.println("11. Rimuovere auto");
            System.out.println("2. Cercare un'auto per targa");
            System.out.println("3. Cercare auto per marca");
            System.out.println("4. Visualizzare tutte le auto");
            System.out.println("5. Uscire");
            System.out.print("Seleziona un'opzione: ");

            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1:
                    System.out.print("Inserisci la targa: ");
                    String targa = scanner.nextLine();
                    System.out.print("Inserisci la marca: ");
                    String marca = scanner.nextLine();
                    System.out.print("Inserisci il modello: ");
                    String modello = scanner.nextLine();
                    parcheggio.aggiungiAuto(targa, marca, modello);
                    break;

                case 11:
                    System.out.print("Inserisci la targa dell'auto che vuoi rimouvere: ");
                    targa = scanner.nextLine();
                    parcheggio.rimuoviAuto(targa);
                    break;

                case 2:
                    System.out.print("Inserisci la targa: ");
                    targa = scanner.nextLine();
                    Auto auto = parcheggio.cercaAutoPerTarga(targa);
                    if (auto != null)
                        System.out.println("\nAuto trovata: " + auto);

                    else
                        System.out.println("\nNessuna auto trovata con la targa specificata.");

                    break;

                case 3:
                    System.out.print("Inserisci la marca: ");
                    marca = scanner.nextLine();
                    System.out.println("\nAuto trovate:\n" + parcheggio.cercaAutoPerMarca(marca));
                    break;

                case 4:
                    System.out.println("\nAuto presenti nel parcheggio:\n" + parcheggio);
                    break;

                case 5:
                    System.out.println("\nUscita dal programma.\n");
                    scanner.close();
                    return;

                default:
                    System.out.println("\nOpzione non valida.\n");
            }
        }
    }
}