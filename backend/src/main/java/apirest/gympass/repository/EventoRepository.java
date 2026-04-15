package apirest.gympass.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import apirest.gympass.entity.EstadoEvento;
import apirest.gympass.entity.Evento;
import apirest.gympass.entityDto.EventoDTO;

@Repository
public interface EventoRepository extends JpaRepository<Evento,Integer> {
		List<Evento>findByDestacado(String destacado);
		List<Evento>findByEstado(EstadoEvento estado);
		List<Evento> findByTipo_IdTipo(int idTipo);

}

