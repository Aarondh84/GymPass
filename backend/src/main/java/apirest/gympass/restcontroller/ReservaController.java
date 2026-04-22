package apirest.gympass.restcontroller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import apirest.gympass.entityDto.ReservaDTO;

import apirest.gympass.service.ReservaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ReservaController {
  private final ReservaService reservaService;

  @GetMapping("/usuario/{username}")
  public ResponseEntity<List<ReservaDTO>> getByUsuario(@PathVariable String username) {
    return ResponseEntity.ok(reservaService.findByUsername(username));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> cancelarReserva(@PathVariable Long id) {
    reservaService.cancelarReserva(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping
  public ResponseEntity<?> crearReserva(@RequestBody ReservaDTO reservaDTO) {
    try {
      return ResponseEntity.ok(reservaService.crearReserva(reservaDTO));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/plazas/{idEvento}")
  public ResponseEntity<Long> countReservasPorEvento(@PathVariable int idEvento) {
    return ResponseEntity.ok(reservaService.countReservasPorEvento(idEvento));
  }
}