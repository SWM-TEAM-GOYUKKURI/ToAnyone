<template>
  <div id="home-page">
    <div class="home-page__headlines">
      <div class="home-page__headlines__first">{{ $store.state.auth.userBasicInfo.nickname }}님,<br />반가워요👋</div>
      <div class="home-page__headlines__second">지금 느끼는 감정을 다른 사람과 나눠보세요.</div>
    </div>

    <div class="home-page__links">
      <router-link :to="{ name: 'letter-write' }"
                   class="button primary">
        <img src="#" alt="편지 쓰기 아이콘" />

        <div class="content">
          <span class="title">편지 쓰기</span>
          <span class="desc">익명의 사용자와 나누고픈 이야기를 쓰고 보내보세요.</span>
        </div>

        <img src="#" alt="편지 쓰기 링크" />
      </router-link>

      <router-link :to="{ name: 'letter-box' }"
                   class="button">
        <img src="#" alt="편지 보관함 아이콘" />

        <div class="content">
          <span class="title">편지 보관함</span>
          <span class="desc">주고받았던 편지를 확인해보세요.</span>
        </div>

        <img src="#" alt="편지 쓰기 링크" />
      </router-link>

      <router-link :to="{ name: 'item-store' }"
                   class="button">
        <img src="#" alt="아이템 상점 아이콘" />

        <div class="content">
          <span class="title">아이템 상점</span>
          <span class="desc">편지를 꾸미기 위한 스티커, 폰트, 편지지 등을 포인트로 구매할 수 있어요.</span>
        </div>

        <img src="#" alt="아이템 상점 링크" />
      </router-link>
    </div>
  </div>
</template>

<script lang="ts">
import { Vue } from "vue-class-component";

export default class MainPage extends Vue { }
</script>

<style lang="scss">
#home-page {
  padding: 0 10vw;

  .home-page {
    &__headlines {
      padding-top: 2rem;
      font-size: 3rem;
      font-weight: 300;
      line-height: 1.5;

      &__first {
        font-weight: 400;
      }

      &__second {
        font-size: 0.66em;
      }
    }

    &__links {
      margin-top: 3rem;

      a.button {
        flex-direction: row;
        justify-content: space-between;
        margin: 1em 2em;
        padding-top: 1.5em;
        padding-bottom: 1.5em;

        .content {
          display: flex;
          flex-grow: 1;
          flex-direction: column;
          align-items: flex-start;
          justify-content: center;

          .title { font-weight: bold; font-size: 1.75em; }

          & > * { margin: 0.5rem 0; }
        }
      }
    }
  }
}
</style>
