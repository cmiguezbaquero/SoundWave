package excepciones.playlist;

public class ContenidoDuplicadoException extends Exception {

    public ContenidoDuplicadoException (){

    }
    public ContenidoDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
