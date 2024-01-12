package bo.gob.aj.rio.pgsql.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(schema = "rio", name = "cajas_ventas")
@SequenceGenerator(name = "rio.cajas_ventas_caja_venta_id_seq", sequenceName = "rio.cajas_ventas_caja_venta_id_seq", allocationSize = 1)
@Data
public class CajaVentaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rio.cajas_ventas_caja_venta_id_seq")
    @Column(name = "caja_venta_id")
    private Long cajaVentaId;

    @Column(name = "operador_id")
    private Long operadorId;

    @Column(name = "numero_caja")
    private Integer numeroCaja;

    @Column(name = "nombre_completo_jugador")
    private String nombreCompletoJugador;

    @Column(name = "documento_identidad")
    private String documentoIdentidad;

    @Column(name = "lugar_expedicion")
    private String lugarExpedicion;

    @Column(name = "direccion_jugador")
    private String direccionJugador;

    @Column(name = "fecha_nacimiento")
    private Timestamp fechaNacimiento;

    @Column(name = "numero_factura")
    private String numeroFactura;

    @Column(name = "numero_autorizacion")
    private String numeroAutorizacion;

    @Column(name = "fecha_factura")
    private Timestamp fechaFactura;

    @Column(name = "numero_ticket")
    private String numeroTicket;

    @Column(name = "codigo_verificacion")
    private String codigoVerificacion;

    @Column(name = "codigo_control")
    private String codigoControl;

    @Column(name = "monto_ticket_comprado", precision = 18, scale = 2)
    private BigDecimal montoTicketComprado;

    @Column(name = "fecha_emision_ticket")
    private Timestamp fechaEmisionTicket;

    @Column(name = "monto_importe_iva", precision = 18, scale = 2)
    private BigDecimal montoImporteIva;

    @Column(name = "monto_importe_sujeto_ij", precision = 18, scale = 2)
    private BigDecimal montoImporteSujetoIj;

    @Column(name = "monto_importe_ij", precision = 18, scale = 2)
    private BigDecimal montoImporteIj;

    @Column(name = "monto_importe_ipj", precision = 18, scale = 2)
    private BigDecimal montoImporteIpj;

    @Column(name = "monto_total_pagado", precision = 18, scale = 2)
    private BigDecimal montoTotalPagado;

    @Column(name = "fecha_archivo")
    private Timestamp fechaArchivo;

    @Column(name = "fecha_registro")
    private Timestamp fechaRegistro;

    @Column(name = "estado_id")
    private Integer estadoId;

    @Transient
    private BigDecimal montoAcumuladoDia;

    @Transient
    private BigDecimal montoAcumuladoMes;

    @Transient
    private BigDecimal montoAcumuladoUmbral;

    @Transient
    private Integer cantidadComprasDia;

    @Transient
    private BigDecimal montoTotalCompras;
}
