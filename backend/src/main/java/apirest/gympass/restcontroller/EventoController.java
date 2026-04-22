package apirest.gympass.restcontroller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import apirest.gympass.entity.EstadoEvento;
import apirest.gympass.entityDto.EventoDTO;
import apirest.gympass.service.EventoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

    // --- PÚBLICO / CLIENTES ---

    @GetMapping("/activos")
    public ResponseEntity<List<EventoDTO>> getActivos() {
        return ResponseEntity.ok(eventoService.findByEstado(EstadoEvento.ACTIVO));
    }

    @GetMapping("/destacados")
    public ResponseEntity<List<EventoDTO>> getDestacados() {
        return ResponseEntity.ok(eventoService.findByDestacado("S"));
    }

    @GetMapping("/cancelados")
    public ResponseEntity<List<EventoDTO>> getCancelados() {
        return ResponseEntity.ok(eventoService.findByEstado(EstadoEvento.CANCELADO));
    }

    @GetMapping("/terminados")
    public ResponseEntity<List<EventoDTO>> getTerminados() {
        return ResponseEntity.ok(eventoService.findByEstado(EstadoEvento.TERMINADO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> getById(@PathVariable Integer id) {
        EventoDTO dto = eventoService.findById(id);
        return (dto != null) ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    // --- ADMINISTRACIÓN (ROLE_ADMON) ---

    @PostMapping
    public ResponseEntity<EventoDTO> crear(@RequestBody EventoDTO eventoDto) {
        return new ResponseEntity<>(eventoService.save(eventoDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoDTO> editar(@PathVariable Integer id, @RequestBody EventoDTO eventoDto) {
        eventoDto.setIdEvento(id);
        return ResponseEntity.ok(eventoService.save(eventoDto));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Integer id) {
        eventoService.cancelarEvento(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        eventoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}