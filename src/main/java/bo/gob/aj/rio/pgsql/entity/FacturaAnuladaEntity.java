package bo.gob.aj.rio.pgsql.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(schema = "rio", name = "facturas_anuladas")
@SequenceGenerator(name = "rio.facturas_anuladas_factura_anulada_id_seq", sequenceName = "rio.facturas_anuladas_factura_anulada_id_seq", allocationSize = 1)
@Data
public class FacturaAnuladaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rio.facturas_anuladas_factura_anulada_id_seq")
    @Column(name = "factura_anulada_id")
    private Long facturaAnuladaId;

    @Column(name = "operador_id")
    private Long operadorId;

    @Column(name = "numero_caja")
    private Integer numeroCaja;

    @Column(name = "nombre_completo_jugador")
    private String nombreCompletoJugador;

    @Column(name = "documento_identidad")
    private String documentoIdentidad;

    @Column(name = "lugar_expedicion")
    private String lugarExpedicion;

    @Column(name = "numero_factura_anulada")
    private String numeroFacturaAnulada;

    @Column(name = "numero_autorizacion")
    private Long numeroAutorizacion;

    @Column(name = "fecha_factura_anulada")
    private Timestamp fechaFacturaAnulada;

    @Column(name = "monto_total_factura_anulada", precision = 18, scale = 2)
    private BigDecimal montoTotalFacturaAnulada;

    @Column(name = "numero_ticket")
    private Integer numeroTicket;

    @Column(name = "monto_total_ticket", precision = 18, scale = 2)
    private BigDecimal montoTotalTicket;

    @Column(name = "fecha_factura_nueva")
    private Timestamp fechaFacturaNueva;

    @Column(name = "numero_factura_nueva")
    private String numeroFacturaNueva;

    @Column(name = "monto_total_factura_nueva", precision = 18, scale = 2)
    private BigDecimal montoTotalFacturaNueva;

    @Column(name = "fecha_archivo")
    private Timestamp fechaArchivo;

    @Column(name = "fecha_registro")
    private Timestamp fechaRegistro;

    @Column(name = "estado_id")
    private Integer estadoId;
}
