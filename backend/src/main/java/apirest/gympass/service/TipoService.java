package apirest.gympass.service;

import java.util.List;

import apirest.gympass.entity.Tipo;
import apirest.gympass.entityDto.TipoDTO;

public interface TipoService {
    List<TipoDTO> findAll();
    TipoDTO findById(int id);
    TipoDTO save(TipoDTO tipo);
    void delete(int id);
	
}
