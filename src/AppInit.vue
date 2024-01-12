<template>
  <v-app>
    <AppHeader v-if="!isUserAuthenticated"/>
    <AppHeaderLogged v-if="isUserAuthenticated" />
    <router-view></router-view>
    <AppFooter />
  </v-app>
</template>

<script>
  import AppHeader from './components/AppHeader'
  import AppHeaderLogged from './components/AppHeaderLogged'
  import AppFooter from './components/AppFooter'

  export default {
    name: 'App',
    components: {
      AppHeader,
      AppHeaderLogged,
      AppFooter
    }, computed: {
      isUserAuthenticated() {
        return this.$store.getters.isUserAuthenticated
      },

      userAuthenticatedToken() {
        return this.$store.getters.userAuthenticatedToken
      }
    }
  }
</script>

<style>
  .swal2-popup {
    font-family: Roboto !important;
  }

</style>
