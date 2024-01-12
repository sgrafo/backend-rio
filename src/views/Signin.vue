<template>
  <!-- copy from layout defined vuetify -->
  <v-container>
    <v-layout align-center justify-center mt-5>

      <v-flex xs12 sm8 md6 fluid>

        <v-card class="elevation-12">

          <!-- title form -->
          <v-toolbar dark color="light-blue darken-4">
            <v-toolbar-title>
              <v-tooltip bottom>
                <v-btn
                        slot="activator"
                        icon
                        large
                        target="_blank">
                  <v-icon large>star_border</v-icon>
                </v-btn>
                <span></span>
              </v-tooltip>
              <span>Inicio de Sesión</span>
            </v-toolbar-title>
          </v-toolbar>
          <!-- end title form -->

          <v-card-text>

            <v-alert
                    :value="error"
                    outline
                    transition="scale-transition"
                    type="info">
              Tus credenciales no son válidas. Por favor inténtalo otra vez.
            </v-alert>

            <v-form v-model="valid">
              <v-text-field prepend-icon="assignment_ind" label="Nombre de Usuario" type="text"
                            v-model="username"
                            :rules="userRules"
                            required></v-text-field>

              <v-text-field prepend-icon="linear_scale" label="Contraseña"
                            v-model="password"
                            :rules="passwordRules"
                            :append-icon="passwordVisible ? 'visibility' : 'visibility_off'"
                            :type="passwordVisible ? 'text' : 'password'"
                            @click:append="passwordVisible = !passwordVisible"
                            @keyup.enter="signin"
                            required></v-text-field>
            </v-form>

          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn @click.prevent="signin"
                   color="primary"

                   v-bind:disabled="processing || !valid">Entrar
            </v-btn>
          </v-card-actions>

        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
  <!-- end copy from layout defined vuetify -->

</template>

<script>
  import AppHeader from './../components/AppHeader'

  export default {
    components: {
      AppHeader
    },
    // data property
    data() {
      return {
        // user credentials
        username: null,
        password: null,

        // custom
        valid: false,
        passwordVisible: false,

        // rules
        userRules: [
          (v) => !!v || 'el nombre de usuario es obligatorio.',
          (v) => (v && v.length >= 2) || 'el nombre de usuario no puede ser menor a 5 caracteres.'
        ],
        passwordRules: [
          (v) => !!v || 'la contraseña es obligatoria.',
          (v) => (v && v.length >= 2) || 'la contraseña no puede ser menor a 5 caracteres.'
        ]
      }
    },
    // computed property
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
    // watch property
    watch: {
      isUserAuthenticated(val) {
        if (val === true) {
          this.$router.push("/salones")
        }
      }
    },
    // methods property
    methods: {
      signin() {
        this.$store.dispatch('SIGNIN', {
          username: this.username,
          password: this.password
        }).then(() => {
          if (this.error) {
            this.$router.push('/salones')
          } else {
            this.$router.push('/signin')
          }
        });
      }
    }
  }
</script>
