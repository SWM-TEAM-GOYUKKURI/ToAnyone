<!-- UNUSED -->

<template>
    <div class="app-navbar__profile animation-button"
        @click="menuOpened = !menuOpened">
      <profile-image class="app-navbar__profile__profile-image"
                    :srcUrl="profileImageUrl" />
      <div class="app-navbar__profile__nickname">{{ $store.state.user.user.nickname }}</div>
    </div>

    <v-slide-y-transition>
      <div v-show="menuOpened"
          class="app-navbar__profile-menu">
        <router-link class="animation-button"
                    :to="{ name: 'profile' }"
                    @click="onMenuClick">
          <v-icon>mdi-account</v-icon> <span>내 프로필 보기</span>
        </router-link>
        <router-link class="animation-button"
                    :to="{ name: 'logout' }"
                    @click="onMenuClick">
          <v-icon>mdi-logout</v-icon> <span>로그아웃</span>
        </router-link>
      </div>
    </v-slide-y-transition>
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
export default class AppNavbarProfileMenu extends Vue {
  @Prop({ type: String, required: true }) profileImageUrl! :string;

  menuOpened = false;

  onMenuClick(): void { this.menuOpened = false; }
}
</script>

<style lang="scss" scoped>
@mixin button-style {
  display: flex;
  flex-direction: row;
  align-items: center;
  height: 100%;
  background-color: rgba($color-foreground, 0.33);
  border-radius: 999em;
  box-shadow: 0 0.33em 0.75em rgba(24, 24, 24, 0.5);
}

.app-navbar__profile {
  @include button-style();
  cursor: pointer;
  padding: 0.5em 1.25em 0.5em 0.5em;

  &__profile-image {
    aspect-ratio: 1;
    width: auto;
    max-width: unset;
    height: 100%;
    max-height: 100%;
    border-radius: 100%;
    margin-right: 0.66em;
  }

  &__nickname {
    font-size: 1.5em;
    font-weight: 500;
  }
}

.app-navbar__profile-menu {
  position: absolute;
  top: 100%;
  right: 0;
  font-size: 1.25em;

  & > a {
    @include button-style();
    min-width: max-content;
    padding: 0.75em 1.25em;
    margin: 0.5em 0;
    justify-content: space-between;

    & > span {
      margin-left: 1em;
    }
  }
}
</style>
