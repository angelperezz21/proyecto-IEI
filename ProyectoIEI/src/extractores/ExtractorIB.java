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

        //Atributos
        String nombre;
        String tipo;
        String direccion = "";
        int codigoPostal = 0;
        double longitud = 0;
        double latitud = 0;
        int telefono = 0;
        String descripcion = "";
        Localidad localidad = null;
        Provincia provincia = null;

        //Atributos IB
        String funcio;

        JSONObject jsonHospital;

        for(int i = 0; i < nHospitales; i++){
            jsonHospital = this.json.getJSONObject(i);

            //Nombre
            nombre = (String)jsonHospital.get("nom");

            //Tipo
            funcio = (String)jsonHospital.get("funcio");
            if(funcio.equals("CENTRE SANITARI") || funcio.equals("UNITAT BÀSICA")){
                tipo = "Centro de salud";
            }
            else{
                //CENTRE SANITARI PREVIST
                tipo = "Otros";
            }

            //Direccion
            direccion = (String)jsonHospital.get("adreca");

            //Codigo postal
            //Hacer scrapper

            System.out.println(direccion);

            hospitales[i] = new Hospital(nombre, tipo, direccion, codigoPostal, longitud, latitud, telefono,descripcion, localidad, provincia);
        }

        return hospitales;
    }
}
