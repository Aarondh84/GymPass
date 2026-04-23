package apirest.gympass.service;

import java.util.List;
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

    private final EventoRepository eventoRepo;
    private final TipoRepository tipoRepo;

    @Override
    public List<EventoDTO> findByEstado(EstadoEvento estado) {
        return eventoRepo.findByEstado(estado).stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public List<EventoDTO> findByDestacado(String destacado) {
        // Solo mostramos destacados que esten ACTIVOS
        return eventoRepo.findByDestacadoAndEstado(destacado, EstadoEvento.ACTIVO).stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public EventoDTO findById(int id) {
        Evento evento = eventoRepo.findById(id).orElse(null);
        return (evento != null) ? convertToDto(evento) : null;
    }

    @Override
    public EventoDTO save(EventoDTO dto) {
        Evento evento = convertToEntity(dto);

        // Si el id es 0 es un alta nueva — dejamos que Hibernate genere el id
        if (evento.getIdEvento() != null && evento.getIdEvento() == 0) {
            evento.setIdEvento(null);
        }

        // Al dar de alta el estado es ACTIVO por defecto
        if (evento.getIdEvento() == null || evento.getEstado() == null) {
            evento.setEstado(EstadoEvento.ACTIVO);
        }

        // Por defecto no destacado
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
            evento.setEstado(EstadoEvento.CANCELADO);
            eventoRepo.save(evento);
        }
    }

    @Override
    public void delete(int id) {
        try {
            eventoRepo.deleteById(id);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            // Si tiene reservas vinculadas cancelamos en lugar de borrar
            cancelarEvento(id);
        }
    }

    // --- CONVERSIONES ---

    private EventoDTO convertToDto(Evento evento) {
        EventoDTO dto = new EventoDTO();
        dto.setIdEvento(evento.getIdEvento());
        dto.setNombre(evento.getNombre());
        dto.setDescripcion(evento.getDescripcion());
        dto.setFechaInicio(evento.getFechaInicio());
        dto.setDuracion(evento.getDuracion());
        dto.setDireccion(evento.getDireccion());
        dto.setEstado(evento.getEstado() != null ? evento.getEstado().name() : null);
        dto.setDestacado(evento.getDestacado());
        dto.setAforoMaximo(evento.getAforoMaximo());
        dto.setMinimoAsistencia(evento.getMinimoAsistencia());
        dto.setPrecio(evento.getPrecio());
        if (evento.getTipo() != null) {
            dto.setIdTipo(evento.getTipo().getIdTipo());
            dto.setNombreTipo(evento.getTipo().getNombre());
        }
        return dto;
    }

    private Evento convertToEntity(EventoDTO dto) {
        Evento evento = new Evento();
        evento.setIdEvento(dto.getIdEvento());
        evento.setNombre(dto.getNombre());
        evento.setDescripcion(dto.getDescripcion());
        evento.setFechaInicio(dto.getFechaInicio());
        evento.setDuracion(dto.getDuracion());
        evento.setDireccion(dto.getDireccion());
        evento.setDestacado(dto.getDestacado());
        evento.setAforoMaximo(dto.getAforoMaximo());
        evento.setMinimoAsistencia(dto.getMinimoAsistencia());
        evento.setPrecio(dto.getPrecio());
        if (dto.getEstado() != null) {
            evento.setEstado(EstadoEvento.valueOf(dto.getEstado()));
        }
        if (dto.getIdTipo() != 0) {
            evento.setTipo(tipoRepo.findById(dto.getIdTipo()).orElse(null));
        }
        return evento;
    }
    
    @Override
    public List<EventoDTO> findAll() {
        return eventoRepo.findAll().stream()
                .map(this::convertToDto)
                .toList();
    }
}