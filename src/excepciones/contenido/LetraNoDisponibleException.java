package excepciones.contenido;

public class LetraNoDisponibleException extends Exception {
    public LetraNoDisponibleException (){

    }
    public LetraNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
