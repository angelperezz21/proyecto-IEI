package entidades;

public class Provincia {
    private int ID;
    private int codigo;
    private String nombre;

    public Provincia(int codigo, String nombre){
        this.codigo = codigo;
        this.nombre = nombre;
    }


    public int getID(){
        return this.ID;
    }

    public int getCodigo(){
        return this.codigo;
    }
    public String getNombre(){
        return this.nombre;
    }

    public void setID(int id){
        this.ID = id;
    }
}
