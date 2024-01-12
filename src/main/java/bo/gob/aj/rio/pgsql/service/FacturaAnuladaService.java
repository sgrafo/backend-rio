package bo.gob.aj.rio.pgsql.service;

import bo.gob.aj.rio.pgsql.entity.FacturaAnuladaEntity;
import bo.gob.aj.rio.pgsql.repository.IFacturaAnuladaRepository;
import bo.gob.aj.rio.util.Constantes;
import bo.gob.aj.rio.util.Fechas;
import bo.gob.aj.rio.util.UtilPOI;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class FacturaAnuladaService {

    private IFacturaAnuladaRepository facturaAnuladaRepository;

    @Autowired
    public FacturaAnuladaService(IFacturaAnuladaRepository pFacturaAnuladaRepository) {
        this.facturaAnuladaRepository = pFacturaAnuladaRepository;
    }

    public FacturaAnuladaEntity RegistrarFacturaAnulada(FacturaAnuladaEntity pFacturaAnuladaEntity) {
        FacturaAnuladaEntity vFacturaAnulada = new FacturaAnuladaEntity();

        //vFacturaAnulada.setFacturaAnuladaId(pFacturaAnuladaEntity.getFacturaAnuladaId());
        vFacturaAnulada.setOperadorId(pFacturaAnuladaEntity.getOperadorId());
        vFacturaAnulada.setNumeroCaja(pFacturaAnuladaEntity.getNumeroCaja());
        vFacturaAnulada.setNombreCompletoJugador(pFacturaAnuladaEntity.getNombreCompletoJugador());
        vFacturaAnulada.setDocumentoIdentidad(pFacturaAnuladaEntity.getDocumentoIdentidad());
        vFacturaAnulada.setLugarExpedicion(pFacturaAnuladaEntity.getLugarExpedicion());
        vFacturaAnulada.setNumeroFacturaAnulada(pFacturaAnuladaEntity.getNumeroFacturaAnulada());
        vFacturaAnulada.setNumeroAutorizacion(pFacturaAnuladaEntity.getNumeroAutorizacion());
        vFacturaAnulada.setFechaFacturaAnulada(pFacturaAnuladaEntity.getFechaFacturaAnulada());
        vFacturaAnulada.setMontoTotalFacturaAnulada(pFacturaAnuladaEntity.getMontoTotalFacturaAnulada());
        vFacturaAnulada.setNumeroTicket(pFacturaAnuladaEntity.getNumeroTicket());
        vFacturaAnulada.setMontoTotalTicket(pFacturaAnuladaEntity.getMontoTotalTicket());
        vFacturaAnulada.setFechaFacturaNueva(pFacturaAnuladaEntity.getFechaFacturaNueva());
        vFacturaAnulada.setNumeroFacturaNueva(pFacturaAnuladaEntity.getNumeroFacturaNueva());
        vFacturaAnulada.setMontoTotalFacturaNueva(pFacturaAnuladaEntity.getMontoTotalFacturaNueva());
        vFacturaAnulada.setFechaArchivo(pFacturaAnuladaEntity.getFechaArchivo());
        vFacturaAnulada.setFechaRegistro(new Timestamp(System.currentTimeMillis()));
        vFacturaAnulada.setEstadoId(Constantes.ESTADO_ACTIVO);

        this.facturaAnuladaRepository.save(vFacturaAnulada);

        return vFacturaAnulada;
    }

    public FacturaAnuladaEntity ActualizarFacturaAnulada(Long pFacturaAnuladaId, FacturaAnuladaEntity pFacturaAnuladaEntity) {
        Optional<FacturaAnuladaEntity> vOptionalEntity = this.facturaAnuladaRepository.findById(pFacturaAnuladaId);

        if (vOptionalEntity != null) {
            FacturaAnuladaEntity vUpdate = vOptionalEntity.get();

            if (pFacturaAnuladaEntity.getOperadorId() != null)
                vUpdate.setOperadorId(pFacturaAnuladaEntity.getOperadorId());

            if (pFacturaAnuladaEntity.getNumeroCaja() != null)
                vUpdate.setNumeroCaja(pFacturaAnuladaEntity.getNumeroCaja());

            if (pFacturaAnuladaEntity.getNombreCompletoJugador() != null)
                vUpdate.setNombreCompletoJugador(pFacturaAnuladaEntity.getNombreCompletoJugador());

            if (pFacturaAnuladaEntity.getDocumentoIdentidad() != null)
                vUpdate.setDocumentoIdentidad(pFacturaAnuladaEntity.getDocumentoIdentidad());

            if (pFacturaAnuladaEntity.getLugarExpedicion() != null)
                vUpdate.setLugarExpedicion(pFacturaAnuladaEntity.getLugarExpedicion());

            if (pFacturaAnuladaEntity.getNumeroFacturaAnulada() != null)
                vUpdate.setNumeroFacturaAnulada(pFacturaAnuladaEntity.getNumeroFacturaAnulada());

            if (pFacturaAnuladaEntity.getNumeroAutorizacion() != null)
                vUpdate.setNumeroAutorizacion(pFacturaAnuladaEntity.getNumeroAutorizacion());

            if (pFacturaAnuladaEntity.getFechaFacturaAnulada() != null)
                vUpdate.setFechaFacturaAnulada(pFacturaAnuladaEntity.getFechaFacturaAnulada());

            if (pFacturaAnuladaEntity.getMontoTotalFacturaAnulada() != null)
                vUpdate.setMontoTotalFacturaAnulada(pFacturaAnuladaEntity.getMontoTotalFacturaAnulada());

            if (pFacturaAnuladaEntity.getNumeroTicket() != null)
                vUpdate.setNumeroTicket(pFacturaAnuladaEntity.getNumeroTicket());

            if (pFacturaAnuladaEntity.getMontoTotalTicket() != null)
                vUpdate.setMontoTotalTicket(pFacturaAnuladaEntity.getMontoTotalTicket());

            if (pFacturaAnuladaEntity.getFechaFacturaNueva() != null)
                vUpdate.setFechaFacturaNueva(pFacturaAnuladaEntity.getFechaFacturaNueva());

            if (pFacturaAnuladaEntity.getNumeroFacturaNueva() != null)
                vUpdate.setNumeroFacturaNueva(pFacturaAnuladaEntity.getNumeroFacturaNueva());

            if (pFacturaAnuladaEntity.getMontoTotalFacturaNueva() != null)
                vUpdate.setMontoTotalFacturaNueva(pFacturaAnuladaEntity.getMontoTotalFacturaNueva());

            if (pFacturaAnuladaEntity.getFechaArchivo() != null)
                vUpdate.setFechaArchivo(pFacturaAnuladaEntity.getFechaArchivo());

            this.facturaAnuladaRepository.save(vUpdate);

            return vUpdate;
        }
        else
            return null;
    }

    public FacturaAnuladaEntity EliminarFacturaAnulada(Long pFacturaAnuladaId) {
        Optional<FacturaAnuladaEntity> vOptionalEntity = this.facturaAnuladaRepository.findById(pFacturaAnuladaId);
        FacturaAnuladaEntity vDelete = vOptionalEntity.get();

        vDelete.setEstadoId(Constantes.ESTADO_ANULADO);
        this.facturaAnuladaRepository.save(vDelete);

        return vDelete;
    }

    public void EliminarTodasFacturasAnuladas() {
        List<FacturaAnuladaEntity> vOptionalEntity = this.facturaAnuladaRepository.findAll();

        if (vOptionalEntity.size() > 0) {
            vOptionalEntity.forEach(x -> x.setEstadoId(Constantes.ESTADO_ANULADO));
            this.facturaAnuladaRepository.saveAll(vOptionalEntity);
        }
    }

    public void EliminacionFisicaFacturasAnuladas() {
        List<FacturaAnuladaEntity> vOptionalEntity = this.facturaAnuladaRepository.findAll();

        if (vOptionalEntity.size() > 0)
            this.facturaAnuladaRepository.deleteAll(vOptionalEntity);
    }

    public void EliminacionFisicaFacturasAnuladasPorFechas(String pFechaInicio, String pFechaFinal) {
        List<FacturaAnuladaEntity> vOptionalEntity = this.facturaAnuladaRepository.ObtenerFacturasAnuladasPorFechas(pFechaInicio, pFechaFinal);

        if (vOptionalEntity.size() > 0)
            this.facturaAnuladaRepository.deleteAll(vOptionalEntity);
    }

    public List<FacturaAnuladaEntity> ObtenerFacturasAnuladasPorOperador(Long pOperadorId) {
        return this.facturaAnuladaRepository.ObtenerFacturasAnuladasPorOperador(pOperadorId);
    }

    public void MigrarFacturasAnuladas(Long pOperadorId, Sheet pHoja, Timestamp pFechaArchivo) {
        DataFormatter vFormato = new DataFormatter();

        for (Row vFila: pHoja) {
            FacturaAnuladaEntity vFacturaAnulada = new FacturaAnuladaEntity();

            if (vFila.getCell(0) != null) {
                for (Cell vCelda: vFila) {
                    String vValorCelda = vFormato.formatCellValue(vCelda).trim();

                    try {
                        if (vCelda.getRowIndex() >= 12) {

                            switch (vCelda.getColumnIndex()) {
                                case 0:
                                    vFacturaAnulada.setNumeroCaja(Integer.parseInt(vValorCelda));
                                    break;
                                case 1:
                                    vFacturaAnulada.setNombreCompletoJugador(vValorCelda);
                                    break;
                                case 2:
                                    vFacturaAnulada.setDocumentoIdentidad(vValorCelda);
                                    break;
                                case 3:
                                    vFacturaAnulada.setLugarExpedicion(vValorCelda);
                                    break;
                                case 4:
                                    vFacturaAnulada.setNumeroFacturaAnulada(vValorCelda);
                                    break;
                                case 5:
                                    vFacturaAnulada.setNumeroAutorizacion(Long.parseLong(vValorCelda));
                                    break;
                                case 6:
                                    //vFacturaAnulada.setFechaFacturaAnulada(Fechas.CovertirDateToTimestamp(vCelda.getDateCellValue()));
                                    vFacturaAnulada.setFechaFacturaAnulada(Fechas.ConvertirFechaStringTimestamp(UtilPOI.ObtenerValorCelda(vCelda)));
                                    break;
                                case 7:
                                    vFacturaAnulada.setMontoTotalFacturaAnulada(new BigDecimal(UtilPOI.ObtenerValorCelda(vCelda)));
                                    break;
                                case 8:
                                    vFacturaAnulada.setNumeroTicket(Integer.parseInt(vValorCelda));
                                    break;
                                case 9:
                                    vFacturaAnulada.setMontoTotalTicket(new BigDecimal(UtilPOI.ObtenerValorCelda(vCelda)));
                                    break;
                                case 10:
                                    vFacturaAnulada.setFechaFacturaNueva(Fechas.CovertirDateToTimestamp(vCelda.getDateCellValue()));
                                    break;
                                case 11:
                                    vFacturaAnulada.setNumeroFacturaNueva(vValorCelda);
                                    break;
                                case 12:
                                    vFacturaAnulada.setMontoTotalFacturaNueva(new BigDecimal(vCelda.getNumericCellValue()));
                                    break;
                            }
                        }
                        else
                            break;

                        if (vCelda.getColumnIndex() == 12 && vFacturaAnulada.getNumeroCaja() > 0) {
                            vFacturaAnulada.setOperadorId(pOperadorId);
                            vFacturaAnulada.setFechaArchivo(pFechaArchivo);
                            RegistrarFacturaAnulada(vFacturaAnulada);
                        }
                    }
                    catch(Exception ex) {

                    }
                }
            }
            else
                break;
        }
    }
}
