const baseURL = 'http://192.168.3.117:8080/';

export const GET_LIST_CAJAS_BY_SALON_ID = baseURL + 'EstacionCaja/findBySalonIdForGrid/';
export const POST_ADD_CAJA = baseURL + 'EstacionCaja/save';
export const FIND_CAJA_BY_ESTACION_CAJA_ID = baseURL + 'EstacionCaja/findByCajaEstacionId/';
export const POST_UPDATE_CAJA = baseURL + 'EstacionCaja/update';
export const POST_ENABLE_CAJA = baseURL + 'EstacionCaja/CambiarEstado/';
export const POST_DELTE_CAJA = baseURL + 'EstacionCaja/delete/';
export const POST_ENABLE_LIST_CAJAS = baseURL + 'EstacionCaja/cambiarEstadoList/1335';
export const GET_SHOW_DESCRIPTION = baseURL + 'EstacionCaja/observacion/';

export const GET_MACHINES_FOR_COLECTOR_BY_SALON_ID = baseURL + 'Maquina/findBySalonIdAnColectorIdForGrid/';