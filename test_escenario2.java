import enums.CategoriaPodcast;
import enums.GeneroMusical;
import excepciones.artista.ArtistaNoVerificadoException;
import modelo.artistas.Album;
import modelo.artistas.Artista;
import modelo.artistas.Creador;
import modelo.contenido.Cancion;
import modelo.contenido.Podcast;
import modelo.plataforma.Plataforma;

import java.util.Date;

public class test_escenario2 {
    public static void main(String[] args) {
        try {
            // Setup
            Plataforma.reiniciarInstancia();
            Plataforma plataforma = Plataforma.getInstancia("SoundWave Test");
            System.out.println("✓ Plataforma creada");

            // Test 1: Crear artistas verificados
            Artista badBunny = plataforma.registrarArtista("Bad Bunny", "Benito Martínez", "Puerto Rico", true);
            Artista shakira = plataforma.registrarArtista("Shakira", "Shakira Mebarak", "Colombia", true);
            Artista taylor = plataforma.registrarArtista("Taylor Swift", "Taylor Swift", "USA", true);
            System.out.println("✓ 3 artistas verificados registrados");
            System.out.println("  Artistas verificados: " + plataforma.getArtistasVerificados().size());

            // Test 2: Crear artistas no verificados
            Artista artistaEmer = plataforma.registrarArtista("Artista Emergente", "Juan Nuevo", "México", false);
            Artista nuevoTalento = plataforma.registrarArtista("Nuevo Talento", "María Nueva", "España", false);
            System.out.println("✓ 2 artistas no verificados registrados");
            System.out.println("  Artistas no verificados: " + plataforma.getArtistasNoVerificados().size());

            // Test 3: Crear álbum y canciones
            Album album = plataforma.crearAlbum(badBunny, "Un Verano Sin Ti", new Date());
            System.out.println("✓ Álbum creado: " + album.getTitulo());

            Cancion c1 = album.crearCancion("Moscow Mule", 200, GeneroMusical.REGGAETON);
            Cancion c2 = album.crearCancion("Después de la Playa", 210, GeneroMusical.REGGAETON);
            Cancion c3 = album.crearCancion("Me Porto Bonito", 195, GeneroMusical.REGGAETON);
            plataforma.agregarContenidoCatalogo(c1);
            plataforma.agregarContenidoCatalogo(c2);
            plataforma.agregarContenidoCatalogo(c3);
            System.out.println("✓ 3 canciones creadas en álbum");
            System.out.println("  Canciones en álbum: " + album.getNumCanciones());
            System.out.println("  Canciones en catálogo: " + plataforma.getCanciones().size());

            // Test 4: Artista no verificado no puede crear álbum
            try {
                plataforma.crearAlbum(artistaEmer, "Mi Primer Álbum", new Date());
                System.out.println("✗ Error: Artista no verificado pudo crear álbum");
            } catch (ArtistaNoVerificadoException e) {
                System.out.println("✓ Artista no verificado no puede crear álbum");
            }

            // Test 5: Crear creadores
            Creador creador1 = plataforma.registrarCreador("The Wild Project", "Jordi Wild", "Podcast de entrevistas");
            creador1.setSuscriptores(2500000);
            Creador creador2 = plataforma.registrarCreador("Leyendas Legendarias", "Bsjf", "Podcast de crímenes");
            creador2.setSuscriptores(1800000);
            System.out.println("✓ 2 creadores registrados");
            System.out.println("  Creadores: " + plataforma.getTodosLosCreadores().size());

            // Test 6: Crear podcasts
            for (int i = 1; i <= 3; i++) {
                Podcast p = plataforma.crearPodcast("Episodio " + i, 3600, creador1, i, 1, CategoriaPodcast.ENTRETENIMIENTO);
                System.out.println("  ✓ Podcast " + i + " creado");
            }
            System.out.println("✓ Podcasts creados");
            System.out.println("  Podcasts en catálogo: " + plataforma.getPodcasts().size());
            System.out.println("  Episodios de creador1: " + creador1.getNumEpisodios());

        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            e.printStackTrace();
        }
    }
}

