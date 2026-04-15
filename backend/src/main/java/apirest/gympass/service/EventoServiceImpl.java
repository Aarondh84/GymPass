package apirest.gympass.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apirest.gympass.entity.EstadoEvento;
import apirest.gympass.entity.Evento;
import apirest.gympass.entityDto.EventoDTO;
import apirest.gympass.repository.EventoRepository;

@Service
public class EventoServiceImpl implements EventoService {

    @Autowired
    private EventoRepository eventoRepo;

    @Override
    public List<EventoDTO> findByEstado(EstadoEvento estado) {
        // Buscamos las entidades en la tabla de MySQL 
        List<Evento> entidades = eventoRepo.findByEstado(estado);
        // Las metemos en la "maleta" DTO para enviarlas a Angular
        return entidades.stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList());
    }

    @Override
    public List<EventoDTO> findByDestacado(String destacado) {
        // Filtramos por la columna 'S' o 'N' del diagrama 
        List<Evento> entidades = eventoRepo.findByDestacado(destacado);
        return entidades.stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList());
    }

    @Override
    public List<EventoDTO> findByTipo(int idTipo) {
        // Relación con la tabla 'tipos'
        List<Evento> entidades = eventoRepo.findByTipo_IdTipo(idTipo); 
        return entidades.stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList());
    }

    @Override
    public EventoDTO findById(int id) {
        // Buscamos por el ID_EVENTO 
        return eventoRepo.findById(id)
                         .map(this::convertToDto)
                         .orElse(null);
    }

    @Override
    public EventoDTO save(EventoDTO eventoDTO) {
        // Pasamos los datos de la "maleta" a la entidad de MySQL
        Evento entidad = convertToEntity(eventoDTO);
        
        // Al dar de alta, el estado debe ser "ACTIVO" 
        if (entidad.getIdEvento() == 0) {
            entidad.setEstado(EstadoEvento.ACTIVO);
        }
        
        Evento guardado = eventoRepo.save(entidad);
        return convertToDto(guardado);
    }

    @Override
    public void delete(int idEvento) {
        eventoRepo.deleteById(idEvento);
    }

    @Override
    public void cancelarEvento(int idEvento) {
        // No borramos, cambiamos estado a "CANCELADO" 
        eventoRepo.findById(idEvento).ifPresent(e -> {
            e.setEstado(EstadoEvento.CANCELADO);
            eventoRepo.save(e);
        });
    }

    // --- MAPPERS

    private EventoDTO convertToDto(Evento e) {
        // El DTO solo contiene variables para enviar a Angular
        EventoDTO dto = new EventoDTO();
        dto.setIdEvento(e.getIdEvento());
        dto.setNombre(e.getNombre());
        dto.setDescripcion(e.getDescripcion());
        dto.setFechaInicio(e.getFechaInicio());
        dto.setPrecio(e.getPrecio());
        dto.setEstado(e.getEstado());
        dto.setAforoMaximo(e.getAforoMaximo());
        dto.setDestacado(e.getDestacado());
        
        // Si el evento tiene un tipo, mandamos su ID 
        if (e.getTipo() != null) {
            dto.setIdTipo(e.getTipo().getIdTipo());
        }
        return dto;
    }

    private Evento convertToEntity(EventoDTO dto) {
        
        Evento e = new Evento();
        e.setIdEvento(dto.getIdEvento());
        e.setNombre(dto.getNombre());
        e.setDescripcion(dto.getDescripcion());
        e.setFechaInicio(dto.getFechaInicio());
        e.setPrecio(dto.getPrecio());
        e.setEstado(dto.getEstado());
        e.setAforoMaximo(dto.getAforoMaximo());
        e.setDestacado(dto.getDestacado());
        return e;
    }
}