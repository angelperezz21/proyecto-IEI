package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class MunicipioManager {
    private static MunicipioManager instance;

    public static MunicipioManager getInstance() {
        if (instance == null)
            instance = new MunicipioManager();
        return instance;
    }

    private HashMap<String, Integer> towns;
    private int currentId;
    
    private String persistenceFilePath;

    private MunicipioManager() {
        this.towns = new HashMap<String, Integer>();
        this.currentId = 0;

        loadPersistenceData();
    }

    /**
     * Devuelve un ID único para cada par (Provincia, Municipio)
     * 
     * @param provincia
     * @param municipio
     * @return ID asignada al municipio
     * @throws Exception
     */
    public int obtenerIdPara(String provincia, String municipio) {
        // Si nos pasamos de 2^31 - 1 cagamos
        // if(this.currentId == Integer.MAX_VALUE) throw new Exception("No caben más xd");
        if (this.currentId == Integer.MAX_VALUE) {
            System.err.println("No caben más xd");
            System.exit(-1);
        }

        // Intentamos buscar la provincia+municipio
        String key = provincia + municipio;
        int id = this.towns.getOrDefault(key, -1);

        // Si ya la tenemos, devolvemos su id
        if (id != -1)
            return id;

        // Si no la tenemos, la ponemos y le asignamos una id
        this.towns.put(key, this.currentId);

        // Auxiliar para poder incrementar currentId
        id = this.currentId;

        this.currentId++;

        return id;
    }

    public void persistir(){
        try {
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.persistenceFilePath), "UTF-8"));
            JSONArray json = new JSONArray();
            JSONObject jsonObject;

            for (Map.Entry<String, Integer> set : this.towns.entrySet()) {
                jsonObject = new JSONObject();
                jsonObject.put("key", set.getKey());
                jsonObject.put("idValue", set.getValue());
                json.put(jsonObject);
            }

            writer.print(json.toString());

            writer.flush();
            writer.close();
        } 
        catch (FileNotFoundException e) {} 
        catch (UnsupportedEncodingException e) {}
    }

    private void loadPersistenceData(){
        this.persistenceFilePath = new File("").getAbsolutePath() + "\\proyectoIEI\\src\\util\\MunicipioManager.json";
        try {
            Scanner persistenceFileReader = new Scanner(new File(this.persistenceFilePath), "utf-8");
            String persistenceFileJson = "";
            while (persistenceFileReader.hasNext()) {
                persistenceFileJson += persistenceFileReader.nextLine();
            }
            JSONArray json = new JSONArray(persistenceFileJson);

            int jsonLength = json.length();
            JSONObject currentObject;
            for(int i = 0; i < jsonLength; i++){
                currentObject = json.getJSONObject(i);
                this.towns.put(
                    (String) currentObject.get("key"),
                    (int) currentObject.get("idValue")
                );
            }

            persistenceFileReader.close();
        } catch (FileNotFoundException e) {} //Si no la encuentra no pasa nada, se crea de 0
    }

}