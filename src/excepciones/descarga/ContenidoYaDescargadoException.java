package excepciones.descarga;

public class ContenidoYaDescargadoException extends Exception {

    public ContenidoYaDescargadoException (){

    }
    public ContenidoYaDescargadoException(String mensaje) {
        super(mensaje);
    }
}
