import { Module } from "vuex";
import { RootStoreState } from "..";
import { UserInfoBasic } from "@/interfaces/internal";

export interface AuthStoreState {
  isLoggedIn: boolean,
  token: string | null,
  userBasicInfo: UserInfoBasic | null,
}

const authStore: Module<AuthStoreState, RootStoreState> = {
  namespaced: true,
  state: {
    isLoggedIn: false,
    token: null,
    userBasicInfo: null,
  },
  getters: {
    isUserLoggedIn(state): boolean {
      return state.isLoggedIn;
    },
  },
  mutations: {
    registerLoginState(state, payload: {
      user: UserInfoBasic | null,
      token?: string,
    }): void {
      // payload의 데이터가 모두 null이 아닌 값이 있는 경우 로그인 상태를 설정하는 것으로 처리
      // 그렇지 않은 경우(payload의 데이터 하나라도 null이 존재하는 경우) 로그인 상태를 지우는 것으로 처리
      if(payload) {
        if(payload.user && payload.token) {
          state.isLoggedIn = true;
          state.userBasicInfo = payload.user;
          state.token = payload.token;

          return;
        }
      }

      state.isLoggedIn = false;
      state.userBasicInfo = null;
      state.token = null;
    },
  },
};

export default authStore;
