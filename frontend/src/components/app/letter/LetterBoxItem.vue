<template>
  <router-link class="letter-box-item button"
               :class="{ 'not-read': !letterItem.read }"
               :to="{ name: 'letter-view', params: { letterId: letterItem.id } }">
    <div class="letter-box-item__profile-area vp-small-hide">
      <profile-image class="letter-box-item__profile-image"
                     :srcUrl="profileImageUrl" />
    </div>

    <div class="letter-box-item__content-area">
      <div class="letter-box-item__content-area__sender">
        <span class="vp-small-hide">{{ sentByMe ? "To. " : "From. " }}</span>
        <span class="vp-small-show" style="margin-right: 0.5em"><v-icon v-if="sentByMe" size="small">mdi-arrow-right</v-icon><v-icon v-else size="small">mdi-arrow-left</v-icon></span>

        <span style="font-weight: bold">{{ sentByMe ? letterItem.receiverName : letterItem.senderName }}</span>
      </div>
      <div class="letter-box-item__content-area__preview-content"><strong v-if="letterItem.replied">답장 : </strong> {{ letterItem.content }}</div>
      <div class="letter-box-item__content-area__send-date"><span class="vp-small-show">{{ sentByMe ? "보낸 편지" : "받은 편지" }} &bull; </span>{{ new Date(letterItem.sendDate).toLocaleDateString("ko-KR") }}에 전송됨</div>
    </div>

    <div v-if="!letterItem.read" class="letter-box-item__unread-icon" />
  </router-link>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { Prop } from "vue-property-decorator";
import ProfileImage from "@/components/app/global/ProfileImage.vue";
import { LetterInboxItem } from "@/interfaces/backend";
import { getPicsumUrl } from "@/util/path-transform";

@Options({
  components: {
    ProfileImage,
  },
})
export default class LetterBoxItem extends Vue {
  @Prop({ type: Object, required: true }) letterItem!: LetterInboxItem;
  @Prop({ type: Boolean, default: false, required: true }) sentByMe!: boolean;

  get profileImageUrl(): string {
    return getPicsumUrl(this.sentByMe ? parseInt(this.letterItem.receiverImageUrl) : parseInt(this.letterItem.senderImageUrl));
  }
}
</script>

<style lang="scss">
.letter-box-item {
  cursor: pointer;
  position: relative;
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

  &__unread-icon {
    position: absolute;
    right: 0;
    top: 0;
    transform: translate(25%, -25%);
    width: 1.25em;
    height: 1.25em;
    border-radius: 11111em;
    background-color: $color-primary;
  }
}
</style>
