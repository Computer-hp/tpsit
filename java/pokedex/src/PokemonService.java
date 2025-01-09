import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;

import com.google.gson.*;


public class PokemonService {

    private static final String API_URL = "https://pokeapi.co/api/v2/pokemon/";

    public static Pokemon getPokemonInfo(String pokemonName) {
        try {
            URL url = new URL(API_URL + pokemonName);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");

            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
            JsonObject jsonObject = JsonParser.parseReader(inputStreamReader).getAsJsonObject();

            int id = jsonObject.get("id").getAsInt();
            String name = jsonObject.get("name").getAsString();
            JsonObject type1Json = jsonObject.getAsJsonArray("types").get(0).getAsJsonObject();
//            System.out.println(type1Json);

            String type1 = type1Json.get("type").getAsJsonObject().get("name").getAsString();
            String type2 = "";

            if (jsonObject.getAsJsonArray("types").size() > 1)
                type2 = jsonObject.getAsJsonArray("types").get(1).getAsJsonObject().get("type").getAsJsonObject().get("name").getAsString();

            return new Pokemon(id, name, type1, type2);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;

    }
}
