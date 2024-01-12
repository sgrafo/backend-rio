package bo.gob.aj.rio.pgsql.repository;

import bo.gob.aj.rio.pgsql.entity.CajaVentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ICajaVentaRepository extends JpaRepository<CajaVentaEntity, Long> {

    @Query(value = "SELECT c FROM CajaVentaEntity c WHERE c.estadoId = 1000 AND c.operadorId = :pOperadorId")
    List<CajaVentaEntity> ObtenerCajasVentasPorOperador(@Param("pOperadorId") Long pOperadorId);

    @Query(value = "SELECT c FROM CajaVentaEntity c " +
                   "WHERE c.estadoId = 1000 AND c.fechaArchivo BETWEEN DATE(:pFechaInicio) AND DATE(:pFechaFinal)")
    List<CajaVentaEntity> ObtenerCajasVentasPorFechas(@Param("pFechaInicio") String pFechaInicio,
                                                      @Param("pFechaFinal") String pFechaFinal);

    // Obtener Cajas Pagos Vs Cajas Ventas y su diferencia por rango de fechas
    @Query(value = "WITH cajas_ventas_tmp AS ( " +
                   "  SELECT " +
                   "    TRIM(UPPER(c.nombre_completo_jugador)) as nombre_completo_jugador, " +
                   "    TRIM(UPPER(c.documento_identidad)) as documento_identidad, " +
                   "    c.fecha_archivo, " +
                   "    SUM(c.monto_ticket_comprado) AS monto_ticket_comprado " +
                   "  FROM rio.cajas_ventas c " +
                   "  WHERE UPPER(c.nombre_completo_jugador) NOT IN ('NULO') " +
                   "  AND c.estado_id = 1000 AND ((:pFechaInicio = '1900-01-01' AND :pFechaFin = '1900-01-01' AND 1 = 1) OR c.fecha_archivo BETWEEN DATE(:pFechaInicio) AND DATE(:pFechaFin)) " +
                   "  GROUP BY nombre_completo_jugador, documento_identidad, c.fecha_archivo " +
                   "), cajas_pagos_tmp AS ( " +
                   "   SELECT " +
                    "    TRIM(UPPER(p.nombre_completo_jugador)) as nombre_completo_jugador, " +
                    "    TRIM(UPPER(p.documento_identidad)) as documento_identidad, " +
                    "    p.fecha_archivo, " +
                    "    SUM(p.monto_total_pagado) AS monto_total_pagado " +
                    "  FROM rio.cajas_pagos p " +
                    "  WHERE UPPER(p.nombre_completo_jugador) NOT IN ('NULO') " +
                    "  AND p.estado_id = 1000 AND ((:pFechaInicio = '1900-01-01' AND :pFechaFin = '1900-01-01' AND 1 = 1) OR p.fecha_archivo BETWEEN DATE(:pFechaInicio) AND DATE(:pFechaFin) )" +
                    "  GROUP BY nombre_completo_jugador, documento_identidad, p.fecha_archivo " +
                    ") " +
                    "SELECT " +
                    "    c.nombre_completo_jugador, " +
                    "    c.documento_identidad, " +
                    "    c.fecha_archivo, " +
                    "    COALESCE(c.monto_ticket_comprado, 0) AS monto_ticket_comprado, " +
                    "    COALESCE(p.monto_total_pagado, 0) AS monto_total_pagado, " +
                    "    (COALESCE(c.monto_ticket_comprado, 0) - COALESCE(p.monto_total_pagado, 0)) AS diferencia " +
                    "FROM cajas_ventas_tmp c " +
                    "LEFT JOIN cajas_pagos_tmp p " +
                    "  ON c.nombre_completo_jugador = p.nombre_completo_jugador " +
                    "  AND c.documento_identidad = p.documento_identidad " +
                    "  AND c.fecha_archivo = p.fecha_archivo " +
                    "ORDER BY 3, 1", nativeQuery = true)
    List<Object[]> ObtenerCajasPagosVsCajasVentasNormal(@Param("pFechaInicio") String pFechaInicio,
                                                        @Param("pFechaFin") String pFechaFin);

    @Query(value = "WITH cajas_ventas_tmp AS ( " +
                    "  SELECT " +
                    "    MAX(TRIM(UPPER(c.nombre_completo_jugador))) as nombre_completo_jugador, " +
                    "    TRIM(UPPER(c.documento_identidad)) as documento_identidad, " +
                    "    c.fecha_archivo, " +
                    "    SUM(c.monto_ticket_comprado) AS monto_ticket_comprado " +
                    "  FROM rio.cajas_ventas c " +
                    "  WHERE UPPER(c.nombre_completo_jugador) NOT IN ('NULO') " +
                    "  AND c.estado_id = 1000 AND ((:pFechaInicio = '1900-01-01' AND :pFechaFin = '1900-01-01' AND 1 = 1) OR c.fecha_archivo BETWEEN DATE(:pFechaInicio) AND DATE(:pFechaFin)) " +
                    "  GROUP BY 2, c.fecha_archivo " +
                    "), cajas_pagos_tmp AS ( " +
                    "   SELECT " +
                    "    MAX(TRIM(UPPER(p.nombre_completo_jugador))) as nombre_completo_jugador, " +
                    "    TRIM(UPPER(p.documento_identidad)) as documento_identidad, " +
                    "    p.fecha_archivo, " +
                    "    SUM(p.monto_total_pagado) AS monto_total_pagado " +
                    "  FROM rio.cajas_pagos p " +
                    "  WHERE UPPER(p.nombre_completo_jugador) NOT IN ('NULO') " +
                    "  AND p.estado_id = 1000 AND ((:pFechaInicio = '1900-01-01' AND :pFechaFin = '1900-01-01' AND 1 = 1) OR p.fecha_archivo BETWEEN DATE(:pFechaInicio) AND DATE(:pFechaFin) )" +
                    "  GROUP BY 2, p.fecha_archivo " +
                    ") " +
                    "SELECT " +
                    "    c.nombre_completo_jugador, " +
                    "    c.documento_identidad, " +
                    "    c.fecha_archivo, " +
                    "    COALESCE(c.monto_ticket_comprado, 0) AS monto_ticket_comprado, " +
                    "    COALESCE(p.monto_total_pagado, 0) AS monto_total_pagado, " +
                    "    (COALESCE(c.monto_ticket_comprado, 0) - COALESCE(p.monto_total_pagado, 0)) AS diferencia " +
                    "FROM cajas_ventas_tmp c " +
                    "LEFT JOIN cajas_pagos_tmp p " +
                    "  ON c.documento_identidad = p.documento_identidad " +
                    "  AND c.fecha_archivo = p.fecha_archivo " +
                    "ORDER BY 3, 1", nativeQuery = true)
    List<Object[]> ObtenerCajasPagosVsCajasVentasOptimizado(@Param("pFechaInicio") String pFechaInicio,
                                                            @Param("pFechaFin") String pFechaFin);

    @Query(value = "SELECT c.nombreCompletoJugador, c.documentoIdentidad, COUNT(c) AS cantidadComprasDia, SUM(c.montoTicketComprado) AS montoTotalCompras " +
                    "FROM CajaVentaEntity c " +
                    "WHERE UPPER(c.nombreCompletoJugador) NOT IN ('NULO') " +
                    "AND c.estadoId = 1000 " +
                    "AND ((:pOperadorId < 1L AND 1 = 1) OR (:pOperadorId > 0L AND c.operadorId = :pOperadorId)) " +
                    "AND ((:pFechaInicio = '1900-01-01' AND :pFechaFin = '1900-01-01' AND 1 = 1) OR (:pFechaInicio <> '1900-01-01' AND :pFechaFin <> '1900-01-01' AND c.fechaArchivo BETWEEN DATE(:pFechaInicio) AND DATE(:pFechaFin))) " +
                    "GROUP BY c.nombreCompletoJugador, c.documentoIdentidad " +
                    "ORDER BY montoTotalCompras DESC")
    List<Object[]> ObtenerNJugadoresCompranMasDiaNormal(@Param("pOperadorId") Long pOperadorId,
                                                        @Param("pFechaInicio") String pFechaInicio,
                                                        @Param("pFechaFin") String pFechaFin);

    @Query(value = "SELECT MAX(UPPER(c.nombreCompletoJugador)) AS nombreCompletoJugador, UPPER(c.documentoIdentidad) AS documentoIdentidad, COUNT(c) AS cantidadComprasDia, SUM(c.montoTicketComprado) AS montoTotalCompras " +
                    "FROM CajaVentaEntity c " +
                    "WHERE UPPER(c.nombreCompletoJugador) NOT IN ('NULO') " +
                    "AND c.estadoId = 1000 " +
                    "AND ((:pOperadorId < 1L AND 1 = 1) OR (:pOperadorId > 0L AND c.operadorId = :pOperadorId)) " +
                    "AND ((:pFechaInicio = '1900-01-01' AND :pFechaFin = '1900-01-01' AND 1 = 1) OR (:pFechaInicio <> '1900-01-01' AND :pFechaFin <> '1900-01-01' AND c.fechaArchivo BETWEEN DATE(:pFechaInicio) AND DATE(:pFechaFin))) " +
                    "GROUP BY 2 " +
                    "ORDER BY montoTotalCompras DESC")
    List<Object[]> ObtenerNJugadoresCompranMasDiaOptimizado(@Param("pOperadorId") Long pOperadorId,
                                                            @Param("pFechaInicio") String pFechaInicio,
                                                            @Param("pFechaFin") String pFechaFin);

    @Query(value = "SELECT c.nombreCompletoJugador, c.documentoIdentidad, c.fechaArchivo, c.montoTicketComprado " +
                   "FROM CajaVentaEntity c " +
                   "WHERE UPPER(c.nombreCompletoJugador) NOT IN ('NULO') " +
                   "AND c.estadoId = 1000 AND " +
                   "((:pFechaInicio = '1900-01-01' AND :pFechaFin = '1900-01-01' AND 1 = 1) OR (:pFechaInicio <> '1900-01-01' AND :pFechaFin <> '1900-01-01' AND c.fechaArchivo BETWEEN DATE(:pFechaInicio) AND DATE(:pFechaFin))) " +
                   "AND c.fechaArchivo IS NOT NULL " +
                   "AND UPPER(TRIM(c.documentoIdentidad)) = UPPER(TRIM(:pDocumentoIdentidad)) " +
                   "ORDER BY c.fechaArchivo")
    List<Object[]> ObtenerUmbralVentasPorFechasJugador(@Param("pFechaInicio") String pFechaInicio,
                                                       @Param("pFechaFin") String pFechaFin,
                                                       @Param("pDocumentoIdentidad") String pDocumentoIdentidad);
}
