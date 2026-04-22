package apirest.gympass.service;

import java.util.List;
import apirest.gympass.entity.EstadoEvento;
import apirest.gympass.entity.Evento;
import apirest.gympass.entityDto.EventoDTO;

public interface EventoService {

    // --- MÉTODOS DE CONSULTA ---
    // Usamos EventoDTO para que el controlador no vea la base de datos 
    List<EventoDTO> findByEstado(EstadoEvento estado); 
    List<EventoDTO> findByDestacado(String destacado);
    EventoDTO findById(int id);
    
    // --- MÉTODOS DE GESTIÓN ---
    // Recibe DTO para crear o editar 
    EventoDTO save(EventoDTO eventoDTO);
    
    // El PDF pide específicamente "cancelar" cambiando el estado a CANCELADO 
    void cancelarEvento(int id);
    
    // Eliminación física (método delete del repo) 
    void delete(int id);
    
    // --- LÓGICA DE NEGOCIO ---
    // Para validar el máximo de 10 personas y el aforo 
    String gestionarReservas(Evento evento, int cantidad);
}