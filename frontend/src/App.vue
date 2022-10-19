<template>
  <router-view />
</template>

<script lang="ts">
import { Vue } from "vue-class-component";

export default class App extends Vue {
  /* 앱 전역 before instance create 훅 */
  beforeCreate(): void {
    // Router 로그인 상태 가드 추가
    this.$router.beforeResolve((to) => {
      const jwtCookie = this.$cookies.get("userSession");

      const isLoginStateReliable = () => {
        return this.$store.getters["auth/isUserLoggedIn"] &&
          this.$store.state.auth.userBasicInfo &&
          jwtCookie;
      };

      if(to.name !== "login") {
        // 로그인 페이지 외의 라우트로 이동하고 있는 경우...

        if(!isLoginStateReliable()) {
          // 로그인 상태 정보(store state + JWT cookie)가 비정상적일 때...

          // if(this.$route.path !== "/") {     <- 현재 루트가 앱 메인 페이지로 사용 중이기 때문에 강제 이동 예외 라우트에서 제외
          // 로그인 상태를 초기화하고 로그인 페이지로 강제 이동
          this.$store.commit("auth/registerLoginState", null);
          // this.$router.replace({ name: "login" });
          window.location.replace("/login");

          // TODO: 랜딩 페이지 생성 시 이 로직이 변경될 수 있음(랜딩 페이지 관련 라우트는 강제 이동에서 제외)
          // }
        } else if(isLoginStateReliable() &&
          !to.name?.toString().startsWith("signup-profile") &&
          !this.$store.state.auth.userBasicInfo?.firstSignupPassed) {
          // 로그인 상태 정보는 정상적이지만 초기 회원가입 과정을 거치지 않은 경우...

          if(to.name !== "logout") {
            // 회원가입(개인정보 입력) 페이지로 강제 이동 (로그아웃 페이지 제외)
            this.$router.replace({ name: "signup-profile" });
          }
        }
      } else {
        // 로그인 페이지로 이동하고 있는 경우...

        if(isLoginStateReliable()) {
          // 로그인 상태 정보가 정상적일 때...

          // 이미 로그인 상태라고 판단하여 메인 페이지로 강제 이동
          this.$router.replace({ name: "main" });
        }
      }
    });
  }

  /* 앱 전역 instance created 훅 */
  created(): void {
    // to be filled
  }
}
</script>

<style lang="scss">
</style>
