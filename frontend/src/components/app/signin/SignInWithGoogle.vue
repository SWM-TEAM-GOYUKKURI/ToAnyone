<template>
  <div id="login-google" class="button-login no-styling" ref="googleLoginButton" />
</template>

<script lang="ts">
import { Vue } from "vue-class-component";
import { Prop, Watch } from "vue-property-decorator";
import { GoogleAuthResponse } from "@/plugins/signin/google";

export default class SignInWithGoogle extends Vue {
  @Prop({ type: Boolean, default: false }) loaded!: boolean;
  @Prop({ type: Function, required: true }) callback!: (data: GoogleAuthResponse) => void;

  get googleClientId(): string {
    return process.env.VUE_APP_SIGNIN_GOOGLE_CLIENT_ID;
  }

  mounted(): void { this.onScriptLoadStateChange(); }

  @Watch("loaded")
  onScriptLoadStateChange() {
    if(this.loaded && window.google) {
      window.google.accounts.id.initialize({
        client_id: this.googleClientId,
        callback: this.callback,
      });

      window.google.accounts.id.renderButton(this.$refs.googleLoginButton, {
        type: "standard",
        width: 400,
        theme: "outline",
        text: "signin_with",
      });
    }
  }
}
</script>
