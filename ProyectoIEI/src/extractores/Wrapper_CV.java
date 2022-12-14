package extractores;

import java.io.*;

import org.checkerframework.checker.units.qual.C;
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
        //JsonWriter writer = Json.createWriter(jsonPath);
        JSONArray CentrosSalud = new JSONArray();
        String[] atributos = lectorCSV.nextLine().split(";");
        while (lectorCSV.hasNext()) {
            String[] centroSalud = lectorCSV.nextLine().split(";");
            JSONObject centroJSON = new JSONObject();
            for (int i = 0; i < atributos.length; i++) {
                centroJSON.put(atributos[i], centroSalud[i]);
            }
            //System.out.println(centroSalud);
            CentrosSalud.put(centroSalud);
        }
        System.out.println(CentrosSalud);
    }
}
