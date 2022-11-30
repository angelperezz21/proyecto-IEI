package extractores;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.json.JSONArray;
import org.json.JSONObject;

import entidades.Hospital;
import entidades.Localidad;
import entidades.Provincia;
import scrappers.CoordenadasGPS;
import util.MunicipioManager;

public class ExtractorIB {
    private JSONArray json;

    public ExtractorIB(String json) {
        try {
            this.json = new JSONArray(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Hospital[] convertir() {
        int nHospitales = this.json.length();

        Hospital[] hospitales = new Hospital[nHospitales];

        // Atributos globales
        String nombre;
        String tipo;
        String direccion;
        int codigoPostal;
        double longitud;
        double latitud;
        int telefono = 0xFFFFFFFF;
        String descripcion = null;
        Localidad localidad;
        Provincia provincia = new Provincia(07, "Islas Baleares");

        // Notas:
        // 1) Habra que hacer un gestor de localidades y provincias para que se guarden con id unica
        // Y asi en la tabla Hospital, estos atributos seran un FK a tablas Localidad y Provincia
        // y cumplen la tercera forma normal
        // 2) Gracias a eso podre implementar localidad.codigo

        // Atributos IB
        String funcio;
        int localidadCodigo;
        String localidadNombre;

        CoordenadasGPS coordenadasGPS = CoordenadasGPS.getInstance();
        MunicipioManager municipioManager = MunicipioManager.getInstance();

        JSONObject jsonHospital;

        for (int i = 0; i < nHospitales; i++) {
            jsonHospital = this.json.getJSONObject(i);

            // Nombre
            nombre = (String) jsonHospital.get("nom");

            // Tipo
            funcio = (String) jsonHospital.get("funcio");
            if (funcio.equals("CENTRE SANITARI") || funcio.equals("UNITAT BÀSICA")) {
                tipo = "Centro de salud";
            } else {
                // CENTRE SANITARI PREVIST
                tipo = "Otros";
            }

            // Direccion
            direccion = (String) jsonHospital.get("adreca");

            // Latitud y longitud
            latitud = (double) jsonHospital.get("lat");
            longitud = (double) jsonHospital.get("long");

            // Codigo postal
            codigoPostal = extraerCPDeDireccion(coordenadasGPS.direccionDeCoordenadas(latitud, longitud));

            // Telefono (no hay)

            // Descripcion (no hay)

            // Localidad
            localidadNombre = (String) jsonHospital.get("municipi");
            localidadCodigo = municipioManager.obtenerIdPara("baleares", localidadNombre);
            localidad = new Localidad(localidadCodigo, localidadNombre);

            // Provincia (creada fuera)
            

            hospitales[i] = new Hospital(nombre, tipo, direccion, codigoPostal, longitud, latitud, telefono, descripcion, localidad, provincia);
        }

        try (PrintWriter pw = new PrintWriter(new File("C:\\Users\\Vicent\\Desktop\\xd.txt"))) {
            for (int i = 0; i < hospitales.length; i++) {
                pw.println(hospitales[i].toString() + "\n");
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return hospitales;
    }

    private static int extraerCPDeDireccion(String direccion){
        String[] split = direccion.split(",");
        String[] cpCiudad = split[split.length - 2].trim().split(" ");
        System.out.println(direccion);

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
