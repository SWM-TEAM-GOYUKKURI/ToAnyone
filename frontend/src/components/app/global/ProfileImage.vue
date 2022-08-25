<template>
  <v-img class="profile-image"
         :class="{ 'small': size == 'small' }"
         :src="srcUrl"
         aspect-ratio="1" />
</template>

<script lang="ts">
import { Vue } from "vue-class-component";
import { Prop } from "vue-property-decorator";

export default class ProfileImage extends Vue {
  @Prop({ type: String, default: "https://picsum.photos/id/1/300" }) srcUrl!: string;
  @Prop({ type: String, default: "normal" }) size!: "small" | "normal";
}
</script>

<style lang="scss">
.profile-image {
  aspect-ratio: 1;
  width: 6rem;
  max-width: 6rem;
  height: 6rem;
  max-height: 6rem;
  border-radius: 99999em;

  &.small {
    width: 3rem;
    max-width: 3rem;
    height: 3rem;
    max-height: 3rem;
  }
}
</style>
