<template>
  <div id="signup-personal-data-wrapper">
    <!-- <div v-if="devMode"
         @click="devSkipInput">SKIP</div> -->

    <div class="signup-personal-data__content">
      <h1><strong>처음 오셨네요!🙌</strong><br />당신은 어떤 사람인가요?👂</h1>
      <h3>아래 정보를 입력하면 잘 맞을 듯한 익명 사용자와 편지를 주고받게 될 확률이 높아져요.</h3>
      <h4>입력한 개인정보는 다른 사용자<small>(닉네임 제외)</small>나 외부 개인·업체에 제공되지 않아요. <a href="#">개인정보처리방침 <small>(준비 중)</small></a></h4>
      <h4 class="sub">잘못 로그인했나요? <a ref="logout" href="#">로그아웃</a></h4>

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
import { SignupData, UserInfoBasic } from "@/interfaces/internal";
import { bePUT } from "@/util/backend";
import { UserProfilePsychologicalExamItem, UserProfileUpdateRequest } from "@/interfaces/backend";

export default class SignupPersonalDataPage extends Vue {
  private signupData: SignupData = {};
  basicDataEntered = false;

  /* get devMode(): boolean {
    return process.env.VUE_APP_DEVMODE === "true";
  } */

  mounted(): void {
    (this.$refs.logout as HTMLAnchorElement).href = this.$router.resolve({ name: "logout" }).href;

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

  /* devSkipInput(): void {
    if(this.devMode) {
      this.$store.commit("auth/registerLoginState", {
        user: {
          ...this.$store.state.auth.userBasicInfo,
          firstSignupPassed: true,
        },
        token: this.$store.state.auth.token,
      });
      window.location.href = "/";
    }
  } */

  async onSubmitData(from: "basic" | "survey", data: Record<string, unknown>) {
    switch(from) {
      case "basic":
        this.signupData = { ...data };
        this.$router.push({ name: "signup-profile-survey" });
        this.basicDataEntered = true;
        break;

      case "survey":
        this.signupData = { ...this.signupData, survey: { ...(data as Record<number, number>) } };

        if(this.validateSignupData()) {
          const requestData: UserProfileUpdateRequest = {
            age: this.signupData.age!,
            gender: this.signupData.gender!,
            job: this.signupData.job!,
            nickname: this.signupData.nickname!,
            psychologicalExams: Object.entries(this.signupData.survey!).map((v) => { return { questionId: parseInt(v[0]), answerId: v[1] } as UserProfilePsychologicalExamItem; }),
          };

          const response = await bePUT("/user", requestData, {
            credentials: this.$store.state.auth.token!,
          });

          if(response.statusCode === 200) {
            if(this.$store.state.auth.userBasicInfo) {
              const user: UserInfoBasic = { ...this.$store.state.auth.userBasicInfo, firstSignupPassed: true };
              this.$store.commit("auth/registerLoginState", {
                user,
                token: this.$store.state.auth.token,
              });
            }

            this.$router.replace({ name: "main" });
          } else {
            // TEMP ALERT
            alert(`사용자 정보 업데이트 중 오류: ${response.statusCode}`);
          }
        }
        break;
    }
  }

  validateSignupData(): boolean {
    function isSignupData(data: SignupData): data is SignupData { return !!data; }

    if(isSignupData(this.signupData) &&
      "nickname" in this.signupData && typeof this.signupData.nickname === "string" &&
      "gender" in this.signupData && typeof this.signupData.gender === "string" &&
      "age" in this.signupData && typeof this.signupData.age === "string" &&
      "job" in this.signupData && typeof this.signupData.job === "string" &&
      "survey" in this.signupData && typeof this.signupData.survey === "object") {
      // Additional checks for survey
      if(Object.keys(this.signupData.survey).length === 10 &&
        Object.keys(this.signupData.survey).every((v) => parseInt(v) >= 1 && parseInt(v) <= 10) &&
        Object.values(this.signupData.survey).every((v) => v >= 1 && v <= 4)) {
        return true;
      }
      return false;
    } else {
      return false;
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

        &.sub {
          opacity: 0.5;
        }
      }

      hr {
        margin: 1.5rem 0;
      }
    }
  }
}
</style>
