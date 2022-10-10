<template>
  <v-slide-y-reverse-transition>
    <div id="profile-edit-page-wrapper"
         v-show="show">
      <h1>프로필 수정</h1>
    </div>
  </v-slide-y-reverse-transition>
</template>

<script lang="ts">
import { Vue } from "vue-class-component";

export default class ProfileEditPage extends Vue {
  show = false;

  mounted(): void {
    this.show = true;
  }
}
</script>

<style lang="scss" scoped>
#profile-edit-page-wrapper {
  pointer-events: initial;
  display: block;
  padding: 2rem;
  margin: 2rem;
  min-width: 50vw;
  border-radius: 1rem;
  background: $color-background;
  box-shadow: 0 0.5rem 1rem rgba(#000, 0.33);
}
</style>
