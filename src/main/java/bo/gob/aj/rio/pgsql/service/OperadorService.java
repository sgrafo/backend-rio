package bo.gob.aj.rio.pgsql.service;

import bo.gob.aj.rio.pgsql.entity.OperadorEntity;
import bo.gob.aj.rio.pgsql.repository.IOperadorRepository;
import bo.gob.aj.rio.util.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class OperadorService {

    private IOperadorRepository operadorRepository;

    @Autowired
    public OperadorService(IOperadorRepository pOperadorRepository) {
        this.operadorRepository = pOperadorRepository;
    }

    public OperadorEntity RegistrarOperador(OperadorEntity pOperadorEntity) {
        OperadorEntity vOperador = new OperadorEntity();

        //vOperador.setOperadorId(pOperadorEntity.getOperadorId());
        vOperador.setNombreEmpresa(pOperadorEntity.getNombreEmpresa());
        vOperador.setNombreSalon(pOperadorEntity.getNombreSalon());
        vOperador.setFechaRegistro(new Timestamp(System.currentTimeMillis()));
        vOperador.setEstadoId(Constantes.ESTADO_ACTIVO);

        this.operadorRepository.save(vOperador);

        return vOperador;
    }

    public OperadorEntity ActualizarOperador(Long pOperadorId, OperadorEntity pOperadorEntity) {
        Optional<OperadorEntity> vOptionalEntity = this.operadorRepository.findById(pOperadorId);

        if (vOptionalEntity != null) {
            OperadorEntity vUpdate = vOptionalEntity.get();

            if (pOperadorEntity.getNombreEmpresa() != null)
                vUpdate.setNombreEmpresa(pOperadorEntity.getNombreEmpresa());

            if (pOperadorEntity.getNombreSalon() != null)
                vUpdate.setNombreSalon(pOperadorEntity.getNombreSalon());

            this.operadorRepository.save(vUpdate);

            return vUpdate;
        }
        else
            return null;
    }

    public OperadorEntity EliminarOperador(Long pOperadorId) {
        Optional<OperadorEntity> vOptionalEntity = this.operadorRepository.findById(pOperadorId);
        OperadorEntity vDelete = vOptionalEntity.get();

        vDelete.setEstadoId(Constantes.ESTADO_ANULADO);
        this.operadorRepository.save(vDelete);

        return vDelete;
    }

    public void EliminarTodosOperadores() {
        List<OperadorEntity> vOptionalEntity = this.operadorRepository.findAll();

        if (vOptionalEntity.size() > 0) {
            vOptionalEntity.forEach(x -> x.setEstadoId(Constantes.ESTADO_ANULADO));
            this.operadorRepository.saveAll(vOptionalEntity);
        }
    }

    public void EliminacionFisicaOperadores() {
        List<OperadorEntity> vOptionalEntity = this.operadorRepository.findAll();

        if (vOptionalEntity.size() > 0)
            this.operadorRepository.saveAll(vOptionalEntity);
    }

    public List<OperadorEntity> ObtenerOperadores() {
        return this.operadorRepository.ObtenerOperadores();
    }


    public Boolean ExisteOperador(OperadorEntity pOperadorEntity) {
        List<OperadorEntity> vOperadorList = this.operadorRepository.BuscarOperadorPorEmpresaSalon(pOperadorEntity.getNombreEmpresa(), pOperadorEntity.getNombreSalon());

        if (vOperadorList != null && vOperadorList.size() > 0)
            return true;
        return false;
    }

    public Long ObtenerOperador(OperadorEntity pOperadorEntity) {
        List<OperadorEntity> vOperadorList = this.operadorRepository.BuscarOperadorPorEmpresaSalon(pOperadorEntity.getNombreEmpresa(), pOperadorEntity.getNombreSalon());
        Long vOperadorId = 0L;

        if (vOperadorList != null && vOperadorList.size() > 0) {
            vOperadorId = vOperadorList.get(0).getOperadorId();
        }
        else {
            vOperadorId = RegistrarOperador(pOperadorEntity).getOperadorId();
        }
        return vOperadorId;
    }
}
