package bo.gob.aj.rio.util.scheduler;

import bo.gob.aj.rio.mssql.entity.OperadorArchivoMesaEntity;
import bo.gob.aj.rio.mssql.service.OperadorArchivoMesaService;
import bo.gob.aj.rio.pgsql.service.*;
import bo.gob.aj.rio.util.Fechas;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Component
public class SchedulingTasksMigrar
{
    @Autowired
    private OperadorArchivoMesaService operadorArchivoMesaService;

    @Value("${rio.filepath}")
    private String rutaCarpetaRio;

    @Autowired
    public SchedulingTasksMigrar(OperadorArchivoMesaService pOperadorArchivoMesaService) {
        this.operadorArchivoMesaService = pOperadorArchivoMesaService;
    }

    //@Scheduled(fixedDelay = 15000)//cada 10 segundos
    /*@Scheduled(cron = "0 30 2 * * ?") // se ejecutara a las 2 de la mañana de cada dia
    public void MigrarDatosDeExcelApostgre(){
        try{
            Date fechaActualMenos1Dia = Fechas.restarDias(new Date(),1);
            List<OperadorArchivoMesaEntity> vListArchivoMesa = this.operadorArchivoMesaService.ObtenerArchivoMesaPorRangoFechas(Fechas.convertirFormatoDDMMYYY(new Timestamp(fechaActualMenos1Dia.getTime())),Fechas.convertirFormatoDDMMYYY( new Timestamp(fechaActualMenos1Dia.getTime())));
            System.out.println("fecha actual: "+fechaActualMenos1Dia.toString());
        }catch (Exception ex){

        }
    }*/

    //1 -> segundos (0-59)
    //2 -> minutos (0-59)
    //3 -> horas (0-23)
    //4 -> días (1-31)
    //5 -> meses (1-12)
    //6 -> día de la semana (1-7)
    //                 1 2 3 4 5 6
    @Scheduled(cron = "0 0 2 * * *") // Todos los días a las 02:00 de la mañana
    //@Scheduled(cron = "0 * * * * *") // Cada minuto
    public void MigrarDatosMSSQLExcelPGSQL() {
        try {
            String vFechaIni = Fechas.ObtenerFechaConXdias(new Date(), -1);
            String vFechaFin = Fechas.ObtenerFechaConXdias(new Date(), 0);

            Boolean vRespuesta = false;
            vRespuesta = this.operadorArchivoMesaService.MigrarArchivosExcelMSSQLaPGSQL(vFechaIni, vFechaFin);

            if (vRespuesta)
                System.out.println("Migración realizada con éxito..");
        }
        catch(Exception ex) {
            System.out.println("No se pudo llevar a cabo la operación.");
        }
    }
}