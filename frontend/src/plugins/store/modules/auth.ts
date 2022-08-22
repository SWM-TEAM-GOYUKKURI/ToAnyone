import { Module } from "vuex";
import { IUserBasicInfo } from "@/interfaces/IUserInfo";

interface AuthStoreState {
  isLoggedIn: boolean,
  token: string | null,
  userBasicInfo: IUserBasicInfo | null,
}

const authStore: Module<AuthStoreState, unknown> = {
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
    registerLoginState(state, payload: IUserBasicInfo | null): void {
      // payload의 데이터가 IUserInfo의 형태로 존재할 경우 로그인 상태를 설정하는 것으로 처리
      // payload가 null일 경우 로그인 상태를 지우는 것으로 처리
      state.isLoggedIn = !!payload;
      state.userBasicInfo = payload ?? null;
    },
  },
};

export default authStore;
