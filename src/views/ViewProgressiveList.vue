<template>
  <div v-show="isUserAuthenticated">
    <v-card class="mt-5">
      <v-card-title>
        <!-- back button -->
        <v-btn small color="blue lighten-5" @click.prevent="returnBack">
          <v-icon left>arrow_back</v-icon>
          Atras&nbsp;&nbsp;
        </v-btn>
        &nbsp;&nbsp;
        <!-- back button -->
        <h3 class="indigo--text">Progresivos, para el Salón: &nbsp;</h3>
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
                label="Búsqueda por número de estación, ubicación"
                single-line
                hide-details
        ></v-text-field>

        <v-spacer></v-spacer>
        <v-btn small fab color="yellow darken-1" dark class="mb-2"
               v-show="!isArrayEmpty"
               @click.native="allDoneExecute"
               title="Solicitar">
          <v-icon>done_all</v-icon>
        </v-btn>
        <!-- make dialog -->
        <v-dialog v-model="dialog" max-width="500px">
          <v-btn small fab slot="activator" dark class="mb-2 light-blue darken-4" title="Añadir un Progresivo">
            <v-icon>add</v-icon>
          </v-btn>
          <v-card>
            <v-card-title style="background: #2980B9; height: 0px; padding: 25px; color: white;">
              <h2 class="">{{ formTitle }}</h2>
            </v-card-title>
            <v-card-text>
              <v-container grid-list-md>
                <v-form ref="form">
                  <v-layout wrap>
                    <v-flex>
                      <v-text-field :rules="globalRules" v-model="editedItem.nombreFabricante" label="Nombre del Fabricante"></v-text-field>
                    </v-flex>
                  </v-layout>
                  <v-layout wrap>
                    <v-flex xs12 sm6 md6>
                      <v-text-field :rules="globalRules" v-model="editedItem.nroSerie" label="Número de Serie"></v-text-field>
                    </v-flex>
                    <v-flex>
                      <v-text-field :rules="globalRules" v-model="editedItem.nroModelo" label="Número de Modelo"></v-text-field>
                    </v-flex>
                  </v-layout>
                  <v-layout row wrap>
                    <v-flex >
                      <v-menu
                              ref="fecha1"
                              :close-on-content-click="false"
                              v-model="fecha1"
                              :nudge-right="40"
                              lazy
                              transition="scale-transition"
                              offset-y
                              full-width
                              max-width="290px"
                              min-width="290px">
                        <v-text-field
                                slot="activator"
                                v-model="dateFormatted"
                                label="Fecha Inicio"
                                hint="Formato: día/mes/año"
                                :rules="dateRules"

                                persistent-hint
                                prepend-icon="event"
                                @blur="date = parseDate(dateFormatted)"></v-text-field>
                        <v-date-picker v-model="date" no-title @input="fecha1 = false"></v-date-picker>
                      </v-menu>
                    </v-flex>

                    <v-flex >
                      <v-menu
                              ref="fecha2"
                              :close-on-content-click="false"
                              v-model="fecha2"
                              :nudge-right="40"
                              lazy
                              transition="scale-transition"
                              offset-y
                              full-width
                              max-width="290px"
                              min-width="290px">
                        <v-text-field
                                slot="activator"
                                v-model="dateFormatted2"
                                label="Fecha Fin"
                                hint="Formato: día/mes/año"
                                persistent-hint
                                :rules="dateRules"
                                prepend-icon="event"
                                @blur="date2 = parseDate(dateFormatted2)"></v-text-field>
                        <v-date-picker v-model="date2" no-title @input="fecha2 = false"></v-date-picker>
                      </v-menu>
                    </v-flex>
                  </v-layout>
                </v-form>
              </v-container>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn class="blue-grey lighten-4" color="black" flat @click.native="close">Cancelar</v-btn>
              <v-btn v-bind:disabled="!isComplete" class="light-blue darken-1" color="white" flat @click.native="save">Guardar</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <!-- end make dialog -->
      </v-card-title>
      <v-progress-linear :indeterminate="loading"></v-progress-linear>
      <v-data-table
              rows-per-page-text="Filas por página"
              :pagination.sync="pagination"
              :headers="headers"
              :items="progresivos"
              :search="search"
              item-key="progresivosId"
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
        <template slot="items" slot-scope="props">
          <td>{{ props.index + 1 }}</td>
          <td>{{ props.item.codigoProgresivo }}</td>
          <td>{{ props.item.nombreFabricante }}</td>
          <td>{{ props.item.nroSerie }}</td>
          <td>{{ props.item.nroModelo }}</td>
          <td>{{ state(props.item.estadoId) }}</td>
          <td v-if="props.item.estadoId === 1334 || props.item.estadoId === 1338 || props.item.estadoId === 1339">
            <v-icon title="Solicitar"
                    small
                    class="mr-2"
                    color="info"
                    v-if="props.item.estadoId !== 1338"
                    @click="enableItem(props.item)">
              done_outline
            </v-icon>

            <v-icon title="Editar"
                    small
                    class="mr-2"
                    color="success"
                    @click="editItem(props.item)">
              edit
            </v-icon>
            <v-icon title="Eliminar"
                    small
                    class="mr-2"
                    color="red"
                    @click="deleteItem(props.item)">
              delete
            </v-icon>
          </td>
          <td v-else>&nbsp;</td>
          <!---->
          <td v-if="props.item.estadoId === 1338">
            <v-icon
                    title="Ver observación"
                    class="mr-2"
                    color="indigo"
                    @click="showDescription(props.item)">
              visibility
            </v-icon>
          </td>
          <td v-else>&nbsp;</td>
          <!---->
          <td v-if="props.item.estadoId === 1334 || props.item.estadoId === 1339">
            <v-checkbox
                    :value="props.item.progresivosId"
                    :active="false"
                    color="orange darken-3"
                    v-model="seleccionado"
                    @click.native="selectCheck"
            ></v-checkbox>
          </td>
          <td v-else>&nbsp;</td>
        </template>
        <template slot="pageText" slot-scope="props">
          Cajas {{ props.pageStart }} - {{ props.pageStop }} de {{ props.itemsLength }}
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
  </div>
</template>

<script>

  import axios from 'axios';
  import * as api from '../services/ProgressivesApiEndpoint';
  import * as get_api from '../services/ColectoresApiEndpoint';

  export default {
    data() {
      return {
        globalRules: [
          (v) => !!v || 'El campo es obligatorio.'
        ],
        dateRules: [
          (v) => !!v || 'El campo es obligatorio.',
          (v) => (v && this.isValidDate(v)) || 'El campo debe tener formato de fecha.'
        ],
        seleccionado: [],
        pagination: {
          rowsPerPage: 10
        },
        // user credentials
        username: null,
        progresivos: [],
        // test
        dialog: false,
        editedIndex: -1,
        defaultItem: {
          estadoId: '',
          salonId: '',
          nombreFabricante: '',
          nroSerie: '',
          nroModelo: ''
          // fechaInicio: '',
          // fechaFin: ''
        },
        editedItem: {
          progresivosId: '',
          estadoId: '',
          salonId: '',
          nombreFabricante: '',
          nroSerie: '',
          nroModelo: ''
        },
        haEcontrado: false,
        progresivosId: null,
        //
        search: '',
        selected: [],
        headers: [
          {
            text: '',
            align: 'left',
            sortable: false,
            value: 'progresivosId'
          },
          {text: 'Código Progresivo', value: 'codigoProgresivo', sortable: false},
          {
            text: 'Nombre fabricante',
            align: 'left',
            sortable: false,
            value: 'nombreFabricante'
          },
          {text: 'Número serie', value: 'nroSerie', sortable: false},
          {text: 'Número modelo', value: 'nroModelo', sortable: false},
          {text: 'Estado', value: 'estadoId', sortable: false},
          {text: 'Operaciones', value: '', sortable: false},
          {text: "Observación", value: "", sortable: false},
          {text: 'Solicitar', value: '', sortable: false}
        ],
        //
        snackbar: false,
        y: 'top',
        x: null,
        mode: '',
        timeout: 4000,
        text: '',
        //
        objetoEncontrado: {},
        codeSalon: null,
        nameSalon: null,
        loading: true,
        // fecha 1
        fecha1: null,
        date: new Date().toISOString().substr(0, 10),
        dateFormatted: this.formatDate(new Date().toISOString().substr(0, 10)),
        // fecha 2
        fecha2: null,
        date2: new Date().toISOString().substr(0, 10),
        dateFormatted2: this.formatDate(new Date().toISOString().substr(0, 10))
      }
    },
    computed: {
      isUserAuthenticated() {
        return this.$store.getters.isUserAuthenticated
      },
      userAuthenticatedToken() {
        return this.$store.getters.userAuthenticatedToken
      },
      formTitle() {
        return this.editedIndex === -1 ? 'Añadir Progresivo' : 'Editar Progresivo'
      },
      isArrayEmpty() {
        return this.seleccionado.length === 0;
      },
      isComplete() {
        return (this.editedItem.nombreFabricante &&
          this.editedItem.nroModelo &&
          this.editedItem.nroSerie);
      }
    },
    watch: {
      dialog(val) {
        val || this.close();
        this.$refs.form.reset();
      },
      date() {
        this.dateFormatted = this.formatDate(this.date)
      },
      date2() {
        this.dateFormatted2 = this.formatDate(this.date2)
      }
    },
    mounted() {
      this.fetchAllProgresivos();
      this.loadCodeSalon();
    },
    methods: {
      isValidDate(dateString) {
        if (dateString) {
          let value = dateString.split('/');
          dateString = value[2] + '-' + value[1] + '-' + value[0];
          console.log(dateString)
        }
        // First check for the pattern
        const regex_date = /^\d{4}\-\d{1,2}\-\d{1,2}$/;

        if (!regex_date.test(dateString)) {
          return false;
        }

        // Parse the date parts to integers
        let parts = dateString.split("-");
        let day = parseInt(parts[2], 10);
        let month = parseInt(parts[1], 10);
        let year = parseInt(parts[0], 10);

        // Check the ranges of month and year
        if (year < 1000 || year > 3000 || month == 0 || month > 12) {
          return false;
        }

        let monthLength = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];

        // Adjust for leap years
        if (year % 400 == 0 || (year % 100 != 0 && year % 4 == 0)) {
          monthLength[1] = 29;
        }

        // Check the range of the day
        return day > 0 && day <= monthLength[month - 1];
      },
      customFormtat(date) {
        if (!date) return null;
        let splits = date.split("/");
        let reverses = splits.reverse();
        return reverses.join('-');
      },
      formatDate(date) {
        if (!date) return null;
        const [year, month, day] = date.split('-');
        return `${day}/${month}/${year}`;
      },
      parseDate(date) {
        if (!date) return null;
        // ["11", "14", "2018"]
        if (this.isValidDate(date)) {
          const [day, month, year] = date.split('/');
          return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`;
        }
      },
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
        axios.get(api.GET_SHOW_DESCRIPTION + props.progresivosId + '/' + props.salonId, auth)
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
      selectCheck() {
        console.log(this.seleccionado)
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
                progresivosId: this.seleccionado[i]
              };
              newArray.push(obj);
            }
            const auth = {
              headers: {Authorization: this.userAuthenticatedToken}
            };
            axios.post(api.POST_ENABLE_LIST_PROGRESIVO, newArray, auth)
              .then(() => {
                this.seleccionado = [];
                this.fetchAllProgresivos();
              })
              .catch(error => {
                console.log(error.message);
              });
          } else {
            console.log('No han sido solicitados.');
          }
        });
      },
      state(estadoId) {
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
      fetchAllProgresivos() {
        const auth = {
          headers: {Authorization: this.userAuthenticatedToken},
          algo: this.$route.params.idSalon
        };
        const url = api.GET_LIST_PROGRESIVOS_BY_SALON_ID + `${auth.algo}`;
        axios.get(url, auth).then((res) => {
          if (res.data) {
            this.progresivos = res.data;
          } else {
            this.progresivos = [];
          }
          this.loading = false;
        }).catch(() => {
          this.loading = false;
        });
      },
      editItem(item) {
        this.editedIndex = this.progresivos.indexOf(item);
        this.editedItem = Object.assign({}, item);
        this.dialog = true;

        const auth = {
          headers: {Authorization: this.userAuthenticatedToken}
        };

        const url = api.FIND_PROGRESIVO_BY_ID + `${this.editedItem.progresivosId}`;
        axios.get(url, auth).then((res) => {
          this.haEcontrado = true;
          this.progresivosId = res.data.progresivosId;
          this.objetoEncontrado = res.data;
        }).catch(error => {
          console.log(error.message)
        });

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
            let newArray = [];
            let obj = {
              progresivosId: item.progresivosId
            };
            newArray.push(obj);
            axios.post(api.POST_ENABLE_LIST_PROGRESIVO, newArray, auth)
              .then(() => {
                if (result.value) {
                  this.$swal({
                    title: '¡Solicitud!',
                    text: 'El progresivo ha sido solicitado.',
                    type: 'success',
                    confirmButtonText: 'Aceptar'
                  });
                }
                this.fetchAllProgresivos();
              })
              .catch(error => {
                console.log(error.message);
              });
          } else {
            console.log('No ha sido solicitado.')
          }
        });
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
          console.log(result.value)
          if (result.value) {
            const auth = {
              headers: {Authorization: this.userAuthenticatedToken}
            };
            axios.post(api.POST_DELETE_PROGRESIVO + `${item.progresivosId}`, {}, auth)
              .then(() => {
                this.$swal(
                  'Eliminado',
                  'El registro ha sido eliminado',
                  'success'
                );
                this.fetchAllProgresivos();
              })
              .catch(error => {
                console.log(error.message);
              });
          } else {
            console.log('No ha sido eliminado.')
          }
        });
      },
      close() {
        this.dialog = false;
        setTimeout(() => {
          this.editedItem = Object.assign({}, this.defaultItem);
          this.editedIndex = -1
        }, 300)
      },
      save() {
        const auth = {
          headers: {Authorization: this.userAuthenticatedToken}
        };

        if (this.editedIndex === -1) {
          const newItem = {
            salonId: parseInt(this.$route.params.idSalon),
            nombreFabricante: this.editedItem.nombreFabricante,
            nroSerie: this.editedItem.nroSerie,
            nroModelo: this.editedItem.nroModelo,
            fechaInicio: this.customFormtat(this.dateFormatted),
            fechaFin: this.customFormtat(this.dateFormatted2)
          };

          axios.post(api.POST_ADD_PROGRESIVO, newItem, auth).then(() => {
            this.fetchAllProgresivos();
            this.text = '¡Se añadió un registro correctamente!';
            this.snackbar = true;
            this.date = this.date2 = new Date().toISOString().substr(0, 10);
          }).catch(error => {
            console.log(error.message)
          });
        } else {
          const updateItem = {
            progresivosId: this.progresivosId,
            nombreFabricante: this.editedItem.nombreFabricante,
            nroSerie: this.editedItem.nroSerie,
            nroModelo: this.editedItem.nroModelo,
            fechaInicio: this.customFormtat(this.dateFormatted),
            fechaFin: this.customFormtat(this.dateFormatted2)
          };

          if (this.objetoEncontrado.estadoCatalogoId === 1338) {
            updateItem.estadoCatalogoId = 1339;
          }

          if (this.haEcontrado) {
            axios.post(api.POST_UPDATE_PROGRESIVO, updateItem, auth).then(() => {
              this.fetchAllProgresivos();
              this.text = '¡Se actualizó correctamente!';
              this.snackbar = true;
            }).catch(error => {
              console.log(error.message)
            });
          } else {
            console.log('no se que paso')
          }
        }
        this.close();
      },
      returnBack() {
        window.history.back();
      }
    }
  }
</script>
<style scoped>
  .v-alert.v-alert--outline {
    border: 1px solid transparent !important;
  }
</style>