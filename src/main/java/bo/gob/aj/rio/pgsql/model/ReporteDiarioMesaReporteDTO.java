package bo.gob.aj.rio.pgsql.model;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class ReporteDiarioMesaReporteDTO {
    private String nombre_empresa;
    private String nombre_salon;
    private Long operador_id;
    private Integer numero_precinto;
    private String identificacion_mesa;
    private String juego;
    private BigDecimal apertura;
    private BigDecimal relleno;
    private BigDecimal devolucion;
    private BigDecimal cierre;
    private Integer cantidad_tickets_autorizados;
    private BigDecimal monto_tickets_autorizados;
    private BigDecimal premios;
    private BigDecimal resultado;
    private Timestamp fecha_archivo;
    private Timestamp fecha_registro;
    private Integer estado_id;
    private Long reporte_diario_mesa_id;

    public ReporteDiarioMesaReporteDTO(String nombre_empsare, String nombre_salon, Long operador_id, Integer numero_precinto, String identificacion_mesa, String juego, BigDecimal apertura, BigDecimal relleno, BigDecimal devolucion, BigDecimal cierre, Integer cantidad_tickets_autorizados, BigDecimal monto_tickets_autorizados, BigDecimal premios, BigDecimal resultado, Timestamp fecha_archivo, Timestamp fecha_registro, Integer estado_id, Long reporte_diario_mesa_id) {
        this.nombre_empresa = nombre_empsare;
        this.nombre_salon = nombre_salon;
        this.operador_id = operador_id;
        this.numero_precinto = numero_precinto;
        this.identificacion_mesa = identificacion_mesa;
        this.juego = juego;
        this.apertura = apertura;
        this.relleno = relleno;
        this.devolucion = devolucion;
        this.cierre = cierre;
        this.cantidad_tickets_autorizados = cantidad_tickets_autorizados;
        this.monto_tickets_autorizados = monto_tickets_autorizados;
        this.premios = premios;
        this.resultado = resultado;
        this.fecha_archivo = fecha_archivo;
        this.fecha_registro = fecha_registro;
        this.estado_id = estado_id;
        this.reporte_diario_mesa_id = reporte_diario_mesa_id;
    }
}
