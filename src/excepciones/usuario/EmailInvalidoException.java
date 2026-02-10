package excepciones.usuario;

public class EmailInvalidoException extends Exception {
    public EmailInvalidoException() {
        super();
    }

    public EmailInvalidoException (String mensaje){
        super(mensaje);
    }
}
