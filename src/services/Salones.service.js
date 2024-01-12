import Api from './Api'

export default {
  fetchAllSalones (auth) {
    return Api().get('Salones/findForGrid', auth)
  }
}