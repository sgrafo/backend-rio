<template>

  <v-footer absolute dark height="auto">
    <v-card
            class="flex"
            flat
            tile>
      <v-card-actions class="light-blue darken-3 justify-center">
        &copy; {{ getCurrentYear }} — &nbsp;<strong>Autoridad de Fiscalización del Juego</strong>
      </v-card-actions>
    </v-card>

  </v-footer>

</template>

<script>
  export default {

    data() {
      return {
        anio: new Date().getFullYear()
      }
    },

    computed: {
      getCurrentYear() {
        return this.anio;
      }
    }
  }
</script>

<style scoped>

</style>