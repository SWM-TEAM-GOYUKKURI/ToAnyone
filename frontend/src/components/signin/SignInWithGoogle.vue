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
    // NOTE: JWT 토큰 파싱은 백엔드에서 처리하고, 파싱된 데이터(ID, 계정 이름 등)는 로그인 처리 응답의 일부로 받도록 합니다.
    //       프론트엔드에서는 백엔드에 이 JWT 토큰을 그대로 전송합니다.

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
