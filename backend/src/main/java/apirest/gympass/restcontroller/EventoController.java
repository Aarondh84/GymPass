package apirest.gympass.restcontroller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apirest.gympass.entity.EstadoEvento;
import apirest.gympass.entity.Evento;
import apirest.gympass.service.EventoService;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class EventoController {
  private final EventoService eventoService;

  @GetMapping("/destacados")
  public ResponseEntity<List<Evento>> getDestacados() {
    List<Evento> eventosDestacados = eventoService.findByDestacado("SI");
    return ResponseEntity.ok(eventoService.findByDestacado("S"));
  }

  @GetMapping("/activos")
  public ResponseEntity<List<Evento>> getActivos() {
    return ResponseEntity.ok(eventoService.findByEstado(EstadoEvento.ACTIVO));
  }
}