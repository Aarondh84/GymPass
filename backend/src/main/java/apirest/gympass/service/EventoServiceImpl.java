package apirest.gympass.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apirest.gympass.entity.EstadoEvento;
import apirest.gympass.entity.Evento;
import apirest.gympass.entityDto.EventoDTO;
import apirest.gympass.repository.EventoRepository;
import apirest.gympass.repository.TipoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventoServiceImpl implements EventoService {

    @Autowired
    private EventoRepository eventoRepo;
    @Autowired
    private TipoRepository tipoRepo;

    // 1. CONSULTAS (Siempre devolviendo DTO para el controlador)
    
    @Override
    public List<EventoDTO> findByEstado(EstadoEvento estado) {
        // Obtenemos entidades y las transformamos a DTOs para Angular 
        return eventoRepo.findByEstado(estado).stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public List<EventoDTO> findByDestacado(String destacado) {
        return eventoRepo.findByDestacado(destacado).stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public EventoDTO findById(int id) {
        Evento evento = eventoRepo.findById(id).orElse(null);
        return (evento != null) ? convertToDto(evento) : null;
    }

    // 2. GESTIÓN (Alta y Modificación)

    @Override
    public EventoDTO save(EventoDTO dto) {
        // Convertimos la "maleta" (DTO) a Entidad para JPA 
        Evento evento = convertToEntity(dto);
        if (evento.getIdEvento() == 0) {
            evento.setIdEvento(null); 
        }
       

        // al dar de alta el estado es ACTIVO 
        if (evento.getIdEvento() == null || evento.getEstado() == null) {
            evento.setEstado(EstadoEvento.ACTIVO);
        }
        
        if (evento.getDestacado() == null) {
            evento.setDestacado("N");
        }

        Evento guardado = eventoRepo.save(evento);
        return convertToDto(guardado);
    }

    @Override
    public void cancelarEvento(int id) {
        Evento evento = eventoRepo.findById(id).orElse(null);
        if (evento != null) {
            // No borramos, cambiamos estado a CANCELADO 
            evento.setEstado(EstadoEvento.CANCELADO); 
            eventoRepo.save(evento);
        }
    }

    
    @Override
    public void delete(int id) {
        try {
            // Intentamos el borrado físico
            eventoRepo.deleteById(id);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            // Si falla porque tiene reservas vinculadas, aplicamos "borrado lógico" (cancelar)
            System.out.println("No se puede eliminar físicamente: existen reservas vinculadas. Cancelando evento...");
            cancelarEvento(id);
        } catch (Exception e) {
            System.err.println("Error inesperado al eliminar: " + e.getMessage());
        }
    }

    // 3. LÓGICA DE NEGOCIO (Validaciones críticas del PDF)

    public String gestionarReservas(Evento evento, int cantidad) {
        // Regla: máximo 10 personas por reserva 
        if (cantidad > 10) {
            return "No se permiten mas de 10 personas por reserva";
        }
        // Regla: no superar el aforo máximo 
        if (cantidad > evento.getAforoMaximo()) {
            return "Se supera la cantidad de aforo máximo";
        }
        return "Reserva realizada con éxito";
    }

    // 4. MÉTODOS DE CONVERSIÓN (Privados)

    private EventoDTO convertToDto(Evento evento) {
        EventoDTO dto = new EventoDTO();
        dto.setIdEvento(evento.getIdEvento());
        dto.setNombre(evento.getNombre());
        dto.setPrecio(evento.getPrecio());
        dto.setAforoMaximo(evento.getAforoMaximo());
        dto.setFechaInicio(evento.getFechaInicio());
        dto.setEstado(evento.getEstado() != null ? evento.getEstado().name() : null);
        
        // Si el evento tiene tipo, pasamos el ID o nombre al DTO 
        if (evento.getTipo() != null) {
            dto.setIdTipo(evento.getTipo().getIdTipo());
        }
        return dto;
    }

    private Evento convertToEntity(EventoDTO dto) {
    	Evento evento = new Evento();
        
        // Si el DTO trae un id, lo ponemos. Si es 0, lo dejamos tal cual (el save lo limpiará)
        evento.setIdEvento(dto.getIdEvento());
        
        evento.setNombre(dto.getNombre());
        evento.setPrecio(dto.getPrecio());
        evento.setAforoMaximo(dto.getAforoMaximo());
        evento.setFechaInicio(dto.getFechaInicio());
        
        // Asegúrate de que el estado también se mapee de vuelta si viene en el DTO
        if (dto.getEstado() != null) {
            evento.setEstado(EstadoEvento.valueOf(dto.getEstado()));
        }
        
        if (dto.getIdTipo() != 0) {
            evento.setTipo(tipoRepo.findById(dto.getIdTipo()).orElse(null));
        }
        return evento;
    }


}
