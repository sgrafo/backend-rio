package bo.gob.aj.rio.pgsql.model;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class FacturasAnuladasDTO {
    private String nombre_empresa;
    private String nombre_salon;


    private Long operador_id;
    private Integer numero_caja;
    private String nombre_completo_jugador;
    private String documento_identidad;
    private String lugar_expedicion;
    private String numero_factura_anulada;
    private Long numero_autorizacion;
    private Timestamp fecha_factura_anulada;
    private BigDecimal monto_total_factura_anulada;
    private Integer numero_ticket;
    private BigDecimal monto_total_ticket;
    private Timestamp fecha_factura_nueva;
    private String numero_factura_nueva;
    private BigDecimal monto_total_factura_nueva;
    private Timestamp fecha_archivo;
    private Timestamp fecha_registro;
    private Integer estado_id;
    private  Long factura_anulada_id;

    public FacturasAnuladasDTO(String nombre_empsare, String nombre_salon, Long operador_id, Integer numero_caja, String nombre_completo_jugador, String documento_identidad, String lugar_expedicion, String numero_factura_anulada, Long numero_autorizacion, Timestamp fecha_factura_anulada, BigDecimal monto_total_factura_anulada, Integer numero_ticket, BigDecimal monto_total_ticket, Timestamp fecha_factura_nueva, String numero_factura_nueva, BigDecimal monto_total_factura_nueva, Timestamp fecha_archivo, Timestamp fecha_registro, Integer estado_id,Long factura_anulada_id) {
        this.factura_anulada_id = factura_anulada_id;
        this.nombre_empresa = nombre_empsare;
        this.nombre_salon = nombre_salon;
        this.operador_id = operador_id;
        this.numero_caja = numero_caja;
        this.nombre_completo_jugador = nombre_completo_jugador;
        this.documento_identidad = documento_identidad;
        this.lugar_expedicion = lugar_expedicion;
        this.numero_factura_anulada = numero_factura_anulada;
        this.numero_autorizacion = numero_autorizacion;
        this.fecha_factura_anulada = fecha_factura_anulada;
        this.monto_total_factura_anulada = monto_total_factura_anulada;
        this.numero_ticket = numero_ticket;
        this.monto_total_ticket = monto_total_ticket;
        this.fecha_factura_nueva = fecha_factura_nueva;
        this.numero_factura_nueva = numero_factura_nueva;
        this.monto_total_factura_nueva = monto_total_factura_nueva;
        this.fecha_archivo = fecha_archivo;
        this.fecha_registro = fecha_registro;
        this.estado_id = estado_id;
    }
}
