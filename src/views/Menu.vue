<template>
  <v-layout class="hidden-sm-and-down" v-if="!requerido">

    <div class="text-xs-center d-flex align-center">
      <div style="margin-left: 95px;">
        <v-btn small
               slot="activator"
               round
               disabled>
          <v-icon style="color: white !important;" left>party_mode</v-icon>
          Colectores
        </v-btn>
        <v-icon small
                slot="activator"
                color="pink"
                dark
        >party_mode
        </v-icon>
        <v-tooltip bottom>
          <span slot="activator">Información de <strong class="brown--text">Colectores</strong></span>
          <span>Colectores</span>
        </v-tooltip>
        <!-- -->
        <v-btn small
               slot="activator"
               class="success"
               round
               disabled>
          <v-icon style="color: white !important;" left>screen_share</v-icon>
          Máquinas
        </v-btn>
        <v-icon small
                slot="activator"
                color="brown"
                dark
        >screen_share
        </v-icon>
        <v-tooltip bottom>
          <span slot="activator">Información de <strong class="indigo--text">Máquinas</strong></span>
          <span>Máquinas</span>
        </v-tooltip>
        <!---->
        <v-btn small
               slot="activator"
               class="success"
               round
               disabled>
          <v-icon style="color: white !important;" left>inbox</v-icon>
          Cajas
        </v-btn>
        <v-icon small
                slot="activator"
                color="info"
                dark
        >call_to_action
        </v-icon>
        <v-tooltip bottom>
          <span slot="activator">Información de <strong class="pink--text">Cajas</strong></span>
          <span>Cajas</span>
        </v-tooltip>
      </div>
    </div>
  </v-layout>
</template>

<script>

  export default {
    props: {
      "requerido": {
        type: Object,
        required: false
      }
    },
    data() {
      return {
        props: null
      }
    }
  }
</script>

<style scoped>
  .theme--light.v-btn.v-btn--disabled:not(.v-btn--icon):not(.v-btn--flat) {
    background-color: #00BCD4 !important;
    color: white !important;
  }

</style>