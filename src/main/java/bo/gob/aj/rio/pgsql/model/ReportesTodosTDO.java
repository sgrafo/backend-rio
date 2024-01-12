package bo.gob.aj.rio.pgsql.model;

import lombok.Data;

import java.util.List;
@Data
public class ReportesTodosTDO {
    private List<CajaVentaReporteDTO> cajaVentaReporteTDO;
    private List<CajaPagosReporteDTO> cajaPagosReporteDTO;
    private  List<TransaccionesMesaReporteDTO> transaccionMesaDTO;
    private List<ReporteDiarioMesaReporteDTO> repDiarioMesaDTO;
    private  List<FacturasAnuladasDTO> factAnuladaDTO;
}
