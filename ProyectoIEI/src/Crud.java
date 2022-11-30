import java.sql.*;
import entidades.Provincia;
public class Crud {

    private Connection conn = null;
	Statement sqlSt;

	public Crud() throws SQLException {
		try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String dbURL = "jdbc:mariadb://IEI-006-v0.dsicv.upv.es:3306";
        conn = DriverManager.getConnection(dbURL, "root", "");
        sqlSt = conn.createStatement(); //Permite a SQL ser ejecutado

        
	}

    public void createProvincia(Provincia provincia) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "insert into Provincia (Codigo, Nombre) values (?,?)'",
                Statement.RETURN_GENERATED_KEYS);
    
        stmt.setString(1, Integer.toString(provincia.getCodigo()));
        stmt.setString(2, provincia.getNombre());

    
        stmt.executeUpdate();
        
        ResultSet rs = stmt.getGeneratedKeys();
        
        if (rs.next()) {
            provincia.setID(rs.getInt(1));
        }
        
    }
}
