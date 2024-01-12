<template>
    <div>
        <!-- navigation drawer -->
        <v-navigation-drawer
                class="hidden-md-and-up"
                absolute temporary
                v-model="lateral">
            <v-list dense class="pt-0">
                <!-- all items -->
                <v-list-tile
                        v-for="(item, i) in menuItems"
                        v-bind:key="`menuItems${i}`">

                    <!-- icon -->
                    <v-list-tile-action>
                        <v-icon v-html="item.icon"></v-icon>
                    </v-list-tile-action>

                    <!-- title -->
                    <v-list-tile-content>
                        <v-list-tile-title v-text="item.title"></v-list-tile-title>
                    </v-list-tile-content>

                </v-list-tile>
            </v-list>
        </v-navigation-drawer>
        <!-- end navigation drawer -->

        <!-- toolbar nav -->
        <v-toolbar app dark class="light-blue darken-3 text-uppercase">

            <!-- side-icon -->
            <v-toolbar-side-icon
                    class="hidden-md-and-up"
                    @click.stop="lateral = !lateral"></v-toolbar-side-icon>
            <!-- side-icon -->

            <!-- title -->
            <router-link to="/" tag="span" style="cursor: pointer" >
                <v-toolbar-title v-text="title"></v-toolbar-title>
            </router-link>
            <!-- end title -->


            <v-spacer></v-spacer>
            <!-- items with icons -->
            <v-toolbar-items class="hidden-sm-and-down">
                <v-btn flat
                       v-for="(item, i) in menuItems"
                       v-bind:key="`menuItems${i}`"
                       v-bind:to="item.route">
                    <v-icon left v-html="item.icon"></v-icon>
                    {{ item.title }}
                </v-btn>
                <v-btn flat @click.prevent="signout"
                       v-if="isUserAuthenticated">
                    <v-icon left>exit_to_app</v-icon>
                    salir
                </v-btn>
            </v-toolbar-items>
            <!-- end items with icons -->
        </v-toolbar>
        <!-- end toolbar nav -->
    </div>
</template>

<script>

  export default {

    data() {
      return {
        title: "SICEL - extern🔥 | v1",
        lateral: false,
        userName: '',
        razonSocial: ''
      }
    },
    computed: {
      isUserAuthenticated() {
        return this.$store.getters.isUserAuthenticated
      },

      userAuthenticatedToken() {
        return this.$store.getters.userAuthenticatedToken
      },

      menuItems() {
        return this.isUserAuthenticated ? [
          {
            icon: 'spa',
            title: 'salones',
            route: '/salones'
          }
        ] : [
          {
            icon: 'input',
            title: 'iniciar',
            route: '/signin'
          }
        ]
      }
    }
  }
</script>

<style scoped>
    .v-alert.v-alert--outline {
        border: 1px solid transparent !important;
    }
</style>