package excepciones.recomendacion;

public class HistorialVacioException extends Exception {

    public HistorialVacioException (){

    }
    public HistorialVacioException(String mensaje) {
        super(mensaje);
    }
}
