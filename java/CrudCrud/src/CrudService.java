import com.google.gson.Gson;  // Import GSON library to convert Java objects to JSON and vice versa
import com.google.gson.reflect.TypeToken; // TypeToken is used to handle generic types during deserialization

import java.io.*;  // This is for InputStream, OutputStream, and other I/O classes
import java.lang.reflect.Type; // Used to handle complex generic types in Gson
import java.net.HttpURLConnection; // This class is used to manage HTTP connections
import java.net.URL;  // URL class is used to represent and manage the API endpoints
import java.util.*;
import java.text.MessageFormat;


public class CrudService {
    private static final String API_KEY = "9c66a62f21974c3886694a584d91b0c9";
    private static final String BASE_URL = MessageFormat.format("https://crudcrud.com/api/{0}/products", API_KEY);
    private final Gson gson = new Gson();


    // Method to create a new product (using the POST HTTP method)
    public void createProduct(Product product) throws IOException {
        URL url = new URL(BASE_URL);

        // Open an HTTP connection to the URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set the request method to POST, because we are creating a new resource
        connection.setRequestMethod("POST");

        // Set the "Content-Type" header to "application/json" since we are sending JSON data
        connection.setRequestProperty("Content-Type", "application/json");

        // We need to enable output because we are sending data (JSON)
        connection.setDoOutput(true);

        // Convert the Product object into JSON format using Gson
        String jsonProduct = gson.toJson(product);

        // Write the JSON data to the request's body (using an OutputStream)
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonProduct.getBytes("utf-8"); // Convert JSON string to bytes
            os.write(input, 0, input.length); // Write the bytes to the output stream
        }

        int responseCode = connection.getResponseCode();
        System.out.println("POST Response Code: " + responseCode);

        connection.disconnect();
    }

    // Method to retrieve all products (using the GET HTTP method)
    public List<Product> getAllProducts() throws IOException {
        URL url = new URL(BASE_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        int responseCode = connection.getResponseCode();
        System.out.println("PUT Response Code: " + responseCode);

        if (hasConnectionFailed(responseCode)) {
            connection.disconnect();
            System.out.println("\n'Connection failed!'\n");
            return Collections.emptyList();
        }

        // Set the request method to GET, because we are retrieving data
        connection.setRequestMethod("GET");

        // Create a BufferedReader to read the response from the server
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        // Read the response line by line and append it to a StringBuilder
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        // Close the BufferedReader
        in.close();

        // We now have the full response as a JSON string in the content variable.
        // We need to convert this JSON string into a List of Product objects.

        Type productListType = new TypeToken<ArrayList<Product>>() {}.getType();

        // Convert JSON response into a List<Product> using Gson
        List<Product> productList = gson.fromJson(content.toString(), productListType);

        // Close the HTTP connection
        connection.disconnect();

        // Return the list of products
        return productList;
    }

    // Method to update an existing product (using the PUT HTTP method)
    public void updateProduct(Product product) throws IOException {
        // 1. Create a URL object with the BASE_URL + product ID (this identifies which product to update)
        URL url = new URL(BASE_URL + "/" + product.getId());

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        int responseCode = connection.getResponseCode();
        System.out.println("PUT Response Code: " + responseCode);

        if (hasConnectionFailed(responseCode)) {
            connection.disconnect();
            System.out.println("Connection failed\n");
            return;
        }

        // Set the request method to PUT, because we are updating an existing resource
        connection.setRequestMethod("PUT");

        // Set the "Content-Type" header to "application/json" since we are sending JSON data
        connection.setRequestProperty("Content-Type", "application/json");

        // Enable output because we are sending data (JSON)
        connection.setDoOutput(true);

        // Convert the Product object into JSON format using Gson
        String jsonProduct = gson.toJson(product);

        // Write the JSON data to the request's body (using an OutputStream)
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonProduct.getBytes("utf-8"); // Convert JSON string to bytes
            os.write(input, 0, input.length); // Write the bytes to the output stream
        }

        connection.disconnect();
    }

    // Method to delete a product (using the DELETE HTTP method)
    public void deleteProduct(String productId) throws IOException {
        // 1. Create a URL object with the BASE_URL + product ID (this identifies which product to delete)
        URL url = new URL(BASE_URL + "/" + productId);

        // 2. Open an HTTP connection to the URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // 3. Get the response code (e.g., 204 for success, 404 for not found, etc.)
        int responseCode = connection.getResponseCode();
        System.out.println("DELETE Response Code: " + responseCode);

        if (hasConnectionFailed(responseCode)) {
            connection.disconnect();
            System.out.println("Connection failed\n");
            return;
        }

        // 4. Set the request method to DELETE, because we are removing a resource
        connection.setRequestMethod("DELETE");

        // 5. Close the connection
        connection.disconnect();
    }

    private boolean hasConnectionFailed(int responseCode) {
        if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST)
            return true;

        return false;
    }
}
