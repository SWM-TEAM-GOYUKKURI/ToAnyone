import { Module } from "vuex";
import { RootStoreState } from "..";
import { UserInfo } from "@/interfaces/internal";
import { LetterInboxItemList } from "@/interfaces/backend";

export interface UserspaceStoreState {
  user: UserInfo | null,
  unreadLetters: LetterInboxItemList,
  settings: {
    // to be filled
  },
}

const userspaceStore: Module<UserspaceStoreState, RootStoreState> = {
  namespaced: true,
  state: {
    user: null,
    unreadLetters: [],
    settings: { },
  },
  mutations: {
    updateUserInfo(state, payload: UserInfo) { state.user = payload; },
    updateUnreadLetters(state, payload: LetterInboxItemList) { state.unreadLetters = payload; },
  },
};

export default userspaceStore;
