import java.sql.*;
import java.util.Locale;

import entidades.CentroSanitario;

import entidades.Localidad;
import entidades.Provincia;

public class Crud {
    private static Crud instance;

    public static Crud getInstance() throws SQLException {
        if (instance == null)
            instance = new Crud();
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

    public void createHospital(CentroSanitario[] hospitales) throws SQLException {
        // PreparedStatement stmt = conn.prepareStatement(
        //         "insert into hospital (Nombre,Tipo,Direccion,CodigoPostal,Longitud,Latitud,Telefono,Descripcion,Localidad,Provincia) values (?,?,?,?,?,?,?,?,?,?)",
        //         Statement.RETURN_GENERATED_KEYS);

        // stmt.setString(1, hospital.getNombre());
        // stmt.setString(2, hospital.getTipo());
        // stmt.setString(3, hospital.getDireccion());
        // stmt.setString(4, hospital.getCodigoPostal() + "");
        // stmt.setString(5, hospital.getLongitud() + "");
        // stmt.setString(6, hospital.getLatitud() + "");
        // stmt.setString(7, hospital.getTelefono() == -1 ? null : hospital.getTelefono() + "");
        // stmt.setString(8, hospital.getDescripcion());
        // stmt.setString(9, hospital.getLocalidad().getCodigo() + "");
        // stmt.setString(10, hospital.getProvincia().getCodigo() + "");

        // stmt.executeUpdate();

        // ResultSet rs = stmt.getGeneratedKeys();

        // if (rs.next()) {
        //     hospital.setID(rs.getInt(1));
        // }

        Statement stmt = conn.createStatement();

        CentroSanitario h;
        String codigoPostalString, latitudString, longitudString, telefonoString, descripcionString;
        int codigoPostal, telefono;
        double latitud, longitud;
        for(int i = 0; i < hospitales.length; i++){
            h = hospitales[i];

            codigoPostal = h.getCodigoPostal();
            codigoPostalString = codigoPostal == -1 ? null : "'"+Integer.toString(codigoPostal)+"'";

            longitud = h.getLongitud();
            longitudString = Double.isNaN(longitud) ? null : "'"+Double.toString(longitud)+"'";

            latitud = h.getLatitud();
            latitudString = Double.isNaN(latitud) ? null : "'"+Double.toString(latitud)+"'";

            telefono = h.getTelefono();
            telefonoString = telefono == -1 ? null : "'"+Integer.toString(telefono)+"'";

            descripcionString = h.getDescripcion();
            descripcionString = descripcionString == null ? null : "'"+descripcionString+"'";

            stmt.addBatch(
                    String.format(
                        Locale.ROOT,
                        "insert into centro_sanitario (ID, Nombre,Tipo,Direccion,CodigoPostal,Longitud,Latitud,Telefono,Descripcion,Localidad) values ('%d','%s','%s','%s',%s,%s,%s,%s,%s,'%d')",
                        h.getID(),
                        h.getNombre(),
                        h.getTipo(),
                        h.getDireccion(),
                        codigoPostalString,
                        longitudString,
                        latitudString,
                        telefonoString,
                        descripcionString,
                        h.getLocalidad().getCodigo()
                    )
            );
        }

        stmt.executeBatch();
    }

    public void createLocalidad(Localidad[] localidades) throws SQLException {
        // PreparedStatement stmt = conn.prepareStatement(
        //         "insert into localidad (Codigo, Nombre) values (?,?)",
        //         Statement.RETURN_GENERATED_KEYS);

        // stmt.setString(1, uLocalidad.getCodigo() + "");
        // stmt.setString(2, uLocalidad.getNombre());

        // stmt.executeUpdate();

        // ResultSet rs = stmt.getGeneratedKeys();

        // if (rs.next()) {
        // uLocalidad.setID(rs.getInt(1));
        // }
        Statement stmt = conn.createStatement();

        Localidad l;
        for(int i = 0; i < localidades.length; i++){
            l= localidades[i];
            stmt.addBatch(String.format("insert into localidad (Codigo, Nombre, Cod_provincia) values ('%d','%s','%d')", l.getCodigo(),l.getNombre(),l.getProvincia().getCodigo()));
        }

        stmt.executeBatch();
    }

    public void createProvincia(Provincia[] provincias) throws SQLException {
        // PreparedStatement stmt = conn.prepareStatement(
        //         "insert into provincia (Codigo, Nombre) values (?,?)",
        //         Statement.RETURN_GENERATED_KEYS);

        // stmt.setString(1, Integer.toString(provincia.getCodigo()));
        // stmt.setString(2, provincia.getNombre());

        // stmt.executeUpdate();

        // ResultSet rs = stmt.getGeneratedKeys();

        // if (rs.next()) {
        // provincia.setID(rs.getInt(1));
        // }

        Statement stmt = conn.createStatement();

        Provincia p;
        for(int i = 0; i < provincias.length; i++){
            p = provincias[i];
            stmt.addBatch(String.format("insert into provincia (Codigo, Nombre) values ('%d','%s')", p.getCodigo(),p.getNombre()));
        }

        stmt.executeBatch();
    }
}
