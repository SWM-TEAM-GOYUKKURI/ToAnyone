import { createStore } from "vuex";
import IUserInfo from "@/interfaces/IUserInfo";

export interface StoreState {
  loginInfo: {
    isLoggedIn: boolean,
    user: IUserInfo | null,
  },
}

const store = createStore<StoreState>({
  state: {
    loginInfo: {
      isLoggedIn: false,
      user: null,
    },
  },
  getters: {
    isUserLoggedIn(state): boolean {
      return state.loginInfo.isLoggedIn;
    },
  },
  mutations: {
    registerLoginState(state, payload: IUserInfo | null): void {
      // payload의 데이터가 IUserInfo의 형태로 존재할 경우 로그인 상태를 설정하는 것으로 처리
      // payload가 null일 경우 로그인 상태를 지우는 것으로 처리
      state.loginInfo.isLoggedIn = !!payload;
      state.loginInfo.user = payload ?? null;
    },
  },
});

export default store;
