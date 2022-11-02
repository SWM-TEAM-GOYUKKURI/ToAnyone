<template>
  <router-link class="letter-box-item button"
               :class="{ 'not-read': !letterItem.read }"
               :to="{ name: 'letter-view', params: { letterId: letterItem.id }}">
    <div class="letter-box-item__profile-area">
      <profile-image class="letter-box-item__profile-image"
                     :srcUrl="'https://picsum.photos/seed/toanyone' + null /*letterItem.sender.profileImageId */ + '/300'" />
    </div>

    <div class="letter-box-item__content-area">
      <div class="letter-box-item__content-area__sender">From. <span style="font-weight: bold">{{ letterItem.senderName }}</span></div>
      <div class="letter-box-item__content-area__preview-content">{{ letterItem.content }}</div>
    </div>
  </router-link>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { Prop } from "vue-property-decorator";
import ProfileImage from "@/components/app/global/ProfileImage.vue";
import { LetterInboxItem } from "@/interfaces/backend";

@Options({
  components: {
    ProfileImage,
  },
})
export default class LetterBoxItem extends Vue {
  /* TODO: Needed data for letterItem: sender data(nickname/profile image id), letter read status */
  @Prop({ type: Object, required: true }) letterItem!: LetterInboxItem;
}
</script>

<style lang="scss">
.letter-box-item {
  cursor: pointer;
  margin: 1em 0;
  border-radius: 1.5em !important;
  box-shadow: 0 0.33em 0.75em rgba($color-dark, 0.25);

  &.not-read {
    background-color: rgba($color-primary, 0.33);
  }

  &__content-area {
    flex-grow: 1;
    padding-left: 1em;
    overflow: hidden;
    white-space: nowrap;

    & > * {
      text-align: left;
      text-overflow: ellipsis;
    }

    &__sender {
      font-size: 1.66em;
    }

    &__preview-content {
      font-size: 1.2em;
      margin-top: 0.5em;
    }
  }
}
</style>
