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
      <div class="signup-personal-data-survey__buttons__back animation-button"
          @click="onBackButtonClick">〈&nbsp; 뒤로</div>

      <div class="signup-personal-data-survey__buttons__done animation-button"
          @click="onDoneButtonClick">확인 &nbsp;✓</div>
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
    { // 10
      question: "나는 나의 외모에 관심이 많다.",
      selections: ["", "", "", ""],
    },
    { // 12
      question: "...",
      selections: ["", "", "", ""],
    },
    { // 14
      question: "...",
      selections: ["", "", "", ""],
    },
  ];

  mentalHealthData = {};

  onBackButtonClick(): void {
    this.$emit("backSurvey");
  }

  onDoneButtonClick(): void {
    this.$emit("submitSurvey", this.mentalHealthData);
  }
}
</script>

<style lang="scss" scoped>
#signup-personal-data-survey {
  padding-bottom: 2rem;

  .signup-personal-data-survey {
    @mixin button-style {
      cursor: pointer;
      display: inline-block;
      padding: 1em 2em;
      font-size: 1.1em;
      border-radius: 99999px;
      box-shadow: 0 0.33rem 0.5rem rgba(#000, 0.25);

      &.disabled {
        cursor: not-allowed;
        pointer-events: none;
        box-shadow: none;
        opacity: 0.5;
      }
    }

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

      &__back {
        @include button-style;
        background: rgba($color-tertiary, 0.8);
      }

      &__done {
        @include button-style;
        background: rgba($color-secondary, 0.8);
      }
    }
  }
}
</style>
