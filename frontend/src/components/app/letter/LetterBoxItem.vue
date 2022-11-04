<template>
  <router-link class="letter-box-item button"
               :class="{ 'not-read': !letterItem.read }"
               :to="{ name: 'letter-view', params: { letterId: letterItem.id } }">
    <div class="letter-box-item__profile-area vp-small-hide">
      <profile-image class="letter-box-item__profile-image"
                     :srcUrl="'https://picsum.photos/seed/toanyone' + null /*letterItem.sender.profileImageId */ + '/300'" />
    </div>

    <div class="letter-box-item__content-area">
      <div class="letter-box-item__content-area__sender">
        <span class="vp-small-hide">{{ sentByMe ? "To. " : "From. " }}</span>
        <span class="vp-small-show" style="margin-right: 0.5em"><v-icon v-if="sentByMe" size="small">mdi-arrow-right</v-icon><v-icon v-else size="small">mdi-arrow-left</v-icon></span>

        <span style="font-weight: bold">{{ sentByMe ? letterItem.receiverName : letterItem.senderName }}</span>
      </div>
      <div class="letter-box-item__content-area__preview-content">{{ letterItem.content }}</div>
      <div class="letter-box-item__content-area__send-date">{{ new Date(letterItem.sendDate).toLocaleDateString("ko-KR") }}에 전송됨</div>
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
  @Prop({ type: Boolean, default: false, required: true }) sentByMe!: boolean;
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

  &__profile-area {
    padding-right: 1em;
  }

  &__content-area {
    flex-grow: 1;
    overflow: hidden;
    white-space: nowrap;

    & > * {
      overflow: hidden;
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

    &__send-date {
      display: block;
      font-size: 0.8em;
      margin-top: 0.5em;
      text-align: right;
      opacity: 0.8;
    }
  }
}
</style>
