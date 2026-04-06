package apirest.gympass.restcontroller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apirest.gympass.entityDto.UsuarioDTO;
import apirest.gympass.service.UsuarioService;
import lombok.RequiredArgsConstructor;

// Controlador que gestiona las operaciones sobre usuarios
// Solo accesible para administradores (ver SecurityConfig)
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // GET /api/usuarios
    // Devuelve la lista de usuarios sin contraseña
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    // PATCH /api/usuarios/juan/estado
    // Activa o desactiva el usuario y devuelve el nuevo estado
    @PatchMapping("/{username}/estado")
    public ResponseEntity<?> userEnabled(@PathVariable String username) {
        boolean activo = usuarioService.userEnabled(username);
        return ResponseEntity.ok(activo);
    }

    // DELETE /api/usuarios/juan
    // Elimina el usuario — devuelve 204 sin contenido
    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteByUsername(@PathVariable String username) {
        usuarioService.deleteByUsername(username);
        return ResponseEntity.noContent().build();
    }
}