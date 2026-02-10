package modelo.contenido;

import enums.CategoriaPodcast;
import excepciones.contenido.ContenidoNoDisponibleException;
import excepciones.contenido.DuracionInvalidaException;
import excepciones.contenido.EpisodioNoEncontradoException;
import excepciones.contenido.TranscripcionNoDisponibleException;
import excepciones.descarga.ContenidoYaDescargadoException;
import excepciones.descarga.LimiteDescargasException;
import interfaces.Descargable;
import interfaces.Recomendador;
import interfaces.Reproducible;
import modelo.artistas.Creador;

import java.util.ArrayList;
import java.util.Date;
import java.util.SortedMap;

public class Podcast extends Contenido implements Reproducible, Descargable {

    private Creador creador;
    private int numeroEpisodios;
    private int temporada;
    private String descripcion;
    private CategoriaPodcast categoria;
    private ArrayList<String> invitados;
    private String transcripcion;
    private boolean reproduciendo;
    private boolean pausado;
    private boolean descargado;


    public Podcast(String titulo, int duracionSegundos, Creador creador, int numeroEpisodios, int temporada, CategoriaPodcast categoria)
                    throws DuracionInvalidaException {

        super(titulo,duracionSegundos);
        this.creador = creador;
        this.numeroEpisodios = numeroEpisodios;
        this.temporada = temporada;
        this.categoria = categoria;
        this.invitados = new ArrayList<>();
        this.reproduciendo = false;
        this.pausado = false;
        this.descargado = false;
    }

    public Podcast (String titulo, int duracionSegundos, Creador creador, int numeroEpisodios, int temporada, CategoriaPodcast categoria, String descripcion)
                    throws DuracionInvalidaException{
        super(titulo,duracionSegundos,creador, numeroEpisodios, temporada, categoria);
        this.descripcion = descripcion;
    }

    public Creador getCreador() {
        return creador;
    }

    public void setCreador(Creador creador) {
        this.creador = creador;
    }

    public int getNumeroEpisodios() {
        return numeroEpisodios;
    }

    public void setNumeroEpisodios(int numeroEpisodios) {
        this.numeroEpisodios = numeroEpisodios;
    }

    public int getTemporada() {
        return temporada;
    }

    public void setTemporada(int temporada) {
        this.temporada = temporada;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public CategoriaPodcast getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaPodcast categoria) {
        this.categoria = categoria;
    }

    public ArrayList<String> getInvitados() {
        return new ArrayList<>(invitados);
    }

    public String getTranscripcion() {
        return transcripcion;
    }

    public void setTranscripcion(String transcripcion) {
        this.transcripcion = transcripcion;
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

    @Override
    public void reproducir () throws ContenidoNoDisponibleException {
        if (!disponible){
            throw new ContenidoNoDisponibleException("El podcast no está disponible");
        }

    }

    @Override
    public void play (){
        reproduciendo = true;
        pausado = false;
        System.out.println("Reproduciendo podcast: " + titulo);

    }

    @Override
    public void pause (){
        if (reproduciendo){
            reproduciendo = false;
            pausado = true;
            System.out.println("Podcast pausado");
        }

    }

    public void stop (){
        reproduciendo = false;
        pausado = false;
        System.out.println("Podcast detenido");

    }

    public int getDuracion(){

        return duracionSegundos;
    }

    public boolean descargar ()
            throws LimiteDescargasException, ContenidoYaDescargadoException {

        if (descargado){
            throw new ContenidoYaDescargadoException("El podcast ya está descargado");
        }
        descargado = true;
        return true;
    }

    public boolean eliminarDescarga(){

        if (descargado){
            descargado = false;
            return true;
        }
        return false;
    }

    public int espacioRequerido() {
        return duracionSegundos / 300;
    }

    public String obtenerDescripcion(){

        return descripcion != null  && descripcion.isEmpty() ? descripcion : "Podcast sin descripción";
    }

    public void agregarInvitado (String nombre){
        if (nombre != null && !nombre.isEmpty() && !invitados.contains(nombre)){
            invitados.add(nombre);
        }
    }

    public boolean esTemporadaNueva (){

        return temporada >= 1;
    }

    public String obtenerTranscripcion () throws TranscripcionNoDisponibleException {
        if (transcripcion == null || transcripcion.isBlank()) {
            throw new TranscripcionNoDisponibleException("No hay transcripción disponible");
        }
            return transcripcion;
    }

    public void validarEpisodio () throws EpisodioNoEncontradoException {
        if (numeroEpisodios <=0 || temporada <= 0){
            throw new EpisodioNoEncontradoException("Episodio inválido");
        }
    }

    @Override
    public String toString() {
        return "Podcast: " + titulo + " (T" + temporada + " E " + numeroEpisodios + " ) - " + creador.getNombre();
    }
}
