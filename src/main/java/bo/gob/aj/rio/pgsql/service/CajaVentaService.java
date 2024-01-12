package bo.gob.aj.rio.pgsql.service;

import bo.gob.aj.rio.pgsql.entity.CajaVentaEntity;
import bo.gob.aj.rio.pgsql.repository.ICajaVentaRepository;
import bo.gob.aj.rio.util.Constantes;
import bo.gob.aj.rio.util.Fechas;
import bo.gob.aj.rio.util.UtilPOI;
import bo.gob.aj.rio.util.pojo.CajaVentaPago;
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
public class CajaVentaService {

    private ICajaVentaRepository cajaVentaRepository;

    @Autowired
    public CajaVentaService(ICajaVentaRepository pCajaVentaRepository) {
        this.cajaVentaRepository = pCajaVentaRepository;
    }

    public CajaVentaEntity RegistrarCajaVenta(CajaVentaEntity pCajaVentaEntity) {
        CajaVentaEntity vCajaVenta = new CajaVentaEntity();

        vCajaVenta.setCajaVentaId(pCajaVentaEntity.getCajaVentaId());
        vCajaVenta.setOperadorId(pCajaVentaEntity.getOperadorId());
        vCajaVenta.setNumeroCaja(pCajaVentaEntity.getNumeroCaja());
        vCajaVenta.setNombreCompletoJugador(pCajaVentaEntity.getNombreCompletoJugador());
        vCajaVenta.setDocumentoIdentidad(pCajaVentaEntity.getDocumentoIdentidad());
        vCajaVenta.setLugarExpedicion(pCajaVentaEntity.getLugarExpedicion());
        vCajaVenta.setDireccionJugador(pCajaVentaEntity.getDireccionJugador());
        vCajaVenta.setFechaNacimiento(pCajaVentaEntity.getFechaNacimiento());
        vCajaVenta.setNumeroFactura(pCajaVentaEntity.getNumeroFactura());
        vCajaVenta.setNumeroAutorizacion(pCajaVentaEntity.getNumeroAutorizacion());
        vCajaVenta.setFechaFactura(pCajaVentaEntity.getFechaFactura());
        vCajaVenta.setNumeroTicket(pCajaVentaEntity.getNumeroTicket());
        vCajaVenta.setCodigoVerificacion(pCajaVentaEntity.getCodigoVerificacion());
        vCajaVenta.setCodigoControl(pCajaVentaEntity.getCodigoControl());
        vCajaVenta.setMontoTicketComprado(pCajaVentaEntity.getMontoTicketComprado());
        vCajaVenta.setFechaEmisionTicket(pCajaVentaEntity.getFechaEmisionTicket());
        vCajaVenta.setMontoImporteIva(pCajaVentaEntity.getMontoImporteIva());
        vCajaVenta.setMontoImporteSujetoIj(pCajaVentaEntity.getMontoImporteSujetoIj());
        vCajaVenta.setMontoImporteIj(pCajaVentaEntity.getMontoImporteIj());
        vCajaVenta.setMontoImporteIpj(pCajaVentaEntity.getMontoImporteIpj());
        vCajaVenta.setMontoTotalPagado(pCajaVentaEntity.getMontoTotalPagado());
        vCajaVenta.setFechaArchivo(pCajaVentaEntity.getFechaArchivo());
        vCajaVenta.setFechaRegistro(new Timestamp(System.currentTimeMillis()));
        vCajaVenta.setEstadoId(Constantes.ESTADO_ACTIVO);

        this.cajaVentaRepository.save(vCajaVenta);
        return vCajaVenta;
    }

    public CajaVentaEntity ActualizarCajaVenta(Long pCajaVentaId, CajaVentaEntity pCajaVentaEntity) {
        Optional<CajaVentaEntity> vOptionalEntity = this.cajaVentaRepository.findById(pCajaVentaId);

        if (vOptionalEntity != null) {
            CajaVentaEntity vUpdate = vOptionalEntity.get();

            if (pCajaVentaEntity.getCajaVentaId() != null)
                vUpdate.setCajaVentaId(pCajaVentaEntity.getCajaVentaId());

            if (pCajaVentaEntity.getOperadorId() != null)
                vUpdate.setOperadorId(pCajaVentaEntity.getOperadorId());

            if (pCajaVentaEntity.getNumeroCaja() != null)
                vUpdate.setNumeroCaja(pCajaVentaEntity.getNumeroCaja());

            if (pCajaVentaEntity.getNombreCompletoJugador() != null)
                vUpdate.setNombreCompletoJugador(pCajaVentaEntity.getNombreCompletoJugador());

            if (pCajaVentaEntity.getDocumentoIdentidad() != null)
                vUpdate.setDocumentoIdentidad(pCajaVentaEntity.getDocumentoIdentidad());

            if (pCajaVentaEntity.getLugarExpedicion() != null)
                vUpdate.setLugarExpedicion(pCajaVentaEntity.getLugarExpedicion());

            if (pCajaVentaEntity.getDireccionJugador() != null)
                vUpdate.setDireccionJugador(pCajaVentaEntity.getDireccionJugador());

            if (pCajaVentaEntity.getFechaNacimiento() != null)
                vUpdate.setFechaNacimiento(pCajaVentaEntity.getFechaNacimiento());

            if (pCajaVentaEntity.getNumeroFactura() != null)
                vUpdate.setNumeroFactura(pCajaVentaEntity.getNumeroFactura());

            if (pCajaVentaEntity.getNumeroAutorizacion() != null)
                vUpdate.setNumeroAutorizacion(pCajaVentaEntity.getNumeroAutorizacion());

            if (pCajaVentaEntity.getFechaFactura() != null)
                vUpdate.setFechaFactura(pCajaVentaEntity.getFechaFactura());

            if (pCajaVentaEntity.getNumeroTicket() != null)
                vUpdate.setNumeroTicket(pCajaVentaEntity.getNumeroTicket());

            if (pCajaVentaEntity.getCodigoVerificacion() != null)
                vUpdate.setCodigoVerificacion(pCajaVentaEntity.getCodigoVerificacion());

            if (pCajaVentaEntity.getCodigoControl() != null)
                vUpdate.setCodigoControl(pCajaVentaEntity.getCodigoControl());

            if (pCajaVentaEntity.getMontoTicketComprado() != null)
                vUpdate.setMontoTicketComprado(pCajaVentaEntity.getMontoTicketComprado());

            if (pCajaVentaEntity.getFechaEmisionTicket() != null)
                vUpdate.setFechaEmisionTicket(pCajaVentaEntity.getFechaEmisionTicket());

            if (pCajaVentaEntity.getMontoImporteIva() != null)
                vUpdate.setMontoImporteIva(pCajaVentaEntity.getMontoImporteIva());

            if (pCajaVentaEntity.getMontoImporteSujetoIj() != null)
                vUpdate.setMontoImporteSujetoIj(pCajaVentaEntity.getMontoImporteSujetoIj());

            if (pCajaVentaEntity.getMontoImporteIj() != null)
                vUpdate.setMontoImporteIj(pCajaVentaEntity.getMontoImporteIj());

            if (pCajaVentaEntity.getMontoImporteIpj() != null)
                vUpdate.setMontoImporteIpj(pCajaVentaEntity.getMontoImporteIpj());

            if (pCajaVentaEntity.getMontoTotalPagado() != null)
                vUpdate.setMontoTotalPagado(pCajaVentaEntity.getMontoTotalPagado());

            if (pCajaVentaEntity.getFechaArchivo() != null)
                vUpdate.setFechaArchivo(pCajaVentaEntity.getFechaArchivo());

            this.cajaVentaRepository.save(vUpdate);

            return vUpdate;
        }
        else
            return null;
    }

    public CajaVentaEntity EliminarCajaVenta(Long pCajaVentaId) {
        Optional<CajaVentaEntity> vOptionalEntity = this.cajaVentaRepository.findById(pCajaVentaId);
        CajaVentaEntity vDelete = vOptionalEntity.get();

        vDelete.setEstadoId(Constantes.ESTADO_ANULADO);
        this.cajaVentaRepository.save(vDelete);

        return vDelete;
    }

    public void EliminarTodasCajasVentas() {
        List<CajaVentaEntity> vOptionalEntity = this.cajaVentaRepository.findAll();

        if (vOptionalEntity.size() > 0) {
            vOptionalEntity.forEach(x -> x.setEstadoId(Constantes.ESTADO_ANULADO));
            this.cajaVentaRepository.saveAll(vOptionalEntity);
        }
    }

    public void EliminacionFisicaCajasVentas() {
        List<CajaVentaEntity> vOptionalEntity = this.cajaVentaRepository.findAll();

        if (vOptionalEntity.size() > 0)
            this.cajaVentaRepository.deleteAll(vOptionalEntity);
    }

    public void EliminacionFisicaCajasVentasPorFechas(String pFechaInicio, String pFechaFinal) {
        List<CajaVentaEntity> vOptionalEntity = this.cajaVentaRepository.ObtenerCajasVentasPorFechas(pFechaInicio, pFechaFinal);

        if (vOptionalEntity.size() > 0)
            this.cajaVentaRepository.deleteAll(vOptionalEntity);
    }

    public List<CajaVentaEntity> ObtenerCajasVentasPorOperador(Long pOperadorId) {
        return this.cajaVentaRepository.ObtenerCajasVentasPorOperador(pOperadorId);
    }

    public void MigrarCajasVentas(Long pOperadorId, Sheet pHoja, Timestamp pFechaArchivo) {

        DataFormatter vFormato = new DataFormatter();

        for (Row vFila: pHoja) {
            CajaVentaEntity vCajaVenta = new CajaVentaEntity();

            if (vFila.getCell(0) != null) {
                for (Cell vCelda: vFila) {
                    String vValorCelda = vFormato.formatCellValue(vCelda).trim();

                    try {
                        if (vCelda.getRowIndex() >= 12) {

                            switch (vCelda.getColumnIndex()) {
                                case 0:
                                    vCajaVenta.setNumeroCaja(Integer.parseInt(vValorCelda));
                                    break;
                                case 1:
                                    vCajaVenta.setNombreCompletoJugador(vValorCelda != null ? vValorCelda.toUpperCase() : vValorCelda);
                                    break;
                                case 2:
                                    vCajaVenta.setDocumentoIdentidad(vValorCelda != null ? vValorCelda.toUpperCase() : vValorCelda);
                                    break;
                                case 3:
                                    vCajaVenta.setLugarExpedicion(vValorCelda);
                                    break;
                                case 4:
                                    vCajaVenta.setDireccionJugador(vValorCelda);
                                    break;
                                case 5:
                                    if (vValorCelda != null && !vValorCelda.equals("") && !vValorCelda.equalsIgnoreCase("NULO"))
                                        vCajaVenta.setFechaNacimiento(Fechas.ConvertirFechaStringTimestamp(vValorCelda));
                                    break;
                                case 6:
                                    vCajaVenta.setNumeroFactura(vValorCelda);
                                    break;
                                case 7:
                                    vCajaVenta.setNumeroAutorizacion(vValorCelda);
                                    break;
                                case 8:
                                    if (vValorCelda != null && !vValorCelda.equals("") && !vValorCelda.equalsIgnoreCase("NULO"))
                                        vCajaVenta.setFechaFactura(Fechas.ConvertirFechaStringTimestamp(UtilPOI.ObtenerValorCelda(vCelda)));
                                    break;
                                case 9:
                                    vCajaVenta.setNumeroTicket(vValorCelda);
                                    break;
                                case 10:
                                    vCajaVenta.setCodigoVerificacion(vValorCelda);
                                    break;
                                case 11:
                                    vCajaVenta.setCodigoControl(vValorCelda);
                                    break;
                                case 12:
                                    vCajaVenta.setMontoTicketComprado(new BigDecimal(UtilPOI.ObtenerValorCelda(vCelda)));
                                    break;
                                case 13:
                                    if (vValorCelda != null && !vValorCelda.equals("") && !vValorCelda.equalsIgnoreCase("NULO"))
                                        vCajaVenta.setFechaEmisionTicket(Fechas.ConvertirFechaHoraStringTimestamp(UtilPOI.ObtenerValorCelda(vCelda)));
                                    break;
                                case 14:
                                    vCajaVenta.setMontoImporteIva(new BigDecimal(vCelda.getNumericCellValue()));
                                    break;
                                case 15:
                                    vCajaVenta.setMontoImporteSujetoIj(new BigDecimal(vCelda.getNumericCellValue()));
                                    break;
                                case 16:
                                    vCajaVenta.setMontoImporteIj(new BigDecimal(vCelda.getNumericCellValue()));
                                    break;
                                case 17:
                                    vCajaVenta.setMontoImporteIpj(new BigDecimal(vCelda.getNumericCellValue()));
                                    break;
                                case 18:
                                    vCajaVenta.setMontoTotalPagado(new BigDecimal(vCelda.getNumericCellValue()));
                                    break;
                            }
                        }
                        else
                            break;

                        if (vCelda.getColumnIndex() == 18) {
                            vCajaVenta.setOperadorId(pOperadorId);
                            vCajaVenta.setFechaArchivo(pFechaArchivo);
                            RegistrarCajaVenta(vCajaVenta);
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

    public List<CajaVentaPago> ObtenerCajasPagosVsCajasVentas(String pFechaInicio, String pFechaFin) {
        List<CajaVentaPago> vListCajaVentasPagos = new ArrayList<>();

        try {
            //List<Object[]> vListUmbralVentas = this.cajaVentaRepository.ObtenerCajasPagosVsCajasVentasNormal(pFechaInicio, pFechaFin);
            List<Object[]> vListUmbralVentas = this.cajaVentaRepository.ObtenerCajasPagosVsCajasVentasOptimizado(pFechaInicio, pFechaFin);

            for(Object[] vObjeto : vListUmbralVentas) {
                CajaVentaPago vCajaVentaPago = new CajaVentaPago();

                vCajaVentaPago.setNombreCompletoJugador(vObjeto[0].toString());
                vCajaVentaPago.setDocumentoIdentidad(vObjeto[1].toString());
                vCajaVentaPago.setFechaArchivo(Timestamp.valueOf(vObjeto[2].toString()));
                vCajaVentaPago.setMontoTicketComprado(BigDecimal.valueOf(Double.valueOf(vObjeto[3].toString())));
                vCajaVentaPago.setMontoTotalPagado(BigDecimal.valueOf(Double.valueOf(vObjeto[4].toString())));
                vCajaVentaPago.setDiferencia(BigDecimal.valueOf(Double.valueOf(vObjeto[5].toString())));

                vListCajaVentasPagos.add(vCajaVentaPago);
            }

            return vListCajaVentasPagos;
        }
        catch(Exception ex) {
            return new ArrayList<>();
        }
    }

    public List<CajaVentaEntity> ObtenerNJugadoresCompranMasDiaV2(Long pOperadorId, String pFechaInicio, String pFechaFin, Integer pNumeroJugadores) {
        List<CajaVentaEntity> vListNJugadoresCompranMas = new ArrayList<>();

        try {
            List<Object[]> vListJugadoresCompranMas = new ArrayList<>();
            //List<Object[]> vListJugadoresCompranMasTodos = this.cajaVentaRepository.ObtenerNJugadoresCompranMasDiaNormal(pOperadorId, pFechaInicio, pFechaFin);
            List<Object[]> vListJugadoresCompranMasTodos = this.cajaVentaRepository.ObtenerNJugadoresCompranMasDiaOptimizado(pOperadorId, pFechaInicio, pFechaFin);

            if (pNumeroJugadores > vListJugadoresCompranMasTodos.size())
                vListJugadoresCompranMas = vListJugadoresCompranMasTodos.subList(0, vListJugadoresCompranMasTodos.size());
            else
                vListJugadoresCompranMas = vListJugadoresCompranMasTodos.subList(0, pNumeroJugadores);

            for(Object[] vObjeto : vListJugadoresCompranMas) {
                CajaVentaEntity vCajaVenta = new CajaVentaEntity();

                vCajaVenta.setNombreCompletoJugador(vObjeto[0].toString());
                vCajaVenta.setDocumentoIdentidad(vObjeto[1].toString());
                vCajaVenta.setCantidadComprasDia(Integer.parseInt(vObjeto[2].toString()));
                vCajaVenta.setMontoTotalCompras(BigDecimal.valueOf(Double.valueOf(vObjeto[3].toString())));

                vListNJugadoresCompranMas.add(vCajaVenta);
            }

            return vListNJugadoresCompranMas;
        }
        catch(Exception ex) {
            return new ArrayList<>();
        }
    }

    public List<CajaVentaEntity> ObtenerUmbralVentasPorFechasJugador(String pDocumentoIdentidad, String pFechaInicio, String pFechaFin, BigDecimal pMontoUmbral) {
        List<CajaVentaEntity> vListUmbralCajaVentaPorJugador = new ArrayList<>();

        try {
            List<Object[]> vListUmbralVentas = this.cajaVentaRepository.ObtenerUmbralVentasPorFechasJugador(pFechaInicio, pFechaFin, pDocumentoIdentidad);

            BigDecimal vSumaAcumulada = BigDecimal.ZERO;
            Integer vCantidadCompras = 0;

            for (Object[] vObjeto : vListUmbralVentas) {
                CajaVentaEntity vCajaVenta = new CajaVentaEntity();

                vCajaVenta.setNombreCompletoJugador(vObjeto[0].toString());
                vCajaVenta.setDocumentoIdentidad(vObjeto[1].toString());

                vCantidadCompras = vCantidadCompras + 1;

                if (vObjeto[2] != null)
                    vCajaVenta.setFechaArchivo(Timestamp.valueOf(vObjeto[2].toString()));

                BigDecimal vMontoAcumulado = BigDecimal.valueOf(Double.valueOf(vObjeto[3].toString()));
                vSumaAcumulada = vSumaAcumulada.add(vMontoAcumulado);

                if (vSumaAcumulada.compareTo(pMontoUmbral) != -1) {
                    vCajaVenta.setMontoAcumuladoUmbral(vSumaAcumulada);
                    vCajaVenta.setCantidadComprasDia(vCantidadCompras);
                    vListUmbralCajaVentaPorJugador.add(vCajaVenta);
                    vSumaAcumulada = BigDecimal.ZERO; // Reiniciar suma de monto acumulado
                    vCantidadCompras = 0; // reiniciar conteo de compras o transacciones
                }
            }

            return vListUmbralCajaVentaPorJugador;
        }
        catch(Exception ex) {
            return new ArrayList<>();
        }
    }
}
