package modelo.contenido;

import enums.CategoriaPodcast;
import excepciones.contenido.ContenidoNoDisponibleException;
import excepciones.contenido.DuracionInvalidaException;
import modelo.artistas.Creador;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public abstract class Contenido {

    protected String id;
    protected String titulo;
    protected int reproducciones;
    protected int likes;
    protected int duracionSegundos;
    protected ArrayList <String> tags;
    protected boolean disponible;
    protected Date fechaPublicacion;

    public Contenido(String titulo, int duracionSegundos) throws DuracionInvalidaException{

        if (duracionSegundos <= 0){
            throw new DuracionInvalidaException("La duración debe ser mayor a 0");
        }
        this.id = UUID.randomUUID().toString();
        this.titulo = titulo;
        this.reproducciones = 0;
        this.likes = 0;
        this.duracionSegundos = duracionSegundos;
        this.tags = new ArrayList<>();
        this.disponible = true;
        this.fechaPublicacion = new Date();
    }

    public Contenido(String titulo, int duracionSegundos, Creador creador, int numeroEpisodios, int temporada, CategoriaPodcast categoria) {
    }

    public Contenido() {

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

    public int getReproduciones() {
        return reproducciones;
    }

    public void setReproduciones(int reproduciones) {
        this.reproducciones = reproduciones;
    }

    public int getLikes() {
        return likes;
    }

    public int getDuracionSegundos() {
        return duracionSegundos;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public abstract void reproducir () throws ContenidoNoDisponibleException;

    public void aumentarReproduciones (){
        reproducciones++;

    }

    public void agregarLike(){
        likes++;
    }

    public boolean esPopular (){

        return reproducciones > 100000;
    }

    public void validarDuracion () throws DuracionInvalidaException {

        if (duracionSegundos <= 0){
            throw new DuracionInvalidaException("La duración debe ser positiva");
        }

    }

    public void agregarTag (String tag){

    }

    public boolean tieneTag (String tag){
        return true;
    }

    public void marcarNoDisponible (){

    }

    public void marcarDisponible (){

    }

    public String getDuracionFormateada (){

        return "";
    }


    @Override
    public String toString() {
        return titulo + "[" + getDuracionFormateada() + "]";}

    @Override
    public boolean equals (Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Contenido contenido = (Contenido) obj;
        return id.equals(contenido.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public int getReproducciones() {
        return 0;
    }

    public void setReproducciones(int i) {
    }

    public int getMaxCanciones() {
        return 0;
    }

    public int getTotalReproducciones() {
        return 0;
    }
}
