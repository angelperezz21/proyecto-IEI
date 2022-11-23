package entidades;


public class Hospital {

    private String nombre;
    private String tipo;
    private String direccion;
    private int codigoPostal;
    private double longitud;
    private double latitud;
    private int telefono;
    private String descripcion;
    private Localidad localidad;
    private Provincia provincia;

    public Hospital(String nombre, String tipo, String direccion, int codigoPostal, double longitud, double latitud, int telefono,
                    String descripcion, Localidad localidad, Provincia provincia){
        this.nombre = nombre;
        this.tipo = tipo;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.longitud = longitud;
        this.latitud = latitud;
        this.telefono = telefono;
        this.descripcion = descripcion;
        this.localidad = localidad;
        this.provincia = provincia;
    }

}
