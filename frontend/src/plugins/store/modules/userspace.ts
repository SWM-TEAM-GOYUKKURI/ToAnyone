import { Module } from "vuex";
import { RootStoreState } from "..";
import { UserInfo } from "@/interfaces/internal";

export interface UserspaceStoreState {
  user: UserInfo | null,
  settings: {
    // to be filled
  },
}

const userspaceStore: Module<UserspaceStoreState, RootStoreState> = {
  namespaced: true,
  state: {
    user: null,
    settings: { },
  },
};

export default userspaceStore;
