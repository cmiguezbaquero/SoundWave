package excepciones.playlist;

public class PlaylistLlenaException extends Exception {

    public PlaylistLlenaException (){

    }
    public PlaylistLlenaException(String mensaje) {
        super(mensaje);
    }
}
