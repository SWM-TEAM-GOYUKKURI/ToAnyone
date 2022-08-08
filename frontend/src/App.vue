<template>
  <router-view />
</template>

<script lang="ts">
import { Vue } from "vue-class-component";
import { Watch } from "vue-property-decorator";

export default class App extends Vue {
  /* $route 변경 감시 훅 */
  @Watch("$route", { immediate: true, deep: true })
  onRouteChange(): void {
    const jwtCookie = this.$cookies.get("session");

    if(!this.$store.getters.isUserLoggedIn ||
      !this.$store.state.loginInfo.user ||
      !jwtCookie) {
      // 로그인 상태 정보(store state + JWT cookie)가 비정상적일 때...

      // if(this.$route.path !== "/") {     <- 현재 루트가 앱 메인 페이지로 사용 중이기 때문에 강제 이동 예외 라우트에서 제외
      // 현재 URL 경로가 루트(/)가 아닐 경우, 로그인 상태를 초기화하고 로그인 페이지로 이동
      this.$store.dispatch("registerLoginState", null);
      this.$router.replace({ name: "login" });

      // TODO: 랜딩 페이지 생성 시 이 로직이 변경될 수 있음(랜딩 페이지 관련 라우트는 강제 이동에서 제외)
      // }
    }
  }

  /* 앱 전역 instance created 훅 */
  created(): void {
    this.onRouteChange();
  }
}
</script>

<style lang="scss">
</style>
