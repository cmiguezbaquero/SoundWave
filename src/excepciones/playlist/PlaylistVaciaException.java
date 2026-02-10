package excepciones.playlist;

public class PlaylistVaciaException extends Exception {

    public PlaylistVaciaException (){

    }
    public PlaylistVaciaException(String mensaje) {
        super(mensaje);
    }
}
