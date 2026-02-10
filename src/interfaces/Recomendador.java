package interfaces;

import excepciones.recomendacion.RecomendacionException;
import modelo.contenido.Contenido;
import modelo.usuarios.Usuario;

import java.util.ArrayList;
import java.util.Collection;

public interface Recomendador {

    ArrayList<Contenido> recomendar (Usuario usuario) throws RecomendacionException;
    ArrayList<Contenido> obtenerSimilares (Contenido contenido) throws RecomendacionException;

}
