<template>
  <div id="landing-login-wrapper">
    <div class="landing-login__external-account-text">외부 계정으로 로그인하기</div>

    <hr />

    <sign-in-with-google :loaded="googleLoadState"
                         :callback="onGoogleLogin" />

    <div class="landing-login__controls">
      <button class="button narrow" @click="close"><v-icon>mdi-close</v-icon> <span>닫기</span></button>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import * as GoogleLogin from "@/plugins/signin/google";
import SignInWithGoogle from "@/components/signin/SignInWithGoogle.vue";
import { UserInfoBasic } from "@/interfaces/internal";

@Options({
  components: {
    SignInWithGoogle,
  },
})
export default class LoginView extends Vue {
  get googleLoadState(): boolean {
    return GoogleLogin.loadState.value;
  }

  close(): void {
    const immediateParentRouteName = this.$route.matched[this.$route.matched.length - 2].name;
    this.$router.replace({ name: immediateParentRouteName });
  }

  async onGoogleLogin(data: GoogleLogin.GoogleAuthResponse) {
    try {
      const response = await this.$api.requestGoogleLogin(data.credential);

      if(response.data) {
        const user: UserInfoBasic = {
          nickname: response.data.name,
          email: response.data.email,
          registrationFormFilled: response.data.registrationFormFilled,
        };

        this.$store.commit("auth/registerLoginState", { user, token: response.data.token });
        this.$cookies.set("userSession", response.data.token);

        if(user.registrationFormFilled) {
          window.location.href = this.$router.resolve({ name: "main" }).href;
        } else {
          window.location.href = this.$router.resolve({ name: "signup-profile" }).href;
        }
      } else {
        alert("로그인하는 중 오류가 발생했어요. " + response.statusCode);
      }
    } catch(e) {
      alert("로그인하는 중 오류가 발생했어요. " + e);
    }
  }
}
</script>

<style lang="scss" scoped>
#landing-login-wrapper {
  & > * { text-align: center; }

  .landing-login {
    &__external-account-text {
      font-size: 1.2em;
      font-weight: bold;
      margin-bottom: 0.5em;
    }

    &__controls {
      display: flex;
      justify-content: flex-end;
      margin-top: 2em;
    }
  }

  .button-login {
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 0.5em;
    margin: 0.5em 0;
  }
}
</style>
