package extractores;

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
            if(tipus == "HOSPITALES DE MEDIA Y LARGA DISTANCIA"
            || tipus == "HOSPITALES DE SALUD MENTAL Y TRATAMIENTO DE TOXICOMANÍAS"
            || tipus == "HOSPITALES ESPECIALIZADOS"
            || tipus == "HOSPITALES GENERALES"){
                tipo = "Hospital";
            }

            Hospital hospital = new Hospital(nombre, tipo, direccion, codigoPostal, longitud, latitud, telefono, descripcion, localidad, provincia);
            hospitales[i] = hospital;
        }

        return hospitales;
        
    }
}
