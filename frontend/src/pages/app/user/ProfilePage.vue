<template>
  <div id="profile-page-wrapper">
    <div class="profile__my-area">
      <div class="profile__my-area__image-nickname">
        <profile-image :srcUrl="tempProfileImage" />

        <div class="profile__my-area__image-nickname__info">
          <span class="nickname">{{ $store.state.auth.userBasicInfo.nickname }}</span>
          <span class="info"
                title="이 정보는 다른 사람들에게 보여지지 않아요!">(나이대) / (성별)</span>
        </div>
      </div>

      <div class="profile__my-area__achievements">
        <div class="profile__my-area__achievements__item">
          <span class="title">달성한 업적</span>
          <span class="content">nn개 <small>/ nn개</small></span>
        </div>
        <div class="profile__my-area__achievements__item">
          <span class="title">총 편지 작성 개수</span>
          <span class="content">nn통</span>
        </div>
      </div>
    </div>

    <!-- ↓ TODO: styling -->
    <router-link :to="{ name: 'profile-edit' }" style="color: #FFFFAA">(프로필 수정 버튼)</router-link>

    <h1>통계</h1>
    <div>(icon) nnnn년 nn월 nn일<br />에 마음을 나누기 시작했어요</div>
    <div>(icon) 총 nn일<br />To. Anyone을 찾아와주셨어요</div>
    <div>(icon) nn통<br />의 편지를 보냈어요</div>
    <div>(icon) nn통<br />의 편지를 받았어요</div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import ProfileImage from "@/components/app/global/ProfileImage.vue";

@Options({
  components: {
    ProfileImage,
  },
})
export default class ProfilePage extends Vue {
  private tempProfileImage = "https://picsum.photos/seed/toanyone/300";

  mounted(): void {
    // TODO: request to backend for user profile data
  }
}
</script>

<style lang="scss">
#profile-page-wrapper {
  margin: auto;
  width: 80vw;

  .profile {
    &__my-area {
      display: flex;
      justify-content: space-between;
      padding: 1rem 2rem 1rem 1rem;
      margin: 1rem;
      background: rgba($color-secondary, 0.5);
      border-radius: 10rem 2rem 2rem 10rem;

      & > div {
        display: flex;
      }

      &__image-nickname {
        display: flex;
        align-items: center;

        &__info {
          cursor: help;
          display: flex;
          flex-direction: column;
          justify-content: center;
          align-items: flex-start;
          margin-left: 1.5rem;
          line-height: 1.5;

          .nickname {
            font-size: 2em;
            font-weight: 700;
          }

          .info {
            font-size: 1.25em;
          }
        }
      }

      &__achievements {
        &__item {
          display: flex;
          flex-direction: column;
          justify-content: center;
          align-items: center;
          padding: 0 0.75em;
          border-left: solid currentColor 1px;
          border-right: solid currentColor 1px;

          & > * {
            margin: 0.25em 0;
          }

          .title {
            font-size: 1.25em;
          }

          .content {
            font-size: 1.5em;
            font-weight: 700;

            small {
              font-size: 0.75em;
              font-weight: 400;
            }
          }
        }
      }
    }
  }
}
</style>
