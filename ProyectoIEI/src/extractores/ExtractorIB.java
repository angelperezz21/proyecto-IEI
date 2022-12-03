package extractores;

import org.json.JSONArray;
import org.json.JSONObject;

import entidades.Hospital;
import entidades.Localidad;
import entidades.Provincia;
import scrappers.CoordenadasGPS;
import util.LocalidadManager;
import util.ProvinciaManager;

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

        CoordenadasGPS coordenadasGPS = CoordenadasGPS.getInstance();
        LocalidadManager localidadManager = LocalidadManager.getInstance();
        ProvinciaManager provinciaManager = ProvinciaManager.getInstance();

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
        Provincia provincia = provinciaManager.crearProvincia(07, "Islas Baleares");

        // Atributos IB
        String funcio;
        int localidadCodigo;
        String localidadNombre;

        JSONObject jsonHospital;

        for (int i = 0; i < nHospitales; i++) {
            jsonHospital = this.json.getJSONObject(i);

            // Nombre
            nombre = ((String) jsonHospital.get("nom")).replaceAll("'", "''");

            // Tipo
            funcio = (String) jsonHospital.get("funcio");
            if (funcio.equals("CENTRE SANITARI") || funcio.equals("UNITAT BÀSICA")) {
                tipo = "Centro de salud";
            } else {
                // CENTRE SANITARI PREVIST
                tipo = "Otros";
            }

            // Direccion
            direccion = ((String) jsonHospital.get("adreca")).replaceAll("'","''");

            // Latitud y longitud
            latitud = (double) jsonHospital.get("lat");
            longitud = (double) jsonHospital.get("long");

            // Codigo postal
            codigoPostal = extraerCPDeDireccion(coordenadasGPS.direccionDeCoordenadas(latitud, longitud));

            // Telefono (no hay)

            // Descripcion (no hay)

            // Localidad
            localidadNombre = ((String) jsonHospital.get("municipi")).replaceAll("'", "''");
            localidad = localidadManager.crearLocalidad("baleares", localidadNombre);

            // Provincia (creada fuera)
            

            hospitales[i] = new Hospital(nombre, tipo, direccion, codigoPostal, longitud, latitud, telefono, descripcion, localidad, provincia);
        }

        return hospitales;
    }

    private static int extraerCPDeDireccion(String direccion){
        String[] split = direccion.split(",");
        String[] cpCiudad = split[split.length - 2].trim().split(" ");

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
