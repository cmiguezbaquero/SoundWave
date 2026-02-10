package excepciones.contenido;

public class DuracionInvalidaException extends Exception {
    public DuracionInvalidaException (){

    }
    public DuracionInvalidaException(String mensaje) {
        super(mensaje);
    }
}
