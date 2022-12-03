package extractores;

import java.util.ArrayList;

import org.json.*;

import entidades.Hospital;
import entidades.Localidad;
import entidades.Provincia;
import scrappers.CoordenadasGPS;

import util.MunicipioManager;

public class ExtractorEUS {
    private JSONArray json;

    public ExtractorEUS(String json){
        try {
            this.json = new JSONArray(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Hospital[] convertir(){
        int nHospitales = this.json.length();
        Hospital[] hospitales = new Hospital[nHospitales];

        //Atributos globales
        String nombre = "";
        String tipo = "";
        String direccion = "";
        int codigoPostal;
        double longitud;
        double latitud;
        int telefono = 0xFFFFFF;
        String descripcion = null;
        Localidad localidad;
        Provincia provincia = new Provincia(00 , "Euskadi");

        // Notas:
        // 1) Habra que hacer un gestor de localidades y provincias para que se guarden con id unica
        // Y asi en la tabla Hospital, estos atributos seran un FK a tablas Localidad y Provincia
        // y cumplen la tercera forma normal
        // 2) Gracias a eso podre implementar localidad.codigo

        // Atributos EUS
        String Tipodecentro;
        String horarioAtencionCiudadana;
        String horarioEspecial;
        int codLocalidad;
        String nombreLocalidad;
        int codProvincia;
        String nombreProvincia;
        MunicipioManager municipioManager = MunicipioManager.getInstance();

        JSONObject jsonHospital;

        for(int i = 0; i < nHospitales; i++){
            jsonHospital = this.json.getJSONObject(i);

            //Nombre
            nombre = (String) jsonHospital.get("Nombre");
            //Tipo
            Tipodecentro = (String) jsonHospital.get("Tipodecentro");
            if(Tipodecentro.equals("Hospital")){
                tipo = "Hospital";
            }else if(Tipodecentro.equals("Centro de Salud") 
                    || Tipodecentro.equals("Centro de Salud Mental")
                    || Tipodecentro.equals("Consultorio")
                    || Tipodecentro.equals("Ambulatorio")){
                        tipo = "Centro de salud";

            }else if(Tipodecentro.equals("Otros")){
                tipo = "Otros";
            }
            //Direccion
            direccion = (String) jsonHospital.get("Direccion");
            // Latitud y longitud
            latitud = (double) jsonHospital.get("LONWGS84");
            longitud = (double) jsonHospital.get("LATWGS84");
            //Codigo Postal
            codigoPostal = (int) jsonHospital.get("Codigopostal");
            //Telefono
            telefono = (int) jsonHospital.get("Telefono");
            //Descripcion
            horarioAtencionCiudadana = (String) jsonHospital.get("Horarioatencionciudadana");
            horarioEspecial = (String) jsonHospital.get("Horario especial");
            descripcion = horarioAtencionCiudadana + "\n" + horarioEspecial;
            //Localidad
            nombreLocalidad = (String) jsonHospital.get("Municipio");
            codLocalidad = municipioManager.obtenerIdPara("euskadi", nombreLocalidad);
            localidad = new Localidad(codLocalidad, nombreLocalidad);
            //Provincia
            codProvincia = ((int) jsonHospital.get("Codigopostal"))/1000;
            nombreProvincia = (String) jsonHospital.get("Provincia");
            provincia = new Provincia(codProvincia, nombreProvincia);

            hospitales[i] = new Hospital(nombre, tipo, direccion, codigoPostal, longitud, latitud, telefono, descripcion, localidad, provincia);
        }
        
        return hospitales;
    }
}
