package bo.gob.aj.rio.pgsql.model;

import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class CajaVentaReporteDTO {
    private String nombre_empresa;
    private String nombre_salon;
    private Long operador_id;
    private Integer numero_caja;
    private String nombre_completo_jugador;
    private String documento_identidad;
    private String lugar_expedicion;
    private String direccion_jugador;
    private Timestamp fecha_nacimiento;
    private String numero_factura;
    private String numero_autorizacion;
    private Timestamp fecha_factura;
    private String numero_ticket;
    private String codigo_verificacion;
    private String codigo_control;
    private BigDecimal monto_ticket_comprado;
    private Timestamp fecha_emision_ticket;
    private BigDecimal monto_importe_iva;
    private BigDecimal monto_importe_sujeto_ij;
    private BigDecimal monto_importe_ij;
    private BigDecimal monto_importe_ipj;
    private BigDecimal monto_total_pagado;
    private Timestamp fecha_archivo;
    private Timestamp fecha_registro;
    private Integer estado_id;
    private Long caja_venta_id;

    public CajaVentaReporteDTO(String nombre_empresa, String nombre_salon, Long operador_id, Integer numero_caja, String nombre_completo_jugador, String documento_identidad, String lugar_expedicion, String direccion_jugador, Timestamp fecha_nacimiento, String numero_factura, String numero_autorizacion, Timestamp fecha_factura, String numero_ticket, String codigo_verificacion, String codigo_control, BigDecimal monto_ticket_comprado, Timestamp fecha_emision_ticket, BigDecimal monto_importe_iva, BigDecimal monto_importe_sujeto_ij, BigDecimal monto_importe_ij, BigDecimal monto_importe_ipj, BigDecimal monto_total_pagado, Timestamp fecha_archivo, Timestamp fecha_registro, Integer estado_id, Long caja_venta_id) {
        this.nombre_empresa = nombre_empresa;
        this.nombre_salon = nombre_salon;
        this.operador_id = operador_id;
        this.numero_caja = numero_caja;
        this.nombre_completo_jugador = nombre_completo_jugador;
        this.documento_identidad = documento_identidad;
        this.lugar_expedicion = lugar_expedicion;
        this.direccion_jugador = direccion_jugador;
        this.fecha_nacimiento = fecha_nacimiento;
        this.numero_factura = numero_factura;
        this.numero_autorizacion = numero_autorizacion;
        this.fecha_factura = fecha_factura;
        this.numero_ticket = numero_ticket;
        this.codigo_verificacion = codigo_verificacion;
        this.codigo_control = codigo_control;
        this.monto_ticket_comprado = monto_ticket_comprado;
        this.fecha_emision_ticket = fecha_emision_ticket;
        this.monto_importe_iva = monto_importe_iva;
        this.monto_importe_sujeto_ij = monto_importe_sujeto_ij;
        this.monto_importe_ij = monto_importe_ij;
        this.monto_importe_ipj = monto_importe_ipj;
        this.monto_total_pagado = monto_total_pagado;
        this.fecha_archivo = fecha_archivo;
        this.fecha_registro = fecha_registro;
        this.estado_id = estado_id;
        this.caja_venta_id = caja_venta_id;
    }
}
