<template>
  <div id="signup-personal-data-wrapper">
    <div v-if="devMode"
         @click="devSkipInput">SKIP</div>

    <div class="signup-personal-data__content">
      <h1><strong>처음 오셨네요!🙌</strong><br />당신은 어떤 사람인가요?👂</h1>
      <h3>아래 정보를 입력하면 잘 맞을 듯한 익명 사용자와 편지를 주고받게 될 확률이 커져요.</h3>
      <h4>입력한 개인정보는 다른 사용자<small>(닉네임 제외)</small>나 외부 개인·업체에 제공되지 않아요. <a href="#">개인정보처리방침 (준비 중)</a></h4>

      <hr />

      <div class="signup-personal-data__content__form">
        <div class="signup-personal-data__content__form__nickname">
          <span>닉네임</span>

          <v-text-field v-model="nickname"
                        hint="연동 계정에서 가져온 이름을 자동으로 입력했어요. 지금 변경할 수도 있고, 나중에 프로필 페이지에서도 변경할 수 있어요."
                        single-line
                        persistent-hint
                        :hide-details="nicknameFieldHideHint"
                        @input="onNicknameFieldInput" />
        </div>

        <div class="signup-personal-data__content__form__gender">
          <span>성별</span>

          <v-select v-model="genderItemSelected"
                    :items="genderItems"
                    item-title="title"
                    item-value="value"
                    label="성별"
                    return-object
                    single-line />
        </div>

        <div class="signup-personal-data__content__form__age">
          <span>나이대</span>

          <v-select v-model="ageItemSelected"
                    :items="ageItems"
                    item-title="title"
                    item-value="value"
                    label="나이대"
                    return-object
                    single-line />
        </div>

        <div class="signup-personal-data__content__form__job">
          <span>직업 분류</span>

          <v-select v-model="jobItemSelected"
                    :items="jobItems"
                    item-title="title"
                    item-value="value"
                    label="직업 분류"
                    return-object
                    single-line />
        </div>
      </div>
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

  jobItemSelected = this.jobItems[this.jobItems.length - 1];

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
    }
  }
}
</style>
