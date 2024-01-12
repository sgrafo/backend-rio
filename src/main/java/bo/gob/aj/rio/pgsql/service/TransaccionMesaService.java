package bo.gob.aj.rio.pgsql.service;

import bo.gob.aj.rio.pgsql.entity.TransaccionMesaEntity;
import bo.gob.aj.rio.pgsql.repository.ITransaccionMesaRepository;
import bo.gob.aj.rio.util.Constantes;
import bo.gob.aj.rio.util.Fechas;
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
public class TransaccionMesaService {

    private ITransaccionMesaRepository transaccionMesaRepository;

    @Autowired
    public TransaccionMesaService(ITransaccionMesaRepository pTransaccionMesaRepository) {
        this.transaccionMesaRepository = pTransaccionMesaRepository;
    }

    public TransaccionMesaEntity RegistrarTransaccionMesa(TransaccionMesaEntity pTransaccionMesaEntity) {
        TransaccionMesaEntity vTransaccionMesa = new TransaccionMesaEntity();

        //vTransaccionMesa.setTransaccionMesaId(pTransaccionMesaEntity.getTransaccionMesaId());
        vTransaccionMesa.setOperadorId(pTransaccionMesaEntity.getOperadorId());
        vTransaccionMesa.setCodigoMesa(pTransaccionMesaEntity.getCodigoMesa());
        vTransaccionMesa.setFechaRegistroReal(pTransaccionMesaEntity.getFechaRegistroReal());
        vTransaccionMesa.setHoraRegistro(pTransaccionMesaEntity.getHoraRegistro());
        vTransaccionMesa.setNumeroFormulario(pTransaccionMesaEntity.getNumeroFormulario());
        vTransaccionMesa.setTipoTransaccion(pTransaccionMesaEntity.getTipoTransaccion());
        vTransaccionMesa.setDenominacionFicha1000(pTransaccionMesaEntity.getDenominacionFicha1000());
        vTransaccionMesa.setCantidadFichas1000(pTransaccionMesaEntity.getCantidadFichas1000());
        vTransaccionMesa.setDenominacionFicha500(pTransaccionMesaEntity.getDenominacionFicha500());
        vTransaccionMesa.setCantidadFichas500(pTransaccionMesaEntity.getCantidadFichas500());
        vTransaccionMesa.setDenominacionFicha200(pTransaccionMesaEntity.getDenominacionFicha200());
        vTransaccionMesa.setCantidadFichas200(pTransaccionMesaEntity.getCantidadFichas200());
        vTransaccionMesa.setDenominacionFicha100(pTransaccionMesaEntity.getDenominacionFicha100());
        vTransaccionMesa.setCantidadFichas100(pTransaccionMesaEntity.getCantidadFichas100());
        vTransaccionMesa.setDenominacionFicha50(pTransaccionMesaEntity.getDenominacionFicha50());
        vTransaccionMesa.setCantidadFichas50(pTransaccionMesaEntity.getCantidadFichas50());
        vTransaccionMesa.setDenominacionFicha20(pTransaccionMesaEntity.getDenominacionFicha20());
        vTransaccionMesa.setCantidadFichas20(pTransaccionMesaEntity.getCantidadFichas20());
        vTransaccionMesa.setDenominacionFicha10(pTransaccionMesaEntity.getDenominacionFicha10());
        vTransaccionMesa.setCantidadFichas10(pTransaccionMesaEntity.getCantidadFichas10());
        vTransaccionMesa.setDenominacionFicha5(pTransaccionMesaEntity.getDenominacionFicha5());
        vTransaccionMesa.setCantidadFichas5(pTransaccionMesaEntity.getCantidadFichas5());
        vTransaccionMesa.setDenominacionFicha1(pTransaccionMesaEntity.getDenominacionFicha1());
        vTransaccionMesa.setCantidadFichas1(pTransaccionMesaEntity.getCantidadFichas1());
        vTransaccionMesa.setMontoTotalPagado(pTransaccionMesaEntity.getMontoTotalPagado());
        vTransaccionMesa.setFechaArchivo(pTransaccionMesaEntity.getFechaArchivo());
        vTransaccionMesa.setFechaRegistro(new Timestamp(System.currentTimeMillis()));
        vTransaccionMesa.setEstadoId(Constantes.ESTADO_ACTIVO);

        this.transaccionMesaRepository.save(vTransaccionMesa);

        return vTransaccionMesa;
    }

    public TransaccionMesaEntity ActualizarTransaccionMesa(Long pTransaccionMesaId, TransaccionMesaEntity pTransaccionMesaEntity) {
        Optional<TransaccionMesaEntity> vOptionalEntity = this.transaccionMesaRepository.findById(pTransaccionMesaId);

        if (vOptionalEntity != null) {
            TransaccionMesaEntity vUpdate = vOptionalEntity.get();

            if (pTransaccionMesaEntity.getOperadorId() != null)
                vUpdate.setOperadorId(pTransaccionMesaEntity.getOperadorId());

            if (pTransaccionMesaEntity.getCodigoMesa() != null)
                vUpdate.setCodigoMesa(pTransaccionMesaEntity.getCodigoMesa());

            if (pTransaccionMesaEntity.getFechaRegistroReal() != null)
                vUpdate.setFechaRegistroReal(pTransaccionMesaEntity.getFechaRegistroReal());

            if (pTransaccionMesaEntity.getHoraRegistro() != null)
                vUpdate.setHoraRegistro(pTransaccionMesaEntity.getHoraRegistro());

            if (pTransaccionMesaEntity.getNumeroFormulario() != null)
                vUpdate.setNumeroFormulario(pTransaccionMesaEntity.getNumeroFormulario());

            if (pTransaccionMesaEntity.getTipoTransaccion() != null)
                vUpdate.setTipoTransaccion(pTransaccionMesaEntity.getTipoTransaccion());

            if (pTransaccionMesaEntity.getDenominacionFicha1000() != null)
                vUpdate.setDenominacionFicha1000(pTransaccionMesaEntity.getDenominacionFicha1000());

            if (pTransaccionMesaEntity.getCantidadFichas1000() != null)
                vUpdate.setCantidadFichas1000(pTransaccionMesaEntity.getCantidadFichas1000());

            if (pTransaccionMesaEntity.getDenominacionFicha500() != null)
                vUpdate.setDenominacionFicha500(pTransaccionMesaEntity.getDenominacionFicha500());

            if (pTransaccionMesaEntity.getCantidadFichas500() != null)
                vUpdate.setCantidadFichas500(pTransaccionMesaEntity.getCantidadFichas500());

            if (pTransaccionMesaEntity.getDenominacionFicha200() != null)
                vUpdate.setDenominacionFicha200(pTransaccionMesaEntity.getDenominacionFicha200());

            if (pTransaccionMesaEntity.getCantidadFichas200() != null)
                vUpdate.setCantidadFichas200(pTransaccionMesaEntity.getCantidadFichas200());

            if (pTransaccionMesaEntity.getDenominacionFicha100() != null)
                vUpdate.setDenominacionFicha100(pTransaccionMesaEntity.getDenominacionFicha100());

            if (pTransaccionMesaEntity.getCantidadFichas100() != null)
                vUpdate.setCantidadFichas100(pTransaccionMesaEntity.getCantidadFichas100());

            if (pTransaccionMesaEntity.getDenominacionFicha50() != null)
                vUpdate.setDenominacionFicha50(pTransaccionMesaEntity.getDenominacionFicha50());

            if (pTransaccionMesaEntity.getCantidadFichas50() != null)
                vUpdate.setCantidadFichas50(pTransaccionMesaEntity.getCantidadFichas50());

            if (pTransaccionMesaEntity.getDenominacionFicha20() != null)
                vUpdate.setDenominacionFicha20(pTransaccionMesaEntity.getDenominacionFicha20());

            if (pTransaccionMesaEntity.getCantidadFichas20() != null)
                vUpdate.setCantidadFichas20(pTransaccionMesaEntity.getCantidadFichas20());

            if (pTransaccionMesaEntity.getDenominacionFicha10() != null)
                vUpdate.setDenominacionFicha10(pTransaccionMesaEntity.getDenominacionFicha10());

            if (pTransaccionMesaEntity.getCantidadFichas10() != null)
                vUpdate.setCantidadFichas10(pTransaccionMesaEntity.getCantidadFichas10());

            if (pTransaccionMesaEntity.getDenominacionFicha5() != null)
                vUpdate.setDenominacionFicha5(pTransaccionMesaEntity.getDenominacionFicha5());

            if (pTransaccionMesaEntity.getCantidadFichas5() != null)
                vUpdate.setCantidadFichas5(pTransaccionMesaEntity.getCantidadFichas5());

            if (pTransaccionMesaEntity.getDenominacionFicha1() != null)
                vUpdate.setDenominacionFicha1(pTransaccionMesaEntity.getDenominacionFicha1());

            if (pTransaccionMesaEntity.getCantidadFichas1() != null)
                vUpdate.setCantidadFichas1(pTransaccionMesaEntity.getCantidadFichas1());

            if (pTransaccionMesaEntity.getMontoTotalPagado() != null)
                vUpdate.setMontoTotalPagado(pTransaccionMesaEntity.getMontoTotalPagado());

            if (pTransaccionMesaEntity.getFechaArchivo() != null)
                vUpdate.setFechaArchivo(pTransaccionMesaEntity.getFechaArchivo());

            this.transaccionMesaRepository.save(vUpdate);

            return vUpdate;
        }
        else
            return null;
    }

    public TransaccionMesaEntity EliminarTransaccionMesa(Long pTransaccionMesaId) {
        Optional<TransaccionMesaEntity> vOptionalEntity = this.transaccionMesaRepository.findById(pTransaccionMesaId);
        TransaccionMesaEntity vDelete = vOptionalEntity.get();

        vDelete.setEstadoId(Constantes.ESTADO_ANULADO);
        this.transaccionMesaRepository.save(vDelete);

        return vDelete;
    }

    public void EliminarTodasTransaccionesMesas() {
        List<TransaccionMesaEntity> vOptionalEntity = this.transaccionMesaRepository.findAll();

        if (vOptionalEntity.size() > 0) {
            vOptionalEntity.forEach(x -> x.setEstadoId(Constantes.ESTADO_ANULADO));
            this.transaccionMesaRepository.saveAll(vOptionalEntity);
        }
    }

    public void EliminacionFisicaTransaccionesMesas() {
        List<TransaccionMesaEntity> vOptionalEntity = this.transaccionMesaRepository.findAll();

        if (vOptionalEntity.size() > 0)
            this.transaccionMesaRepository.deleteAll(vOptionalEntity);
    }

    public void EliminacionFisicaTransaccionesMesasPorFechas(String pFechaInicio, String pFechaFinal) {
        List<TransaccionMesaEntity> vOptionalEntity = this.transaccionMesaRepository.ObtenerTransaccionesMesasPorFechas(pFechaInicio, pFechaFinal);

        if (vOptionalEntity.size() > 0)
            this.transaccionMesaRepository.deleteAll(vOptionalEntity);
    }

    public List<TransaccionMesaEntity> ObtenerTransaccionesMesasPorOperador(Long pOperadorId) {
        return this.transaccionMesaRepository.ObtenerTransaccionesMesasPorOperador(pOperadorId);
    }

    public void MigrarTransaccionesMesas(Long pOperadorId, Sheet pHoja, Timestamp pFechaArchivo) {
        DataFormatter vFormato = new DataFormatter();

        for (Row vFila: pHoja) {
            TransaccionMesaEntity vTransaccionMesa = new TransaccionMesaEntity();

            if (vFila.getCell(0) != null) {
                for (Cell vCelda: vFila) {
                    String vValorCelda = vFormato.formatCellValue(vCelda).trim();

                    try {
                        if (vCelda.getRowIndex() >= 11) {

                            switch (vCelda.getColumnIndex()) {
                                case 0:
                                    vTransaccionMesa.setCodigoMesa(vValorCelda);
                                    break;
                                case 1:
                                    if (vValorCelda != null && !vValorCelda.equals("") && !vValorCelda.equalsIgnoreCase("NULO") && !vValorCelda.equalsIgnoreCase("SIN APERTURA") && !vValorCelda.equalsIgnoreCase("SIN CIERRE"))
                                        vTransaccionMesa.setFechaRegistroReal(vCelda != null ? Fechas.CovertirDateToTimestamp(vCelda.getDateCellValue()) : null);
                                    break;
                                case 2:
                                    vTransaccionMesa.setHoraRegistro(vValorCelda);
                                    break;
                                case 3:
                                    vTransaccionMesa.setNumeroFormulario(vValorCelda);
                                    break;
                                case 4: //Celda Combinada con la anterior
                                    break;
                                case 5:
                                    vTransaccionMesa.setTipoTransaccion(vValorCelda);
                                    break;
                                case 6:
                                    if (vFila.getLastCellNum() == 25)
                                        vTransaccionMesa.setDenominacionFicha1000(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 23) {
                                        vTransaccionMesa.setDenominacionFicha1000(Constantes.DENOMINA_FICHA_1000);
                                        vTransaccionMesa.setDenominacionFicha500(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 21) {
                                        vTransaccionMesa.setDenominacionFicha1000(Constantes.DENOMINA_FICHA_1000);
                                        vTransaccionMesa.setDenominacionFicha200(Integer.parseInt(vValorCelda));
                                    }
                                    break;
                                case 7:
                                    if (vFila.getLastCellNum() == 25)
                                        vTransaccionMesa.setCantidadFichas1000(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 23) {
                                        vTransaccionMesa.setCantidadFichas1000(0);
                                        vTransaccionMesa.setCantidadFichas500(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 21) {
                                        vTransaccionMesa.setCantidadFichas1000(0);
                                        vTransaccionMesa.setCantidadFichas200(Integer.parseInt(vValorCelda));
                                    }
                                    break;
                                case 8:
                                    if (vFila.getLastCellNum() == 25)
                                        vTransaccionMesa.setDenominacionFicha500(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 23) {
                                        vTransaccionMesa.setDenominacionFicha200(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 21) {
                                        vTransaccionMesa.setDenominacionFicha500(Constantes.DENOMINA_FICHA_500);
                                        vTransaccionMesa.setDenominacionFicha100(Integer.parseInt(vValorCelda));
                                    }
                                    break;
                                case 9:
                                    if (vFila.getLastCellNum() == 25)
                                        vTransaccionMesa.setCantidadFichas500(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 23) {
                                        vTransaccionMesa.setCantidadFichas200(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 21) {
                                        vTransaccionMesa.setCantidadFichas500(0);
                                        vTransaccionMesa.setCantidadFichas100(Integer.parseInt(vValorCelda));
                                    }
                                    break;
                                case 10:
                                    if (vFila.getLastCellNum() == 25)
                                        vTransaccionMesa.setDenominacionFicha200(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 23) {
                                        vTransaccionMesa.setDenominacionFicha100(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 21)
                                        vTransaccionMesa.setDenominacionFicha50(Integer.parseInt(vValorCelda));
                                    break;
                                case 11:
                                    if (vFila.getLastCellNum() == 25)
                                        vTransaccionMesa.setCantidadFichas200(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 23) {
                                        vTransaccionMesa.setCantidadFichas100(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 21)
                                        vTransaccionMesa.setCantidadFichas50(Integer.parseInt(vValorCelda));
                                    break;
                                case 12:
                                    if (vFila.getLastCellNum() == 25)
                                        vTransaccionMesa.setDenominacionFicha100(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 23) {
                                        vTransaccionMesa.setDenominacionFicha50(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 21)
                                        vTransaccionMesa.setDenominacionFicha20(Integer.parseInt(vValorCelda));
                                    break;
                                case 13:
                                    if (vFila.getLastCellNum() == 25)
                                        vTransaccionMesa.setCantidadFichas100(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 23) {
                                        vTransaccionMesa.setCantidadFichas50(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 21)
                                        vTransaccionMesa.setCantidadFichas20(Integer.parseInt(vValorCelda));
                                    break;
                                case 14:
                                    if (vFila.getLastCellNum() == 25)
                                        vTransaccionMesa.setDenominacionFicha50(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 23) {
                                        vTransaccionMesa.setDenominacionFicha20(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 21)
                                        vTransaccionMesa.setDenominacionFicha10(Integer.parseInt(vValorCelda));
                                    break;
                                case 15:
                                    if (vFila.getLastCellNum() == 25)
                                        vTransaccionMesa.setCantidadFichas50(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 23) {
                                        vTransaccionMesa.setCantidadFichas20(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 21)
                                        vTransaccionMesa.setCantidadFichas10(Integer.parseInt(vValorCelda));
                                    break;
                                case 16:
                                    if (vFila.getLastCellNum() == 25)
                                        vTransaccionMesa.setDenominacionFicha20(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 23) {
                                        vTransaccionMesa.setDenominacionFicha10(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 21)
                                        vTransaccionMesa.setDenominacionFicha5(Integer.parseInt(vValorCelda));
                                    break;
                                case 17:
                                    if (vFila.getLastCellNum() == 25)
                                        vTransaccionMesa.setCantidadFichas20(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 23) {
                                        vTransaccionMesa.setCantidadFichas10(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 21)
                                        vTransaccionMesa.setCantidadFichas5(Integer.parseInt(vValorCelda));
                                    break;
                                case 18:
                                    if (vFila.getLastCellNum() == 25)
                                        vTransaccionMesa.setDenominacionFicha10(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 23) {
                                        vTransaccionMesa.setDenominacionFicha5(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 21)
                                        vTransaccionMesa.setDenominacionFicha1(Integer.parseInt(vValorCelda));
                                    break;
                                case 19:
                                    if (vFila.getLastCellNum() == 25)
                                        vTransaccionMesa.setCantidadFichas10(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 23) {
                                        vTransaccionMesa.setCantidadFichas5(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 21)
                                        vTransaccionMesa.setCantidadFichas1(Integer.parseInt(vValorCelda));
                                    break;
                                case 20:
                                    if (vFila.getLastCellNum() == 25)
                                        vTransaccionMesa.setDenominacionFicha5(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 23) {
                                        vTransaccionMesa.setDenominacionFicha1(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 21)
                                        vTransaccionMesa.setMontoTotalPagado(new BigDecimal(vCelda.getNumericCellValue()));
                                    break;
                                case 21:
                                    if (vFila.getLastCellNum() == 25)
                                        vTransaccionMesa.setCantidadFichas5(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 23) {
                                        vTransaccionMesa.setCantidadFichas1(Integer.parseInt(vValorCelda));
                                    }
                                    break;
                                case 22:
                                    if (vFila.getLastCellNum() == 25)
                                        vTransaccionMesa.setDenominacionFicha1(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 23) {
                                        vTransaccionMesa.setMontoTotalPagado(new BigDecimal(vCelda.getNumericCellValue()));
                                    }
                                    break;
                                case 23:
                                    vTransaccionMesa.setCantidadFichas1(Integer.parseInt(vValorCelda));
                                    break;
                                case 24:
                                    vTransaccionMesa.setMontoTotalPagado(new BigDecimal(vCelda.getNumericCellValue()));
                                    break;
                            }
                        }
                        else
                            break;

                        if ((vFila.getLastCellNum() == 25 && vCelda.getColumnIndex() == 24) || (vFila.getLastCellNum() == 23 && vCelda.getColumnIndex() == 22) || (vFila.getLastCellNum() == 21 && vCelda.getColumnIndex() == 20)) {
                            vTransaccionMesa.setOperadorId(pOperadorId);
                            vTransaccionMesa.setFechaArchivo(pFechaArchivo);
                            RegistrarTransaccionMesa(vTransaccionMesa);
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
