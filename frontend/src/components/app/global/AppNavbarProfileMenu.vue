<template>
  <div class="app-navbar__profile-menu animation-button">
    <profile-image class="app-navbar__profile-menu__profile-image"
                   :srcUrl="profileImageUrl" />
    <div class="app-navbar__profile-menu__nickname">{{ $store.state.auth.userBasicInfo.nickname }}</div>
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
export default class AppNavbarProfileMenu extends Vue {
  @Prop({ type: String, required: true }) profileImageUrl! :string;
}
</script>

<style lang="scss" scoped>
.app-navbar__profile-menu {
  cursor: pointer;
  display: flex;
  flex-direction: row;
  align-items: center;
  height: 100%;
  padding: 0.5em 1.25em 0.5em 0.5em;
  background-color: rgba($color-foreground, 0.2);
  border-radius: 999em;
  box-shadow: 0 0.33em 0.75em rgba(24, 24, 24, 0.5);

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
</style>
