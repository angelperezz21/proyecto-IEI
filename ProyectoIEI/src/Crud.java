import java.sql.*;
import entidades.Hospital;

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

    public void createHospital(Hospital hospital ) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "insert into Hospital (Nombre,Tipo,Direccion,CodigoPostal,Lonigutd,Latitud,Telefono,Descripcion,Localidad,Provinicia) values (?,?,?,?,?,?,?,?,?,?)'",
                Statement.RETURN_GENERATED_KEYS);
    
       // stmt.setString(1,aCustomer.name);
       // stmt.setString(2,aCustomer.address);
       // stmt.setString(3,aCustomer.telephone);
       // stmt.setString(4,aCustomer.email);
    
        stmt.executeUpdate();
        
        ResultSet rs = stmt.getGeneratedKeys();
        
        if (rs.next()) {
            //aCustomer.ID = rs.getInt(1);
        }
        
    }
        
}
