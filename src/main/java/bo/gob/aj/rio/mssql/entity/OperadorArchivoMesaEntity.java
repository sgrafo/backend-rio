package bo.gob.aj.rio.mssql.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(schema = "[dbo]", name = "[juegosazar.operadoresarchivosmesas]")
@Data
public class OperadorArchivoMesaEntity {

    @Id
    @Column(name = "archivomesaid")
    private Integer archivoMesaId;

    @Column(name = "operadorid")
    private Integer operadorId;

    @Column(name = "archivo")
    private byte[] archivo;

    @Column(name = "nombrearchivo")
    private String nombreArchivo;

    @Column(name = "fechahorasubida")
    private Timestamp fechaHoraSubida;

    @Column(name = "usuarioid")
    private Integer usuarioId;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "estado")
    private String estado;
}
