import { Module } from "vuex";
import { RootStoreState } from "..";
import { IUserInfo } from "@/interfaces/IUserInfo";

export interface UserspaceStoreState {
  user: IUserInfo | null,
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
