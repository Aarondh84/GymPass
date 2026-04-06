package apirest.gympass.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import apirest.gympass.entity.Perfil;
import apirest.gympass.entity.Usuario;
import apirest.gympass.entityDto.LoginRequestDTO;
import apirest.gympass.entityDto.LoginResponseDTO;
import apirest.gympass.entityDto.RegistroRequestDTO;
import apirest.gympass.repository.PerfilRepository;
import apirest.gympass.repository.UsuarioRepository;
import apirest.gympass.security.JwtSecurityService;
import lombok.RequiredArgsConstructor;

// Este servicio gestiona todo lo relacionado con autenticacion:
// login (comprobar credenciales y devolver token) y registro (crear cuenta nueva)
@Service
@RequiredArgsConstructor
public class AuthService {

    // Componente de Spring Security que valida usuario y contraseña
    private final AuthenticationManager authenticationManager;

    // Para buscar y guardar usuarios en la BD
    private final UsuarioRepository usuarioRepository;

    // Para buscar el perfil ROLE_CLIENTE al registrar un usuario nuevo
    private final PerfilRepository perfilRepository;

    // Para cifrar la contraseña antes de guardarla
    private final PasswordEncoder passwordEncoder;

    // Para generar el token JWT tras el login
    private final JwtSecurityService jwtService;

    // LOGIN — comprueba credenciales y devuelve token + datos del usuario
    public LoginResponseDTO login(LoginRequestDTO request) {

        // Le pedimos a Spring Security que compruebe usuario y contraseña
        // Si son incorrectos lanza una excepcion automaticamente
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );

        // Si llegamos aqui es porque las credenciales son correctas
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Generamos el token JWT con el username y sus roles
        String token = jwtService.generateToken(
            userDetails.getUsername(),
            userDetails.getAuthorities()
        );

        // Extraemos los roles como lista de strings para el frontend
        List<String> roles = userDetails.getAuthorities().stream()
            .map(a -> a.getAuthority())
            .toList();

        // Devolvemos token, username y roles — exactamente lo que espera el frontend
        return new LoginResponseDTO(token, userDetails.getUsername(), roles);
    }

    // REGISTRO — crea una cuenta nueva con perfil ROLE_CLIENTE
    public void registro(RegistroRequestDTO request) {

        // Comprobamos que el username no exista ya en la BD
        if (usuarioRepository.existsById(request.getUsername())) {
            throw new RuntimeException("El usuario ya existe.");
        }

        // Buscamos el perfil ROLE_CLIENTE en la BD
        Perfil perfilCliente = perfilRepository.findByNombre("ROLE_CLIENTE")
            .orElseThrow(() -> new RuntimeException("Perfil ROLE_CLIENTE no encontrado."));

        // Creamos el usuario nuevo con los datos del formulario
        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setEmail(request.getEmail());
        usuario.setNombre(request.getNombre());
        usuario.setApellidos(request.getApellidos());
        usuario.setEnabled(1);
        usuario.setFechaRegistro(LocalDate.now());
        usuario.setPerfiles(Set.of(perfilCliente));

        // Guardamos el usuario en la BD
        usuarioRepository.save(usuario);
    }
}