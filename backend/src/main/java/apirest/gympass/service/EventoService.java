package apirest.gympass.service;

import java.util.List;
import apirest.gympass.entity.EstadoEvento;
import apirest.gympass.entity.Evento;
import apirest.gympass.entityDto.EventoDTO;

public interface EventoService {

    // Métodos de consulta
    List<Evento> findByEstado(EstadoEvento estado);
    List<Evento> findByDestacado(String destacado);
    EventoDTO findById(int id);
    
    // Métodos de gestión
    EventoDTO save(EventoDTO eventoDTO);
    void cancelarEvento(int id);
    void delete(int id);
    
    // (El reto de las 10 personas)
    String gestionarReservas(Evento evento, int cantidad);

	Evento findById (Integer id);
	Evento guardar (Evento evento);
	void eliminar (Integer id);

}
