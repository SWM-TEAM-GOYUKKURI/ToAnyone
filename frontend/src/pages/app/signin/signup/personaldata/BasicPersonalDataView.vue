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

    <div class="signup-personal-data-basic__next-button animation-button"
         :class="{ 'disabled': !formReqFulfilled }"
         @click="onNextButtonClick">다음 &nbsp;〉</div>
  </div>
</template>

<script lang="ts">
import { Vue } from "vue-class-component";

export default class PersonalDataView extends Vue {
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

  jobItems = [
    {
      value: "student",
      title: "초·중·고등학생",
    },
    {
      value: "student_univ",
      title: "대학생",
    },
    {
      value: "management",
      title: "경영·관리",
    },
    {
      value: "office_tech",
      title: "사무·기술직",
    },
    {
      value: "professional",
      title: "전문직",
    },
    {
      value: "freelancer",
      title: "프리랜서",
    },
    {
      value: "simple_labor",
      title: "단순노무 (기능·작업 등)",
    },
    {
      value: "seller",
      title: "판매·영업·마케팅",
    },
    {
      value: "service",
      title: "서비스",
    },
    {
      value: "self_emp",
      title: "자영업",
    },
    {
      value: "homemaker",
      title: "전업주부",
    },
    {
      value: "noemp_etc",
      title: "무직·기타",
    },
    {
      value: "notselected",
      title: "선택하지 않음",
    },
  ];

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
      cursor: pointer;
      display: inline-block;
      align-self: flex-end;
      padding: 1em 2em;
      font-size: 1.1em;
      background: rgba($color-secondary, 0.8);
      border-radius: 99999px;
      box-shadow: 0 0.33rem 0.5rem rgba(#000, 0.25);

      &.disabled {
        cursor: not-allowed;
        pointer-events: none;
        box-shadow: none;
        opacity: 0.5;
      }
    }
  }
}
</style>
