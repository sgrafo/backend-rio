package bo.gob.aj.rio.pgsql.repository;

import bo.gob.aj.rio.pgsql.entity.TransaccionMesaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITransaccionMesaRepository extends JpaRepository<TransaccionMesaEntity, Long> {

    @Query(value = "SELECT t FROM TransaccionMesaEntity t WHERE t.estadoId = 1000 AND t.operadorId = :pOperadorId")
    List<TransaccionMesaEntity> ObtenerTransaccionesMesasPorOperador(@Param("pOperadorId") Long pOperadorId);

    @Query(value = "SELECT t FROM TransaccionMesaEntity t " +
                   "WHERE t.estadoId = 1000 AND t.fechaArchivo BETWEEN DATE(:pFechaInicio) AND DATE(:pFechaFinal)")
    List<TransaccionMesaEntity> ObtenerTransaccionesMesasPorFechas(@Param("pFechaInicio") String pFechaInicio,
                                                                   @Param("pFechaFinal") String pFechaFinal);
}
