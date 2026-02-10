package excepciones.playlist;

public class CancionNoEncontradaException extends Exception {

    public CancionNoEncontradaException (){

    }
    public CancionNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
