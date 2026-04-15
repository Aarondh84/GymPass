package apirest.gympass.restcontroller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import apirest.gympass.entity.EstadoEvento;
import apirest.gympass.entity.Evento;
import apirest.gympass.entityDto.EventoDTO;
import apirest.gympass.service.EventoService;

@RestController
@RequestMapping("/api/eventos") 
@CrossOrigin(origins = "http://localhost:4200")
public class EventosRestController {

    @Autowired
    private EventoService eventoService;

    // Ver eventos activos (PÚBLICO)
    @GetMapping("/activos")
    public List<Evento> getActivos() {
        return eventoService.findByEstado(EstadoEvento.ACTIVO);
    }

    // Ver eventos destacados (PÚBLICO)
    @GetMapping("/destacados")
    public List<Evento> getDestacados() {
        return eventoService.findByDestacado("S");
    }

    // Ver un evento por su ID
    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> getUno(@PathVariable int id) {
        EventoDTO dto = eventoService.findById(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    // 4. Crear o editar un evento (SOLO ADMIN en SecurityConfig)
    @PostMapping("/alta")
    public ResponseEntity<EventoDTO> crear(@RequestBody EventoDTO eventoDTO) {
        return new ResponseEntity<>(eventoService.save(eventoDTO), HttpStatus.CREATED);
    }

    // Cancelar un evento (Cambiamos estado, no borramos)
    @PutMapping("/cancelar/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable int id) {
        eventoService.cancelarEvento(id);
        return ResponseEntity.noContent().build();
    }

    // Eliminar físicamente un evento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        eventoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}