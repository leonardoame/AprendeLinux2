package mx.edu.utng.aprendelinux.model;


public class Tema {

    private int  idTema;
    private String nombre;
    private int modulo;
    private boolean activo;

    public Tema() {
        idTema=0;
        nombre="";
        modulo=0;
        activo=false;
    }

    public Tema( String nombre, int modulo, boolean activo) {
        this.nombre = nombre;
        this.modulo = modulo;
        this.activo = activo;
    }

    public int getIdTema() {
        return idTema;
    }

    public void setIdTema(int idTema) {
        this.idTema = idTema;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getModulo() {
        return modulo;
    }

    public void setModulo(int modulo) {
        this.modulo = modulo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Tema{" +
                "idTema=" + idTema +
                ", nombre='" + nombre + '\'' +
                ", usuario=" + modulo +
                ", activo=" + activo +
                '}';
    }
}
