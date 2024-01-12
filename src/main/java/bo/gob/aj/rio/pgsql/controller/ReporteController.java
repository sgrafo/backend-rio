package bo.gob.aj.rio.pgsql.controller;

import bo.gob.aj.rio.pgsql.model.ReportesTodosTDO;
import bo.gob.aj.rio.pgsql.service.OperadorService;
import bo.gob.aj.rio.pgsql.service.ReportesService;
import bo.gob.aj.rio.util.Fechas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReporteController {

    @Autowired
    private OperadorService operadorService;
    private ReportesService reportesService;

    @Autowired
    public ReporteController(OperadorService pOperadorService,ReportesService pReportesService) {
        this.operadorService = pOperadorService;
        this.reportesService = pReportesService;
    }

    @GetMapping("/filtroDatosTodos/{operadorId}/{pFechaInicio}/{pFechaFinal}")
    public ResponseEntity<?> ObtenerDatosTodos(@PathVariable String operadorId, @PathVariable String pFechaInicio, @PathVariable String pFechaFinal) {
        ReportesTodosTDO objRepTodos = new ReportesTodosTDO();
        try{

            Long opId = (operadorId!="0")?Long.parseLong(operadorId):null;
            Timestamp fechaIni = (pFechaInicio!="-")? Fechas.ConvertirFechaParametroTimestamp(pFechaInicio) :null;
            Timestamp fechaFin = (pFechaFinal!="-")? Fechas.ConvertirFechaParametroTimestamp(pFechaFinal) :null;
            objRepTodos.setCajaVentaReporteTDO(this.reportesService.obtenerCajaVentasTodos(opId,fechaIni,fechaFin));
            objRepTodos.setCajaPagosReporteDTO(this.reportesService.obtenerCajaPagosTodos(opId,fechaIni,fechaFin));
            objRepTodos.setTransaccionMesaDTO(this.reportesService.obtenerTransaccionesMesaTodos(opId,fechaIni,fechaFin));
            objRepTodos.setRepDiarioMesaDTO(this.reportesService.obtenerRepDiarioMesaTodos(opId,fechaIni,fechaFin));
            objRepTodos.setFactAnuladaDTO(this.reportesService.obtenerFacturaAnuladasTodos(opId,fechaIni,fechaFin));

            return new ResponseEntity<>(objRepTodos, HttpStatus.OK);


        }catch (Exception ex){
            return new ResponseEntity<>(ex.toString(), HttpStatus.NOT_FOUND);
        }
    }
}
