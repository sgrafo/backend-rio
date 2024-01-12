<template>
  <v-container fluid>
    <v-card class="mt-5">
      <v-card-title>
        <!-- back button -->
        <v-btn small color="blue lighten-5" @click.prevent="returnBack">
          <v-icon left>arrow_back</v-icon>
          Atras&nbsp;&nbsp;
        </v-btn>
        <!-- back button -->
        <h3 class="indigo--text">Máquinas, para el Salón: &nbsp;</h3>
        <v-badge color="red">
          <v-alert
                  :value="true"
                  type="info"
                  color="#1ABC9C"
                  style="font-size: 15px;"
                  outline>
            <strong style="font-size: 18px;">{{ nameSalon }} | {{ codeSalon }}</strong>
          </v-alert>
        </v-badge>
        <v-spacer></v-spacer>

        <v-text-field
                v-model="search"
                append-icon="search"
                label="Búsqueda por código, fabricante, número serie, modelo "
                single-line
                hide-details>
        </v-text-field>
        <v-spacer></v-spacer>
        <v-btn title="Añadir nueva máquina" fab small dark
               class="light-blue darken-4 mb-2" @click.native="nuevo()">
          <v-icon>add</v-icon>
        </v-btn>
      </v-card-title>

      <v-dialog v-model="dialog" max-width="650" persistent>
        <v-card>
          <v-card-title style="background: #2980B9; height: 0px; padding: 25px; color: white;">
            <h3>{{ tituloFormulario }}</h3>
          </v-card-title>
          <v-card-text>
            <v-form ref="form" lazy-validation>
              <v-layout row wrap>
                <v-flex xs12>
                  <v-text-field v-show="false" label="Código Máquina" v-model="maquina.codigoMaquina"
                                clearable readonly></v-text-field>
                </v-flex>
                <v-flex xs12>
                  <v-text-field label="Nombre Fabricante" v-model="maquina.nombreFabricante" clearable :rules="globalRules"
                  ></v-text-field>
                </v-flex>
                <v-flex xs12>
                  <v-text-field label="Número Serie" v-model="maquina.nroSerie" clearable :rules="globalRules"
                  ></v-text-field>
                </v-flex>
                <v-flex xs12>
                  <v-text-field label="Modelo" v-model="maquina.nroModelo" clearable :rules="globalRules"
                  ></v-text-field>
                </v-flex>
                <v-flex xs12>
                  <v-menu
                          :close-on-content-click="false"
                          v-model="dialogFechaFabri"
                          :nudge-right="40"
                          lazy
                          transition="scale-transition"
                          offset-y
                          full-width
                          max-width="290px"
                          min-width="290px">
                    <v-text-field
                            slot="activator"
                            v-model="maquina.fechaFabricacion"
                            label="Fecha Fabricación"
                            hint="Formato DD/MM/AAAA"
                            :rules="globalRules"
                            readonly
                            persistent-hint
                            prepend-icon="event"
                            clearable
                    />
                    <v-date-picker v-model="maquina.fechaFabricacion" no-title
                                   @input="dialogFechaFabri = false" locale="es-ES"></v-date-picker>
                  </v-menu>
                </v-flex>
                <v-flex xs12>
                  <v-menu
                          :close-on-content-click="false"
                          v-model="dialogFechaInicio"
                          :nudge-right="40"
                          lazy
                          transition="scale-transition"
                          offset-y
                          full-width
                          max-width="290px"
                          min-width="290px">
                    <v-text-field
                            slot="activator"
                            v-model="maquina.fechaInicio"
                            label="Fecha Inicio"
                            hint="Formato DD/MM/AAAA"
                            persistent-hint
                            :rules="globalRules"
                            readonly
                            prepend-icon="event"
                            clearable
                    />
                    <v-date-picker v-model="maquina.fechaInicio" no-title
                                   @input="dialogFechaInicio = false"
                                   locale="es-ES"></v-date-picker>
                  </v-menu>
                </v-flex>
                <v-flex xs12>
                  <v-select label="Colector Datos" :items="colectores"
                            v-model="maquina.colectorDatoId"
                            :rules="globalRules"
                            item-text="descripcion" item-value="colectorDatoId"
                  ></v-select>
                </v-flex>
              </v-layout>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn class="blue-grey lighten-4" color="black" flat @click.native="close()">Cancelar</v-btn>
            <v-btn v-bind:disabled="!isComplete" class="light-blue darken-1" color="white" flat @click.native="enviarMaquina()">Guardar</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <!-- button all done -->
      <v-btn small fab color="yellow darken-1" dark class="mb-2"
             v-show="!isArrayEmpty"
             style="position: absolute; right: 0;"
             @click.native="allDoneExecute"
             title="Cambiar estados">
        <v-icon>done_all</v-icon>
      </v-btn>
      <v-data-table
              rows-per-page-text="Filas por página"
              :pagination.sync="pagination"
              :headers="headers"
              :items="maquinas"
              :search="search"
              item-key="maquinaId"
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
          <td>{{ props.item.codigoMaquina }}</td>
          <td>{{ props.item.nombreFabricante }}</td>
          <td>{{ props.item.nroSerie }}</td>
          <td>{{ props.item.nroModelo }}</td>
          <td>{{ props.item.codigoColector }}</td>
          <td>{{ changeState(props.item.estadoId) }}</td>
          <td v-if="props.item.estadoId === 1334 || props.item.estadoId === 1338 || props.item.estadoId === 1339">
            <v-icon small
                    class="mr-2"
                    color="info"
                    v-if="props.item.estadoId !== 1338"
                    @click="enableItem(props.item)">
              done_outline
            </v-icon>
            <v-icon small
                    class="mr-2"
                    color="success"
                    @click="editItem(props.item)">
              edit
            </v-icon>
            <v-icon small
                    class="mr-2"
                    color="red"
                    @click="deleteItem(props.item)">
              delete
            </v-icon>
          </td>
          <td v-else>&nbsp;</td>
          <!-- description -->
          <td class="text-xs-center" v-if="props.item.estadoId === 1338">
            <v-icon
                    title="Ver observación"
                    class="mr-2"
                    color="indigo"
                    @click="showDescription(props.item)">
              visibility
            </v-icon>
          </td>
          <td v-else>&nbsp;</td>
          <!-- select -->
          <td v-if="props.item.estadoId === 1334 || props.item.estadoId === 1339">
            <v-checkbox
                    :value="props.item.maquinaId"
                    :active="false"
                    color="orange darken-3"
                    v-model="seleccionado"
                    @click.native="selectCheck"
            ></v-checkbox>
          </td>
          <td v-else>&nbsp;</td>
        </template>
        <template slot="pageText" slot-scope="props">
          Máquinas {{ props.pageStart }} - {{ props.pageStop }} de {{ props.itemsLength }}
        </template>
        <!-- show message from no data available or no match results -->
        <v-alert slot="no-results" :value="true" outline color="info" icon="warning">
          La búsqueda para <strong>{{ search }} </strong> no tiene resultados.
        </v-alert>
        <!-- using slot no-data -->
        <template slot="no-data">
          <v-alert outline :value="true" color="info" icon="warning">
            Lo sentimos, no hay nada que mostrar aquí
          </v-alert>
        </template>
      </v-data-table>
      <!-- define a new custom notification bar -->
      <v-snackbar
              color="success"
              v-model="snackbar"
              :bottom="y === 'bottom'"
              :left="x === 'left'"
              :multi-line="mode === 'multi-line'"
              :right="x === 'right'"
              :timeout="timeout"
              :top="y === 'top'"
              :vertical="mode === 'vertical'">
        {{ text }}
        <v-btn
                color="white"
                flat
                @click="snackbar = false">
          Cerrar
        </v-btn>
      </v-snackbar>
    </v-card>
  </v-container>
</template>

<script>
  import axios from 'axios';
  import * as api from '../services/MaquinasApiEndpoint';
  import * as get_api from '../services/ColectoresApiEndpoint';

  export default {
    data() {
      return {
        globalRules: [
          (v) => !!v || 'El campo es obligatorio.'
        ],
        seleccionado: [],
        dialog: false,
        maquinas: [],
        texto: null,
        search: '',
        tituloFormulario: '',
        colectores: [],
        dialogFechaInicio: false,
        dialogFechaFabri: false,
        fechaInicio: null,
        editedIndex: -1,
        maquinaId: null,
        headers: [
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
          {text: 'Estado', value: 'estadoId'},
          {text: 'Operaciones', value: '', sortable: false},
          {text: "Observación", value: "", sortable: false},
          {text: 'Solicitar', value: '', sortable: false}
        ],
        maquina: {
          estadoId: null,
          codigoMaquina: null,
          nombreFabricante: null,
          nroSerie: null,
          nroModelo: null,
          fechaInicio: null,
          fechaFabricacion: null,
          salonId: null,
          colectorDatoId: null,
          maquinaSalonesId: null,
          codigoColector: null
        },
        defaultItem: {
          estadoId: null,
          codigoMaquina: null,
          nombreFabricante: null,
          nroSerie: null,
          nroModelo: null,
          fechaInicio: null,
          fechaFabricacion: null,
          salonId: null,
          colectorDatoId: null,
          maquinaSalonesId: null,
          codigoColector: null
        },
        // small notification
        snackbar: false,
        y: 'top',
        x: null,
        mode: '',
        timeout: 4000,
        text: '',
        // default number pagination
        pagination: {
          rowsPerPage: 10
        },
        objetoEncontrado: {},
        codeSalon: null,
        nameSalon: null
      }
    },
    computed: {
      isUserAuthenticated() {
        return this.$store.getters.isUserAuthenticated
      },
      userAuthenticatedToken() {
        return this.$store.getters.userAuthenticatedToken
      },
      isArrayEmpty() {
        return this.seleccionado.length === 0;
      },
      isComplete() {
        return (this.maquina.nombreFabricante &&
          this.maquina.nroSerie &&
          this.maquina.colectorDatoId &&
          this.maquina.nroModelo);
      }
    },
    watch: {
      dialog(val) {
        val || this.close();
        this.$refs.form.reset();
      }
    },
    mounted() {
      this.fetchAllMaquinasBySalonId();
      this.loadCodeSalon();
    },
    methods: {
      loadCodeSalon() {
        const auth = {
          headers: {Authorization: this.userAuthenticatedToken}
        };
        axios.get(get_api.GET_CODE_SALON_BY_SALON_ID + this.$route.params.idSalon, auth).then((res) => {
          this.codeSalon = res.data.codigoSalon;
          this.nameSalon = res.data.nombre.toUpperCase();
        });
      },
      showDescription(props) {
        const auth = {
          headers: {Authorization: this.userAuthenticatedToken}
        };
        axios.get(api.GET_SHOW_DESCRIPTION + props.maquinaId + '/' + props.salonId, auth)
          .then((res) => {
            this.$swal({
              title: '<strong>Descripción de la <u style="color: #880E4F;">Observación</u></strong>',
              type: 'info',
              html:
                'El motivo de <b style="color: #E91E63;">rechazo</b> de la solicitud fue la siguiente 👇, ' + '<br />' +
                '<p style="text-align: center; font-size: 1.5em; color: #3E2723; padding-top: 11px;">' + res.data.descripcion + '</p>',
              showCloseButton: true,
              focusConfirm: false,
              confirmButtonText: 'ACEPTAR'
            });
          })
          .catch(error => {
            console.log(error.message);
          });
      },
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
      returnBack() {
        window.history.back();
      },
      allDoneExecute() {
        this.$swal({
          title: '¿Estás seguro de solicitar?',
          type: 'question',
          showCancelButton: true,
          confirmButtonColor: '#1ABC9C',
          cancelButtonColor: '#3498DB',
          reverseButtons: true,
          confirmButtonText: 'Solicitar',
          cancelButtonText: 'Cancelar'
        }).then((result) => {
          if (result.value) {
            const arrayLength = this.seleccionado.length;
            let newArray = [];
            for (let i = 0; i < arrayLength; i++) {
              let obj = {
                maquinaId: this.seleccionado[i],
                salonId: parseInt(this.$route.params.idSalon, 10)
              };
              newArray.push(obj);
            }
            const auth = {
              headers: {Authorization: this.userAuthenticatedToken}
            };
            axios.post(api.POST_ENABLE_LIST_MAQUINAS, newArray, auth)
              .then(() => {
                this.seleccionado = [];
                this.fetchAllMaquinasBySalonId();
              })
              .catch(error => {
                console.log(error.message);
              });
          } else {
            console.log('No han sido solcitados.');
          }
        });
      },
      selectCheck() {
        console.log(this.seleccionado)
      },
      fetchAllMaquinasBySalonId() {
        const auth = {
          headers: {Authorization: this.userAuthenticatedToken},
          salon: this.$route.params.idSalon
        };
        // initialize array values from machines and collectors
        axios.get(api.GET_LIST_MAQUINAS_BY_SALON_ID + `${auth.salon}`, auth).then((res) => {
          if (res.data) {
            this.maquinas = res.data;
          } else {
            this.maquinas = [];
          }
        });

        axios.get(api.GET_LIST_COLECTORES_BY_SALON_ID + `${auth.salon}`, auth).then((res) => {
          if (res.data) {
            this.colectores = res.data;
          } else {
            this.colectores = [];
          }
        });
      },
      nuevo() {
        this.tituloFormulario = 'Registro Máquina';
        this.desplegarDialog();
      },
      editItem(item) {
        this.editedIndex = this.maquinas.indexOf(item);
        this.maquina = Object.assign({}, item);
        this.desplegarDialog();
        const auth = {
          headers: {Authorization: this.userAuthenticatedToken}
        };
        axios.get(api.FIND_MAQUINA_BY_ID + `${item.maquinaId}`, auth).then((res) => {
          this.maquinaId = res.data.maquinaId;
          this.maquina = res.data;
          this.objetoEncontrado = res.data;
        })
      },
      deleteItem(item) {
        this.$swal({
          title: '¿Estás seguro de borrar?',
          text: '¡No podrás revertir esto!',
          type: 'warning',
          showCancelButton: true,
          confirmButtonColor: '#00ACC1',
          cancelButtonColor: '#95A5A6',
          reverseButtons: true,
          confirmButtonText: 'Borrar',
          cancelButtonText: 'Cancelar'
        }).then((result) => {
          if (result.value) {
            const auth = {
              headers: {Authorization: this.userAuthenticatedToken}
            };
            axios.post(api.POST_DELETE_MAQUINA + `${item.maquinaId}`, {}, auth)
              .then(() => {
                this.$swal(
                  'Eliminado',
                  'El registro ha sido eliminado',
                  'success'
                );
                this.fetchAllMaquinasBySalonId();
              })
              .catch(error => {
                console.log(error.message);
              });
          } else {
            console.log('No ha sido eliminado.')
          }
        });
      },
      desplegarDialog() {
        this.dialog = !this.dialog;
      },
      close() {
        this.dialog = false
        setTimeout(() => {
          this.maquina = Object.assign({}, this.defaultItem);
          this.editedIndex = -1
        }, 300);
      },
      enviarMaquina() {
        const auth = {
          headers: {Authorization: this.userAuthenticatedToken}
        };

        if (this.editedIndex === -1) {
          const newItem = {
            estadoId: 1003,
            salonId: parseInt(this.$route.params.idSalon),
            nombreFabricante: this.maquina.nombreFabricante.toUpperCase(),
            nroSerie: this.maquina.nroSerie.toUpperCase(),
            nroModelo: this.maquina.nroModelo.toUpperCase(),
            fechaInicio: this.maquina.fechaInicio,
            fechaFabricacion: this.maquina.fechaFabricacion,
            colectorDatoId: this.maquina.colectorDatoId
          };
          // insert
          axios.post(api.POST_ADD_MAQUINA, newItem, auth).then(() => {
            this.fetchAllMaquinasBySalonId();
            this.text = '¡Se añadió un registro correctamente!';
            this.snackbar = true;
          }).catch(error => {
            console.log(error.message)
          });
        } else {
          const updateItem = {
            maquinaId: this.maquinaId,
            nombreFabricante: this.maquina.nombreFabricante.toUpperCase(),
            nroSerie: this.maquina.nroSerie.toUpperCase(),
            nroModelo: this.maquina.nroModelo.toUpperCase(),
            fechaInicio: this.maquina.fechaInicio,
            fechaFabricacion: this.maquina.fechaFabricacion,
            colectorDatoId: this.maquina.colectorDatoId,
            maquinaSalonesId: this.maquina.maquinaSalonesId,
            estadoId: this.maquina.estadoId
          };

          if (this.objetoEncontrado.estadoCatalogoId === 1338) {
            updateItem.estadoCatalogoId = 1339;
          }

          // update
          axios.post(api.POST_UPDATE_MAQUINA, updateItem, auth).then(() => {
            this.fetchAllMaquinasBySalonId();
            this.text = '¡Se actualizó correctamente!';
            this.snackbar = true;
          }).catch(error => {
            console.log(error.message)
          });
        }
        this.desplegarDialog();
        this.close();
      },
      enableItem(item) {
        this.$swal({
          title: '¿Estás seguro, solicitar?',
          type: 'info',
          showCancelButton: true,
          confirmButtonColor: '#00ACC1',
          cancelButtonColor: '#95A5A6',
          reverseButtons: true,
          confirmButtonText: 'Solicitar',
          cancelButtonText: 'Cancelar'
        }).then((result) => {
          if (result.value) {
            const auth = {
              headers: {Authorization: this.userAuthenticatedToken}
            };
            const url = api.POST_ENABLE_MAQUINA + `${item.maquinaId}/1335`;
            axios.post(url, {}, auth)
              .then(() => {
                if (result.value) {
                  this.$swal({
                    title: '¡Solicitud!',
                    text: 'La caja ha sido solicitado.',
                    type: 'success',
                    confirmButtonText: 'Aceptar'
                  });
                }
                this.fetchAllMaquinasBySalonId();
              })
              .catch(error => {
                console.log(error.message);
              });
          } else {
            console.log('No ha sido solicitado.')
          }
        });
      }
    }
  }
</script>

<style scoped>
  .v-alert.v-alert--outline {
    border: 1px solid transparent !important;
  }
</style>
