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
        this.redesSociales = new HashMap<>();
        this.categoriasPrincipales = new ArrayList<>();
        this.suscriptores = 0;
    }

    public Creador (String nombreCanal, String nombre, String descripcion){
        this.nombreCanal = nombreCanal;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.episodios = new ArrayList<>();
        this.redesSociales = new HashMap<>();
        this.categoriasPrincipales = new ArrayList<>();
        this.suscriptores = 0;
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
        return new EstadisticasCreador(this);
    }

    public void agregarRedSocial (String red, String usuario){
        if (redesSociales == null) {
            redesSociales = new HashMap<>();
        }
        redesSociales.put(red.toLowerCase(), usuario);
    }

    public double calcularPromedioReproducciones (){
        if (episodios.isEmpty()) {
            return 0.0;
        }
        int total = getTotalReproducciones();
        return (double) total / episodios.size();
    }

    public void eliminarEpisodio (String idEpisodio) throws EpisodioNoEncontradoException {
        for (int i = 0; i < episodios.size(); i++) {
            if (episodios.get(i).getId().equals(idEpisodio)) {
                episodios.remove(i);
                return;
            }
        }
        throw new EpisodioNoEncontradoException("Episodio no encontrado");
    }

    public int getTotalReproducciones (){
        int total = 0;
        for (Podcast p : episodios) {
            total += p.getReproducciones();
        }
        return total;
    }

    public void incrementarSuscriptores (){
        this.suscriptores++;
    }

    public ArrayList<Podcast> obtenerTopEpisodios (int cantidad){
        ArrayList<Podcast> top = new ArrayList<>(episodios);
        top.sort((p1, p2) -> Integer.compare(p2.getReproducciones(), p1.getReproducciones()));
        return new ArrayList<>(top.subList(0, Math.min(cantidad, top.size())));
    }

    public int getUltimaTemporada (){
        if (episodios.isEmpty()) {
            return 0;
        }
        int maxTemporada = 0;
        for (Podcast p : episodios) {
            if (p.getTemporada() > maxTemporada) {
                maxTemporada = p.getTemporada();
            }
        }
        return maxTemporada;
    }

    public Podcast[] getPodcasts() {
        return episodios.toArray(new Podcast[0]);
    }

    public int getTotalLikes() {
        int total = 0;
        for (Podcast p : episodios) {
            total += p.getLikes();
        }
        return total;
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
