package extractores;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.json.*;

import entidades.Hospital;
import entidades.Localidad;
import entidades.Provincia;
import scrappers.CoordenadasGPS;
import util.LocalidadManager;
import util.ProvinciaManager;

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

        LocalidadManager localidadManager = LocalidadManager.getInstance();
        ProvinciaManager provinciaManager = ProvinciaManager.getInstance();

        CoordenadasGPS coordenadasGPS = CoordenadasGPS.getInstance();

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
        int codigoPostal = -1;
        double longitud = 0;
        double latitud = 0;
        int telefono = -1;
        String descripcion = null;
        Localidad localidad = null;
        Provincia provincia = null;

        JSONObject jsonHospital;

        String array[];

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

            array = coordenadasGPS.longlatcp(direccionBuscar);

            codigoPostal = extraerCPDeDireccion(array[2]);

            //Longitud
            try{
                longitud = Double.parseDouble(array[1]);
            }
            catch(NullPointerException e){
                longitud = Double.NaN;
            }
            

            //Latutud
            try{
                latitud = Double.parseDouble(array[0]);
            }
            catch(NullPointerException e){
                latitud = Double.NaN;
            }
            

            //Telefono(no hay)

            //Descripción(no hay)

            // Provincia
            //provincia = new Provincia((int) jsonHospital.get("Codi_província / Código_provincia"),(String) jsonHospital.get("Província / Provincia"));
            provincia = provinciaManager.crearProvincia(
                (int) jsonHospital.get("Codi_província / Código_provincia"),
                (String) jsonHospital.get("Província / Provincia")
            );

            //Localidad
            //localidad = new Localidad((int) jsonHospital.get("Codi_municipi / Código_municipio"), (String) jsonHospital.get("Municipi / Municipio"));
            localidad = localidadManager.crearLocalidad(provincia.getNombre(), ((String) jsonHospital.get("Municipi / Municipio")).replaceAll("'", "''"));

            Hospital hospital = new Hospital(nombre.replaceAll("'","''"), tipo, direccion.replaceAll("'",
                    "''"), codigoPostal, longitud, latitud, telefono, descripcion, localidad, provincia);
            hospitales[i] = hospital;
        }

        return hospitales;
    
    }


    private static int extraerCPDeDireccion(String direccion){
        if(direccion == null) return -1;
        String[] split = direccion.split(",");
        String[] cpCiudad = split[split.length - 2].trim().split(" ");
        //System.out.println(direccion);

        int res;
        try{
            res = Integer.parseInt(cpCiudad[0]);

        }
        catch(NumberFormatException e){
            res = -1;
        }
        return res;
    }
}
