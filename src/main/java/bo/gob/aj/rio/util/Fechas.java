package bo.gob.aj.rio.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Fechas {

    public static Timestamp ObtenerFechaArchivoExcel(String pNombreArchivo) {
        try {
            String[] vPartes = pNombreArchivo.split("-");
            String vFechaCadenaJunta = vPartes[0];
            String vFechaCadena = vFechaCadenaJunta.substring(0, 2) + "/" + vFechaCadenaJunta.substring(2, 4) + "/" + vFechaCadenaJunta.substring(4, 8);

            DateFormat vFormato = new SimpleDateFormat("dd/MM/yyyy");
            Date vFecha = (Date) vFormato.parse(vFechaCadena);
            Timestamp vFechaHora = new Timestamp(vFecha.getTime());
            return vFechaHora;
        }
        catch(ParseException exception) {
            return null;
        }
    }
    public static Timestamp obtenerFechaActual() {
        try {
            java.sql.Timestamp timeStampDate = new Timestamp(System.currentTimeMillis());

            return timeStampDate;

        } catch (Exception ex) {
            System.out.println("Exception :" + ex);
            return null;
        }
    }
    public static Timestamp ConvertirFechaParametroTimestamp(String pFechaExcel) {
        try {
            DateFormat vFormato = new SimpleDateFormat("dd-MM-yyyy");
            Date vFecha = (Date) vFormato.parse(pFechaExcel);
            Timestamp vFechaHora = new Timestamp(vFecha.getTime());
            return vFechaHora;
        }
        catch(Exception ex) {
            return null;
        }
    }
    public static Timestamp ConvertirFechaExcelTimestamp(String pFechaExcel) {
        try {
            String fecha = pFechaExcel.substring(0, 4)+"-"+pFechaExcel.substring(5, 7)+"-"+pFechaExcel.substring(8, 10) + " " +
                           pFechaExcel.substring(11, 13) + ":" + pFechaExcel.substring(14, 16) + ":" + pFechaExcel.substring(17, 19);
            DateFormat vFormato = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date vFecha = (Date) vFormato.parse(fecha);
            Timestamp vFechaHora = new Timestamp(vFecha.getTime());
            return vFechaHora;
        }
        catch(Exception ex) {
            return null;
        }
    }

    public static Timestamp CovertirDateToTimestamp(Date pFecha) {
        try {
            Timestamp vFechaHora = new Timestamp(pFecha.getTime());
            return vFechaHora;
        }
        catch(Exception ex) {
            return null;
        }
    }
    public static String convertirFormatoDDMMYYY(Timestamp fecha)  {
        if(fecha!=null)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            return sdf.format(fecha);
        }
        else return "";

    }
    public static Date restarDias(Date fecha, int dias) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(fecha);
        cal.add(Calendar.DATE, -dias);
        return cal.getTime();
    }

    public static String ObtenerHorasFecha(Date pFecha) {
        if (pFecha != null) {
            SimpleDateFormat vFormato = new SimpleDateFormat("MM:mm");
            return vFormato.format(pFecha);
        }
        else
            return "";
    }

    public static String ConvertirFormatoFecha(String pFechaCadena, String pSeparador, String pNuevoSeparador) {
        try {
            String[] parts = pFechaCadena.split(pSeparador);
            return parts[0] + pNuevoSeparador + parts[1] + pNuevoSeparador + parts[2];
        }
        catch (ArrayIndexOutOfBoundsException exception) {
            return "";
        }
    }

    public static String ObtenerFechaConXdias(Date pFecha, int pDias) {
        SimpleDateFormat vFormato = new SimpleDateFormat("dd-MM-yyyy");
        String vFechaNula = "01-01-1900";

        try {
            Calendar vCalendar = Calendar.getInstance();
            vCalendar.setTime(pFecha);
            vCalendar.add(Calendar.DAY_OF_YEAR, pDias);

            return vFormato.format(vCalendar.getTime());
        }
        catch(Exception ex) {
            return vFechaNula;
        }
    }

    public static Timestamp ConvertirFechaStringTimestamp(String pFechaExcel) {
        try {
            DateFormat vFormato = new SimpleDateFormat("dd/MM/yyyy");
            Date vFecha = (Date) vFormato.parse(pFechaExcel);
            Timestamp vFechaHora = new Timestamp(vFecha.getTime());
            return vFechaHora;
        }
        catch(Exception ex) {
            return null;
        }
    }

    public static Timestamp ConvertirFechaHoraStringTimestamp(String pFechaExcel) {
        try {
            DateFormat vFormato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date vFecha = (Date) vFormato.parse(pFechaExcel);
            Timestamp vFechaHora = new Timestamp(vFecha.getTime());
            return vFechaHora;
        }
        catch(Exception ex) {
            return null;
        }
    }
}
