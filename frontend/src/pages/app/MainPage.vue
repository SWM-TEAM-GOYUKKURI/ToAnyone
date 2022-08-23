<template>
  <div id="home-page">
    <div class="home-page__headlines">
      <div class="home-page__headlines__first">반가워요👋</div>
      <div class="home-page__headlines__second">오늘 느꼈던 감정을<br>다른 사람과 나눠보세요.</div>
    </div>
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
}
</style>
