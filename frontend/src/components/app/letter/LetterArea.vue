<template>
  <div class="letter-area"
       :style="decorInlineStyle">
    <div v-if="decorations && decorations.stickers" class="letter-area__decorations">
      <store-item-preview v-for="(sticker, index) in decorations.stickers"
                          :key="index"
                          :item="getStoreItem('stickers', sticker.key)"
                          itemType="stickers"
                          :itemKey="sticker.key"
                          :style="{ left: sticker.x + 'px', top: sticker.y + 'px' }"
                          @click="$emit('stickerClick', index)" />
    </div>

    <div class="letter-area__content-area">
      <div class="fromto">From. <profile-image :srcUrl="getPicsumUrl(realSenderImageId)" size="em" /> <strong>{{ realSenderNickname }}</strong></div>
      <div class="letter-area__content-area__text-area-wrapper">
        <contenteditable v-model="letterTextContent"
                         ref="letterTextElement"
                         class="text-area"
                         tag="div"
                         :contenteditable="letterWriteMode"
                         :no-html="true"
                         @input="onEditorInput"
                         @keydown.ctrl="onEditorKeyDown" />

        <div class="line-wrapper">
          <div ref="letterTextElementFirstLine" class="line" /> <!-- First line is mandatory for height calculation -->
          <div v-for="x in letterTextElementRequiredLines"
               :key="x"
               class="line" />
        </div>
      </div>
      <div class="fromto" style="justify-content: flex-end">To. <profile-image v-if="!letterWriteMode" :srcUrl="getPicsumUrl(realReceiverImageId)" size="em" /> <strong>{{ receiverNickname }}</strong></div>
    </div>

    <div v-if="!letterWriteMode" class="letter-area__controls">
      <button class="button narrow report" title="편지 신고하기" @click="$emit('reportButtonClick')"><v-icon>mdi-alert-octagon</v-icon></button>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { Prop, PropSync, Watch } from "vue-property-decorator";
import contenteditable from "vue-contenteditable";
import ProfileImage from "@/components/app/global/ProfileImage.vue";
import StoreItemPreview from "@/components/app/store/StoreItemPreview.vue";
import { getPicsumUrl } from "@/util/path-transform";
import { LetterStickerItem } from "@/interfaces/internal";
import { getStoreItem, StoreItemFonts, StoreItemPapers } from "@/util/item-loader";

export enum LetterSendStatus {
  NORMAL,
  SENDING,
  DONE,
  ERROR,
}

interface Decorations {
  fontKey?: string,
  paperKey?: string,
  stickers?: LetterStickerItem[],
}

@Options({
  components: {
    contenteditable,
    ProfileImage,
    StoreItemPreview,
  },
})
export default class LetterArea extends Vue {
  LetterSendStatus = LetterSendStatus;
  getPicsumUrl = getPicsumUrl;
  getStoreItem = getStoreItem;

  @Prop({ type: Boolean, default: false }) letterWriteMode!: boolean;
  @Prop({ type: Boolean, default: false }) letterReplyMode!: boolean;
  @Prop({ default: LetterSendStatus.NORMAL }) letterSendStatus!: LetterSendStatus;
  @Prop({ type: String, default: "" }) senderNickname!: string;
  @Prop({ type: String, default: "" }) senderProfileImageId!: string;
  @Prop({ type: String, default: "Anyone" }) receiverNickname!: string;
  @Prop({ type: String, default: "" }) receiverProfileImageId!: string;
  @PropSync("textContent", { type: String, default: "" }) letterTextContent!: string;
  @Prop({ type: Object, default: {} }) decorations!: Decorations;

  letterTextElementHeight = 0;
  letterTextElementLineHeight = 0;
  letterTextElementRequiredLines = 0;

  get realSenderNickname(): string {
    return this.letterWriteMode ? this.$store.state.user.user!.nickname : this.senderNickname;
  }

  get realSenderImageId(): number {
    return this.letterWriteMode ? parseInt(this.$store.state.user.user!.userImageUrl) : parseInt(this.senderProfileImageId);
  }

  get realReceiverImageId(): number {
    return parseInt(this.receiverProfileImageId);
  }

  get letterTextElement(): HTMLDivElement {
    return (this.$refs.letterTextElement as Vue).$el as HTMLDivElement;
  }

  get decorInlineStyle(): Record<string, unknown> {
    const obj: Record<string, unknown> = {};
    if(this.decorations.fontKey) {
      const fontItem = getStoreItem("fonts", this.decorations.fontKey) as StoreItemFonts;
      if(!fontItem.default) import(`@/assets/items/fonts/${this.decorations.fontKey}.css`);
      obj.fontFamily = `"${fontItem.fontFamilyName}", "MaruBuri", serif`;
    }
    if(this.decorations.paperKey) {
      const paperItem = getStoreItem("papers", this.decorations.paperKey) as StoreItemPapers;
      obj.backgroundColor = paperItem.color;
    }
    return obj;
  }

  mounted(): void {
    this.letterTextElementHeight = this.letterTextElement.offsetHeight;
    this.letterTextElementLineHeight = (this.$refs.letterTextElementFirstLine as HTMLDivElement).offsetHeight;
  }

  onEditorKeyDown(event: KeyboardEvent): void {
    if(event.ctrlKey) {
      switch(event.key.toLowerCase()) {
        case "s":
          // CTRL+S : 임시 저장
          event.preventDefault();
          this.$emit("requestTempSave");
          break;
      }
    }
  }

  onEditorInput(): void {
    this.letterTextElementHeight = this.letterTextElement.offsetHeight;

    this.$emit("textareaInput");
  }

  @Watch("letterTextElementHeight", { immediate: true })
  onLetterTextElementHeightChange(): void {
    const required = Math.floor(this.letterTextElementHeight / this.letterTextElementLineHeight);
    if(required) {
      this.letterTextElementRequiredLines = required;
    }
  }
}
</script>

<style lang="scss" scoped>
.letter-area {
  display: flex;
  flex-direction: column;
  position: relative;
  overflow: hidden;
  width: var(--letter-area-width);
  padding: 1em;
  font-size: 1.5em;
  background-color: #FFF7E8;  // 기본값: 개나리 색상
  box-shadow: 0 1em 1.5em rgba(black, 0.33);

  .fromto {
    display: inline-flex;
    flex-direction: row;
    align-items: center;

    & > .profile-image { margin-right: 0.25em; }
  }

  &__decorations {
    z-index: 1;

    & > * {
      cursor: pointer;
      position: absolute;
      width: 96px;
      height: 96px;
    }
  }

  &__content-area {
    white-space: pre-wrap;
    word-break: break-all;

    display: flex;
    flex-grow: 1;
    flex-direction: column;
    width: 100%;
    overflow: hidden;
    color: $color-dark;
    padding-bottom: 1em;

    &__text-area-wrapper {
      position: relative;
      overflow: hidden;
      display: flex;
      flex-grow: 1;
      width: 100%;
      margin: 0.5em 0 1em 0;
      padding: 0 0.5em;
      line-height: 2;

      .text-area {
        flex-grow: 1;

        &:focus, &:focus-visible, &:focus-within {
          border: none;
          outline: none;
        }
      }

      .line-wrapper {
        position: absolute;
        width: calc(100% - (0.5em * 2)); // minus parent padding

        &, & * { pointer-events: none; }

        .line {
          height: 2em; // font-size(1em) * line-height(2)
          border-bottom: solid rgba($color-dark, 0.25) 2px;
        }
      }
    }
  }

  &__controls {
    display: flex;
    justify-content: flex-end;
    margin-top: 0.75em;

    button {
      font-size: 0.66em;

      & > * { margin: 0; }

      &.report {
        background-color: #F99;
        color: #633;
        opacity: 0.5;
        transition: opacity 0.33s;

        &:hover { opacity: 1; }
      }
    }
  }
}
</style>
