<template>
  <div id="login-page">
    <div class="logo-area">LOGO<br>HERE</div>

    <div class="login-button-area">
      <sign-in-with-google :loaded="googleLoadState"
                           :callback="onGoogleLogin" />
      <sign-in-with-kakao />
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import * as GoogleLogin from "@/plugins/signin/google/index";
import SignInWithGoogle from "@/components/app/signin/SignInWithGoogle.vue";
import SignInWithKakao from "@/components/app/signin/SignInWithKakao.vue";
import { bePOST } from "@/util/backend";
import { IUserBasicInfo } from "@/interfaces/IUserInfo";

@Options({
  components: {
    SignInWithGoogle,
    SignInWithKakao,
  },
})
export default class LoginPage extends Vue {
  get googleLoadState(): boolean {
    return GoogleLogin.loadState.value;
  }

  created() {
    GoogleLogin.load();
  }

  unmounted() {
    GoogleLogin.unload();
  }

  async onGoogleLogin(data: GoogleLogin.GoogleAuthResponse) {
    try {
      const response = (await bePOST("/login/google", {}, {
        credential: data.credential,
      })).data as {
        name: string,
        email: string,
        token: string,
      };

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

<style lang="scss" scoped>
#login-page {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;

  width: 100%;
  height: 100%;

  .logo-area {
    font-size: 5em;
    font-weight: 700;
    text-align: center;
  }

  .login-button-area {
    display: flex;
    flex-direction: column;

    .button-login {
      display: flex;
      align-items: center;
      justify-content: center;

      width: 400px;
      max-width: 100vw;
      height: 40px;
      padding: 0.5em;
      margin: 0.5em;

      cursor: pointer;

      &:not(.no-styling) {
        background-color: $color-tertiary;
      }
    }
  }
}
</style>
