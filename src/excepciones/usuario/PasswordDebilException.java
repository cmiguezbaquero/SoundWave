package excepciones.usuario;

public class PasswordDebilException extends Exception {

    public PasswordDebilException (){

    }
    public PasswordDebilException(String mensaje) {
        super(mensaje);
    }
}
