<template>
  <div>
    <!-- navigation drawer -->
    <v-navigation-drawer
            class="hidden-md-and-up"
            absolute temporary
            v-model="lateral">
      <v-list dense class="pt-0">
        <!-- all items -->
        <v-list-tile
                v-for="(item, i) in menuItems"
                v-bind:key="`menuItems${i}`">

          <!-- icon -->
          <v-list-tile-action>
            <v-icon v-html="item.icon"></v-icon>
          </v-list-tile-action>

          <!-- title -->
          <v-list-tile-content>
            <v-list-tile-title v-text="item.title"></v-list-tile-title>
          </v-list-tile-content>

        </v-list-tile>
      </v-list>
    </v-navigation-drawer>
    <!-- end navigation drawer -->

    <!-- toolbar nav -->
    <v-toolbar app dark class="light-blue darken-3 text-uppercase">

      <!-- side-icon -->
      <v-toolbar-side-icon
              class="hidden-md-and-up"
              @click.stop="lateral = !lateral"></v-toolbar-side-icon>
      <!-- side-icon -->

      <!-- user details -->
      <v-avatar color="yellow accent-2">
        <span class="indigo--text headline"><strong>{{ avatar }}</strong></span>
      </v-avatar>
      <v-alert
              :value="true"
              dark
              color="transparent"
              style="color: white !important;"
              icon="check_circle"
              outline>
                <span style="font-size: 12px;">
                    <em>NOMBRE DEL USUARIO</em> <strong>&nbsp;&nbsp;{{ userName }}</strong>
                </span>
        <br>
        <span style="font-size: 11px;">
                    <em>RAZON SOCIAL</em> <strong>&nbsp;&nbsp;{{ razonSocial }}</strong>
                </span>
      </v-alert>
      <!-- end user details -->
      <v-spacer></v-spacer>
      <!-- items with icons -->
      <v-toolbar-items class="hidden-sm-and-down">
        <v-btn flat
               v-for="(item, i) in menuItems"
               v-bind:key="`menuItems${i}`"
               v-bind:to="item.route">
          <v-icon left v-html="item.icon"></v-icon>
          {{ item.title }}
        </v-btn>
        <v-menu offset-y>

          <v-btn slot="activator" flat icon dark>
            <v-icon>view_list</v-icon>
          </v-btn>
          <v-list>
            <v-list-tile
                    v-for="(item, index) in items"
                    :key="index"
                    @click.prevent="chooseAction(item)">
              <v-list-tile-title>
                <v-icon :color="item.color">{{ item.icon }}</v-icon>&nbsp;{{ item.title }}
              </v-list-tile-title>
            </v-list-tile>
            <v-divider></v-divider>
          </v-list>
        </v-menu>
      </v-toolbar-items>
      <!-- end items with icons -->
    </v-toolbar>
    <!-- end toolbar nav -->
  </div>
</template>

<script>
  import axios from 'axios';
  import * as api from './../services/ColectoresApiEndpoint';

  export default {
    data() {
      return {
        title: "SICEL - extern🔥 | v1",
        lateral: false,
        userName: '',
        razonSocial: '',
        items: [
          {title: 'SALIR', icon: 'arrow_forward', color: 'red'},
          {title: 'CAMBIAR CONTRASEÑA', icon: 'vpn_key', color: 'blue'}
        ]
      }
    },
    mounted() {
      this.getNames();
    },
    computed: {
      isUserAuthenticated() {
        return this.$store.getters.isUserAuthenticated
      },
      userAuthenticatedToken() {
        return this.$store.getters.userAuthenticatedToken
      },
      menuItems() {
        return this.isUserAuthenticated ? [
          {
            icon: 'spa',
            title: 'salones',
            route: '/salones'
          }
        ] : [
          {
            icon: 'input',
            title: 'iniciar',
            route: '/signin'
          }
        ]
      },
      avatar() {
        return this.userName.charAt(0);
      }
    },
    methods: {
      chooseAction(action) {
        if (action.title === 'CAMBIAR CONTRASEÑA') {
          this.changePassword();
        } else {
          this.signout();
        }
      },
      changePassword() {
        if (localStorage.getItem('token')) {
          const auth = {
            headers: {Authorization: localStorage.getItem('token')}
          };
          this.$swal.mixin({
            input: 'password',
            confirmButtonText: 'Siguiente &rarr;',
            cancelButtonText: 'Cancelar',
            showCancelButton: true,
            progressSteps: ['1', '2', '3'],
            reverseButtons: true
          }).queue([
            {
              title: 'Cambio de contraseña',
              text: 'Escribe la contraseña antigua'
            },
            'Escribe la nueva contraseña',
            'Confirma la nueva contraseña'
          ]).then((result) => {
            let isEqual = false;
            if (result.value[1] === result.value[2]) {
              isEqual = true;
            }
            if (isEqual && result.value) {
              axios.get(api.GET_CHANGE_PASSWORD_USER + result.value[2] + '/' + result.value[0], auth).then(() => {
                this.$swal({
                  title: 'La contraseña se ha cambiado con éxito',
                  type: 'success',
                  confirmButtonText: 'ACEPTAR',
                  confirmButtonColor: '#00ACC1'
                });
              })
                .catch(() => {
                  this.$swal({
                    title: 'La contraseña NO se modificó',
                    text: 'Verifique la contraseña antigua y vuelva a intentar.',
                    type: 'info',
                    confirmButtonText: 'ACEPTAR',
                    confirmButtonColor: '#1cc199'
                  });
                });
            } else {
              this.$swal({
                title: 'La contraseña NO se modificó',
                text: 'Las nuevsa contraseñas no coinciden.',
                type: 'error',
                confirmButtonText: 'ACEPTAR',
                confirmButtonColor: '#1cc199'
              });
            }
          })
        }
      },
      signout() {
        this.$store.dispatch('SIGNOUT').then(() => {
          this.$router.push('/')
        });
      },
      getNames() {
        if (localStorage.getItem('token')) {
          const auth = {
            headers: {Authorization: localStorage.getItem('token')}
          };
          axios.get(api.GET_SHOW_DATA_USER, auth).then((res) => {
            this.userName = res.data.nombreCompleto;
            this.razonSocial = res.data.operadorId.razonSocial;
          });
        }
      }
    }
  }
</script>

<style scoped>
  .v-alert.v-alert--outline {
    border: 1px solid transparent !important;
  }
</style>