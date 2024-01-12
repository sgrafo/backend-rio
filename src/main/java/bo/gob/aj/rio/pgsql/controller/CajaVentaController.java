package bo.gob.aj.rio.pgsql.controller;

import bo.gob.aj.rio.pgsql.entity.CajaVentaEntity;
import bo.gob.aj.rio.pgsql.service.CajaVentaService;
import bo.gob.aj.rio.util.Fechas;
import bo.gob.aj.rio.util.pojo.CajaVentaPago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.rmi.ServerException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/caja_venta")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CajaVentaController {

    @Autowired
    private CajaVentaService cajaVentaService;

    @Autowired
    public CajaVentaController(CajaVentaService pCajaVentaService) {
        this.cajaVentaService = pCajaVentaService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> RegistrarCajaVenta(@RequestBody CajaVentaEntity pCajaVentaEntity) throws ServerException {

        if (pCajaVentaEntity.getOperadorId() == null || pCajaVentaEntity.getFechaArchivo() == null) {
            throw new ServerException("Por favor, llene los datos obligatorios.");
        }

        this.cajaVentaService.RegistrarCajaVenta(pCajaVentaEntity);
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("message", "Se agregó un nuevo registro de Caja Venta.");
        objectMap.put("status", 200);

        return new ResponseEntity<>(objectMap, HttpStatus.OK);
    }

    @PutMapping("/update/{pCajaVentaId}")
    public ResponseEntity<?> ActualizarCajaVenta(@PathVariable Long pCajaVentaId,
                                                 @Valid @RequestBody CajaVentaEntity pCajaVentaEntity,
                                                 Errors pErrors) {
        CajaVentaEntity vEntity = this.cajaVentaService.ActualizarCajaVenta(pCajaVentaId, pCajaVentaEntity);

        String vRespuesta = "";

        if (pErrors.hasErrors()) {
            vRespuesta = pErrors.getAllErrors().stream()
                    .map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(","));
            return ResponseEntity.badRequest().body(vRespuesta);
        }

        Map<String, Object> objectMap = new HashMap<>();

        if (vEntity.getCajaVentaId() == null) {
            objectMap.put("message", "No se modificó ningun registro de Caja Venta.");
            objectMap.put("status", 204);
            return new ResponseEntity<>(objectMap, HttpStatus.NOT_FOUND);
        } else {
            objectMap.put("message", "Se ha actualizado el registro de Caja Venta.");
            objectMap.put("status", 200);
            return new ResponseEntity<>(objectMap, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{pOperadorId}")
    public ResponseEntity<?> EliminarCajaVenta(@PathVariable Long pCajaVentaId) {
        CajaVentaEntity vCajaVenta = this.cajaVentaService.EliminarCajaVenta(pCajaVentaId);

        if (vCajaVenta == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("message", "Se ha eliminado un registro de Caja Venta.");
        objectMap.put("status", 200);

        return new ResponseEntity<>(objectMap, HttpStatus.OK);
    }

    @GetMapping("/list/{pOperadorId}")
    public ResponseEntity<?> ObtenerOperadores(@PathVariable Long pOperadorId) {
        List<CajaVentaEntity> vListCajasVentas = this.cajaVentaService.ObtenerCajasVentasPorOperador(pOperadorId);

        List<Map<String, Object>> lista = new ArrayList<>();

        for (CajaVentaEntity cajaVenta: vListCajasVentas) {
            Map<String, Object> iterationMap = new LinkedHashMap<>();

            iterationMap.put("cajaVentaId", cajaVenta.getCajaVentaId());
            iterationMap.put("operadorId", cajaVenta.getOperadorId());
            iterationMap.put("numeroCaja", cajaVenta.getNumeroCaja());
            iterationMap.put("nombreCompletoJugador", cajaVenta.getNombreCompletoJugador());
            iterationMap.put("documentoIdentidad", cajaVenta.getDocumentoIdentidad());
            iterationMap.put("lugarExpedicion", cajaVenta.getLugarExpedicion());
            iterationMap.put("direccionJugador", cajaVenta.getDireccionJugador());
            iterationMap.put("fechaNacimiento", cajaVenta.getFechaNacimiento());
            iterationMap.put("numeroFactura", cajaVenta.getNumeroFactura());
            iterationMap.put("numeroAutorizacion", cajaVenta.getNumeroAutorizacion());
            iterationMap.put("fechaFactura", cajaVenta.getFechaFactura());
            iterationMap.put("numeroTicket", cajaVenta.getNumeroTicket());
            iterationMap.put("codigoVerificacion", cajaVenta.getCodigoVerificacion());
            iterationMap.put("codigoControl", cajaVenta.getCodigoControl());
            iterationMap.put("montoTicketComprado", cajaVenta.getMontoTicketComprado());
            iterationMap.put("fechaEmisionTicket", cajaVenta.getFechaEmisionTicket());
            iterationMap.put("montoImporteIva", cajaVenta.getMontoImporteIva());
            iterationMap.put("montoImporteSujetoIj", cajaVenta.getMontoImporteSujetoIj());
            iterationMap.put("montoImporteIj", cajaVenta.getMontoImporteIj());
            iterationMap.put("montoImporteIpj", cajaVenta.getMontoImporteIpj());
            iterationMap.put("montoTotalPagado", cajaVenta.getMontoTotalPagado());
            iterationMap.put("fechaArchivo", cajaVenta.getFechaArchivo());
            iterationMap.put("fechaRegistro", cajaVenta.getFechaRegistro());
            iterationMap.put("estadoId", cajaVenta.getEstadoId());

            lista.add(iterationMap);
        }

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/list_caja_ventas_pagos/{pFechaInicio}/{pFechaFin}")
    public ResponseEntity<?> ObtenerCajasPagosVsCajasVentas(@PathVariable String pFechaInicio,
                                                            @PathVariable String pFechaFin) {

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

            List<CajaVentaPago> vListCajasVentasPagos = this.cajaVentaService.ObtenerCajasPagosVsCajasVentas(vFechaInicioCadena, vFechaFinCadena);

            for (CajaVentaPago cajaVentaPago: vListCajasVentasPagos) {

                Map<String, Object> iterationMap = new LinkedHashMap<>();

                iterationMap.put("nombreCompletoJugador", cajaVentaPago.getNombreCompletoJugador());
                iterationMap.put("documentoIdentidad", cajaVentaPago.getDocumentoIdentidad());
                iterationMap.put("fechaArchivo", cajaVentaPago.getFechaArchivo());
                iterationMap.put("montoTicketComprado", cajaVentaPago.getMontoTicketComprado());
                iterationMap.put("montoTotalPagado", cajaVentaPago.getMontoTotalPagado());
                iterationMap.put("diferencia", cajaVentaPago.getDiferencia());

                lista.add(iterationMap);
            }
        }
        catch(Exception ex) {
            System.out.println(ex.toString());
        }

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/list_njugadores_compran_mas_dia_v2/{pOperadorId}/{pFechaInicio}/{pFechaFin}/{pNumeroJugadores}")
    public ResponseEntity<?> ObtenerNJugadoresCompranMasDiaV2(@PathVariable Long pOperadorId,
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

            List<CajaVentaEntity> vListCajasVentas = this.cajaVentaService.ObtenerNJugadoresCompranMasDiaV2(pOperadorId, vFechaInicioCadena, vFechaFinCadena, pNumeroJugadores);

            for (CajaVentaEntity cajaVenta: vListCajasVentas) {

                Map<String, Object> iterationMap = new LinkedHashMap<>();

                iterationMap.put("nombreCompletoJugador", cajaVenta.getNombreCompletoJugador());
                iterationMap.put("documentoIdentidad", cajaVenta.getDocumentoIdentidad());
                iterationMap.put("cantidadComprasDia", cajaVenta.getCantidadComprasDia());
                iterationMap.put("montoTotalCompras", cajaVenta.getMontoTotalCompras());

                lista.add(iterationMap);
            }
        }
        catch(Exception ex) {
            System.out.println(ex.toString());
        }

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/list_umbral_ventas_fechas_jugador/{pDocumentoIdentidad}/{pFechaInicio}/{pFechaFin}/{pMontoUmbral}")
    public ResponseEntity<?> ObtenerUmbralVentasPorFechasJugador(@PathVariable String pDocumentoIdentidad,
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

            List<CajaVentaEntity> vListCajasVentas = this.cajaVentaService.ObtenerUmbralVentasPorFechasJugador(pDocumentoIdentidad, vFechaInicioCadena, vFechaFinCadena, pMontoUmbral);

            for (CajaVentaEntity cajaVenta: vListCajasVentas) {

                Map<String, Object> iterationMap = new LinkedHashMap<>();

                iterationMap.put("nombreCompletoJugador", cajaVenta.getNombreCompletoJugador());
                iterationMap.put("documentoIdentidad", cajaVenta.getDocumentoIdentidad());
                iterationMap.put("fechaArchivo", cajaVenta.getFechaArchivo());
                iterationMap.put("montoAcumuladoUmbral", cajaVenta.getMontoAcumuladoUmbral());
                iterationMap.put("cantidadComprasDia", cajaVenta.getCantidadComprasDia());

                lista.add(iterationMap);
            }
        }
        catch(Exception ex) {
            System.out.println(ex.toString());
        }

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
}
