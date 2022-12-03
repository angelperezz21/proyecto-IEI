import java.sql.*;
import entidades.Hospital;

import entidades.Localidad;
import entidades.Provincia;
public class Crud {
    private static Crud instance;

    public static Crud getInstance() throws SQLException {
        if(instance == null)instance = new Crud();
        return instance;
    }

    private Connection conn = null;
	Statement sqlSt;

	private Crud() throws SQLException {
		try {
            Class.forName("org.mariadb.jdbc.Driver");

            String dbURL = "jdbc:mariadb://IEI-006-v0.dsicv.upv.es:3306";

            conn = DriverManager.getConnection(dbURL, "root", "");
            sqlSt = conn.createStatement(); // Permite a SQL ser ejecutado
            sqlSt.executeQuery("use test");
            
        } catch (ClassNotFoundException e) {
            
            e.printStackTrace();
        }
        
        
	}

    public void createHospital(Hospital hospital ) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "insert into hospital (Nombre,Tipo,Direccion,CodigoPostal,Longitud,Latitud,Telefono,Descripcion,Localidad,Provincia) values (?,?,?,?,?,?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS);
    
        stmt.setString(1,hospital.getNombre());
        stmt.setString(2,hospital.getTipo());
        stmt.setString(3,hospital.getDireccion());
        stmt.setString(4,hospital.getCodigoPostal() + "");
        stmt.setString(5,hospital.getLongitud() + "");
        stmt.setString(6,hospital.getLatitud() + "");
        stmt.setString(7,hospital.getTelefono()==-1?null:hospital.getTelefono() + "");
        stmt.setString(8,hospital.getDescripcion());
        stmt.setString(9,hospital.getLocalidad().getCodigo() + "");
        stmt.setString(10,hospital.getProvincia().getCodigo() + "");
        
        stmt.executeUpdate();
        
        ResultSet rs = stmt.getGeneratedKeys();
        
        if (rs.next()) {
            hospital.setID(rs.getInt(1));
        }
        
    }


    public void createLocalidad(Localidad uLocalidad) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "insert into localidad (Codigo, Nombre) values (?,?)",
                Statement.RETURN_GENERATED_KEYS);
    
        stmt.setString(1,uLocalidad.getCodigo()+"");
        stmt.setString(2,uLocalidad.getNombre());
    
        stmt.executeUpdate();
        
        ResultSet rs = stmt.getGeneratedKeys();
        
        // if (rs.next()) {
        //     uLocalidad.setID(rs.getInt(1));
        // }
        
    }


    public void createProvincia(Provincia provincia) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "insert into provincia (Codigo, Nombre) values (?,?)",
                Statement.RETURN_GENERATED_KEYS);
    
        stmt.setString(1, Integer.toString(provincia.getCodigo()));
        stmt.setString(2, provincia.getNombre());

    
        stmt.executeUpdate();
        
        ResultSet rs = stmt.getGeneratedKeys();
        
        // if (rs.next()) {
        //     provincia.setID(rs.getInt(1));
        // }
        
    }
}
