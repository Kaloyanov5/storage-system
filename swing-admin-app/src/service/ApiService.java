package service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.FurnitureSwingModel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ApiService {
    private static final String API_URL = System.getenv("API_URL");
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String IMAGE_FOLDER = System.getenv("IMAGE_FOLDER");

    public static List<FurnitureSwingModel> getFurniture() {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200)
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());

            InputStream inputStream = conn.getInputStream();
            List<FurnitureSwingModel> furniture = objectMapper.readValue(inputStream,
                    new TypeReference<List<FurnitureSwingModel>>() {});
            conn.disconnect();
            return furniture;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void addFurniture(FurnitureSwingModel furnitureRequest, File imageFile) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInput = objectMapper.writeValueAsString(furnitureRequest);
            System.out.println(jsonInput);
            OutputStream os = conn.getOutputStream();
            os.write(jsonInput.getBytes());
            os.flush();
            os.close();

            if (conn.getResponseCode() != 200)
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());

            if (imageFile != null)
                Files.copy(imageFile.toPath(), Paths.get(IMAGE_FOLDER + imageFile.getName()));

            conn.disconnect();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void updateFurniture(FurnitureSwingModel furniture, File imageFile, String imageName) {
        try {
            URL url = new URL(API_URL + "/" + furniture.getId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInput = objectMapper.writeValueAsString(furniture);
            System.out.println(jsonInput);
            OutputStream os = conn.getOutputStream();
            os.write(jsonInput.getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();

            if (conn.getResponseCode() != 200)
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());

            if (!imageFile.getName().equals(imageName)) {
                Files.delete(Paths.get(IMAGE_FOLDER + imageName));
                Files.copy(imageFile.toPath(), Paths.get(IMAGE_FOLDER + imageFile.getName()));
            }

            conn.disconnect();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void deleteFurniture(Long id) {
        try {
            FurnitureSwingModel furnitureSwingModel = getFurnitureById(id);
            URL url = new URL(API_URL + "/" + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");

            if (conn.getResponseCode() != 200)
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());

            if (furnitureSwingModel.getImageName() != null)
                Files.delete(Paths.get(IMAGE_FOLDER + furnitureSwingModel.getImageName()));

            conn.disconnect();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static FurnitureSwingModel getFurnitureById(Long id) {
        try {
            URL url = new URL(API_URL + "/" + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200)
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());

            InputStream inputStream = conn.getInputStream();
            FurnitureSwingModel furniture = objectMapper.readValue(inputStream, FurnitureSwingModel.class);
            conn.disconnect();
            return furniture;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
