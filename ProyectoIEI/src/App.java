import java.io.File;


import entidades.Hospital;
import entidades.Localidad;
import entidades.Provincia;
import extractores.ExtractorCV;
import extractores.ExtractorEUS;
import extractores.ExtractorIB;
import scrappers.CoordenadasGPS;
import util.Lector;
import util.LocalidadManager;
import util.ProvinciaManager;

public class App {
    public static void main(String[] args) throws Exception {
        Crud crud = Crud.getInstance();
        LocalidadManager localidadManager = LocalidadManager.getInstance();
        ProvinciaManager provinciaManager = ProvinciaManager.getInstance();
        
        String projectPath = new File("").getAbsolutePath();
        String pathCV = projectPath + "/ProyectoIEI/src/fuentedatos/fuente_CV_small.json";
        String pathEUS = projectPath + "/ProyectoIEI/src/fuentedatos/fuente_EUS_small.json";
        String pathIB = projectPath + "/ProyectoIEI/src/fuentedatos/fuente_IB_small.json";

        CoordenadasGPS.getInstance();
        System.out.println();

        ExtractorCV extractorCV = new ExtractorCV(Lector.leerFicheroDeTexto(pathCV));
        ExtractorEUS extractorEUS = new ExtractorEUS(Lector.leerFicheroDeTexto(pathEUS));
        ExtractorIB extractorIB = new ExtractorIB(Lector.leerFicheroDeTexto(pathIB));

        //Extrae los datos de las fuentes
        System.out.println("Extrayendo Comunidad Valenciana");
        Hospital[] hospitalesCV = extractorCV.convertir();

        System.out.println("Extrayendo País Vasco");
        Hospital[] hospitalesEUS = extractorEUS.convertir();

        System.out.println("Extrayendo Islas Baleares");
        Hospital[] hospitalesIB = extractorIB.convertir();


        localidadManager.persistir();


        Localidad[] localidades = localidadManager.obtenerLocalidades();
        Provincia[] provincias = provinciaManager.obtenerProvincias();

        //Guarda localidades y provincias en la BD
        crud.createLocalidad(localidades);
        crud.createProvincia(provincias);

        //Guarda los hospitales en la BD
        System.out.println("Guardando Comunidad Valenciana en BD");
        crud.createHospital(hospitalesCV);

        System.out.println("Guardando País Vasco en BD");
        crud.createHospital(hospitalesEUS);

        System.out.println("Guardando Islas Baleares en BD");
        crud.createHospital(hospitalesIB);
    }
}