package bo.gob.aj.rio.pgsql.controller;

import bo.gob.aj.rio.pgsql.entity.FacturaAnuladaEntity;
import bo.gob.aj.rio.pgsql.service.FacturaAnuladaService;
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
@RequestMapping("/api/factura_anulada")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FacturaAnuladaController {

    @Autowired
    private FacturaAnuladaService facturaAnuladaService;

    @Autowired
    public FacturaAnuladaController(FacturaAnuladaService pFacturaAnuladaService) {
        this.facturaAnuladaService = pFacturaAnuladaService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> RegistrarFacturaAnulada(@RequestBody FacturaAnuladaEntity pFacturaAnuladaEntity) throws ServerException {

        if (pFacturaAnuladaEntity.getOperadorId() == null || pFacturaAnuladaEntity.getFechaArchivo() == null) {
            throw new ServerException("Por favor, llene los datos obligatorios.");
        }

        this.facturaAnuladaService.RegistrarFacturaAnulada(pFacturaAnuladaEntity);
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("message", "Se agregó un nuevo registro de Factura Anulada.");
        objectMap.put("status", 200);

        return new ResponseEntity<>(objectMap, HttpStatus.OK);
    }

    @PutMapping("/update/{pFacturaAnuladaId}")
    public ResponseEntity<?> ActualizarFacturaAnulada(@PathVariable Long pFacturaAnuladaId,
                                                      @Valid @RequestBody FacturaAnuladaEntity pFacturaAnuladaEntity,
                                                      Errors pErrors) {
        FacturaAnuladaEntity vEntity = this.facturaAnuladaService.ActualizarFacturaAnulada(pFacturaAnuladaId, pFacturaAnuladaEntity);

        String vRespuesta = "";

        if (pErrors.hasErrors()) {
            vRespuesta = pErrors.getAllErrors().stream()
                    .map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(","));
            return ResponseEntity.badRequest().body(vRespuesta);
        }

        Map<String, Object> objectMap = new HashMap<>();

        if (vEntity.getFacturaAnuladaId() == null) {
            objectMap.put("message", "No se modificó ningun registro de Factura Anulada.");
            objectMap.put("status", 204);
            return new ResponseEntity<>(objectMap, HttpStatus.NOT_FOUND);
        } else {
            objectMap.put("message", "Se ha actualizado el registro de Factura Anulada.");
            objectMap.put("status", 200);
            return new ResponseEntity<>(objectMap, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{pFacturaAnuladaId}")
    public ResponseEntity<?> EliminarFacturaAnulada(@PathVariable Long pFacturaAnuladaId) {
        FacturaAnuladaEntity vFacturaAnulada = this.facturaAnuladaService.EliminarFacturaAnulada(pFacturaAnuladaId);

        if (vFacturaAnulada == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("message", "Se ha eliminado un registro de Factura Anulada.");
        objectMap.put("status", 200);

        return new ResponseEntity<>(objectMap, HttpStatus.OK);
    }

    @GetMapping("/list/{pFacturaAnuladaId}")
    public ResponseEntity<?> ObtenerFacturasAnuladas(@PathVariable Long pFacturaAnuladaId) {
        List<FacturaAnuladaEntity> vListFacturasAnuladas = this.facturaAnuladaService.ObtenerFacturasAnuladasPorOperador(pFacturaAnuladaId);

        List<Map<String, Object>> lista = new ArrayList<>();

        for (FacturaAnuladaEntity facturaAnulada: vListFacturasAnuladas) {
            Map<String, Object> iterationMap = new LinkedHashMap<>();

            iterationMap.put("facturaAnuladaId", facturaAnulada.getFacturaAnuladaId());
            iterationMap.put("operadorId", facturaAnulada.getOperadorId());
            iterationMap.put("numeroCaja", facturaAnulada.getNumeroCaja());
            iterationMap.put("nombreCompletoJugador", facturaAnulada.getNombreCompletoJugador());
            iterationMap.put("documentoIdentidad", facturaAnulada.getDocumentoIdentidad());
            iterationMap.put("lugarExpedicion", facturaAnulada.getLugarExpedicion());
            iterationMap.put("numeroFacturaAnulada", facturaAnulada.getNumeroFacturaAnulada());
            iterationMap.put("numeroAutorizacion", facturaAnulada.getNumeroAutorizacion());
            iterationMap.put("fechaFacturaAnulada", facturaAnulada.getFechaFacturaAnulada());
            iterationMap.put("montoTotalFacturaAnulada", facturaAnulada.getMontoTotalFacturaAnulada());
            iterationMap.put("numeroTicket", facturaAnulada.getNumeroTicket());
            iterationMap.put("montoTotalTicket", facturaAnulada.getMontoTotalTicket());
            iterationMap.put("fechaFacturaNueva", facturaAnulada.getFechaFacturaNueva());
            iterationMap.put("numeroFacturaNueva", facturaAnulada.getNumeroFacturaNueva());
            iterationMap.put("montoTotalFacturaNueva", facturaAnulada.getMontoTotalFacturaNueva());
            iterationMap.put("fechaArchivo", facturaAnulada.getFechaArchivo());
            iterationMap.put("fechaRegistro", facturaAnulada.getFechaRegistro());
            iterationMap.put("estadoId", facturaAnulada.getEstadoId());

            lista.add(iterationMap);
        }

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
}
