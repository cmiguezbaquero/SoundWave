package modelo.artistas;

import enums.GeneroMusical;
import excepciones.artista.AlbumCompletoException;
import excepciones.contenido.DuracionInvalidaException;
import excepciones.playlist.CancionNoEncontradaException;
import modelo.contenido.Cancion;

import java.util.ArrayList;
import java.util.Date;

public class Album extends Cancion {

    private String id;
    private String titulo;
    private Artista artista;
    private Date fechaLanzamiento;
    private ArrayList<Cancion> canciones;
    private String portadaURL;
    private String discografia;
    private String tipoAlbum;

    private static final int MAX_CANCIONES = 20;

    public Album(String titulo, Artista artista, Date fechaLanzamiento) {
        super(titulo,fechaLanzamiento, artista);
        this.titulo = titulo;
        this.artista = artista;
        this.fechaLanzamiento = fechaLanzamiento;
        this.canciones = new ArrayList<>();
    }

    public Album (String titulo, Artista artista, Date fechaLanzamiento, String discografia, String tipoAlbum){
        super(titulo, artista, fechaLanzamiento, discografia, tipoAlbum);
        this.titulo = titulo;
        this.artista = artista;
        this.fechaLanzamiento = fechaLanzamiento;
        this.discografia = discografia;
        this.tipoAlbum = tipoAlbum;
        this.canciones = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(Date fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public ArrayList<Cancion> getCanciones() {
        return canciones;
    }

    public String getPortadaURL() {
        return portadaURL;
    }

    public void setPortadaURL(String portadaURL) {
        this.portadaURL = portadaURL;
    }

    public String getDiscografia() {
        return discografia;
    }

    public void setDiscografia(String discografia) {
        this.discografia = discografia;
    }

    public String getTipoAlbum() {
        return tipoAlbum;
    }

    public void setTipoAlbum(String tipoAlbum) {
        this.tipoAlbum = tipoAlbum;
    }

    public void eliminarCancion (int posicion) throws CancionNoEncontradaException {
        if (posicion < 0 || posicion >= canciones.size()) {
            throw new CancionNoEncontradaException("Posición de canción no válida");
        }
        canciones.remove(posicion);
    }
    public void eliminarCancion (Cancion cancion){
        canciones.remove(cancion);
    }

    public int getDuracionTotal() {
        int duracionTotal = 0;
        for (Cancion cancion : canciones) {
            duracionTotal += cancion.getDuracionSegundos();
        }
        return duracionTotal;
    }

    public String getDuracionTotalFormateada() {
        int duracionTotal = getDuracionTotal();
        int minutos = duracionTotal / 60;
        int segundos = duracionTotal % 60;
        return String.format("%d:%02d", minutos, segundos);
    }

    public Cancion crearCancion (String titulo, int duracion, GeneroMusical genero)
            throws AlbumCompletoException, DuracionInvalidaException {
        if (canciones.size() >= MAX_CANCIONES) {
            throw new AlbumCompletoException("Álbum lleno");
        }
        Cancion cancion = new Cancion(titulo, duracion, artista, genero);
        canciones.add(cancion);
        return cancion;
    }

    public Cancion crearCancion (String titulo, int duracionSegundos, GeneroMusical genero, String letra, boolean explicit)
            throws AlbumCompletoException, DuracionInvalidaException {
        if (canciones.size() >= MAX_CANCIONES) {
            throw new AlbumCompletoException("Álbum lleno");
        }
        Cancion cancion = new Cancion(titulo, duracionSegundos, artista, genero, letra, explicit);
        canciones.add(cancion);
        return cancion;
    }

    public void agregarCancion (Cancion cancion){
        if (canciones.size() < MAX_CANCIONES) {
            canciones.add(cancion);
        }
    }

    public int getNumCanciones (){
        return canciones != null ? canciones.size() : 0;
    }

    public void ordenarPorPopularidad(){
    }

    public Cancion getCancion (int posicion) throws CancionNoEncontradaException{

        return null;
    }

    public int getTotalReproduciones (){

        return 0;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
