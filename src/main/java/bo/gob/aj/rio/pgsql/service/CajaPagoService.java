package bo.gob.aj.rio.pgsql.service;

import bo.gob.aj.rio.pgsql.entity.CajaPagoEntity;
import bo.gob.aj.rio.pgsql.repository.ICajaPagoRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CajaPagoService {

    private ICajaPagoRepository cajaPagoRepository;

    @Autowired
    public CajaPagoService(ICajaPagoRepository pCajaPagoRepository) {
        this.cajaPagoRepository = pCajaPagoRepository;
    }

    public CajaPagoEntity RegistrarCajaPago(CajaPagoEntity pCajaPagoEntity) {
        CajaPagoEntity vCajaPago = new CajaPagoEntity();

        //vCajaPago.setCajaPagoId(pCajaPagoEntity.getCajaPagoId());
        vCajaPago.setOperadorId(pCajaPagoEntity.getOperadorId());
        vCajaPago.setNumeroCaja(pCajaPagoEntity.getNumeroCaja());
        vCajaPago.setNombreCompletoJugador(pCajaPagoEntity.getNombreCompletoJugador());
        vCajaPago.setDocumentoIdentidad(pCajaPagoEntity.getDocumentoIdentidad());
        vCajaPago.setLugarExpedicion(pCajaPagoEntity.getLugarExpedicion());
        vCajaPago.setNumeroComprobante(pCajaPagoEntity.getNumeroComprobante());
        vCajaPago.setFechaPago(pCajaPagoEntity.getFechaPago());
        vCajaPago.setHoraPago(pCajaPagoEntity.getHoraPago());
        vCajaPago.setDenominacionFicha1000(pCajaPagoEntity.getDenominacionFicha1000());
        vCajaPago.setCantidadFichas1000(pCajaPagoEntity.getCantidadFichas1000());
        vCajaPago.setDenominacionFicha500(pCajaPagoEntity.getDenominacionFicha500());
        vCajaPago.setCantidadFichas500(pCajaPagoEntity.getCantidadFichas500());
        vCajaPago.setDenominacionFicha200(pCajaPagoEntity.getDenominacionFicha200());
        vCajaPago.setCantidadFichas200(pCajaPagoEntity.getCantidadFichas200());
        vCajaPago.setDenominacionFicha100(pCajaPagoEntity.getDenominacionFicha100());
        vCajaPago.setCantidadFichas100(pCajaPagoEntity.getCantidadFichas100());
        vCajaPago.setDenominacionFicha50(pCajaPagoEntity.getDenominacionFicha50());
        vCajaPago.setCantidadFichas50(pCajaPagoEntity.getCantidadFichas50());
        vCajaPago.setDenominacionFicha20(pCajaPagoEntity.getDenominacionFicha20());
        vCajaPago.setCantidadFichas20(pCajaPagoEntity.getCantidadFichas20());
        vCajaPago.setDenominacionFicha10(pCajaPagoEntity.getDenominacionFicha10());
        vCajaPago.setCantidadFichas10(pCajaPagoEntity.getCantidadFichas10());
        vCajaPago.setDenominacionFicha5(pCajaPagoEntity.getDenominacionFicha5());
        vCajaPago.setCantidadFichas5(pCajaPagoEntity.getCantidadFichas5());
        vCajaPago.setDenominacionFicha1(pCajaPagoEntity.getDenominacionFicha1());
        vCajaPago.setCantidadFichas1(pCajaPagoEntity.getCantidadFichas1());
        vCajaPago.setMontoTotalPagado(pCajaPagoEntity.getMontoTotalPagado());
        vCajaPago.setFechaArchivo(pCajaPagoEntity.getFechaArchivo());
        vCajaPago.setFechaRegistro(new Timestamp(System.currentTimeMillis()));
        vCajaPago.setEstadoId(Constantes.ESTADO_ACTIVO);

        this.cajaPagoRepository.save(vCajaPago);

        return vCajaPago;
    }

    public CajaPagoEntity ActualizarCajaPago(Long pCajaPagoId, CajaPagoEntity pCajaPagoEntity) {
        Optional<CajaPagoEntity> vOptionalEntity = this.cajaPagoRepository.findById(pCajaPagoId);

        if (vOptionalEntity != null) {
            CajaPagoEntity vUpdate = vOptionalEntity.get();

            if (pCajaPagoEntity.getOperadorId() != null)
                vUpdate.setOperadorId(pCajaPagoEntity.getOperadorId());

            if (pCajaPagoEntity.getNumeroCaja() != null)
                vUpdate.setNumeroCaja(pCajaPagoEntity.getNumeroCaja());

            if (pCajaPagoEntity.getNombreCompletoJugador() != null)
                vUpdate.setNombreCompletoJugador(pCajaPagoEntity.getNombreCompletoJugador());

            if (pCajaPagoEntity.getDocumentoIdentidad() != null)
                vUpdate.setDocumentoIdentidad(pCajaPagoEntity.getDocumentoIdentidad());

            if (pCajaPagoEntity.getLugarExpedicion() != null)
                vUpdate.setLugarExpedicion(pCajaPagoEntity.getLugarExpedicion());

            if (pCajaPagoEntity.getNumeroComprobante() != null)
                vUpdate.setNumeroComprobante(pCajaPagoEntity.getNumeroComprobante());

            if (pCajaPagoEntity.getFechaPago() != null)
                vUpdate.setFechaPago(pCajaPagoEntity.getFechaPago());

            if (pCajaPagoEntity.getHoraPago() != null)
                vUpdate.setHoraPago(pCajaPagoEntity.getHoraPago());

            if (pCajaPagoEntity.getDenominacionFicha1000() != null)
                vUpdate.setDenominacionFicha1000(pCajaPagoEntity.getDenominacionFicha1000());

            if (pCajaPagoEntity.getCantidadFichas1000() != null)
                vUpdate.setCantidadFichas1000(pCajaPagoEntity.getCantidadFichas1000());

            if (pCajaPagoEntity.getDenominacionFicha500() != null)
                vUpdate.setDenominacionFicha500(pCajaPagoEntity.getDenominacionFicha500());

            if (pCajaPagoEntity.getCantidadFichas500() != null)
                vUpdate.setCantidadFichas500(pCajaPagoEntity.getCantidadFichas500());

            if (pCajaPagoEntity.getDenominacionFicha200() != null)
                vUpdate.setDenominacionFicha200(pCajaPagoEntity.getDenominacionFicha200());

            if (pCajaPagoEntity.getCantidadFichas200() != null)
                vUpdate.setCantidadFichas200(pCajaPagoEntity.getCantidadFichas200());

            if (pCajaPagoEntity.getDenominacionFicha100() != null)
                vUpdate.setDenominacionFicha100(pCajaPagoEntity.getDenominacionFicha100());

            if (pCajaPagoEntity.getCantidadFichas100() != null)
                vUpdate.setCantidadFichas100(pCajaPagoEntity.getCantidadFichas100());

            if (pCajaPagoEntity.getDenominacionFicha50() != null)
                vUpdate.setDenominacionFicha50(pCajaPagoEntity.getDenominacionFicha50());

            if (pCajaPagoEntity.getCantidadFichas50() != null)
                vUpdate.setCantidadFichas50(pCajaPagoEntity.getCantidadFichas50());

            if (pCajaPagoEntity.getDenominacionFicha20() != null)
                vUpdate.setDenominacionFicha20(pCajaPagoEntity.getDenominacionFicha20());

            if (pCajaPagoEntity.getCantidadFichas20() != null)
                vUpdate.setCantidadFichas20(pCajaPagoEntity.getCantidadFichas20());

            if (pCajaPagoEntity.getDenominacionFicha10() != null)
                vUpdate.setDenominacionFicha10(pCajaPagoEntity.getDenominacionFicha10());

            if (pCajaPagoEntity.getCantidadFichas10() != null)
                vUpdate.setCantidadFichas10(pCajaPagoEntity.getCantidadFichas10());

            if (pCajaPagoEntity.getDenominacionFicha5() != null)
                vUpdate.setDenominacionFicha5(pCajaPagoEntity.getDenominacionFicha5());

            if (pCajaPagoEntity.getCantidadFichas5() != null)
                vUpdate.setCantidadFichas5(pCajaPagoEntity.getCantidadFichas5());

            if (pCajaPagoEntity.getDenominacionFicha1() != null)
                vUpdate.setDenominacionFicha1(pCajaPagoEntity.getDenominacionFicha1());

            if (pCajaPagoEntity.getCantidadFichas1() != null)
                vUpdate.setCantidadFichas1(pCajaPagoEntity.getCantidadFichas1());

            if (pCajaPagoEntity.getMontoTotalPagado() != null)
                vUpdate.setMontoTotalPagado(pCajaPagoEntity.getMontoTotalPagado());

            if (pCajaPagoEntity.getFechaArchivo() != null)
                vUpdate.setFechaArchivo(pCajaPagoEntity.getFechaArchivo());

            this.cajaPagoRepository.save(vUpdate);

            return vUpdate;
        }
        else
            return null;
    }

    public CajaPagoEntity EliminarCajaPago(Long pCajaPagoId) {
        Optional<CajaPagoEntity> vOptionalEntity = this.cajaPagoRepository.findById(pCajaPagoId);
        CajaPagoEntity vDelete = vOptionalEntity.get();

        vDelete.setEstadoId(Constantes.ESTADO_ANULADO);
        this.cajaPagoRepository.save(vDelete);

        return vDelete;
    }

    public void EliminarTodasCajasPagos() {
        List<CajaPagoEntity> vOptionalEntity = this.cajaPagoRepository.findAll();

        if (vOptionalEntity.size() > 0) {
            vOptionalEntity.forEach(x -> x.setEstadoId(Constantes.ESTADO_ANULADO));
            this.cajaPagoRepository.saveAll(vOptionalEntity);
        }
    }

    public void EliminacionFisicaCajasPagos() {
        List<CajaPagoEntity> vOptionalEntity = this.cajaPagoRepository.findAll();

        if (vOptionalEntity.size() > 0)
            this.cajaPagoRepository.deleteAll(vOptionalEntity);
    }

    public void EliminacionFisicaCajasPagosPorFechas(String pFechaInicio, String pFechaFinal) {
        List<CajaPagoEntity> vOptionalEntity = this.cajaPagoRepository.ObtenerCajasPagosPorFechas(pFechaInicio, pFechaFinal);

        if (vOptionalEntity.size() > 0)
            this.cajaPagoRepository.deleteAll(vOptionalEntity);
    }

    public List<CajaPagoEntity> ObtenerCajasPagosPorOperador(Long pOperadorId) {
        return this.cajaPagoRepository.ObtenerCajasPagosPorOperador(pOperadorId);
    }

    public void MigrarCajasPagos(Long pOperadorId, Sheet pHoja, Timestamp pFechaArchivo) {
        DataFormatter vFormato = new DataFormatter();

        for (Row vFila: pHoja) {
            CajaPagoEntity vCajaPago = new CajaPagoEntity();
            //System.out.println("=======> " + vFila.getLastCellNum());
            if (vFila.getCell(0) != null) {
                boolean vForzarRegistro = false;
                for (Cell vCelda: vFila) {
                    String vValorCelda = vFormato.formatCellValue(vCelda).trim();

                    try {
                        if (vCelda.getRowIndex() >= 11) {
                            //System.out.println("("+vCelda.getRowIndex() + ","+ vCelda.getColumnIndex() + ")" + " - " + vCelda);

                            switch (vCelda.getColumnIndex()) {
                                case 0:
                                    vCajaPago.setNumeroCaja(Integer.parseInt(vValorCelda));
                                    break;
                                case 1:
                                    vCajaPago.setNombreCompletoJugador(vValorCelda != null ? vValorCelda.toUpperCase() : vValorCelda);
                                    break;
                                case 2:
                                    vCajaPago.setDocumentoIdentidad(vValorCelda != null ? vValorCelda.toUpperCase() : vValorCelda);
                                    break;
                                case 3:
                                    vCajaPago.setLugarExpedicion(vValorCelda);
                                    break;
                                case 4:
                                    vCajaPago.setNumeroComprobante(Integer.parseInt(vValorCelda));
                                    break;
                                case 5:
                                    if (vValorCelda != null && !vValorCelda.equals("") && !vValorCelda.equalsIgnoreCase("NULO") && !vValorCelda.equalsIgnoreCase("SIN APERTURA") && !vValorCelda.equalsIgnoreCase("SIN CIERRE"))
                                        //vCajaPago.setFechaPago(vCelda != null ? Fechas.CovertirDateToTimestamp(vCelda.getDateCellValue()) : null);
                                        vCajaPago.setFechaPago(vCelda != null ? Fechas.ConvertirFechaStringTimestamp(UtilPOI.ObtenerValorCelda(vCelda)): null);
                                    break;
                                case 6:
                                    vCajaPago.setHoraPago(vValorCelda);
                                    break;
                                case 7:
                                    if (vFila.getLastCellNum() == 26)
                                        vCajaPago.setDenominacionFicha1000(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 24 || vFila.getLastCellNum() >= 25) {
                                        vCajaPago.setDenominacionFicha1000(Constantes.DENOMINA_FICHA_1000);
                                        vCajaPago.setDenominacionFicha500(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 22) {
                                        vCajaPago.setDenominacionFicha1000(Constantes.DENOMINA_FICHA_1000);
                                        vCajaPago.setDenominacionFicha200(Integer.parseInt(vValorCelda));
                                    }
                                    break;
                                case 8:
                                    if (vFila.getLastCellNum() == 26)
                                        vCajaPago.setCantidadFichas1000(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 24 || vFila.getLastCellNum() >= 25) {
                                        vCajaPago.setCantidadFichas1000(0);
                                        vCajaPago.setCantidadFichas500(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 22) {
                                        vCajaPago.setCantidadFichas1000(0);
                                        vCajaPago.setCantidadFichas200(Integer.parseInt(vValorCelda));
                                    }
                                    break;
                                case 9:
                                    if (vFila.getLastCellNum() == 26)
                                        vCajaPago.setDenominacionFicha500(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 24 || vFila.getLastCellNum() >= 25) {
                                        vCajaPago.setDenominacionFicha200(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 22) {
                                        vCajaPago.setDenominacionFicha500(Constantes.DENOMINA_FICHA_500);
                                        vCajaPago.setDenominacionFicha100(Integer.parseInt(vValorCelda));
                                    }
                                    break;
                                case 10:
                                    if (vFila.getLastCellNum() == 26)
                                        vCajaPago.setCantidadFichas500(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 24 || vFila.getLastCellNum() >= 25) {
                                        if (vValorCelda != null && !vValorCelda.equals(""))
                                            vCajaPago.setCantidadFichas200(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 22) {
                                        vCajaPago.setCantidadFichas500(0);

                                        if (vValorCelda != null && !vValorCelda.equals(""))
                                            vCajaPago.setCantidadFichas100(Integer.parseInt(vValorCelda));
                                    }
                                    break;
                                case 11:
                                    if (vFila.getLastCellNum() == 26)
                                        vCajaPago.setDenominacionFicha200(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 24 || vFila.getLastCellNum() >= 25) {
                                        vCajaPago.setDenominacionFicha100(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 22)
                                        vCajaPago.setDenominacionFicha50(Integer.parseInt(vValorCelda));
                                    break;
                                case 12:
                                    if (vFila.getLastCellNum() == 26)
                                        vCajaPago.setCantidadFichas200(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 24 || vFila.getLastCellNum() >= 25) {
                                        vCajaPago.setCantidadFichas100(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 22)
                                        vCajaPago.setCantidadFichas50(Integer.parseInt(vValorCelda));
                                    break;
                                case 13:
                                    if (vFila.getLastCellNum() == 26)
                                        vCajaPago.setDenominacionFicha100(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 24 || vFila.getLastCellNum() >= 25) {
                                        vCajaPago.setDenominacionFicha50(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 22)
                                        vCajaPago.setDenominacionFicha20(Integer.parseInt(vValorCelda));
                                    break;
                                case 14:
                                    if (vFila.getLastCellNum() == 26)
                                        vCajaPago.setCantidadFichas100(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 24 || vFila.getLastCellNum() >= 25) {
                                        vCajaPago.setCantidadFichas50(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 22)
                                        vCajaPago.setCantidadFichas20(Integer.parseInt(vValorCelda));
                                    break;
                                case 15:
                                    if (vFila.getLastCellNum() == 26)
                                        vCajaPago.setDenominacionFicha50(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 24 || vFila.getLastCellNum() >= 25) {
                                        vCajaPago.setDenominacionFicha20(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 22)
                                        vCajaPago.setDenominacionFicha10(Integer.parseInt(vValorCelda));
                                    break;
                                case 16:
                                    if (vFila.getLastCellNum() == 26)
                                        vCajaPago.setCantidadFichas50(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 24 || vFila.getLastCellNum() >= 25) {
                                        vCajaPago.setCantidadFichas20(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 22)
                                        vCajaPago.setCantidadFichas10(Integer.parseInt(vValorCelda));
                                    break;
                                case 17:
                                    if (vFila.getLastCellNum() == 26)
                                        vCajaPago.setDenominacionFicha20(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 24 || vFila.getLastCellNum() >= 25) {
                                        vCajaPago.setDenominacionFicha10(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 22)
                                        vCajaPago.setDenominacionFicha5(Integer.parseInt(vValorCelda));
                                    break;
                                case 18:
                                    if (vFila.getLastCellNum() == 26)
                                        vCajaPago.setCantidadFichas20(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 24 || vFila.getLastCellNum() >= 25) {
                                        vCajaPago.setCantidadFichas10(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 22)
                                        vCajaPago.setCantidadFichas5(Integer.parseInt(vValorCelda));
                                    break;
                                case 19:
                                    if (vFila.getLastCellNum() == 26)
                                        vCajaPago.setDenominacionFicha10(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 24 || vFila.getLastCellNum() >= 25) {
                                        vCajaPago.setDenominacionFicha5(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 22)
                                        vCajaPago.setDenominacionFicha1(Integer.parseInt(vValorCelda));
                                    break;
                                case 20:
                                    if (vFila.getLastCellNum() == 26)
                                        vCajaPago.setCantidadFichas10(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 24 || vFila.getLastCellNum() >= 25) {
                                        vCajaPago.setCantidadFichas5(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 22)
                                        vCajaPago.setCantidadFichas1(Integer.parseInt(vValorCelda));
                                    break;
                                case 21:
                                    if (vFila.getLastCellNum() == 26)
                                        vCajaPago.setDenominacionFicha5(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 24 || vFila.getLastCellNum() >= 25) {
                                        vCajaPago.setDenominacionFicha1(Integer.parseInt(vValorCelda));
                                    }
                                    else if (vFila.getLastCellNum() == 22)
                                        vCajaPago.setMontoTotalPagado(new BigDecimal(vCelda.getNumericCellValue()));
                                    break;
                                case 22:
                                    if (vFila.getLastCellNum() == 26)
                                        vCajaPago.setCantidadFichas5(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 24 || vFila.getLastCellNum() >= 25) {
                                        vCajaPago.setCantidadFichas1(Integer.parseInt(vValorCelda));
                                    }
                                    break;
                                case 23:
                                    if (vFila.getLastCellNum() == 26)
                                        vCajaPago.setDenominacionFicha1(Integer.parseInt(vValorCelda));
                                    else if (vFila.getLastCellNum() == 24 || vFila.getLastCellNum() >= 25) {
                                        vCajaPago.setMontoTotalPagado(new BigDecimal(vCelda.getNumericCellValue()));
                                        vForzarRegistro = true;
                                    }
                                    break;
                                case 24:
                                    vCajaPago.setCantidadFichas1(Integer.parseInt(vValorCelda));
                                    break;
                                case 25:
                                    vCajaPago.setMontoTotalPagado(new BigDecimal(vCelda.getNumericCellValue()));
                                    break;
                            }
                        }
                        else
                            break;

                        if ((vFila.getLastCellNum() == 26 && vCelda.getColumnIndex() == 25)
                                || (vFila.getLastCellNum() == 24 && vCelda.getColumnIndex() == 23)
                                || (vFila.getLastCellNum() == 22 && vCelda.getColumnIndex() == 21)
                                || vForzarRegistro) {
                            vForzarRegistro = false;
                            vCajaPago.setOperadorId(pOperadorId);
                            vCajaPago.setFechaArchivo(pFechaArchivo);
                            RegistrarCajaPago(vCajaPago);
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

    public List<CajaPagoEntity> ObtenerNJugadoresGananMasDia(Long pOperadorId, String pFechaInicio, String pFechaFin, Integer pNumeroJugadores) {
        List<CajaPagoEntity> vListNJugadoresCompranMas = new ArrayList<>();

        try {
            List<Object[]> vListJugadoresGananMas = new ArrayList<>();
            //List<Object[]> vListJugadoresGananMasTodos = this.cajaPagoRepository.ObtenerNJugadoresGananMasDiaNormal(pOperadorId, pFechaInicio, pFechaFin);
            List<Object[]> vListJugadoresGananMasTodos = this.cajaPagoRepository.ObtenerNJugadoresGananMasDiaOptimizado(pOperadorId, pFechaInicio, pFechaFin);

            if (pNumeroJugadores > vListJugadoresGananMasTodos.size())
                vListJugadoresGananMas = vListJugadoresGananMasTodos.subList(0, vListJugadoresGananMasTodos.size());
            else
                vListJugadoresGananMas = vListJugadoresGananMasTodos.subList(0, pNumeroJugadores);

            for(Object[] vObjeto : vListJugadoresGananMas) {
                CajaPagoEntity vCajaPago = new CajaPagoEntity();

                vCajaPago.setNombreCompletoJugador(vObjeto[0].toString());
                vCajaPago.setDocumentoIdentidad(vObjeto[1].toString());
                vCajaPago.setCantidadGananciaDia(Integer.parseInt(vObjeto[2].toString()));
                vCajaPago.setMontoTotalPagadoSuma(BigDecimal.valueOf(Double.valueOf(vObjeto[3].toString())));

                vListNJugadoresCompranMas.add(vCajaPago);
            }

            return vListNJugadoresCompranMas;
        }
        catch(Exception ex) {
            return new ArrayList<>();
        }
    }

    public List<CajaPagoEntity> ObtenerUmbralPagosPorFechasJugador(String pDocumentoIdentidad, String pFechaInicio, String pFechaFin, BigDecimal pMontoUmbral) {
        List<CajaPagoEntity> vListUmbralCajaPagoPorJugador = new ArrayList<>();

        try {
            List<Object[]> vListUmbralPagos = this.cajaPagoRepository.ObtenerUmbralPagosPorFechasJugador(pFechaInicio, pFechaFin, pDocumentoIdentidad);

            BigDecimal vSumaAcumulada = BigDecimal.ZERO;
            Integer vCantidadGanancias = 0;

            for(Object[] vObjeto : vListUmbralPagos) {
                CajaPagoEntity vCajaPago = new CajaPagoEntity();

                vCajaPago.setNombreCompletoJugador(vObjeto[0].toString());
                vCajaPago.setDocumentoIdentidad(vObjeto[1].toString());

                vCantidadGanancias = vCantidadGanancias + 1;

                if (vObjeto[2] != null)
                    vCajaPago.setFechaArchivo(Timestamp.valueOf(vObjeto[2].toString()));

                BigDecimal vMontoAcumulado = BigDecimal.valueOf(Double.valueOf(vObjeto[3].toString()));
                vSumaAcumulada = vSumaAcumulada.add(vMontoAcumulado);

                if (vSumaAcumulada.compareTo(pMontoUmbral) != -1) {
                    vCajaPago.setMontoTotalPagadoSuma(vSumaAcumulada);
                    vCajaPago.setCantidadGananciaDia(vCantidadGanancias);
                    vListUmbralCajaPagoPorJugador.add(vCajaPago);
                    vSumaAcumulada = BigDecimal.ZERO;
                    vCantidadGanancias = 0;
                }
            }

            return vListUmbralCajaPagoPorJugador;
        }
        catch(Exception ex) {
            return new ArrayList<>();
        }
    }
}
