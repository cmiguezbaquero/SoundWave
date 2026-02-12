package utilidades;

import enums.CategoriaPodcast;
import modelo.artistas.Creador;
import modelo.contenido.Podcast;

import java.util.HashMap;

public class EstadisticasCreador {
    private Creador creador;
    private int totalEpisodios;
    private int totalReproducciones;
    private double promedioReproducciones;
    private int totalSuscriptores;
    private int totalLikes;
    private int duracionTotalSegundos;
    private Podcast episodioMasPopular;
    private HashMap<Integer, Integer> episodiosPorTemporada;

    public EstadisticasCreador(Creador creador) {
        this.creador = creador;
        this.episodiosPorTemporada = new HashMap<>();
        calcularEstadisticas();
    }

    private void calcularEstadisticas () {
        totalEpisodios = 0;
        totalReproducciones = 0;
        duracionTotalSegundos = 0;
        episodioMasPopular = null;

        for (Podcast p : creador.getEpisodios()) {
            totalEpisodios++;
            totalReproducciones += p.getReproducciones();
            duracionTotalSegundos += p.getDuracionSegundos();

            if (episodioMasPopular == null ||
                    p.getReproducciones() > episodioMasPopular.getReproducciones()) {
                episodioMasPopular = p;
            }

            int temporada = p.getTemporada();
            episodiosPorTemporada.put(
                    temporada,
                    episodiosPorTemporada.getOrDefault(temporada, 0) + 1
            );
        }
        if (totalEpisodios > 0) {
            promedioReproducciones = (double) totalReproducciones / totalEpisodios;
        }

        totalSuscriptores = creador.getSuscriptores();
        totalLikes = creador.getTotalLikes();
    }

    private String formatearDuracion (int segundos){
        int minutos = segundos / 60;
        int restoSegundos = segundos % 60;
        return minutos + "m " + restoSegundos + "s";
    }

    public String generarReporte (){
        return "Estadísticas del creador: " + creador.getNombreCanal().toUpperCase() + "\n" +
                "Total episodios: " + totalEpisodios + "\n" +
                "Total reproducciones: " + totalReproducciones + "\n" +
                "Promedio reproducciones: " + String.format("%.2f", promedioReproducciones) + "\n" +
                "Suscriptores: " + totalSuscriptores + "\n" +
                "Likes totales: " + totalLikes + "\n" +
                "Duración total: " + formatearDuracion(duracionTotalSegundos) + "\n" +
                "Episodio más popular: " + (episodioMasPopular != null ? episodioMasPopular.getTitulo() : "N/A");
    }

    public double calcularEngagement (){
        if (totalSuscriptores == 0) return 0;
        return ((double) totalLikes / totalSuscriptores) * 100;
    }

    public int estimarCrecimientoMensual (){
        return (int) (calcularEngagement() * 0.1);
    }

    public Creador getCreador() {
        return creador;
    }

    public int getTotalEpisodios() {
        return totalEpisodios;
    }

    public int getTotalReproducciones() {
        return totalReproducciones;
    }

    public double getPromedioReproducciones() {
        return promedioReproducciones;
    }

    public int getTotalSuscriptores() {
        return totalSuscriptores;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    public int getDuracionTotalSegundos() {
        return duracionTotalSegundos;
    }

    public Podcast getEpisodioMasPopular() {
        return episodioMasPopular;
    }

    public HashMap<Integer, Integer> getEpisodiosPorTemporada () {
        return new HashMap<>(episodiosPorTemporada);
    }

    @Override
    public String toString() {
    return super.toString();
    }
}
