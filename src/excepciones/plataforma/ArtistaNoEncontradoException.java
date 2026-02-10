package excepciones.plataforma;

public class ArtistaNoEncontradoException extends Exception {

    public ArtistaNoEncontradoException (){

    }
    public ArtistaNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
