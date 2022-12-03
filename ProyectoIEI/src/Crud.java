import java.sql.*;
import entidades.Hospital;

import entidades.Localidad;
import entidades.Provincia;
public class Crud {

    private Connection conn = null;
	Statement sqlSt;

	public Crud() throws SQLException {
		try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            
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
    
        stmt.setString(1,hospital.getNombre());
        stmt.setString(2,hospital.getTipo());
        stmt.setString(3,hospital.getDireccion());
        stmt.setString(4,hospital.getCodigoPostal() + "");
        stmt.setString(5,hospital.getLongitud() + "");
        stmt.setString(6,hospital.getLatitud() + "");
        stmt.setString(7,hospital.getTelefono() + "");
        stmt.setString(8,hospital.getDescripcion());
        stmt.setString(9,hospital.getLocalidad() + "");
        stmt.setString(10,hospital.getProvincia() + "");
        
        stmt.executeUpdate();
        
        ResultSet rs = stmt.getGeneratedKeys();
        
        if (rs.next()) {
            hospital.setID(rs.getInt(1));
        }
        
    }


    public void createLocalidad(Localidad uLocalidad) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "insert into Customers (Codigo, Nombre) values (?,?)'",
                Statement.RETURN_GENERATED_KEYS);
    
        stmt.setString(1,uLocalidad.getCodigo()+"");
        stmt.setString(2,uLocalidad.getNombre());
    
        stmt.executeUpdate();
        
        ResultSet rs = stmt.getGeneratedKeys();
        
        if (rs.next()) {
            uLocalidad.setID(rs.getInt(1));
        }
        
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
