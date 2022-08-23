<template>
  <nav id="app-navbar">
    <div class="app-navbar__left">
      <div class="app-navbar__title">To. Anyone</div>
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
}
</script>

<style lang="scss" scoped>
#app-navbar {
  position: fixed;
  left: 0;
  top: 0;
  right: 0;

  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  height: $app-navbar-height;
  padding: $app-navbar-padding-y $app-navbar-padding-x;

  .app-navbar {
    &__left, &__right {
      display: flex;
      align-items: center;
      height: 100%;
    }

    &__title {
      font-size: 2em;
      font-weight: 900;
    }
  }
}
</style>
