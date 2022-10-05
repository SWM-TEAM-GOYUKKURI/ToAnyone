<template>
  <div id="signup-personal-data-survey">
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
  mentalHealthData = {};

  onBackButtonClick(): void {
    this.$emit("backSurvey");
  }

  onDoneButtonClick(): void {
    this.$emit("doneSurvey", this.mentalHealthData);
  }
}
</script>

<style lang="scss" scoped>
#signup-personal-data-survey {
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
