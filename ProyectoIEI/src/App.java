import java.io.File;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import extractores.ExtractorCV;
import extractores.ExtractorEUS;
import extractores.ExtractorIB;
import scrappers.CoordenadasGPS;
import util.Lector;
import util.MunicipioManager;

public class App {
    public static void main(String[] args) throws Exception {
        String projectPath = new File("").getAbsolutePath();
        String pathCV = projectPath + "\\ProyectoIEI\\src\\fuentedatos\\fuente_CV.json";
        String pathEUS = projectPath + "\\ProyectoIEI\\src\\fuentedatos\\fuente_EUS.json";
        String pathIB = projectPath + "\\ProyectoIEI\\src\\fuentedatos\\fuente_IB.json";

        ExtractorCV extractorCV = new ExtractorCV(Lector.leerFicheroDeTexto(pathCV));
        ExtractorEUS extractorEUS = new ExtractorEUS(Lector.leerFicheroDeTexto(pathEUS));
        ExtractorIB extractorIB = new ExtractorIB(Lector.leerFicheroDeTexto(pathIB));

        //MunicipioManager.getInstance().persistir();

        System.exit(0);

        System.out.println("BIENVENIDO LIONEL");

        Statement sqlSt;
        String useSQL = new String("EXTRACCIÓN");
        String output;
        ResultSet resultPrueba;
        ResultSet resultTabla;
        
        try{
            Class.forName("org.mariadb.jdbc.Driver");
            String dbURL = "jdbc:mariadb://IEI-006-v0.dsicv.upv.es:3306";
            Connection dbConnect = DriverManager.getConnection(dbURL, "root", "");
            sqlSt = dbConnect.createStatement(); //Permite a SQL ser ejecutado
            
            sqlSt.executeQuery("use test");
            resultPrueba = sqlSt.executeQuery("select * from prueba");
            resultTabla = sqlSt.executeQuery("select * from valencia");
            
            while(resultPrueba.next()!=false){
                output = resultPrueba.getString("numero");
                System.out.println(output);
            }
            while(resultTabla.next()!=false){
                output = resultTabla.getString("jugador");
                System.out.println(output);
            }
            //System.out.println(result.getString(dbURL));

        }
        catch(SQLException ex){
            Logger.getLogger(App.class.getName()).log(Level.SEVERE,null,ex);
            System.out.println("SQL IS BAD!!" + ex.getMessage());

        }
    }
}