package modelo.usuarios;

import enums.TipoSuscripcion;
import excepciones.contenido.ContenidoNoDisponibleException;
import excepciones.usuario.AnuncioRequeridoException;
import excepciones.usuario.EmailInvalidoException;
import excepciones.usuario.LimiteDiarioAlcanzadoException;
import excepciones.usuario.PasswordDebilException;
import modelo.contenido.Contenido;
import modelo.plataforma.Playlist;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public abstract class Usuario {

    private static final int LIMITE_HISTORIAL = 0;
    protected String id;
    protected String nombre;
    protected String email;
    protected String password;
    protected TipoSuscripcion suscripcion;
    protected ArrayList<Playlist> misPlaylists;
    protected ArrayList<Contenido>historial;
    protected Date fechaRegistro;
    protected ArrayList<Playlist> playlistsSeguidas;
    protected ArrayList<Contenido> contenidosLiked;

    public Usuario(String nombre, String email, String password, TipoSuscripcion suscripcion)
            throws EmailInvalidoException, PasswordDebilException {

        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.suscripcion = suscripcion;

        validarEmail();
        validarPassword();

        this.id = UUID.randomUUID().toString();
        this.misPlaylists = new ArrayList<>();
        this.historial = new ArrayList<>();
        this.playlistsSeguidas = new ArrayList<>();
        this.contenidosLiked = new ArrayList<>();
        this.fechaRegistro = new Date();
    }


    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws EmailInvalidoException {
        this.email = email;
        validarEmail();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws PasswordDebilException{
        this.password = password;
        validarPassword();

    }

    public TipoSuscripcion getSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(TipoSuscripcion suscripcion) {
        this.suscripcion = suscripcion;
    }

    public ArrayList<Playlist> getMisPlaylists() {
        return new ArrayList<>(misPlaylists);
    }

    public ArrayList<Contenido> getHistorial() {
        return new ArrayList<>(historial);
    }

    public Date getFechaRegistro() {
        return new Date(fechaRegistro.getTime());
    }

    public ArrayList<Playlist> getPlaylistsSeguidas (){

        return new ArrayList<>(playlistsSeguidas);
    }

    public ArrayList<Contenido> getContenidosLiked (){

        return new ArrayList<>(contenidosLiked);
    }

    public abstract void reproducir (Contenido contenido)
            throws ContenidoNoDisponibleException, LimiteDiarioAlcanzadoException, AnuncioRequeridoException;


    public Playlist crearPlaylist(String nombrePlaylist) {
        Playlist playlist = new Playlist(nombrePlaylist, this);
        misPlaylists.add(playlist);
        return playlist;
    }

    public void seguirPlaylist(Playlist playlist) {
        if (!playlistsSeguidas.contains(playlist)) {
            playlistsSeguidas.add(playlist);
        }
    }

    public void dejarDeSeguirPlaylist (Playlist playlist){
            playlistsSeguidas.remove (playlist);

    }

    public void darLike(Contenido contenido) {
        if (!contenidosLiked.contains(contenido)){
            contenidosLiked.add(contenido);
        }
    }

    public void quitarLike(Contenido contenido){

        contenidosLiked.remove(contenido);
    }

    public boolean validarEmail() throws EmailInvalidoException {
        if (email == null || email.isEmpty()){
            throw new EmailInvalidoException("Email inválido");
        }
        int atIndex = email.indexOf('@');
        if (atIndex <= 0 || atIndex >= email.length() - 1 || email.indexOf('@', atIndex + 1) != -1){
            throw new EmailInvalidoException("Email inválido");
        }
        return true;
    }

    public abstract void validarPassword() throws PasswordDebilException;

    public void agregarAlHistorial(Contenido contenido) {

        if (contenido!=null) {

            historial.remove(contenido);
            historial.add(contenido);

            if (historial.size() > LIMITE_HISTORIAL) {
                historial.remove(0);
            }
        }
    }

    public void limpiarHistorial (){
        historial.clear();
    }

    public boolean esPremium (){
        return suscripcion != TipoSuscripcion.GRATUITO;
    }

    @Override
    public String toString() {
        return nombre + " (" + email + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof  Usuario)) return false;
        Usuario other = (Usuario) obj;
        return Objects.equals(email, other.email);
    }

    @Override
    public int hashCode() {
            return Objects.hash(email);
    }
}

