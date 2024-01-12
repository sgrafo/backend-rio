package bo.gob.aj.rio.pgsql.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(schema = "rio", name = "cajas_pagos")
@SequenceGenerator(name = "rio.cajas_pagos_caja_pago_id_seq", sequenceName = "rio.cajas_pagos_caja_pago_id_seq", allocationSize = 1)
@Data
public class CajaPagoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rio.cajas_pagos_caja_pago_id_seq")
    @Column(name = "caja_pago_id")
    private Long cajaPagoId;

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

    @Column(name = "numero_comprobante")
    private Integer numeroComprobante;

    @Column(name = "fecha_pago")
    private Timestamp fechaPago;

    @Column(name = "hora_pago")
    private String horaPago;

    @Column(name = "denominacion_ficha_1000")
    private Integer denominacionFicha1000;

    @Column(name = "cantidad_fichas_1000")
    private Integer cantidadFichas1000;

    @Column(name = "denominacion_ficha_500")
    private Integer denominacionFicha500;

    @Column(name = "cantidad_fichas_500")
    private Integer cantidadFichas500;

    @Column(name = "denominacion_ficha_200")
    private Integer denominacionFicha200;

    @Column(name = "cantidad_fichas_200")
    private Integer cantidadFichas200;

    @Column(name = "denominacion_ficha_100")
    private Integer denominacionFicha100;

    @Column(name = "cantidad_fichas_100")
    private Integer cantidadFichas100;

    @Column(name = "denominacion_ficha_50")
    private Integer denominacionFicha50;

    @Column(name = "cantidad_fichas_50")
    private Integer cantidadFichas50;

    @Column(name = "denominacion_ficha_20")
    private Integer denominacionFicha20;

    @Column(name = "cantidad_fichas_20")
    private Integer cantidadFichas20;

    @Column(name = "denominacion_ficha_10")
    private Integer denominacionFicha10;

    @Column(name = "cantidad_fichas_10")
    private Integer cantidadFichas10;

    @Column(name = "denominacion_ficha_5")
    private Integer denominacionFicha5;

    @Column(name = "cantidad_fichas_5")
    private Integer cantidadFichas5;

    @Column(name = "denominacion_ficha_1")
    private Integer denominacionFicha1;

    @Column(name = "cantidad_fichas_1")
    private Integer cantidadFichas1;

    @Column(name = "monto_total_pagado", precision = 18, scale = 2)
    private BigDecimal montoTotalPagado;

    @Column(name = "fecha_archivo")
    private Timestamp fechaArchivo;

    @Column(name = "fecha_registro")
    private Timestamp fechaRegistro;

    @Column(name = "estado_id")
    private Integer estadoId;

    @Transient
    private Integer cantidadGananciaDia;

    @Transient
    private BigDecimal montoTotalPagadoSuma;
}
