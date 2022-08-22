<template>
  <div id="login-google" class="button-login no-styling" ref="googleLoginButton" />
</template>

<script lang="ts">
import { Vue } from "vue-class-component";
import { Prop, Watch } from "vue-property-decorator";
import { GoogleAuthResponse } from "@/plugins/signin/google";
import { bePOST } from "@/util/backend";
import { IUserBasicInfo } from "@/interfaces/IUserInfo";

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
    try {
      const response = (await bePOST("/login/google", {}, {
        credential: data.credential,
      }, true)) as {
        name: string,
        email: string,
        token: string,
      };

      // TODO: test needs from below
      const user: IUserBasicInfo = {
        nickname: response.name,
      };

      this.$store.commit("auth/registerLoginState", { user, token: response.token });
      this.$cookies.set("userSession", response.token);

      this.$router.replace({ name: "main" });
    } catch(e) {
      console.error(e);
    }
  }
}
</script>
