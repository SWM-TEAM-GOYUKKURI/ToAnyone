<template>
  <div>
    <v-slide-y-reverse-transition>
      <span v-show="pageMounted">🫠 로그아웃하고 있어요.</span>
    </v-slide-y-reverse-transition>
  </div>
</template>

<script lang="ts">
import { Vue } from "vue-class-component";

export default class LogoutPage extends Vue {
  pageMounted = false;

  mounted(): void {
    this.pageMounted = true;

    // Unconditional logout
    this.$store.commit("auth/registerLoginState", null);
    this.$cookies.remove("userSession");
    window.location.replace("/");
  }
}
</script>

<style scoped>
div {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
  font-size: 3em;
  text-align: center;
  line-height: 1.25;
}
</style>
