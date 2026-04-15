package apirest.gympass.service;

import java.util.List;

import org.springframework.stereotype.Service;

import apirest.gympass.entity.EstadoEvento;
import apirest.gympass.entity.Evento;
import apirest.gympass.entityDto.EventoDTO;

public interface EventoService {
	List<EventoDTO> findByEstado (EstadoEvento estado);
	List<EventoDTO> findByDestacado (String destacado);
	List<EventoDTO> findByTipo(int idTipo);
	EventoDTO findById(int id);
	EventoDTO save(EventoDTO eventoDTO);
	void delete(int idEvento);
	void cancelarEvento(int idEvento);
}
