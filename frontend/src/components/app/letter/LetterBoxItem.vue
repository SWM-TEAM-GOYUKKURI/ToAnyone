<template>
  <router-link class="letter-box-item animation-button"
               :class="{ 'not-read': !letterItem.hadRead }"
               :to="{ name: 'letter-view', params: { letterId: letterItem.id }}">
    <div class="letter-box-item__profile-area">
      <profile-image class="letter-box-item__profile-image"
                     :srcUrl="'https://picsum.photos/seed/toanyone' + null /*letterItem.sender.profileImageId */ + '/300'" />
    </div>

    <div class="letter-box-item__content-area">
      <div class="letter-box-item__content-area__sender">From. {{ letterItem.senderName }}</div>
      <div class="letter-box-item__content-area__preview-content">{{ letterItem.content }}</div>
    </div>
  </router-link>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { Prop } from "vue-property-decorator";
import ProfileImage from "@/components/app/global/ProfileImage.vue";
import { ILetterBoxItem } from "@/interfaces/ILetterItem";

@Options({
  components: {
    ProfileImage,
  },
})
export default class LetterBoxItem extends Vue {
  /* TODO: Needed data for letterItem: sender data(nickname/profile image id), letter read status */
  @Prop({ type: Object, required: true }) letterItem!: ILetterBoxItem;
}
</script>

<style lang="scss">
.letter-box-item {
  cursor: pointer;
  display: flex;
  flex-direction: row;
  padding: 1em;
  background-color: rgba($color-foreground, 0.33);
  border-radius: 2em;
  margin: 1em 0;
  box-shadow: 0 0.33em 0.75em rgba(black, 0.25);
  text-align: left;

  &.not-read {
    background-color: rgba($color-primary, 0.33);
  }

  &__content-area {
    flex-grow: 1;
    margin-left: 1.5em;
    overflow: hidden;
    white-space: nowrap;

    & > * {
      text-overflow: ellipsis;
    }

    &__sender {
      font-size: 2em;
      font-weight: 700;
    }

    &__preview-content {
      font-size: 1.25em;
      margin-top: 0.5em;
    }
  }
}
</style>
