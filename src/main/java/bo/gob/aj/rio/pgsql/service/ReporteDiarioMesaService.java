package bo.gob.aj.rio.pgsql.service;

import bo.gob.aj.rio.pgsql.entity.ReporteDiarioMesaEntity;
import bo.gob.aj.rio.pgsql.repository.IReporteDiarioMesaRepository;
import bo.gob.aj.rio.util.Constantes;
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
public class ReporteDiarioMesaService {

    private IReporteDiarioMesaRepository reporteDiarioMesaRepository;

    @Autowired
    public ReporteDiarioMesaService(IReporteDiarioMesaRepository pReporteDiarioMesaRepository) {
        this.reporteDiarioMesaRepository = pReporteDiarioMesaRepository;
    }

    public ReporteDiarioMesaEntity RegistrarReporteDiarioMesa(ReporteDiarioMesaEntity pReporteDiarioMesaEntity) {
        ReporteDiarioMesaEntity vReporteDiarioMesa = new ReporteDiarioMesaEntity();

        //vReporteDiarioMesa.setReporteDiarioMesaId(pReporteDiarioMesaEntity.getReporteDiarioMesaId());
        vReporteDiarioMesa.setOperadorId(pReporteDiarioMesaEntity.getOperadorId());
        vReporteDiarioMesa.setNumeroPrecinto(pReporteDiarioMesaEntity.getNumeroPrecinto());
        vReporteDiarioMesa.setIdentificacionMesa(pReporteDiarioMesaEntity.getIdentificacionMesa());
        vReporteDiarioMesa.setJuego(pReporteDiarioMesaEntity.getJuego());
        vReporteDiarioMesa.setApertura(pReporteDiarioMesaEntity.getApertura());
        vReporteDiarioMesa.setRelleno(pReporteDiarioMesaEntity.getRelleno());
        vReporteDiarioMesa.setDevolucion(pReporteDiarioMesaEntity.getDevolucion());
        vReporteDiarioMesa.setCierre(pReporteDiarioMesaEntity.getCierre());
        vReporteDiarioMesa.setCantidadTicketsAutorizados(pReporteDiarioMesaEntity.getCantidadTicketsAutorizados());
        vReporteDiarioMesa.setMontoTicketsAutorizados(pReporteDiarioMesaEntity.getMontoTicketsAutorizados());
        vReporteDiarioMesa.setPremios(pReporteDiarioMesaEntity.getPremios());
        vReporteDiarioMesa.setResultado(pReporteDiarioMesaEntity.getResultado());
        vReporteDiarioMesa.setFechaArchivo(pReporteDiarioMesaEntity.getFechaArchivo());
        vReporteDiarioMesa.setFechaRegistro(new Timestamp(System.currentTimeMillis()));
        vReporteDiarioMesa.setEstadoId(Constantes.ESTADO_ACTIVO);

        this.reporteDiarioMesaRepository.save(vReporteDiarioMesa);

        return vReporteDiarioMesa;
    }

    public ReporteDiarioMesaEntity ActualizarReporteDiarioMesa(Long pReporteDiarioMesaId, ReporteDiarioMesaEntity pReporteDiarioMesaEntity) {
        Optional<ReporteDiarioMesaEntity> vOptionalEntity = this.reporteDiarioMesaRepository.findById(pReporteDiarioMesaId);

        if (vOptionalEntity != null) {
            ReporteDiarioMesaEntity vUpdate = vOptionalEntity.get();

            if (pReporteDiarioMesaEntity.getOperadorId() != null)
                vUpdate.setOperadorId(pReporteDiarioMesaEntity.getOperadorId());

            if (pReporteDiarioMesaEntity.getNumeroPrecinto() != null)
                vUpdate.setNumeroPrecinto(pReporteDiarioMesaEntity.getNumeroPrecinto());

            if (pReporteDiarioMesaEntity.getIdentificacionMesa() != null)
                vUpdate.setIdentificacionMesa(pReporteDiarioMesaEntity.getIdentificacionMesa());

            if (pReporteDiarioMesaEntity.getJuego() != null)
                vUpdate.setJuego(pReporteDiarioMesaEntity.getJuego());

            if (pReporteDiarioMesaEntity.getApertura() != null)
                vUpdate.setApertura(pReporteDiarioMesaEntity.getApertura());

            if (pReporteDiarioMesaEntity.getRelleno() != null)
                vUpdate.setRelleno(pReporteDiarioMesaEntity.getRelleno());

            if (pReporteDiarioMesaEntity.getDevolucion() != null)
                vUpdate.setDevolucion(pReporteDiarioMesaEntity.getDevolucion());

            if (pReporteDiarioMesaEntity.getCierre() != null)
                vUpdate.setCierre(pReporteDiarioMesaEntity.getCierre());

            if (pReporteDiarioMesaEntity.getCantidadTicketsAutorizados() != null)
                vUpdate.setCantidadTicketsAutorizados(pReporteDiarioMesaEntity.getCantidadTicketsAutorizados());

            if (pReporteDiarioMesaEntity.getMontoTicketsAutorizados() != null)
                vUpdate.setMontoTicketsAutorizados(pReporteDiarioMesaEntity.getMontoTicketsAutorizados());

            if (pReporteDiarioMesaEntity.getPremios() != null)
                vUpdate.setPremios(pReporteDiarioMesaEntity.getPremios());

            if (pReporteDiarioMesaEntity.getResultado() != null)
                vUpdate.setResultado(pReporteDiarioMesaEntity.getResultado());

            if (pReporteDiarioMesaEntity.getFechaArchivo() != null)
                vUpdate.setFechaArchivo(pReporteDiarioMesaEntity.getFechaArchivo());

            this.reporteDiarioMesaRepository.save(vUpdate);

            return vUpdate;
        }
        else
            return null;
    }

    public ReporteDiarioMesaEntity EliminarReporteDiarioMesa(Long pReporteDiarioMesaId) {
        Optional<ReporteDiarioMesaEntity> vOptionalEntity = this.reporteDiarioMesaRepository.findById(pReporteDiarioMesaId);
        ReporteDiarioMesaEntity vDelete = vOptionalEntity.get();

        vDelete.setEstadoId(Constantes.ESTADO_ANULADO);
        this.reporteDiarioMesaRepository.save(vDelete);

        return vDelete;
    }

    public void EliminarTodosReportesDiariosMesas() {
        List<ReporteDiarioMesaEntity> vOptionalEntity = this.reporteDiarioMesaRepository.findAll();

        if (vOptionalEntity.size() > 0) {
            vOptionalEntity.forEach(x -> x.setEstadoId(Constantes.ESTADO_ANULADO));
            this.reporteDiarioMesaRepository.saveAll(vOptionalEntity);
        }
    }

    public void EliminacionFisicaReportesDiariosMesas() {
        List<ReporteDiarioMesaEntity> vOptionalEntity = this.reporteDiarioMesaRepository.findAll();

        if (vOptionalEntity.size() > 0)
            this.reporteDiarioMesaRepository.deleteAll(vOptionalEntity);
    }

    public void EliminacionFisicaReportesDiariosMesasPorFechas(String pFechaInicio, String pFechaFinal) {
        List<ReporteDiarioMesaEntity> vOptionalEntity = this.reporteDiarioMesaRepository.ObtenerReporteDiarioMesaPorFechas(pFechaInicio, pFechaFinal);

        if (vOptionalEntity.size() > 0)
            this.reporteDiarioMesaRepository.deleteAll(vOptionalEntity);
    }

    public List<ReporteDiarioMesaEntity> ObtenerReporteDiarioMesaPorOperador(Long pOperadorId) {
        return this.reporteDiarioMesaRepository.ObtenerReporteDiarioMesaPorOperador(pOperadorId);
    }

    public void MigrarReportesDiariosMesas(Long pOperadorId, Sheet pHoja, Timestamp pFechaArchivo) {
        DataFormatter vFormato = new DataFormatter();

        for (Row vFila: pHoja) {
            ReporteDiarioMesaEntity vReporteDiarioMesa = new ReporteDiarioMesaEntity();

            if (vFila.getCell(1) != null) {
                for (Cell vCelda: vFila) {
                    String vValorCelda = vFormato.formatCellValue(vCelda).trim();

                    try {
                        if (vCelda.getRowIndex() >= 11) {
                            //System.out.println("("+vCelda.getRowIndex() + ","+ vCelda.getColumnIndex() + ")" + " - " + vCelda);

                            switch (vCelda.getColumnIndex()) {
                                case 1:
                                    vReporteDiarioMesa.setNumeroPrecinto(Integer.parseInt(vValorCelda));
                                    break;
                                case 2:
                                    vReporteDiarioMesa.setIdentificacionMesa(vValorCelda);
                                    break;
                                case 3:
                                    vReporteDiarioMesa.setJuego(vValorCelda);
                                    break;
                                case 4:
                                    vReporteDiarioMesa.setApertura(new BigDecimal(UtilPOI.ObtenerValorCelda(vCelda)));
                                    break;
                                case 5:
                                    vReporteDiarioMesa.setRelleno(new BigDecimal(UtilPOI.ObtenerValorCelda(vCelda)));
                                    break;
                                case 6:
                                    vReporteDiarioMesa.setDevolucion(new BigDecimal(UtilPOI.ObtenerValorCelda(vCelda)));
                                    break;
                                case 7:
                                    vReporteDiarioMesa.setCierre(new BigDecimal(UtilPOI.ObtenerValorCelda(vCelda)));
                                    break;
                                case 8:
                                    vReporteDiarioMesa.setCantidadTicketsAutorizados(Integer.parseInt(vValorCelda));
                                    break;
                                case 9:
                                    vReporteDiarioMesa.setMontoTicketsAutorizados(new BigDecimal(UtilPOI.ObtenerValorCelda(vCelda)));
                                    break;
                                case 10:
                                    vReporteDiarioMesa.setPremios(new BigDecimal(UtilPOI.ObtenerValorCelda(vCelda)));
                                    break;
                                case 11:
                                    vReporteDiarioMesa.setResultado(new BigDecimal(vCelda.getNumericCellValue()));
                                    break;
                            }
                        }
                        else
                            break;

                        if (vCelda.getColumnIndex() == 11 && vReporteDiarioMesa.getNumeroPrecinto() > 0) {
                            vReporteDiarioMesa.setOperadorId(pOperadorId);
                            vReporteDiarioMesa.setFechaArchivo(pFechaArchivo);
                            RegistrarReporteDiarioMesa(vReporteDiarioMesa);
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
