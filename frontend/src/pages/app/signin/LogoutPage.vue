<template>
  <div>😥 로그아웃하고 있어요.</div>
</template>

<script lang="ts">
import { Vue } from "vue-class-component";

export default class LogoutPage extends Vue {
  mounted(): void {
    // Unconditional logout
    this.$store.commit("auth/registerLoginState", null);
    this.$cookies.remove("userSession");
    window.location.replace("/");
  }
}
</script>
