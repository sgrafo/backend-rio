package bo.gob.aj.rio.pgsql.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(schema = "rio", name = "operadores")
@SequenceGenerator(name = "rio.operadores_operador_id_seq", sequenceName = "rio.operadores_operador_id_seq", allocationSize = 1)
@Data
public class OperadorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rio.operadores_operador_id_seq")
    @Column(name = "operador_id")
    private Long operadorId;

    @Column(name = "nombre_empresa")
    private String nombreEmpresa;

    @Column(name = "nombre_salon")
    private String nombreSalon;

    @Column(name = "fecha_registro")
    private Timestamp fechaRegistro;

    @Column(name = "estado_id")
    private Integer estadoId;
}
