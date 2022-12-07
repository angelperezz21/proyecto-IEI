import java.io.File;


import entidades.CentroSanitario;
import entidades.Localidad;
import entidades.Provincia;
import extractores.ExtractorCV;
import extractores.ExtractorEUS;
import extractores.ExtractorIB;
import scrappers.CoordenadasGPS;
import util.CentroSanitarioManager;
import util.Lector;
import util.LocalidadManager;
import util.ProvinciaManager;

public class App {
    public static void main(String[] args) throws Exception {
        Crud crud = Crud.getInstance();
        CentroSanitarioManager centroSanitarioManager = CentroSanitarioManager.getInstance();
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
        CentroSanitario[] hospitalesCV = extractorCV.convertir();

        System.out.println("Extrayendo País Vasco");
        CentroSanitario[] hospitalesEUS = extractorEUS.convertir();

        System.out.println("Extrayendo Islas Baleares");
        CentroSanitario[] hospitalesIB = extractorIB.convertir();


        //localidadManager.persistir();

        CentroSanitario[] centrosSanitarios = centroSanitarioManager.obtenerCentrosSanitarios();
        Localidad[] localidades = localidadManager.obtenerLocalidades();
        Provincia[] provincias = provinciaManager.obtenerProvincias();

        //Guarda todo en la BD
        System.out.println("Guardando provincias en BD");
        crud.createProvincia(provincias);

        System.out.println("Guardando localidades en BD");
        crud.createLocalidad(localidades);

        System.out.println("Guardando centros sanitarios en BD");
        crud.createHospital(centrosSanitarios);
    }
}