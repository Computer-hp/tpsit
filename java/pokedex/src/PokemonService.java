import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;

public class PokemonService {
    private static final String API_URL = "https://pokeapi.co/api/v2/pokemon/";

    public static Pokemon getPokemonInfo(String inputPokemonName) {
        try {
            URL url = new URL(API_URL + inputPokemonName);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            StringBuilder jsonContent = new StringBuilder();

            while ((line = br.readLine()) != null)
                jsonContent.append(line);

            br.close();
            connection.disconnect();

            JsonObject jsonObj = JsonParser.parseString(jsonContent.toString()).getAsJsonObject();

            int pokedexNumber = jsonObj.get("id").getAsInt();
            String pokemonName = jsonObj.get("name").getAsString();
            String type1 = getPokemonTypeFromJsonArray(jsonObj, 0);
            String type2 = getPokemonTypeFromJsonArray(jsonObj, 1);

            return new Pokemon(pokedexNumber, pokemonName, type1, type2);
        }
        catch (Exception e) {
            System.out.println("Error fetching Pokémon data: " + e.getMessage());
            return null;
        }
    }


    private static String getPokemonTypeFromJsonArray(JsonObject jsonObj, int i) {
        JsonArray typesArray = jsonObj.getAsJsonArray("types");
        System.out.println(typesArray);

        if (i >= typesArray.size()) {
            System.out.print("\nThere is no second element of the pokemon.\n");
            return null;
        }

        JsonObject firstTypeObject = typesArray.get(i).getAsJsonObject();
        System.out.println(firstTypeObject);

        JsonObject typeDetails = firstTypeObject.getAsJsonObject("type");
        System.out.println(typeDetails);

        return typeDetails.get("name").getAsString();
    }
}