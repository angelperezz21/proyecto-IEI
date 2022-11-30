package util;

import java.util.HashMap;

public class MunicipioManager {
    private static MunicipioManager instance;

    public static MunicipioManager getInstance() {
        if (instance == null)
            instance = new MunicipioManager();
        return instance;
    }

    private HashMap<String, Integer> towns;
    private int currentId;

    private MunicipioManager() {
        this.towns = new HashMap<String, Integer>();
        this.currentId = 0;
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
        // if(this.currentId == Integer.MAX_VALUE) throw new Exception("No caben más
        // xd");
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

}