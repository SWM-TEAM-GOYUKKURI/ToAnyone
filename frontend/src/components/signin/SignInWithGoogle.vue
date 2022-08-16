<template>
  <div id="login-google" class="button-login no-styling" ref="googleLoginButton" />
</template>

<script lang="ts">
import { Vue } from "vue-class-component";
import { Prop, Watch } from "vue-property-decorator";
import { GoogleAuthResponse } from "@/plugins/signin/google/interfaces";
import { bePOST } from "@/util/backend";
import IUserInfo from "@/interfaces/IUserInfo";

export default class SignInWithGoogle extends Vue {
  @Prop({ type: Boolean, default: false }) loaded!: boolean;

  get googleClientId(): string {
    return process.env.VUE_APP_SIGNIN_GOOGLE_CLIENT_ID;
  }

  @Watch("loaded")
  onScriptLoadStateChange() {
    if(this.loaded && window.google) {
      window.google.accounts.id.initialize({
        client_id: this.googleClientId,
        callback: this.loginCallback,
      });

      window.google.accounts.id.renderButton(this.$refs.googleLoginButton, {
        type: "standard",
        width: 400,
        theme: "outline",
        text: "signin_with",
      });
    }
  }

  async loginCallback(data: GoogleAuthResponse) {
    // NOTE: JWT 토큰(data.credential) 파싱은 백엔드에서 처리하고, 파싱된 데이터(ID, 계정 이름 등)는 로그인 요청의 응답으로 받도록 합니다.
    //       프론트엔드에서는 백엔드에 이 JWT 토큰을 그대로 전송합니다.

    if(process.env.NODE_ENV === "development") {
      // Bypass login process in development environment
      const devUser: IUserInfo = {
        id: -1,
        nickname: "DEVELOPMENT",
      };

      this.$store.commit("registerLoginState", devUser);
      this.$cookies.set("userSession", btoa(JSON.stringify(devUser)));

      this.$router.replace({ name: "main" });
    } else {
      const response = await bePOST("/TOBEFILLED", {
        credential: data.credential,
      });
      console.log(response);
    }
  }
}
</script>
