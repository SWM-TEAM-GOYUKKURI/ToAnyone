<template>
  <div>
    <v-slide-x-reverse-transition>
      <div v-show="open"
           class="main-sidebar">
        <div class="main-sidebar__profile">
          <profile-image />
          <span class="nickname">{{ $store.state.auth.userBasicInfo.nickname }}</span>
        </div>

        <router-link :to="{ name: 'profile' }"><button class="button dark-background fill-width" @click.self="$emit('request-close', false)">프로필 보기</button></router-link>

        <div class="main-sidebar__unread">
          <span class="title">✉️ 읽지 않은 편지 <span v-if="unreadLetters.length >= 1" class="unread-count">{{ unreadLetters.length }}</span></span>

          <div class="main-sidebar__unread__contents">
            <div v-for="letter in unreadLetters"
                :key="letter.id">
              <router-link :to="{ name: 'letter-view', params: { letterId: letter.id } }">
                <button class="button narrow">
                  <span class="name">{{ letter.sentByMe ? letter.receiverName : letter.senderName }}</span>
                  <span class="content">{{ letter.content }}</span>
                </button>
              </router-link>
            </div>
          </div>
        </div>

        <div class="main-sidebar__controls">
          <button v-if="!hideCloseButton" class="button bg-transparent fill-width narrow" @click="$emit('request-close')"><v-icon size="small">mdi-close</v-icon> <span>닫기</span></button>
          <span v-if="!hideCloseButton"> · </span>
          <button class="button bg-transparent fill-width narrow" @click="onLogoutButtonClick"><v-icon size="small">mdi-logout</v-icon> <span>로그아웃</span></button>
        </div>
      </div>
    </v-slide-x-reverse-transition>

    <v-fade-transition>
      <div v-if="open"
           class="main-sidebar-back"
           :class="{ 'main-page': $route.name === 'main' }"
           @click.self.prevent.stop="$emit('request-close')" />
    </v-fade-transition>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { Prop } from "vue-property-decorator";
import { LetterInboxItem } from "@/interfaces/backend";
import ProfileImage from "./ProfileImage.vue";

interface LetterInboxItemExt extends LetterInboxItem {
  sentByMe: boolean,
}

@Options({
  components: {
    ProfileImage,
  },
})
export default class MainSidebar extends Vue {
  @Prop({ type: Boolean, default: false }) open!: boolean;
  @Prop({ type: Boolean, default: false }) hideCloseButton!: boolean;

  get unreadLetters(): LetterInboxItemExt[] {
    const unreadLetters = this.$store.state.user.unreadLetters.map<LetterInboxItemExt>((v) => {
      return { ...v, sentByMe: false };
    });
    const unreadSentLetters = this.$store.state.user.unreadSentLetters.map<LetterInboxItemExt>((v) => {
      return { ...v, sentByMe: true };
    });

    return [...unreadLetters, ...unreadSentLetters].sort((a, b) => {
      return (new Date(a.sendDate) >= new Date(b.sendDate)) ? -1 : 1;
    });
  }

  onLogoutButtonClick(): void {
    const choice = confirm("정말 로그아웃 하시겠어요?");
    if(choice) this.$router.push({ name: "logout" });
  }
}
</script>

<style lang="scss" scoped>
.main-sidebar {
  position: fixed;
  right: 0;
  top: 0;
  bottom: 0;
  display: flex;
  flex-direction: column;
  align-items: stretch;
  width: $main-sidebar-width;
  height: 100%;
  padding: 1.5em;
  background-color: $color-secondary;
  z-index: 1000;

  & > * {
    margin: 0.5em 0;
  }

  &__profile {
    display: flex;
    flex-direction: column;
    align-items: center;

    .nickname {
      font-size: 1.5em;
      margin-top: 0.5em;
    }
  }

  &__unread {
    flex-grow: 1;
    margin: 3em 0;
    height: 30%; // Brainless, hardcoded height but just works(tm)

    .title { font-size: 1.2em; }
    .unread-count {
      padding: 0.25em;
      margin-left: 0.25em;
      font-size: 0.8em;
      color: $color-dark;
      background-color: $color-primary;
      border-radius: 99999rem;
    }

    &__contents {
      overflow: hidden auto;
      height: 100%;
      margin-top: 0.5em;

      .button {
        flex-direction: column;
        align-items: stretch;
        text-align: left;
        width: 100%;
        margin: 0.5em 0;
        background-color: rgba($color-background, 0.4);

        overflow: hidden;
        text-overflow: ellipsis;

        & > * {
          margin: 0;
          text-overflow: ellipsis;
          overflow: hidden;
          text-align: left;
        }

        .name { font-size: 0.9em; opacity: 0.75; }
        .content { font-size: 1.1em; margin-top: 0.5em; }
      }
    }
  }

  &__controls {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
  }
}

.main-sidebar-back {
  position: fixed;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  z-index: 999;
  background-color: rgba($color-dark, 0.5);
  backdrop-filter: blur(2px);

  &.main-page {
    display: none;

    @media (max-width: $viewport-main-small-max-width) {
      display: initial;
    }
  }

  &.fade-transition-leave-active { pointer-events: none; }
}
</style>
