package apirest.gympass.Service;

import java.util.List;

import apirest.gympass.entities.EstadoEvento;
import apirest.gympass.entities.Evento;

public interface EventoService {
	List<Evento> findByEstado (EstadoEvento estado);
	List<Evento> findByDestacado (String destacado);
}
