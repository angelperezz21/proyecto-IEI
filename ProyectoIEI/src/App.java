import java.io.File;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import entidades.Hospital;
import entidades.Localidad;
import extractores.ExtractorCV;
import extractores.ExtractorEUS;
import extractores.ExtractorIB;
import scrappers.CoordenadasGPS;
import util.Lector;
import util.MunicipioManager;

public class App {
    public static void main(String[] args) throws Exception {

        Crud crud = new Crud();
        
        String projectPath = new File("").getAbsolutePath();
        String pathCV = projectPath + "/ProyectoIEI/src/fuentedatos/fuente_CV.json";
        String pathEUS = projectPath + "/ProyectoIEI/src/fuentedatos/fuente_EUS.json";
        String pathIB = projectPath + "/ProyectoIEI/src/fuentedatos/fuente_IB.json";

        // ExtractorCV extractorCV = new ExtractorCV(Lector.leerFicheroDeTexto(pathCV));
        // ExtractorEUS extractorEUS = new ExtractorEUS(Lector.leerFicheroDeTexto(pathEUS));
        ExtractorIB extractorIB = new ExtractorIB(Lector.leerFicheroDeTexto(pathIB));

        // Hospital[] hospitalesCV = extractorCV.convertir();
        // Hospital[] hospitalesEUS = extractorEUS.convertir();
        Hospital[] hospitalesIB = extractorIB.convertir();

        MunicipioManager.getInstance().persistir();

        // for (int i = 0; i < hospitalesCV.length; i++) {
        //     try {
        //         crud.createLocalidad(hospitalesCV[i].getLocalidad());
        //         crud.createProvincia(hospitalesCV[i].getProvincia());
        //     } catch (SQLException e) {
        //     }

        //     crud.createHospital(hospitalesCV[i]);
        // }

        // for (int i = 0; i < hospitalesEUS.length; i++) {
        //     try {
        //         crud.createLocalidad(hospitalesEUS[i].getLocalidad());
        //         crud.createProvincia(hospitalesEUS[i].getProvincia());
        //     } catch (SQLException e) {
        //     }

        //     crud.createHospital(hospitalesEUS[i]);
        // }

        for(int i = 0; i < hospitalesIB.length; i++){
            try{
                crud.createLocalidad(hospitalesIB[i].getLocalidad());
                crud.createProvincia(hospitalesIB[i].getProvincia());
            }
            catch(SQLException e){} //Si ya existe, seguimos
            
            crud.createHospital(hospitalesIB[i]);
        }
    }
}