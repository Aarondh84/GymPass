package apirest.gympass.restcontroller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apirest.gympass.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

// Controlador para el panel de administracion
// Por ahora solo tiene el endpoint de estadisticas del dashboard
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    // Usamos el repository directamente porque es una consulta simple
    // Si hubiera mas logica lo pasariamos por un service
    private final UsuarioRepository usuarioRepository;

    // GET /api/admin/stats
    // Devuelve un resumen de estadisticas para el dashboard
    // Los datos de eventos y reservas los completara el compañero
    // cuando tenga sus repositorios listos
    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {

        long totalUsuarios = usuarioRepository.count();

        // Devolvemos un Map con los datos — el frontend espera estos campos:
        // totalEventos, totalActivos, totalReservas, totalUsuarios
        return ResponseEntity.ok(Map.of(
            "totalEventos",  0,
            "totalActivos",  0,
            "totalReservas", 0,
            "totalUsuarios", totalUsuarios
        ));
    }
}