<template>
  <div id="profile-page-wrapper">
    <div class="profile__my-area">
      <div class="profile__my-area__me">
        <profile-image :srcUrl="tempData.profileImageUrl"
                       size="large" />

        <div class="profile__my-area__me__info">
          <span class="nickname"><strong>{{ $store.state.auth.userBasicInfo.nickname }}</strong></span>
          <hr />
          <span class="info"><strong>{{ tempData.age }}</strong> / <strong>{{ tempData.gender }}</strong></span>
          <span class="info">보유 포인트 <strong>{{ tempData.points }}P</strong></span>
          <span class="info">업적 달성 <strong>{{ tempData.achievementsCount }}개</strong></span>
        </div>

        <router-link :to="{ name: 'profile-edit' }"><button class="profile__my-area__me__profile-edit button"><v-icon>mdi-account-edit</v-icon> <span>프로필 수정</span></button></router-link>
      </div>
    </div>

    <div class="profile__statistics">
      <div class="profile__statistics__statistics">
        <h1>통계</h1>
        <div>(icon) nnnn년 nn월 nn일<br />에 마음을 나누기 시작했어요</div>
        <div>(icon) 총 nn일<br />To. Anyone을 찾아와주셨어요</div>
        <div>(icon) nn통<br />의 편지를 보냈어요</div>
        <div>(icon) nn통<br />의 편지를 받았어요</div>
      </div>

      <div class="profile_statistics__achievements">
        <h1>업적</h1>
      </div>
    </div>

    <router-view v-slot="{ Component }">
      <v-slide-y-transition>
        <in-app-dialog v-if="$route.name === 'profile-edit'"
                      id="profile-page-edit-dialog"
                      :fullscreenOnVPSmall="true">
          <component :is="Component" />
        </in-app-dialog>
      </v-slide-y-transition>
    </router-view>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import InAppDialog from "@/components/InAppDialog.vue";
import ProfileImage from "@/components/app/global/ProfileImage.vue";

@Options({
  components: {
    InAppDialog,
    ProfileImage,
  },
})
export default class ProfilePage extends Vue {
  tempData = {
    profileImageUrl: "https://picsum.photos/seed/toanyone/300",
    age: "00대",
    gender: "남성",
    points: 3000,
    achievementsCount: 5,
  };

  mounted(): void {
    // TODO: request to backend for user profile data
  }
}
</script>

<style lang="scss">
#profile-page-wrapper {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: flex-start;
  margin: auto;
  width: 80vw;
  min-height: calc(100vh - $app-navbar-height);

  @media (max-width: $viewport-small-max-width) {
    & {
      flex-direction: column;
      align-items: center;
      justify-content: flex-start;
    }
  }

  .profile {
    &__my-area {
      position: sticky;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      height: 100%;
      padding: 1em;
      margin: 1em 2em;

      &__me {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;

        &__info {
          display: flex;
          flex-direction: column;
          justify-content: center;
          align-items: center;
          line-height: 1.5;

          & > * {
            text-align: center;
            margin: 0.33em 0;
          }

          hr { width: 100%; }

          .nickname {
            font-size: 2em;
            font-weight: 700;
            margin: 0.33em 0 0 0;
          }

          .info {
            font-size: 1.25em;
          }
        }

        &__profile-edit {
          margin-top: 1em;
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
            text-align: center;
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

    &__statistics {
      display: flex;
      flex-grow: 1;
      flex-direction: column;
    }
  }
}

#profile-page-edit-view {
  overflow: auto;
  display: flex;
  align-items: flex-start;
  justify-content: center;
  position: fixed;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  z-index: 100;
  background: rgba(#000, 0.5);

  & > div {
    display: flex;
    align-items: center;
    min-height: 100%;
  }
}
</style>
