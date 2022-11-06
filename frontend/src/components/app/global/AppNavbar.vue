<template>
  <nav id="app-navbar">
    <div class="app-navbar__left">
      <v-slide-x-transition group
                            leave-absolute
                            origin="center center">
        <a v-if="!isCurrentRouteMain"
           key="1"
           @click="$router.back()"
           class="app-navbar__go-back"><v-icon>mdi-chevron-left</v-icon></a>
        <div class="app-navbar__title" key="2">{{ navbarTitle }}</div>
      </v-slide-x-transition>
    </div>

    <div class="app-navbar__right">
      <button v-if="!$route.meta.hideNavbarMenu"
              class="button bg-transparent"
              @click="toggleSidebar"><v-icon size="x-large">mdi-menu</v-icon></button>
    </div>
  </nav>

  <main-sidebar :open="mainSidebarOpen"
                :hideCloseButton="this.isCurrentRouteMain && !this.windowSmallViewportOnMainPageMatches"
                class="main-sidebar-nav"
                @request-close="toggleSidebar" />
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
  _mainSidebarOpen = false;
  windowSmallViewportOnMainPageMatches = false;

  get navbarTitle(): string {
    return this.$route.meta ? (this.$route.meta.title as string ?? "To. Anyone") : "To. Anyone";
  }

  get isCurrentRouteMain(): boolean {
    return this.$route.name === "main";
  }

  get mainSidebarOpen(): boolean {
    return (this.isCurrentRouteMain && !this.windowSmallViewportOnMainPageMatches) || // Main page + Large viewport
           (this.isCurrentRouteMain && this.windowSmallViewportOnMainPageMatches && this._mainSidebarOpen) || // Main page + Small viewport + Sidebar button toggled
           this._mainSidebarOpen; // Other pages + Sidebar button toggled
  }

  mounted(): void {
    const smallViewportOnMainPageMatch = matchMedia("(max-width: 1000px)"); // Hardcoded, ref: @/styles/globals.scss
    this.windowSmallViewportOnMainPageMatches = smallViewportOnMainPageMatch.matches;
    smallViewportOnMainPageMatch.addEventListener("change", (ev) => {
      this.windowSmallViewportOnMainPageMatches = ev.matches;
    });
  }

  toggleSidebar(forceState?: boolean): void {
    if(typeof forceState === "undefined") {
      this._mainSidebarOpen = !this._mainSidebarOpen;
    } else {
      this._mainSidebarOpen = forceState;
    }
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
</style>
