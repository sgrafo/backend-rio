package bo.gob.aj.rio.pgsql.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(schema = "rio", name = "transacciones_mesas")
@SequenceGenerator(name = "rio.transacciones_mesas_transaccion_mesa_id_seq", sequenceName = "rio.transacciones_mesas_transaccion_mesa_id_seq", allocationSize = 1)
@Data
public class TransaccionMesaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rio.transacciones_mesas_transaccion_mesa_id_seq")
    @Column(name = "transaccion_mesa_id")
    private Long transaccionMesaId;

    @Column(name = "operador_id")
    private Long operadorId;

    @Column(name = "codigo_mesa")
    private String codigoMesa;

    @Column(name = "fecha_registro_real")
    private Timestamp fechaRegistroReal;

    @Column(name = "hora_registro")
    private String horaRegistro;

    @Column(name = "numero_formulario")
    private String numeroFormulario;

    @Column(name = "tipo_transaccion")
    private String tipoTransaccion;

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
}
