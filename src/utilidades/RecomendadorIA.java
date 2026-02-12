package utilidades;

import enums.AlgoritmoRecomendacion;
import excepciones.recomendacion.RecomendacionException;
import excepciones.recomendacion.HistorialVacioException;
import excepciones.recomendacion.ModeloNoEntrenadoException;
import interfaces.Recomendador;
import modelo.contenido.Contenido;
import modelo.contenido.Cancion;
import modelo.contenido.Podcast;
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
        this.matrizPreferencias = new HashMap<>();
        this.historialCompleto = new HashMap<>();
        this.modeloEntrenado = false;
        this.umbralSimilar = UMBRAL_DEFAULT;
        this.algoritmo = AlgoritmoRecomendacion.CONTENIDO;
        this.catalogoReferencia = new ArrayList<>();
    }

    public RecomendadorIA(AlgoritmoRecomendacion algoritmo) {
        this();
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
        this.catalogoReferencia = new ArrayList<>(catalogo);
    }

    public ArrayList<Contenido> recomendar (Usuario usuario) throws RecomendacionException {
        if (!modeloEntrenado) {
            throw new ModeloNoEntrenadoException("El modelo no ha sido entrenado");
        }

        ArrayList<Contenido> historial = usuario.getHistorial();
        if (historial.isEmpty()) {
            throw new HistorialVacioException("El usuario no tiene historial");
        }

        ArrayList<Contenido> recomendaciones = new ArrayList<>();

        // Obtener géneros y categorías del historial
        HashMap<String, Integer> generos = new HashMap<>();
        for (Contenido c : historial) {
            if (c instanceof Cancion) {
                Cancion cancion = (Cancion) c;
                String genero = cancion.getGenero().toString();
                generos.put(genero, generos.getOrDefault(genero, 0) + 1);
            }
        }

        // Recomendar contenido similar que NO esté en el historial
        for (Contenido contenido : catalogoReferencia) {
            if (!historial.contains(contenido)) {
                if (contenido instanceof Cancion) {
                    Cancion cancion = (Cancion) contenido;
                    if (generos.containsKey(cancion.getGenero().toString())) {
                        recomendaciones.add(contenido);
                    }
                }
            }
        }

        return recomendaciones;
    }

    public ArrayList<Contenido> obtenerSimilares (Contenido contenido) throws RecomendacionException{
        ArrayList<Contenido> similares = new ArrayList<>();

        if (contenido instanceof Cancion) {
            Cancion cancion = (Cancion) contenido;
            for (Contenido c : catalogoReferencia) {
                if (c instanceof Cancion && !c.equals(contenido)) {
                    Cancion otra = (Cancion) c;
                    if (otra.getGenero().equals(cancion.getGenero())) {
                        similares.add(c);
                    }
                }
            }
        } else if (contenido instanceof Podcast) {
            Podcast podcast = (Podcast) contenido;
            for (Contenido c : catalogoReferencia) {
                if (c instanceof Podcast && !c.equals(contenido)) {
                    Podcast otro = (Podcast) c;
                    if (otro.getCategoria().equals(podcast.getCategoria())) {
                        similares.add(c);
                    }
                }
            }
        }

        return similares;
    }

    public void entrenarModelo (ArrayList<Usuario> usuarios) {
        if (usuarios == null || usuarios.isEmpty()) {
            this.modeloEntrenado = false;
            return;
        }

        ArrayList<Usuario> usuariosConHistorial = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (!u.getHistorial().isEmpty()) {
                usuariosConHistorial.add(u);
            }
        }

        if (usuariosConHistorial.isEmpty()) {
            this.modeloEntrenado = false;
            return;
        }

        this.modeloEntrenado = true;
    }

    public void entrenarModelo (ArrayList<Usuario> usuarios, ArrayList<Contenido> catalogo){
        this.catalogoReferencia = new ArrayList<>(catalogo);
        entrenarModelo(usuarios);
    }

    public double calcularSimilitud (Usuario u1, Usuario u2){
        ArrayList<Contenido> historial1 = u1.getHistorial();
        ArrayList<Contenido> historial2 = u2.getHistorial();

        if (historial1.isEmpty() || historial2.isEmpty()) {
            return 0.0;
        }

        int contenidoComun = 0;
        for (Contenido c1 : historial1) {
            for (Contenido c2 : historial2) {
                if (c1.equals(c2)) {
                    contenidoComun++;
                    break;
                }
            }
        }

        int totalContenido = Math.max(historial1.size(), historial2.size());
        return (double) contenidoComun / totalContenido;
    }

    public void actualizarPreferencias (Usuario usuario){
        ArrayList<Contenido> historial = usuario.getHistorial();
        ArrayList<String> preferencias = new ArrayList<>();

        for (Contenido c : historial) {
            if (c instanceof Cancion) {
                Cancion cancion = (Cancion) c;
                preferencias.add(cancion.getGenero().toString());
            }
        }

        matrizPreferencias.put(usuario.getId(), preferencias);
    }

    public HashMap<String, Integer> obtenerGenerosPopulares (){
        HashMap<String, Integer> generosCount = new HashMap<>();

        for (Contenido contenido : catalogoReferencia) {
            if (contenido instanceof Cancion) {
                Cancion cancion = (Cancion) contenido;
                String genero = cancion.getGenero().toString();
                generosCount.put(genero, generosCount.getOrDefault(genero, 0) + 1);
            }
        }

        return generosCount;
    }

    private double calcularSimilitudContenido (Contenido contenido, ArrayList<String> preferencias){

        return 0;
    }
}
