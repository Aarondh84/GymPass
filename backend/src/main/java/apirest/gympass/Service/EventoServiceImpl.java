package apirest.gympass.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import apirest.gympass.Repository.EventoRepository;
import apirest.gympass.entities.EstadoEvento;
import apirest.gympass.entities.Evento;

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
	
	
	private EventoDto conVertirADto(Evento evento) {
	    EventoDto dto = new EventoDto();
	    dto.setIdEvento(evento.getIdEvento());
	    dto.setNombre(evento.getNombre());
	    dto.setPrecio(evento.getPrecio());
	    // Solo metes las variables que quieres enviar [cite: 2026-03-18]
	    return dto;
	}

}
