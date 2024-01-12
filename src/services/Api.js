import Axios from 'axios'

export default () => {
  return Axios.create({
    // baseURL: `http://172.16.30.13:8080/`
    baseURL: `http://192.168.3.117:8080/`
  })
}