package apirest.gympass.restcontroller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    return ResponseEntity.ok(eventoService.findByDestacado("S"));
  }

  @GetMapping("/activos")
  public ResponseEntity<List<Evento>> getActivos() {
    return ResponseEntity.ok(eventoService.findByEstado(EstadoEvento.ACTIVO));
  }

  @PostMapping
  public ResponseEntity<Evento> crear(@RequestBody Evento evento) {
    Evento nuevoEvento = eventoService.guardar(evento);
    return ResponseEntity.ok(nuevoEvento);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Evento> editar(@PathVariable Integer id, @RequestBody Evento evento) {
    evento.setIdEvento(id);
    Evento actualizado = eventoService.guardar(evento);
    return ResponseEntity.ok(actualizado);
  }

  @PatchMapping("/cancelar/{id}")
  public ResponseEntity<Evento> cancelar(@PathVariable Integer id) {
    Evento evento = eventoService.findById(id);
    if (evento == null) {
      return ResponseEntity.notFound().build();
    }
    evento.setEstado(EstadoEvento.CANCELADO);
    return ResponseEntity.ok(eventoService.guardar(evento));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
    eventoService.eliminar(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/cancelados")
  public ResponseEntity<List<Evento>> getCancelados() {
    return ResponseEntity.ok(eventoService.findByEstado(EstadoEvento.CANCELADO));
  }

  @GetMapping("/terminados")
  public ResponseEntity<List<Evento>> getTerminados() {
    return ResponseEntity.ok(eventoService.findByEstado(EstadoEvento.TERMINADO));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Evento> getById(@PathVariable Integer id) {
    Evento evento = eventoService.findById(id);
    if (evento == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(evento);
  }
}