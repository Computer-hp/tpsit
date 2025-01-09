import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String pokemonName;

        while (true) {
            System.out.println("1 - Search pokemon");
            System.out.println("2 - See pokemon in database");
            System.out.println("3 - Exit");
            int option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    System.out.println("Enter pokemon name");
                    pokemonName = sc.nextLine();
                    Pokemon pokemon = PokemonService.getPokemonInfo(pokemonName);

                    if (pokemon == null) {
                        System.out.println("Pokemon not found\n");
                        break;
                    }

                    String response;
                    do {
                        System.out.println("Do you want to save the pokemon into the database y/n:");
                        response = sc.nextLine();

                    } while (!response.equalsIgnoreCase("y") &&
                            !response.equalsIgnoreCase("n"));

                    if (response.equalsIgnoreCase("n")) break;

                    DatabaseManager.insertPokemon(pokemon);
                    break;

                case 2:
                    DatabaseManager.showAllPokemons();
                    break;

                case 3:
                    System.exit(0);
                    break;

                default:
                    break;
            }
        }
    }
}
