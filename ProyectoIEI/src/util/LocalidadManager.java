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

import entidades.Localidad;

public class LocalidadManager {
    private static LocalidadManager instance;

    public static LocalidadManager getInstance() {
        if (instance == null)
            instance = new LocalidadManager();
        return instance;
    }

    private HashMap<String, Localidad> towns;
    private int currentId;
    
    private String persistenceFilePath;

    private LocalidadManager() {
        this.towns = new HashMap<String, Localidad>();
        this.currentId = 0;

        loadPersistenceData();
    }

    /**
     * Devuelve un ID único para cada par (Provincia, Municipio)
     * 
     * @param nombreProvincia
     * @param localidad
     * @return ID asignada al municipio
     * @throws Exception
     */
    public Localidad crearLocalidad(String nombreProvincia, String nombreLocalidad) {
        // Si nos pasamos de 2^31 - 1 cagamos
        if (this.currentId == Integer.MAX_VALUE) {
            System.err.println("No caben más xd");
            System.exit(-1);
        }

        // Intentamos buscar la provincia+municipio
        String key = nombreProvincia + nombreLocalidad;
        Localidad localidad = this.towns.getOrDefault(key, null);

        // Si ya la tenemos, devolvemos su id
        if (localidad != null)
            return localidad;

        // Si no la tenemos, la ponemos y le asignamos una id
        localidad = new Localidad(currentId, nombreLocalidad);
        this.towns.put(key, localidad);

        this.currentId++;

        return localidad;
    }

    public void persistir(){
        try {
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.persistenceFilePath), "UTF-8"));
            JSONArray json = new JSONArray();
            JSONObject jsonObject;

            for (Map.Entry<String, Localidad> set : this.towns.entrySet()) {
                jsonObject = new JSONObject();
                jsonObject.put("key", set.getKey());
                jsonObject.put("codigo", set.getValue().getCodigo());
                jsonObject.put("nombre", set.getValue().getNombre());
                json.put(jsonObject);
            }

            writer.print(json.toString());

            writer.flush();
            writer.close();
        } 
        catch (FileNotFoundException e) {} 
        catch (UnsupportedEncodingException e) {}
    }

    public Localidad[] obtenerLocalidades(){
        Localidad[] localidades = new Localidad[this.towns.size()];

        int i = 0;
        for (Localidad localidad : this.towns.values()) {
            localidades[i] = localidad;
            i++;
        }

        return localidades;
    }

    private void loadPersistenceData(){
        this.persistenceFilePath = new File("").getAbsolutePath() + "/proyectoIEI/src/util/MunicipioManager.json";
        try {
            Scanner persistenceFileReader = new Scanner(new File(this.persistenceFilePath), "utf-8");
            String persistenceFileJson = "";
            while (persistenceFileReader.hasNext()) {
                persistenceFileJson += persistenceFileReader.nextLine();
            }
            JSONArray json = new JSONArray(persistenceFileJson);

            int jsonLength = json.length();
            JSONObject currentObject;
            int i;
            for(i = 0; i < jsonLength; i++){
                currentObject = json.getJSONObject(i);
                this.towns.put(
                    (String) currentObject.get("key"),
                    new Localidad(
                        (int) currentObject.get("codigo"),
                        (String) currentObject.get("nombre")
                    )
                );
            }
            this.currentId = i;

            persistenceFileReader.close();
        } catch (FileNotFoundException e) {} //Si no la encuentra no pasa nada, se crea de 0
    }

}