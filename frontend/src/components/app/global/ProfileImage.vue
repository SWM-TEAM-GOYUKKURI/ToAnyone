<template>
  <v-img class="profile-image"
         :class="{ small: size === 'small', large: size === 'large' }"
         :src="srcUrl"
         aspect-ratio="1">
    <div v-if="profilePage"
         class="profile-image__edit"
         @click="$emit('profileImageEditClick')">
      <span>이미지 변경</span>
    </div>
  </v-img>
</template>

<script lang="ts">
import { getPicsumUrl } from "@/util/path-transform";
import { Vue } from "vue-class-component";
import { Prop } from "vue-property-decorator";

export default class ProfileImage extends Vue {
  @Prop({ type: String, default: getPicsumUrl(null) }) srcUrl!: string;
  @Prop({ type: String, default: "normal" }) size!: "large" | "small" | "normal";
  @Prop({ type: Boolean, default: false }) profilePage!: boolean;
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

  &.large {
    width: 9rem;
    max-width: 9rem;
    height: 9rem;
    max-height: 9rem;
  }

  &__edit {
    cursor: pointer;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    background-color: rgba($color-dark, 0.75);
    opacity: 0;
    transition: opacity 0.33s;

    &:hover {
      opacity: 1;
    }
  }
}
</style>
