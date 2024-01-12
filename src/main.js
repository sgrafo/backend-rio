import '@babel/polyfill'
import Vue from 'vue'
import './plugins/vuetify'
import AppInit from './AppInit'
import router from './router'
import store from './store'
import VueSweetalert2 from 'vue-sweetalert2';

Vue.use(VueSweetalert2);
Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(AppInit),
  created() {
    let vm = this;
    const token = window.localStorage.getItem('token');
    if (token) {
      let user = {
        username: token
      };
      vm.$store.dispatch('STATE_CHANGED', user)
        .then(() => {
          console.log('the state has been changed XD')
        });
    }
  }
}).$mount('#app');
