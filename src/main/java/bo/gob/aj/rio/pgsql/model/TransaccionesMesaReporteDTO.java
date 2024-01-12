package bo.gob.aj.rio.pgsql.model;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class TransaccionesMesaReporteDTO {
    private String nombre_empresa;
    private String nombre_salon;
    private Long operador_id;
    private String codigo_mesa;
    private Timestamp fecha_registro_real;
    private String hora_registro;
    private String numero_formulario;
    private String tipo_transaccion;
    private Integer denominacion_ficha_1000;
    private Integer cantidad_fichas_1000;
    private Integer denominacion_ficha_500;
    private Integer cantidad_fichas_500;
    private Integer denominacion_ficha_200;
    private Integer cantidad_fichas_200;
    private Integer denominacion_ficha_100;
    private Integer cantidad_fichas_100;
    private Integer denominacion_ficha_50;
    private Integer cantidad_fichas_50;
    private Integer denominacion_ficha_20;
    private Integer cantidad_fichas_20;
    private Integer denominacion_ficha_10;
    private Integer cantidad_fichas_10;
    private Integer denominacion_ficha_5;
    private Integer cantidad_fichas_5;
    private Integer denominacion_ficha_1;
    private Integer cantidad_fichas_1;
    private BigDecimal monto_total_pagado;
    private Timestamp fecha_archivo;
    private Timestamp fecha_registro;
    private Integer estado_id;
    private Long transaccion_mesa_id;

    public TransaccionesMesaReporteDTO(String nombre_empsare, String nombre_salon, Long operador_id, String codigo_mesa, Timestamp fecha_registro_real, String hora_registro, String numero_formulario, String tipo_transaccion, Integer denominacion_ficha_1000, Integer cantidad_fichas_1000, Integer denominacion_ficha_500, Integer cantidad_fichas_500, Integer denominacion_ficha_200, Integer cantidad_fichas_200, Integer denominacion_ficha_100, Integer cantidad_fichas_100, Integer denominacion_ficha_50, Integer cantidad_fichas_50, Integer denominacion_ficha_20, Integer cantidad_fichas_20, Integer denominacion_ficha_10, Integer cantidad_fichas_10, Integer denominacion_ficha_5, Integer cantidad_fichas_5, Integer denominacion_ficha_1, Integer cantidad_fichas_1, BigDecimal monto_total_pagado, Timestamp fecha_archivo, Timestamp fecha_registro, Integer estado_id , Long transaccion_mesa_id) {
        this.transaccion_mesa_id = transaccion_mesa_id;
        this.nombre_empresa = nombre_empsare;
        this.nombre_salon = nombre_salon;
        this.operador_id = operador_id;
        this.codigo_mesa = codigo_mesa;
        this.fecha_registro_real = fecha_registro_real;
        this.hora_registro = hora_registro;
        this.numero_formulario = numero_formulario;
        this.tipo_transaccion = tipo_transaccion;
        this.denominacion_ficha_1000 = denominacion_ficha_1000;
        this.cantidad_fichas_1000 = cantidad_fichas_1000;
        this.denominacion_ficha_500 = denominacion_ficha_500;
        this.cantidad_fichas_500 = cantidad_fichas_500;
        this.denominacion_ficha_200 = denominacion_ficha_200;
        this.cantidad_fichas_200 = cantidad_fichas_200;
        this.denominacion_ficha_100 = denominacion_ficha_100;
        this.cantidad_fichas_100 = cantidad_fichas_100;
        this.denominacion_ficha_50 = denominacion_ficha_50;
        this.cantidad_fichas_50 = cantidad_fichas_50;
        this.denominacion_ficha_20 = denominacion_ficha_20;
        this.cantidad_fichas_20 = cantidad_fichas_20;
        this.denominacion_ficha_10 = denominacion_ficha_10;
        this.cantidad_fichas_10 = cantidad_fichas_10;
        this.denominacion_ficha_5 = denominacion_ficha_5;
        this.cantidad_fichas_5 = cantidad_fichas_5;
        this.denominacion_ficha_1 = denominacion_ficha_1;
        this.cantidad_fichas_1 = cantidad_fichas_1;
        this.monto_total_pagado = monto_total_pagado;
        this.fecha_archivo = fecha_archivo;
        this.fecha_registro = fecha_registro;
        this.estado_id = estado_id;
    }
}
