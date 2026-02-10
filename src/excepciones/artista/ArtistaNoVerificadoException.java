package excepciones.artista;

public class ArtistaNoVerificadoException extends Exception {
    public ArtistaNoVerificadoException (){

    }
    public ArtistaNoVerificadoException(String mensaje) {
        super(mensaje);
    }
}
