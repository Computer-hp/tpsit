import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        CrudService crudService = new CrudService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Crea prodotto");
            System.out.println("2. Mostra tutti i prodotti");
            System.out.println("3. Aggiorna prodotto");
            System.out.println("4. Elimina prodotto");
            System.out.println("5. Esci");
            System.out.print("Seleziona un'opzione: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Inserisci il nome del prodotto: ");
                    String name = scanner.nextLine();
                    System.out.print("Inserisci il prezzo del prodotto: ");
                    double price = scanner.nextDouble();
                    Product product = new Product(name, price);
                    crudService.createProduct(product);
                    break;

                case 2:
                    List<Product> products = crudService.getAllProducts();
                    products.forEach(System.out::println);
                    break;

                case 3:
                    System.out.print("Inserisci l'ID del prodotto da aggiornare: ");
                    String id = scanner.nextLine();
                    System.out.print("Inserisci il nuovo nome del prodotto: ");
                    String newName = scanner.nextLine();
                    System.out.print("Inserisci il nuovo prezzo del prodotto: ");
                    double newPrice = scanner.nextDouble();
                    Product updatedProduct = new Product(newName, newPrice);
                    updatedProduct.setId(id);
                    crudService.updateProduct(updatedProduct);
                    break;

                case 4:
                    System.out.print("Inserisci l'ID del prodotto da eliminare: ");
                    String productId = scanner.nextLine();
                    crudService.deleteProduct(productId);
                    break;

                case 5:
                    System.out.println("Uscita...");
                    return;

                default:
                    System.out.println("Opzione non valida. Riprova.");
            }
        }
    }
}