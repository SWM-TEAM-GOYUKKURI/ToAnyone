<template>
  <div id="signup-personal-data-wrapper">
    <div v-if="devMode"
         @click="devSkipInput">SKIP</div>

    <div class="signup-personal-data__nickname">
      <span>닉네임</span>

      <v-text-field v-model="nickname"
                    hint="연동 계정에서 가져온 이름을 자동으로 입력했어요. 원한다면 지금 변경할 수 있고, 나중에 프로필 페이지에서도 변경할 수 있어요."
                    density="compact"
                    single-line
                    persistent-hint
                    :hide-details="nicknameFieldHideHint"
                    @input="onNicknameFieldInput" />
    </div>

    <div class="signup-personal-data__gender">
      <span>성별</span>

      <v-select v-model="genderItemSelected"
                :items="genderItems"
                item-title="title"
                item-value="value"
                label="성별"
                density="compact"
                return-object
                single-line />
    </div>

    <div class="signup-personal-data__age">
      <span>나이대</span>

      <v-select v-model="ageItemSelected"
                :items="ageItems"
                item-title="title"
                item-value="value"
                label="나이대"
                density="compact"
                return-object
                single-line />
    </div>
  </div>
</template>

<script lang="ts">
import { Vue } from "vue-class-component";

export default class SignupPersonalDataPage extends Vue {
  nickname = "";
  nicknameFieldHideHint = false;

  genderItems = [
    {
      value: "male",
      title: "남성",
    }, {
      value: "female",
      title: "여성",
    }, {
      value: "nonbinary",
      title: "논바이너리 / 기타",
    }, {
      value: "notselected",
      title: "선택하지 않음",
    },
  ];

  genderItemSelected = this.genderItems[this.genderItems.length - 1];

  ageItems = [
    {
      value: "ten",
      title: "10대",
    },
    {
      value: "twenty",
      title: "20대",
    },
    {
      value: "thirty",
      title: "30대",
    },
    {
      value: "fourty",
      title: "40대",
    },
    {
      value: "fifty",
      title: "50대 이상",
    },
    {
      value: "notselected",
      title: "선택하지 않음",
    },
  ];

  ageItemSelected = this.ageItems[this.ageItems.length - 1];

  get devMode(): boolean {
    return process.env.VUE_APP_DEVMODE === "true";
  }

  mounted(): void {
    this.nickname = this.$store.state.auth.userBasicInfo!.nickname;
  }

  onNicknameFieldInput(): void {
    this.nicknameFieldHideHint = true;
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
}
</script>
