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
        <h1><v-icon>mdi-chart-timeline-variant</v-icon> 통계</h1>

        <div class="button narrow"><v-icon>mdi-home-heart</v-icon> <span>To. Anyone에 <span class="t-primary">{{ signupDateFormatted }}</span>에 가입했어요.</span></div>
        <div class="button narrow"><v-icon>mdi-login-variant</v-icon> <span>총 <span class="t-primary">{{ tempData.signinDays }}일</span> 동안 To. Anyone을 찾아왔어요.</span></div>
        <div class="button narrow"><v-icon>mdi-email-send</v-icon> <span>지금까지 <span class="t-primary">{{ tempData.sentLetterCount }}통</span>의 편지를 보냈어요.</span></div>
        <div class="button narrow"><v-icon>mdi-email-receive</v-icon> <span>지금까지 <span class="t-primary">{{ tempData.receivedLetterCount }}통</span>의 편지를 받았어요.</span></div>
      </div>

      <div class="profile_statistics__achievements">
        <h1><v-icon>mdi-trophy-variant</v-icon> 업적</h1>
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

    signupDate: new Date("2022-06-01"),
    signinDays: 16,
    sentLetterCount: 42,
    receivedLetterCount: 51,
  };

  get signupDateFormatted(): string {
    return `${this.tempData.signupDate.getFullYear()}년 ${this.tempData.signupDate.getMonth() + 1}월 ${this.tempData.signupDate.getDate()}일`;
  }

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
    flex-direction: column;
    align-items: stretch;
    justify-content: flex-start;
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
      width: max-content;

      @media (max-width: $viewport-small-max-width) {
        width: 100%;
      }

      & > * { margin: 1em 0; }

      &__statistics {
        .button {
          margin: 0.5em 0;
        }
      }
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
