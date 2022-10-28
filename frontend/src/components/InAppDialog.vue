<template>
  <div class="dialog-wrapper">
    <div class="dialog__window">
      <slot />
    </div>
  </div>
</template>

<script lang="ts">
import { Vue } from "vue-class-component";

export default class InAppDialog extends Vue { }
</script>

<style lang="scss" scoped>
.dialog-wrapper {
  position: fixed;
  width: 100vw;
  height: 100vh;
  left: 0;
  top: 0;
  z-index: 100000;

  display: flex;
  align-items: center;
  justify-content: center;

  background: rgba($color-dark, 0.9);
  backdrop-filter: blur(4px);

  .dialog__window {
    background: $color-background;
    padding: 1.5em;
    border-radius: 1em;
    box-shadow: 0 0.25em 0.5em rgba(black, 0.33);
  }
}
</style>
