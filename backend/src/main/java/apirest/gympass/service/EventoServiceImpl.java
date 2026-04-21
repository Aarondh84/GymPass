package apirest.gympass.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apirest.gympass.entity.EstadoEvento;
import apirest.gympass.entity.Evento;
import apirest.gympass.entityDto.EventoDTO;
import apirest.gympass.repository.EventoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventoServiceImpl implements EventoService {

    @Autowired
    private EventoRepository eventoRepo;


    @Override
    public List<Evento> findByEstado(EstadoEvento estado) {
        // Devolvemos la lista de entidades directamente para el controlador
        return eventoRepo.findByEstado(estado);
    }

	@Override
	public List<Evento> findByDestacado(String destacado) {
		
		return eventoRepo.findByDestacado(destacado);
	}
	
	//metodo para gestionar el maximo de 10 reservas 
	
	public String gestionarReservas(Evento evento, int cantidad) {
		
		if (cantidad > 10){
			return "No se permiten mas de 10 personas por reserva";
		}
		if(cantidad> evento.getAforoMaximo()) {
			return "Se supera la cantidad de aforo máximo";
		}
		
		return "Reserva realizada con éxito";
	}

	@Override
	public Evento findById(Integer id) {
		return eventoRepo.findById(id).orElse(null);
	}

	@Override
	public Evento guardar(Evento evento) {
		if (evento.getEstado() == null) {
			evento.setEstado(EstadoEvento.ACTIVO);
		}
		return eventoRepo.save(evento);
	}

	@Override
	public void eliminar(Integer id) {
		eventoRepo.deleteById(id);
	}
	
	
	private EventoDTO converToDto(Evento evento) {
	    EventoDTO dto = new EventoDTO();
	    dto.setIdEvento(evento.getIdEvento());
	    dto.setNombre(evento.getNombre());
	    dto.setPrecio(evento.getPrecio());
	    
	    return dto;
	}


 
    @Override
    public EventoDTO findById(int id) {
        // Buscamos la entidad, si no existe devolvemos null
        Evento evento = eventoRepo.findById(id).orElse(null);
        return (evento != null) ? convertToDto(evento) : null;
    }

    @Override
    public EventoDTO save(EventoDTO eventoDTO) {
        // 1. Convertimos el DTO que viene de Angular a una Entidad de Java
        Evento evento = new Evento();
        // Si el ID es 0 o null, Hibernate creará uno nuevo (Alta)
        if(eventoDTO.getIdEvento() != 0) {
            evento.setIdEvento(eventoDTO.getIdEvento());
        }
        evento.setNombre(eventoDTO.getNombre());
        evento.setPrecio(eventoDTO.getPrecio());
        // Importante: No olvides asignar el resto de campos (fecha, aforo...)
        
        // 2. Guardamos la entidad
        Evento guardado = eventoRepo.save(evento);
        
        // 3. Devolvemos el resultado convertido a DTO
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
        eventoRepo.deleteById(id);
    }


    // --- MÉTODOS DE CONVERSIÓN 

    private EventoDTO convertToDto(Evento evento) {
        EventoDTO dto = new EventoDTO();
        dto.setIdEvento(evento.getIdEvento());
        dto.setNombre(evento.getNombre());
        dto.setPrecio(evento.getPrecio());
       
        return dto;
    }
}