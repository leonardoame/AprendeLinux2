package mx.edu.utng.aprendelinux.model;


public class Modulo {

    private int  idModulo;
    private String nombre;
    private int usuario;
    private boolean activo;
    private int calificacion;

    public Modulo() {
        idModulo=0;
        nombre="";
        usuario=0;
        activo=false;
        calificacion=0;
    }

    public Modulo(String nombre, int usuario, boolean activo, int calificacion) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.activo = activo;
        this.calificacion = calificacion;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public int getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(int idModulo) {
        this.idModulo = idModulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Modulo{" +
                "idModulo=" + idModulo +
                ", nombre='" + nombre + '\'' +
                ", usuario=" + usuario +
                ", activo=" + activo +
                ", calificacion=" + calificacion +
                '}';
    }
}
