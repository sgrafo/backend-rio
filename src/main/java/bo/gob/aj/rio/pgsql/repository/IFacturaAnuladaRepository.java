package bo.gob.aj.rio.pgsql.repository;

import bo.gob.aj.rio.pgsql.entity.FacturaAnuladaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFacturaAnuladaRepository extends JpaRepository<FacturaAnuladaEntity, Long> {

    @Query(value = "SELECT f FROM FacturaAnuladaEntity f WHERE f.estadoId = 1000 AND f.operadorId = :pOperadorId")
    List<FacturaAnuladaEntity> ObtenerFacturasAnuladasPorOperador(@Param("pOperadorId") Long pOperadorId);

    @Query(value = "SELECT f FROM FacturaAnuladaEntity f " +
                   "WHERE f.estadoId = 1000 AND f.fechaArchivo BETWEEN DATE(:pFechaInicio) AND DATE(:pFechaFinal)")
    List<FacturaAnuladaEntity> ObtenerFacturasAnuladasPorFechas(@Param("pFechaInicio") String pFechaInicio,
                                                                @Param("pFechaFinal") String pFechaFinal);
}
