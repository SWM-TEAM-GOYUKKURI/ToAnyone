<template>
  <div id="login-google" ref="googleLoginButton" />
</template>

<script lang="ts">
import { Vue } from "vue-class-component";
import waitFor from "@/util/script-waiter";
import { GoogleAuthResponse } from "@/plugins/signin/google/interfaces";

export default class SignInWithGoogle extends Vue {
  get googleClientId(): string {
    return process.env.VUE_APP_SIGNIN_GOOGLE_CLIENT_ID;
  }

  async mounted() {
    const foundGoogle = await waitFor("google");

    if(foundGoogle) {
      window.google.accounts.id.initialize({
        client_id: this.googleClientId,
        callback: this.loginCallback,
      });

      window.google.accounts.id.renderButton(this.$refs.googleLoginButton, {
        theme: "outline",
        size: "large",
      });
    }
  }

  async loginCallback(data: GoogleAuthResponse) {
    // to be filled
  }
}
</script>

<style lang="scss">
/* 임시 버튼 스타일 */
.button-login {
  display: inline-block;
  padding: 0.5em;
  background-color: rgba(0, 0, 0, 0.25);
  cursor: pointer;
}
</style>
