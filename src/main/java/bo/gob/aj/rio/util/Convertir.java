package bo.gob.aj.rio.util;

import java.math.BigDecimal;

public class Convertir {
    public static Integer  convertToInteger(String valor){
        try{
            return new Integer(valor.trim());
        }catch (Exception ex){
            return null;
        }
    }
    public static BigDecimal convertToBigDecimal(String valor){
        try{
            return new BigDecimal(valor.trim());
        }catch (Exception ex){
            return null;
        }
    }
    public static Long convertToLong(String valor){
        try{
            return new Long(valor.trim());
        }catch (Exception ex){
            return  null;
        }
    }
}
