package entidades;

public class Localidad {
    private int ID;
    private int codigo;
    private String nombre;

    public int getID() {
        return ID;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

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
