package modelo.plataforma;

import enums.CriterioOrden;
import excepciones.playlist.ContenidoDuplicadoException;
import excepciones.playlist.PlaylistLlenaException;
import excepciones.playlist.PlaylistVaciaException;
import modelo.contenido.Contenido;
import modelo.usuarios.Usuario;

import java.util.*;

public class Playlist {

    private String id;
    private String nombre;
    private Usuario creador;
    private ArrayList<Contenido>contenidos;
    private boolean esPublica;
    private int seguidores;
    private String descripcion;
    private String portadaURL;
    private Date fechaCreacion;
    private int maxContenidos;

    private static final int MAX_CONTENIDOS_DEFAULT = 500;

    public Playlist(String nombre, Usuario creador) {
        this.nombre = nombre;
        this.creador = creador;
        this.esPublica = false;
        this.descripcion = "";
        this.contenidos = new ArrayList<>();
        this.fechaCreacion = new Date();
        this.seguidores = 0;
        this.maxContenidos = MAX_CONTENIDOS_DEFAULT;
        this.id = UUID.randomUUID().toString();
    }

    public Playlist (String nombre, Usuario creador, boolean esPublica, String descripcion){
        this.nombre = nombre;
        this.creador = creador;
        this.esPublica = esPublica;
        this.descripcion = descripcion;
        this.id = UUID.randomUUID().toString();
        this.contenidos = new ArrayList<>();
        this.fechaCreacion = new Date();
        this.seguidores = 0;
        this.maxContenidos = MAX_CONTENIDOS_DEFAULT;
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

    public Usuario getCreador() {
        return creador;
    }

    public ArrayList<Contenido> getContenidos() {
        return new ArrayList<>(contenidos);
    }

    public boolean isEsPublica() {

        return esPublica;
    }

    public void setEsPublica(boolean esPublica) {
        this.esPublica = esPublica;
    }

    public int getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(int seguidores) {
        this.seguidores = seguidores;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPortadaURL() {
        return portadaURL;
    }

    public void setPortadaURL(String portadaURL) {
        this.portadaURL = portadaURL;
    }

    public Date getFechaCreacion() {
        return new Date(fechaCreacion.getTime());
    }

    public int getMaxContenidos() {
        return maxContenidos;
    }

    public void agregarContenido (Contenido contenido)
            throws PlaylistLlenaException, ContenidoDuplicadoException {

        if (contenidos.size () >= maxContenidos){
            throw new PlaylistLlenaException("La playlist está llena");
        }
        if (contenidos.contains(contenido)){
            throw new ContenidoDuplicadoException("El contenido ya existe");
        }
        contenidos.add(contenido);
    }

    public boolean eliminarContenido (String idContenido){

        return contenidos.removeIf(c -> c.getId().equals(idContenido));
    }

    public boolean eliminarContenido (Contenido contenido){

        return contenidos.remove(contenido);
    }

    public void ordenarPor (CriterioOrden criterio) throws PlaylistVaciaException {
        if (contenidos.isEmpty()){
            throw new PlaylistVaciaException("Playlist vacía");
        }

        switch(criterio) {
            case POPULARIDAD:
                contenidos.sort((c1, c2) -> Integer.compare(c2.getReproducciones(), c1.getReproducciones()));
                break;
            case DURACION:
                contenidos.sort((c1, c2) -> Integer.compare(c1.getDuracionSegundos(), c2.getDuracionSegundos()));
                break;
            case ALFABETICO:
                contenidos.sort((c1, c2) -> c1.getTitulo().compareTo(c2.getTitulo()));
                break;
            case ALEATORIO:
                Collections.shuffle(contenidos);
                break;
            case ARTISTA:
                // Ordenar por artista (si el contenido es una canción)
                contenidos.sort((c1, c2) -> {
                    String artista1 = (c1 instanceof modelo.contenido.Cancion) ?
                        ((modelo.contenido.Cancion)c1).getArtista().getNombreArtistico() : "";
                    String artista2 = (c2 instanceof modelo.contenido.Cancion) ?
                        ((modelo.contenido.Cancion)c2).getArtista().getNombreArtistico() : "";
                    return artista1.compareTo(artista2);
                });
                break;
            case FECHA_AGREGADO:
            default:
                // Por defecto no cambiar el orden (fue agregado en orden)
                break;
        }
    }

    public int getDuracionTotal (){

        int total = 0;
        for (Contenido c : contenidos){
            total += c.getDuracionSegundos();
        }
        return total;
    }

    public String getDuracionTotalFormateada (){
        int total = getDuracionTotal();
        int minutos = total / 60;
        int segundos = total % 60;

        return String.format("%d:%02d", minutos, segundos);
    }

    public void shuffle (){
        Collections.shuffle(contenidos);
    }

    public ArrayList<Contenido> buscarContenido (String termino){
        ArrayList<Contenido> resultado = new ArrayList<>();
        for (Contenido c : contenidos){
            if (c.getTitulo().toLowerCase().contains(termino.toLowerCase())){
                resultado.add(c);
            }
        }
        return resultado;
    }

    public void hacerPublica (){
        esPublica = true;
    }

    public void hacerPrivada(){
        esPublica = false;
    }

    public void incrementarSeguirdores (){
        seguidores++;
    }

    public  void decrementarSeguidores (){
        if (seguidores > 0){
            seguidores--;
        }
    }

    public int getNumContenidos(){

        return contenidos.size();
    }

    public boolean estaVacia (){

        return contenidos.isEmpty();
    }

    public Contenido getContenido (int posicion){
        if (posicion < 0 || posicion >= contenidos.size()){
            return null;
        }
        return contenidos.get(posicion);
    }

    @Override
    public String toString() {
        return "Playlist {" +
                "Nombre= " + nombre +
                ", creador = " + creador+
                ", contenidos = " + contenidos.size() + " }";
    }

    @Override
    public boolean equals(Object obj){

        if (this == obj) return true;
        if (!(obj instanceof Playlist other)) return false;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
