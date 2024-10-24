import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int pokedexNumber;
        String name, type1, type2;

        // chiedi info
        while (true) {
            System.out.println("1. Search a pokemon");
            System.out.println("2. See pokemons in database");
            System.out.println("3. Exit");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.println("\nEnter the name of the pokemon: ");
                    String pokemonName = scanner.nextLine();

                    Pokemon pokemon = PokemonService.getPokemonInfo(pokemonName);

                    if (pokemon == null) {
                        System.out.println("\nPokemon not found.\n");
                        break;
                    }

                    System.out.println("Do you want to add this pokemon to the database? (y/n): ");
                    String reply;

                    do {
                        reply = scanner.nextLine().toLowerCase();
                    } while (reply.length() != 1 || reply.charAt(0) != 'y' && reply.charAt(0) != 'n');

                    if (reply.charAt(0) == 'n') break;

                    // add to database
                    DatabaseManager.insertPokemon(pokemon.getPokedexNumber(), pokemon.getName(), pokemon.getType1(), pokemon.getType2());
                    System.out.println("Pokémon added to the database.");

                    break;

                case 2:
                    System.out.println("\nPokemon in the database:\n");

                    break;

                case 3:
                    System.out.println("\nExit the application\n");
                    System.exit(0);
                    break;
            }
        }
    }
}
