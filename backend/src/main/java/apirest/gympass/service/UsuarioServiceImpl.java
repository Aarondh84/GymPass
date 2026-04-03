package apirest.gympass.service;

import java.util.List;

import org.springframework.stereotype.Service;

import apirest.gympass.entity.Usuario;
import apirest.gympass.repository.UsuarioRepository;

// @Service le dice a Spring Boot que esta clase es un servicio
// y que la gestione automáticamente
@Service
public class UsuarioServiceImpl implements UsuarioService {

    // El repository es el que habla con la base de datos
    private final UsuarioRepository usuarioRepository;

    // Constructor — Spring Boot inyecta el repository automáticamente
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
 // Devuelve todos los usuarios — findAll() ya existe en JpaRepository
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    // Elimina un usuario por su username
    // deleteById() ya existe en JpaRepository
    // Si el usuario no existe, JpaRepository lanza una excepción automáticamente
    public void deleteByUsername(String username) {
        usuarioRepository.deleteById(username);
    }

    // Cambia el estado activo/inactivo del usuario
    // 1. Buscamos el usuario — findById devuelve un Optional porque puede no existir
    // 2. Si no existe lanzamos una excepción con un mensaje claro
    // 3. Invertimos el enabled (si era 1 pasa a 0, si era 0 pasa a 1)
    // 4. Guardamos los cambios en la BD
    // 5. Devolvemos el nuevo estado como boolean
    public boolean userEnabled(String username) {

        // findById devuelve Optional — puede tener valor o estar vacío
        Usuario usuario = usuarioRepository.findById(username)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Si enabled es 1 lo ponemos a 0, y al revés
        int nuevoEstado = usuario.getEnabled() == 1 ? 0 : 1;
        usuario.setEnabled(nuevoEstado);

        // Guardamos el usuario modificado en la BD
        usuarioRepository.save(usuario);

        // Devolvemos true si quedó activo, false si quedó inactivo
        return nuevoEstado == 1;
    }
}