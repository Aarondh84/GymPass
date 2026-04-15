package apirest.gympass.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import apirest.gympass.entities.EstadoEvento;
import apirest.gympass.entities.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento,Integer>{
	List<Evento>findByDestacado(String destacado);
	List<Evento>findByEstado(EstadoEvento estado);

}
