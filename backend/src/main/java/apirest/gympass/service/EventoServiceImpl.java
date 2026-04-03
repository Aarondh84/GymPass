package apirest.gympass.service;

import java.util.List;

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
	public List<Evento> findByEstado(EstadoEvento estado) {
		
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
	
	
	private EventoDTO converToDto(Evento evento) {
	    EventoDTO dto = new EventoDTO();
	    dto.setIdEvento(evento.getIdEvento());
	    dto.setNombre(evento.getNombre());
	    dto.setPrecio(evento.getPrecio());
	    
	    return dto;
	}

}
