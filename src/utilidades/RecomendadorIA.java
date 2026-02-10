package utilidades;

import enums.AlgoritmoRecomendacion;
import excepciones.recomendacion.RecomendacionException;
import interfaces.Recomendador;
import modelo.contenido.Contenido;
import modelo.usuarios.Usuario;

import java.util.ArrayList;
import java.util.HashMap;

public class RecomendadorIA implements Recomendador {

    private HashMap<String, ArrayList<String>> matrizPreferencias;
    private HashMap<String, ArrayList<Contenido>> historialCompleto;
    private AlgoritmoRecomendacion algoritmo;
    private double umbralSimilar;
    private boolean modeloEntrenado;
    private ArrayList<Contenido> catalogoReferencia;

    private static final double UMBRAL_DEFAULT = 0.6;

    public RecomendadorIA (){

    }

    public RecomendadorIA(AlgoritmoRecomendacion algoritmo) {
        this.algoritmo = algoritmo;
    }

    public AlgoritmoRecomendacion getAlgoritmo() {
        return algoritmo;
    }

    public void setAlgoritmo(AlgoritmoRecomendacion algoritmo) {
        this.algoritmo = algoritmo;
    }

    public double getUmbralSimilar() {
        return umbralSimilar;
    }

    public void setUmbralSimilar(double umbralSimilar) {
        this.umbralSimilar = umbralSimilar;
    }

    public boolean isModeloEntrenado() {
        return modeloEntrenado;
    }

    public HashMap<String, ArrayList<String>> getMatrizPreferencias() {
        return matrizPreferencias;
    }

    public void setCatalogoReferencia(ArrayList<Contenido> catalogo) {
        this.catalogoReferencia = catalogoReferencia;
    }

    public ArrayList<Contenido> recomendar (Usuario usuario) throws RecomendacionException {

        return null;
    }

    public ArrayList<Contenido> obtenerSimilares (Contenido contenido) throws RecomendacionException{

        return null;
    }

    public void entrenarModelo (ArrayList<Usuario> usuarios) {

    }
    public void entrenarModelo (ArrayList<Usuario> usuarios, ArrayList<Contenido> catalogo){

    }

    public double calcularSimilitud (Usuario u1, Usuario u2){

        return 0;
    }

    public void actualizarPreferencias (Usuario usuario){

    }

    public HashMap<String, Integer> obtenerGenerosPopulares (){

        return null;
    }

    private double calcularSimilitudContenido (Contenido contenido, ArrayList<String> preferencias){

        return 0;
    }
}
