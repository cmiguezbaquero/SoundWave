package modelo.plataforma;

import enums.CategoriaPodcast;
import enums.GeneroMusical;
import enums.TipoSuscripcion;
import excepciones.artista.AlbumCompletoException;
import excepciones.artista.AlbumYaExisteException;
import excepciones.artista.ArtistaNoVerificadoException;
import excepciones.artista.LimiteEpisodiosException;
import excepciones.contenido.DuracionInvalidaException;
import excepciones.plataforma.ArtistaNoEncontradoException;
import excepciones.plataforma.ContenidoNoEncontradoException;
import excepciones.plataforma.UsuarioYaExisteException;
import excepciones.usuario.EmailInvalidoException;
import excepciones.usuario.PasswordDebilException;
import modelo.artistas.Album;
import modelo.artistas.Artista;
import modelo.artistas.Creador;
import modelo.contenido.Cancion;
import modelo.contenido.Contenido;
import modelo.contenido.Podcast;
import modelo.usuarios.Usuario;
import modelo.usuarios.UsuarioGratuito;
import modelo.usuarios.UsuarioPremium;
import utilidades.RecomendadorIA;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;


public class Plataforma {

    private static Plataforma instancia;
    private final String nombre;
    private HashMap<String, Usuario> usuarios;
    private final HashMap<String, Usuario> usuariosPorEmail;
    private ArrayList<UsuarioPremium> usuariosPremium;
    private ArrayList<UsuarioGratuito> usuariosGratuitos;
    private ArrayList<Contenido> catalogo;
    private ArrayList<Playlist> playlistsPublicas;
    private HashMap<String, Artista> artistas;
    private HashMap <String, Creador> creadores;
    private ArrayList <Album> albumes;
    private ArrayList <Anuncio> anuncios;
    private RecomendadorIA recomendador;
    private int totalAnunciosReproducidos;

    private Plataforma(String nombre) {
        this.nombre = nombre;
        this.usuarios = new HashMap<>();
        this.usuariosPorEmail = new HashMap<>();
        this.usuariosPremium = new ArrayList<>();
        this.usuariosGratuitos = new ArrayList<>();
        this.catalogo = new ArrayList<>();
        this.playlistsPublicas = new ArrayList<>();
        this.artistas = new HashMap<>();
        this.creadores = new HashMap<>();
        this.albumes = new ArrayList<>();
        this.anuncios = new ArrayList<>();
        this.recomendador = new RecomendadorIA();
        this.totalAnunciosReproducidos = 0;
    }

    public Plataforma(String nombre, HashMap<String, Usuario> usuariosPorEmail) {
        this.nombre = nombre;
        this.usuariosPorEmail = usuariosPorEmail;
    }

    public static synchronized Plataforma getInstancia (String nombre){
        if (instancia == null){
            instancia = new Plataforma(nombre);
        }
        return instancia;
    }

    public static synchronized Plataforma getInstancia (){
        return getInstancia("Plataforma por defecto");
    }

    public static synchronized void reiniciarInstancia (){
        instancia = null;
    }

    // Validaciones
    private void validarEmail(String email) throws EmailInvalidoException {
        if (email == null || email.isEmpty()){
            throw new EmailInvalidoException("Email inválido");
        }
        int atIndex = email.indexOf('@');
        if (atIndex <= 0 || atIndex >= email.length() - 1 || email.indexOf('@', atIndex + 1) != -1){
            throw new EmailInvalidoException("Email inválido");
        }
    }

    private void verificarDuplicado(String email) throws UsuarioYaExisteException {
        if (usuariosPorEmail.containsKey(email)){
            throw new UsuarioYaExisteException("Usuario ya existe");
        }
    }

    public UsuarioPremium registarUsuarioPremium (String nombre, String email, String password, TipoSuscripcion tipo)
            throws UsuarioYaExisteException, EmailInvalidoException, PasswordDebilException {

        validarEmail(email);
        verificarDuplicado(email);
        UsuarioPremium uP = new UsuarioPremium(nombre, email, password, tipo);
        usuarios.put(uP.getId(), uP);
        usuariosPorEmail.put(email,uP);
        usuariosPremium.add(uP);
        return uP;
    }

    public UsuarioGratuito registrarUsuarioGratuito (String nombre, String email, String password)
            throws UsuarioYaExisteException, EmailInvalidoException, PasswordDebilException{
        validarEmail(email);
        verificarDuplicado(email);
        UsuarioGratuito uG = new UsuarioGratuito(nombre, email, password);
        usuarios.put(uG.getId(), uG);
        usuariosPorEmail.put(email,uG);
        usuariosGratuitos.add(uG);
        return uG;
    }

    // Nuevos métodos públicos para los tets (registrarUsuarioPremium/registrarUsuarioGratuito)
    public UsuarioPremium registrarUsuarioPremium (String nombre, String email, String password)
            throws UsuarioYaExisteException, EmailInvalidoException, PasswordDebilException {
        return registarUsuarioPremium(nombre, email, password, TipoSuscripcion.PREMIUM);
    }

    public UsuarioPremium registrarUsuarioPremium (String nombre, String email, String password, TipoSuscripcion tipo)
            throws UsuarioYaExisteException, EmailInvalidoException, PasswordDebilException {
        return registarUsuarioPremium(nombre, email, password, tipo);
    }


    public String getNombre() {
        return nombre;
    }

    public ArrayList<Contenido> getCatalogo() {
        return new ArrayList<>(catalogo);
    }

    public HashMap<String, Artista> getArtistas() {
        return artistas;
    }

    public HashMap<String, Creador> getCreadores() {
        return creadores;
    }

    public ArrayList<Anuncio> getAnuncios() {
        return new ArrayList<>(anuncios);
    }

    public RecomendadorIA getRecomendador() {
        return recomendador;
    }

    public int getTotalUsuarios (){
        return usuarios.size();
    }

    public int getTotalContenido (){
        return catalogo.size();
    }

    public int getTotalAnunciosReproducidos (){
        return totalAnunciosReproducidos;
    }



    public ArrayList<Usuario> getTodosLosUsuarios (){
        return new ArrayList<>(usuarios.values());
    }

    public Usuario buscarUsuarioPorEmail (String email){
        return usuariosPorEmail.get(email);
    }

    public Artista registrarArtista (String nombreArtistico, String nombreReal, String paisOrigen, boolean verificado){
        Artista artista = new Artista(nombreArtistico, nombreReal, paisOrigen, verificado, "");
        artistas.put(artista.getNombreArtistico(), artista);
        return artista;
    }

    public void registrarArtista (Artista artista){
        artistas.put(artista.getNombreArtistico(), artista);
    }

    public ArrayList<Artista> getArtistasVerificados(){
        ArrayList<Artista> verificados = new ArrayList<>();
        for (Artista a : artistas.values()) {
            if (a.isVerificado()) {
                verificados.add(a);
            }
        }
        return verificados;
    }

    public ArrayList<Artista> getArtistasNoVerificados (){
        ArrayList<Artista> noVerificados = new ArrayList<>();
        for (Artista a : artistas.values()) {
            if (!a.isVerificado()) {
                noVerificados.add(a);
            }
        }
        return noVerificados;
    }

    public Artista buscarArtista (String nombre) throws ArtistaNoEncontradoException {
        Artista artista = artistas.get(nombre);
        if (artista == null) {
            throw new ArtistaNoEncontradoException("Artista no encontrado");
        }
        return artista;
    }

    public Album crearAlbum (Artista artista, String titulo, Date fecha) throws ArtistaNoVerificadoException, AlbumYaExisteException {
        if (!artista.isVerificado()) {
            throw new ArtistaNoVerificadoException("Artista no verificado");
        }

        // Verificar si el álbum ya existe
        for (Album a : artista.getAlbumes()) {
            if (a.getTitulo().equals(titulo)) {
                throw new AlbumYaExisteException("El álbum ya existe");
            }
        }
        Album album = new Album(titulo, artista, fecha);
        albumes.add(album);
        artista.getAlbumes().add(album);
        return album;
    }


    public ArrayList<Album> getAlbumes() {
        return new ArrayList<>(albumes);
    }

    public ArrayList<UsuarioPremium> getUsuariosPremium (){
        return new ArrayList<>(usuariosPremium);
    }

    public ArrayList<UsuarioGratuito> getUsuariosGratuitos (){
        return new ArrayList<>(usuariosGratuitos);
    }

    public Cancion crearCancioneEnAlbum (String titulo, int duracion, Artista artista, GeneroMusical genero, Album album) throws DuracionInvalidaException, AlbumCompletoException {

        return null;
    }

    public void agregarContenidoCatalogo (Contenido contenido){
        catalogo.add(contenido);
    }

    public ArrayList<Cancion> getCanciones (){
        ArrayList<Cancion> canciones = new ArrayList<>();
        for (Contenido c : catalogo) {
            if (c instanceof Cancion && !(c instanceof Album)) {
                canciones.add((Cancion) c);
            }
        }
        return canciones;
    }

    public Creador registrarCreador (String nombreCanal, String nombre, String descripcion){
        Creador creador = new Creador(nombreCanal, nombre, descripcion);
        creadores.put(nombreCanal, creador);
        return creador;
    }

    public void registrarCreador (Creador creador){
        creadores.put(creador.getNombreCanal(), creador);
    }

    public Podcast crearPodcast (String titulo, int duracion, Creador creador, int numEpisodio, int temporada, CategoriaPodcast categoria)
            throws DuracionInvalidaException, LimiteEpisodiosException{
        Podcast podcast = new Podcast(titulo, duracion, creador, numEpisodio, temporada, categoria);
        catalogo.add(podcast);
        creador.publicarPodcast(podcast);
        return podcast;
    }

    public ArrayList<Podcast> getPodcasts (){
        ArrayList<Podcast> podcasts = new ArrayList<>();
        for (Contenido c : catalogo) {
            if (c instanceof Podcast) {
                podcasts.add((Podcast) c);
            }
        }
        return podcasts;
    }

    public ArrayList<Creador> getTodosLosCreadores (){
        return new ArrayList<>(creadores.values());
    }

    public Playlist crearPlaylistPublica (String nombre, Usuario creador){
        Playlist playlist = new Playlist(nombre, creador, true, "");
        playlistsPublicas.add(playlist);
        return playlist;
    }

    public ArrayList<Playlist> getPlaylistsPublicas (){
        return new ArrayList<>(playlistsPublicas);
    }

    public ArrayList<Contenido> buscarContenido (String termino) throws ContenidoNoEncontradoException {
        ArrayList<Contenido> resultados = new ArrayList<>();
        for (Contenido c : catalogo) {
            if (c.getTitulo().toLowerCase().contains(termino.toLowerCase())) {
                resultados.add(c);
            }
        }
        if (resultados.isEmpty()) {
            throw new ContenidoNoEncontradoException("No se encontraron contenidos para el término: " + termino);
        }
        return resultados;
    }

    public ArrayList<Cancion> buscarPorGenero (GeneroMusical genero) throws ContenidoNoEncontradoException{
        ArrayList<Cancion> resultados = new ArrayList<>();
        for (Contenido c : catalogo) {
            if (c instanceof Cancion) {
                Cancion cancion = (Cancion) c;
                if (cancion.getGenero() == genero) {
                    resultados.add(cancion);
                }
            }
        }
        if (resultados.isEmpty()) {
            throw new ContenidoNoEncontradoException("No se encontraron canciones del género " + genero);
        }
        return resultados;
    }

    public ArrayList<Podcast> buscarPorCategria (CategoriaPodcast categoria) throws ContenidoNoEncontradoException{
        ArrayList<Podcast> resultados = new ArrayList<>();
        for (Contenido c : catalogo) {
            if (c instanceof Podcast) {
                Podcast podcast = (Podcast) c;
                if (podcast.getCategoria() == categoria) {
                    resultados.add(podcast);
                }
            }
        }
        if (resultados.isEmpty()) {
            throw new ContenidoNoEncontradoException("No se encontraron podcasts de la categoría " + categoria);
        }
        return resultados;
    }

    public ArrayList<Contenido> obtenerTopContenidos (int cantidad){
        ArrayList<Contenido> top = new ArrayList<>(catalogo);
        top.sort((c1, c2) -> Integer.compare(c2.getReproducciones(), c1.getReproducciones()));
        return new ArrayList<>(top.subList(0, Math.min(cantidad, top.size())));
    }

    public ArrayList<Contenido> obtenerRecomendaciones (Usuario usuario){
        try {
            return recomendador.recomendar(usuario);
        } catch (excepciones.recomendacion.RecomendacionException e) {
            return new ArrayList<>();
        }
    }

    public Anuncio obtenerAnuncioAleatorio (){
        if (anuncios.isEmpty())return null;
        return anuncios.get(new Random().nextInt(anuncios.size()));
    }

    public void incrementarAnuncioReproducidos (){
        totalAnunciosReproducidos++;

    }

    public String obtenerEstadisticasGenerales (){

        return "";
    }

    @Override
    public String toString() {
        return "Plataforma " + nombre + " | Usuarios: " + getTotalUsuarios();
    }

    public ArrayList<Podcast> buscarPorCategoria(CategoriaPodcast categoriaPodcast) {
        ArrayList<Podcast> resultados = new ArrayList<>();
        for (Contenido c : catalogo) {
            if (c instanceof Podcast) {
                Podcast podcast = (Podcast) c;
                if (podcast.getCategoria() == categoriaPodcast) {
                    resultados.add(podcast);
                }
            }
        }
        return resultados;
    }
}
