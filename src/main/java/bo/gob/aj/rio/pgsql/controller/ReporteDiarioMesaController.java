package bo.gob.aj.rio.pgsql.controller;

import bo.gob.aj.rio.pgsql.entity.ReporteDiarioMesaEntity;
import bo.gob.aj.rio.pgsql.service.ReporteDiarioMesaService;
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
@RequestMapping("/api/reporte_diario_mesa")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReporteDiarioMesaController {

    @Autowired
    private ReporteDiarioMesaService reporteDiarioMesaService;

    @Autowired
    public ReporteDiarioMesaController(ReporteDiarioMesaService pReporteDiarioMesaService) {
        this.reporteDiarioMesaService = pReporteDiarioMesaService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> RegistrarReporteDiarioMesa(@RequestBody ReporteDiarioMesaEntity pReporteDiarioMesaEntity) throws ServerException {

        if (pReporteDiarioMesaEntity.getOperadorId() == null || pReporteDiarioMesaEntity.getFechaArchivo() == null) {
            throw new ServerException("Por favor, llene los datos obligatorios.");
        }

        this.reporteDiarioMesaService.RegistrarReporteDiarioMesa(pReporteDiarioMesaEntity);
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("message", "Se agregó un nuevo registro de Reporte Diario Mesa.");
        objectMap.put("status", 200);

        return new ResponseEntity<>(objectMap, HttpStatus.OK);
    }

    @PutMapping("/update/{pReporteDiarioMesaId}")
    public ResponseEntity<?> ActualizarReporteDiarioMesa(@PathVariable Long pReporteDiarioMesaId,
                                                         @Valid @RequestBody ReporteDiarioMesaEntity pReporteDiarioMesaEntity,
                                                         Errors pErrors) {
        ReporteDiarioMesaEntity vEntity = this.reporteDiarioMesaService.ActualizarReporteDiarioMesa(pReporteDiarioMesaId, pReporteDiarioMesaEntity);

        String vRespuesta = "";

        if (pErrors.hasErrors()) {
            vRespuesta = pErrors.getAllErrors().stream()
                    .map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(","));
            return ResponseEntity.badRequest().body(vRespuesta);
        }

        Map<String, Object> objectMap = new HashMap<>();

        if (vEntity.getReporteDiarioMesaId() == null) {
            objectMap.put("message", "No se modificó ningun registro de Reporte Diario Mesas.");
            objectMap.put("status", 204);
            return new ResponseEntity<>(objectMap, HttpStatus.NOT_FOUND);
        }
        else {
            objectMap.put("message", "Se ha actualizado el registro de Reporte Diario Mesas.");
            objectMap.put("status", 200);
            return new ResponseEntity<>(objectMap, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{pReporteDiarioMesaId}")
    public ResponseEntity<?> EliminarReporteDiarioMesa(@PathVariable Long pReporteDiarioMesaId) {
        ReporteDiarioMesaEntity vReporteDiario = this.reporteDiarioMesaService.EliminarReporteDiarioMesa(pReporteDiarioMesaId);

        if (vReporteDiario == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("message", "Se ha eliminado un registro de Reporte Diario Mesas.");
        objectMap.put("status", 200);

        return new ResponseEntity<>(objectMap, HttpStatus.OK);
    }

    @GetMapping("/list/{pOperadorId}")
    public ResponseEntity<?> ObtenerReportesDiariosMesas(@PathVariable Long pOperadorId) {
        List<ReporteDiarioMesaEntity> vListReportesDiarios = this.reporteDiarioMesaService.ObtenerReporteDiarioMesaPorOperador(pOperadorId);

        List<Map<String, Object>> lista = new ArrayList<>();

        for (ReporteDiarioMesaEntity reporteDiario: vListReportesDiarios) {
            Map<String, Object> iterationMap = new LinkedHashMap<>();

            iterationMap.put("reporteDiarioMesaId", reporteDiario.getReporteDiarioMesaId());
            iterationMap.put("operadorId", reporteDiario.getOperadorId());
            iterationMap.put("numeroPrecinto", reporteDiario.getNumeroPrecinto());
            iterationMap.put("identificacionMesa", reporteDiario.getIdentificacionMesa());
            iterationMap.put("juego", reporteDiario.getJuego());
            iterationMap.put("apertura", reporteDiario.getApertura());
            iterationMap.put("relleno", reporteDiario.getRelleno());
            iterationMap.put("devolucion", reporteDiario.getDevolucion());
            iterationMap.put("cierre", reporteDiario.getCierre());
            iterationMap.put("cantidadTicketsAutorizados", reporteDiario.getCantidadTicketsAutorizados());
            iterationMap.put("montoTicketsAutorizados", reporteDiario.getMontoTicketsAutorizados());
            iterationMap.put("premios", reporteDiario.getPremios());
            iterationMap.put("resultado", reporteDiario.getResultado());
            iterationMap.put("fechaArchivo", reporteDiario.getFechaArchivo());
            iterationMap.put("fechaRegistro", reporteDiario.getFechaRegistro());
            iterationMap.put("estadoId", reporteDiario.getEstadoId());

            lista.add(iterationMap);
        }

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
}
