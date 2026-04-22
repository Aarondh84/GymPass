package apirest.gympass.restcontroller;


import apirest.gympass.entityDto.TipoDTO;

import apirest.gympass.service.TipoService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos")
@CrossOrigin(origins = "http://localhost:4200")
public class TipoController {

    @Autowired
    private TipoService tipoService;

    @GetMapping("/todos")
    public ResponseEntity<List<TipoDTO>> listar() {
        return ResponseEntity.ok(tipoService.findAll());
    }

    @PostMapping("/alta")
    public ResponseEntity<TipoDTO> crear(@RequestBody TipoDTO tipoDto) {
        return new ResponseEntity<>(tipoService.save(tipoDto), HttpStatus.CREATED);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<TipoDTO> editar(@PathVariable int id, @RequestBody TipoDTO tipoDto) {
        tipoDto.setIdTipo(id);
        return ResponseEntity.ok(tipoService.save(tipoDto));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        tipoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}