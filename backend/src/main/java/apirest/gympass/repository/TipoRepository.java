package apirest.gympass.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import apirest.gympass.entity.Tipo;

@Repository
public interface TipoRepository extends JpaRepository<Tipo, Integer> {

}