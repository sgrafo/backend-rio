package bo.gob.aj.rio.pgsql.controller;

import bo.gob.aj.rio.pgsql.entity.CajaPagoEntity;
import bo.gob.aj.rio.pgsql.service.CajaPagoService;
import bo.gob.aj.rio.util.Fechas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.rmi.ServerException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/caja_pago")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CajaPagoController {

    @Autowired
    private CajaPagoService cajaPagoService;

    @Autowired
    public CajaPagoController(CajaPagoService pCajaPagoService) {
        this.cajaPagoService = pCajaPagoService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> RegistrarCajaPago(@RequestBody CajaPagoEntity pCajaPagoEntity) throws ServerException {

        if (pCajaPagoEntity.getOperadorId() == null || pCajaPagoEntity.getFechaArchivo() == null) {
            throw new ServerException("Por favor, llene los datos obligatorios.");
        }

        this.cajaPagoService.RegistrarCajaPago(pCajaPagoEntity);
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("message", "Se agregó un nuevo registro de Caja Pago.");
        objectMap.put("status", 200);

        return new ResponseEntity<>(objectMap, HttpStatus.OK);
    }

    @PutMapping("/update/{pCajaPagoId}")
    public ResponseEntity<?> ActualizarCajaPago(@PathVariable Long pCajaPagoId,
                                                 @Valid @RequestBody CajaPagoEntity pCajaPagoEntity,
                                                 Errors pErrors) {
        CajaPagoEntity vEntity = this.cajaPagoService.ActualizarCajaPago(pCajaPagoId, pCajaPagoEntity);

        String vRespuesta = "";

        if (pErrors.hasErrors()) {
            vRespuesta = pErrors.getAllErrors().stream()
                    .map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(","));
            return ResponseEntity.badRequest().body(vRespuesta);
        }

        Map<String, Object> objectMap = new HashMap<>();

        if (vEntity.getCajaPagoId() == null) {
            objectMap.put("message", "No se modificó ningun registro de Caja Pago.");
            objectMap.put("status", 204);
            return new ResponseEntity<>(objectMap, HttpStatus.NOT_FOUND);
        } else {
            objectMap.put("message", "Se ha actualizado el registro de Caja Pago.");
            objectMap.put("status", 200);
            return new ResponseEntity<>(objectMap, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{pCajaPagoId}")
    public ResponseEntity<?> EliminarCajaPago(@PathVariable Long pCajaPagoId) {
        CajaPagoEntity vCajaPago = this.cajaPagoService.EliminarCajaPago(pCajaPagoId);

        if (vCajaPago == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("message", "Se ha eliminado un registro de Caja Pago.");
        objectMap.put("status", 200);

        return new ResponseEntity<>(objectMap, HttpStatus.OK);
    }

    @GetMapping("/list/{pOperadorId}")
    public ResponseEntity<?> ObtenerCajasPagos(@PathVariable Long pOperadorId) {
        List<CajaPagoEntity> vListCajasPagos = this.cajaPagoService.ObtenerCajasPagosPorOperador(pOperadorId);

        List<Map<String, Object>> lista = new ArrayList<>();

        for (CajaPagoEntity cajaPago: vListCajasPagos) {
            Map<String, Object> iterationMap = new LinkedHashMap<>();

            iterationMap.put("cajaPagoId", cajaPago.getCajaPagoId());
            iterationMap.put("operadorId", cajaPago.getOperadorId());
            iterationMap.put("numeroCaja", cajaPago.getNumeroCaja());
            iterationMap.put("nombreCompletoJugador", cajaPago.getNombreCompletoJugador());
            iterationMap.put("documentoIdentidad", cajaPago.getDocumentoIdentidad());
            iterationMap.put("lugarExpedicion", cajaPago.getLugarExpedicion());
            iterationMap.put("numeroComprobante", cajaPago.getNumeroComprobante());
            iterationMap.put("fechaPago", cajaPago.getFechaPago());
            iterationMap.put("horaPago", cajaPago.getHoraPago());
            iterationMap.put("denominacionFicha1000", cajaPago.getDenominacionFicha1000());
            iterationMap.put("cantidadFichas1000", cajaPago.getCantidadFichas1000());
            iterationMap.put("denominacionFicha500", cajaPago.getDenominacionFicha500());
            iterationMap.put("cantidadFichas500", cajaPago.getCantidadFichas500());
            iterationMap.put("denominacionFicha200", cajaPago.getDenominacionFicha200());
            iterationMap.put("cantidadFichas200", cajaPago.getCantidadFichas200());
            iterationMap.put("denominacionFicha100", cajaPago.getDenominacionFicha100());
            iterationMap.put("cantidadFichas100", cajaPago.getCantidadFichas100());
            iterationMap.put("denominacionFicha50", cajaPago.getDenominacionFicha50());
            iterationMap.put("cantidadFichas50", cajaPago.getCantidadFichas50());
            iterationMap.put("denominacionFicha20", cajaPago.getDenominacionFicha20());
            iterationMap.put("cantidadFichas20", cajaPago.getCantidadFichas20());
            iterationMap.put("denominacionFicha10", cajaPago.getDenominacionFicha10());
            iterationMap.put("cantidadFichas10", cajaPago.getCantidadFichas10());
            iterationMap.put("denominacionFicha5", cajaPago.getDenominacionFicha5());
            iterationMap.put("cantidadFichas5", cajaPago.getCantidadFichas5());
            iterationMap.put("denominacionFicha1", cajaPago.getDenominacionFicha1());
            iterationMap.put("cantidadFichas1", cajaPago.getCantidadFichas1());
            iterationMap.put("montoTotalPagado", cajaPago.getMontoTotalPagado());
            iterationMap.put("fechaArchivo", cajaPago.getFechaArchivo());
            iterationMap.put("fechaRegistro", cajaPago.getFechaRegistro());
            iterationMap.put("estadoId", cajaPago.getEstadoId());

            lista.add(iterationMap);
        }

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/list_njugadores_ganan_mas_dia/{pOperadorId}/{pFechaInicio}/{pFechaFin}/{pNumeroJugadores}")
    public ResponseEntity<?> ObtenerNJugadoresGananMasDia(@PathVariable Long pOperadorId,
                                                          @PathVariable String pFechaInicio,
                                                          @PathVariable String pFechaFin,
                                                          @PathVariable Integer pNumeroJugadores) {

        List<Map<String, Object>> lista = new ArrayList<>();

        String vFechaInicioCadena = "";
        String vFechaFinCadena = "";

        try {
            if (pFechaInicio.length() > 1 && pFechaFin.length() > 1) {
                vFechaInicioCadena = Fechas.ConvertirFormatoFecha(pFechaInicio, "-", "/");
                vFechaFinCadena = Fechas.ConvertirFormatoFecha(pFechaFin, "-", "/");
            }
            else {
                vFechaInicioCadena = "1900-01-01";
                vFechaFinCadena = "1900-01-01";
            }

            List<CajaPagoEntity> vListCajasPagos = this.cajaPagoService.ObtenerNJugadoresGananMasDia(pOperadorId, vFechaInicioCadena, vFechaFinCadena, pNumeroJugadores);

            for (CajaPagoEntity cajaPago: vListCajasPagos) {

                Map<String, Object> iterationMap = new LinkedHashMap<>();

                iterationMap.put("nombreCompletoJugador", cajaPago.getNombreCompletoJugador());
                iterationMap.put("documentoIdentidad", cajaPago.getDocumentoIdentidad());
                iterationMap.put("cantidadGananciaDia", cajaPago.getCantidadGananciaDia());
                iterationMap.put("montoTotalPagadoSuma", cajaPago.getMontoTotalPagadoSuma());

                lista.add(iterationMap);
            }
        }
        catch(Exception ex) {
            System.out.println(ex.toString());
        }

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/list_umbral_jugador_gana_mas_rango_fechas/{pDocumentoIdentidad}/{pFechaInicio}/{pFechaFin}/{pMontoUmbral}")
    public ResponseEntity<?> ObtenerUmbralPagosPorFechasJugador(@PathVariable String pDocumentoIdentidad,
                                                                @PathVariable String pFechaInicio,
                                                                @PathVariable String pFechaFin,
                                                                @PathVariable BigDecimal pMontoUmbral) {

        List<Map<String, Object>> lista = new ArrayList<>();

        String vFechaInicioCadena = "";
        String vFechaFinCadena = "";

        try {
            if (pFechaInicio.length() > 1 && pFechaFin.length() > 1) {
                vFechaInicioCadena = Fechas.ConvertirFormatoFecha(pFechaInicio, "-", "/");
                vFechaFinCadena = Fechas.ConvertirFormatoFecha(pFechaFin, "-", "/");
            }
            else {
                vFechaInicioCadena = "1900-01-01";
                vFechaFinCadena = "1900-01-01";
            }

            List<CajaPagoEntity> vListCajasPagos = this.cajaPagoService.ObtenerUmbralPagosPorFechasJugador(pDocumentoIdentidad, vFechaInicioCadena, vFechaFinCadena, pMontoUmbral);

            for (CajaPagoEntity cajaPago: vListCajasPagos) {

                Map<String, Object> iterationMap = new LinkedHashMap<>();

                iterationMap.put("nombreCompletoJugador", cajaPago.getNombreCompletoJugador());
                iterationMap.put("documentoIdentidad", cajaPago.getDocumentoIdentidad());
                iterationMap.put("fechaArchivo", cajaPago.getFechaArchivo());
                iterationMap.put("montoTotalPagadoSuma", cajaPago.getMontoTotalPagadoSuma());
                iterationMap.put("cantidadGananciaDia", cajaPago.getCantidadGananciaDia());

                lista.add(iterationMap);
            }
        }
        catch(Exception ex) {
            System.out.println(ex.toString());
        }

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
}
