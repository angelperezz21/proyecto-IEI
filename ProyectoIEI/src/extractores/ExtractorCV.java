package extractores;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.json.*;

import entidades.Hospital;
import entidades.Localidad;
import entidades.Provincia;

public class ExtractorCV {
    private JSONArray json;

    public ExtractorCV(String json){
        try {
            this.json = new JSONArray(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Hospital[] convertir(){
        
        int nHospitales = this.json.length();

        Hospital[] hospitales = new Hospital[nHospitales];

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

        JSONObject jsonHospital;

        for(int i = 0; i < nHospitales; i++){
            jsonHospital = this.json.getJSONObject(i);

            //Nombre
            nombre = (String) jsonHospital.get("Tipus_centre / Tipo_centro");

            //Tipo
            String tipus = (String)jsonHospital.get("Centre / Centro");
            if(tipus_H.contains(tipus)){
                tipo = "Hospital";
            }else if(tipus_C.contains(tipus)){
                tipo = "Centro de salud";
            }else{
                tipo = "Otros";
            }

            //Dirección 
            direccion = (String) jsonHospital.get("Adreça / Dirección");

            
            //Código Postal
            String direccionBuscar = direccion + ", " +  (String) jsonHospital.get("Municipi / Municipio");

            

            //Longitud


            //Latutud


            //Telefono


            //Descripción


            //Localidad


            //Provincia



            




            Hospital hospital = new Hospital(nombre, tipo, direccion, codigoPostal, longitud, latitud, telefono, descripcion, localidad, provincia);
            hospitales[i] = hospital;
        }

        return hospitales;
        
    }
}
