import enums.TipoSuscripcion;
import excepciones.plataforma.UsuarioYaExisteException;
import excepciones.usuario.EmailInvalidoException;
import excepciones.usuario.PasswordDebilException;
import modelo.plataforma.Plataforma;
import modelo.usuarios.UsuarioPremium;
import modelo.usuarios.UsuarioGratuito;

public class test_simple {
    public static void main(String[] args) {
        try {
            // Test 1: Crear plataforma
            Plataforma.reiniciarInstancia();
            Plataforma plataforma = Plataforma.getInstancia("SoundWave Test");
            System.out.println("✓ Plataforma creada: " + plataforma.getNombre());

            // Test 2: Registrar usuario premium válido
            UsuarioPremium user1 = plataforma.registrarUsuarioPremium(
                "Juan Pérez", "juan@gmail.com", "password123", TipoSuscripcion.PREMIUM);
            System.out.println("✓ Usuario premium registrado: " + user1.getNombre());

            // Test 3: Registrar usuario gratuito válido
            UsuarioGratuito user2 = plataforma.registrarUsuarioGratuito(
                "Pedro Sánchez", "pedro@yahoo.com", "freeuser123");
            System.out.println("✓ Usuario gratuito registrado: " + user2.getNombre());

            // Test 4: Intentar email inválido
            try {
                plataforma.registrarUsuarioPremium("Test", "emailsinArroba", "password123");
                System.out.println("✗ Email inválido no fue detectado!");
            } catch (EmailInvalidoException e) {
                System.out.println("✓ Email inválido detectado correctamente");
            }

            // Test 5: Intentar password débil
            try {
                plataforma.registrarUsuarioGratuito("Test", "test@gmail.com", "123");
                System.out.println("✗ Password débil no fue detectado!");
            } catch (PasswordDebilException e) {
                System.out.println("✓ Password débil detectado correctamente");
            }

            // Test 6: Intentar duplicado
            try {
                plataforma.registrarUsuarioPremium("Duplicado", "juan@gmail.com", "password123");
                System.out.println("✗ Usuario duplicado no fue detectado!");
            } catch (UsuarioYaExisteException e) {
                System.out.println("✓ Usuario duplicado detectado correctamente");
            }

            // Test 7: Contar usuarios
            System.out.println("✓ Total usuarios: " + plataforma.getTodosLosUsuarios().size());
            System.out.println("✓ Usuarios premium: " + plataforma.getUsuariosPremium().size());
            System.out.println("✓ Usuarios gratuitos: " + plataforma.getUsuariosGratuitos().size());

        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            e.printStackTrace();
        }
    }
}

