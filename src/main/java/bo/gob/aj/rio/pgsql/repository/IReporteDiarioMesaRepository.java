package bo.gob.aj.rio.pgsql.repository;

import bo.gob.aj.rio.pgsql.entity.ReporteDiarioMesaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReporteDiarioMesaRepository extends JpaRepository<ReporteDiarioMesaEntity, Long> {

    @Query(value = "SELECT r FROM ReporteDiarioMesaEntity r WHERE r.estadoId = 1000 AND r.operadorId = :pOperadorId")
    List<ReporteDiarioMesaEntity> ObtenerReporteDiarioMesaPorOperador(@Param("pOperadorId") Long pOperadorId);

    @Query(value = "SELECT r FROM ReporteDiarioMesaEntity r " +
                   "WHERE r.estadoId = 1000 AND r.fechaArchivo BETWEEN DATE(:pFechaInicio) AND DATE(:pFechaFinal)")
    List<ReporteDiarioMesaEntity> ObtenerReporteDiarioMesaPorFechas(@Param("pFechaInicio") String pFechaInicio,
                                                                    @Param("pFechaFinal") String pFechaFinal);
}
