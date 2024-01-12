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
        <h3 class="indigo--text">Colectores, para el Salón</h3>
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
                label="Búsqueda por número de serie, modelo del colector"
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
          <v-btn small fab slot="activator" dark class="mb-2 light-blue darken-4"
                 title="Añadir un nuevo Salón">
            <v-icon>add</v-icon>
          </v-btn>
          <v-card>
            <v-card-title style="background: #2980B9; height: 0px; padding: 25px; color: white;">
              <h3>{{ formTitle }}</h3>
            </v-card-title>
            <v-card-text>
              <v-container grid-list-md>
                <v-form ref="form">
                  <v-layout wrap ref="form">
                    <v-flex xs12 sm6 md6>
                      <v-text-field v-model="editedItem.marca" :rules="globalRules"
                                    label="Nombre Marca"></v-text-field>
                    </v-flex>
                    <v-flex xs12 sm6 md6>
                      <v-text-field v-model="editedItem.nroModelo" :rules="globalRules"
                                    label="Número de Modelo"></v-text-field>
                    </v-flex>
                    <v-flex xs12 sm6 md6>
                      <v-text-field v-model="editedItem.nroSerie" :rules="globalRules"
                                    label="Número de Serie"></v-text-field>
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
              :items="colectores"
              :search="search"
              item-key="colectorDatoId"
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
        <!-- values from data table -->
        <template slot="items" slot-scope="props">
          <td>{{ props.index + 1 }}</td>
          <td>{{ props.item.codigoColector }}</td>
          <td>{{ props.item.marca }}</td>
          <td>{{ props.item.nroModelo }}</td>
          <td>{{ props.item.nroSerie }}</td>
          <td>{{ changeState(props.item.estadoId) }}</td>
          <td v-if="props.item.estadoId === 1334 || props.item.estadoId === 1338 || props.item.estadoId === 1339">
            <v-icon
                    title="Solicitar"
                    small
                    v-if="props.item.estadoId !== 1338"
                    class="mr-2"
                    color="info"
                    @click="enableItem(props.item)">
              done_outline
            </v-icon>
            <v-icon
                    title="Editar"
                    small
                    class="mr-2"
                    color="success"
                    @click="editItem(props.item)">
              edit
            </v-icon>
            <v-icon
                    title="Eliminar"
                    small
                    class="mr-2"
                    color="red"
                    @click="deleteItem(props.item)">
              delete
            </v-icon>
          </td>
          <td v-else>&nbsp;</td>
          <!-- observacion -->
          <td class="text-xs-center px-0" v-if="props.item.estadoId === 1338">
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
          <td class="text-xs-left" v-if="props.item.estadoId === 1334 || props.item.estadoId === 1339">
            <v-checkbox
                    :value="props.item.colectorDatoId"
                    :active="false"
                    color="orange darken-3"
                    v-model="seleccionado"
                    @click.native="selectCheck"
            ></v-checkbox>
          </td>
          <td v-else>&nbsp;</td>
        </template>
        <template slot="pageText" slot-scope="props">
          Colectores {{ props.pageStart }} - {{ props.pageStop }} de {{ props.itemsLength }}
        </template>
        <v-alert slot="no-results" :value="true" outline color="info" icon="warning">
          La búsqueda para <strong>"{{ search }}"</strong> no tiene resultados.
        </v-alert>
        <!-- using slot no-data -->
        <template slot="no-data">
          <v-alert outline :value="true" color="info" icon="info">
            Lo sentimos, no hay nada que mostrar aquí
          </v-alert>
        </template>
      </v-data-table>
      <!-- custom notification -->
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
  import axios from "axios";
  import * as api from '../services/ColectoresApiEndpoint';

  export default {
    data() {
      return {
        globalRules: [
          (v) => !!v || 'El campo es obligatorio.'
        ],
        pagination: {
          rowsPerPage: 10
        },
        // user credentials
        username: null,
        colectores: [],
        // test
        dialog: false,
        editedIndex: -1,
        defaultItem: {
          colectorDatoId: "",
          codigoColector: "",
          estadoId: "",
          salonId: "",
          nroSerie: "",
          nroModelo: "",
          marca: ""
        },
        editedItem: {
          estadoId: "",
          salonId: "",
          nroSerie: "",
          nroModelo: "",
          marca: ""
        },
        haEcontrado: false,
        colectorDatoId: null,

        search: "",
        selected: [],
        allDone: false,
        headers: [
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
          {text: "Operaciones", value: "", sortable: false},
          {text: "Observación", value: "", sortable: false},
          {text: 'Solicitar', value: '', sortable: false}
        ],
        //
        snackbar: false,
        y: 'top',
        x: null,
        mode: '',
        timeout: 4500,
        text: '',
        //
        seleccionado: [],
        objetoEncontrado: {},
        codeSalon: null,
        nameSalon: null
      };
    },
    computed: {
      error() {
        return this.$store.getters.getError;
      },
      processing() {
        return this.$store.getters.getProcessing;
      },
      isUserAuthenticated() {
        return this.$store.getters.isUserAuthenticated;
      },
      userAuthenticatedToken() {
        return this.$store.getters.userAuthenticatedToken;
      },
      formTitle() {
        return this.editedIndex === -1 ? "Añadir Colector" : "Editar Colector";
      },
      isArrayEmpty() {
        return this.seleccionado.length === 0;
      },
      isComplete() {
        return (this.editedItem.marca &&
          this.editedItem.nroModelo &&
          this.editedItem.nroSerie);
      }
    },
    watch: {
      dialog(val) {
        val || this.close();
        this.$refs.form.reset();
      }
    },
    mounted() {
      this.fetchAllColectores();
      this.loadCodeSalon();
    },
    methods: {
      loadCodeSalon() {
        const auth = {
          headers: {Authorization: this.userAuthenticatedToken}
        };
        axios.get(api.GET_CODE_SALON_BY_SALON_ID + this.$route.params.idSalon, auth).then((res) => {
          this.codeSalon = res.data.codigoSalon;
          this.nameSalon = res.data.nombre.toUpperCase();
        });
      },
      showDescription(props) {
        const auth = {
          headers: {Authorization: this.userAuthenticatedToken}
        };
        axios.get(api.GET_SHOW_DESCRIPTION + props.colectorDatoId + '/' + props.salonId, auth)
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
                colectorDatoId: this.seleccionado[i]
              };
              newArray.push(obj);
            }
            const auth = {
              headers: {Authorization: this.userAuthenticatedToken}
            };
            axios.post(api.POST_ENABLE_LIST_COLECTOR, newArray, auth)
              .then(() => {
                this.seleccionado = [];
                this.fetchAllColectores();
              })
              .catch(error => {
                console.log(error.message);
              });
          } else {
            console.log('No han sido aprobados.');
          }
        });
      },
      fetchAllColectores() {
        const auth = {
          headers: {Authorization: this.userAuthenticatedToken},
          algo: this.$route.params.idSalon
        };
        axios
          .get(api.GET_LIST_COLECTORES_BY_SALON_ID + `${auth.algo}`, auth)
          .then(res => {
            if (res.data) {
              this.colectores = res.data;
            } else {
              this.colectores = [];
            }
          });
      },
      editItem(item) {

        this.editedIndex = this.colectores.indexOf(item);
        this.editedItem = Object.assign({}, item);
        this.dialog = true;

        const auth = {
          headers: {Authorization: this.userAuthenticatedToken}
        };

        axios.get(api.FIND_COLECTOR_BY_ID + `${this.editedItem.colectorDatoId}`, auth)
          .then(res => {
            this.haEcontrado = true;
            this.colectorDatoId = res.data.colectorDatoId;
            this.objetoEncontrado = res.data;
          })
          .catch(error => {
            console.log(error.message);
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
            const url = api.POST_ENABLE_COLECTOR + `${item.colectorDatoId}/1335`;
            axios.post(url, {}, auth)
              .then(() => {
                if (result.value) {
                  this.$swal({
                    title: '¡Solicitud!',
                    text: 'El colector ha sido solicitado.',
                    type: 'success',
                    confirmButtonText: 'Aceptar'
                  });
                }
                this.fetchAllColectores();
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
            axios.post(api.POST_DELETE_COLECTOR + `${item.colectorDatoId}`, {}, auth)
              .then(() => {
                this.$swal(
                  'Eliminado',
                  'El registro ha sido eliminado',
                  'success'
                );
                this.fetchAllColectores();
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
          this.editedIndex = -1;
        }, 300);
      },
      returnBack() {
        window.history.back();
      },
      save() {
        const auth = {
          headers: {Authorization: this.userAuthenticatedToken}
        };

        if (this.editedIndex === -1) {
          const newItem = {
            estadoId: 1000,
            salonId: parseInt(this.$route.params.idSalon),
            nroSerie: this.editedItem.nroSerie,
            nroModelo: this.editedItem.nroModelo,
            marca: this.editedItem.marca
          };

          axios.post(api.POST_ADD_COLECTOR, newItem, auth)
            .then(() => {
              this.fetchAllColectores();
              this.text = '¡Se añadió un registro correctamente!';
              // show the notification
              this.snackbar = true;
            })
            .catch(error => {
              console.log(error.message);
            });
        } else {

          const updateItem = {
            colectorDatoId: this.colectorDatoId,
            nroSerie: this.editedItem.nroSerie,
            nroModelo: this.editedItem.nroModelo,
            marca: this.editedItem.marca
          };

          if (this.objetoEncontrado.estadoCatalogoId === 1338) {
            updateItem.estadoCatalogoId = 1339;
          }

          if (this.haEcontrado) {
            axios.post(api.POST_UPDATE_COLECTOR, updateItem, auth)
              .then(() => {
                this.fetchAllColectores();
                this.text = '¡Se actualizó correctamente!';
                // show the notification
                this.snackbar = true;
              })
              .catch(error => {
                console.log(error.message);
              });
          }
        }
        this.close();
      }
    }
  };
</script>

<style scoped>
  .v-alert.v-alert--outline {
    border: 1px solid transparent !important;
  }
</style>