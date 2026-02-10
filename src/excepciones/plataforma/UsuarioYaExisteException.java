package excepciones.plataforma;

public class UsuarioYaExisteException extends Exception {
    public UsuarioYaExisteException (){
    }
    public UsuarioYaExisteException(String mensaje) {
        super(mensaje);
    }
}
