import Authentication from '../services/Authentication'

export default {
  state: {
    user: {
      isAuthenticated: false,
      username: null
    }
  },
  mutations: {

    SET_USER(state, payload) {
      state.user.isAuthenticated = true;
      state.user.username = payload;
    },

    UNSET_USER(state) {
      state.user = {
        isAuthenticated: false,
        username: null
      }
    }

  },
  actions: {

    SIGNIN({commit}, payload) {

      commit('SET_PROCESSING', true);
      commit('CLEAR_ERROR');

      Authentication.login(payload).then((res) => {

        commit('SET_USER', res.data.token);
        window.localStorage.setItem('token', res.data.token);
        commit('SET_PROCESSING', false);
      }).catch((error) => {
        commit('SET_PROCESSING', false);
        commit('SET_ERROR', error.message);
      });

    },

    SIGNOUT({commit}) {

      window.localStorage.removeItem('token');
      commit('UNSET_USER');

    },

    STATE_CHANGED({commit}, payload) {
      if (payload) {
        commit('SET_USER', payload.username);
      } else {
        commit('UNSET_USER');
      }
    }
  },

  getters: {
    isUserAuthenticated: (state) => state.user.isAuthenticated,
    userAuthenticatedToken: (state) => state.user.username
  }

}