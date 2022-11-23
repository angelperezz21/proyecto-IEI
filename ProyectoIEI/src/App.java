import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("BIENVENIDO LIONEL");

        Statement sqlSt;
        String useSQL = new String("EXTRACCIÓN");
        String output;
        ResultSet result;
        
        try{
            Class.forName("org.mariadb.jdbc.Driver");
            String dbURL = "jdbc:mariadb://IEI-006-v0.dsicv.upv.es:3306";
            Connection dbConnect = DriverManager.getConnection(dbURL, "root", "");
            sqlSt = dbConnect.createStatement(); //Permite a SQL ser ejecutado
            
            sqlSt.executeQuery("use test");
            result = sqlSt.executeQuery("select * from prueba");
            
            while(result.next()!=false){
                output = result.getString("numero");
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