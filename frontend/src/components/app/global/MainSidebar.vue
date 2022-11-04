<template>
  <div>
    <v-slide-x-reverse-transition>
      <div v-show="open"
           class="main-sidebar">
        <div class="main-sidebar__profile">
          <profile-image />
          <span class="nickname">{{ $store.state.auth.userBasicInfo.nickname }}</span>
        </div>

        <router-link :to="{ name: 'profile' }"><button class="button dark-background fill-width">프로필 보기</button></router-link>

        <div class="main-sidebar__unread">
          <span class="title">✉️ 읽지 않은 편지 <span v-if="unreadCount >= 1" class="unread-count">{{ unreadCount }}</span></span>
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
import { LetterInboxItemList } from "@/interfaces/backend";
import ProfileImage from "./ProfileImage.vue";

@Options({
  components: {
    ProfileImage,
  },
})
export default class MainSidebar extends Vue {
  @Prop({ type: Boolean, default: false }) open!: boolean;
  @Prop({ type: Boolean, default: false }) hideCloseButton!: boolean;
  @Prop({ type: Number, default: 0 }) unreadLetters!: LetterInboxItemList;

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

    .title { font-size: 1.2em; }
    .unread-count {
      padding: 0.25em;
      margin-left: 0.25em;
      font-size: 0.7em;
      color: $color-dark;
      background-color: $color-primary;
      border-radius: 99999rem;
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
