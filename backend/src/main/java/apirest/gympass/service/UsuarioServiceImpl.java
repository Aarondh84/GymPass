package apirest.gympass.service;

import java.util.List;

import org.springframework.stereotype.Service;

import apirest.gympass.entity.Usuario;
import apirest.gympass.entityDto.UsuarioDTO;
import apirest.gympass.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

// Implementacion del servicio de usuarios
// Aqui esta la logica real de cada operacion
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    // El repository es el que habla con la base de datos
    private final UsuarioRepository usuarioRepository;

    // Obtiene todos los usuarios y los convierte a DTO para no exponer la contraseña
    @Override
    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll().stream()
            .map(this::convertirADto)
            .toList();
    }

    // Cambia el estado del usuario — si estaba activo lo desactiva y al reves
    @Override
    public boolean userEnabled(String username) {

        // Buscamos el usuario — si no existe lanzamos error
        Usuario usuario = usuarioRepository.findById(username)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Invertimos el estado: 1 pasa a 0, 0 pasa a 1
        int nuevoEstado = usuario.getEnabled() == 1 ? 0 : 1;
        usuario.setEnabled(nuevoEstado);

        // Guardamos el cambio en la BD
        usuarioRepository.save(usuario);

        // Devolvemos true si quedo activo, false si quedo inactivo
        return nuevoEstado == 1;
    }

    // Elimina un usuario por su username
    @Override
    public void deleteByUsername(String username) {
        usuarioRepository.deleteById(username);
    }

    // Metodo privado que convierte una entidad Usuario a UsuarioDTO
    // Lo usamos para no repetir esta logica en cada metodo
    private UsuarioDTO convertirADto(Usuario usuario) {
        // Extraemos solo los nombres de los perfiles
        List<String> nombrePerfiles = usuario.getPerfiles().stream()
            .map(p -> p.getNombre())
            .toList();

        return new UsuarioDTO(
            usuario.getUsername(),
            usuario.getNombre(),
            usuario.getApellidos(),
            usuario.getEmail(),
            usuario.getEnabled(),
            nombrePerfiles
        );
    }
}