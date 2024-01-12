package bo.gob.aj.rio.pgsql.repository;

import bo.gob.aj.rio.pgsql.entity.CajaPagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICajaPagoRepository extends JpaRepository<CajaPagoEntity, Long> {

    @Query(value = "SELECT c FROM CajaPagoEntity c WHERE c.estadoId = 1000 AND c.operadorId = :pOperadorId")
    List<CajaPagoEntity> ObtenerCajasPagosPorOperador(@Param("pOperadorId") Long pOperadorId);

    @Query(value = "SELECT c FROM CajaPagoEntity c " +
                   "WHERE c.estadoId = 1000 AND c.fechaArchivo BETWEEN DATE(:pFechaInicio) AND DATE(:pFechaFinal)")
    List<CajaPagoEntity> ObtenerCajasPagosPorFechas(@Param("pFechaInicio") String pFechaInicio,
                                                    @Param("pFechaFinal") String pFechaFinal);

    @Query(value = "SELECT c.nombreCompletoJugador, c.documentoIdentidad, COUNT(c) AS cantidadGananciaDia, SUM(c.montoTotalPagado) AS montoTotalPagadoSuma " +
                   "FROM CajaPagoEntity c " +
                   "WHERE UPPER(c.nombreCompletoJugador) NOT IN ('NULO') " +
                   "AND c.estadoId = 1000 " +
                   "AND ((:pOperadorId < 1L AND 1 = 1) OR (:pOperadorId > 0L AND c.operadorId = :pOperadorId)) " +
                   "AND ((:pFechaInicio = '1900-01-01' AND :pFechaFin = '1900-01-01' AND 1 = 1) OR (:pFechaInicio <> '1900-01-01' AND :pFechaFin <> '1900-01-01' AND c.fechaArchivo BETWEEN DATE(:pFechaInicio) AND DATE(:pFechaFin))) " +
                   "GROUP BY c.nombreCompletoJugador, c.documentoIdentidad " +
                   "ORDER BY montoTotalPagadoSuma DESC")
    List<Object[]> ObtenerNJugadoresGananMasDiaNormal(@Param("pOperadorId") Long pOperadorId,
                                                      @Param("pFechaInicio") String pFechaInicio,
                                                      @Param("pFechaFin") String pFechaFin);

    @Query(value = "SELECT MAX(UPPER(c.nombreCompletoJugador)) AS nombreCompletoJugador, UPPER(c.documentoIdentidad) AS documentoIdentidad, COUNT(c) AS cantidadGananciaDia, SUM(c.montoTotalPagado) AS montoTotalPagadoSuma " +
                    "FROM CajaPagoEntity c " +
                    "WHERE UPPER(c.nombreCompletoJugador) NOT IN ('NULO') " +
                    "AND c.estadoId = 1000 " +
                    "AND ((:pOperadorId < 1L AND 1 = 1) OR (:pOperadorId > 0L AND c.operadorId = :pOperadorId)) " +
                    "AND ((:pFechaInicio = '1900-01-01' AND :pFechaFin = '1900-01-01' AND 1 = 1) OR (:pFechaInicio <> '1900-01-01' AND :pFechaFin <> '1900-01-01' AND c.fechaArchivo BETWEEN DATE(:pFechaInicio) AND DATE(:pFechaFin))) " +
                    "GROUP BY 2 " +
                    "ORDER BY montoTotalPagadoSuma DESC")
    List<Object[]> ObtenerNJugadoresGananMasDiaOptimizado(@Param("pOperadorId") Long pOperadorId,
                                                          @Param("pFechaInicio") String pFechaInicio,
                                                          @Param("pFechaFin") String pFechaFin);

    @Query(value = "SELECT c.nombreCompletoJugador, c.documentoIdentidad, c.fechaArchivo, c.montoTotalPagado " +
                    "FROM CajaPagoEntity c " +
                    "WHERE UPPER(c.nombreCompletoJugador) NOT IN ('NULO') " +
                    "AND c.estadoId = 1000 AND " +
                    "((:pFechaInicio = '1900-01-01' AND :pFechaFin = '1900-01-01' AND 1 = 1) OR (:pFechaInicio <> '1900-01-01' AND :pFechaFin <> '1900-01-01' AND c.fechaArchivo BETWEEN DATE(:pFechaInicio) AND DATE(:pFechaFin))) " +
                    "AND c.fechaArchivo IS NOT NULL " +
                    "AND UPPER(TRIM(c.documentoIdentidad)) = UPPER(TRIM(:pDocumentoIdentidad)) " +
                    "ORDER BY c.fechaArchivo")
    List<Object[]> ObtenerUmbralPagosPorFechasJugador(@Param("pFechaInicio") String pFechaInicio,
                                                      @Param("pFechaFin") String pFechaFin,
                                                      @Param("pDocumentoIdentidad") String pDocumentoIdentidad);
}
