<template>
  <div id="signup-personal-data-wrapper">
    <div v-if="devMode"
         @click="devSkipInput">SKIP</div>

    <div class="signup-personal-data__content">
      <h1><strong>처음 오셨네요!🙌</strong><br />당신은 어떤 사람인가요?👂</h1>
      <h3>아래 정보를 입력하면 잘 맞을 듯한 익명 사용자와 편지를 주고받게 될 확률이 높아져요.</h3>
      <h4>입력한 개인정보는 다른 사용자<small>(닉네임 제외)</small>나 외부 개인·업체에 제공되지 않아요. <a href="#">개인정보처리방침 <small>(준비 중)</small></a></h4>

      <hr />

      <router-view v-slot="{ Component }"
                   @submitBasic="d => onSubmitData('basic', d)"
                   @backSurvey="this.$router.replace({ name: 'signup-profile-basic' })"
                   @submitSurvey="d => onSubmitData('survey', d)">
        <v-slide-y-reverse-transition leave-absolute>
          <component :is="Component" />
        </v-slide-y-reverse-transition>
      </router-view>
    </div>
  </div>
</template>

<script lang="ts">
import { Vue } from "vue-class-component";
import { RouteLocationNormalized } from "vue-router";

export default class SignupPersonalDataPage extends Vue {
  private signupData = {};
  basicDataEntered = false;

  get devMode(): boolean {
    return process.env.VUE_APP_DEVMODE === "true";
  }

  mounted(): void {
    if(this.$route.name === "signup-profile") {
      // 라우트 경로가 `/signup/profile`인 경우 페이지 정상 표시를 위해 핸들링 (`/signup/profile/basic`으로 replace)
      this.$router.replace({ name: "signup-profile-basic" });
    }

    this.onRouteUpdate(this.$route);
  }

  beforeRouteUpdate(to: RouteLocationNormalized) {
    this.onRouteUpdate(to);
  }

  onRouteUpdate(to: RouteLocationNormalized) {
    if(to.name === "signup-profile-basic") {
      // 라우트 경로가 개인정보 입력 뷰인 경우 개인정보 입력 완료 여부 초기화
      this.basicDataEntered = false;
    }

    if(to.name === "signup-profile-survey" && !this.basicDataEntered) {
      // 라우트 경로가 심리검사 뷰인데 개인정보 입력 완료 여부가 확인이 되지 않을 경우 개인정보 입력 뷰로 강제이동
      this.$router.replace({ name: "signup-profile-basic" });
    }
  }

  devSkipInput(): void {
    if(this.devMode) {
      this.$store.commit("auth/registerLoginState", {
        user: {
          ...this.$store.state.auth.userBasicInfo,
          signedUp: true,
        },
        token: this.$store.state.auth.token,
      });
      window.location.href = "/";
    }
  }

  onSubmitData(from: "basic" | "survey", data: Record<string, unknown>): void {
    switch(from) {
      case "basic":
        this.signupData = { ...data };
        this.$router.push({ name: "signup-profile-survey" });
        this.basicDataEntered = true;
        break;

      case "survey":
        this.signupData = { ...this.signupData, survey: { ...data } };
        // TODO: send basic + survey data to backend, get reply from backend, if all good set signed up bit for auth user and go to main page
        break;
    }
  }
}
</script>

<style lang="scss" scoped>
#signup-personal-data-wrapper {
  .signup-personal-data {
    &__content {
      line-height: 1.5;
      width: 80vw;
      margin: auto;

      h1 {
        font-weight: 400;
        font-size: 3em;
      }

      h3 {
        font-weight: 300;
        font-size: 1.5em;
        margin-block-start: 0.5em;
        margin-block-end: 0.5em;
      }

      h4 {
        font-weight: 300;
        font-size: 1em;

        a { text-decoration: underline; }
      }

      hr {
        margin: 1.5rem 0;
      }
    }
  }
}
</style>
