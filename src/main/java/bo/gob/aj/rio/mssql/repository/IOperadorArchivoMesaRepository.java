package bo.gob.aj.rio.mssql.repository;

import bo.gob.aj.rio.mssql.entity.OperadorArchivoMesaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOperadorArchivoMesaRepository extends JpaRepository<OperadorArchivoMesaEntity, Long> {

    // Obtener registros por Usuario
    @Query(value = "SELECT o FROM OperadorArchivoMesaEntity o WHERE o.usuarioId = :pUsuarioId")
    List<OperadorArchivoMesaEntity> ObtenerArchivoMesaPorUsuario(@Param("pUsuarioId") Integer pUsuarioId);

    // Obtener registros por Operador
    @Query(value = "SELECT o FROM OperadorArchivoMesaEntity o WHERE o.operadorId = :pOperadorId")
    List<OperadorArchivoMesaEntity> ObtenerArchivoMesaPorOperador(@Param("pOperadorId") Integer pOperadorId);

    // Obtener registros por Estado
    @Query(value = "SELECT o FROM OperadorArchivoMesaEntity o WHERE o.estado = :pEstado")
    List<OperadorArchivoMesaEntity> ObtenerArchivoMesaPorEstado(@Param("pEstado") String pEstado);

    @Query(value = "SELECT o FROM OperadorArchivoMesaEntity o " +
                   "WHERE CONVERT (DATE, STUFF(STUFF(SUBSTRING(o.nombreArchivo, 1, 8), 3, 0, '-'), 6, 0, '-')) BETWEEN :pFechaInicio AND :pFechaFinal " +
                   "AND o.estado = 'VÁLIDO'")
    List<OperadorArchivoMesaEntity> ObtenerArchivoMesaPorRangoFechas(@Param("pFechaInicio") String pFechaInicio,
                                                                     @Param("pFechaFinal") String pFechaFinal);
}
