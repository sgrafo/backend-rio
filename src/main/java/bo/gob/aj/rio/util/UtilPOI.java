package bo.gob.aj.rio.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

import java.text.SimpleDateFormat;

public class UtilPOI {

    public static String ObtenerValorCelda(Cell pCelda) {

        String vValorCelda = "";

        switch (pCelda.getCellType()) {
            case BOOLEAN:
                vValorCelda = String.valueOf(pCelda.getBooleanCellValue());
                break;
            case STRING:
                vValorCelda = pCelda.getRichStringCellValue().getString();
                break;
            case NUMERIC:
                //vValorCelda = String.valueOf(pCelda.getNumericCellValue());
                if (DateUtil.isCellDateFormatted(pCelda)) {
                    SimpleDateFormat vFormato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    vValorCelda = vFormato.format(pCelda.getDateCellValue());
                }
                else
                    vValorCelda = String.valueOf(pCelda.getNumericCellValue());
                break;
            case FORMULA:
                vValorCelda = String.valueOf(pCelda.getCellFormula());
                break;
            case BLANK:
                vValorCelda = "";
                break;
            default:
                vValorCelda = "";
        }
        return vValorCelda;
    }
}
