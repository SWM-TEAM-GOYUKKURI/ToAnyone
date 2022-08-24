import { createStore } from "vuex";
import createPersistedState from "vuex-persistedstate";
import authModule, { AuthStoreState } from "./modules/auth";
import userspaceModule, { UserspaceStoreState } from "./modules/userspace";

export interface RootStoreState {
  auth: AuthStoreState,
  user: UserspaceStoreState,
}

const store = createStore<RootStoreState>({
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
