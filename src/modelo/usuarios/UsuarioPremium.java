package modelo.usuarios;

import enums.TipoSuscripcion;
import excepciones.contenido.ContenidoNoDisponibleException;
import excepciones.descarga.ContenidoYaDescargadoException;
import excepciones.descarga.LimiteDescargasException;
import excepciones.usuario.AnuncioRequeridoException;
import excepciones.usuario.EmailInvalidoException;
import excepciones.usuario.LimiteDiarioAlcanzadoException;
import excepciones.usuario.PasswordDebilException;
import modelo.contenido.Contenido;

import java.util.ArrayList;

public class UsuarioPremium extends Usuario {

    private boolean descargasOffline;
    private int maxDescargas;
    private ArrayList<Contenido> descargados;
    private String calidadAudio;

    private static final int MAX_DESCARGAS_DEFAULT = 100;

    public UsuarioPremium(String nombre, String email, String password)
            throws EmailInvalidoException, PasswordDebilException {
        super(nombre, email, password, TipoSuscripcion.PREMIUM);
        inicializar();
    }

    public UsuarioPremium (String nombre, String email, String password, TipoSuscripcion suscripcion)
            throws EmailInvalidoException, PasswordDebilException{
        super(nombre,email,password,suscripcion);
        inicializar();
    }

    private void inicializar() {
        this.descargasOffline = true;
        this.maxDescargas = MAX_DESCARGAS_DEFAULT;
        this.descargados = new ArrayList<>();
        this.calidadAudio = "ALTA";
    }

    @Override
    public void validarPassword() throws PasswordDebilException {
        if (password == null || password.length() < 8){
            throw new PasswordDebilException("Password débil");
        }
    }

    public boolean isDescargasOffline() {
        return descargasOffline;
    }

    public void setDescargasOffline(boolean descargasOffline) {
        this.descargasOffline = descargasOffline;
    }

    public int getMaxDescargas() {
        return maxDescargas;
    }

    public ArrayList<Contenido> getDescargados() {
        return descargados;
    }

    public int getNumDescargados (){
        return descargados.size();
    }

    public String getCalidadAudio() {
        return calidadAudio;
    }

    public void setCalidadAudio(String calidadAudio) {
        this.calidadAudio = calidadAudio;
    }


    public void reproducir (Contenido contenido)
            throws ContenidoNoDisponibleException, LimiteDiarioAlcanzadoException, AnuncioRequeridoException {

        if (contenido == null || !contenido.isDisponible()){
            throw new ContenidoNoDisponibleException();
        }

        contenido.aumentarReproduciones();
        agregarAlHistorial(contenido);
    }

    public void descargar (Contenido contenido)
            throws LimiteDescargasException, ContenidoYaDescargadoException {
        if (!verificarEspacioDescarga()) {
            throw new LimiteDescargasException();
        }

        if (descargados.contains(contenido)) {
            throw new ContenidoYaDescargadoException();
        }

        descargados.add(contenido);
    }

    public boolean eliminarDescarga (Contenido contenido){

        return descargados.remove(contenido);
    }

    public boolean verificarEspacioDescarga (){

        return descargados.size() < maxDescargas;
    }

    public  int getDescargasRestantes (){

        return maxDescargas - descargados.size();
    }

    public void  cambiarCalidadAudio (String calidad){

        if (calidad == null) return;
        switch (calidad.toUpperCase()){
            case "BAJA":
            case "MEDIA":
            case "ALTA":
                this.calidadAudio = calidad.toUpperCase();
                break;
        }
    }

    public void limpiarDescargas (){
        descargados.clear();

    }

    @Override
    public String toString() {
        return super.toString();
    }
}
