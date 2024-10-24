import java.sql.*;


public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:pokemon.db";

    public static void createDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {
            String sql = "create table if not exists pokemon (\n" +
                         "id int primary key,\n" +
                         "pokedex_number int,\n" +
                         "name varchar(16) primary key,\n" +
                         "type_1 varchar(8),\n" +
                         "type_2 varchar(8),\n" +
                         ");";

            statement.execute(sql);
        }
        catch (SQLException e) {
            System.out.print('\n' + e.getMessage() + '\n');
        }
    }

    public static void insertPokemon(int pokedexNumber, String name, String type1, String type2) {
        String sql = "insert into pokemon(pokedexNumber, name, type1, type2) values(?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, pokedexNumber);
            preparedStatement.setString(3, type1);
            preparedStatement.setString(4, type2);
        }
        catch (SQLException e) {
            System.out.print('\n' + e.getMessage() + '\n');
        }
    }

    public static void showAllPokemons() {
        String sql = "SELECT * FROM pokemon";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             var rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("name") + "\t" +
                        rs.getInt("pokedex_number") + "\t" +
                        rs.getString("type_1") + "\t" +
                        rs.getString("type_2"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
