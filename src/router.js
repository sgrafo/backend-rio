import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import Signin from './views/Signin'
// import Salones from './views/Salones'
import SalonesExpand from './views/SalonesExpand'

import ColectorList from './views/ColectorList'
import CajasList from './views/CajasList'
import Maquinas from './views/Maquinas'
import ViewProgressiveList from './views/ViewProgressiveList'


Vue.use(Router);

const router = new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/signin',
      name: 'Signin',
      component: Signin
    },
    {
      path: '/salones',
      // name: 'Salones',
      // component: Salones
      name: 'SalonesExpand',
      component: SalonesExpand
    },
    {
      path: '/colectores/:idSalon',
      name: 'ColectorList',
      component: ColectorList
    },
    {
      path: '/cajas/:idSalon',
      name: 'CajasList',
      component: CajasList
    },
    {
      path: '/maquinas/:idSalon',
      name: 'maquinas',
      component: Maquinas
    },
    {
      path: '/progresivos/:idSalon',
      name: 'ViewProgressiveList',
      component: ViewProgressiveList
    }
  ],
  mode: 'history'
});

export default router
