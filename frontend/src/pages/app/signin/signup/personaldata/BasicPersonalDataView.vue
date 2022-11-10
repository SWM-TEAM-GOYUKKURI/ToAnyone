<template>
  <div id="signup-personal-data-basic">
    <div class="signup-personal-data-basic__form">
      <div class="signup-personal-data-basic__form__nickname">
        <span>닉네임</span>

        <v-text-field v-model="formData.nickname"
                      single-line
                      persistent-hint
                      :hint="!nicknameFieldHideHint ? '연동 계정에서 가져온 이름을 자동으로 입력했어요. 지금 변경할 수도 있고, 나중에 프로필 페이지에서도 변경할 수 있어요.' : ''"
                      :rules="[ v => !!v || '닉네임을 입력해주세요.', v => v.length >= 4 || '닉네임을 최소 4자 이상 입력해주세요.' ]"
                      @input="onNicknameFieldInput" />
      </div>

      <div class="signup-personal-data-basic__form__gender">
        <span>성별</span>

        <v-select v-model="formData.gender"
                  :items="genderItems"
                  item-title="title"
                  item-value="value"
                  label="성별"
                  single-line />
      </div>

      <div class="signup-personal-data-basic__form__age">
        <span>나이대</span>

        <v-select v-model="formData.age"
                  :items="ageItems"
                  item-title="title"
                  item-value="value"
                  label="나이대"
                  single-line />
      </div>

      <div class="signup-personal-data-basic__form__job">
        <span>직업 분류</span>

        <v-select v-model="formData.job"
                  :items="jobItems"
                  item-title="title"
                  item-value="value"
                  label="직업 분류"
                  single-line />
      </div>
    </div>

    <button class="signup-personal-data-basic__next-button button primary"
            :class="{ 'disabled': !formReqFulfilled }"
            @click="onNextButtonClick">다음 &nbsp;〉</button>
  </div>
</template>

<script lang="ts">
import { Vue } from "vue-class-component";
import { AGE_ITEMS, GENDER_ITEMS, JOB_ITEMS } from "@/data/profile-data";

export default class PersonalDataView extends Vue {
  readonly genderItems = GENDER_ITEMS;
  readonly ageItems = AGE_ITEMS;
  readonly jobItems = JOB_ITEMS;

  nicknameFieldHideHint = false;

  formData = {
    nickname: "",
    gender: this.genderItems[this.genderItems.length - 1].value,
    age: this.ageItems[this.ageItems.length - 1].value,
    job: this.jobItems[this.jobItems.length - 1].value,
  };

  get formReqFulfilled(): boolean {
    return (
      !!this.formData.nickname &&
      this.formData.nickname.length >= 4
    );
  }

  mounted(): void {
    // Fill nickname textfield with signin account username
    this.formData.nickname = this.$store.state.auth.userBasicInfo!.nickname;
  }

  onNicknameFieldInput(): void {
    this.nicknameFieldHideHint = true;
    console.log(this.formData);
  }

  onNextButtonClick(): void {
    if(this.formReqFulfilled) {
      this.$emit("submitBasic", this.formData);
    }
  }
}
</script>

<style lang="scss" scoped>
#signup-personal-data-basic {
  display: flex;
  flex-direction: column;
  padding-bottom: 2rem;

  .signup-personal-data-basic {
    &__form {
      display: flex;
      flex-wrap: wrap;
      width: 95%;
      margin: auto;

      & > div {
        margin: 1em 1em;
        width: calc(50% - (1em * 2));

        & > span {
          display: inline-block;
          margin: 0 0.5em 0.5em 0;
          font-size: 1.25em;
        }
      }
    }

    &__next-button {
      align-self: flex-end;
      padding-left: 3em;
      padding-right: 3em;
    }
  }
}
</style>
