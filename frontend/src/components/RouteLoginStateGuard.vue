<template><div></div>
</template>

<script lang="ts">
import { Vue } from "vue-class-component";

export default class RouteLoginStateGuard extends Vue {
  beforeCreate(): void {
    this.$router.beforeResolve((to) => {
      const jwtCookie = this.$cookies.get("userSession");

      if(to.name !== "login") {
        // 로그인 페이지 외의 라우트로 이동하고 있는 경우...

        if(!this.$store.getters.isUserLoggedIn ||
          !this.$store.state.loginInfo.user ||
          !jwtCookie) {
          // 로그인 상태 정보(store state + JWT cookie)가 비정상적일 때...

          // if(this.$route.path !== "/") {     <- 현재 루트가 앱 메인 페이지로 사용 중이기 때문에 강제 이동 예외 라우트에서 제외
          // 로그인 상태를 초기화하고 로그인 페이지로 강제 이동
          this.$store.commit("registerLoginState", null);
          this.$router.replace({ name: "login" });

          // TODO: 랜딩 페이지 생성 시 이 로직이 변경될 수 있음(랜딩 페이지 관련 라우트는 강제 이동에서 제외)
          // }
        }
      } else {
        // 로그인 페이지로 이동하고 있는 경우...

        if(this.$store.getters.isUserLoggedIn &&
          this.$store.state.loginInfo.user &&
          jwtCookie) {
          // 로그인 상태 정보가 정상적일 때...

          // 이미 로그인 상태라고 판단하여 메인 페이지로 강제 이동
          this.$router.replace({ name: "main" });
        }
      }
    });
  }
}
</script>
