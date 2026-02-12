package excepciones.recomendacion;

public class HistorialVacioException extends RecomendacionException {

    public HistorialVacioException (){

    }
    public HistorialVacioException(String mensaje) {
        super(mensaje);
    }
}
