<template>
  <div id="login-page">
    <div class="logo-area">LOGO<br>HERE</div>

    <div class="login-button-area">
      <sign-in-with-google :loaded="googleLoadState" />
      <sign-in-with-kakao />
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import googleSigninScript from "@/plugins/signin/google/index";
import SignInWithGoogle from "@/components/signin/SignInWithGoogle.vue";
import SignInWithKakao from "@/components/signin/SignInWithKakao.vue";

@Options({
  components: {
    SignInWithGoogle,
    SignInWithKakao,
  },
})
export default class LoginPage extends Vue {
  get googleLoadState(): boolean {
    return googleSigninScript.loadState.value;
  }

  created() {
    googleSigninScript.load();
  }

  unmounted() {
    googleSigninScript.unload();
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
