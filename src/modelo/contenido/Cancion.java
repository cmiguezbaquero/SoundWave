package modelo.contenido;

import enums.GeneroMusical;
import excepciones.contenido.ArchivoAudioNoEncontradoException;
import excepciones.contenido.ContenidoNoDisponibleException;
import excepciones.contenido.DuracionInvalidaException;
import excepciones.contenido.LetraNoDisponibleException;
import excepciones.descarga.ContenidoYaDescargadoException;
import excepciones.descarga.LimiteDescargasException;
import interfaces.Descargable;
import interfaces.Reproducible;
import modelo.artistas.Album;
import modelo.artistas.Artista;

import java.util.Date;
import java.util.UUID;


public class Cancion extends Contenido implements Reproducible, Descargable {

    private String letra;
    private Artista artista;
    private Album album;
    private GeneroMusical genero;
    private String audioURL;
    private boolean explicit;
    private String ISRC;
    private boolean reproduciendo;
    private boolean pausado;
    private boolean descargado;

    public Cancion(String titulo, int duracionSegundos, Artista artista, GeneroMusical genero) throws DuracionInvalidaException {
        super(titulo, duracionSegundos);
        this.artista = artista;
        this.genero = genero;
        this.audioURL = "default.mp3";
        this.ISRC = generarISRC();
        this.reproduciendo = false;
        this.pausado = false;
        this.descargado = false;
    }

    public Cancion (String titulo, int duracionSegundos, Artista artista, GeneroMusical genero, String letra, boolean explicit) throws DuracionInvalidaException {
        this(titulo, duracionSegundos, artista, genero);
        this.letra = letra;
        this.explicit = explicit;
    }

    public Cancion(String titulo, Date fechaLanzamiento, Artista artista) {
        super();
    }

    public Cancion(String titulo, Artista artista, Date fechaLanzamiento, String discografia, String tipoAlbum) {
    }

    public String getLetra() {
        return letra;
    }

    public String setLetra(String letra){
        return letra;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
    }

    public Album getAlbum() {
        return album;
    }

    public Album setAlbum(Album album) {
        return album;
    }

    public GeneroMusical getGenero() {
        return genero;
    }

    public GeneroMusical setGenero(GeneroMusical genero) {
        return genero;
    }

    public String getAudioURL() {
        return audioURL;
    }

    public String setAudioURL(String audioURL) {
        return audioURL;
    }

    public boolean isExplicit() {
        return explicit;
    }

    public boolean setExplicit(boolean explicit) {
        return explicit;
    }


    public String getISRC() {
        return ISRC;
    }

    public boolean isReproduciendo() {
        return reproduciendo;
    }

    public boolean isPausado() {
        return pausado;
    }

    public boolean isDescargado() {
        return descargado;
    }

    public void setDescargado(boolean descargado) {
        this.descargado = descargado;
    }

    private String generarISRC (){
        return "ISRC" + UUID.randomUUID().toString().substring(0,8).toUpperCase();
    }

    public void reproducir() throws ContenidoNoDisponibleException {
        play();
        aumentarReproduciones();

    }

    public void play (){
        reproduciendo = true;
        pausado = false;
        System.out.println("Reproduciendo: " + getTitulo() + " - " + artista.getNombreArtistico());
    }

    public void pause (){
        reproduciendo = false;
        pausado = true;
    }

    public void stop (){
        reproduciendo = false;
        pausado= false;
    }

    public int getDuracion (){
        return duracionSegundos;
    }


    public String obtenerLetra() throws LetraNoDisponibleException {
        return letra;
    }

    public boolean esExplicit (){

        return false;
    }

    public void cambiarGenero (GeneroMusical nuevoGenero){
    }

    public void validarAudioURL () throws ArchivoAudioNoEncontradoException {
    }


    public boolean descargar () throws LimiteDescargasException, ContenidoYaDescargadoException {

        if (descargado){
            throw new ContenidoYaDescargadoException();
        }
        descargado = true;
        return true;
    }

    public boolean eliminarDescarga (){
        if (descargado){
            descargado = false;
            return true;
        }

        return false;
    }

    public int espacioRequerido (){
        return duracionSegundos * 2;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
