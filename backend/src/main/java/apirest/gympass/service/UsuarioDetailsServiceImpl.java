package apirest.gympass.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import apirest.gympass.entity.Usuario;
import apirest.gympass.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

// Spring Security llama a esta clase cada vez que alguien intenta autenticarse
// Necesita saber cómo cargar un usuario desde nuestra base de datos
@Service
@RequiredArgsConstructor
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    // Spring Security llama a este método con el username del formulario de login
    // Tiene que devolver un UserDetails con username, password y roles
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        // Buscamos el usuario en la BD — si no existe lanzamos la excepción
        Usuario usuario = usuarioRepository.findById(username)
            .orElseThrow(() -> new UsernameNotFoundException(
                "Usuario no encontrado: " + username));

        // Convertimos los perfiles del usuario (ROLE_ADMON, ROLE_CLIENTE)
        // al formato que entiende Spring Security (SimpleGrantedAuthority)
        var authorities = usuario.getPerfiles().stream()
            .map(p -> new SimpleGrantedAuthority(p.getNombre()))
            .toList();

        // User es la implementación de UserDetails que viene con Spring Security
        // Le pasamos: username, password cifrado, si está activo y sus roles
        return new User(
            usuario.getUsername(),
            usuario.getPassword(),
            usuario.getEnabled() == 1,  // true si enabled=1, false si enabled=0
            true,                        // cuenta no expirada
            true,                        // credenciales no expiradas
            true,                        // cuenta no bloqueada
            authorities
        );
    }
}