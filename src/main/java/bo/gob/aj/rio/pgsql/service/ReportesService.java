package bo.gob.aj.rio.pgsql.service;

import bo.gob.aj.rio.pgsql.model.*;
import bo.gob.aj.rio.pgsql.repository.ProcedimientosFuncionesRepository;
import bo.gob.aj.rio.util.Convertir;
import bo.gob.aj.rio.util.Fechas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportesService {
    @Autowired
    private ProcedimientosFuncionesRepository procedimientosFuncionesRepository;



    public List<CajaVentaReporteDTO> obtenerCajaVentasTodos(Long pOperadorId, Timestamp pFechaInicio, Timestamp pFechaFin) {

        try{


        List<Object[]> lstObject = this.procedimientosFuncionesRepository.obtenerCajaVentasTodos(pOperadorId,pFechaInicio,pFechaFin);
        List<CajaVentaReporteDTO> lst = lstObject.stream()
                .map(p -> new CajaVentaReporteDTO(
                        p[0]+"",
                        p[1]+"",
                        Convertir.convertToLong(p[2]+""),
                        Convertir.convertToInteger(p[3]+""),
                        p[4]+"",
                        p[5]+"",
                        p[6]+"",
                        p[7]+"",
                        Fechas.ConvertirFechaExcelTimestamp(p[8]+""), // TimeStamp
                        p[9]+"",
                        p[10]+"",
                        Fechas.ConvertirFechaExcelTimestamp(p[11]+""), // TimeStamp
                        p[12]+"",
                        p[13]+"",
                        p[14]+"",
                        Convertir.convertToBigDecimal(p[15]+""),
                        Fechas.ConvertirFechaExcelTimestamp(p[16]+""), // TimeStamp
                        Convertir.convertToBigDecimal(p[17]+""),
                        Convertir.convertToBigDecimal(p[18]+""),
                        Convertir.convertToBigDecimal(p[19]+""),
                        Convertir.convertToBigDecimal(p[20]+""),
                        Convertir.convertToBigDecimal(p[21]+""),
                        Fechas.ConvertirFechaExcelTimestamp(p[22]+""), // TimeStamp
                        Fechas.ConvertirFechaExcelTimestamp(p[23]+""), // TimeStamp
                        Convertir.convertToInteger(p[24]+""),
                        Convertir.convertToLong(p[25]+"")

                ))
                .collect(Collectors.toList());
        return lst;
        }catch (Exception ex){
            return new ArrayList<>();
        }
    }
    public List<CajaPagosReporteDTO> obtenerCajaPagosTodos(Long pOperadorId, Timestamp pFechaInicio, Timestamp pFechaFin) {
        List<Object[]> lstObj = this.procedimientosFuncionesRepository.obtenerCajaPagosTodos(pOperadorId,pFechaInicio,pFechaFin);
        List<CajaPagosReporteDTO> lst = lstObj.stream()
                .map(p -> new CajaPagosReporteDTO(
                        p[0]+"",
                        p[1]+"",
                        Convertir.convertToLong(p[2]+""),
                        Convertir.convertToInteger(p[3]+""),
                        p[4]+"",
                        p[5]+"",
                        p[6]+"",
                        Convertir.convertToInteger(p[7]+""),
                        Fechas.ConvertirFechaExcelTimestamp(p[8]+""), // TimeStamp
                        p[9]+"",
                        Convertir.convertToInteger( p[10]+""),
                        Convertir.convertToInteger(p[11]+""),
                        Convertir.convertToInteger(p[12]+""),
                        Convertir.convertToInteger(p[13]+""),
                        Convertir.convertToInteger(p[14]+""),
                        Convertir.convertToInteger(p[15]+""),
                        Convertir.convertToInteger(p[16]+""),
                        Convertir.convertToInteger(p[17]+""),
                        Convertir.convertToInteger(p[18]+""),
                        Convertir.convertToInteger(p[19]+""),
                        Convertir.convertToInteger(p[20]+""),
                        Convertir.convertToInteger(p[21]+""),
                        Convertir.convertToInteger(p[22]+""),
                        Convertir.convertToInteger(p[23]+""),
                        Convertir.convertToInteger(p[24]+""),
                        Convertir.convertToInteger(p[25]+""),
                        Convertir.convertToInteger(p[26]+""),
                        Convertir.convertToInteger(p[27]+""),
                        Convertir.convertToBigDecimal(p[28]+""),
                        Fechas.ConvertirFechaExcelTimestamp(p[29]+""),
                        Fechas.ConvertirFechaExcelTimestamp(p[30]+""),
                        Convertir.convertToInteger(p[31]+""),
                        Convertir.convertToLong(p[32]+"")

                ))
                .collect(Collectors.toList());
        return lst;
    }
    public List<TransaccionesMesaReporteDTO> obtenerTransaccionesMesaTodos(Long pOperadorId, Timestamp pFechaInicio, Timestamp pFechaFin) {
        try{


        List<Object[]> lstObj = this.procedimientosFuncionesRepository.obtenerTransaccionesMesaTodos(pOperadorId,pFechaInicio,pFechaFin);
        List<TransaccionesMesaReporteDTO> lst = lstObj.stream()
                .map(p -> new TransaccionesMesaReporteDTO(
                        p[0]+"",
                        p[1]+"",
                        Convertir.convertToLong(p[2]+""),
                        p[3]+"",
                        Fechas.ConvertirFechaExcelTimestamp(p[4]+""),
                        p[5]+"",
                        p[6]+"",
                        p[7]+"",
                        Convertir.convertToInteger(p[8]+""), // TimeStamp
                        Convertir.convertToInteger(p[9]+""),
                        Convertir.convertToInteger(p[10]+""),
                        Convertir.convertToInteger(p[11]+""),
                        Convertir.convertToInteger(p[12]+""),
                        Convertir.convertToInteger(p[13]+""),
                        Convertir.convertToInteger(p[14]+""),
                        Convertir.convertToInteger(p[15]+""),
                        Convertir.convertToInteger(p[16]+""),
                        Convertir.convertToInteger(p[17]+""),
                        Convertir.convertToInteger(p[18]+""),
                        Convertir.convertToInteger(p[19]+""),
                        Convertir.convertToInteger(p[20]+""),
                        Convertir.convertToInteger(p[21]+""),
                        Convertir.convertToInteger(p[22]+""),
                        Convertir.convertToInteger(p[23]+""),
                        Convertir.convertToInteger(p[24]+""),
                        Convertir.convertToInteger(p[25]+""),
                        Convertir.convertToBigDecimal(p[26]+""),
                        Fechas.ConvertirFechaExcelTimestamp(p[27]+""),
                        Fechas.ConvertirFechaExcelTimestamp(p[28]+""),
                        Convertir.convertToInteger(p[29]+""),
                        Convertir.convertToLong(p[30]+"")

                ))
                .collect(Collectors.toList());
        return lst;
        }catch (Exception ex){
            return new ArrayList<>();
        }
    }
    public List<ReporteDiarioMesaReporteDTO> obtenerRepDiarioMesaTodos(Long pOperadorId, Timestamp pFechaInicio, Timestamp pFechaFin) {
        try{


        List<Object[]> lstObj = this.procedimientosFuncionesRepository.obtenerRepDiarioMesaTodos(pOperadorId,pFechaInicio,pFechaFin);
        List<ReporteDiarioMesaReporteDTO> lst = lstObj.stream()
                .map(p -> new ReporteDiarioMesaReporteDTO(
                        p[0]+"",
                        p[1]+"",
                        Convertir.convertToLong(p[2]+""),
                        Convertir.convertToInteger(p[3]+""),
                        p[4]+"",
                        p[5]+"",
                        Convertir.convertToBigDecimal(p[6]+""),
                        Convertir.convertToBigDecimal(p[7]+""),
                        Convertir.convertToBigDecimal(p[8]+""),
                        Convertir.convertToBigDecimal(p[9]+""),
                        Convertir.convertToInteger(p[10]+""),
                        Convertir.convertToBigDecimal(p[11]+""),
                        Convertir.convertToBigDecimal(p[12]+""),
                        Convertir.convertToBigDecimal(p[13]+""),
                        Fechas.ConvertirFechaExcelTimestamp(p[14]+""),
                        Fechas.ConvertirFechaExcelTimestamp(p[15]+""),
                        Convertir.convertToInteger(p[16]+""),
                        Convertir.convertToLong(p[17]+"")

                ))
                .collect(Collectors.toList());
        return lst;
        }catch (Exception ex){
            return  new ArrayList<>();
        }
    }
    public List<FacturasAnuladasDTO> obtenerFacturaAnuladasTodos(Long pOperadorId, Timestamp pFechaInicio, Timestamp pFechaFin) {
        try
        {


        List<Object[]> lstObj = this.procedimientosFuncionesRepository.obtenerFacturaAnuladasTodos(pOperadorId,pFechaInicio,pFechaFin);
        List<FacturasAnuladasDTO> lst = lstObj.stream()
                .map(p -> new FacturasAnuladasDTO(
                        p[0]+"",
                        p[1]+"",
                        Convertir.convertToLong(p[2]+""),
                        Convertir.convertToInteger(p[3]+""),
                        p[4]+"",
                        p[5]+"",
                        p[6]+"",
                        p[7]+"",
                        Convertir.convertToLong(p[8]+""), // TimeStamp
                        Fechas.ConvertirFechaExcelTimestamp(p[9]+""),
                        Convertir.convertToBigDecimal(p[10]+""),
                        Convertir.convertToInteger(p[11]+""),
                        Convertir.convertToBigDecimal(p[12]+""),
                        Fechas.ConvertirFechaExcelTimestamp(p[13]+""),
                        p[14]+"",
                        Convertir.convertToBigDecimal(p[15]+""),
                        Fechas.ConvertirFechaExcelTimestamp(p[16]+""),
                        Fechas.ConvertirFechaExcelTimestamp(p[17]+""),
                        Convertir.convertToInteger(p[18]+""),
                        Convertir.convertToLong(p[19]+"")

                ))
                .collect(Collectors.toList());
        return lst;
        }catch (Exception ex){
            return new ArrayList<>();
        }
    }
}
