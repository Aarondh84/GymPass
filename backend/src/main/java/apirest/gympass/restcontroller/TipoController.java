package apirest.gympass.restcontroller;

import apirest.gympass.entityDto.TipoDTO;
import apirest.gympass.service.TipoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tipos")
@RequiredArgsConstructor
public class TipoController {

    private final TipoService tipoService;

    @GetMapping
    public ResponseEntity<List<TipoDTO>> listar() {
        return ResponseEntity.ok(tipoService.findAll());
    }

    @PostMapping
    public ResponseEntity<TipoDTO> crear(@RequestBody TipoDTO tipoDto) {
        return new ResponseEntity<>(tipoService.save(tipoDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoDTO> editar(@PathVariable int id, @RequestBody TipoDTO tipoDto) {
        tipoDto.setIdTipo(id);
        return ResponseEntity.ok(tipoService.save(tipoDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        tipoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}