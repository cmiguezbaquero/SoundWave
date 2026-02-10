package excepciones.contenido;

public class TranscripcionNoDisponibleException extends Exception {

    public TranscripcionNoDisponibleException (){

    }
    public TranscripcionNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
