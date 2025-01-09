import java.sql.*;
import java.util.Arrays;


/*
<database_name> = pokedex
<table_name> = pokemons

-----------------------------
| id | name | type1 | type2 |
| .  | ..   | ...   | ....  |
-----------------------------
*/


public class DatabaseManager {
    private static final String DB_NAME = "pokedex";
    private static final String TABLE_NAME = "pokemons";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";


    public static void insertPokemon(Pokemon pokemon) {
        String query = "INSERT INTO " + TABLE_NAME + " (id, name, type1, type2) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, pokemon.getPokedexNumber());
            preparedStatement.setString(2, pokemon.getName());
            preparedStatement.setString(3, pokemon.getType1());
            preparedStatement.setString(4, pokemon.getType2());

        // for single queries
            preparedStatement.executeUpdate();

        // for multiple queries, example: when using a for loop to insert a list of pokemons
        /*
            preparedStatement.addBatch();
            preparedStatement.executeBatch();
        */

            System.out.println("Insertion was successful\n");
        }
        catch (SQLException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public static void showAllPokemons() {
        String query = "SELECT * FROM pokemons";

        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("\nPokemons:\n");

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String type1 = resultSet.getString(3);
                String type2 = resultSet.getString(4);

                System.out.println("id: " + String.valueOf(id) + ", name: " + name + ", type1: " + type1 + ", type2: " + type2 + ";");
            }
        }
        catch (SQLException e) {
            System.out.println(e.getStackTrace());
        }
    }
}
