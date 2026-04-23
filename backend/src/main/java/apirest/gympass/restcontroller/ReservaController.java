package apirest.gympass.restcontroller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import apirest.gympass.entityDto.ReservaDTO;
import apirest.gympass.service.ReservaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    // GET /api/reservas/usuario/{username}
    // Devuelve las reservas de un usuario concreto
    @GetMapping("/usuario/{username}")
    public ResponseEntity<List<ReservaDTO>> getByUsuario(@PathVariable String username) {
        return ResponseEntity.ok(reservaService.findByUsername(username));
    }

    // GET /api/reservas/plazas/{idEvento}
    // Devuelve cuantas reservas tiene un evento para calcular plazas disponibles
    @GetMapping("/plazas/{idEvento}")
    public ResponseEntity<Long> countReservasPorEvento(@PathVariable int idEvento) {
        return ResponseEntity.ok(reservaService.countReservasPorEvento(idEvento));
    }

    // POST /api/reservas
    // Crea una nueva reserva
    @PostMapping
    public ResponseEntity<?> crearReserva(@RequestBody ReservaDTO reservaDTO) {
        try {
            return ResponseEntity.ok(reservaService.crearReserva(reservaDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETE /api/reservas/{id}
    // Cancela una reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarReserva(@PathVariable Long id) {
        reservaService.cancelarReserva(id);
        return ResponseEntity.noContent().build();
    }
    
	 // GET /api/reservas — solo ROLE_ADMON
	 // Devuelve todas las reservas para el panel de administracion
	 @GetMapping
	 public ResponseEntity<List<ReservaDTO>> getAll() {
	     return ResponseEntity.ok(reservaService.findAll());
	 }
}