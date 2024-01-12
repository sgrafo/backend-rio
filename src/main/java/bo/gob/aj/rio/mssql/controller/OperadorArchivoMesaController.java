package bo.gob.aj.rio.mssql.controller;

import bo.gob.aj.rio.mssql.entity.OperadorArchivoMesaEntity;
import bo.gob.aj.rio.mssql.service.OperadorArchivoMesaService;
import bo.gob.aj.rio.pgsql.entity.*;
import bo.gob.aj.rio.pgsql.service.*;
import bo.gob.aj.rio.util.Archivo;
import bo.gob.aj.rio.util.Constantes;
import bo.gob.aj.rio.util.Fechas;
import bo.gob.aj.rio.util.UtilPOI;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.*;
import java.io.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping("/api/msql/operador_archivo_mesa")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OperadorArchivoMesaController {

    @Value("${rio.filepath}")
    public String rioFilePath;

    @Autowired
    private OperadorArchivoMesaService operadorArchivoMesaService;
    private OperadorService operadorService;
    private CajaVentaService cajaVentaService;
    private CajaPagoService cajaPagoService;
    private TransaccionMesaService transaccionMesaService;
    private ReporteDiarioMesaService reporteDiarioMesaService;
    private FacturaAnuladaService facturaAnuladaService;

    @Autowired
    public OperadorArchivoMesaController(OperadorArchivoMesaService pOperadorArchivoMesaService,
                                         OperadorService pOperadorService,
                                         CajaVentaService pCajaVentaService,
                                         CajaPagoService pCajaPagoService,
                                         TransaccionMesaService pTransaccionMesaService,
                                         ReporteDiarioMesaService pReporteDiarioMesaService,
                                         FacturaAnuladaService pFacturaAnuladaService) {
        this.operadorArchivoMesaService = pOperadorArchivoMesaService;
        this.operadorService = pOperadorService;
        this.cajaVentaService = pCajaVentaService;
        this.cajaPagoService = pCajaPagoService;
        this.transaccionMesaService = pTransaccionMesaService;
        this.reporteDiarioMesaService = pReporteDiarioMesaService;
        this.facturaAnuladaService = pFacturaAnuladaService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> ObtenerOperadoresArchivoMesa() {
        List<OperadorArchivoMesaEntity> vListArchivoMesa = this.operadorArchivoMesaService.ObtenerOperadoresArchivoMesa();

        List<Map<String, Object>> lista = new ArrayList<>();

        for (OperadorArchivoMesaEntity archivoMesa: vListArchivoMesa) {
            Map<String, Object> iterationMap = new LinkedHashMap<>();

            iterationMap.put("archivoMesaId", archivoMesa.getArchivoMesaId());
            iterationMap.put("operadorId", archivoMesa.getOperadorId());
            iterationMap.put("archivo", archivoMesa.getArchivo());
            iterationMap.put("nombreArchivo", archivoMesa.getNombreArchivo());
            iterationMap.put("fechaHoraSubida", archivoMesa.getFechaHoraSubida());
            iterationMap.put("usuarioId", archivoMesa.getUsuarioId());
            iterationMap.put("observaciones", archivoMesa.getObservaciones());
            iterationMap.put("estado", archivoMesa.getEstado());

            lista.add(iterationMap);
        }

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/migrar_rango_fechas/{pFechaInicio}/{pFechaFinal}")
    public ResponseEntity<?> ObtenerArchivoMesaPorRangoFechas(@PathVariable String pFechaInicio,
                                                              @PathVariable String pFechaFinal) throws IOException {

        Map<String, Object> objectMap = new HashMap<>();

        Boolean vRespuesta = false;
        vRespuesta = this.operadorArchivoMesaService.MigrarArchivosExcelMSSQLaPGSQL(pFechaInicio, pFechaFinal);

        if (vRespuesta) {
            objectMap.put("message", "Se han migrado los registros de las fechas indicadas.");
            objectMap.put("status", 200);
        }
        else {
            objectMap.put("message", "Ocurrió un error al migrar los registros.");
            objectMap.put("status", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(objectMap, HttpStatus.OK);
    }

    @GetMapping("/list_archivos_erroneos/{pFechaInicio}/{pFechaFinal}")
    public ResponseEntity<?> ObtenerListaArchivosErroneos(@PathVariable String pFechaInicio,
                                                          @PathVariable String pFechaFinal) throws IOException {
        String [] vNombresArchivos = new String[10000000];
        List<String> vListArchivosErroneos = new ArrayList<>();
        int vContador = 0;

        List<OperadorArchivoMesaEntity> vListArchivoMesa = this.operadorArchivoMesaService.ObtenerArchivoMesaPorRangoFechas(pFechaInicio, pFechaFinal);

        List<Map<String, Object>> lista = new ArrayList<>();

        for (OperadorArchivoMesaEntity archivoMesa: vListArchivoMesa) {
            vNombresArchivos[vContador++] = archivoMesa.getNombreArchivo();
            Archivo.SubirArchivoServidor(archivoMesa.getArchivo(), archivoMesa.getNombreArchivo());
        }

        for (int vIndice = 0; vIndice < vContador; vIndice++) {
            File vFile = new File(rioFilePath + "/" + vNombresArchivos[vIndice]);
            FileInputStream vArchivo = new FileInputStream(vFile);
            Workbook vLibroTrabajo = WorkbookFactory.create(vArchivo);

            Timestamp vFechaArchivo =  Fechas.ObtenerFechaArchivoExcel(vNombresArchivos[vIndice]);

            // Registro de la cabecera Operador
            Long vOperadorId = 0L;
            OperadorEntity vOperador = new OperadorEntity();

            Sheet vPrimerHoja = vLibroTrabajo.getSheetAt(0);
            DataFormatter vFormato = new DataFormatter();

            for (Row vFila: vPrimerHoja) {
                for (Cell vCelda: vFila) {
                    String vValorCelda = UtilPOI.ObtenerValorCelda(vCelda);

                    if (vCelda.getRowIndex() == 7 && vCelda.getColumnIndex() == 2)
                        vOperador.setNombreEmpresa(vValorCelda);
                    else if (vCelda.getRowIndex() == 8 && vCelda.getColumnIndex() == 2)
                        vOperador.setNombreSalon(vValorCelda);
                }

                if (vOperadorId == 0L && vOperador.getNombreEmpresa() != null && vOperador.getNombreSalon() != null)
                    vOperadorId = 1000L;
            }

            // Registro de Detalle
            for (Sheet vHoja: vLibroTrabajo) {
                Boolean vVolverIngresar = true;

                if (vHoja.getSheetName().equalsIgnoreCase("Caja Venta") && vVolverIngresar) {

                     for (Row vFila : vHoja) {
                         CajaVentaEntity vCajaVenta = new CajaVentaEntity();

                         if (vFila.getCell(0) != null) {
                             for (Cell vCelda : vFila) {
                                 String vValorCelda = vFormato.formatCellValue(vCelda).trim();

                                 try {
                                     if (vCelda.getRowIndex() >= 12 && vVolverIngresar) {

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

                                         if (vCelda.getColumnIndex() == 18) {
                                             /*vCajaVenta.setOperadorId(vOperadorId);
                                             vCajaVenta.setFechaArchivo(vFechaArchivo);
                                             this.cajaVentaService.RegistrarCajaVenta(vCajaVenta);*/
                                             vVolverIngresar = false;
                                         }
                                     } else
                                         break;
                                 } catch (Exception ex) {
                                     //System.out.println(ex.toString());
                                     if (vNombresArchivos[vIndice] != null) {
                                         vListArchivosErroneos.add(vNombresArchivos[vIndice] + " --> Caja Venta");
                                         vVolverIngresar = false;
                                     }
                                 }
                             }
                         }
                         else
                             break;
                     }
                }
                else if (vHoja.getSheetName().equalsIgnoreCase("Caja Pagos (Canje Fichas)") && vVolverIngresar) {

                    for (Row vFila: vHoja) {
                        CajaPagoEntity vCajaPago = new CajaPagoEntity();

                        if (vFila.getCell(0) != null) {
                            boolean vForzarRegistro = false;
                            for (Cell vCelda: vFila) {
                                String vValorCelda = vFormato.formatCellValue(vCelda).trim();

                                try {
                                    if (vCelda.getRowIndex() >= 11 && vVolverIngresar) {
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
                                            || vForzarRegistro ) {
                                        vForzarRegistro = false;
                                        /*vCajaPago.setOperadorId(vOperadorId);
                                        vCajaPago.setFechaArchivo(vFechaArchivo);
                                        this.cajaPagoService.RegistrarCajaPago(vCajaPago);*/
                                        vVolverIngresar = false;
                                    }
                                }
                                catch(Exception ex) {
                                    if (vNombresArchivos[vIndice] != null) {
                                        vListArchivosErroneos.add(vNombresArchivos[vIndice] + " --> Caja Pagos");
                                        vVolverIngresar = false;
                                    }
                                }
                            }
                        }
                        else
                            break;
                    }
                }
                else if (vHoja.getSheetName().equalsIgnoreCase("Transacciones Mesas") && vVolverIngresar) {

                    for (Row vFila: vHoja) {
                        TransaccionMesaEntity vTransaccionMesa = new TransaccionMesaEntity();

                        if (vFila.getCell(0) != null) {
                            for (Cell vCelda: vFila) {
                                String vValorCelda = vFormato.formatCellValue(vCelda).trim();

                                try {
                                    if (vCelda.getRowIndex() >= 11 && vVolverIngresar) {

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
                                        /*vTransaccionMesa.setOperadorId(vOperadorId);
                                        vTransaccionMesa.setFechaArchivo(vFechaArchivo);
                                        this.transaccionMesaService.RegistrarTransaccionMesa(vTransaccionMesa);*/
                                        vVolverIngresar = false;
                                    }
                                }
                                catch(Exception ex) {
                                    if (vNombresArchivos[vIndice] != null) {
                                        //System.out.println(ex.toString());
                                        //System.out.println("("+ vCelda.getRowIndex() + ","+ vCelda.getColumnIndex() + ")" + " - " + vCelda + " - " + vCelda.getAddress());
                                        vListArchivosErroneos.add(vNombresArchivos[vIndice] + " --> Transacciones Mesas");
                                        vVolverIngresar = false;
                                    }
                                }
                            }
                        }
                        else
                            break;
                    }
                }
                else if (vHoja.getSheetName().equalsIgnoreCase("Reporte Diario Mesas") && vVolverIngresar) {
                    for (Row vFila: vHoja) {
                        ReporteDiarioMesaEntity vReporteDiarioMesa = new ReporteDiarioMesaEntity();

                        if (vFila.getCell(1) != null) {
                            for (Cell vCelda: vFila) {
                                String vValorCelda = vFormato.formatCellValue(vCelda).trim();

                                try {
                                    if (vCelda.getRowIndex() >= 11 && vVolverIngresar) {
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
                                        /*vReporteDiarioMesa.setOperadorId(vOperadorId);
                                        vReporteDiarioMesa.setFechaArchivo(vFechaArchivo);
                                        this.reporteDiarioMesaService.RegistrarReporteDiarioMesa(vReporteDiarioMesa);*/
                                        vVolverIngresar = false;
                                    }
                                }
                                catch(Exception ex) {
                                    if (vNombresArchivos[vIndice] != null) {
                                        //System.out.println(ex.toString());
                                        //System.out.println("("+ vCelda.getRowIndex() + ","+ vCelda.getColumnIndex() + ")" + " - " + vCelda + " - " + vCelda.getAddress());
                                        vListArchivosErroneos.add(vNombresArchivos[vIndice] + " --> Reporte Diario Mesas");
                                        vVolverIngresar = false;
                                    }
                                }
                            }
                        }
                        else
                            break;
                    }
                }
                else if (vHoja.getSheetName().equalsIgnoreCase("Facturas Anuladas") && vVolverIngresar) {
                    for (Row vFila: vHoja) {
                        FacturaAnuladaEntity vFacturaAnulada = new FacturaAnuladaEntity();

                        if (vFila.getCell(0) != null) {
                            for (Cell vCelda: vFila) {
                                String vValorCelda = vFormato.formatCellValue(vCelda).trim();

                                try {
                                    if (vCelda.getRowIndex() >= 12 && vVolverIngresar) {
                                        //System.out.println("("+ vCelda.getRowIndex() + ","+ vCelda.getColumnIndex() + ")" + " - " + vCelda + " - " + vCelda.getAddress());
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
                                                vFacturaAnulada.setFechaFacturaAnulada(Fechas.ConvertirFechaStringTimestamp(UtilPOI.ObtenerValorCelda(vCelda)));
                                                //vFacturaAnulada.setFechaFacturaAnulada(Fechas.CovertirDateToTimestamp(vCelda.getDateCellValue()));
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
                                        /*vFacturaAnulada.setOperadorId(vOperadorId);
                                        vFacturaAnulada.setFechaArchivo(vFechaArchivo);
                                        this.facturaAnuladaService.RegistrarFacturaAnulada(vFacturaAnulada);*/
                                        vVolverIngresar = false;
                                    }
                                }
                                catch(Exception ex) {
                                    //System.out.println(ex.toString());
                                    //System.out.println("("+ vCelda.getRowIndex() + ","+ vCelda.getColumnIndex() + ")" + " - " + vCelda + " - " + vCelda.getAddress());
                                    vListArchivosErroneos.add(vNombresArchivos[vIndice] + " --> Facturas Anuladas");
                                    vVolverIngresar = false;
                                }
                            }
                        }
                        else
                            break;
                    }
                }
            }

            vOperadorId = 0L;
            vArchivo.close();
        }
        return new ResponseEntity<>(vListArchivosErroneos, HttpStatus.OK);
    }
}
