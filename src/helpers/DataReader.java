package helpers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import model.Producte;
import model.Punt;
import model.Warehouse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class DataReader {

    public Warehouse readWarehouse(String path) {
        int[][] shelves;
        Gson gson = new Gson();
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(path));
            JsonObject unparsed = gson.fromJson(reader, JsonObject.class);

            int aux0 = unparsed.getAsJsonObject("dim").get("max_x").getAsInt();
            int aux1 = unparsed.getAsJsonObject("dim").get("max_y").getAsInt();
            shelves = new int[aux0][aux1];

            Punt entrance = new Punt();
            entrance.setY(unparsed.getAsJsonObject("entrance").get("y").getAsInt());
            entrance.setX(unparsed.getAsJsonObject("entrance").get("x").getAsInt());

            JsonArray shelvesConfig = unparsed.getAsJsonArray("shelves_config");
            HashMap<Integer, Integer> config = new HashMap<Integer, Integer>();
            for (int i = 0; i < shelvesConfig.size(); i++) {
                config.put(shelvesConfig.get(i).getAsJsonObject().get("id").getAsInt(),
                        shelvesConfig.get(i).getAsJsonObject().get("length").getAsInt());
            }

            JsonArray shelvesPosition = unparsed.getAsJsonArray("shelves");
            for (int i = 0; i < shelvesPosition.size(); i++) {
                JsonObject shelveInfo = shelvesPosition.get(i).getAsJsonObject();
                aux0 = shelveInfo.get("x_start").getAsInt();
                aux1 = shelveInfo.get("y_start").getAsInt();
                for (int j = 0; j < config.get(shelveInfo.get("config").getAsInt()); j++){
                    if (shelveInfo.get("orientation").getAsString().equals("H")) {
                        shelves[aux0 + j][aux1] = i + 1;
                    } else {
                        shelves[aux0][aux1 + j] = i + 1;
                    }
                }
            }

            return new Warehouse(shelves, entrance);

        } catch (FileNotFoundException e) {
            System.out.println("\r\nError, no s'ha trobat el fitxer del magatzem.\r\n");
        } catch (JsonSyntaxException e) {
            System.out.println("\r\nError, el format del fitxer no és correcte.\r\n");
        }

        return null;
    }

    public Producte[] readProducts(String path) {
        Producte[] productes;
        Gson gson = new Gson();
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(path));
            productes = gson.fromJson(reader, Producte[].class);
            return productes;
        } catch (FileNotFoundException e) {
            System.out.println("\r\nError, no s'ha trobat el fitxer de productes.\r\n");
        } catch (JsonSyntaxException e) {
            System.out.println("\r\nError, el format del fitxer no és correcte.\r\n");
        }
        return null;
    }

    public double[][] readGraph(String path, Producte[] productes) {

        double[][] graf = new double[productes.length][productes.length];

        try {
            BufferedReader bf = new BufferedReader(new FileReader(path));
            String line;
            while((line = bf.readLine()) != null) {
                String parts[] = line.split(" ");
                int id1 = Integer.parseInt(parts[0]);
                int id2 = Integer.parseInt(parts[1]);
                graf[Producte.getPositionFromID(id1, productes)][Producte.getPositionFromID(id2, productes)] = Double.parseDouble(parts[2]);
            }
        } catch (IOException e) {
            System.out.println("\r\nError, no s'ha trobat el fitxer del graf.\r\n");
        }

        return graf;
    }

}
