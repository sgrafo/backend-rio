package bo.gob.aj.rio.pgsql.controller;

import bo.gob.aj.rio.pgsql.entity.OperadorEntity;
import bo.gob.aj.rio.pgsql.service.OperadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.rmi.ServerException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/operador")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OperadorController {

    @Autowired
    private OperadorService operadorService;

    @Autowired
    public OperadorController(OperadorService pOperadorService) {
        this.operadorService = pOperadorService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> RegistrarOperador(@RequestBody OperadorEntity pOperadorEntity) throws ServerException {

        if (pOperadorEntity.getNombreEmpresa() == null || pOperadorEntity.getNombreSalon() == null) {
            throw new ServerException("Por favor, llene los datos obligatorios.");
        }

        this.operadorService.RegistrarOperador(pOperadorEntity);
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("message", "Se agregó un nuevo registro de Operador.");
        objectMap.put("status", 200);

        return new ResponseEntity<>(objectMap, HttpStatus.OK);
    }

    @PutMapping("/update/{pOperadorId}")
    public ResponseEntity<?> ActualizarOperador(@PathVariable Long pOperadorId,
                                                @Valid @RequestBody OperadorEntity pOperadorEntity,
                                                Errors pErrors) {
        OperadorEntity vEntity = this.operadorService.ActualizarOperador(pOperadorId, pOperadorEntity);

        String vRespuesta = "";

        if (pErrors.hasErrors()) {
            vRespuesta = pErrors.getAllErrors().stream()
                    .map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(","));
            return ResponseEntity.badRequest().body(vRespuesta);
        }

        Map<String, Object> objectMap = new HashMap<>();

        if (vEntity.getOperadorId() == null) {
            objectMap.put("message", "No se modificó ningun registro de Operador.");
            objectMap.put("status", 204);
            return new ResponseEntity<>(objectMap, HttpStatus.NOT_FOUND);
        } else {
            objectMap.put("message", "Se ha actualizado el registro del Operador.");
            objectMap.put("status", 200);
            return new ResponseEntity<>(objectMap, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{pOperadorId}")
    public ResponseEntity<?> EliminarOperador(@PathVariable Long pOperadorId) {
        OperadorEntity vOperador = this.operadorService.EliminarOperador(pOperadorId);

        if (vOperador == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("message", "Se ha eliminado un eegistro de Operador.");
        objectMap.put("status", 200);

        return new ResponseEntity<>(objectMap, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> ObtenerOperadores() {
        List<OperadorEntity> vListOperadores = this.operadorService.ObtenerOperadores();

        List<Map<String, Object>> lista = new ArrayList<>();

        for (OperadorEntity operador: vListOperadores) {
            Map<String, Object> iterationMap = new LinkedHashMap<>();

            iterationMap.put("operadorId", operador.getOperadorId());
            iterationMap.put("nombreEmpresa", operador.getNombreEmpresa());
            iterationMap.put("nombreSalon", operador.getNombreSalon());
            iterationMap.put("fechaRegistro", operador.getFechaRegistro());
            iterationMap.put("estadoId", operador.getEstadoId());

            lista.add(iterationMap);
        }

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
}
