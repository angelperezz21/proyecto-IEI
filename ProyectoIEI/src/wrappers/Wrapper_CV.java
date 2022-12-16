package wrappers;

import java.io.*;

import org.json.*;
import java.util.*;

public class Wrapper_CV {

    private Scanner lectorCSV;

    public Wrapper_CV(String fuentePath) {
        File fuente = new File(fuentePath);
        try {
            lectorCSV = new Scanner(fuente);
        } catch (FileNotFoundException e) {

        }
    }

    public void getJSON(String jsonPath) {
        if (lectorCSV == null) return;
        File jsonFile = new File(jsonPath);
        try {
            PrintWriter writer = new PrintWriter(jsonFile);
            //JsonWriter writer = Json.createWriter(jsonPath);
            //JSONObject lista = new JSONObject();
            JSONArray CentrosSalud = new JSONArray();
            String[] atributos = lectorCSV.nextLine().split(";");
            while (lectorCSV.hasNext()) {
                String[] centroSalud = lectorCSV.nextLine().split(";");
                JSONObject centroJSON = new JSONObject();
                for (int i = 0; i < atributos.length; i++) {
                    centroJSON.put(atributos[i], centroSalud[i]);
                }
                //System.out.println(centroSalud);
                CentrosSalud.put(centroJSON);
            }
            //lista.put("Centros", CentrosSalud);
            writer.println(CentrosSalud.toString());
            writer.close();
        } catch (FileNotFoundException e) {
            return;
        }
    }
}
