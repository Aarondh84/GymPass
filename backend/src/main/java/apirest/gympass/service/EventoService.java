package apirest.gympass.service;

import java.util.List;

import apirest.gympass.entity.EstadoEvento;
import apirest.gympass.entity.Evento;

public interface EventoService {
	List<Evento> findByEstado (EstadoEvento estado);
	List<Evento> findByDestacado (String destacado);

}
