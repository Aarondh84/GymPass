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
@RequestMapping("/eventos") 
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
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

    @GetMapping("/detalle/{id}") 
    public ResponseEntity<EventoDTO> getById(@PathVariable Integer id) {
        EventoDTO dto = eventoService.findById(id);
        return (dto != null) ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    // --- ADMINISTRACIÓN (ROLE_ADMON) 

    @PostMapping("/alta") 
    public ResponseEntity<EventoDTO> crear(@RequestBody EventoDTO eventoDto) {
        // Al crear, el servicio pondrá el estado ACTIVO por defecto 
        return new ResponseEntity<>(eventoService.save(eventoDto), HttpStatus.CREATED);
    }

    @PutMapping("/editar/{id}") 
    public ResponseEntity<EventoDTO> editar(@PathVariable Integer id, @RequestBody EventoDTO eventoDto) {
        eventoDto.setIdEvento(id);
        return ResponseEntity.ok(eventoService.save(eventoDto));
    }

    @PatchMapping("/cancelar/{id}") 
    public ResponseEntity<Void> cancelar(@PathVariable Integer id) {
        eventoService.cancelarEvento(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/eliminar/{id}") 
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        eventoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}