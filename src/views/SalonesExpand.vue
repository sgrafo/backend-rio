<template>
  <div v-show="isUserAuthenticated">
    <v-card class="mt-5">
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
          <tr @click="props.expanded = !props.expanded">
            <td class="pink--text"
                v-for="(header, j) in props.headers"
                :key="j">
              {{ header.text.toUpperCase() }}
            </td>
          </tr>
        </template>
        <!-- end custom headers -->
        <template slot="items" slot-scope="props">
          <tr @click="getAllDataFromSalonById(props)">
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
                          color="pink darken-4"
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
                          color="teal"
                          dark>
                    call_to_action
                  </v-icon>
                  <span>Cajas</span>
                </v-tooltip>
              </router-link>
              <!-- progressive event-->
              <router-link :to="{name: 'ViewProgressiveList', params: {idSalon: props.item.salonId}}"
                           style="font-size: 10px !important; text-decoration: none !important;">
                <v-tooltip bottom>
                  <v-icon
                          class="mr-3"
                          slot="activator"
                          center
                          color="blue-grey darken-4"
                          dark>
                    markunread_mailbox
                  </v-icon>
                  <span>Progresivos</span>
                </v-tooltip>
              </router-link>
            </td>
          </tr>
        </template>
        <!-- custom -->
        <template slot="pageText" slot-scope="props">
          Salones {{ props.pageStart }} - {{ props.pageStop }} de {{ props.itemsLength }}
        </template>
        <!-- alert -->
        <v-alert slot="no-results" :value="true" outline color="info" icon="warning">
          La búsqueda para <strong>"{{ search }}"</strong> no tiene resultados.
        </v-alert>
        <!-- using slot no-data -->
        <template slot="no-data">
          <v-alert outline :value="true" color="info" icon="warning">
            Lo sentimos, no hay nada que mostrar aquí
          </v-alert>
        </template>
        <!-- expandable demo -->
        <template slot="expand" scope="props">
          <v-card class="elevation-10">
            <v-card-text>
              <v-data-table :headers="headersColectores"
                            :items="colectores"
                            item-key="colectorDatoId"
                            hide-actions
                            class="elevation-10">
                <!-- custom headers for colectores -->
                <template slot="headers" slot-scope="props">
                  <tr>
                    <td class="pink darken-4 white--text"
                        v-for="(header, j) in props.headers"
                        :key="j">
                      {{ header.text.toUpperCase() }}
                    </td>
                  </tr>
                </template>
                <!-- main data -->
                <template slot="items" scope="props">
                  <tr @click.prevent="fetchAllMaquinasForColectorBySalonId(props)">
                    <td>{{ props.index + 1 }}</td>
                    <td>{{ props.item.codigoColector }}</td>
                    <td>{{ props.item.marca }}</td>
                    <td>{{ props.item.nroModelo }}</td>
                    <td>{{ props.item.nroSerie }}</td>
                    <td>{{ changeState(props.item.estadoId) }}</td>
                  </tr>
                </template>
                <!-- expandable -->
                <template slot="expand" scope="props">
                  <v-card class="elevation-10">
                    <v-card-text>
                      <v-data-table :headers="headersMaquinas"
                                    :items="maquinas"
                                    item-key="maquinaId"
                                    hide-actions
                                    class="elevation-10">
                        <!-- custom headers for cajas -->
                        <template slot="headers" slot-scope="props">
                          <tr>
                            <td class="pink lighten-1 white--text"
                                v-for="(header, j) in props.headers"
                                :key="j">
                              {{ header.text.toUpperCase() }}
                            </td>
                          </tr>
                        </template>
                        <!-- main data -->
                        <template slot="items" scope="props">
                          <td>{{ props.index + 1 }}</td>
                          <td>{{ props.item.codigoMaquina }}</td>
                          <td>{{ props.item.nombreFabricante }}</td>
                          <td>{{ props.item.nroSerie }}</td>
                          <td>{{ props.item.nroModelo }}</td>
                          <td>{{ props.item.codigoColector }}</td>
                          <td>{{ changeState(props.item.estadoId) }}</td>
                        </template>
                      </v-data-table>
                    </v-card-text>
                  </v-card>
                </template>
              </v-data-table>
            </v-card-text>
          </v-card>
          <!-- data for cajas -->
          <v-card class="elevation-10">
            <v-card-text>
              <v-data-table :headers="headersCajas"
                            :items="cajas"
                            item-key="estacionCajaId"
                            hide-actions
                            class="elevation-10">
                <!-- custom headers for cajas -->
                <template slot="headers" slot-scope="props">
                  <tr>
                    <td class="teal white--text"
                        v-for="(header, j) in props.headers"
                        :key="j">
                      {{ header.text.toUpperCase() }}
                    </td>
                  </tr>
                </template>
                <!-- main data -->
                <template slot="items" scope="props">
                  <td>{{ props.index + 1 }}</td>
                  <td>{{ props.item.numeroEstacionCaja }}</td>
                  <td>{{ props.item.descripcionUbicacion }}</td>
                  <td>{{ changeState(props.item.estadoId) }}</td>
                </template>
              </v-data-table>
            </v-card-text>
          </v-card>
        </template>

      </v-data-table>
    </v-card>
  </div>
</template>
<script>
  import SalonService from '../services/Salones.service'
  import Menu from './Menu';
  import Axios from 'axios';
  import * as api from '../services/ColectoresApiEndpoint';
  import * as service from '../services/CajasApiEndpoint';

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
        ],
        //
        colectores: [],
        headersColectores: [
          {
            text: "",
            align: "left",
            sortable: false,
            value: "colectorDatoId"
          },
          {
            text: "Código Colector",
            align: "",
            sortable: false,
            value: "codigoColector"
          },
          {text: "Marca", value: "marca", sortable: false},
          {text: "Número Modelo", value: "nroModelo", sortable: false},
          {text: "Número Serie", value: "nroSerie", sortable: false},
          {text: "Estado", value: "estadoId", sortable: false},
        ],
        cajas: [],
        headersCajas: [
          {
            text: '',
            align: 'left',
            sortable: false,
            value: 'estacionCajaId'
          },
          {
            text: 'Número Estación Caja',
            align: 'left',
            sortable: false,
            value: 'numeroEstacionCaja'
          },
          {text: 'Descripción Ubicación', value: 'descripcionUbicacion', sortable: false},
          {text: 'Estado', value: 'estadoId', sortable: false}
        ],
        maquinas: [],
        headersMaquinas: [
          {
            text: '',
            align: 'left',
            sortable: false,
            value: ''
          },
          {
            text: 'Código Máquina',
            align: 'left',
            value: 'codigoMaquina'
          },
          {text: 'Nombre Fabricante', value: 'nombreFabricante'},
          {text: 'Número Serie', value: 'nroSerie'},
          {text: 'Modelo', value: 'nroModelo'},
          {text: 'Colector', value: 'codigoColector'},
          {text: 'Estado', value: 'estadoId'}
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
      changeState(estadoId) {
        let text = '';
        if (estadoId === 1334) {
          text = 'NUEVO';
        } else if (estadoId === 1335) {
          text = 'SOLICITADO';
        }
        else if (estadoId === 1336) {
          text = 'ABROBADO';
        }
        else if (estadoId === 1337) {
          text = 'INACTIVO';
        }
        else if (estadoId === 1338) {
          text = 'RECHAZADO';
        }
        else if (estadoId === 1339) {
          text = 'CORREGIDO';
        } else {
          text = 'SIN DEFINIR';
        }
        return text;
      },
      fetchAllSalones() {
        const auth = {
          headers: {Authorization: this.userAuthenticatedToken}
        };

        SalonService.fetchAllSalones(auth).then(res => {
          // console.log(res.data)
          if (res.data) {
            this.salones = res.data;
          } else {
            this.salones = [];
          }
        }).catch(() => {
          // todo by winter sun
        });
      },
      getAllDataFromSalonById(props) {
        Axios.all([this.fetchAllColectores(props.item.salonId), this.fetchAllCajas(props.item.salonId)]).then(
          Axios.spread(() => {
            props.expanded = !props.expanded
          }));
      },
      fetchAllColectores(salonID) {
        const auth = {
          headers: {Authorization: this.userAuthenticatedToken}
        };
        const url = api.GET_LIST_COLECTORES_BY_SALON_ID + salonID;
        Axios.get(url, auth)
          .then(res => {
            if (res.data) {
              this.colectores = res.data;
            } else {
              this.colectores = [];
            }
          });
      },
      fetchAllCajas(salonID) {
        const auth = {
          headers: {Authorization: this.userAuthenticatedToken},
        };
        const url = service.GET_LIST_CAJAS_BY_SALON_ID + salonID;
        Axios.get(url, auth)
          .then((res) => {
            if (res.data) {
              this.cajas = res.data;
            } else {
              this.cajas = [];
            }
          });
      },
      fetchAllMaquinasForColectorBySalonId(props) {
        const auth = {
          headers: {Authorization: this.userAuthenticatedToken},
        };
        const url = service.GET_MACHINES_FOR_COLECTOR_BY_SALON_ID + props.item.salonId + '/' + props.item.colectorDatoId;
        Axios.get(url, auth)
          .then((res) => {
            console.log(res)

            if (res.data) {
              this.maquinas = res.data;
            } else {
              this.maquinas = [];
            }
            props.expanded = !props.expanded
          });
      }
    },
    components: {
      Menu
    }
  }
</script>
