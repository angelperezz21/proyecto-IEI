package entidades;

public class Localidad {
    private int codigo;
    private String nombre;

    public Localidad(int codigo, String nombre){
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public String getNombre() {
        return this.nombre;
    }
}
