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

      if(!(to.name === "login" || to.name === "landing")) {
        // 로그인 페이지나 랜딩 페이지 외의 라우트로 이동하고 있는 경우...

        if(!isLoginStateReliable()) {
          // 로그인 상태 정보(store state + JWT cookie)가 비정상적일 때...

          // 로그인 상태를 초기화하고 랜딩 페이지로 강제 이동
          this.$store.commit("auth/registerLoginState", null);
          window.location.replace("/hello");
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
        // 로그인 페이지 또는 랜딩 페이지로 이동하고 있는 경우...

        if(isLoginStateReliable()) {
          // 로그인 상태 정보가 정상적일 때...

          // 이미 로그인 상태라고 판단하여 메인 페이지로 강제 이동
          this.$router.replace({ name: "main" });
        }
      }
    });
  }
}
</script>

<style lang="scss">
</style>
