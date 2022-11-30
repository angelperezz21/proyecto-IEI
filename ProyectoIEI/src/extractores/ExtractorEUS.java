package extractores;

import java.util.ArrayList;

import org.json.*;

import entidades.Hospital;
import entidades.Localidad;
import entidades.Provincia;

public class ExtractorEUS {
    JSONArray arrayJson;
    ArrayList<Hospital> arrayRespuesta = new ArrayList<Hospital>();
    
    private Hospital[] convertir(){
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

        for(int i = 0; i < hospitales.length; i++){
            JSONObject x = (JSONObject) arrayJson.get(i);

            String tipus = (String)x.get("Tipodecentro");
            if(tipus == "Hospital"){tipo = "Hospital";}
        }
        return hospitales;
    }


}
