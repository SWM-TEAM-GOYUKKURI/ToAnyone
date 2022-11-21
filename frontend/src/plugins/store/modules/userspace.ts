import { Module } from "vuex";
import { RootStoreState } from "..";
import { UserInfo } from "@/interfaces/internal";
import { DecorationItemList, LetterInboxItemList } from "@/interfaces/backend";

export interface UserspaceStoreState {
  user: UserInfo | null,
  unreadLetters: LetterInboxItemList,
  unreadSentLetters: LetterInboxItemList,
  userItems: DecorationItemList,
  settings: {
    // to be filled
  },
}

const userspaceStore: Module<UserspaceStoreState, RootStoreState> = {
  namespaced: true,
  state: {
    user: null,
    unreadLetters: [],
    unreadSentLetters: [],
    userItems: [],
    settings: { },
  },
  mutations: {
    updateUserInfo(state, payload: UserInfo) { state.user = payload; },
    updateUnreadLetters(state, payload: LetterInboxItemList) { state.unreadLetters = payload; },
    updateUnreadSentLetters(state, payload: LetterInboxItemList) { state.unreadSentLetters = payload; },
    updateUserItems(state, payload: DecorationItemList) { state.userItems = payload; },
  },
  getters: {
    unreadLettersAll(state): LetterInboxItemList {
      return [...state.unreadLetters, ...state.unreadSentLetters];
    },
  },
};

export default userspaceStore;
