package excepciones.contenido;

public class ArchivoAudioNoEncontradoException extends Exception {

    public  ArchivoAudioNoEncontradoException (){

    }
    public ArchivoAudioNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
