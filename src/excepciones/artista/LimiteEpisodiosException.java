package excepciones.artista;

public class LimiteEpisodiosException extends Exception {

    public LimiteEpisodiosException (){

    }

    public LimiteEpisodiosException(String mensaje) {
        super(mensaje);
    }
}
