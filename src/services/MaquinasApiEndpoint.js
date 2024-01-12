const baseURL = 'http://192.168.3.117:8080/';

export const GET_LIST_MAQUINAS_BY_SALON_ID = baseURL + 'Maquina/findBySalonIdForGrid/';
export const GET_LIST_COLECTORES_BY_SALON_ID = baseURL + 'ColectoresDatos/findBySalonIdForGrid/';
export const POST_ADD_MAQUINA = baseURL + 'Maquina/save';
export const FIND_MAQUINA_BY_ID = baseURL + 'Maquina/findById/';
export const POST_UPDATE_MAQUINA = baseURL + 'Maquina/update';
export const POST_ENABLE_MAQUINA = baseURL + 'Maquina/cambiarEstado/';
export const POST_DELETE_MAQUINA = baseURL + 'Maquina/delete/';
export const POST_ENABLE_LIST_MAQUINAS = baseURL + 'Maquina/cambiarEstadoList/1335';
export const GET_SHOW_DESCRIPTION = baseURL + 'Maquina/observacion/';