<template>
  <nav id="app-navbar">
    <div class="app-navbar__left">
      <v-slide-x-transition group
                            leave-absolute
                            origin="center center">
        <a v-if="isCurrentRouteNotHome"
           key="1"
           @click="$router.back()"
           class="app-navbar__go-back"><v-icon>mdi-chevron-left</v-icon></a>
        <div class="app-navbar__title"
             key="2">{{ $route.meta.title }}</div>
      </v-slide-x-transition>
    </div>

    <div class="app-navbar__right">
      <app-navbar-profile-menu :profileImageUrl="tempProfileImage" />
    </div>
  </nav>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import AppNavbarProfileMenu from "./AppNavbarProfileMenu.vue";

@Options({
  components: {
    AppNavbarProfileMenu,
  },
})
export default class AppNavbar extends Vue {
  private tempProfileImage = "https://picsum.photos/seed/toanyone/300";

  get isCurrentRouteNotHome(): boolean {
    return this.$route.name !== "main";
  }
}
</script>

<style lang="scss" scoped>
#app-navbar {
  position: fixed;
  left: 0;
  top: 0;
  right: 0;
  z-index: 10;

  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  height: $app-navbar-height;
  padding: $app-navbar-padding-y $app-navbar-padding-x;

  backdrop-filter: blur(3px);

  .app-navbar {
    &__left, &__right {
      position: relative;
      display: flex;
      align-items: center;
      height: 100%;
    }

    &__title {
      font-size: 2em;
      font-weight: 900;
    }

    &__go-back {
      cursor: pointer;
      font-size: 2em;
      margin-right: 0.5em;
    }
  }
}
</style>
