package excepciones.artista;

public class AlbumCompletoException extends Exception {
    public AlbumCompletoException (){

    }
    public AlbumCompletoException(String mensaje) {
        super(mensaje);
    }
}
