import { Module } from "vuex";
import { IUserInfo } from "@/interfaces/IUserInfo";

interface UserspaceStoreState {
  user: IUserInfo | null,
  settings: {
    // to be filled
  },
}

const userspaceStore: Module<UserspaceStoreState, unknown> = {
  namespaced: true,
  state: {
    user: null,
    settings: { },
  },
};

export default userspaceStore;
