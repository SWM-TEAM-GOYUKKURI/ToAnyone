<template>
  <div class="button-login" @click="onLoginButtonClick">
    {{ buttonText }}
  </div>
</template>

<script lang="ts">
import type { Vue3GoogleOAuth2Component } from "@/plugins/signin/google-oauth2/interfaces";
import { inject } from "vue";
import { Vue } from "vue-class-component";

export default class LoginWithGoogle extends Vue {
  private Vue3GoogleOauth = inject<Vue3GoogleOAuth2Component>("Vue3GoogleOauth");

  get buttonText(): string {
    if(this.Vue3GoogleOauth) {
      if(this.Vue3GoogleOauth.isInit) {
        return "Google 계정으로 로그인";
      }
    }

    return "Google 로그인 초기화 중...";
  }

  async onLoginButtonClick() {
    let googleAuthUser;
    try {
      googleAuthUser = await this.$gAuth.signIn();
    } catch {
      // TODO: catch initialization error
    }

    if(!googleAuthUser) {
      // TODO: handle improper object
    }
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
