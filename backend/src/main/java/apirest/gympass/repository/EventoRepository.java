package apirest.gympass.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import apirest.gympass.entity.EstadoEvento;
import apirest.gympass.entity.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {
    List<Evento> findByDestacado(String destacado);
    List<Evento> findByEstado(EstadoEvento estado);
    List<Evento> findByTipo_IdTipo(int idTipo);
    // Devuelve solo los eventos destacados que ademas esten en un estado concreto
    List<Evento> findByDestacadoAndEstado(String destacado, EstadoEvento estado);
}