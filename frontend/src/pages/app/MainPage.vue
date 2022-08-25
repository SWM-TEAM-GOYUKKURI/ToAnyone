<template>
  <div id="home-page">
    <div class="home-page__headlines">
      <div class="home-page__headlines__first">반가워요👋</div>
      <div class="home-page__headlines__second">오늘 느꼈던 감정을<br>다른 사람과 나눠보세요.</div>

      <!-- temp -->
      <router-link :to="{ name: 'letter-write' }"
                   class="temp-write-letter-button animation-button">익명 편지 쓰기 <v-icon>mdi-chevron-right</v-icon></router-link>
      <router-link :to="{ name: 'letter-box' }"
                   class="temp-letter-box-button animation-button"><v-icon>mdi-email-open-multiple</v-icon> 편지 보관함</router-link>
    </div>

    <div style="margin-top: 5rem">개발 중입니다.</div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";

@Options({
  components: {
  },
})
export default class MainPage extends Vue {}
</script>

<style lang="scss">
#home-page {
  padding: 0 10vw;

  .home-page {
    &__headlines {
      padding-top: 2rem;
      font-size: 4rem;
      font-weight: 300;
      line-height: 1.5;

      &__first {
        font-weight: 400;
      }

      &__second {
        font-size: 0.8em;
      }
    }
  }

  .temp-write-letter-button,
  .temp-letter-box-button {
    display: inline-flex;
    align-items: center;
    padding: 1rem 1.5rem;
    font-size: 1.5rem;
    border-radius: 999em;
    text-decoration: none;
    color: currentColor;
    box-shadow: 0 0.33em 0.5em rgba(24, 24, 24, 0.33);
    margin: 0 0.5em;

    i {
      font-size: 1em !important;
    }
  }

  .temp-write-letter-button {
    background-color: $color-secondary;
    i {
      margin-left: 0.5em;
    }
  }

  .temp-letter-box-button {
    background-color: $color-tertiary;
    i {
      margin-right: 0.5em;
    }
  }
}
</style>
