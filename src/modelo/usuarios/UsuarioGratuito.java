package modelo.usuarios;

import enums.TipoSuscripcion;
import excepciones.contenido.ContenidoNoDisponibleException;
import excepciones.usuario.AnuncioRequeridoException;
import excepciones.usuario.EmailInvalidoException;
import excepciones.usuario.LimiteDiarioAlcanzadoException;
import excepciones.usuario.PasswordDebilException;
import modelo.contenido.Contenido;
import modelo.plataforma.Anuncio;

import java.util.Date;

public class UsuarioGratuito extends Usuario {
    private int anunciosEscuchados;
    private Date ultimoAnuncio;
    private int reproduccionesHoy;
    private int limiteReproducciones;
    private int cancionesSinAnuncio;
    private Date fechaUltimaReproduccion;

    private static final  int LIMITE_DIARIO = 50;
    private static final int CANCIONES_ENTRE_ANUNCIOS = 3;

    public UsuarioGratuito(String nombre, String email, String password) throws EmailInvalidoException, PasswordDebilException{
        super(nombre, email, password, TipoSuscripcion.GRATUITO);
        this.anunciosEscuchados = 0;
        this.ultimoAnuncio = null;
        this.reproduccionesHoy = 0;
        this.limiteReproducciones = LIMITE_DIARIO;
        this.cancionesSinAnuncio = 0;
        this.fechaUltimaReproduccion = null;
    }

    @Override
    public void validarPassword() throws PasswordDebilException {
        if (password == null || password.length() < 6){
            throw new PasswordDebilException("Password débil");
        }
    }

    public int getAnunciosEscuchados() {
        return anunciosEscuchados;
    }

    // ...existing code...

    public Date getUltimoAnuncio() {
        return ultimoAnuncio;
    }

    public int getReproduccionesHoy() {
        return reproduccionesHoy;
    }

    public void setReproduccionesHoy(int reproduccionesHoy) {
        this.reproduccionesHoy = reproduccionesHoy;
    }

    public int getLimiteReproducciones() {
        return limiteReproducciones;
    }

    public int getCancionesSinAnuncio() {
        return cancionesSinAnuncio;
    }

    public void setCancionesSinAnuncio(int cancionesSinAnuncio) {
        this.cancionesSinAnuncio = cancionesSinAnuncio;
    }

    public void reproducir (Contenido contenido) throws ContenidoNoDisponibleException, LimiteDiarioAlcanzadoException, AnuncioRequeridoException {
        // Verificar disponibilidad
        if (contenido == null || !contenido.isDisponible()){
            throw new ContenidoNoDisponibleException();
        }

        // Verificar límite diario
        if (reproduccionesHoy >= LIMITE_DIARIO) {
            throw new LimiteDiarioAlcanzadoException();
        }

        // Verificar si debe ver anuncio ANTES de reproducir
        if (debeVerAnuncio()) {
            throw new AnuncioRequeridoException("Anuncio requerido");
        }

        // Reproducir
        contenido.aumentarReproduciones();
        agregarAlHistorial(contenido);
        reproduccionesHoy++;
        cancionesSinAnuncio++;
    }

    public void verAnuncio (){
        anunciosEscuchados++;
        cancionesSinAnuncio = 0;
    }

    public void verAnuncio (Anuncio anuncio){
        anunciosEscuchados++;
        cancionesSinAnuncio = 0;
    }

    public boolean puedeReproducir(){
        return reproduccionesHoy < LIMITE_DIARIO;
    }

    public boolean debeVerAnuncio (){
        return cancionesSinAnuncio >= CANCIONES_ENTRE_ANUNCIOS;
    }

    public void reiniciarContadorDiario(){
        reproduccionesHoy = 0;
        cancionesSinAnuncio = 0;
    }

    public int getReproduccionesRestantes (){
        return LIMITE_DIARIO - reproduccionesHoy;
    }

    public int getCancionesHastaAnuncio (){
        return CANCIONES_ENTRE_ANUNCIOS - cancionesSinAnuncio;
    }
}
