package bo.gob.aj.rio.pgsql.repository;

import bo.gob.aj.rio.pgsql.entity.OperadorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOperadorRepository extends JpaRepository<OperadorEntity, Long> {

    @Query(value = "SELECT o FROM OperadorEntity o WHERE o.estadoId = 1000")
    List<OperadorEntity> ObtenerOperadores();

    @Query(value = "SELECT o FROM OperadorEntity o WHERE o.estadoId = 1000 AND o.operadorId = :pOperadorId")
    OperadorEntity ObtenerOperadorPorId(@Param("pOperadorId") Long pOperadorId);

    @Query(value = "SELECT o FROM OperadorEntity o WHERE o.estadoId = 1000 AND o.nombreEmpresa = :pNombreEmpresa AND o.nombreSalon = :pNombreSalon")
    List<OperadorEntity> BuscarOperadorPorEmpresaSalon(@Param("pNombreEmpresa") String pNombreEmpresa,
                                                       @Param("pNombreSalon") String pNombreSalon);
}
