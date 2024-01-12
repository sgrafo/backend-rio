<template>
  <v-container fluid v-show="isUserAuthenticated">
    <v-card class="mt-5">
      <v-card-title>
        <!-- back button -->
        <v-btn small color="blue lighten-5" @click.prevent="returnBack">
          <v-icon left>arrow_back</v-icon>
          Atras&nbsp;&nbsp;
        </v-btn>
        &nbsp;&nbsp;
        <!-- back button -->
        <h3 class="indigo--text">Cajas, para el Salón: &nbsp;</h3>
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
               title="Cambiar estados">
          <v-icon>done_all</v-icon>
        </v-btn>
        <!-- make dialog -->
        <v-dialog v-model="dialog" max-width="500px">
          <v-btn small fab slot="activator" dark class="mb-2 light-blue darken-4" title="Añadir una nueva Caja">
            <v-icon>add</v-icon>
          </v-btn>
          <v-card>
            <v-card-title style="background: #2980B9; height: 0px; padding: 25px; color: white;">
              <h2>{{ formTitle }}</h2>

            </v-card-title>
            <v-card-text>
              <v-container grid-list-md>
                <v-form ref="form">
                  <v-layout wrap>
                    <v-flex>
                      <v-textarea
                              solo
                              required
                              counter="25"
                              :rules="[
              () => !!editedItem.descripcionUbicacion || 'El campo es requerido',
              () => !!editedItem.descripcionUbicacion && editedItem.descripcionUbicacion.length <= 25 || 'Este campo solo puede tener 25 caracteres'
            ]"
                              name="input-7-4"
                              label="Descripción Ubicación"
                              v-model="editedItem.descripcionUbicacion"
                      ></v-textarea>
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

      <v-data-table
              rows-per-page-text="Filas por página"
              :pagination.sync="pagination"
              :headers="headers"
              :items="cajas"
              :search="search"
              item-key="estacionCajaId"
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
          <td>{{ props.item.numeroEstacionCaja }}</td>
          <td>{{ props.item.descripcionUbicacion }}</td>
          <td>{{ state(props.item.estadoId) }}</td>
          <td v-if="props.item.estadoId === 1334 || props.item.estadoId === 1338 || props.item.estadoId === 1339">
            <v-icon
                    small
                    v-if="props.item.estadoId !== 1338"
                    class="mr-2"
                    color="info"
                    @click="enableItem(props.item)">
              done_outline
            </v-icon>

            <v-icon
                    small
                    class="mr-2"
                    color="success"
                    @click="editItem(props.item)">
              edit
            </v-icon>
            <v-icon
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
                    :value="props.item.estacionCajaId"
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
  </v-container>
</template>

<script>

  import axios from 'axios';
  import * as api from '../services/CajasApiEndpoint';
  import * as get_api from '../services/ColectoresApiEndpoint';

  export default {
    data() {
      return {
        seleccionado: [],
        pagination: {
          rowsPerPage: 10
        },
        // user credentials
        username: null,
        cajas: [],
        // test
        dialog: false,
        editedIndex: -1,
        defaultItem: {
          estadoId: '',
          salonId: '',
          numeroEstacionCaja: '',
          descripcionUbicacion: ''
        },
        editedItem: {
          estacionCajaId: '',
          estadoId: '',
          salonId: '',
          numeroEstacionCaja: '',
          descripcionUbicacion: ''
        },
        haEcontrado: false,
        estacionCajaId: null,

        search: '',
        selected: [],
        headers: [
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
      formTitle() {
        return this.editedIndex === -1 ? 'Añadir Caja' : 'Editar Caja'
      },
      isArrayEmpty() {
        return this.seleccionado.length === 0;
      },
      isComplete() {
        return (this.editedItem.descripcionUbicacion);
      }
    },
    watch: {
      dialog(val) {
        val || this.close();
        this.$refs.form.reset();
      }
    },
    mounted() {
      this.fetchAllCajas();
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
        axios.get(api.GET_SHOW_DESCRIPTION + props.estacionCajaId + '/' + props.salonId, auth)
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
                estacionCajaId: this.seleccionado[i]
              };
              newArray.push(obj);
            }
            const auth = {
              headers: {Authorization: this.userAuthenticatedToken}
            };
            axios.post(api.POST_ENABLE_LIST_CAJAS, newArray, auth)
              .then(() => {
                this.seleccionado = [];
                this.fetchAllCajas();
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
      fetchAllCajas() {
        const auth = {
          headers: {Authorization: this.userAuthenticatedToken},
          algo: this.$route.params.idSalon
        };
        const url = api.GET_LIST_CAJAS_BY_SALON_ID + `${auth.algo}`;
        axios.get(url, auth).then((res) => {
          if (res.data) {
            this.cajas = res.data;
          } else {
            this.cajas = [];
          }
        });
      },
      editItem(item) {
        this.editedIndex = this.cajas.indexOf(item);
        this.editedItem = Object.assign({}, item);
        this.dialog = true;

        const auth = {
          headers: {Authorization: this.userAuthenticatedToken}
        };

        const url = api.FIND_CAJA_BY_ESTACION_CAJA_ID + `${this.editedItem.estacionCajaId}`;
        axios.get(url, auth).then((res) => {
          this.haEcontrado = true;
          this.estacionCajaId = res.data.estacionCajaId;
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
            const url = api.POST_ENABLE_CAJA + `${item.estacionCajaId}/1335`;
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
                this.fetchAllCajas();
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
          if (result.value) {
            const auth = {
              headers: {Authorization: this.userAuthenticatedToken}
            };
            axios.post(api.POST_DELTE_CAJA + `${item.estacionCajaId}`, {}, auth)
              .then(() => {
                this.$swal(
                  'Eliminado',
                  'El registro ha sido eliminado',
                  'success'
                );
                this.fetchAllCajas();
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
            estadoId: 1000,
            salonId: parseInt(this.$route.params.idSalon),
            descripcionUbicacion: this.editedItem.descripcionUbicacion
          };
          axios.post(api.POST_ADD_CAJA, newItem, auth).then(() => {
            this.fetchAllCajas();
            this.text = '¡Se añadió un registro correctamente!';
            this.snackbar = true;
          }).catch(error => {
            console.log(error.message)
          });
        } else {
          const updateItem = {
            estacionCajaId: this.estacionCajaId,
            numeroEstacionCaja: this.editedItem.numeroEstacionCaja,
            descripcionUbicacion: this.editedItem.descripcionUbicacion
          };

          if (this.objetoEncontrado.estadoCatalogoId === 1338) {
            updateItem.estadoCatalogoId = 1339;
          }

          if (this.haEcontrado) {
            axios.post(api.POST_UPDATE_CAJA, updateItem, auth).then(() => {
              this.fetchAllCajas();
              this.text = '¡Se actualizó correctamente!';
              this.snackbar = true;
            }).catch(error => {
              console.log(error.message)
            });
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