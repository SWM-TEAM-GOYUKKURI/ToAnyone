<template>
  <div class="profile-area-small">
    <img class="profile-area-small__profile-image" :src="profileImageUrl" />
    <div class="profile-area-small__nickname">{{ nickname }}</div>
  </div>
</template>

<script lang="ts">
import { Vue } from "vue-class-component";
import { Prop } from "vue-property-decorator";

export default class ProfileAreaSmall extends Vue {
  @Prop({ type: String, required: true }) nickname!: string;
  @Prop({ type: String, required: true }) profileImageUrl! :string;
}
</script>

<style lang="scss" scoped>
.profile-area-small {
  $profile-image-size: 5em;

  display: inline-flex;
  flex-direction: row;
  align-items: center;
  padding: 1em 2em 1em 1em;
  margin: 1em;
  background-color: rgba($color-foreground, 0.2);
  border-radius: 999em;
  box-shadow: 0 0.33em 0.75em rgba(24, 24, 24, 0.5);

  &__profile-image {
    width: $profile-image-size;
    height: $profile-image-size;
    border-radius: $profile-image-size;
    margin-right: 1em;
  }

  &__nickname {
    font-size: 1.5em;
    font-weight: 700;
  }
}
</style>
