package excepciones.contenido;

public class ContenidoNoDisponibleException extends Exception {

    public ContenidoNoDisponibleException (){

    }
    public ContenidoNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
