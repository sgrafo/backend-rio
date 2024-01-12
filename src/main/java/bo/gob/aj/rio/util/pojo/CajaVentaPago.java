package bo.gob.aj.rio.util.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class CajaVentaPago {

    private String nombreCompletoJugador;
    private String documentoIdentidad;
    private Timestamp fechaArchivo;
    private BigDecimal montoTicketComprado;
    private BigDecimal montoTotalPagado;
    private BigDecimal diferencia;
}
