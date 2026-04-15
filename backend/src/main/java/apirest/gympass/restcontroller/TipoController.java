package apirest.gympass.restcontroller;

import apirest.gympass.entity.Tipo;
import apirest.gympass.repository.TipoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TipoController {
  
  private final TipoRepository tipoRepository;

    @GetMapping
    public ResponseEntity<List<Tipo>> findAll() {
        return ResponseEntity.ok(tipoRepository.findAll());
    }

}