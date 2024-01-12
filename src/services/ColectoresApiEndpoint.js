const baseURL = 'http://192.168.3.117:8080/';
// const baseURL = 'http://172.16.30.13:8080/';
export const GET_LIST_COLECTORES_BY_SALON_ID = baseURL + 'ColectoresDatos/findBySalonIdForGrid/';
export const POST_ADD_COLECTOR = baseURL + 'ColectoresDatos/save';
export const FIND_COLECTOR_BY_ID = baseURL + 'ColectoresDatos/findByColectorId/';
export const POST_UPDATE_COLECTOR = baseURL + 'ColectoresDatos/update';
export const POST_ENABLE_COLECTOR = baseURL + 'ColectoresDatos/cambiarEstado/';
export const POST_ENABLE_LIST_COLECTOR = baseURL + 'ColectoresDatos/cambiarEstadoList/1335';
export const POST_DELETE_COLECTOR = baseURL + 'ColectoresDatos/delete/';
export const GET_SHOW_DESCRIPTION = baseURL + 'ColectoresDatos/observacion/';

export const GET_SHOW_DATA_USER = baseURL + 'Operadores/find';
export const GET_CODE_SALON_BY_SALON_ID = baseURL + 'Salones/findBySalonId/';
export const GET_CHANGE_PASSWORD_USER = baseURL + 'Operadores/cambiarPassword/';