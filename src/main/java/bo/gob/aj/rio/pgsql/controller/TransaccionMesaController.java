package bo.gob.aj.rio.pgsql.controller;

import bo.gob.aj.rio.pgsql.entity.TransaccionMesaEntity;
import bo.gob.aj.rio.pgsql.service.TransaccionMesaService;
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
@RequestMapping("/api/transaccion_mesa")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TransaccionMesaController {

    @Autowired
    private TransaccionMesaService transaccionMesaService;

    @Autowired
    public TransaccionMesaController(TransaccionMesaService pTransaccionMesaService) {
        this.transaccionMesaService = pTransaccionMesaService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> RegistrarTransaccionMesa(@RequestBody TransaccionMesaEntity pTransaccionMesaEntity) throws ServerException {

        if (pTransaccionMesaEntity.getOperadorId() == null || pTransaccionMesaEntity.getFechaArchivo() == null) {
            throw new ServerException("Por favor, llene los datos obligatorios.");
        }

        this.transaccionMesaService.RegistrarTransaccionMesa(pTransaccionMesaEntity);
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("message", "Se agregó un nuevo registro de Transacciones Mesas.");
        objectMap.put("status", 200);

        return new ResponseEntity<>(objectMap, HttpStatus.OK);
    }

    @PutMapping("/update/{pTransaccionMesaId}")
    public ResponseEntity<?> ActualizarTransaccionMesa(@PathVariable Long pTransaccionMesaId,
                                                       @Valid @RequestBody TransaccionMesaEntity pTransaccionMesaEntity,
                                                       Errors pErrors) {
        TransaccionMesaEntity vEntity = this.transaccionMesaService.ActualizarTransaccionMesa(pTransaccionMesaId, pTransaccionMesaEntity);

        String vRespuesta = "";

        if (pErrors.hasErrors()) {
            vRespuesta = pErrors.getAllErrors().stream()
                    .map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(","));
            return ResponseEntity.badRequest().body(vRespuesta);
        }

        Map<String, Object> objectMap = new HashMap<>();

        if (vEntity.getTransaccionMesaId() == null) {
            objectMap.put("message", "No se modificó ningun registro de Transacciones Mesas.");
            objectMap.put("status", 204);
            return new ResponseEntity<>(objectMap, HttpStatus.NOT_FOUND);
        } else {
            objectMap.put("message", "Se ha actualizado el registro de Transacciones Mesas.");
            objectMap.put("status", 200);
            return new ResponseEntity<>(objectMap, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{pTransaccionMesaId}")
    public ResponseEntity<?> EliminarTransaccionMesa(@PathVariable Long pTransaccionMesaId) {
        TransaccionMesaEntity vCajaPago = this.transaccionMesaService.EliminarTransaccionMesa(pTransaccionMesaId);

        if (vCajaPago == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("message", "Se ha eliminado un registro de Transacciones Mesas.");
        objectMap.put("status", 200);

        return new ResponseEntity<>(objectMap, HttpStatus.OK);
    }

    @GetMapping("/list/{pOperadorId}")
    public ResponseEntity<?> ObtenerCajasPagos(@PathVariable Long pOperadorId) {
        List<TransaccionMesaEntity> vListTransaccionesMesas = this.transaccionMesaService.ObtenerTransaccionesMesasPorOperador(pOperadorId);

        List<Map<String, Object>> lista = new ArrayList<>();

        for (TransaccionMesaEntity transaccionMesa: vListTransaccionesMesas) {
            Map<String, Object> iterationMap = new LinkedHashMap<>();

            iterationMap.put("transaccionMesaId", transaccionMesa.getTransaccionMesaId());
            iterationMap.put("operadorId", transaccionMesa.getOperadorId());
            iterationMap.put("codigoMesa", transaccionMesa.getCodigoMesa());
            iterationMap.put("fechaRegistroReal", transaccionMesa.getFechaRegistroReal());
            iterationMap.put("horaRegistro", transaccionMesa.getHoraRegistro());
            iterationMap.put("numeroFormulario", transaccionMesa.getNumeroFormulario());
            iterationMap.put("tipoTransaccion", transaccionMesa.getTipoTransaccion());
            iterationMap.put("denominacionFicha1000", transaccionMesa.getDenominacionFicha1000());
            iterationMap.put("cantidadFichas1000", transaccionMesa.getCantidadFichas1000());
            iterationMap.put("denominacionFicha500", transaccionMesa.getDenominacionFicha500());
            iterationMap.put("cantidadFichas500", transaccionMesa.getCantidadFichas500());
            iterationMap.put("denominacionFicha200", transaccionMesa.getDenominacionFicha200());
            iterationMap.put("cantidadFichas200", transaccionMesa.getCantidadFichas200());
            iterationMap.put("denominacionFicha100", transaccionMesa.getDenominacionFicha100());
            iterationMap.put("cantidadFichas100", transaccionMesa.getCantidadFichas100());
            iterationMap.put("denominacionFicha50", transaccionMesa.getDenominacionFicha50());
            iterationMap.put("cantidadFichas50", transaccionMesa.getCantidadFichas50());
            iterationMap.put("denominacionFicha20", transaccionMesa.getDenominacionFicha20());
            iterationMap.put("cantidadFichas20", transaccionMesa.getCantidadFichas20());
            iterationMap.put("denominacionFicha10", transaccionMesa.getDenominacionFicha10());
            iterationMap.put("cantidadFichas10", transaccionMesa.getCantidadFichas10());
            iterationMap.put("denominacionFicha5", transaccionMesa.getDenominacionFicha5());
            iterationMap.put("cantidadFichas5", transaccionMesa.getCantidadFichas5());
            iterationMap.put("denominacionFicha1", transaccionMesa.getDenominacionFicha1());
            iterationMap.put("cantidadFichas1", transaccionMesa.getCantidadFichas1());
            iterationMap.put("montoTotalPagado", transaccionMesa.getMontoTotalPagado());
            iterationMap.put("fechaArchivo", transaccionMesa.getFechaArchivo());
            iterationMap.put("fechaRegistro", transaccionMesa.getFechaRegistro());
            iterationMap.put("estadoId", transaccionMesa.getEstadoId());

            lista.add(iterationMap);
        }

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
}
