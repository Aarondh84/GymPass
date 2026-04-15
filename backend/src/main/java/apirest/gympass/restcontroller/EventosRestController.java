package apirest.gympass.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import apirest.gympass.entityDto.EventoDTO;
import apirest.gympass.service.EventoService;

@RestController
@RequestMapping("/api/eventos")
public class EventosRestController {
	@Autowired
	private EventoService eventoService;

//endpoints publicos de clientes
	@GetMapping("/activos")
    public List<EventoDTO> listarActivos() {
        // Devuelve eventos con estado "ACTIVO" 
        return eventoService.findByEstado(EstadoEvento.ACTIVO);
    }

    @GetMapping("/destacados")
    public List<EventoDTO> listarDestacados() {
        // Devuelve eventos donde destacado es 'S' 
        return eventoService.findByDestacado("S");
    }

    @GetMapping("/terminados")
    public List<EventoDTO> listarTerminados() {
        // Eventos que ya han pasado de fecha 
        return eventoService.findByEstado(EstadoEvento.TERMINADO);
    }

    @GetMapping("/cancelados")
    public List<EventoDTO> listarCancelados() {
        // Eventos anulados por la administración 
        return eventoService.findByEstado(EstadoEvento.CANCELADO);
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<EventoDTO> verDetalle(@PathVariable int id) {
        // Muestra todos los datos del evento seleccionado 
        EventoDTO evento = eventoService.findById(id);
        return ResponseEntity.ok(evento);
    }

//endpoints admin

    @PostMapping("/alta")
    public ResponseEntity<EventoDTO> altaEvento(@RequestBody EventoDTO eventoDTO) {
        // Crea el evento con estado inicial "ACTIVO" 
        EventoDTO nuevo = eventoService.save(eventoDTO);
        return ResponseEntity.status(201).body(nuevo);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<EventoDTO> editarEvento(@PathVariable int id, @RequestBody EventoDTO eventoDTO) {
        // Modifica los datos del evento en la base de datos 
        EventoDTO editado = eventoService.save(eventoDTO);
        return ResponseEntity.ok(editado);
    }

    @PatchMapping("/cancelar/{id}")
    public ResponseEntity<Void> cancelarEvento(@PathVariable int id) {
        // Solo cambiamos el estado a CANCELADO 
        eventoService.cancelarEvento(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarEvento(@PathVariable int id) {
        // Eliminación física del registro
        eventoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}