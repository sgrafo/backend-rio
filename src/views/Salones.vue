<template>
  <v-container v-show="isUserAuthenticated">
    <v-card class="mt-5">
      <Menu/>
    </v-card>
    <br>
    <v-card>
      <v-card-title>
        <h3 class="pink--text">Listado de Salones</h3>
        <v-spacer></v-spacer>
        <v-text-field
                v-model="search"
                append-icon="search"
                label="Búsqueda por ciudad, código de salón"
                single-line
                hide-details
        ></v-text-field>
      </v-card-title>

      <v-data-table
              rows-per-page-text="Filas por página"
              :pagination.sync="pagination"
              :headers="headers"
              :items="salones"
              :search="search"
              item-key="codigoSalon"
              class="elevation-1">
        <!-- custom headers -->
        <template slot="headers" slot-scope="props">
          <tr>
            <td class="pink--text"
                v-for="(header, j) in props.headers"
                :key="j">
              {{ header.text.toUpperCase() }}
            </td>
          </tr>
        </template>
        <!-- end custom headers -->
        <template slot="items" slot-scope="props">
          <td>{{ props.index + 1 }}</td>
          <td>{{ props.item.codigoSalon }}</td>
          <td>{{ props.item.nombre }}</td>
          <td>{{ props.item.descripcion }}</td>
          <td>{{ props.item.direccion }}</td>
          <td class="text-xs-right">
            <!-- colector event-->
            <router-link :to="{name: 'ColectorList', params: {idSalon: props.item.salonId}}"
                         style="font-size: 10px !important; text-decoration: none !important;">
              <v-tooltip bottom>
                <v-icon
                        class="mr-3"
                        slot="activator"
                        center
                        color="pink"
                        dark>
                  party_mode
                </v-icon>
                <span>Colectores</span>
              </v-tooltip>
            </router-link>
            <!-- machine event-->
            <router-link :to="{name: 'maquinas', params: {idSalon: props.item.salonId }}"
                         style="font-size: 10px !important; text-decoration: none !important;">
              <v-tooltip bottom>
                <v-icon
                        class="mr-3"
                        slot="activator"
                        center
                        color="brown"
                        dark>
                  screen_share
                </v-icon>
                <span>Máquinas</span>
              </v-tooltip>
            </router-link>
            <!-- box event-->
            <router-link :to="{name: 'CajasList', params: {idSalon: props.item.salonId}}"
                         style="font-size: 10px !important; text-decoration: none !important;">
              <v-tooltip bottom>
                <v-icon
                        class="mr-3"
                        slot="activator"
                        center
                        color="indigo"
                        dark>
                  call_to_action
                </v-icon>
                <span>Cajas</span>
              </v-tooltip>
            </router-link>
          </td>
        </template>
        <template slot="pageText" slot-scope="props">
          Salones {{ props.pageStart }} - {{ props.pageStop }} de {{ props.itemsLength }}
        </template>
        <v-alert slot="no-results" :value="true" outline color="info" icon="warning">
          La búsqueda para <strong>"{{ search }}"</strong> no tiene resultados.
        </v-alert>
        <!-- using slot no-data -->
        <template slot="no-data">
          <v-alert outline :value="true" color="info" icon="warning">
            Lo sentimos, no hay nada que mostrar aquí
          </v-alert>
        </template>
      </v-data-table>

    </v-card>
  </v-container>
</template>
<script>
  import SalonService from '../services/Salones.service'
  import Menu from './Menu'

  export default {

    data() {
      return {
        pagination: {
          rowsPerPage: 10
        },
        // user credentials
        username: null,
        salones: [],
        // test
        dialog: false,
        form: {
          parent_id: []
        },

        search: '',
        selected: [],
        headers: [
          {
            text: '',
            align: 'left',
            sortable: false,
            value: ''
          },
          {
            text: 'Código Salón',
            align: 'left',
            value: 'codigoSalon'
          },
          {text: 'Nombre Salón', value: 'nombre'},
          {text: 'Ciudad Salón', value: 'descripcion'},
          {text: 'Dirección Salón', value: 'direccion'},
          {text: 'Opciones', value: '', sortable: false}

        ]
      }
    },
    computed: {
      error() {
        return this.$store.getters.getError
      },
      processing() {
        return this.$store.getters.getProcessing
      },
      isUserAuthenticated() {
        return this.$store.getters.isUserAuthenticated
      },
      userAuthenticatedToken() {
        return this.$store.getters.userAuthenticatedToken
      }
    },
    mounted() {
      if (this.userAuthenticatedToken) {
        this.fetchAllSalones();
      }
    },
    watch: {
      isUserAuthenticated(val) {
        if (val === true) {
          this.$router.push("/salones");
        }
      }
    },
    methods: {
      fetchAllSalones() {
        const auth = {
          headers: {Authorization: this.userAuthenticatedToken}
        };

        SalonService.fetchAllSalones(auth).then(res => {
          if (res.data) {
            this.salones = res.data;
          } else {
            this.salones = [];
          }
        }).catch(() => {
          // todo by winter sun
        });
      }
    },
    components: {
      Menu
    }
  }
</script>
