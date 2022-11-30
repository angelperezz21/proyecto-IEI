import java.io.File;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import extractores.ExtractorIB;
import scrappers.CoordenadasGPS;
import util.Lector;

public class App {
    public static void main(String[] args) throws Exception {
        // CoordenadasGPS gps = CoordenadasGPS.getInstance();
        // System.out.println(gps.longlatcp("Calle del emparrado, 3 Mislata"));
        // System.out.println(gps.direccionDeCoordenadas(39.85494039, 3.12508175));
        // System.out.println(gps.direccionDeCoordenadas(39.4886571, 2.4810291700000002));

        // ExtractorIB extrib = new ExtractorIB(Lector.leerFicheroDeTexto("C:\\Users\\Vicent\\Documents\\UPV\\4o\\IEI\\practicas\\proyecto-IEI\\ProyectoIEI\\src\\fuentedatos\\fuente_ib.json"));
        ExtractorIB extrib = new ExtractorIB(Lector.leerFicheroDeTexto(
                new File("").getAbsolutePath() + "\\ProyectoIEI\\src\\fuentedatos\\fuente_ib.json"));

        extrib.convertir();

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