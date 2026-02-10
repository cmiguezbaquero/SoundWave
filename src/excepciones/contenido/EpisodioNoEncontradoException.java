package excepciones.contenido;

public class EpisodioNoEncontradoException extends Exception {

    public EpisodioNoEncontradoException (){

    }
    public EpisodioNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
