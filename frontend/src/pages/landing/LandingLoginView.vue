<template>
  <div id="landing-login-wrapper">
    <div class="landing-login__external-account-text">외부 계정으로 로그인하기</div>

    <hr />

    <sign-in-with-google :loaded="googleLoadState"
                         :callback="onGoogleLogin" />
    <sign-in-with-kakao />

    <hr />

    <div class="landing-login__controls">
      <button class="button narrow" @click="close"><v-icon>mdi-close</v-icon> <span>닫기</span></button>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import * as GoogleLogin from "@/plugins/signin/google";
import SignInWithGoogle from "@/components/app/signin/SignInWithGoogle.vue";
import SignInWithKakao from "@/components/app/signin/SignInWithKakao.vue";
import { LoginGoogleResponse } from "@/interfaces/backend";
import { UserInfoBasic } from "@/interfaces/internal";
import { bePOST } from "@/util/backend";

@Options({
  components: {
    SignInWithGoogle,
    SignInWithKakao,
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
      const response = (await bePOST<LoginGoogleResponse>("/login/google", {}, {
        credential: data.credential,
      })).data;

      const user: UserInfoBasic = {
        nickname: response.name,
        email: response.email,
        firstSignupPassed: response.registrationFormFilled,
      };

      this.$store.commit("auth/registerLoginState", { user, token: response.token });
      this.$cookies.set("userSession", response.token);

      if(user.firstSignupPassed) {
        this.$router.replace({ name: "main" });
      } else {
        this.$router.replace({ name: "signup-profile" });
      }
    } catch(e) {
      alert("로그인하는 중 오류가 발생했어요. " + e);
    }
  }
}
</script>

<style lang="scss" scoped>
#landing-login-wrapper {
  width: 400px;

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
