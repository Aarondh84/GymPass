package apirest.gympass.restcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apirest.gympass.entityDto.LoginRequestDTO;
import apirest.gympass.entityDto.LoginResponseDTO;
import apirest.gympass.entityDto.RegistroRequestDTO;
import apirest.gympass.service.AuthService;
import lombok.RequiredArgsConstructor;

// Controlador que gestiona el login y el registro
// Es publico — no requiere token para acceder (ver SecurityConfig)
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // POST /api/auth/login
    // Recibe username y password, devuelve token + username + roles
    // @RequestBody indica que los datos vienen en el cuerpo de la peticion en formato JSON
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    // POST /api/auth/registro
    // Recibe los datos del formulario de registro y crea la cuenta
    @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody RegistroRequestDTO request) {
        authService.registro(request);
        return ResponseEntity.ok().build();
    }
}
