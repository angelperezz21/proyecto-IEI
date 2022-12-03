import java.io.File;
import java.sql.*;


import entidades.Hospital;
import extractores.ExtractorCV;
import extractores.ExtractorEUS;
import extractores.ExtractorIB;
import scrappers.CoordenadasGPS;
import util.Lector;
import util.MunicipioManager;

public class App {
    public static void main(String[] args) throws Exception {
        Crud crud = Crud.getInstance();
        
        String projectPath = new File("").getAbsolutePath();
        String pathCV = projectPath + "/ProyectoIEI/src/fuentedatos/fuente_CV.json";
        String pathEUS = projectPath + "/ProyectoIEI/src/fuentedatos/fuente_EUS.json";
        String pathIB = projectPath + "/ProyectoIEI/src/fuentedatos/fuente_IB.json";

        CoordenadasGPS.getInstance();
        System.out.println();

        // ExtractorCV extractorCV = new ExtractorCV(Lector.leerFicheroDeTexto(pathCV));
        ExtractorEUS extractorEUS = new ExtractorEUS(Lector.leerFicheroDeTexto(pathEUS));
        ExtractorIB extractorIB = new ExtractorIB(Lector.leerFicheroDeTexto(pathIB));

        System.out.println("Extrayendo Comunidad Valenciana");
        // Hospital[] hospitalesCV = extractorCV.convertir();

        System.out.println("Extrayendo País Vasco");
        Hospital[] hospitalesEUS = extractorEUS.convertir();

        System.out.println("Extrayendo Islas Baleares");
        Hospital[] hospitalesIB = extractorIB.convertir();

        MunicipioManager.getInstance().persistir();

        System.out.println("Guardando Comunidad Valenciana en BD");
        // for (int i = 0; i < hospitalesCV.length; i++) {
        //     try {
        //         crud.createLocalidad(hospitalesCV[i].getLocalidad());
        //         crud.createProvincia(hospitalesCV[i].getProvincia());
        //     } catch (SQLException e) {
        //     }

        //     crud.createHospital(hospitalesCV[i]);
        // }

        System.out.println("Guardando País Vasco en BD");
        for (int i = 0; i < hospitalesEUS.length; i++) {
            try {
                crud.createLocalidad(hospitalesEUS[i].getLocalidad());
                crud.createProvincia(hospitalesEUS[i].getProvincia());
            } catch (SQLException e) {
            }

            crud.createHospital(hospitalesEUS[i]);
        }

        System.out.println("Guardando Islas Baleares en BD");
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