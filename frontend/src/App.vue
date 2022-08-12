<template>
  <route-login-state-guard />
  <router-view />
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import RouteLoginStateGuard from "@/components/RouteLoginStateGuard.vue";

import "@/styles/main.scss";

@Options({
  components: {
    RouteLoginStateGuard,
  },
})
export default class App extends Vue {
  /* 앱 전역 instance created 훅 */
  created(): void {
    // to be filled
  }
}
</script>

<style lang="scss">
</style>
