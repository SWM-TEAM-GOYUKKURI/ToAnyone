<template>
  <div id="profile-page-wrapper">
    <div class="profile__my-area">
      <div class="profile__my-area__me">
        <profile-image :srcUrl="builtData.profileImageUrl"
                       size="large" />

        <div class="profile__my-area__me__info">
          <span class="nickname"><strong>{{ $store.state.user.user.nickname }}</strong></span>
          <hr />
          <span class="info">
            <strong v-if="builtData.age !== UserProfileAgeName['NOT_SELECTED']">{{ builtData.age }}</strong>
            <span v-if="builtData.age !== UserProfileAgeName['NOT_SELECTED'] && builtData.gender !== UserProfileGenderName['NOT_SELECTED']"> &bull; </span>
            <strong v-if="builtData.gender !== UserProfileGenderName['NOT_SELECTED']">{{ builtData.gender }}</strong>
          </span>
          <span class="info">보유 포인트 <strong>{{ builtData.points }}P</strong> <router-link :to="{ name: 'point-help' }" title="포인트란?"><v-icon size="x-small">mdi-help-circle-outline</v-icon></router-link></span>
          <span class="info">업적 달성 <strong>{{ builtData.achievementsCount }}개</strong></span>
        </div>

        <router-link :to="{ name: 'profile-edit' }"><button class="profile__my-area__me__profile-edit button"><v-icon>mdi-account-edit</v-icon> <span>프로필 수정</span></button></router-link>
      </div>
    </div>

    <div class="profile__statistics">
      <div class="profile__statistics__statistics">
        <h1><v-icon>mdi-chart-timeline-variant</v-icon> 통계</h1>

        <div class="button narrow"><v-icon>mdi-home-heart</v-icon> <span>To. Anyone에 <span class="t-primary">{{ signupDateString }}</span>에 가입했어요.</span></div>
        <div class="button narrow"><v-icon>mdi-login-variant</v-icon> <span>총 <span class="t-primary">{{ builtData.signinDays }}일</span> 동안 To. Anyone을 찾아왔어요.</span></div>
        <div class="button narrow"><v-icon>mdi-email-send</v-icon> <span>지금까지 <span class="t-primary">{{ builtData.sentLetterCount }}통</span>의 편지를 보냈어요.</span></div>
        <div class="button narrow"><v-icon>mdi-email-receive</v-icon> <span>지금까지 <span class="t-primary">{{ builtData.receivedLetterCount }}통</span>의 편지를 받았어요.</span></div>
      </div>

      <div class="profile__statistics__achievements">
        <h1><v-icon>mdi-trophy-variant</v-icon> 업적</h1>

        <div v-for="(achiv, index) in achivements"
             :key="index"
             class="button item"
             :class="{ disabled: !builtData.achivements[index] }">
          <div class="content">
            <span class="title">{{ achiv.name }}</span>
            <span class="desc">{{ achiv.desc }}</span>
          </div>

          <v-icon v-if="builtData.achivements[index]" class="done">mdi-check</v-icon>
        </div>
      </div>
    </div>

    <router-view v-slot="{ Component }">
      <v-slide-y-transition>
        <in-app-dialog v-if="$route.name === 'profile-edit' || $route.name === 'point-help'"
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
import Achievements from "@/data/json/achievements.json";
import { UserProfileAgeName, UserProfileGenderName } from "@/data/profile-data";

interface ProfilePageData {
  profileImageUrl?: string,
  age: UserProfileAgeName,
  gender: UserProfileGenderName,
  points: number,
  achievementsCount: number,
  signupDate: Date,
  signinDays: number,
  sentLetterCount: number,
  receivedLetterCount: number,
  achivements: Record<number, boolean>,
}

@Options({
  components: {
    InAppDialog,
    ProfileImage,
  },
})
export default class ProfilePage extends Vue {
  achivements = Achievements;
  UserProfileAgeName = UserProfileAgeName;
  UserProfileGenderName = UserProfileGenderName;

  builtData: ProfilePageData = {
    profileImageUrl: "https://picsum.photos/seed/toanyone/300",
    age: UserProfileAgeName.NOT_SELECTED,
    gender: UserProfileGenderName.NOT_SELECTED,
    points: 0,
    achievementsCount: 0,
    signupDate: new Date("2000-01-01"),
    signinDays: 0,
    sentLetterCount: 0,
    receivedLetterCount: 0,
    achivements: { },
  };

  get signupDateString(): string {
    return `${this.builtData.signupDate.getFullYear()}년 ${this.builtData.signupDate.getMonth() + 1}월 ${this.builtData.signupDate.getDate()}일`;
  }

  async mounted() {
    // Request user info data and register to store
    const response = await this.$api.getUserInfo();

    if(response.data) {
      this.$store.commit("user/updateUserInfo", response.data);
    } else {
      alert("사용자 정보를 불러오는 중 오류: " + response.statusCode);
      return;
    }

    // Get achievements data
    const achivResponse = await this.$api.getUserAchievementInfo();

    if(!achivResponse.data) {
      alert("사용자 업적 정보를 불러오는 중 오류: " + achivResponse.statusCode);
      return;
    }

    const achivList: Record<number, boolean> = {};
    for(const achiv of achivResponse.data) {
      achivList[achiv.level] = true;
    }

    // Build data to display
    if(response.data) {
      this.builtData = {
        ...this.builtData,
        age: UserProfileAgeName[response.data.profile.age],
        gender: UserProfileGenderName[response.data.profile.gender],
        points: response.data.point,
        achievementsCount: response.data.achievementCountValue,
        signupDate: new Date(response.data.createdAt),
        signinDays: response.data.loginCount,
        sentLetterCount: response.data.sendLetterCountValue,
        receivedLetterCount: response.data.receiveCount,
        achivements: achivList,
      };
    }
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
  min-height: calc(100vh - var(--app-navbar-height));

  @media (max-width: $viewport-small-max-width) {
    flex-direction: column;
    align-items: stretch;
    justify-content: flex-start;

    .profile {
      &__my-area {
        position: relative !important;
        top: 0 !important;
      }
    }
  }

  .profile {
    &__my-area {
      position: sticky;
      top: calc(1em + var(--app-navbar-height));
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

      &__achievements {
        .button {
          position: relative;
          margin: 0.5em 0;
          overflow: hidden;

          .content {
            display: inline-flex;
            flex-direction: column;
            line-height: 1.5;

            .title { font-size: 1.1em; }
            .desc { font-size: 0.85em; opacity: 0.8; }
          }

          .done {
            position: absolute;
            right: -0.3em;
            bottom: -0.3em;
            font-size: 10em;
            opacity: 0.33;
          }
        }
      }
    }
  }
}
</style>
