<template>
  <div id="signup-personal-data-survey">
    <div class="signup-personal-data-survey__content">
      <div v-for="(item, index) in mentalHealthSurveyItems"
           :key="index + 1"
           class="signup-personal-data-survey__content__item">
        <span class="question-title">{{ index + 1 }}. {{ item.question }}</span>

        <v-radio-group v-model="mentalHealthData[index + 1]">
          <v-radio v-for="(value, index) in item.selections"
                   :key="index + 1"
                   :label="value"
                   :value="index + 1" />
        </v-radio-group>
      </div>
    </div>

    <div class="signup-personal-data-survey__buttons">
      <button class="signup-personal-data-survey__buttons__back button"
           @click="onBackButtonClick">〈&nbsp; 뒤로</button>

      <button class="signup-personal-data-survey__buttons__done button primary"
           :disabled="!formReqFulfilled"
           @click="onDoneButtonClick">완료 &nbsp;<v-icon>mdi-check</v-icon></button>
    </div>
  </div>
</template>

<script lang="ts">
import { Vue } from "vue-class-component";

export default class MentalHealthSurveyView extends Vue {
  mentalHealthSurveyItems = [
    {
      question: "나는 즐겨오던 것들을 현재도 즐기고 있다.",
      selections: ["똑같이 즐긴다", "많이 즐기지는 못한다", "단지 조금만 즐긴다", "거의 즐기지 못한다"],
    },
    {
      question: "나는 사물을 긍정적으로 보고 잘 웃는다.",
      selections: ["항상 그렇다", "현재는 그다지 그렇지 않다", "거의 그렇지 않다", "전혀 아니다"],
    },
    {
      question: "마음 속에 걱정스러운 생각이 든다.",
      selections: ["거의 그렇지 않다", "가끔 그렇다", "자주 그렇다", "항상 그렇다"],
    },
    {
      question: "나는 기분이 좋다.",
      selections: ["항상 그렇다", "자주 그렇다", "가끔 그렇다", "전혀 그렇지 않다"],
    },
    {
      question: "나는 편하게 긴장을 풀 수 있다.",
      selections: ["항상 그렇다", "대부분 그렇다", "대부분 그렇지 않다", "전혀 그렇지 않다"],
    },
    {
      question: "나는 평소에 활발하다.",
      selections: ["거의 항상 그렇다", "자주 그렇다", "가끔 그렇다", "전혀 아니다"],
    },
    {
      question: "나의 마음은 안정되어 있다.",
      selections: ["매우 자주 그렇다", "자주 그렇다", "가끔 그렇다", "전혀 아니다"],
    },
    {
      question: "나는 나의 외모에 관심이 있는 편이다.",
      selections: ["매우 그렇다", "그렇다", "그렇지 않다", "전혀 그렇지 않다"],
    },
    {
      question: "나는 무언가를 기다리는 것이 즐겁다.",
      selections: ["이전에 그랬던 것처럼 그렇다", "그렇다", "전보다 덜 즐겁다", "전혀 그렇지 않다"],
    },
    {
      question: "나는 문화생활(책, 노래, 영화 등)을 즐긴다.",
      selections: ["자주 즐긴다", "가끔 즐긴다", "거의 못 즐긴다", "전혀 못 즐긴다"],
    },
  ];

  mentalHealthData = {};

  get formReqFulfilled(): boolean {
    try {
      return (Object.keys(this.mentalHealthData).length === 10) &&
            (Object.keys(this.mentalHealthData).every((p) => parseInt(p) >= 1 && parseInt(p) <= 10)) &&
            (Object.values<number>(this.mentalHealthData).every((p) => p >= 1 && p <= 4));
    } catch(e) {
      return false;
    }
  }

  onBackButtonClick(): void {
    this.$emit("backSurvey");
  }

  onDoneButtonClick(): void {
    if(this.formReqFulfilled) {
      this.$emit("submitSurvey", this.mentalHealthData);
    }
  }
}
</script>

<style lang="scss" scoped>
#signup-personal-data-survey {
  padding-bottom: 2rem;

  .signup-personal-data-survey {

    &__content {
      display: flex;
      flex-direction: row;
      flex-wrap: wrap;

      &__item {
        width: calc(50% - (1em * 2));
        margin: 1em;

        .question-title {
          font-size: 1.25em;
        }
      }
    }

    &__buttons {
      display: flex;
      align-items: center;
      justify-content: space-between;

      & > .button {
        padding-left: 3em;
        padding-right: 3em;
      }
    }
  }
}
</style>
