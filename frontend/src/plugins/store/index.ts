import { createStore } from "vuex";
import createPersistedState from "vuex-persistedstate";
import authModule from "./modules/auth";
import userspaceModule from "./modules/userspace";

const store = createStore({
  modules: {
    auth: authModule,
    user: userspaceModule,
  },
  plugins: [
    createPersistedState({
      storage: window.sessionStorage,
      paths: ["auth"],
    }),
  ],
});

export default store;
