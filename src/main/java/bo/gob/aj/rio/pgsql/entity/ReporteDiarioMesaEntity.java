package bo.gob.aj.rio.pgsql.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(schema = "rio", name = "reportes_diarios_mesas")
@SequenceGenerator(name = "rio.reportes_diarios_mesas_reporte_diario_mesa_id_seq", sequenceName = "rio.reportes_diarios_mesas_reporte_diario_mesa_id_seq", allocationSize = 1)
@Data
public class ReporteDiarioMesaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rio.reportes_diarios_mesas_reporte_diario_mesa_id_seq")
    @Column(name = "reporte_diario_mesa_id")
    private Long reporteDiarioMesaId;

    @Column(name = "operador_id")
    private Long operadorId;

    @Column(name = "numero_precinto")
    private Integer numeroPrecinto;

    @Column(name = "identificacion_mesa")
    private String identificacionMesa;

    @Column(name = "juego")
    private String juego;

    @Column(name = "apertura", precision = 18, scale = 2)
    private BigDecimal apertura;

    @Column(name = "relleno", precision = 18, scale = 2)
    private BigDecimal relleno;

    @Column(name = "devolucion", precision = 18, scale = 2)
    private BigDecimal devolucion;

    @Column(name = "cierre", precision = 18, scale = 2)
    private BigDecimal cierre;

    @Column(name = "cantidad_tickets_autorizados")
    private Integer cantidadTicketsAutorizados;

    @Column(name = "monto_tickets_autorizados", precision = 18, scale = 2)
    private BigDecimal montoTicketsAutorizados;

    @Column(name = "premios", precision = 18, scale = 2)
    private BigDecimal premios;

    @Column(name = "resultado", precision = 18, scale = 2)
    private BigDecimal resultado;

    @Column(name = "fecha_archivo")
    private Timestamp fechaArchivo;

    @Column(name = "fecha_registro")
    private Timestamp fechaRegistro;

    @Column(name = "estado_id")
    private Integer estadoId;
}
