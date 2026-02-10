package excepciones.usuario;

public class AnuncioRequeridoException extends Exception {
    public AnuncioRequeridoException (){

    }
    public AnuncioRequeridoException(String mensaje) {
        super(mensaje);
    }
}
