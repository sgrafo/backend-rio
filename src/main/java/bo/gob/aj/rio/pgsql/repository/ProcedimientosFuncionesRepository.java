package bo.gob.aj.rio.pgsql.repository;

import bo.gob.aj.rio.pgsql.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.swing.text.html.Option;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class ProcedimientosFuncionesRepository {

    private EntityManagerFactory emf;


    @Autowired
    public ProcedimientosFuncionesRepository(final EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    public List<Object[]> obtenerCajaVentasTodos(Long pOperadorId, Timestamp pFechaInicio, Timestamp pFechaFin){
        StoredProcedureQuery storedProcedureQuery = this.emf.createEntityManager().createStoredProcedureQuery("rio.pa_obtener_caja_venta");
        storedProcedureQuery.registerStoredProcedureParameter("_operador_id", Long.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("_fecha_inicio", Timestamp.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("_fecha_fin", Timestamp.class, ParameterMode.IN);
        storedProcedureQuery.setParameter("_operador_id", pOperadorId);
        storedProcedureQuery.setParameter("_fecha_inicio", pFechaInicio);
        storedProcedureQuery.setParameter("_fecha_fin", pFechaFin);
        storedProcedureQuery.execute();
        return  storedProcedureQuery.getResultList();
    }
    public List<Object[]> obtenerCajaPagosTodos(Long pOperadorId, Timestamp pFechaInicio, Timestamp pFechaFin){
        StoredProcedureQuery storedProcedureQuery = this.emf.createEntityManager().createStoredProcedureQuery("rio.pa_obtener_caja_pagos");
        storedProcedureQuery.registerStoredProcedureParameter("_operador_id", Long.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("_fecha_inicio", Timestamp.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("_fecha_fin", Timestamp.class, ParameterMode.IN);
        storedProcedureQuery.setParameter("_operador_id", pOperadorId);
        storedProcedureQuery.setParameter("_fecha_inicio", pFechaInicio);
        storedProcedureQuery.setParameter("_fecha_fin", pFechaFin);
        storedProcedureQuery.execute();
        List<Object[]> lstObj =  storedProcedureQuery.getResultList();
        return lstObj;
    }

    public List<Object[]> obtenerTransaccionesMesaTodos(Long pOperadorId, Timestamp pFechaInicio, Timestamp pFechaFin){
        StoredProcedureQuery storedProcedureQuery = this.emf.createEntityManager().createStoredProcedureQuery("rio.pa_obtener_transacciones_mesa");
        storedProcedureQuery.registerStoredProcedureParameter("_operador_id", Long.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("_fecha_inicio", Timestamp.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("_fecha_fin", Timestamp.class, ParameterMode.IN);
        storedProcedureQuery.setParameter("_operador_id", pOperadorId);
        storedProcedureQuery.setParameter("_fecha_inicio", pFechaInicio);
        storedProcedureQuery.setParameter("_fecha_fin", pFechaFin);
        storedProcedureQuery.execute();
        return  storedProcedureQuery.getResultList();
    }
    public List<Object[]> obtenerRepDiarioMesaTodos(Long pOperadorId, Timestamp pFechaInicio, Timestamp pFechaFin){
        StoredProcedureQuery storedProcedureQuery = this.emf.createEntityManager().createStoredProcedureQuery("rio.pa_obtener_reporte_diario_mesa");
        storedProcedureQuery.registerStoredProcedureParameter("_operador_id", Long.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("_fecha_inicio", Timestamp.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("_fecha_fin", Timestamp.class, ParameterMode.IN);
        storedProcedureQuery.setParameter("_operador_id", pOperadorId);
        storedProcedureQuery.setParameter("_fecha_inicio", pFechaInicio);
        storedProcedureQuery.setParameter("_fecha_fin", pFechaFin);
        storedProcedureQuery.execute();
        return  storedProcedureQuery.getResultList();
    }
    public  List<Object[]> obtenerFacturaAnuladasTodos(Long pOperadorId, Timestamp pFechaInicio, Timestamp pFechaFin){
        StoredProcedureQuery storedProcedureQuery = this.emf.createEntityManager().createStoredProcedureQuery("rio.pa_obtener_factura_anulada");
        storedProcedureQuery.registerStoredProcedureParameter("_operador_id", Long.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("_fecha_inicio", Timestamp.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("_fecha_fin", Timestamp.class, ParameterMode.IN);
        storedProcedureQuery.setParameter("_operador_id", pOperadorId);
        storedProcedureQuery.setParameter("_fecha_inicio", pFechaInicio);
        storedProcedureQuery.setParameter("_fecha_fin", pFechaFin);
        storedProcedureQuery.execute();
        return  storedProcedureQuery.getResultList();
    }
}
