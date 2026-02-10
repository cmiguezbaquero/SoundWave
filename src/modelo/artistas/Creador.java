package modelo.artistas;

import enums.CategoriaPodcast;
import excepciones.artista.LimiteEpisodiosException;
import excepciones.contenido.EpisodioNoEncontradoException;
import modelo.contenido.Podcast;
import utilidades.EstadisticasCreador;

import java.util.ArrayList;
import java.util.HashMap;

public class Creador {

    private String id;
    private String nombreCanal;
    private String nombre;
    private ArrayList<Podcast> episodios;
    private int suscriptores;
    private String descripcion;
    private HashMap<String ,String> redesSociales;
    private ArrayList<CategoriaPodcast> categoriasPrincipales;

    private static final int MAX_EPISODIOS = 500;

    public Creador(String nombreCanal, String nombre) {
        this.nombreCanal = nombreCanal;
        this.nombre = nombre;
        this.episodios = new ArrayList<>();
    }

    public Creador (String nombreCanal, String nombre, String descripcion){
        this.nombreCanal = nombreCanal;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.episodios = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getNombreCanal() {
        return nombreCanal;
    }

    public void setNombreCanal(String nombreCanal) {
        this.nombreCanal = nombreCanal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Podcast> getEpisodios() {
        return episodios;
    }

    public int getSuscriptores() {
        return suscriptores;
    }

    public void setSuscriptores(int suscriptores) {
        this.suscriptores = suscriptores;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public HashMap<String, String> getRedesSociales() {
        return redesSociales;
    }

    public ArrayList<CategoriaPodcast> getCategoriasPrincipales() {
        return categoriasPrincipales;
    }

    public int getNumEpisodios (){
        return episodios != null ? episodios.size() : 0;
    }

    public void publicarPodcast (Podcast episodio) throws LimiteEpisodiosException {
        if (episodios.size() >= MAX_EPISODIOS) {
            throw new LimiteEpisodiosException("Límite de episodios alcanzado");
        }
        episodios.add(episodio);
    }

    public EstadisticasCreador obtenerEstadisticas (){

        return null;
    }

    public void agregarRedSocial (String red, String usuario){

    }

    public double calcularPromedioReproducciones (){

        return 0;
    }

    public void eliminarEpisodio (String idEpisodio) throws EpisodioNoEncontradoException {

    }

    public int getTotalReproducciones (){

        return 0;
    }

    public void incrementarSuscriptores (){
    }

    public ArrayList<Podcast> obtenerTopEpisodios (int cantidad){

        return null;
    }

    public int getUltimaTemporada (){

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

    public Podcast[] getPodcasts() {
        return new Podcast[0];
    }

    public int getTotalLikes() {
        return 0;
    }

}
