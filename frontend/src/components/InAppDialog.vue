<template>
  <div class="dialog-wrapper"
       :class="{ 'fullscreen-on-vp-small': fullscreenOnVPSmall }">
    <div class="dialog__window">
      <slot />
    </div>
  </div>
</template>

<script lang="ts">
import { Vue } from "vue-class-component";
import { Prop } from "vue-property-decorator";

export default class InAppDialog extends Vue {
  @Prop({ type: Boolean, default: false }) fullscreenOnVPSmall!: boolean;
}
</script>

<style lang="scss" scoped>
.dialog-wrapper {
  position: fixed;
  width: 105vw;
  height: 105vh;
  left: -2.5vw;
  top: -2.5vh;
  z-index: 100000;

  display: flex;
  align-items: center;
  justify-content: center;

  background: rgba($color-dark, 0.9);
  backdrop-filter: blur(4px);

  .dialog__window {
    background: $color-background;
    max-width: 100%;
    padding: 1.5em;
    border-radius: 1em;
    box-shadow: 0 0.25em 0.5em rgba(black, 0.33);
  }

  @media (max-width: $viewport-small-max-width) {
    &.fullscreen-on-vp-small .dialog__window {
      display: flex;
      align-items: center;
      justify-content: center;
      width: 100%;
      height: 100%;

      & > * { width: 100%; }
    }
  }
}
</style>

<style lang="scss">
.dialog-wrapper {
  @media (max-width: $viewport-small-max-width) {
    &.fullscreen-on-vp-small .dialog__window > * {
      width: 100%;
    }
  }
}
</style>
