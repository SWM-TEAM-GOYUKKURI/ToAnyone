<template>
  <div id="home-page">
    <div class="home-page__headlines">
      <div class="home-page__headlines__first">{{ $store.state.auth.userBasicInfo.nickname }}님, 반가워요👋</div>
      <div class="home-page__headlines__second">지금 느끼는 감정을 다른 사람과 나눠보세요.</div>
    </div>

    <div class="home-page__links">
      <router-link :to="{ name: 'letter-write' }"
                   class="button primary">
        <img class="icon" :src="getAssetPath('icon/ico_write@2x.png')" alt="편지 쓰기 아이콘" />

        <div class="content">
          <span class="title">편지 쓰기</span>
          <span class="desc">익명의 사용자와 나누고픈 이야기를 쓰고 보내보세요.</span>
        </div>

        <span class="link-icon"><v-icon size="xx-large">mdi-chevron-right</v-icon></span>
      </router-link>

      <router-link :to="{ name: 'letter-box' }"
                   class="button">
        <img class="icon" :src="getAssetPath('icon/ico_box@2x.png')" alt="편지 보관함 아이콘" />

        <div class="content">
          <span class="title">편지 보관함</span>
          <span class="desc">주고받았던 편지를 확인해보세요.</span>
        </div>

        <span class="link-icon"><v-icon size="xx-large">mdi-chevron-right</v-icon></span>
      </router-link>

      <router-link :to="{ name: 'item-store' }"
                   class="button">
        <img class="icon" :src="getAssetPath('icon/ico_stars@2x.png')" alt="아이템 상점 아이콘" />

        <div class="content">
          <span class="title">아이템 상점</span>
          <span class="desc">편지를 꾸미기 위한 스티커, 폰트, 편지지 등을 포인트로 구매할 수 있어요.</span>
        </div>

        <span class="link-icon"><v-icon size="xx-large">mdi-chevron-right</v-icon></span>
      </router-link>
    </div>
  </div>
</template>

<script lang="ts">
import { Vue } from "vue-class-component";
import { getAssetPath } from "@/util/path-transform";

export default class MainPage extends Vue {
  getAssetPath = getAssetPath;

  async mounted() {
    console.log(await this.$api.getInbox());
  }
}
</script>

<style lang="scss">
#home-page {
  padding: 0 calc($main-sidebar-width + 6vw) 3vh 6vw;

  @media (max-width: $viewport-main-small-max-width) {
    padding: 0 5vw 3vh 5vw;
  }

  .home-page {
    &__headlines {
      padding-top: 2rem;
      font-size: 3rem;
      font-weight: 300;
      line-height: 1.5;

      &__first {
        font-weight: 400;
        word-break: keep-all;
      }

      &__second {
        font-size: 0.66em;
      }

      @media (max-width: $viewport-small-max-width) {
        font-size: 2.25rem;
      }
    }

    &__links {
      margin-top: 3rem;

      a.button {
        flex-direction: row;
        justify-content: space-between;
        margin: 1em 10%;
        padding-top: 1.5em;
        padding-bottom: 1.5em;

        img {
          width: 6em;
          height: 6em;
          margin-right: 1.5em;
        }

        &.primary {
          background-color: fade-out($color-primary, 0.1);  // Slightly darker color
        }

        .content {
          display: flex;
          flex-grow: 1;
          flex-direction: column;
          align-items: flex-start;
          justify-content: center;

          & > * {
            margin: 0.5rem 0;
            text-align: left;
          }

          .title { font-weight: bold; font-size: 1.75em; }
        }

        @media (max-width: $viewport-small-max-width) {
          flex-direction: column;
          margin: 1em 1em;

          img {
            width: 3em;
            height: 3em;
            margin-right: 0;
            margin-bottom: 1em;
          }

          .content {
            align-items: center;

            & > * { margin: 0; }
            .title { font-size: 1.33em; }
            .desc { display: none !important; }
          }

          .link-icon { display: none; }
        }
      }
    }
  }
}
</style>
