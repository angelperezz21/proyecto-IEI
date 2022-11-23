package extractores;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.json.*;

import Entidades.Hospital;
import Entidades.Localidad;
import Entidades.Provincia;

public class ExtractorCV {
    JSONArray arrayJson;

    public ExtractorCV(JSONArray arrayJson){

        this.arrayJson = arrayJson;
    }

    public Hospital[] convertir(){
        
        String[] tipus_Hospital = {"HOSPITALES DE MEDIA Y LARGA ESTANCIA", 
        "HOSPITALES DE SALUD MENTAL Y TRATAMIENTO DE TOXICOMANÍAS"
        ,"HOSPITALES ESPECIALIZADOS",
        "HOSPITALES GENERALES"};
        Set<String> tipus_H = new HashSet<>(Arrays.asList(tipus_Hospital));

        String[] tipus_Centro = {"CENTROS DE SALUD",
            "CENTROS DE SALUD MENTAL"};
        Set<String> tipus_C = new HashSet<>(Arrays.asList(tipus_Centro));

        
        String nombre = "";
        String tipo = "";
        String direccion = "";
        int codigoPostal = 000;
        double longitud = 0;
        double latitud = 0;
        int telefono = 0;
        String descripcion = "";
        Localidad localidad = null;
        Provincia provincia = null;

        Hospital hospitales[] = new Hospital[arrayJson.length()];

        for(int i = 0; i < arrayJson.length(); i++){
            JSONObject x = (JSONObject) arrayJson.get(i);

            String tipus = (String)x.get("Tipus_centre");
            if(tipus_H.contains(tipus)){
                tipo = "Hospital";
            }else if(tipus_C.contains(tipus)){
                tipo = "Centro de salud";
            }else{
                tipo = "Otros";
            }
            



            Hospital hospital = new Hospital(nombre, tipo, direccion, codigoPostal, longitud, latitud, telefono, descripcion, localidad, provincia);
            hospitales[i] = hospital;
        }

        return hospitales;
        
    }
}
