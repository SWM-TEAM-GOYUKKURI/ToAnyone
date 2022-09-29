<template>
  <div>
    <div v-if="devMode"
         @click="devSkipInput">SKIP</div>
  </div>
</template>

<script lang="ts">
import { Vue } from "vue-class-component";

export default class SignupPersonalDataPage extends Vue {
  get devMode(): boolean {
    return process.env.VUE_APP_DEVMODE === "true";
  }

  devSkipInput(): void {
    if(this.devMode) {
      this.$store.commit("auth/registerLoginState", {
        user: {
          ...this.$store.state.auth.userBasicInfo,
          signedUp: true,
        },
        token: this.$store.state.auth.token,
      });
      window.location.href = "/";
    }
  }
}
</script>
