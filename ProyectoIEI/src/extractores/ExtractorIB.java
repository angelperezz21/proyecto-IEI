package extractores;

import org.json.JSONArray;
import org.json.JSONObject;

import Entidades.Hospital;
import Entidades.Localidad;
import Entidades.Provincia;

public class ExtractorIB {
    private JSONArray json;

    public ExtractorIB(String json){
        try{
            this.json = new JSONArray(json);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public Hospital[] convertir(){
        int nHospitales = this.json.length();

        Hospital[] hospitales = new Hospital[nHospitales];

        String nombre = "";
        String tipo = "";
        String direccion = "";
        int codigoPostal = 0;
        double longitud = 0;
        double latitud = 0;
        int telefono = 0;
        String descripcion = "";
        Localidad localidad = null;
        Provincia provincia = null;

        JSONObject jsonHospital;

        for(int i = 0; i < nHospitales; i++){
            jsonHospital = this.json.getJSONObject(i);

            nombre = (String)jsonHospital.get("nom");

            hospitales[i] = new Hospital(nombre, tipo, direccion, codigoPostal, longitud, latitud, telefono,descripcion, localidad, provincia);
        }

        return hospitales;
    }
}
