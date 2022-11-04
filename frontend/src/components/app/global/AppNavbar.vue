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
        <div class="app-navbar__title" key="2">{{ navbarTitle }}</div>
      </v-slide-x-transition>
    </div>

    <div class="app-navbar__right">
      <button class="button bg-transparent" @click="toggleSidebar"><v-icon size="xx-large">mdi-menu</v-icon></button>
    </div>
  </nav>

  <v-slide-x-reverse-transition>
    <main-sidebar v-if="($route.name === 'main') || mainSidebarOpen"
                  class="main-sidebar-nav"
                  :class="{ 'vp-small__main-sidebar-nav-hide': !mainSidebarOpen }" />
  </v-slide-x-reverse-transition>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import MainSidebar from "./MainSidebar.vue";

@Options({
  components: {
    MainSidebar,
  },
})
export default class AppNavbar extends Vue {
  mainSidebarOpen = false;

  get navbarTitle(): string {
    return this.$route.meta ? (this.$route.meta.title as string ?? "To. Anyone") : "To. Anyone";
  }

  get isCurrentRouteNotHome(): boolean {
    return this.$route.name !== "main";
  }

  toggleSidebar(): void {
    this.mainSidebarOpen = !this.mainSidebarOpen;
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

  background: rgba($color-background, 0.9);
  backdrop-filter: blur(4px);

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

.main-sidebar-nav {
  @media (max-width: $viewport-main-small-max-width) {
    &.vp-small__main-sidebar-nav-hide {
      display: none;
    }
  }
}
</style>
