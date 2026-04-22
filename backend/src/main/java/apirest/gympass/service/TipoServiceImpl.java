package apirest.gympass.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apirest.gympass.entity.Tipo;
import apirest.gympass.entityDto.TipoDTO;
import apirest.gympass.repository.TipoRepository;

@Service
public class TipoServiceImpl implements TipoService {

    @Autowired
    private TipoRepository tipoRepo;

    @Override
    public List<TipoDTO> findAll() {
        return tipoRepo.findAll().stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public TipoDTO save(TipoDTO dto) {
        Tipo entidad = convertToEntity(dto);
        Tipo guardado = tipoRepo.save(entidad);
        return convertToDto(guardado);
    }

    @Override
    public void delete(int id) {
        tipoRepo.deleteById(id);
    }


    @Override
    public TipoDTO findById(int id) {
        //Buscamos la entidad en la base de datos
        Tipo entidad = tipoRepo.findById(id).orElse(null);
        
        // Si existe, la convertimos a DTO; si no, devolvemos null
        return (entidad != null) ? convertToDto(entidad) : null;
    }

    // Métodos de conversión
    private TipoDTO convertToDto(Tipo entidad) {
        TipoDTO dto = new TipoDTO();
        dto.setIdTipo(entidad.getIdTipo());
        dto.setNombre(entidad.getNombre());
        dto.setDescripcion(entidad.getDescripcion());
        return dto;
    }

    private Tipo convertToEntity(TipoDTO dto) {
        Tipo entidad = new Tipo();
        entidad.setIdTipo(dto.getIdTipo());
        entidad.setNombre(dto.getNombre());
        entidad.setDescripcion(dto.getDescripcion());
        return entidad;
    }
}
