package modelo.artistas;

import excepciones.artista.AlbumYaExisteException;
import excepciones.artista.ArtistaNoVerificadoException;
import modelo.contenido.Cancion;
import modelo.contenido.Contenido;

import java.util.ArrayList;
import java.util.Date;

public class Artista {

    private String id;
    private String nombreArtistico;
    private String nombreReal;
    private String paisOrigen;
    private ArrayList<Cancion> discografia;
    private ArrayList<Album> albumes;
    private int oyentesMensuales;
    private boolean verificado;
    private String biografia;


    public Artista(String nombreArtistico, String nombreReal, String paisOrigen) {
        this.nombreArtistico = nombreArtistico;
        this.nombreReal = nombreReal;
        this.paisOrigen = paisOrigen;
    }

    public Artista (String nombreArtistico, String nombreReal, String paisOrigen, boolean verificado, String biografia){
        this.nombreArtistico = nombreArtistico;
        this.nombreReal = nombreReal;
        this.paisOrigen = paisOrigen;
        this.verificado = verificado;
        this.biografia = biografia;
    }

    public String getId() {
        return id;
    }

    public String getNombreArtistico() {
        return nombreArtistico;
    }

    public void setNombreArtistico(String nombreArtistico) {
        this.nombreArtistico = nombreArtistico;
    }

    public String getNombreReal() {
        return nombreReal;
    }

    public void setNombreReal(String nombreReal) {
        this.nombreReal = nombreReal;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    public ArrayList<Cancion> getDiscografia() {
        return discografia;
    }

    public ArrayList<Album> getAlbumes() {
        return albumes;
    }

    public int getOyentesMensuales() {
        return oyentesMensuales;
    }

    public void setOyentesMensuales(int oyentesMensuales) {
        this.oyentesMensuales = oyentesMensuales;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public void publicarCancion (Cancion cancion){

    }

    public void crearAlbum (String titulo, Date fecha) throws ArtistaNoVerificadoException, AlbumYaExisteException {
    }

    public ArrayList<Cancion> obtenerTopCanciones (int cantidad){

        return null;
    }

    public double calcularPromedioReproducciones (){

        return 0;
    }

    public boolean esVerificado (){

        return false;
    }

    public int getTotalReproducciones (){

        return 0;
    }

    public void verificar (){
    }

    public void incrementarOyentes (){
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Contenido contenido = (Contenido) obj;
        return id.equals(contenido.getId());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
