package bo.gob.aj.rio.mssql.service;

import bo.gob.aj.rio.mssql.entity.OperadorArchivoMesaEntity;
import bo.gob.aj.rio.mssql.repository.IOperadorArchivoMesaRepository;
import bo.gob.aj.rio.pgsql.entity.OperadorEntity;
import bo.gob.aj.rio.pgsql.service.*;
import bo.gob.aj.rio.util.Archivo;
import bo.gob.aj.rio.util.Fechas;
import bo.gob.aj.rio.util.UtilPOI;
import org.apache.poi.ss.usermodel.*;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OperadorArchivoMesaService {

    private IOperadorArchivoMesaRepository operadorArchivoMesaRepository;
    private OperadorService operadorService;
    private CajaVentaService cajaVentaService;
    private CajaPagoService cajaPagoService;
    private TransaccionMesaService transaccionMesaService;
    private ReporteDiarioMesaService reporteDiarioMesaService;
    private FacturaAnuladaService facturaAnuladaService;

    @Value("${rio.filepath}")
    public String rioFilePath;

    @Value("${fecha.primer.archivo}")
    public String vFechaPrimerArchivo;

    @Autowired
    public OperadorArchivoMesaService(IOperadorArchivoMesaRepository pOperadorArchivoMesaRepository,
                                      OperadorService pOperadorService,
                                      CajaVentaService pCajaVentaService,
                                      CajaPagoService pCajaPagoService,
                                      TransaccionMesaService pTransaccionMesaService,
                                      ReporteDiarioMesaService pReporteDiarioMesaService,
                                      FacturaAnuladaService pFacturaAnuladaService) {
        this.operadorArchivoMesaRepository = pOperadorArchivoMesaRepository;
        this.operadorService = pOperadorService;
        this.cajaVentaService = pCajaVentaService;
        this.cajaPagoService = pCajaPagoService;
        this.transaccionMesaService = pTransaccionMesaService;
        this.reporteDiarioMesaService = pReporteDiarioMesaService;
        this.facturaAnuladaService = pFacturaAnuladaService;
    }

    public List<OperadorArchivoMesaEntity> ObtenerOperadoresArchivoMesa() {
        return this.operadorArchivoMesaRepository.findAll();
    }

    public List<OperadorArchivoMesaEntity> ObtenerArchivoMesaPorRangoFechas(String pFechaInicio, String pFechaFinal) {
        return this.operadorArchivoMesaRepository.ObtenerArchivoMesaPorRangoFechas(pFechaInicio, pFechaFinal);
    }

    public Boolean MigrarArchivosExcelMSSQLaPGSQL(String pFechaInicio, String pFechaFinal) {
        String vFechaInicioCadena = "";
        String vFechaFinCadena = "";

        String [] vNombresArchivos = new String[10000000];
        int vContador = 0;

        try {
            // Eliminar los archivos que están dentro la carpeta "rio"
            FileUtils.cleanDirectory(new File(rioFilePath));

            // Eliminar registros migrados entre las fechas dadas
            if (pFechaInicio.length() > 1 && pFechaFinal.length() > 1) {
                vFechaInicioCadena = Fechas.ConvertirFormatoFecha(pFechaInicio, "-", "/");
                vFechaFinCadena = Fechas.ConvertirFormatoFecha(pFechaFinal, "-", "/");

                facturaAnuladaService.EliminacionFisicaFacturasAnuladasPorFechas(vFechaInicioCadena, vFechaFinCadena);
                reporteDiarioMesaService.EliminacionFisicaReportesDiariosMesasPorFechas(vFechaInicioCadena, vFechaFinCadena);
                transaccionMesaService.EliminacionFisicaTransaccionesMesasPorFechas(vFechaInicioCadena, vFechaFinCadena);
                cajaPagoService.EliminacionFisicaCajasPagosPorFechas(vFechaInicioCadena, vFechaFinCadena);
                cajaVentaService.EliminacionFisicaCajasVentasPorFechas(vFechaInicioCadena, vFechaFinCadena);
            }
            else {
                DateFormat vFormato = new SimpleDateFormat("dd-MM-yyyy");
                Date vFechaActual = new Date();
                vFechaInicioCadena = vFechaPrimerArchivo;
                vFechaFinCadena = vFormato.format(vFechaActual);

                facturaAnuladaService.EliminacionFisicaFacturasAnuladas();
                reporteDiarioMesaService.EliminacionFisicaReportesDiariosMesas();
                transaccionMesaService.EliminacionFisicaTransaccionesMesas();
                cajaPagoService.EliminacionFisicaCajasPagos();
                cajaVentaService.EliminacionFisicaCajasVentas();
            }
            operadorService.EliminacionFisicaOperadores();

            List<OperadorArchivoMesaEntity> vListArchivoMesa = ObtenerArchivoMesaPorRangoFechas(vFechaInicioCadena, vFechaFinCadena);

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
                        vOperadorId = this.operadorService.ObtenerOperador(vOperador);
                }

                // Registro de Detalle
                for (Sheet vHoja: vLibroTrabajo) {
                    if (vHoja.getSheetName().equalsIgnoreCase("Caja Venta")) {
                        this.cajaVentaService.MigrarCajasVentas(vOperadorId, vHoja, vFechaArchivo);
                    }
                    else if (vHoja.getSheetName().equalsIgnoreCase("Caja Pagos (Canje Fichas)")) {
                        this.cajaPagoService.MigrarCajasPagos(vOperadorId, vHoja, vFechaArchivo);
                    }
                    else if (vHoja.getSheetName().equalsIgnoreCase("Transacciones Mesas")) {
                        this.transaccionMesaService.MigrarTransaccionesMesas(vOperadorId, vHoja, vFechaArchivo);
                    }
                    else if (vHoja.getSheetName().equalsIgnoreCase("Reporte Diario Mesas")) {
                        this.reporteDiarioMesaService.MigrarReportesDiariosMesas(vOperadorId, vHoja, vFechaArchivo);
                    }
                    else if (vHoja.getSheetName().equalsIgnoreCase("Facturas Anuladas")) {
                        this.facturaAnuladaService.MigrarFacturasAnuladas(vOperadorId, vHoja, vFechaArchivo);
                    }
                }
                vOperadorId = 0L;
                vArchivo.close();
            }

            return true;
        }
        catch(Exception ex) {
            return false;
        }
    }
}
                  