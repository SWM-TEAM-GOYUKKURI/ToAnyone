<template>
  <div class="main-sidebar">
    <div class="main-sidebar__profile">
      <profile-image />
      <span class="nickname">{{ $store.state.auth.userBasicInfo.nickname }}</span>
    </div>

    <router-link :to="{ name: 'profile' }"><button class="button dark-background fill-width">프로필 보기</button></router-link>

    <div class="main-sidebar__unread">
      <span class="title">✉️ 읽지 않은 편지 <span v-if="unreadCount >= 1" class="unread-count">{{ unreadCount }}</span></span>
    </div>

    <button class="button bg-transparent fill-width narrow" @click="onLogoutButtonClick"><v-icon size="small">mdi-logout</v-icon> 로그아웃</button>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { Prop } from "vue-property-decorator";
import ProfileImage from "./ProfileImage.vue";

@Options({
  components: {
    ProfileImage,
  },
})
export default class MainSidebar extends Vue {
  @Prop({ type: Number, default: 0 }) unreadCount!: number;

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
}
</style>
