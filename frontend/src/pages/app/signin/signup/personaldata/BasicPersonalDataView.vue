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
import { UserProfileAge, UserProfileGender, UserProfileJob } from "@/interfaces/backend";
import { Vue } from "vue-class-component";

export default class PersonalDataView extends Vue {
  readonly genderItems = Object.freeze([
    {
      value: UserProfileGender.MALE,
      title: "남성",
    }, {
      value: UserProfileGender.FEMALE,
      title: "여성",
    }, {
      value: UserProfileGender.NON_BINARY,
      title: "논바이너리 / 기타",
    }, {
      value: UserProfileGender.NOT_SELECTED,
      title: "선택하지 않음",
    },
  ]);

  readonly ageItems = Object.freeze([
    {
      value: UserProfileAge.TEN,
      title: "10대",
    }, {
      value: UserProfileAge.TWENTY,
      title: "20대",
    }, {
      value: UserProfileAge.THIRTY,
      title: "30대",
    }, {
      value: UserProfileAge.FORTY,
      title: "40대",
    }, {
      value: UserProfileAge.FIFTY,
      title: "50대 이상",
    }, {
      value: UserProfileAge.NOT_SELECTED,
      title: "선택하지 않음",
    },
  ]);

  readonly jobItems = Object.freeze([
    {
      value: UserProfileJob.STUDENT,
      title: "초·중·고등학생",
    }, {
      value: UserProfileJob.STUDENT_UNIV,
      title: "대학생",
    }, {
      value: UserProfileJob.MANAGEMENT,
      title: "경영·관리",
    }, {
      value: UserProfileJob.OFFICE_TECH,
      title: "사무·기술직",
    }, {
      value: UserProfileJob.PROFESSIONAL,
      title: "전문직",
    }, {
      value: UserProfileJob.FREELANCER,
      title: "프리랜서",
    }, {
      value: UserProfileJob.SIMPLE_LABOR,
      title: "단순노무 (기능·작업 등)",
    }, {
      value: UserProfileJob.SELLER,
      title: "판매·영업·마케팅",
    }, {
      value: UserProfileJob.SERVICE,
      title: "서비스",
    }, {
      value: UserProfileJob.SELF_EMP,
      title: "자영업",
    }, {
      value: UserProfileJob.HOME_MAKER,
      title: "전업주부",
    }, {
      value: UserProfileJob.NOT_WORKING,
      title: "무직·기타",
    }, {
      value: UserProfileJob.NOT_SELECTED,
      title: "선택하지 않음",
    },
  ]);

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
